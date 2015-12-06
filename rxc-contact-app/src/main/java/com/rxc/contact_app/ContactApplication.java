package com.rxc.contact_app;

import com.rxc.app.LifeCycle;
import com.rxc.contactdata.Contact;
import com.rxc.contactdata.UserContext;
import com.rxc.controller.ControllerModule;
import com.rxc.controller.EntityController;
import com.rxc.dao.RxDao;
import com.rxc.meta.CommonDataDictionary;
import com.rxc.meta.EntityMeta;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIAppContainer;
import com.rxc.ui.UIContainer;

import java.util.UUID;

import static com.rxc.lang.$$.$$;
import static com.rxc.meta.FieldMeta.FieldMeta;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class ContactApplication implements LifeCycle {

  public final static String $save = "Save";

  private final RxDao<UUID, Contact, UserContext> dao;

  private final EntityController<Contact, Contact.Builder> contactEntityController;
  private final ControllerModule controllerModule;
  private final UIAction         saveAction;
  private final UIAppContainer   appContainer;


  public ContactApplication(
      final RxDao<UUID, Contact, UserContext> dao,
      final CommonDataDictionary dataDictionary,
      final UIAppContainer appContainer) {
    this.appContainer = appContainer;

    //TODO: Should be in contact-meta-data module?
    EntityMeta<Contact, Contact.Builder> contactMeta =
        new EntityMeta<Contact, Contact.Builder>(
            Contact.$myName,
            Contact.class,
            $$(
                FieldMeta(dataDictionary.firstName, c -> c.firstName, (v, b) -> b.firstName = v),
                FieldMeta(dataDictionary.lastName, c -> c.lastName, (v, b) -> b.lastName = v)),
            Contact.Builder::new
        );
    this.dao = dao;
    controllerModule = new ControllerModule();

    contactEntityController = controllerModule.entityControllerFactory.create(contactMeta, appContainer.rootContainer()
                                                                                                       .container(
                                                                                                           (Contact.$myName)));

    saveAction = appContainer.rootContainer().action($save);
  }

  @Override public void start() {
    saveAction.listener(this::saveContract);

    appContainer.closeListener(() -> true);

  }

  @Override public void stop() {
    saveAction.listener(null);
  }

  /**
   * @return
   */
  @Override public boolean isSafeToStop() {
    return true;
  }

  private void saveContract() {
    Contact contact = contactEntityController.model();
    dao.create(contact.id, contact, null);
  }

  //  private void sendReq(Event e) {
  //    try {
  //       XMLHttpRequest xhr = XMLHttpRequest.create();
  //      xhr.onComplete(() -> receiveResponse(xhr));
  //      //xhr.open("GET", url.getValue());
  //      xhr.setRequestHeader("Accept-Language", "en,en-US;q=0.8");
  //      xhr.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
  //      xhr.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like
  // Gecko) Chrome/46.0.2490.80 Safari/537.36");
  //      xhr.setRequestHeader("Accept", "ext/html,application/xhtml xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
  //      xhr.send();
  //    } catch (Exception ex) {
  //      ex.printStackTrace();
  //    }
  //
  //  }
  //
  //  private void receiveResponse(final XMLHttpRequest req) {
  //    Window.alert("" + req.getStatus());
  //    Window.alert(req.getResponseText());
  //  }


}


