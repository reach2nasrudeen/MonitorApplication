package com.myapplication.monitor.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

/**
 * Created by Mohamed on 05/13/2017.
 */

public class Utils {
    @SuppressLint("HardwareIds")
    public static String getUDID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isValidString(String text) {
        if (text != null && !text.equalsIgnoreCase("null") && !text.equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }

    }
}