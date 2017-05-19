package com.myapplication.monitor.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
    private TextView textPlace;
    Toolbar toolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = getApp().getUserPreference();
        initToolbar();
//        sessionManager.setPlaceRadius("1500");
        textPlace = (TextView) findViewById(R.id.textPlace);
        textStatus = (TextView) findViewById(R.id.textStatus);

        textPlace.setText("Place Name : "+ sessionManager.getPlaceName());
        textStatus.append("\nAddress : "+sessionManager.getPlaceAddress());
        textStatus.append("\n\nPhone : "+sessionManager.getPlacePhone());
        textStatus.append("\n\nLatitude : "+sessionManager.getPlacelat());
        textStatus.append("\n\nLongitude : "+sessionManager.getPlaceLong());
        textStatus.append("\n\nRadius : "+sessionManager.getPlaceRadius());

        Button buttonStop = (Button) findViewById(R.id.btnStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servStop();
            }
        });

        servStart();
    }
    public void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void servStop(){
        stopService(new Intent(getApplicationContext(), MyService.class));
    }
    private void servStart(){
        startService(new Intent(getApplicationContext(), MyService.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            goHome();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        goHome();
    }
    private void goHome(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
    }
}
