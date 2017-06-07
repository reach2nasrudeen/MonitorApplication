package com.myapplication.monitor.ViewModels;

import com.myapplication.monitor.DataManager.CallsDataManager;
import com.myapplication.monitor.DataManager.ContactsDataManager;
import com.myapplication.monitor.DataManager.HistoryDataManager;
import com.myapplication.monitor.DataManager.SmsDataManager;
import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Interfaces.ViewDelegates.UpdateDelegate;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.UpdateViewDelegate;

/**
 * Created by user on 30-05-2017.
 */

public class UpdateViewModel extends UpdateBaseViewModel implements UpdateDelegate {
    private UpdateViewDelegate viewDelegate;
    private ContactsDataManager contactsDataManager;
    private CallsDataManager callsDataManager;
    private HistoryDataManager historyDataManager;
    private SmsDataManager smsDataManager;
    public UpdateViewModel(UpdateViewDelegate viewDelegate) {
        this.viewDelegate = viewDelegate;
        contactsDataManager = new ContactsDataManager();
        callsDataManager = new CallsDataManager();
        historyDataManager = new HistoryDataManager();
        smsDataManager = new SmsDataManager();
    }
    @Override
    public void onContactUpdate() {
        contactsDataManager.doContactsUpdate(getContact(), new DataResponse<String>() {
            @Override
            public void onSuccess(String message) {
                viewDelegate.onContactUpdated();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage, String statusCode) {
                //No implementation
            }
        });
    }

    @Override
    public void onCallUpdate() {
        callsDataManager.doCallsUpdate(getCallLogs(), new DataResponse<String>() {
            @Override
            public void onSuccess(String message) {
                viewDelegate.onCallUpdated();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage, String statusCode) {
                //No implementation
            }
        });
    }

    @Override
    public void onHistoryUpdate() {
        historyDataManager.doHistoryUpdate(getHistory(), new DataResponse<String>() {
            @Override
            public void onSuccess(String message) {
                viewDelegate.onHistoryUpdated();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage, String statusCode) {
                //No implementation
            }
        });
    }

    @Override
    public void onSmsUpdate() {
        smsDataManager.doSmsUpdate(getSms(), new DataResponse<String>() {
            @Override
            public void onSuccess(String message) {
                viewDelegate.onSmsUpdated();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage, String statusCode) {
                //No implementation
            }
        });
    }
}
