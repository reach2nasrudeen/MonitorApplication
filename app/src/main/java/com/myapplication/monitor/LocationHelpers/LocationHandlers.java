package com.myapplication.monitor.LocationHelpers;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.maps.model.LatLng;
import com.myapplication.monitor.Activities.MapsActivity;
import com.myapplication.monitor.Activities.RegisterActivity;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;

import static com.myapplication.monitor.Base.MonitorApp.getApp;

public class LocationHandlers {
    public static Context mContext;
    public static SessionManager sessionManager;

    public LocationHandlers(Context context) {
        this.mContext = context;
        sessionManager = new SessionManager(mContext);
    }

    public static Handler updateLocListener = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (LocationUpdates.connectGoogleApi()) {
                        if (sessionManager.getUserLoginStatus()) {
                            LocationUpdates.startLocationUpdates();
                        }
                    }
                    break;
                case 1:
                default:
                    break;
            }
        }
    };
    public static Handler distanceCalculator = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    LatLng currentLocation = new LatLng(LocationUpdates.mLatitude, LocationUpdates.mLongitude);
                    double latitude = LocationUpdates.mLatitude;
                    double longitude = LocationUpdates.mLongitude;
                    sessionManager.setUserLat(String.valueOf(latitude));
                    sessionManager.setUserLong(String.valueOf(longitude));

                    double resultDistance;
                    LatLng targetLocation;
                    Double mLat = Double.valueOf(sessionManager.getPlacelat());
                    Double mLong = Double.valueOf(sessionManager.getPlaceLong());
                    double alertKM = Double.parseDouble(sessionManager.getPlaceRadius());

                    targetLocation = new LatLng(mLat, mLong);
                    resultDistance = LocationUpdates.distanceBetween(currentLocation, targetLocation) / 1000;
                    double alertKMs = alertKM / 1000;
                    if (alertKMs >= resultDistance) {
                        showNotification("You reached " + String.valueOf(alertKM) + String.valueOf(resultDistance));
                    }
                    break;
                case 1:
                default:
                    break;
            }
        }
    };

    private static void showNotification(String message) {
        Intent intent;
        SessionManager sessionManager = getApp().getUserPreference();
        if(sessionManager.getUserLoginStatus()) {
            intent = new Intent(getApp().getApplicationContext(), MapsActivity.class);
        } else {
            intent = new Intent(getApp().getApplicationContext(), RegisterActivity.class);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
        NotificationManager manager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), builder.build());

    }
}
