import com.rxc.lang.functional.CaseFunction;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.rxc.lang.collection.$$.$$;
import static com.rxc.lang.tuple.Tuple.*;

/**
 * map HttpRequest -> Context1
 * validate Context1
 * match ServiceContext
 */
public class Scratch {

    // HttpEngine Layer

    // Http Layer
    public enum HttpAction {
        GET, PUT, DELETE, HEADER
    }

    public enum HttpProtocol {
        HTTP, HTTPS
    }

    static class HttpRequest {
        public final HttpAction action;
        public final HttpProtocol httpProtocol;
        public final String[] path;
        public final Map<String, String[]> parameters;
        public final String data;

        HttpRequest(
                final HttpAction action,
                final HttpProtocol httpProtocol,
                final String[] path,
                final Map<String, String[]> parameters,
                final String data

        ) {

            this.action = action;
            this.httpProtocol = httpProtocol;
            this.path = path;
            this.parameters = parameters;
            this.data = data;
        }
    }

    static class HttpResponse {
        int respCode;
        String body;
        String format;

    }

    enum ReqType {
        PF_GET,
        PF_QUERY,
        PF_DELETE,
        PF_UPDATE,
        PF_CREATE,
        PF_HEALTHCHECK,
        PF_NODE_HEALTH,
        PF_GETDOC,
        UNKNOWN
    }

    /**
     * All requests should be resovable to a type (class) and an asset (instance), although some requests
     * will have no asset type because it doesn;t exist (_health/node) or is implied (LOAD_FILE - assetType = "file")
     */
    public static class ReqKey {
        public final ReqType reqType;
        public final String assetType;

        public ReqKey(
                final ReqType reqType,
                final String assetType
        ) {
            this.reqType = reqType;
            this.assetType = assetType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ReqKey reqKey = (ReqKey) o;

            if (reqType != reqKey.reqType) return false;
            return assetType != null ? assetType.equals(reqKey.assetType) : reqKey.assetType == null;

        }

        @Override
        public int hashCode() {
            int result = reqType != null ? reqType.hashCode() : 0;
            result = 31 * result + (assetType != null ? assetType.hashCode() : 0);
            return result;
        }
    }





    public static class RestDataService<Req, Resp> {
        public final Function<Req, Resp> getByIdReq;
        public final Function<Req, Resp> putReq;
        public final Function<Req, Resp> deleteReq;
        public final Function<Req, Resp> queryReq;

        public RestDataService(
                final Function<Req, Resp> getByIdReq,
                final Function<Req, Resp> putReq,
                final Function<Req, Resp> deleteReq,
                final Function<Req, Resp> queryReq
        ) {
            this.getByIdReq = getByIdReq;
            this.putReq = putReq;
            this.deleteReq = deleteReq;
            this.queryReq = queryReq;
        }
    }


    public static Function<HttpRequest, PFHttpReq> httpReqTpFHttpReq =
            r -> new PFHttpReq(
            );

    public static Function<PFHttpResp, HttpResponse> pfHttpRespToHttpResponse =
            r -> new HttpResponse();

    public static Function<PFHttpReq, PFContext> pfHttpReqToPFContext =
            r -> new PFContext();


    public static class PFRestGetReq<CTX, ID, DTO>
            implements Function<PFHttpReq, PFHttpResp> {


        private final PFServiceReqWrapper<PFHttpReq, PFHttpResp, UUID, DTO, PFContext> getReq;

        public PFRestGetReq(
                final Function<DTO, PFHttpResp> dtoToPFResp,
                final Function<PFHttpReq, PFContext> pfHttpReqToPFContext,
                final BiFunction<PFContext, UUID, DTO> getService
        ) {

            getReq = new PFServiceReqWrapper<>(
                    r -> r.uuid,
                    dtoToPFResp,
                    pfHttpReqToPFContext,
                    getService

            );
        }

        @Override
        public PFHttpResp apply(PFHttpReq httpRequest) {
            return getReq.apply(httpRequest);
        }
    }





    //Other Rest/File/Blah Layers ??


    //Service Layer


    public static class DataService<CTX, ID, DTO> {
        public final BiFunction<CTX, ID, DTO> getById;

        public final BiConsumer<CTX, DTO> update;

        public DataService(
                final BiFunction<CTX, ID, DTO> getById,
                final BiConsumer<CTX, DTO> update
        ) {
            this.getById = getById;
            this.update = update;
        }
    }


    /**
     * Creates a production DataService instance that calls a dao.
     *
     * @param <CTX>  servivce context type
     * @param <DTO>  service data transfer object type
     * @param <ID>   id type
     * @param <UCTX> dao user context
     * @param <E>    dao entity type
     */
    public static class DataServiceFactory<CTX, DTO, ID, UCTX, E> {


        public final DataService<CTX, ID, DTO> pfService;

        public DataServiceFactory(
                final Dao<E, UCTX, ID> dao,
                final Function<E, DTO> entityToDto,
                final Function<CTX, UCTX> ctxToUctx
        ) {
            final BiFunction<CTX, ID, DTO> getById
                    = (ctx, id) -> entityToDto.apply(dao.getById.apply(ctxToUctx.apply(ctx), id));

            pfService = new DataService<>(
                    getById,
                    null
            );

        }


    }


    //+ Dao Layer
    //CommonDoa
    public static class Dao<E, UCTX, ID> {


        public final BiFunction<UCTX, ID, E> getById;

        public Dao(
                final BiFunction<UCTX, ID, E> getById
        ) {
            this.getById = getById;
        }

    }


    //+ MockDao


    //TODO: replace with in-memory cache etc
    public static class MockDaoFactory<E, UCTX, ID> {
        public final Dao<E, UCTX, ID> dao;

        public MockDaoFactory(
                final Supplier<E> entityFactory
        ) {
            dao = new Dao<>((u, id) -> entityFactory.get());
        }

        //here
    }

    public static class MockDaoFactoryFunction<E, UCTX, ID> implements Function<Supplier<E>, Dao<E, UCTX, ID>> {
        @Override
        public Dao<E, UCTX, ID> apply(Supplier<E> entityFactory) {
            return new Dao<>((u, id) -> entityFactory.get());
        }
    }

    public static <E, UCTX, ID> Function<Supplier<E>, Dao<E, UCTX, ID>> mockDaoFactory() {
        return new MockDaoFactoryFunction<>();
    }

    //PlaceHolder
    public static class ESDaoFactory<E, U, ID> {

    }

    //PlaceHolder
    public static class JdbcDaoFactory<E, U, ID> {

    }


    // General PF


    public static class PFHttpReq {
        public UUID uuid;
    }

    public static class PFHttpResp {

    }

    public static class PFContext {

    }


    public static class UUID {

    }

    public static class UserCtx {

    }


    public static class PFServiceReqWrapper<In, Out> implements Function<PFHttpReq, PFHttpResp> {

        private final Function<PFHttpReq, In> xin;
        private final Function<Out, PFHttpResp> xout;
        private final Function<PFHttpReq, PFContext> xCtx;
        private final BiFunction<PFContext, In, Out> service;

        public PFServiceReqWrapper(
                final Function<PFHttpReq, In> reqToIn,
                final Function<Out, PFHttpResp> outToResp,
                final Function<PFHttpReq, PFContext> reqToCtx,
                final BiFunction<PFContext, In, Out> service
        ) {
            this.xin = reqToIn;
            this.xout = outToResp;
            this.xCtx = reqToCtx;
            this.service = service;
        }


        @Override
        public PFHttpResp apply(PFHttpReq req) {
            final PFContext ctx = xCtx.apply(req); //Could throw Exception
            final In in = xin.apply(req); //Could throw an exception
            final Out out = service.apply(ctx, in);
            return xout.apply(out);
        }
    }

    public static class PFRestDataServiceFactory<DTO> {
        public final RestDataService<PFHttpReq, PFHttpResp> restDataService;

        public PFRestDataServiceFactory(
                final DataService<PFContext, UUID, DTO> dataService,
                final Function<DTO, PFHttpResp> dtoToPfHttpResp
        ) {
            restDataService = new RestDataService<>(
                    new PFServiceReqWrapper<>(
                            r -> r.uuid,
                            dtoToPfHttpResp,
                            pfHttpReqToPFContext,
                            dataService.getById
                    ),
                    null,
                    null,
                    null
            );
        }
    }



    //Foo

    public static class Foo {

    }

    public static class FooDto {
    }


    static Function<HttpRequest, ReqKey> keyFunc = r -> new ReqKey(ReqType.PF_GET, "");

    static Function<HttpRequest, UUID> xin;

    static Function<FooDto, HttpResponse> xout;
    static Function<HttpRequest, PFContext> xCtx;


    public static void main(String[] args) {
        //TODO: create a test harness

        final HttpRequest httpRequest = new HttpRequest(
                HttpAction.GET,
                HttpProtocol.HTTP,
                "/foo/adadsad/asdsadsad".split("/"),
                null,
                null
        );

        final MockDaoFactory<Foo, UserCtx, UUID> fooMockDaoFactory = new MockDaoFactory<>(Foo::new);

        final DataServiceFactory<PFContext, FooDto, UUID, UserCtx, Foo> fooDataServiceFactory =
                new DataServiceFactory<>(
                        fooMockDaoFactory.dao,
                        null,
                        null
                );


        final Function<FooDto, PFHttpResp> dtoToPfHttpResp = null;

        final PFRestDataServiceFactory<FooDto> fooDtoPFRestDataServiceFactory =
                new PFRestDataServiceFactory<>(
                        fooDataServiceFactory.pfService,
                        dtoToPfHttpResp
                        );



        final CaseFunction<HttpRequest, HttpResponse, ReqKey> caseFunction = new CaseFunction<>(
                "dispatcher",
                $$(
                        $(
                                new ReqKey(ReqType.PF_GET, "foo"),
                                new PFServiceReqWrapper<>(
                                        xin,
                                        xout,
                                        xCtx,
                                        fooDataServiceFactory.
                                )
                        )
                ),
                keyFunc
        );


    }
}

