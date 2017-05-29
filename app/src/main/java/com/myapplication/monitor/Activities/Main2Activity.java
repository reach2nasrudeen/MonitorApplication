package com.myapplication.monitor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.myapplication.monitor.Model.CallLogs;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.CallLogsHelper;
import com.myapplication.monitor.Utils.ContactsHelper;

import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    ContactsHelper contactsHelper;
    CallLogsHelper callLogsHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        contactsHelper = new ContactsHelper(this);
        callLogsHelper = new CallLogsHelper(this);
        textView = (TextView) findViewById(R.id.textStatus);
        textView.setText("\n\n Contacts List");
        for(Map.Entry<String, String> entry : contactsHelper.getContactsList().entrySet()){
            textView.append("\n\n Name : "+entry.getKey()+"\n Phone : "+entry.getValue());
        }
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
