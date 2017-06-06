package com.myapplication.monitor.DataManager;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Model.BrowserHistory;
import com.myapplication.monitor.Model.Realm.BrowserHistoryRealm;
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
 * Created by Nasrudeen on 06/06/17.
 */

public class HistoryDataManager implements AppConstants {
    private final String TAG = HistoryDataManager.class.getSimpleName();
    private final MonitorApiInterface service;

    public HistoryDataManager() {
        service = getApp().getRetrofitInterface();
    }

    public void doHistoryUpdate(BrowserHistory history, final DataResponse<String> dataResponse) {

        Call<ResponseBody> timeLogResponseCall = service.updateHistories(history.getUserId(),
                history.getTitle(),
                history.getUrl());

        timeLogResponseCall.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();

                if (statusCode == 200) {
                    Log.e("Status", "Register Success");
                    dataResponse.onSuccess("Register Success");
                } else {
                    dataResponse.onFailure(ERROR_STATUS, String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
                dataResponse.onFailure("Something went wrong while trying login!");
            }
        });
    }

    public List<BrowserHistoryRealm> getHistoryRealmList(List<BrowserHistory> historyList) {
        List<BrowserHistoryRealm> historyRealmList = new ArrayList<>();
        for (int i = 0; i < historyList.size(); i++) {
            BrowserHistoryRealm historyRealm = new BrowserHistoryRealm();
            historyRealm.setId(i);
            historyRealm.setUserId(historyList.get(i).getUserId());
            historyRealm.setTitle(historyList.get(i).getTitle());
            historyRealm.setUrl(historyList.get(i).getUrl());
            historyRealmList.add(historyRealm);
        }
        return historyRealmList;
    }

    public List<BrowserHistory> getHistoryList(List<BrowserHistoryRealm> historyRealmList) {
        List<BrowserHistory> historyList = new ArrayList<>();
        for (BrowserHistoryRealm historyRealm : historyRealmList) {
            BrowserHistory history = new BrowserHistory();
            history.setUserId(historyRealm.getUserId());
            history.setTitle(historyRealm.getTitle());
            history.setUrl(historyRealm.getUrl());
            historyList.add(history);
        }
        return historyList;
    }
}
