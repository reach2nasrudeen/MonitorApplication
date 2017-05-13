package com.myapplication.monitor.DataManager;

import android.util.Log;

import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Rest.MonitorApiInterface;
import com.myapplication.monitor.Rest.RetrofitCallback;
import com.myapplication.monitor.Utils.AppConstants;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.myapplication.monitor.Base.MonitorApp.getApp;

/**
 * Created by Mohamed on 05/14/2017.
 */

public class RegisterDataManager implements AppConstants {

    private final String TAG = RegisterDataManager.class.getSimpleName();
    private final MonitorApiInterface service;

    public RegisterDataManager() {

        service = getApp().getRetrofitInterface();
    }

    public void doRegister(String name,
            String phone,
            String deviceId,
            String deviceBrand,
            String deviceModel, final DataResponse<String> dataResponse) {

        Call<ResponseBody> timeLogResponseCall = service.registerUser(name,
                phone,
                deviceId,
                deviceBrand,
                deviceModel);

        timeLogResponseCall.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();

                if (statusCode == 200) {
                    dataResponse.onSuccess("Register success");
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
}
