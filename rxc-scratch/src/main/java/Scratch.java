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


    //PFRest

    public static class PFHttpReq {
        public UUID uuid;
    }

    public static class PFHttpResp {

    }

    public static class PFServiceReqWrapper<Req, Resp, In, Out, Ctx> implements Function<Req, Resp> {

        private final Function<Req, In> xin;
        private final Function<Out, Resp> xout;
        private final Function<Req, Ctx> xCtx;
        private final BiFunction<Ctx, In, Out> service;

        public PFServiceReqWrapper(
                final Function<Req, In> reqToIn,
                final Function<Out, Resp> outToResp,
                final Function<Req, Ctx> reqToCtx,
                final BiFunction<Ctx, In, Out> service
        ) {
            this.xin = reqToIn;
            this.xout = outToResp;
            this.xCtx = reqToCtx;
            this.service = service;
        }


        @Override
        public Resp apply(Req req) {
            final Ctx ctx = xCtx.apply(req); //Could throw Exception
            final In in = xin.apply(req); //Could throw an exception
            final Out out = service.apply(ctx, in);
            return xout.apply(out);
        }
    }


    public static class PFRestlet {
        public final Function<PFHttpReq, PFHttpResp> getByIdReq;
        public final Function<PFHttpReq, PFHttpResp> putReq;
        public final Function<PFHttpReq, PFHttpResp> deleteReq;
        public final Function<PFHttpReq, PFHttpResp> queryReq;

        public PFRestlet(
                final Function<PFHttpReq, PFHttpResp> getByIdReq,
                final Function<PFHttpReq, PFHttpResp> putReq,
                final Function<PFHttpReq, PFHttpResp> deleteReq,
                final Function<PFHttpReq, PFHttpResp> queryReq
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


    public static class PFRestGetReq<DTO>
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


    public static class PFRestletFactory<DTO> {

        public final PFRestlet pfRestlet;

        public PFRestletFactory(
                final Function<DTO, PFHttpResp> dtoToPFResp,
                final PFService<DTO> service
        ) {
            final PFRestGetReq<DTO> pfGet = new PFRestGetReq<>(
                    dtoToPFResp,
                    pfHttpReqToPFContext,
                    service.getById
            );
            pfRestlet = new PFRestlet(
                    pfGet,
                    null,
                    null,
                    null

            );
        }
    }


    //Other Rest/File/Blah Layers ??



    //Service Layer

    public static class FooDto {
    }


    public static class PFServiceFactory<DTO, E>  {


        public  final PFService<DTO> pfService;

      public PFServiceFactory(Dao<DTO, E, ?> pfDoa) {
        pfService = new PFService<>(pfDoa.)

        }

        @Override
        public PFService<DTO> get() {
            return fooService;
        }


    }

    public static class PFService<DTO> {
       public final BiFunction<PFContext, UUID, DTO> getById;

        public final BiConsumer<PFContext, DTO> update;

        public PFService(
                final BiFunction<PFContext, UUID, DTO> getById,
                final BiConsumer<PFContext, DTO> update
        ) {
            this.getById = getById;
            this.update = update;
        }
    }


    public static class PFContext {

    }


    public static class UUID {

    }


    //+ Dao Layer
    //CommonDoa
    public static class Dao<E, U, ID> {

        public static class Params<U, P> {
            public final U userCtx;
            public final P params;


            public Params(U userCtx, P params) {
                this.userCtx = userCtx;
                this.params = params;
            }
        }


        public final Function<Params<U, ID>, E> getById;

      public Dao(
          final Function<Params<U, ID>, E> getById
      ) {
            this.getById = getById;
        }

    }


    //+ MockDao
    public static class MockDaoGetByIdReq<E, U, ID> implements Function<Dao.Params<U, ID>, E> {
        private final Supplier<E> factory;

        public MockDaoGetByIdReq(Supplier<E> factory) {
            this.factory = factory;
        }

        @Override
        public E apply(Dao.Params<U, ID> p) {
            return factory.get();
        }
    }

  public static class MockDaoFactory<E, U, ID> {
    public final Dao<E, U, ID>                  dao;
    public final Function<Dao.Params<U, ID>, E> getByIdReq;

    public MockDaoFactory(Supplier<E> entityFactory) {
      getByIdReq = new MockDaoGetByIdReq<>(entityFactory);

      dao = new Dao<>(getByIdReq);
    }

    //here
    }


  // PF

  public static class UserCtx {

  }

  //Foo

    public static class Foo {

    }

    public static class FooMeta {

    }

    //ES FoaDaoFactory

  public static FooMockDaoFactory fooMockDaoFactory = new MockDaoFactory<Foo, UserCtx, UUID>()


    public static class FooDaoESFactory implements Supplier<Dao<Foo, PFDao.UserCtx>> {


        private final Dao<Foo, PFDao.UserCtx> fooDao = new Dao<>(it -> new Foo());


        @Override

        public Dao<Foo, PFDao.UserCtx> get() {
            return fooDao;
        }
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

      MockDaoFactory<>
      PFService<FooDto> fooGetService = new PFServiceFactory<FooDto, Foo>()

        final CaseFunction<HttpRequest, HttpResponse, ReqKey> caseFunction = new CaseFunction<>(
                "dispatcher",
                $$(
                        $(
                                new ReqKey(ReqType.PF_GET, "foo"),
                                new PFServiceReqWrapper<>(
                                        xin,
                                        xout,
                                        xCtx,
                                        fooGetService
                                )
                        )
                ),
                keyFunc
        );


    }
}

