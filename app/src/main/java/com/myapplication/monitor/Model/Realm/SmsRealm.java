package com.myapplication.monitor.Model.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nasrudeen on 07/06/17.
 */

public class SmsRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private String userId;
    private String address;
    private String message;
    private String folder;
    private String smsdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getSmsdate() {
        return smsdate;
    }

    public void setSmsdate(String smsdate) {
        this.smsdate = smsdate;
    }
}
