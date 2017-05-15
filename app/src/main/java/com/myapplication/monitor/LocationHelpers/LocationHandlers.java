package com.myapplication.monitor.LocationHelpers;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.LatLng;
import com.myapplication.monitor.Activities.MainActivity;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
//                        if(arrayList.size()!=0){
                        LocationUpdates.startLocationUpdates();
//                        }if(arrayList.size() == 0){
//                            LocationUpdates.stopLocationUpdates();
//                        }
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

                    /*Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(mContext, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

//                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String country = addresses.get(0).getCountryName();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL


//                    showNotification(String.format("You reached %f %s %s %s %s",latitude,address,city,state,knownName));

                    Integer resultDistance;
                    LatLng targetLocation;
                    Double mLat = Double.valueOf(sessionManager.getPlacelat());
                    Double mLong = Double.valueOf(sessionManager.getPlaceLong());
                    int alertKM = Integer.parseInt(sessionManager.getPlaceRadius());

                    targetLocation = new LatLng(mLat, mLong);
                    resultDistance = Integer.parseInt(
                            LocationUpdates.roundOffKM(
                                    String.valueOf(
                                            LocationUpdates.distanceBetween(currentLocation, targetLocation) / 1000)));
                    Log.e("Alert KM Distance", String.valueOf(alertKM));
                    Log.e("Distance", String.valueOf(resultDistance));
                    if (alertKM >= resultDistance) {
                        showNotification("You reached "+String.valueOf(alertKM)+String.valueOf(resultDistance));
                    }
                    break;
                case 1:
                default:
                    break;
            }
        }
    };

    private static void showNotification(String message) {
        Intent i = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSound(defaultSoundUri)
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), builder.build());

    }
}
