package com.myapplication.monitor.Base;

/**
 * Created by Mohamed on 05/14/2017.
 */

public class WebServiceURL {
    public static String BASE_URL;

    public static String SERVICE_PATH;

    public WebServiceURL() {
        BASE_URL = "http://192.168.0.134/";
        SERVICE_PATH = "Monitor/v1/";
    }

    // Endpoints
    public static final String CHECK_USER_EXIST = "checkUserExist";
    public static final String USER_REGISTER = "createUser";
    public static final String UPDATE_USER_TOKEN = "updateToken";
    public static final String UPDATE_CONTACTS = "updateContacts";
    public static final String UPDATE_HISTORY = "updateHistory";
    public static final String UPDATE_SMS = "updateSms";
    public static final String UPDATE_CALLS = "updateCalls";

}
