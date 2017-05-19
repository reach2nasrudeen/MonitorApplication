package com.myapplication.monitor.Firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.myapplication.monitor.DataManager.RegisterDataManager;
import com.myapplication.monitor.DataManager.callbacks.DataResponse;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
//        String username = "Nasrudeen";
        Log.i("Registered Token ----> ",token);
        RegisterDataManager registerDataManager = new RegisterDataManager();
        registerDataManager.doUpdateToken("name", token, new DataResponse<String>() {
            @Override
            public void onSuccess(String message) {
                Log.e("Token",message);
            }

            @Override
            public void onSuccess(String item, String message) {

            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Token",errorMessage);
            }

            @Override
            public void onFailure(String errorMessage, String statusCode) {

            }
        });
//        Log.i("Reg Username  --->",username);
        //registerToken(token);
    }
}
