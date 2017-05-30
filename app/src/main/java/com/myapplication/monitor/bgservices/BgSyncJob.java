package com.myapplication.monitor.bgservices;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.util.support.PersistableBundleCompat;
import com.myapplication.monitor.Utils.SessionManager;

import static com.myapplication.monitor.Base.MonitorApp.getApp;


public class BgSyncJob extends Job {

    public static final String TAG = "NURVV_SYNC";

    Context mContext = getApp().getBaseContext();

    SessionManager sessionManager;

    private String sessionID;
    private String option;
    PersistableBundleCompat extras;
    public Handler sessionHandler;
    private int sessionPosition = 0;

    @Override
    @NonNull
    protected Result onRunJob(final Params params) {

        boolean success = new BgSyncEngine(getContext()).sync();
        extras = params.getExtras();
        option = extras.getString("option", "");

        sessionManager = new SessionManager(mContext);

        if (option.equals("calls")) {
            syncCalls();

        } else if (option.equals("contacts")) {
            syncContacts();

        }

        return success ? Result.SUCCESS : Result.FAILURE;
    }

    private void syncContacts() {
    }

    private void syncCalls() {
    }

}
