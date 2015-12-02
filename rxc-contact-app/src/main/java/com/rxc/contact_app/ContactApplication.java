package com.rxc.contact_app;

import java.util.UUID;
import com.rxc.contactdata.Contact;
import com.rxc.contactdata.UserContext;
import com.rxc.controller.EntityController;
import com.rxc.dao.RxDao;
import com.rxc.meta.*;
import com.rxc.ui.UIAction;
import com.rxc.ui.UIContainer;

import static com.rxc.lang.Array.*;
import static com.rxc.meta.FieldMeta.FieldMeta;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class ContactApplication {

  private final RxDao<UUID, Contact, UserContext> dao;

  private final EntityController<Contact, Contact.Builder> contactEntityController;


  public ContactApplication(final RxDao<UUID, Contact, UserContext> dao,
      CommonDataDictionary dataDictionary,
      UIContainer rootContainer) {

    EntityMeta<Contact, Contact.Builder> contactMeta =
        new EntityMeta<Contact, Contact.Builder>(
            "Contact",
            Contact.class,
            $$(
                FieldMeta(dataDictionary.firstName, c -> c.firstName, (v, b) -> b.firstName = v),
                FieldMeta(dataDictionary.lastName, c -> c.lastName, (v, b) -> b.lastName = v)),
            Contact.Builder::new
        );
    this.dao = dao;

    contactEntityController = new EntityController<>(contactMeta, rootContainer.container("Contact"), null);

    UIAction saveAction = rootContainer.action("Save");
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


