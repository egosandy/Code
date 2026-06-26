package com.rcdriver.cs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.AlamatAdapter;
import com.rcdriver.cs.adapter.PlacesAutoCompleteAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.json.ListLokasiRequest;
import com.rcdriver.cs.json.ListLokasiResponse;
import com.rcdriver.cs.models.SaveLokasiModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAmbil extends AppCompatActivity
        implements PlacesAutoCompleteAdapter.ClickListener, AlamatAdapter.ClickListener {
    public static final String FORM_VIEW_INDICATOR = "FormToFill";
    public static final String LOCATION_TITLE = "LocationTitle";
    public static final String LOCATION_NAME = "LocationName";
    public static final String LOCATION_LATLNG = "LocationLatLng";
    ImageView cleartext;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private AlamatAdapter alamatAdapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerAlamat;
    private EditText SearchText;
    private int formToFill;
    private double radius;
    private Double Lat, Lng;
    private LinearLayout OpenMap;
    private LinearLayout SetHome;
    private SettingPreference sp;
    private LinearLayout LayoutResult;
    List<SaveLokasiModel> saveLokasiModels;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_ambil);
        sp = new SettingPreference(this);
        recyclerView = findViewById(R.id.mRecycle);
        recyclerAlamat = findViewById(R.id.mAlamat);
        SearchText = findViewById(R.id.locationPicker_autoCompleteText);
        OpenMap = findViewById(R.id.map);
        SetHome = findViewById(R.id.SetHome);
        LayoutResult = findViewById(R.id.LayoutResult);
        cleartext = findViewById(R.id.mClear);
        ((EditText) findViewById(R.id.locationPicker_autoCompleteText)).addTextChangedListener(filterTextWatcher);
        Places.initialize(ActivityAmbil.this, MainActivity.apikey);
        if (!Places.isInitialized()) {
            Places.initialize(ActivityAmbil.this, MainActivity.apikey);
        }
        Intent intent = getIntent();
        formToFill = intent.getIntExtra(FORM_VIEW_INDICATOR, -1);
        Lat = intent.getDoubleExtra("Lat", 0);
        Lng = intent.getDoubleExtra("Lng", 0);
        radius = intent.getDoubleExtra("radius", 5.0);
        LatLng latLng = new LatLng(Lat,Lng);
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this,latLng,radius);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
        ListLokasi();
        OpenMap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityAmbil.this, ActivitySetHome.class);
                i.putExtra("Lat",sp.getSetting()[6]);
                i.putExtra("Lng",sp.getSetting()[7]);
                i.putExtra(ActivityAmbil.FORM_VIEW_INDICATOR, 2);
                startActivityForResult(i, 2);
            }
        });
        cleartext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchText.setText("");
            }
        });
        SetHome.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = Double.parseDouble(sp.getSetting()[6]);
                double lng = Double.parseDouble(sp.getSetting()[7]);
                String Alamat = sp.getSetting()[8];
                LatLng selectedLocation = new LatLng(lat,lng);
                String selectedAddress = sp.getSetting()[8];
                Intent intent = new Intent();
                intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
                intent.putExtra(LOCATION_NAME, selectedAddress);
                intent.putExtra(LOCATION_LATLNG, selectedLocation);
                setResult(Activity.RESULT_OK, intent);
                finish();
                //  Toast.makeText(ActivityAmbil.this, lat + "," + lng + "," + Alamat, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String addressset = data.getStringExtra(ActivitySetHome.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(ActivitySetHome.LOCATION_LATLNG);
                Intent intent = new Intent();
                intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
                intent.putExtra(LOCATION_NAME, addressset);
                intent.putExtra(LOCATION_LATLNG, latLng);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String addressname = data.getStringExtra(ActivitySetHome.LOCATION_TITLE);
                String addressset = data.getStringExtra(ActivitySetHome.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(ActivitySetHome.LOCATION_LATLNG);
                Intent intent = new Intent();
                intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
                intent.putExtra(LOCATION_TITLE, addressname);
                intent.putExtra(LOCATION_NAME, addressset);
                intent.putExtra(LOCATION_LATLNG, latLng);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                if (recyclerView.getVisibility() == View.GONE) {recyclerView.setVisibility(View.VISIBLE);}
                if (LayoutResult.getVisibility() == View.VISIBLE) {LayoutResult.setVisibility(View.GONE);}
                if (cleartext.getVisibility() == View.GONE) {cleartext.setVisibility(View.VISIBLE);}
            } else {
                if (recyclerView.getVisibility() == View.VISIBLE) {recyclerView.setVisibility(View.GONE);}
                if (LayoutResult.getVisibility() == View.GONE) {LayoutResult.setVisibility(View.VISIBLE);}
                if (cleartext.getVisibility() == View.VISIBLE) {cleartext.setVisibility(View.GONE);}
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
    };

    @Override
    public void click(Place place) {
        SearchText.setText(place.getAddress());
        LatLng selectedLocation = new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);
        String selectedAddress = place.getAddress();
        String selectedName = place.getName();
        Intent intent = new Intent();
        intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
        intent.putExtra(LOCATION_TITLE, selectedName);
        intent.putExtra(LOCATION_NAME, selectedAddress);
        intent.putExtra(LOCATION_LATLNG, selectedLocation);
        setResult(Activity.RESULT_OK, intent);
        finish();
        // Toast.makeText(this, place.getAddress()+", "+place.getLatLng().latitude+place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
    }
    private void ListLokasi() {
        try {
            User login = BaseApp.getInstance(this).getLoginUser();
            UserService userService = ServiceGenerator.createService(
                    UserService.class, login.getNoTelepon(), login.getPassword());
            ListLokasiRequest param = new ListLokasiRequest();
            param.setId(login.getId());
            userService.ListLokasi(param).enqueue(new Callback<ListLokasiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ListLokasiResponse> call, @NonNull Response<ListLokasiResponse> response) {
                    if (response.isSuccessful()) {
                        if (saveLokasiModels != null) {
                            saveLokasiModels.clear();
                        }
                        saveLokasiModels = response.body().getData();
                        for (int i = 0; i < saveLokasiModels.size(); ) {
                            if (saveLokasiModels != null && saveLokasiModels.size() > 0) {
                                alamatAdapter = new AlamatAdapter(saveLokasiModels,ActivityAmbil.this);
                                recyclerAlamat.setLayoutManager(new LinearLayoutManager(ActivityAmbil.this));
                                recyclerAlamat.setAdapter(alamatAdapter);
                                alamatAdapter.setClickListener(ActivityAmbil.this);
                                alamatAdapter.notifyDataSetChanged();
                            }else{
                                if (LayoutResult.getVisibility() == View.VISIBLE) {LayoutResult.setVisibility(View.GONE);}
                            }
                            Log.d("mLokasi", "Alamat: " + saveLokasiModels.get(i).getAlamat());
                            i++;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ListLokasiResponse> call, @NonNull Throwable t) {
                    Log.e("mLokasi Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mLokasi Error", e.getMessage());
        }

    }

    @Override
    public void click(SaveLokasiModel lokasiModel) {
        double Lat = Double.parseDouble(lokasiModel.getLatitude());
        double Lng = Double.parseDouble(lokasiModel.getLongitude());
        LatLng selectedLocation = new LatLng(Lat,Lng);
        String selectedName = lokasiModel.getNama();
        String selectedAddress = lokasiModel.getAlamat();
        Intent intent = new Intent();
        intent.putExtra(FORM_VIEW_INDICATOR, formToFill);
        intent.putExtra(LOCATION_TITLE, selectedName);
        intent.putExtra(LOCATION_NAME, selectedAddress);
        intent.putExtra(LOCATION_LATLNG, selectedLocation);
        setResult(Activity.RESULT_OK, intent);
        finish();
        //Toast.makeText(ActivityAmbil.this, "lat :" + lokasiModel.getLatitude() + " lng :" + lokasiModel.getLongitude(), Toast.LENGTH_SHORT).show();
    }
}