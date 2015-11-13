package rxc;

import com.rxc.Application;
import com.rxc.ui.UIModule;
import com.rxc.ui_teavm.ModuleImpl;
import org.teavm.jso.dom.html.*;


public class TeavmClient {



  public static void main(String[] args) {
    UIModule uiModule = new ModuleImpl(HTMLDocument.current());
    Application application = new Application(uiModule);



  }


}
