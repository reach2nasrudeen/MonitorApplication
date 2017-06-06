package com.myapplication.monitor.Model;

/**
 * Created by Nasrudeen on 06/06/17.
 */

public class BrowserHistory {
    private String userId;
    private String title;
    private String url;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
