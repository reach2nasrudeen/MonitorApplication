package com.myapplication.monitor.Base;

/**
 * Created by Mohamed on 05/14/2017.
 */

public class WebServiceURL {
    public static String BASE_URL;

    public static String SERVICE_PATH;

    public WebServiceURL() {
        BASE_URL = "http://192.168.1.4/";
        SERVICE_PATH = "monitor/";
    }

    // Endpoints
    public static final String USER_REGISTER = "addUser.php";

}
