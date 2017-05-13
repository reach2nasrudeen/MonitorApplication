package com.myapplication.monitor.Rest;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetrofitCallback<T> implements Callback<T> {
    private static final String TAG = RetrofitCallback.class.getSimpleName();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        int statusCode = response.code();
        if (response.body() == null || !response.isSuccessful()) {
            String message = null;
            try {
                message = response.errorBody().string();
                if (statusCode == 404 || statusCode == 500 || message.isEmpty()) {
                    onFailureCallback(call, null, response);
                    return;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            onFailureCallback(call, message, response);
            return;
        }

        String responseString="";
        try{
            if (response.body() instanceof  ResponseBody) {
                ResponseBody body = (ResponseBody) response.body();
                responseString = body.string();
            }
            onSuccessCallback(call, responseString, response);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String message = null;
        try {
            message = t.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        onFailureCallback(call, message, null);
    }

    public void onSuccessCallback(Call<T> call, String content, Response<T> response) {
    }

    public void onFailureCallback(Call<T> call, String message, Response<T> response) {
    }

    public void onExpiredCallback(Call<T> call, Response<T> response) {
    }

}
