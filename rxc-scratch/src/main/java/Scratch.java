import com.rxc.lang.collection.$_;
import com.rxc.lang.functional.CaseFunction;
import com.rxc.lang.tuple.T2;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.rxc.lang.collection.$$;

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


    public static class RestDataService<Req, Resp> {
        public final String assetType;
        public final Function<Req, Resp> getByIdReq;
        public final Function<Req, Resp> putReq;
        public final Function<Req, Resp> deleteReq;
        public final Function<Req, Resp> queryReq;

        public RestDataService(
                final String assetType,
                final Function<Req, Resp> getByIdReq,
                final Function<Req, Resp> putReq,
                final Function<Req, Resp> deleteReq,
                final Function<Req, Resp> queryReq
        ) {
            this.assetType = assetType;
            this.getByIdReq = getByIdReq;
            this.putReq = putReq;
            this.deleteReq = deleteReq;
            this.queryReq = queryReq;
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
    enum PFReqType {
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

    enum PFCOntentType {
        JSON,
        XML,
        TEXT
    }

    /**
     * All requests should be resovable to a type (class) and an asset (instance), although some requests
     * will have no asset type because it doesn;t exist (_health/node) or is implied (LOAD_FILE - assetType = "file")
     */
    public static class PFReqKey {
        public final PFReqType PFReqType;
        public final String assetType;

        public PFReqKey(
                final PFReqType reqType,
                final String assetType
        ) {
            this.PFReqType = reqType;
            this.assetType = assetType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PFReqKey reqKey = (PFReqKey) o;

            if (PFReqType != reqKey.PFReqType) return false;
            return assetType != null ? assetType.equals(reqKey.assetType) : reqKey.assetType == null;

        }

        @Override
        public int hashCode() {
            int result = PFReqType != null ? PFReqType.hashCode() : 0;
            result = 31 * result + (assetType != null ? assetType.hashCode() : 0);
            return result;
        }
    }


    public static class PFHttpReq {
        public UUID uuid;
        public PFReqType pfReqType;
        public String[] path;
        public $_<String, $$<Object>> parameters;
        public HttpProtocol httpProtocol;
        public String body;
        public String someHeader;
    }

    public static class PFHttpResp {
        public String body;
    }

    public static class PFContext {

    }


    public static class UUID {
        public final byte[] value;

        public UUID(byte[] value) {
            this.value = value;
        }
    }

    public static class UserCtx {

    }

    public static Function<HttpRequest, PFHttpReq> httpReqTpFHttpReq =
            r -> new PFHttpReq(
            );

    public static Function<PFHttpResp, HttpResponse> pfHttpRespToHttpResponse =
            r -> new HttpResponse();

    public static Function<PFHttpReq, PFContext> pfHttpReqToPFContext =
            r -> new PFContext();


    public static class PFRestDataServiceReqWrapper<In, Out> implements Function<PFHttpReq, PFHttpResp> {

        private final Function<PFHttpReq, In> xin;
        private final Function<Out, PFHttpResp> xout;
        private final Function<PFHttpReq, PFContext> xCtx;
        private final BiFunction<PFContext, In, Out> service;

        public PFRestDataServiceReqWrapper(
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
        public PFHttpResp apply(
                final PFHttpReq req
        ) {
            final PFContext ctx = xCtx.apply(req); //Could throw Exception
            final In in = xin.apply(req); //Could throw an exception
            final Out out = service.apply(ctx, in);
            return xout.apply(out);
        }
    }

    public static class PFRestDataServiceFactory<DTO> {
        public final RestDataService<PFHttpReq, PFHttpResp> restDataService;

        public PFRestDataServiceFactory(
                final String assetType,
                final DataService<PFContext, UUID, DTO> dataService,
                final Function<DTO, PFHttpResp> dtoToPfHttpResp
        ) {
            restDataService = new RestDataService<>(
                    assetType,
                    new PFRestDataServiceReqWrapper<>(
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


    public static void main(String[] args) {
        //TODO: create a test harness


        //Foo
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
                        "foo",
                        fooDataServiceFactory.pfService,
                        dtoToPfHttpResp
                );


        final Function<RestDataService<PFHttpReq, PFHttpResp>, $$<T2<PFReqKey, Function<PFHttpReq, PFHttpResp>>>> ff =
                service -> $$(
                        $(new PFReqKey(
                                        PFReqType.PF_GET, service.assetType),
                                service.getByIdReq
                        )
                );

        final $$<T2<PFReqKey, Function<PFHttpReq, PFHttpResp>>> fooDispatch = ff.apply(fooDtoPFRestDataServiceFactory.restDataService);

        //Bar - TODO
        final $$<T2<PFReqKey, Function<PFHttpReq, PFHttpResp>>> barDispatch = $$.$0();

        //Dispatcher
        final CaseFunction<PFHttpReq, PFHttpResp, PFReqKey> dispatcher = new CaseFunction<>(
                "dispatcher",
                $$(
                        fooDispatch,
                        barDispatch
                ),
                req -> new PFReqKey(req.pfReqType, req.path.length > 0 ? req.path[0] : null)
        );


        // Do some Stuff
        final HttpRequest httpRequest = new HttpRequest(
                HttpAction.GET,
                HttpProtocol.HTTP,
                "/foo/adadsad/asdsadsad".split("/"),
                null,
                null
        );

        final PFHttpReq pfHttpReq = httpReqTpFHttpReq.apply(httpRequest);

        final PFHttpResp fpHttpResp = dispatcher.apply(pfHttpReq);

        final HttpResponse httpResponse = pfHttpRespToHttpResponse.apply(fpHttpResp);


    }
}

