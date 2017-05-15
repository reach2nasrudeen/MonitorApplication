package com.myapplication.monitor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.myapplication.monitor.Base.BaseActivity;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;

public class HomeActivity extends BaseActivity {
    SessionManager sessionManager;
    private TextView textStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = getApp().getUserPreference();
        textStatus = (TextView) findViewById(R.id.textStatus);

        textStatus.setText(sessionManager.getPlaceName());
        textStatus.append("\n"+sessionManager.getPlaceAddress());
        textStatus.append("\n"+sessionManager.getPlacePhone());
        textStatus.append("\n"+sessionManager.getPlacelat());
        textStatus.append("\n"+sessionManager.getPlaceLong());
        textStatus.append("\n"+sessionManager.getPlaceRadius());
    }
}
