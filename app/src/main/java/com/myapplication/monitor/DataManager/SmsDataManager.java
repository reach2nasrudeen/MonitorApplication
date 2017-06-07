package com.myapplication.monitor.DataManager;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Model.Realm.SmsRealm;
import com.myapplication.monitor.Model.Sms;
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
 * Created by Nasrudeen on 07/06/17.
 */

public class SmsDataManager {

    private final String TAG = SmsDataManager.class.getSimpleName();
    private final MonitorApiInterface service;

    public SmsDataManager() {
        service = getApp().getRetrofitInterface();
    }

    public void doSmsUpdate(Sms sms, final DataResponse<String> dataResponse) {

        Call<ResponseBody> timeLogResponseCall = service.updateSms(sms.getUserId(),
                sms.getAddress(),
                sms.getMsg(),
                sms.getFolderName(),
                sms.getTime());

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

    public List<SmsRealm> getSmsRealmList(List<Sms> smsList) {
        List<SmsRealm> smsRealmList = new ArrayList<>();
        for (int i = 0; i < smsList.size(); i++) {
            SmsRealm smsRealm = new SmsRealm();
            smsRealm.setId(i);
            smsRealm.setUserId(smsList.get(i).getUserId());
            smsRealm.setAddress(smsList.get(i).getAddress());
            smsRealm.setMessage(smsList.get(i).getMsg());
            smsRealm.setFolder(smsList.get(i).getFolderName());
            smsRealm.setSmsdate(smsList.get(i).getTime());
            smsRealmList.add(smsRealm);
        }
        return smsRealmList;
    }

    public List<Sms> getSmsList(List<SmsRealm> smsRealmList) {
        List<Sms> smsList = new ArrayList<>();
        for (SmsRealm smsRealm : smsRealmList) {
            Sms sms = new Sms();
            sms.setUserId(smsRealm.getUserId());
            sms.setAddress(smsRealm.getAddress());
            sms.setFolderName(smsRealm.getFolder());
            sms.setMsg(smsRealm.getMessage());
            sms.setTime(smsRealm.getSmsdate());
            smsList.add(sms);
        }
        return smsList;
    }
}
