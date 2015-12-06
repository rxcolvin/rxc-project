package com.rxc.contactapptestharness;

import com.rxc.contact_app.ContactApplication;
import com.rxc.contactdata.Contact;
import com.rxc.contactdata.UserContext;
import com.rxc.dao.QueryData;
import com.rxc.dao.RxDao;
import com.rxc.daocache.DaoCache;
import com.rxc.lang.T2;
import com.rxc.lang.Timing;
import com.rxc.lang.TimingImpl;
import com.rxc.meta.CommonDataDictionary;
import com.rxc.ui.UIContainer;
import com.rxc.uimock.MockUIAppContainer;
import com.rxc.uimock.MockUIAction;
import com.rxc.uimock.MockUIContainer;
import com.rxc.uimock.MockUIEditField;
import rx.Observable;

import java.util.Collection;
import java.util.UUID;


import static com.rxc.contact_app.ContactApplication.$save;
import static com.rxc.lang.$$.$$;
import static com.rxc.lang.$$.$0;
import static com.rxc.lang.Minutes.$;
import static com.rxc.meta.CommonDataDictionary.*;

/**
 * Created by richard.colvin on 01/12/2015.
 */
public class ContactAppTestHarnessMain {

  public static void main(String[] args) {

    final Timing timing = new TimingImpl();
    final RxDao<UUID, Contact, UserContext> dao = new DaoCache<>(
        $(60),
        null,
        (c) -> UUID.randomUUID(),
        $$(new AllContactsQH()),
        timing

    );
    final CommonDataDictionary dd = new CommonDataDictionary();

    final MockUIContainer contactContainer = MockUIContainer.$(Contact.$myName, $$(MockUIEditField.$($firstName), MockUIEditField.$($lastName)), $0(), $0());
    final UIContainer        rootContainer = MockUIContainer.$("root", $0(), $$(contactContainer), $$(MockUIAction.$
        ($save)));
    final MockUIAppContainer appContainer  = MockUIAppContainer.$(rootContainer);

    ContactApplication contactApplication = new ContactApplication(dao,
        dd, appContainer);


    contactApplication.start();

    final MockUIEditField fn = contactContainer.mockEditField($firstName);

    fn.mockUpdate("F");
    fn.mockUpdate("Fr");


  }


  static class AllContactsQH implements DaoCache.QueryHandler<UUID, Contact> {
    @Override
    public Class<? extends QueryData<Contact>> queryType() {
      return null;
    }

    @Override
    public Observable<Collection<T2<UUID, Contact>>> query(final QueryData<Contact> queryData) {
      return null;
    }
  }

  static class AllContacts extends QueryData<Contact> {

    AllContacts() {
      super(Contact.class);
    }
  }


}
