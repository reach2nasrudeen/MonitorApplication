package com.myapplication.monitor.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.myapplication.monitor.Model.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mohamed on 05/28/2017.
 */

public class ContactsHelper {
    private Context mContext;
    private SessionManager sessionManager;
    public ContactsHelper(Context mContext) {
        this.mContext = mContext;
        sessionManager = new SessionManager(mContext);
    }
    public List<Contact> getContactsList(){
        List<Contact> contactList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact();
            contact.setUserId(sessionManager.getUserId());
            contact.setName(name);
            contact.setPhone(phonenumber);
            contactList.add(contact);
        }
        cursor.close();
        return contactList;
    }
}
