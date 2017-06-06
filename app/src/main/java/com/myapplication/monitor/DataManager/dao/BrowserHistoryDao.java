package com.myapplication.monitor.DataManager.dao;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DaoResponse;
import com.myapplication.monitor.Model.Realm.BrowserHistoryRealm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nasrudeen on 06/06/17.
 */

public class BrowserHistoryDao {
    private final String TAG = BrowserHistoryDao.class.getSimpleName();
    public void storeOrUpdateHistoryList(final List<BrowserHistoryRealm> browserHistoryRealmList, final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // As primary key is involved, use copy to realm or update as it will create or
                //  update based on object availability.
                realm.copyToRealmOrUpdate(browserHistoryRealmList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("History list saved successfully!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                callback.onFailure("History list save failed!");
                realm.close();
            }
        });
    }

    public void storeOrUpdateHistory(final BrowserHistoryRealm contactsRealm, final DaoResponse callback) {
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
                callback.onSuccess("History saved successfully!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                callback.onFailure("History save failed!");
                realm.close();
            }
        });
    }

    public List<BrowserHistoryRealm> getHistoryList() {
        Realm realm = Realm.getDefaultInstance();
        List<BrowserHistoryRealm> callsRealmList = realm.copyFromRealm(realm.where(BrowserHistoryRealm.class).findAll());
        realm.close();

        return callsRealmList;
    }

    public void deleteHistories() {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<BrowserHistoryRealm> result = realm.where(BrowserHistoryRealm.class).findAll();
                result.deleteAllFromRealm();
            }
        });
    }
}
