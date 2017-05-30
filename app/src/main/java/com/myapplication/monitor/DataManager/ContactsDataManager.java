package com.myapplication.monitor.DataManager;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Model.Contact;
import com.myapplication.monitor.Model.Place;
import com.myapplication.monitor.Model.Realm.ContactsRealm;
import com.myapplication.monitor.Model.User;
import com.myapplication.monitor.Rest.MonitorApiInterface;
import com.myapplication.monitor.Rest.RetrofitCallback;
import com.myapplication.monitor.Utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

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


    public void doContactsUpdate(Contact contact, final DataResponse<String> dataResponse) {

        Call<ResponseBody> timeLogResponseCall = service.updateContacts(contact.getUserId(),
                contact.getName(),
                contact.getPhone());

        timeLogResponseCall.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();

                if (statusCode == 200) {
                    Log.e("Status", "Register Success");
                    dataResponse.onSuccess("Register Success");
                } else {
                    dataResponse.onFailure(ERROR_STATUS, String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
                dataResponse.onFailure("Something went wrong while trying login!");
            }
        });
    }

    public List<ContactsRealm> getContactsRealmList(List<Contact> contacts) {
        List<ContactsRealm> contactsRealmList = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
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
        for (ContactsRealm contactsRealm : contactsRealms) {
            Contact contact = new Contact();
            contact.setUserId(contactsRealm.getUserId());
            contact.setName(contactsRealm.getName());
            contact.setPhone(contactsRealm.getPhone());
            contactList.add(contact);
        }
        return contactList;
    }
}
