package com.myapplication.monitor.ViewModels;

import android.text.TextUtils;

import com.myapplication.monitor.DataManager.RegisterDataManager;
import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Interfaces.ViewDelegates.RegisterDelegate;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.MessageViewType;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.RegisterViewDelegate;
import com.myapplication.monitor.Model.Place;
import com.myapplication.monitor.Model.UserResponse;
import com.myapplication.monitor.Utils.AppConstants;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by Mohamed on 05/14/2017.
 */

public class RegisterViewModel extends RegisterBaseViewModel implements RegisterDelegate {
    private RegisterDataManager registerDataManager;
    private RegisterViewDelegate registerViewDelegate;

    public RegisterViewModel(RegisterViewDelegate viewDelegate) {
        registerViewDelegate = viewDelegate;
        registerDataManager = new RegisterDataManager();
    }

    @Override
    public void onRegister() {

        if (isValid()) {
            registerViewDelegate.showProgressView(true);
            registerDataManager.doRegister(getUserName(),
                    getUserPhone(),
                    getDeviceId(),
                    getDeviceBrand(),
                    getDeviceModel(), new DataResponse<Place>() {
                        @Override
                        public void onSuccess(String message) {
                            //No implementation
                        }

                        @Override
                        public void onSuccess(Place item, String message) {
                            setPlace(item);
                            registerViewDelegate.showProgressView(false);
                            registerViewDelegate.onRegisterSuccess();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            registerViewDelegate.showProgressView(false);
                            registerViewDelegate.showErrorMessage(errorMessage, MessageViewType.Toast);
                        }

                        @Override
                        public void onFailure(String errorMessage, String statusCode) {
                            registerViewDelegate.showProgressView(false);
                            registerViewDelegate.showErrorMessage(errorMessage, MessageViewType.Toast);
                        }
                    });
        }

    }

    private RequestBody prepareRequestBody() {
        return new FormBody.Builder()
                .add(AppConstants.PARAM_USERNAME, getUserName())
                .add(AppConstants.PARAM_USER_PHONE, getUserPhone())
                .add(AppConstants.PARAM_DEVICE_ID, getDeviceId())
                .add(AppConstants.PARAM_DEVICE_BRAND, getDeviceBrand())
                .add(AppConstants.PARAM_DEVICE_MODEL, getDeviceModel())
                .build();
    }


    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(getUserName())) {
            isValid = false;
            registerViewDelegate.showErrorMessage("Username not valid", MessageViewType.Toast);
        } else if (TextUtils.isEmpty(getUserPhone())) {
            isValid = false;
            registerViewDelegate.showErrorMessage("Phone number not valid", MessageViewType.Toast);
        }
        return isValid;
    }
}
