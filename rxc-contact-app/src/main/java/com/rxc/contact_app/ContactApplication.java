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

import java.util.UUID;

import static com.rxc.lang.collection.$$.$$;
import static com.rxc.meta.FieldMeta.FieldMeta;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class ContactApplication implements LifeCycle {

    public final static String $save = "Save";

    private final RxDao<UUID, Contact, UserContext> dao;

    private final EntityController<Contact, Contact.Builder> contactEntityController;
    private final ControllerModule controllerModule;
    private final UIAction saveAction;
    private final UIAppContainer appContainer;

    public ContactApplication(
            final RxDao<UUID, Contact, UserContext> dao,
            final CommonDataDictionary dataDictionary,
            final UIAppContainer appContainer) {
        this.appContainer = appContainer;

        //TODO: Should be in contact-meta-data module?
        EntityMeta<Contact, Contact.Builder> contactMeta = new EntityMeta<>(
                Contact.$name,
                Contact.class,
                $$(
                        FieldMeta(dataDictionary.firstName, c -> c.firstName, (s, b) -> b.firstName = s),
                        FieldMeta(dataDictionary.lastName,  c -> c.lastName, (s, b) -> b.lastName = s)
                ),
                Contact.Builder::new
        );
        this.dao = dao;
        controllerModule = new ControllerModule();

        contactEntityController = controllerModule.entityControllerFactory.create(contactMeta, appContainer.rootContainer()
                .container(
                        (Contact.$name)));

        saveAction = appContainer.rootContainer().action($save);
    }

    @Override
    public void start() {
        saveAction.listener(this::saveContract);

        appContainer.closeListener(() -> true);

    }

    @Override
    public void stop() {
        saveAction.listener(null);
    }

    /**
     * @return
     */
    @Override
    public boolean isSafeToStop() {
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


