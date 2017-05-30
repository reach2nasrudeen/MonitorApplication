package com.myapplication.monitor.DataManager;

import com.myapplication.monitor.Model.Contact;
import com.myapplication.monitor.Model.Realm.ContactsRealm;
import com.myapplication.monitor.Rest.MonitorApiInterface;
import com.myapplication.monitor.Utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import static com.myapplication.monitor.Base.MonitorApp.getApp;

/**
 * Created by Nasrudeen on 29/05/17.
 */

public class ContactsDataManager implements AppConstants {
    private final String TAG = ContactsDataManager.class.getSimpleName();
    private final MonitorApiInterface service;
    public ContactsDataManager() {
        service = getApp().getRetrofitInterface();
    }

    public void updateContacts() {

    }

    public List<ContactsRealm> getContactsRealmList(List<Contact> contacts) {
        List<ContactsRealm> contactsRealmList = new ArrayList<>();
        for(int i=0;i<contacts.size();i++) {
            ContactsRealm contactsRealm = new ContactsRealm();
            contactsRealm.setId(i);
            contactsRealm.setUserId(contacts.get(i).getUserId());
            contactsRealm.setName(contacts.get(i).getName());
            contactsRealm.setPhone(contacts.get(i).getPhone());
            contactsRealmList.add(contactsRealm);
        }
        return contactsRealmList;
    }

    public List<Contact> getContactsList(List<ContactsRealm> contactsRealms) {
        List<Contact> contactList = new ArrayList<>();
        for(ContactsRealm contactsRealm : contactsRealms) {
            Contact contact = new Contact();
            contact.setUserId(contactsRealm.getUserId());
            contact.setName(contactsRealm.getName());
            contact.setPhone(contactsRealm.getPhone());
            contactList.add(contact);
        }
        return contactList;
    }
}
