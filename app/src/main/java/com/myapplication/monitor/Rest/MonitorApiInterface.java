package com.myapplication.monitor.Rest;

import com.myapplication.monitor.Base.WebServiceURL;
import com.myapplication.monitor.Model.UserResponse;
import com.myapplication.monitor.Utils.AppConstants;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Mohamed on 05/14/2017.
 */

public interface MonitorApiInterface {

    //GET


    //POST
    @FormUrlEncoded
    @POST(WebServiceURL.USER_REGISTER)
    Call<ResponseBody> registerUser(@Field(AppConstants.PARAM_USERNAME) String name,
                                    @Field(AppConstants.PARAM_USER_PHONE) String phone,
                                    @Field(AppConstants.PARAM_DEVICE_ID) String deviceId,
                                    @Field(AppConstants.PARAM_DEVICE_BRAND) String deviceBrand,
                                    @Field(AppConstants.PARAM_DEVICE_MODEL) String deviceModel,
                                    @Field(AppConstants.PARAM_LATITUDE) String latitude,
                                    @Field(AppConstants.PARAM_LONGITUDE) String longitude);
    @FormUrlEncoded
    @POST(WebServiceURL.CHECK_USER_EXIST)
    Call<ResponseBody> checkUserExist(@Field(AppConstants.PARAM_USER_PHONE) String phone);

    @FormUrlEncoded
    @POST(WebServiceURL.UPDATE_USER_TOKEN)
    Call<ResponseBody> updateToken(@Field(AppConstants.PARAM_PUSH_USERNAME) String name,
                                    @Field(AppConstants.PARAM_PUSH_USER_TOKEN) String token);
}
