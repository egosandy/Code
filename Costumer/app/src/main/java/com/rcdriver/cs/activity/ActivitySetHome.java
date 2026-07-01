package com.rcdriver.cs.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.rcdriver.cs.R;

/**
 * Created by Maswend Team on 12/3/2020.
 */

public class ActivitySetHome extends AppCompatActivity
        implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String FORM_VIEW_INDICATOR = "FormToFill";
    public static final String LOCATION_NAME = "LocationName";
    public static final String LOCATION_LATLNG = "LocationLatLng";
    public static final String LOCATION_TITLE = "LocationTitle";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    TextView currentAddress;
    LinearLayout SetLokasi;
    private GoogleMap gMap;
    private GoogleApiClient googleApiClient;
    private int formToFill;
    private TextView TxtAmbil,mAlamat;
    private String Title;
    private double Lat,Lng;
    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sethome);
        currentAddress = findViewById(R.id.locationPicker_currentAddress);
        SetLokasi = findViewById(R.id.locationPicker_container);
        TxtAmbil = findViewById(R.id.TxtAmbil);
        mAlamat = findViewById(R.id.Alamat);
        //recyclerView = findViewById(R.id.mRecycle);
        setupGoogleApiClient();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locationPicker_maps);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        Intent intent = getIntent();
        formToFill = intent.getIntExtra(FORM_VIEW_INDICATOR, -1);
        Lat = intent.getDoubleExtra("Lat", 0);
        Lng = intent.getDoubleExtra("Lng", 0);
        SetLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLocation();
            }
        });
    }

    private void selectLocation() {
        if(Title.isEmpty()){
            String addressname = mAlamat.getText().toString();
            LatLng selectedLocation = gMap.getCameraPosition().target;
            String selectedAddress = currentAddress.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
            intent.putExtra(LOCATION_NAME, selectedAddress);
            intent.putExtra(LOCATION_LATLNG, selectedLocation);
            intent.putExtra(LOCATION_TITLE,"Alamat Tujuan");
            setResult(Activity.RESULT_OK, intent);
            finish();
        }else{
            String addressname = mAlamat.getText().toString();
            LatLng selectedLocation = gMap.getCameraPosition().target;
            String selectedAddress = currentAddress.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
            intent.putExtra(LOCATION_NAME, selectedAddress);
            intent.putExtra(LOCATION_LATLNG, selectedLocation);
            intent.putExtra(LOCATION_TITLE,Title);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

    }

    private void setupGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    private void updateLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }

        Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (lastKnownLocation != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 15f)
            );
            gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        updateLastLocation();
        setupMapOnCameraChange();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLastLocation();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String addressset = data.getStringExtra(ActivitySetHome.LOCATION_NAME);
                String addressname = data.getStringExtra(ActivitySetHome.LOCATION_TITLE);
                LatLng latLng = data.getParcelableExtra(ActivitySetHome.LOCATION_LATLNG);
                Intent intent = new Intent();
                intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
                intent.putExtra(LOCATION_NAME, addressset);
                intent.putExtra(LOCATION_LATLNG, latLng);
                intent.putExtra(LOCATION_TITLE, addressname);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String addressset = data.getStringExtra(ActivitySetHome.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(ActivitySetHome.LOCATION_LATLNG);
                String addressname = data.getStringExtra(ActivitySetHome.LOCATION_TITLE);
                Intent intent = new Intent();
                intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
                intent.putExtra(LOCATION_NAME, addressset);
                intent.putExtra(LOCATION_LATLNG, latLng);
                intent.putExtra(LOCATION_TITLE, addressname);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    private void setupMapOnCameraChange() {
        gMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = gMap.getCameraPosition().target;
                fillAddress(currentAddress, center);
                fillAddress(mAlamat,center);
            }
        });
    }

    private void fillAddress(final TextView textView, final LatLng latLng) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Geocoder geocoder = new Geocoder(ActivitySetHome.this, Locale.getDefault());
                    final List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    ActivitySetHome.this.runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            if (!addresses.isEmpty()) {
                                addresses.size();
                                String address = addresses.get(0).getAddressLine(0);
                                Title = addresses.get(0).getSubLocality();
                                textView.setText(address);
                            } else {
                                textView.setText("not Availeble");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
