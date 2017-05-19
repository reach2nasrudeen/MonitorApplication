package com.myapplication.monitor.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mohamed on 05/15/2017.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;

    private int PRIVATE_MODE = 0;

    private static final String PREFER_NAME = "monitorPreferences";
    private static final String KEY_USER_LOGIN = "USER_LOGGED_IN";
    private static final String KEY_USER_LAT = "USER_LAT";
    private static final String KEY_USER_LONG = "USER_LONG";
    private static final String KEY_PLACE_NAME = "PLACE_NAME";
    private static final String KEY_PLACE_LAT = "PLACE_LAT";
    private static final String KEY_PLACE_LONG = "PLACE_LONG";
    private static final String KEY_PLACE_RADIUS = "PLACE_RADIUS";
    private static final String KEY_PLACE_ADDRESS = "PLACE_ADDRESS";
    private static final String KEY_PLACE_PHONE = "PLACE_PHONE";

    public static final String KEY_MAP_TYPE = "MAP_TYPE";
    public static final String KEY_NOTIFICATION_SOUND = "NOTIFICATION_SOUND";
    public static final String KEY_NOTIFICATION_VIBRATE = "NOTIFICATION_VIBRATE";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setPlaceName(String placeName) {
        editor.putString(KEY_PLACE_NAME, placeName);
        editor.commit();
    }

    public String getPlaceName() {
        return pref.getString(KEY_PLACE_NAME, "");
    }

    public void setPlaceLat(String placeLat) {
        editor.putString(KEY_PLACE_LAT, placeLat);
        editor.commit();
    }

    public String getPlacelat() {
        return pref.getString(KEY_PLACE_LAT, "");
    }

    public void setPlaceLong(String placeLong) {
        editor.putString(KEY_PLACE_LONG, placeLong);
        editor.commit();
    }

    public String getPlaceLong() {
        return pref.getString(KEY_PLACE_LONG, "");
    }

    public void setPlaceRadius(String placeLong) {
        editor.putString(KEY_PLACE_RADIUS, placeLong);
        editor.commit();
    }

    public String getPlaceRadius() {
        return pref.getString(KEY_PLACE_RADIUS, "");
    }

    public void setPlaceAddress(String placeLong) {
        editor.putString(KEY_PLACE_ADDRESS, placeLong);
        editor.commit();
    }

    public String getPlaceAddress() {
        return pref.getString(KEY_PLACE_ADDRESS, "");
    }

    public void setPlacePhone(String placeLong) {
        editor.putString(KEY_PLACE_PHONE, placeLong);
        editor.commit();
    }

    public String getPlacePhone() {
        return pref.getString(KEY_PLACE_PHONE, "");
    }

    public void setUserLoginStatus(boolean isLoggedIn) {
        editor.putBoolean(KEY_USER_LOGIN, isLoggedIn);
        editor.commit();
    }

    public boolean getUserLoginStatus() {
        return pref.getBoolean(KEY_USER_LOGIN, false);
    }

    public void storeMapType(String mapType){
        editor.putString(KEY_MAP_TYPE, mapType);

        // commit changes
        editor.commit();
    }
    public void storeNotificationSound(boolean notificationSound){
        editor.putBoolean(KEY_NOTIFICATION_SOUND, notificationSound);

        // commit changes
        editor.commit();
    }
    public void storeNotificationVibrate(boolean notificationVibrate){
        editor.putBoolean(KEY_NOTIFICATION_VIBRATE, notificationVibrate);

        // commit changes
        editor.commit();
    }
    /**
     * Get stored session data
     * */
    public String getStoredMapType(){
        // return user
        return pref.getString(KEY_MAP_TYPE, "");
    }
    public boolean getStoredNotificationSoundProperty(){
        // return user
        return pref.getBoolean(KEY_NOTIFICATION_SOUND, false);
    }
    public boolean getStoredNotificationVibrateProperty(){
        // return user
        return pref.getBoolean(KEY_NOTIFICATION_VIBRATE, false);
    }

    public void setUserLat(String userLat) {
        editor.putString(KEY_USER_LAT, userLat);
        editor.commit();
    }

    public String getUserlat() {
        return pref.getString(KEY_USER_LAT, "0.0");
    }

    public void setUserLong(String userLong) {
        editor.putString(KEY_USER_LONG, userLong);
        editor.commit();
    }

    public String getUserLong() {
        return pref.getString(KEY_USER_LONG, "0.0");
    }
}
