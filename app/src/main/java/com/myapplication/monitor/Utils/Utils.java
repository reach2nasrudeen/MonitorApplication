package com.myapplication.monitor.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public static String getMiles(double meter) {
        return new DecimalFormat("#.#").format(meter );
    }

    public static String getDateWithFormat(long date, String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);

        SimpleDateFormat dobFormat = new SimpleDateFormat(format, Locale.US);
        String dateFormat = dobFormat.format(c.getTime());
        System.out.println("Printing the date format --  " + dateFormat);

        return dateFormat;
    }
}
