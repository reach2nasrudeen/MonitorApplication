package com.myapplication.monitor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.myapplication.monitor.DataManager.ContactsDataManager;
import com.myapplication.monitor.DataManager.callbacks.DaoResponse;
import com.myapplication.monitor.DataManager.dao.ContactsDao;
import com.myapplication.monitor.Model.CallLogs;
import com.myapplication.monitor.Model.Contact;
import com.myapplication.monitor.Model.Realm.ContactsRealm;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.CallLogsHelper;
import com.myapplication.monitor.Utils.ContactsHelper;

import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    ContactsHelper contactsHelper;
    CallLogsHelper callLogsHelper;
    ContactsDataManager dataManager;
    ContactsDao contactsDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        contactsHelper = new ContactsHelper(this);
        callLogsHelper = new CallLogsHelper(this);
        dataManager = new ContactsDataManager();
        contactsDao = new ContactsDao();
        textView = (TextView) findViewById(R.id.textStatus);

        storeContacts();
    }

    private void storeContacts() {
        contactsDao.storeOrUpdateCallsList(dataManager.getContactsRealmList(contactsHelper.getContactsList()),new DaoResponse<String>(){

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

    private void showContacts() {
        List<Contact> contactsList = dataManager.getContactsList(contactsDao.getContactsList());
        textView.setText("\n\n Contacts List");
        for(Contact contact : contactsList) {
            textView.append("\n\n Name : "+contact.getName()+"\n Phone : "+contact.getPhone());
        }

    }

    private void showCalls() {
        textView.append("\n\n\n Call Logs");
        List<CallLogs> callLogsList = callLogsHelper.getCallLogs();
        for(CallLogs callLogs : callLogsList) {
            textView.append("\n\nPhone : "+callLogs.getPhone()
                    +"\nType : "+callLogs.getType()
                    +"\nDuration : "+callLogs.getDuration()
                    +"\nDate : "+callLogs.getDate());
        }
    }
}
