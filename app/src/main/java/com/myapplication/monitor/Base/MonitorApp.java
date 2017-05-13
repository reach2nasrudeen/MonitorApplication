package com.myapplication.monitor.Base;

import android.app.Application;
import android.content.Context;

import com.myapplication.monitor.Rest.MonitorApiClient;
import com.myapplication.monitor.Rest.MonitorApiInterface;

/**
 * Created by Mohamed on 05/14/2017.
 */

public class MonitorApp extends Application {
    public static Context mContext;

    protected static MonitorApp mInstance;

    private MonitorApiClient mMonitorApiClient;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        mMonitorApiClient = new MonitorApiClient();
    }

    public static MonitorApp getApp() {
        if (mInstance != null && mInstance instanceof MonitorApp){
            return mInstance;
        }else {
            mInstance = new MonitorApp();
            mInstance.onCreate();
            return mInstance;
        }
    }

    public static synchronized Context getContext(){
        return mContext;
    }

    public MonitorApiInterface getRetrofitInterface() {
        return mMonitorApiClient.getClientInterface();
    }
}
