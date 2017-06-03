package com.myapplication.monitor.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    RadioGroup groupMapType;
    View mapTypeLayout;
    View soundLayout;
    View vibrateLayout;
    CheckBox checkBoxSound;
    CheckBox checkBoxVibrate;
    TextView selectedTextMapType;
    String selectedStrMapType;
    String strStoredMapType;
    Button btnLogout;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        initToolbar();
        setListeners();
        handleAlertLayout();
        strStoredMapType = sessionManager.getStoredMapType();
        if(!strStoredMapType.equals("")){
            selectedTextMapType.setText(strStoredMapType);
        }
        checkBoxSound.setChecked(sessionManager.getStoredNotificationSoundProperty());
        checkBoxVibrate.setChecked(sessionManager.getStoredNotificationVibrateProperty());
        processMapTypeDialog();
    }

    public void initView(){
        sessionManager = new SessionManager(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        checkBoxSound = (CheckBox) findViewById(R.id.notificationSound);
        checkBoxVibrate = (CheckBox) findViewById(R.id.notificationVibrate);
        selectedTextMapType = (TextView) findViewById(R.id.selectedTextMapType);
        mapTypeLayout = findViewById(R.id.mapTypeLayout);
        soundLayout = findViewById(R.id.soundLayout);
        vibrateLayout = findViewById(R.id.vibrateLayout);
    }

    public void initToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setListeners(){
        checkBoxSound.setOnClickListener(this);
        checkBoxVibrate.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    public void handleAlertLayout(){
        soundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxSound.isChecked()){
                    checkBoxSound.setChecked(false);
                }else {
                    checkBoxSound.setChecked(true);
                }
            }
        });
        vibrateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxVibrate.isChecked()){
                    checkBoxVibrate.setChecked(false);
                }else {
                    checkBoxVibrate.setChecked(true);
                }
            }
        });
    }

    public void processMapTypeDialog(){
        mapTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_map_type);
                dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                dialog.show();

                groupMapType = (RadioGroup) dialog.findViewById(R.id.maptypeGroup);
                groupMapType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (groupMapType.getCheckedRadioButtonId()){
                            case R.id.normalMapBtn:
                                selectedStrMapType = getString(R.string.map_type_normal);
                                break;
                            case R.id.terrainMapBtn:
                                selectedStrMapType = getString(R.string.map_type_terrain);
                                break;
                            case R.id.hybridMapBtn:
                                selectedStrMapType = getString(R.string.map_type_hybrid);
                                break;
                            case R.id.satelliteMapBtn:
                                selectedStrMapType = getString(R.string.map_type_satellite);
                                break;
                        }
                    }
                });

                dialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sessionManager.storeMapType(selectedStrMapType);
                        selectedTextMapType.setText(selectedStrMapType);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notificationSound:
                sessionManager.storeNotificationSound(checkBoxSound.isChecked());
                break;
            case R.id.notificationVibrate:
                sessionManager.storeNotificationVibrate(checkBoxVibrate.isChecked());
                break;
            case R.id.btnLogout:
                sessionManager.setUserLoginStatus(false);
                Intent intent = new Intent(this, RegisterStep1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                goHome(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            goHome(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        goHome(intent);
    }
    private void goHome(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
    }
}
