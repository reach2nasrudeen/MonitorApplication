package com.myapplication.monitor.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.myapplication.monitor.Model.CallLogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mohamed on 05/28/2017.
 */

public class CallLogsHelper {
    private Context mContext;
    private SessionManager sessionManager;
    public CallLogsHelper(Context mContext) {
        this.mContext = mContext;
        sessionManager = new SessionManager(mContext);
    }
    public List<CallLogs> getCallLogs() {
        List<CallLogs> callLogsList = new ArrayList<>();
        Cursor managedCursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        while (managedCursor.moveToNext()) {
            String phName = managedCursor.getString(name);
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {

                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            CallLogs logs = new CallLogs();
            if(phName == null) {
                logs.setName("UNKNOWN");
            } else {
                logs.setName(phName);
            }
            logs.setPhone(phNumber);
            logs.setType(dir);
            logs.setDate(String.valueOf(callDayTime));
            logs.setDuration(callDuration);
            logs.setUserId(sessionManager.getUserId());
            callLogsList.add(logs);
        }
        managedCursor.close();
        return callLogsList;
    }
}
