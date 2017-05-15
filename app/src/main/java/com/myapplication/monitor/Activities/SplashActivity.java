package com.myapplication.monitor.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.myapplication.monitor.Base.BaseActivity;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;

public class SplashActivity extends BaseActivity {
    private SessionManager sessionManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        sessionManager = getApp().getUserPreference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.getUserLoginStatus()) {
                    initActivity(new Intent(mContext, HomeActivity.class));
                } else {
                    initActivity(new Intent(mContext, RegisterActivity.class));
                }
            }
        }, 3 * 1000); // wait for 3 seconds
    }

    private void initActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }
}
