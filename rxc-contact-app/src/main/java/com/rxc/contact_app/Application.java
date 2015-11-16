package com.rxc;

import com.rxc.contactdata.Contact;
import com.rxc.contactdata.UserContext;
import com.rxc.dao.DaoModule;
import com.rxc.dao.RxDao;
import com.rxc.lang.UUID;
import com.rxc.ui.model.UIAction;
import com.rxc.ui.model.UIModule;
import com.rxc.ui.model.UIStringEditField;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class Application {

  private final UIModule uiModule;
  private final DaoModule daoModule;
  private RxDao<Contact, UUID, UserContext> dao;
  private UIStringEditField firstName;
  private UIStringEditField lastName;
  private UIAction saveUiAction;

  public Application(UIModule uiModule, DaoModule daoModule) {

    this.uiModule = uiModule;
    this.daoModule = daoModule;
  }

  public void start() {

    firstName = uiModule.factory(UIStringEditField.class).create("firstName");
    lastName = uiModule.factory(UIStringEditField.class).create("lastName");
    saveUiAction = uiModule.factory(UIAction.class).create("save");

    dao = daoModule.rxDoa(
        Contact.class, UUID.class, UserContext.class
    );

    saveUiAction.listener(this::save);


  }

  private void save() {
     firstName.value();
     Contact contact = new Contact();
  }

//  private void sendReq(Event e) {
//    try {
//       XMLHttpRequest xhr = XMLHttpRequest.create();
//      xhr.onComplete(() -> receiveResponse(xhr));
//      //xhr.open("GET", url.getValue());
//      xhr.setRequestHeader("Accept-Language", "en,en-US;q=0.8");
//      xhr.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
//      xhr.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
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
