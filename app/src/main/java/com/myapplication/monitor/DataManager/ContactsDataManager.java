package com.myapplication.monitor.DataManager;

import com.myapplication.monitor.Rest.MonitorApiInterface;
import com.myapplication.monitor.Utils.AppConstants;

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
}
