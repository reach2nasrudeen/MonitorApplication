package com.myapplication.monitor.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myapplication.monitor.BackgroundService.MyService;
import com.myapplication.monitor.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        servStart();
    }

    private void servStart(){
        startService(new Intent(getApplicationContext(), MyService.class));
    }
}
