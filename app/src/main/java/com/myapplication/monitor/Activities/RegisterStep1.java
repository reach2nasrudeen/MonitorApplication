package com.myapplication.monitor.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.myapplication.monitor.ViewModels.RegisterViewModel;

public class RegisterStep1 extends BaseActivity implements RegisterViewDelegate {
    Context mContext;
    EditText textPhone;
    Button btnProceed;
    private ProgressDialog dialog;
    private RegisterViewModel registerViewModel;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_step1);
        sessionManager = getApp().getUserPreference();
        mContext = this;
        registerViewModel = new RegisterViewModel(this);
        initView();
        initDialog();
        setupListeners();
    }
    private void setupListeners() {
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewModel.setUserPhone(textPhone.getText().toString());
                registerViewModel.onCheckUserExist();
            }
        });
    }
    private void initView() {
        textPhone = (EditText) findViewById(R.id.textPhone);
        btnProceed = (Button) findViewById(R.id.btnProceed);
    }
    private void initDialog() {
        dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
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
        if (viewType == MessageViewType.Toast) {
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        }
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
        initActivity(new Intent(this,MapsActivity.class));
    }

    @Override
    public void launchRegistration() {
        initActivity(new Intent(this,RegisterActivity.class));
    }

    private void initActivity(Intent intent) {
        intent.putExtra("phone",registerViewModel.getUserPhone());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
