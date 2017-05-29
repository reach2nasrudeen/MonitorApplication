package com.myapplication.monitor.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.myapplication.monitor.Base.BaseActivity;
import com.myapplication.monitor.LocationHelpers.LocationHandlers;
import com.myapplication.monitor.LocationHelpers.LocationUpdates;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;
import com.myapplication.monitor.Utils.Utils;

public class SplashActivity extends BaseActivity {
    private SessionManager sessionManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        LocationHandlers locationHandler = new LocationHandlers(getApplicationContext());
        LocationUpdates.buildGoogleApiClient(getApp().getApplicationContext());
        sessionManager = getApp().getUserPreference();
        registerFCM();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.getUserLoginStatus()) {
                    initActivity(new Intent(mContext, HomeActivity.class));
                } else {
                    initActivity(new Intent(mContext, RegisterActivity.class));
                }
            }
        }, 3 * 1000); // wait for 3 seconds*/
    }

    public void registerFCM(){
        initActivity(new Intent(mContext, Main2Activity.class));
        /*boolean InternetStatus = Utils.isInternetConnected(this);
        if(InternetStatus){
            FirebaseInstanceId.getInstance().getToken();
            new Handler().postDelayed(new Runnable() {
                // Using handler with postDelayed called runnable run method
                @Override
                public void run() {
//                    initActivity(new Intent(mContext, MapsActivity.class));
                    if (sessionManager.getUserLoginStatus()) {
                        initActivity(new Intent(mContext, MapsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
                        initActivity(new Intent(mContext, RegisterStep1.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }
            }, 2*1000); // wait for 2 seconds
        }
        else{
            new Handler().postDelayed(new Runnable() {
                // Using handler with postDelayed called runnable run method
                @Override
                public void run() {
//                    initActivity(new Intent(mContext, MapsActivity.class));
                    if (sessionManager.getUserLoginStatus()) {
                        initActivity(new Intent(mContext, MapsActivity.class));
                    } else {
                        initActivity(new Intent(mContext, RegisterStep1.class));
                    }
                }
            }, 2*1000); // wait for 2 seconds
        }*/
    }

    private void initActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }
}
