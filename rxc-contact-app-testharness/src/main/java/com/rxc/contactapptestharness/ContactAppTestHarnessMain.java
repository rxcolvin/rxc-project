package com.rxc.contactapptestharness;

import com.rxc.contact_app.ContactApplication;
import com.rxc.contactdata.Contact;
import com.rxc.contactdata.UserContext;
import com.rxc.dao.QueryData;
import com.rxc.dao.RxDao;
import com.rxc.daocache.DaoCache;
import com.rxc.lang.Array;
import com.rxc.lang.T2;
import com.rxc.lang.Timing;
import com.rxc.lang.TimingImpl;
import com.rxc.meta.CommonDataDictionary;
import com.rxc.ui.UIContainer;
import com.rxc.uimock.MockUIContainer;
import com.rxc.uimock.MockUIEditField;
import rx.Observable;

import java.util.Collection;
import java.util.UUID;


import static com.rxc.lang.Array.$$;
import static com.rxc.lang.Minutes.Minutes;

/**
 * Created by richard.colvin on 01/12/2015.
 */
public class ContactAppTestHarnessMain {

  public static void main(String[] args) {

    final Timing timing = new TimingImpl();
    final RxDao<UUID, Contact, UserContext> dao = new DaoCache<>(
        Minutes(60),
        null,
        (c) -> UUID.randomUUID(),
        $$(new AllContactsQH()),
        timing

    );
    final CommonDataDictionary dd = new CommonDataDictionary();
    final UIContainer ui = new MockUIContainer(Array.$$("firstName", "lastName"));

    ContactApplication contactApplication = new ContactApplication(dao,
        dd, ui);

    final MockUIEditField fn = (MockUIEditField) ui.editField("firstName");

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
