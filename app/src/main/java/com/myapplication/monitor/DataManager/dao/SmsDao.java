package com.myapplication.monitor.DataManager.dao;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DaoResponse;
import com.myapplication.monitor.Model.Realm.BrowserHistoryRealm;
import com.myapplication.monitor.Model.Realm.SmsRealm;
import com.myapplication.monitor.Model.Sms;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nasrudeen on 07/06/17.
 */

public class SmsDao {
    private final String TAG = BrowserHistoryDao.class.getSimpleName();
    public void storeOrUpdateSmsList(final List<SmsRealm> smsRealmList, final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // As primary key is involved, use copy to realm or update as it will create or
                //  update based on object availability.
                realm.copyToRealmOrUpdate(smsRealmList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("Sms list saved successfully!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                callback.onFailure("Sms list save failed!");
                realm.close();
            }
        });
    }

    public void storeOrUpdateSms(final SmsRealm smsRealm, final DaoResponse callback) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // As primary key is involved, use copy to realm or update as it will create or
                //  update based on object availability.
                realm.copyToRealmOrUpdate(smsRealm);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess("Sms saved successfully!");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                callback.onFailure("Sms save failed!");
                realm.close();
            }
        });
    }

    public List<SmsRealm> getSmsList() {
        Realm realm = Realm.getDefaultInstance();
        List<SmsRealm> callsRealmList = realm.copyFromRealm(realm.where(SmsRealm.class).findAll());
        realm.close();

        return callsRealmList;
    }

    public void deleteSms() {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SmsRealm> result = realm.where(SmsRealm.class).findAll();
                result.deleteAllFromRealm();
            }
        });
    }
}
