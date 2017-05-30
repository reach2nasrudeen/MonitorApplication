package com.myapplication.monitor.ViewModels;

import com.myapplication.monitor.DataManager.CallsDataManager;
import com.myapplication.monitor.DataManager.ContactsDataManager;
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
    public UpdateViewModel(UpdateViewDelegate viewDelegate) {
        this.viewDelegate = viewDelegate;
        contactsDataManager = new ContactsDataManager();
        callsDataManager = new CallsDataManager();
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
}
