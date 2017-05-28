package com.myapplication.monitor.ViewModels;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.myapplication.monitor.DataManager.RegisterDataManager;
import com.myapplication.monitor.DataManager.callbacks.DataResponse;
import com.myapplication.monitor.Interfaces.ViewDelegates.RegisterDelegate;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.MessageViewType;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.RegisterViewDelegate;
import com.myapplication.monitor.Model.Place;
import com.myapplication.monitor.Model.User;
import com.myapplication.monitor.Model.UserResponse;
import com.myapplication.monitor.Utils.AppConstants;

import java.lang.reflect.Type;
import java.util.List;

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
            registerDataManager.doRegister(getUser(), new DataResponse<Place>() {
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

    @Override
    public void onCheckUserExist() {
        if (TextUtils.isEmpty(getUserPhone())) {
            registerViewDelegate.showErrorMessage("Username not valid", MessageViewType.Toast);
            return;
        }

        registerViewDelegate.showProgressView(true);
        registerDataManager.checkUserExist(getUserPhone(), new DataResponse<String[]>() {
            @Override
            public void onSuccess(String message) {
                registerViewDelegate.showProgressView(false);
                registerViewDelegate.launchRegistration();
            }

            @Override
            public void onSuccess(String[] item, String message) {
                setUser(new Gson().fromJson(item[0],User.class));
                setPlace(new Gson().fromJson(item[1],Place.class));
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

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(getUser().getName())) {
            isValid = false;
            registerViewDelegate.showErrorMessage("Username not valid", MessageViewType.Toast);
        } else if (TextUtils.isEmpty(getUser().getPhone())) {
            isValid = false;
            registerViewDelegate.showErrorMessage("Phone number not valid", MessageViewType.Toast);
        }
        return isValid;
    }
}
