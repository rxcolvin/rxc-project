import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * map HttpRequest -> Context1
 * validate Context1
 * match ServiceContext
 */
public class Scratch {


  // Dispatch

  public static enum HttpAction {
    GET, PUT
  }

  public static enum HttpProtocol {
    HTTP, HTTPS
  }

  static class HttpRequest {
    public final HttpAction            action;
    public final HttpProtocol          httpProtocol;
    public final String[]              path;
    public final Map<String, String[]> parameters;
    public final String                data;

    HttpRequest(HttpAction action,
                HttpProtocol httpProtocol,
                String[] path,
                Map<String, String[]> parameters,
                String data) {

      this.action = action;
      this.httpProtocol = httpProtocol;
      this.path = path;
      this.parameters = parameters;
      this.data = data;
    }
  }

  static class HttpResponse {
    int    respCode;
    String body;
    String format;

  }



  public static class DispatchContext {
    public final UUID     authKey;
    public final Class<?> assetType;

    public DispatchContext(UUID authKey, Class<?> assetType) {
      this.authKey = authKey;
      this.assetType = assetType;
    }
  }

  public static class MatchParams {
    public final HttpProtocol httpProtocol;
    public final List<String> path;
    public final HttpAction   action;

    public MatchParams(HttpProtocol httpProtocol, List<String> path, HttpAction action) {
      this.httpProtocol = httpProtocol;
      this.path = path;
      this.action = action;
    }
  }


  public static class Dispatch implements Function<HttpRequest, HttpResponse> {
    final List<Function<MatchParams, Optional<Function<HttpRequest, HttpResponse>>>> matchers;
    final Function<HttpRequest, MatchParams>                                         buildMatchParameters;

    public Dispatch(Function<HttpRequest, MatchParams> buildMatchParameters,
                    Function<MatchParams, Optional<Function<HttpRequest, HttpResponse>>>... matchers) {
      this.buildMatchParameters = buildMatchParameters;
      this.matchers = Arrays.asList(matchers);
    }

    public HttpResponse apply(final HttpRequest httpRequest) {

      //TODO: Implement Cache
      List<Function<HttpRequest, HttpResponse>> x =
          matchers.stream()
                  .map((it) -> it.apply(buildMatchParameters.apply(httpRequest)))
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .collect(Collectors.toList());
      if (x.size() == 1) {
        return x.get(0).apply(httpRequest);
      }
      throw new RuntimeException("No Matcher or too many matches"); //TODO define exceptions
    }
  }


  // Non-Rest Handlers
  public static class FileReqHandler implements Function<MatchParams, Optional<Function<HttpRequest, HttpResponse>>> {

    @Override
    public Optional<Function<HttpRequest, HttpResponse>> apply(MatchParams matchParams) {
      return null;
    }
  }

  //Rest Layer


  public static class GetByIdRestReq implements Function<HttpRequest, HttpResponse> {

    @Override public HttpResponse apply(HttpRequest httpRequest) {
      return null;
    }
  }


  public static class SimplePFRestlet
      implements Function<MatchParams, Optional<Function<HttpRequest, HttpResponse>>> {
    public Function<HttpRequest, HttpResponse> getByIdReq;
    public Function<HttpRequest, HttpResponse> putReq;
    public Function<HttpRequest, HttpResponse> deleteReq;

    @Override
    public Optional<Function<HttpRequest, HttpResponse>> apply(MatchParams matchParams) {
      return null;
    }
  }

  //Service Layer

  public static class FooDto {
  }

  public static class FooByIdParams {
  }

  public static class FooPFServiceFactory implements Supplier<PFService<FooDto, FooByIdParams>> {


    private final PFService<FooDto, FooByIdParams> fooService;

    public FooPFServiceFactory() {
      fooService = new PFService<>(it -> new FooDto());
    }

    @Override public PFService<FooDto, FooByIdParams> get() {
      return fooService;
    }
  }

  public static class PFService<DTO_TYPE, ID_PARAMS> {


    public final Function<ID_PARAMS, DTO_TYPE> getById;

    public PFService(Function<ID_PARAMS, DTO_TYPE> getById) {
      this.getById = getById;
    }
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


}

