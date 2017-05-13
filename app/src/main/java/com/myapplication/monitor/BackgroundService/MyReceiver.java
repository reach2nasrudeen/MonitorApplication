package com.myapplication.monitor.BackgroundService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving
        if ((intent.getAction().equals(Intent.ACTION_USER_PRESENT)) || (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))) {
//            if(arrayList.size()!=0){
//                Toast.makeText(context,"Test service",Toast.LENGTH_SHORT).show();
            context.startService(new Intent(context, MyService.class));
//            }
        }
    }
}
