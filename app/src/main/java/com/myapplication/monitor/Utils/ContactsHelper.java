package com.myapplication.monitor.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mohamed on 05/28/2017.
 */

public class ContactsHelper {
    private Context mContext;
    private HashMap<String,String> contactsList;
    public ContactsHelper(Context mContext) {
        this.mContext = mContext;
    }
    public HashMap<String,String> getContactsList(){
        contactsList = new HashMap<>();
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactsList.put(name, phonenumber);
        }
        cursor.close();
        return contactsList;
    }
}
