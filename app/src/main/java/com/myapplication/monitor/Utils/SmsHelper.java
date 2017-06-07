package com.myapplication.monitor.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.myapplication.monitor.Model.Sms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 06/06/2017.
 */

public class SmsHelper {
    private Context mContext;
    private SessionManager sessionManager;
    public SmsHelper(Context mContext) {
        this.mContext = mContext;
        sessionManager = new SessionManager(mContext);
    }

    public List<Sms> getAllSms() {
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms;
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = mContext.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setUserId(sessionManager.getUserId());
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(Utils.getDateWithFormat(Long.parseLong(c.getString(c.getColumnIndexOrThrow("date"))), "dd/MM/yyyy hh:mm:ss a"));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        c.close();

        return lstSms;
    }
}
