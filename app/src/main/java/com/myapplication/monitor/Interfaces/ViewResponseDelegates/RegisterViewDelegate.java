package com.myapplication.monitor.Interfaces.ViewResponseDelegates;

/**
 * Created by Mohamed on 05/14/2017.
 */

public interface RegisterViewDelegate extends StateViewDelegate, ProgressViewDelegate{
    void onRegisterSuccess();
    void launchRegistration();
}
