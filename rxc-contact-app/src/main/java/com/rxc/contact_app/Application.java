package com.rxc.contact_app;

import com.rxc.contactdata.Contact;
import com.rxc.contactdata.UserContext;
import com.rxc.contoller.EntityController;
import com.rxc.dao.RxDao;
import com.rxc.lang.*;
import com.rxc.meta.*;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIContainer;
import com.rxc.ui.UIStringEditField;

import static com.rxc.lang.Array.*;
import static com.rxc.lang.Map.*;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class Application {

  private final RxDao<UUID, Contact, UserContext> dao;

  private final EntityController<Contact, Contact.Builder> contactEntityController;


  public Application(final RxDao<UUID, Contact, UserContext> dao,
                     CommonDataDictionary dataDictionary,
                     UIContainer rootContainer) {

    EntityMeta<Contact, Contact.Builder> contactMeta =
        new EntityMeta<>(
            "Contact",
            Contact.class,
            $$(
                new FieldMetaImpl<>(dataDictionary.firstName, c -> c.firstName, (v, b) -> b.firstName = v),
                new FieldMetaImpl<>(dataDictionary.lastName, c -> c.lastName, (v, b) -> b.lastName = v)),
            Contact.Builder::new
        );
    this.dao = dao;

    contactEntityController = new EntityController<>(contactMeta, rootContainer.component("Contact"), null);

    UIAction saveAction = rootContainer.component("Save");
    saveAction.listener(this::saveContract);
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


