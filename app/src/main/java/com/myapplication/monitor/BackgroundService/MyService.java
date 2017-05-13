package com.myapplication.monitor.BackgroundService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.myapplication.monitor.LocationHelpers.LocationHandlers;
import com.myapplication.monitor.LocationHelpers.LocationUpdates;


public class MyService extends Service {
    Handler handler;
    Runnable runnableThread;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        handler = new Handler();
        runnableThread = new Runnable() {
            @Override
            public void run() {
                Log.i("BG Service App --> ", "Service Running");
                LocationHandlers locationHandler = new LocationHandlers(getApplicationContext());
                LocationUpdates.buildGoogleApiClient(getApplicationContext());

                handler.postDelayed(runnableThread, 10000);
            }
        };
        handler.postDelayed(runnableThread, 500);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationUpdates.stopLocationUpdates();
        handler.removeCallbacks(runnableThread);
        Log.i("BG Service App --> ", "Service Destroyed");
    }
}
