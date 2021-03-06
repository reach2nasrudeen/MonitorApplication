package com.myapplication.monitor.DataManager;

import android.util.Log;

import com.google.gson.Gson;
import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Model.Place;
import com.myapplication.monitor.Model.User;
import com.myapplication.monitor.Model.UserResponse;
import com.myapplication.monitor.Rest.MonitorApiInterface;
import com.myapplication.monitor.Rest.RetrofitCallback;
import com.myapplication.monitor.Utils.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapplication.monitor.Base.MonitorApp.getApp;

/**
 * Created by Mohamed on 05/14/2017.
 */

public class RegisterDataManager implements AppConstants {

    private final String TAG = RegisterDataManager.class.getSimpleName();
    private final MonitorApiInterface service;

    public RegisterDataManager() {

        service = getApp().getRetrofitInterface();
    }

    public void checkUserExist(String phone, final DataResponse<String[]> dataResponse) {

        Call<ResponseBody> timeLogResponseCall = service.checkUserExist(phone);

        timeLogResponseCall.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();

                if (statusCode == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        JSONArray jsonArray = jsonObject.getJSONArray("user");
                        if(jsonArray.length() != 0) {
                            JSONObject userObject = jsonArray.getJSONObject(0);
                            User user = new User();
                            user.setId(userObject.getString("id"));
                            user.setPhone(userObject.getString("phone"));
                            user.setName(userObject.getString("name"));
                            user.setDeviceBrand(userObject.getString("deviceBrand"));
                            user.setDeviceId(userObject.getString("deviceId"));
                            user.setDeviceModel(userObject.getString("deviceModel"));
                            user.setLatitude(userObject.getString("latitude"));
                            user.setLongitude(userObject.getString("longitude"));

                            JSONArray jsonArray1 = jsonObject.getJSONArray("place");
                            JSONObject placeObject = jsonArray1.getJSONObject(0);
                            Place place = new Place();
                            place.setId(Integer.parseInt(placeObject.getString("id")));
                            place.setAddress(placeObject.getString("address"));
                            place.setPhone(placeObject.getString("phone"));
                            place.setLatitude(Double.parseDouble(placeObject.getString("latitude")));
                            place.setLongitude(Double.parseDouble(placeObject.getString("longitude")));
                            place.setRadius(Integer.parseInt(placeObject.getString("radius")));
                            dataResponse.onSuccess(new String[] {
                                    new Gson().toJson(user),new Gson().toJson(place)}, "Already Registered");
                        } else {
                            dataResponse.onSuccess("User Not available");
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dataResponse.onFailure(ERROR_STATUS, String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
                dataResponse.onFailure("Something went wrong while trying login!");
            }
        });
    }
    public void doRegister(User user, final DataResponse<String[]> dataResponse) {

        Call<ResponseBody> timeLogResponseCall = service.registerUser(user.getName(),
                user.getPhone(),
                user.getDeviceId(),
                user.getDeviceBrand(),
                user.getDeviceModel(),
                user.getLatitude(),
                user.getLongitude());

        timeLogResponseCall.enqueue(new RetrofitCallback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();

                if (statusCode == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String userId = jsonObject.getString("userId");
                        JSONArray jsonArray = jsonObject.getJSONArray("place");
                        JSONObject placeObject = jsonArray.getJSONObject(0);
                        Place place = new Place();
                        place.setId(Integer.parseInt(placeObject.getString("id")));
                        place.setAddress(placeObject.getString("address"));
                        place.setPhone(placeObject.getString("phone"));
                        place.setLatitude(Double.parseDouble(placeObject.getString("latitude")));
                        place.setLongitude(Double.parseDouble(placeObject.getString("longitude")));
                        place.setRadius(Integer.parseInt(placeObject.getString("radius")));
                        Log.e("Status", "Register Success");
                        dataResponse.onSuccess(new String[]{userId,new Gson().toJson(place)}, "Register Success");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dataResponse.onFailure(ERROR_STATUS, String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
                dataResponse.onFailure("Something went wrong while trying login!");
            }
        });
    }

    public void doUpdateToken(String name, String token, final DataResponse<String> dataResponse) {
        Call<ResponseBody> updateTokenCall = service.updateToken(name,token);

        updateTokenCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();

                if (statusCode == 201) {
                    dataResponse.onSuccess("Token updated success");
                    Log.e(TAG,"Token updated success");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dataResponse.onSuccess("Token updated failure");
                Log.e(TAG,"Token updated failure");
            }
        });
    }
}
