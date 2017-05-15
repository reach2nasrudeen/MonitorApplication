package com.myapplication.monitor.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapplication.monitor.BackgroundService.MyService;
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

        Button buttonStop = (Button) findViewById(R.id.btnStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servStop();
            }
        });

        servStart();
    }


    private void servStop(){
        stopService(new Intent(getApplicationContext(), MyService.class));
    }
    private void servStart(){
        startService(new Intent(getApplicationContext(), MyService.class));
    }
}
