package com.myapplication.monitor.Base;

import android.app.Application;
import android.content.Context;

import com.myapplication.monitor.Rest.MonitorApiClient;
import com.myapplication.monitor.Rest.MonitorApiInterface;
import com.myapplication.monitor.Utils.SessionManager;
/**
 * Created by Mohamed on 05/14/2017.
 */

public class MonitorApp extends Application {
    public static Context mContext;

    protected static MonitorApp mInstance;

    private SessionManager mSharedPreferences;
    private MonitorApiClient mMonitorApiClient;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        mMonitorApiClient = new MonitorApiClient();
        mSharedPreferences = new SessionManager(this);
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

    public SessionManager getUserPreference() {
        return mSharedPreferences;
    }

    public MonitorApiInterface getRetrofitInterface() {
        return mMonitorApiClient.getClientInterface();
    }
}
