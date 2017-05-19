package com.myapplication.monitor.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myapplication.monitor.R;
import com.myapplication.monitor.Utils.SessionManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    SessionManager sessionManager;
    private GoogleMap mMap;

    private ImageView btnSettings;
    private ImageView btnDetails;

    private String strMapType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sessionManager = new SessionManager(this);
//        sessionManager.setPlaceLat("13.0304813");
//        sessionManager.setPlaceLong("80.2600098");
//        sessionManager.setPlaceName("Office");
        strMapType = sessionManager.getStoredMapType();
        initView();
        setListeners();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
            }
        });

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
            }
        });
    }

    private int getMapType(){
        int mapType = 0;
        if(strMapType.equals(getString(R.string.map_type_normal))){
            mapType = GoogleMap.MAP_TYPE_NORMAL;
        }if(strMapType.equals(getString(R.string.map_type_hybrid))){
            mapType = GoogleMap.MAP_TYPE_HYBRID;
        }if(strMapType.equals(getString(R.string.map_type_satellite))){
            mapType = GoogleMap.MAP_TYPE_SATELLITE;
        }if(strMapType.equals(getString(R.string.map_type_terrain))){
            mapType = GoogleMap.MAP_TYPE_TERRAIN;
        }if(strMapType.equals("")){
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
}
