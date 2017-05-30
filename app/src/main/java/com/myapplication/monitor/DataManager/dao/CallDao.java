package com.myapplication.monitor.DataManager.dao;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DaoResponse;
import com.myapplication.monitor.Model.Realm.CallsRealm;
import com.myapplication.monitor.Model.Realm.ContactsRealm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Mohamed on 05/29/2017.
 */

public class CallDao {
    private final String TAG = CallDao.class.getSimpleName();
    public void storeOrUpdateCallsList(final List<CallsRealm> callsRealmList, final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // As primary key is involved, use copy to realm or update as it will create or
                //  update based on object availability.
                realm.copyToRealmOrUpdate(callsRealmList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("Calls list saved successfully!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                callback.onFailure("Calls list save failed!");
                realm.close();
            }
        });
    }

    public void storeOrUpdateCalls(final CallsRealm callsRealm, final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // As primary key is involved, use copy to realm or update as it will create or
                //  update based on object availability.
                realm.copyToRealmOrUpdate(callsRealm);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("Calls saved successfully!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                callback.onFailure("Calls save failed!");
                realm.close();
            }
        });
    }

    public List<CallsRealm> getCallList() {
        Realm realm = Realm.getDefaultInstance();
        List<CallsRealm> callsRealmList = realm.copyFromRealm(realm.where(CallsRealm.class).findAll());
        realm.close();

        return callsRealmList;
    }

    public void deleteCalls() {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CallsRealm> result = realm.where(CallsRealm.class).findAll();
                result.deleteAllFromRealm();
            }
        });
    }
}
