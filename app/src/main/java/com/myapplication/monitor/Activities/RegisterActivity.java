package com.myapplication.monitor.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapplication.monitor.Base.BaseActivity;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.MessageViewType;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.RegisterViewDelegate;
import com.myapplication.monitor.Model.Place;
import com.myapplication.monitor.Model.User;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;
import com.myapplication.monitor.Utils.Utils;
import com.myapplication.monitor.ViewModels.RegisterViewModel;


public class RegisterActivity extends BaseActivity implements RegisterViewDelegate {
    EditText textName;
    EditText textPhone;
    Button btnProceed;
    String phone;
    private Context mContext;
    private ProgressDialog dialog;

    private SessionManager sessionManager;
    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;
        sessionManager = getApp().getUserPreference();
        registerViewModel = new RegisterViewModel(this);

        phone = getIntent().getStringExtra("phone");

        initView();
        initDialog();
        setupListener();
    }

    private void initView() {
        textName = (EditText) findViewById(R.id.textName);
        textPhone = (EditText) findViewById(R.id.textPhone);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        textPhone.setText(phone);
        textPhone.setEnabled(false);
    }

    private void initDialog() {
        dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    private User getUserData() {
        User user = new User();
        user.setDeviceId(Utils.getUDID(mContext));
        user.setDeviceModel(Build.MODEL);
        user.setDeviceBrand(Build.BRAND.toUpperCase());
        user.setLatitude(sessionManager.getUserlat());
        user.setLongitude(sessionManager.getUserLong());
        user.setName(textName.getText().toString());
        user.setPhone(phone);
        return user;
    }
    private void setupListener() {
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewModel.setUser(getUserData());
                registerViewModel.onRegister();
            }
        });
    }

    @Override
    public void showProgressView(Boolean show) {
        if (dialog != null) {
            if (show) {
                dialog.show();
            } else {
                dialog.dismiss();
            }
        }
    }

    @Override
    public void showErrorMessage(int errorMessage, MessageViewType viewType) {
        //No implementation
    }

    @Override
    public void showErrorMessage(String errorMessage, MessageViewType viewType) {
        if (viewType == MessageViewType.Toast) {
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showSuccessMessage(int message, MessageViewType viewType) {
        // No implementation
    }

    @Override
    public void onRegisterSuccess() {
        sessionManager.setUserLoginStatus(true);

        User user = registerViewModel.getUser();
        sessionManager.setUserId(user.getId());
        Place place = registerViewModel.getPlace();
        sessionManager.setPlaceName(place.getName());
        sessionManager.setPlaceAddress(place.getAddress());
        sessionManager.setPlaceLat(String.valueOf(place.getLatitude()));
        sessionManager.setPlaceLong(String.valueOf(place.getLongitude()));
        sessionManager.setPlacePhone(place.getPhone());
        sessionManager.setPlaceRadius(String.valueOf(place.getRadius()));

        initActivity(new Intent(mContext,MapsActivity.class));
//        Toast.makeText(mContext, "Register Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchRegistration() {
        initActivity(new Intent(mContext, RegisterStep1.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    private void initActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        checkExit();
    }

    public void checkExit() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, getString(R.string.press_again_to_exit),
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
