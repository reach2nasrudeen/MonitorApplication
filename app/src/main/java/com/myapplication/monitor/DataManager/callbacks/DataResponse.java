package com.myapplication.monitor.DataManager.callbacks;

import com.myapplication.monitor.Model.UserResponse;

import retrofit2.Response;

public interface DataResponse<T> {
    void onSuccess(String message);
    void onSuccess(T item, String message);
    void onFailure(String errorMessage);
    void onFailure(String errorMessage, String statusCode);
}