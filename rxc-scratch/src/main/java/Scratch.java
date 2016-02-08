import com.rxc.lang.T2;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
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

  }

  static class Outlet {

  }


  public static class DispatchContext {
    public final UUID     authKey;
    public final Class<?> assetType;

    public DispatchContext(UUID authKey, Class<?> assetType) {
      this.authKey = authKey;
      this.assetType = assetType;
    }
  }

  public static class HttpRequestToDispatchContext implements Function<HttpRequest, DispatchContext> {
    @Override public DispatchContext apply(HttpRequest httpRequest) {
      return null;
    }
  }

  public static class Dispatch implements Function<DispatchContext, HttpResponse> {
    final List<Function<DispatchContext, Optional<Function<DispatchContext, HttpResponse>>>> matchers;

    public Dispatch(List<Function<DispatchContext, Optional<Function<DispatchContext, HttpResponse>>>> matchers) {
      this.matchers = matchers;
    }

    public HttpResponse apply(final DispatchContext dispatchContext) {
      List<Function<DispatchContext, HttpResponse>> x =
          matchers.stream()
                  .map((it) -> it.apply(dispatchContext))
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .collect(Collectors.toList());
      if (x.size() == 1) {
        return x.get(0).apply(dispatchContext);
      }
      throw new RuntimeException("No Matcher or too many matches"); //TODO define exceptions
    }
  }


  //Service
  public static class ServiceContext {

  }

  public static class Foo {

  }

  public static class GetFooById implements Function<T2<ServiceContext, String>, Foo> {

    public Foo apply(T2<ServiceContext, String> contextStringT2) {
      return null;
    }
  }
}
