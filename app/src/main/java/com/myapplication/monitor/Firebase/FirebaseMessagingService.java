package com.myapplication.monitor.Firebase;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.myapplication.monitor.Activities.MapsActivity;
import com.myapplication.monitor.Activities.RegisterActivity;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;

import java.util.List;

import static com.myapplication.monitor.Base.MonitorApp.getApp;
import static com.myapplication.monitor.Base.MonitorApp.mContext;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
    SessionManager sessionManager;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sessionManager = getApp().getUserPreference();
        Log.i("Notification Type --->",remoteMessage.getData().toString());
        sessionManager.setPlaceLat(remoteMessage.getData().get("latitude"));
        sessionManager.setPlaceLong(remoteMessage.getData().get("longitude"));
        sessionManager.setPlaceRadius(remoteMessage.getData().get("radius"));
        sessionManager.setPlacePhone(remoteMessage.getData().get("phone"));
        sessionManager.setPlaceAddress(remoteMessage.getData().get("address"));
        sessionManager.setPlaceName(remoteMessage.getData().get("name"));
        showNotification("Your monitoring location detils updated");
    }
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    private static void showNotification(String message) {
        Intent intent;
        SessionManager sessionManager = getApp().getUserPreference();
        if(sessionManager.getUserLoginStatus()) {
            intent = new Intent(getApp().getApplicationContext(), MapsActivity.class);
        } else {
            intent = new Intent(getApp().getApplicationContext(), RegisterActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(getApp().getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);

        if(sessionManager.getStoredNotificationSoundProperty()) {
            builder.setSound(defaultSoundUri);
        } if(sessionManager.getStoredNotificationVibrateProperty()) {
            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        }
        NotificationManager manager = (NotificationManager)getApp().getApplicationContext(). getSystemService(NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), builder.build());

    }
}
