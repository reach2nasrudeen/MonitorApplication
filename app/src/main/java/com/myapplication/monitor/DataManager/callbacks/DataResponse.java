package com.myapplication.monitor.DataManager.callbacks;

public interface DataResponse<T> {
    void onSuccess(String message);
    void onSuccess(T item, String message);
    void onFailure(String errorMessage);
    void onFailure(String errorMessage, String statusCode);
}