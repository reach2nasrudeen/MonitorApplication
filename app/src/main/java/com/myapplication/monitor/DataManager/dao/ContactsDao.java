package com.myapplication.monitor.DataManager.dao;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DaoResponse;
import com.myapplication.monitor.Model.Realm.ContactsRealm;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Mohamed on 05/29/2017.
 */

public class ContactsDao {
    private final String TAG = CallDao.class.getSimpleName();
    public void storeOrUpdateCallsList(final List<ContactsRealm> contactsRealmList, final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // As primary key is involved, use copy to realm or update as it will create or
                //  update based on object availability.
                realm.copyToRealmOrUpdate(contactsRealmList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("Contacts list saved successfully!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                callback.onFailure("Contacts list save failed!");
                realm.close();
            }
        });
    }

    public void storeOrUpdateCalls(final ContactsRealm contactsRealm, final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // As primary key is involved, use copy to realm or update as it will create or
                //  update based on object availability.
                realm.copyToRealmOrUpdate(contactsRealm);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("Contacts saved successfully!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                callback.onFailure("Contacts save failed!");
                realm.close();
            }
        });
    }

    public List<ContactsRealm> getContactsList() {
        Realm realm = Realm.getDefaultInstance();
        List<ContactsRealm> callsRealmList = realm.copyFromRealm(realm.where(ContactsRealm.class).findAll());
        realm.close();

        return callsRealmList;
    }
}
