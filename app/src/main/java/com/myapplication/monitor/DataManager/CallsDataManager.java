package com.myapplication.monitor.DataManager;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Model.CallLogs;
import com.myapplication.monitor.Model.Realm.CallsRealm;
import com.myapplication.monitor.Rest.MonitorApiInterface;
import com.myapplication.monitor.Rest.RetrofitCallback;
import com.myapplication.monitor.Utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.myapplication.monitor.Base.MonitorApp.getApp;

/**
 * Created by user on 30-05-2017.
 */

public class CallsDataManager {
    private final String TAG = ContactsDataManager.class.getSimpleName();
    private final MonitorApiInterface service;

    public CallsDataManager() {
        service = getApp().getRetrofitInterface();
    }

    public void doCallsUpdate(CallLogs callLogs, final DataResponse<String> dataResponse) {

        Call<ResponseBody> timeLogResponseCall = service.updateCalls(callLogs.getUserId(),
                callLogs.getName(),
                callLogs.getPhone(),
                callLogs.getType(),
                callLogs.getDate(),
                callLogs.getDuration());

        timeLogResponseCall.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();

                if (statusCode == 200) {
                    Log.e("Status", "Register Success");
                    dataResponse.onSuccess("Register Success");
                } else {
                    dataResponse.onFailure(AppConstants.ERROR_STATUS, String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
                dataResponse.onFailure("Something went wrong while trying login!");
            }
        });
    }

    public List<CallsRealm> getCallsRealmList(List<CallLogs> callLogs) {
        List<CallsRealm> contactsRealmList = new ArrayList<>();
        for (int i = 0; i < callLogs.size(); i++) {
            CallsRealm callsRealm = new CallsRealm();
            callsRealm.setId(i);
            callsRealm.setUserId(callLogs.get(i).getUserId());
            if(callLogs.get(i).getType() != null) {
                callsRealm.setType(callLogs.get(i).getType());
            } else {
                callsRealm.setType("undefined");
            }
            callsRealm.setName(callLogs.get(i).getName());
            callsRealm.setType(callLogs.get(i).getType());
            callsRealm.setDate(callLogs.get(i).getDate());
            callsRealm.setDuration(callLogs.get(i).getDuration());
            callsRealm.setPhone(callLogs.get(i).getPhone());
            contactsRealmList.add(callsRealm);
        }
        return contactsRealmList;
    }

    public List<CallLogs> getCallsList(List<CallsRealm> callsRealms) {
        List<CallLogs> callLogsList = new ArrayList<>();
        for (CallsRealm callsRealm : callsRealms) {
            CallLogs callLogs = new CallLogs();
            callLogs.setUserId(callsRealm.getUserId());
            if(callsRealm.getType() != null) {
                callLogs.setType(callsRealm.getType());
            } else {
                callLogs.setType("undefined");
            }
            callLogs.setName(callsRealm.getName());
            callLogs.setDate(callsRealm.getDate());
            callLogs.setDuration(callsRealm.getDuration());
            callLogs.setPhone(callsRealm.getPhone());
            callLogsList.add(callLogs);
        }
        return callLogsList;
    }
}
