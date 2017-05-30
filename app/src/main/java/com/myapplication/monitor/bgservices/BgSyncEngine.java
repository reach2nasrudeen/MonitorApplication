package com.myapplication.monitor.bgservices;

import android.content.Context;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.os.SystemClock;
import android.support.annotation.WorkerThread;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class BgSyncEngine {


    private final Context mContext;

    public BgSyncEngine(Context context) {
        mContext = context;
    }

    @WorkerThread
    public boolean sync() {
        // do something fancy

        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new NetworkOnMainThreadException();
        }

        SystemClock.sleep(1_000);
        boolean success = Math.random() > 0.1; // successful 90% of the time
        //saveSuccess(success);
        return success;
    }
}
