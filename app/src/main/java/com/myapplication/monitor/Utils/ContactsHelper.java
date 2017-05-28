package com.myapplication.monitor.Utils;

import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Created by Mohamed on 05/28/2017.
 */

public class ContactsHelper {

    Cursor cursor ;
    String name, phonenumber ;
    public void GetContactsIntoArrayList(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + " "  + ":" + " " + phonenumber);
        }

        cursor.close();

    }
}
