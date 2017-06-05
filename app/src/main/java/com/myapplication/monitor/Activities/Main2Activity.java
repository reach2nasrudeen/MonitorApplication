package com.myapplication.monitor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myapplication.monitor.DataManager.CallsDataManager;
import com.myapplication.monitor.DataManager.ContactsDataManager;
import com.myapplication.monitor.DataManager.callbacks.DaoResponse;
import com.myapplication.monitor.DataManager.dao.CallDao;
import com.myapplication.monitor.DataManager.dao.ContactsDao;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.UpdateViewDelegate;
import com.myapplication.monitor.Model.CallLogs;
import com.myapplication.monitor.Model.Contact;
import com.myapplication.monitor.Model.Realm.ContactsRealm;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.BrowserHelper;
import com.myapplication.monitor.Utils.CallLogsHelper;
import com.myapplication.monitor.Utils.ContactsHelper;
import com.myapplication.monitor.ViewModels.UpdateViewModel;

import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements UpdateViewDelegate{
    TextView textView;
    ContactsHelper contactsHelper;
    CallLogsHelper callLogsHelper;
    ContactsDataManager contactsDataManager;
    CallsDataManager callsDataManager;
    ContactsDao contactsDao;
    CallDao callDao;
    UpdateViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewModel = new UpdateViewModel(this);
        contactsHelper = new ContactsHelper(this);
        callLogsHelper = new CallLogsHelper(this);
        contactsDataManager = new ContactsDataManager();
        callsDataManager = new CallsDataManager();
        contactsDao = new ContactsDao();
        callDao = new CallDao();
        textView = (TextView) findViewById(R.id.textStatus);

//        storeCalls();
        showHistory();
    }

    private void showHistory() {
        BrowserHelper browserHelper = new BrowserHelper(this);
        textView.append("\n\n\n History Logs");
        List<String[]> historyList = browserHelper.getBrowserHist();
        if(historyList != null) {
            for(String[] history : historyList) {
                textView.append("\n\nTitle : "+history[0]
                        +"\nURL : "+history[1]);
            }
        }
    }

    private void storeContacts() {
        contactsDao.deleteContacts();
        contactsDao.storeOrUpdateContactsList(contactsDataManager.getContactsRealmList(contactsHelper.getContactsList()),new DaoResponse<String>(){

            @Override
            public void onSuccess(String message) {
                showContacts();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }
        });
    }

    private void storeCalls() {
        callDao.deleteCalls();
        callDao.storeOrUpdateCallsList(callsDataManager.getCallsRealmList(callLogsHelper.getCallLogs()),new DaoResponse<String>(){

            @Override
            public void onSuccess(String message) {
                showCalls();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }
        });
    }

    private void showContacts() {
        List<Contact> contactsList = contactsDataManager.getContactsList(contactsDao.getContactsList());
        textView.setText("\n\n Contacts List");
        for(Contact contact : contactsList) {
            textView.append("\n\n Name : "+contact.getName()+"\n Phone : "+contact.getPhone());
        }
        if(contactsList.size() != 0) {
            viewModel.setContactListLength(contactsList.size());
            viewModel.setContactList(contactsList);
            viewModel.setContact(contactsList.get(0));
            viewModel.setContactPosition(0);
            viewModel.onContactUpdate();
        }
        Log.e("Contact List",new Gson().toJson(contactsList));

    }

    private void showCalls() {
        textView.append("\n\n\n Call Logs");
        List<CallLogs> callLogsList = callsDataManager.getCallsList(callDao.getCallList());
        for(CallLogs callLogs : callLogsList) {
            textView.append("\n\nPhone : "+callLogs.getPhone()
                    +"\nType : "+callLogs.getType()
                    +"\nDuration : "+callLogs.getDuration()
                    +"\nDate : "+callLogs.getDate());
        }

        if(callLogsList.size() != 0) {
            viewModel.setCallListLength(callLogsList.size());
            viewModel.setCallLogsList(callLogsList);
            viewModel.setCallLogs(callLogsList.get(0));
            viewModel.setCallPosition(0);
            viewModel.onCallUpdate();
        } else {
            storeContacts();
        }
    }

    @Override
    public void onContactUpdated() {
        int position = viewModel.getContactPosition() + 1;
        if(position != viewModel.getContactListLength()) {
            viewModel.setContactPosition(position);
            viewModel.setContact(viewModel.getContactList().get(position));
            viewModel.onContactUpdate();
        }
    }

    @Override
    public void onCallUpdated() {
        int position = viewModel.getCallPosition() + 1;
        if(position != viewModel.getCallListLength()) {
            viewModel.setCallPosition(position);
            viewModel.setCallLogs(viewModel.getCallLogsList().get(position));
            viewModel.onCallUpdate();
        }
    }
}
