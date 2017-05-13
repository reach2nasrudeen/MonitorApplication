package com.myapplication.monitor.Interfaces.ViewResponseDelegates;

/**
 * Created by Mohamed on 05/14/2017.
 */

public interface StateViewDelegate {

    void showErrorMessage(int errorMessage, MessageViewType viewType);
    void showErrorMessage(String errorMessage, MessageViewType viewType);
    void showSuccessMessage(int message, MessageViewType viewType);
}
