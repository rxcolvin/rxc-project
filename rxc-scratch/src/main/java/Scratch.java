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


    // com.rxc.lang.functional.CaseFunction

    public enum HttpAction {
        GET, PUT, DELETE
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
        UNKNOWN
    }

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




    //Rest Layer


    public static class PFServiceReqWrapper<Req, Resp, In, Out, Ctx> implements Function<Req, Resp> {

        private final Function<Req, In> xin;
        private final Function<Out, Resp> xout;
        private final Function<Req, Ctx> xCtx;
        private final BiFunction<Ctx, In, Out> service;

        public PFServiceReqWrapper(
                final Function<Req, In> xin,
                final Function<Out, Resp> xout,
                final Function<Req, Ctx> xCtx,
                final BiFunction<Ctx, In, Out> service
        ) {
            this.xin = xin;
            this.xout = xout;
            this.xCtx = xCtx;
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
        public final Function<HttpRequest, HttpResponse> getByIdReq;
        public final Function<HttpRequest, HttpResponse> putReq;
        public final Function<HttpRequest, HttpResponse> deleteReq;
        public final Function<HttpRequest, HttpResponse> queryReq;

        public PFRestlet(
                final Function<HttpRequest, HttpResponse> getByIdReq,
                final Function<HttpRequest, HttpResponse> putReq,
                final Function<HttpRequest, HttpResponse> deleteReq,
                final Function<HttpRequest, HttpResponse> queryReq
        ) {
            this.getByIdReq = getByIdReq;
            this.putReq = putReq;
            this.deleteReq = deleteReq;
            this.queryReq = queryReq;
        }
    }

    //Service Layer

    public static class FooDto {
    }


    public static class PFServiceFactory<DTO, E> implements Supplier<PFService<DTO>> {


        private final PFService<DTO> fooService;

        public PFServiceFactory() {
            fooService = new PFService<>(it -> new FooDto(), update);
        }

        @Override
        public PFService<DTO> get() {
            return fooService;
        }



    }

    public static class PFService<DTO_TYPE> {


        public final Function<UUID, DTO_TYPE> getById;

        public final BiConsumer<UUID, DTO_TYPE> update;

        public PFService(
                final Function<UUID, DTO_TYPE> getById,
                final BiConsumer<UUID, DTO_TYPE> update
        ) {
            this.getById = getById;
            this.update = update;
        }
    }


    public static class PFContext {

    }

    //
    public static class ID {

    }

    public static class UUID {

    }

    public static class PFDao {
        public static class UserCtx {

        }
    }

    //CommonDoa
    public static class SimpleDao<DAO_TYPE, CTX> {

        public static class Params<U, P> {
            public final U userCtx;
            public final P params;


            public Params(U userCtx, P params) {
                this.userCtx = userCtx;
                this.params = params;
            }
        }


        public final Function<Params<CTX, ID>, DAO_TYPE> getById;

        public SimpleDao(Function<Params<CTX, ID>, DAO_TYPE> getById) {
            this.getById = getById;
        }

    }


    //FooDao

    public static class Foo {

    }

    public static class FooMeta {

    }

    //ES FoaDaoFactory


    public static class FooDaoESFactory implements Supplier<SimpleDao<Foo, PFDao.UserCtx>> {


        private final SimpleDao<Foo, PFDao.UserCtx> fooDao = new SimpleDao<>(it -> new Foo());


        @Override

        public SimpleDao<Foo, PFDao.UserCtx> get() {
            return fooDao;
        }
    }

    static Function<HttpRequest, ReqKey> keyFunc = r -> new ReqKey(ReqType.PF_GET, "");

    static Function<HttpRequest, UUID> xin;

    static Function<FooDto,HttpResponse> xout;
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

        final

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

