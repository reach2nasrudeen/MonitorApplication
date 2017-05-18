package com.myapplication.monitor.Firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
//        String username = "Nasrudeen";
        Log.i("Registered Token ----> ",token);
//        Log.i("Reg Username  --->",username);
        //registerToken(token);
    }
}
