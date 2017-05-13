package com.myapplication.monitor.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapplication.monitor.Interfaces.ViewResponseDelegates.MessageViewType;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.RegisterViewDelegate;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.Utils;
import com.myapplication.monitor.ViewModels.RegisterViewModel;


public class RegisterActivity extends AppCompatActivity implements RegisterViewDelegate {
    EditText textName;
    EditText textPhone;
    Button btnProceed;

    private Context mContext;
    private ProgressDialog dialog;

    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;
        registerViewModel = new RegisterViewModel(this);
        initView();
        initDialog();
        setupDefaults();
        setupListener();
    }

    private void initView() {
        textName = (EditText) findViewById(R.id.textName);
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

    private void setupDefaults() {
        registerViewModel.setDeviceId(Utils.getUDID(mContext));
        registerViewModel.setDeviceModel(Build.MODEL);
        registerViewModel.setDeviceBrand(Build.BRAND.toUpperCase());
    }

    private void setupListener() {
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewModel.setUserName(textName.getText().toString());
                registerViewModel.setUserPhone(textPhone.getText().toString());
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
        if(viewType == MessageViewType.Toast) {
            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showSuccessMessage(int message, MessageViewType viewType) {
        // No implementation
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(mContext, "Register Success", Toast.LENGTH_SHORT).show();
    }
}
