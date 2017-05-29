package com.myapplication.monitor.DataManager.callbacks;

public interface DaoResponse<T> {
    void onSuccess(String message);
    void onSuccess(T item, String message);
    void onFailure(String errorMessage);
}