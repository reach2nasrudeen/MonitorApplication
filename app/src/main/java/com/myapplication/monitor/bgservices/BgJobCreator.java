package com.myapplication.monitor.bgservices;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;

public class BgJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case BgSyncJob.TAG:
                return new BgSyncJob();
            default:
                return null;
        }
    }

    public static final class AddReceiver extends AddJobCreatorReceiver {
        @Override
        protected void addJobCreator(@NonNull Context context, @NonNull JobManager manager) {
            // manager.addJobCreator(new BgJobCreator());
        }
    }
}
