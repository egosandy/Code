package com.rcdriver.cs.activity;

import com.rcdriver.cs.utils.LocalStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rcdriver.cs.adapter.PlaceAutoCompletedAdapter;
import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.FiturPromoAdapter;
import com.rcdriver.cs.adapter.ListDriverAdapter;
import com.rcdriver.cs.adapter.ListDriverClick;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.gmap.directions.Directions;
import com.rcdriver.cs.gmap.directions.Route;
import com.rcdriver.cs.json.FiturPromoRequest;
import com.rcdriver.cs.json.GetNearRideCarRequestJson;
import com.rcdriver.cs.json.GetNearRideCarResponseJson;
import com.rcdriver.cs.json.PromoRequestJson;
import com.rcdriver.cs.json.PromoResponse;
import com.rcdriver.cs.json.PromoResponseJson;
import com.rcdriver.cs.json.RideCarRequestJson;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.TransaksiModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.models.VoucherModel;
import com.rcdriver.cs.order.RideOrder;
import com.rcdriver.cs.utils.NetworkManager;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.MapDirectionAPI;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OjekNewActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String FITUR_KEY = "FiturKey";
    private static final String TAG = "OjekNewActivity";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    TransaksiModel transaksi;
    Context context = OjekNewActivity.this;
    private boolean isMapReady = false;


    CoordinatorLayout rootLayout;

    LinearLayout setPickUpContainer;

    LinearLayout setDestinationContainer;

    Button setPickUpButton;

    Button setDestinationButton;

    TextView fiturtext;

    ImageView backbtn;

    LinearLayout bottomsheet;

    RelativeLayout rlprogress;

    TextView NameAsal;

    TextView pickUpText;

    TextView NameTujuan;

    TextView destinationText;

    EditText promokode;

    Button btnpromo;

    RecyclerView mRecycler;

    RadioGroup SetMetode;

    RadioButton SetTunai;

    RadioButton SetSaldo;

    TextView jarak;

    TextView estimasi;

    TextView cost;

    TextView numdiskon;

    TextView diskon;

    TextView priceText;

    Button btnOrder;

    LinearLayout StepLayout2;

    LinearLayout StepLayout3;

    RecyclerView mDriverRec;

    ImageView closeDriver;

    AutoCompleteTextView autoPickUpText;

    AutoCompleteTextView autoDestionationText;

    private GoogleMap gMap;
    private GoogleApiClient googleApiClient;
    private Location lastKnownLocation;
    private LatLng pickUpLatLang;
    private LatLng destinationLatLang;
    private Polyline directionLine;
    private Marker pickUpMarker;
    private Marker destinationMarker;
    int FITURID = -1;
    double dLatitude = 0;
    double dLongitude = 0;
    private double Latitude,Longitude;
    private String fitur,ICONFITUR, NamaAlamat, NamaAsal, dAlamat, checkedpaywallet,
            biayaminimum, saldoWallet, getbiaya, biayaakhir, fiturdesc, icondriver;
    private SettingPreference sp;

    private ArrayList<DriverModel> pilihdriver;
    private List<Marker> driverMarkers;
    private List<DriverModel> driverAvailable;
    private FiturModel designedFitur;

    private double mjarak;
    private long harga, promocode, maksimum;
    private double Radius;

    private List<VoucherModel> mItems = new ArrayList<>();
    private FiturPromoAdapter mAdapter;
    private ListDriverAdapter dAdapter;
    Handler handler;

    private GeoDataClient mGeoDataClient;
    private PlaceAutoCompletedAdapter mPlaceAutocompleteAdapter;
    AutocompleteSessionToken autocompleteSessionToken;
    PlacesClient placesClient;
    private final okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
            setDestinationContainer.setVisibility(View.VISIBLE);
            rlprogress.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(rootLayout, "error connection, please select destination again!", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            snackbar.show();

        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = Objects.requireNonNull(response.body()).string();
                final long distance = MapDirectionAPI.getDistance(OjekNewActivity.this, json);
                final String time = MapDirectionAPI.getTimeDistance(OjekNewActivity.this, json);
                if (distance >= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String format = String.format(Locale.US, "%.0f", (double) distance / 1000f);
                            long dist = Long.parseLong(format);
                            if (dist < maksimum) {
                                rlprogress.setVisibility(View.GONE);
                                promocode = 0;
                                promokode.setText("");
                                updateLineDestination(json);
                                updateDistance(distance);
                                estimasi.setText(time);
                                numdiskon.setText(String.valueOf(promocode));
                                Utility.currencyDiskon(diskon, String.valueOf(promocode), OjekNewActivity.this);
                                DaftarPromo();
                                fetchNearDriver(pickUpLatLang);
                                btnOrder.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which){
                                                    case DialogInterface.BUTTON_POSITIVE:
                                                        //Yes button clicked
                                                        KlikPesan("null",1);
                                                        break;

                                                    case DialogInterface.BUTTON_NEGATIVE:
                                                        //No button clicked
                                                        StepLayout2.setVisibility(View.GONE);
                                                        StepLayout3.setVisibility(View.VISIBLE);
                                                        driverterdekat(pickUpLatLang);
                                                        break;
                                                }
                                            }
                                        };
                                        AlertDialog.Builder builder = new AlertDialog.Builder(OjekNewActivity.this);
                                        builder.setMessage("PILIH METODE PEMESANAN")
                                                .setPositiveButton("Otomatis", dialogClickListener)
                                                .setNegativeButton("Pilih Driver", dialogClickListener).show();

                                    }
                                });
                            } else {
                                rlprogress.setVisibility(View.GONE);
                                setDestinationContainer.setVisibility(View.VISIBLE);
                                Snackbar snackbar = Snackbar.make(rootLayout, "Jarak Tujuan Terlalu Jauh.", Snackbar.LENGTH_LONG);
                                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                                snackbar.show();

                            }
                        }
                    });
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ojek_new);
        rootLayout = findViewById(R.id.rootLayout);
        setPickUpContainer = findViewById(R.id.pickUpContainer);
        setDestinationContainer = findViewById(R.id.destinationContainer);
        setPickUpButton = findViewById(R.id.pickUpButton);
        setDestinationButton = findViewById(R.id.destinationButton);
        fiturtext = findViewById(R.id.fiturtext);
        backbtn = findViewById(R.id.back_btn);
        bottomsheet = findViewById(R.id.bottom_sheet);
        rlprogress = findViewById(R.id.rlprogress);
        NameAsal = findViewById(R.id.NameAsal);
        pickUpText = findViewById(R.id.pickUpText);
        NameTujuan = findViewById(R.id.NameTujuan);
        destinationText = findViewById(R.id.destinationText);
        promokode = findViewById(R.id.promocode);
        btnpromo = findViewById(R.id.btnpromo);
        mRecycler = findViewById(R.id.mRecyclerView);
        SetMetode = findViewById(R.id.SetMetode);
        SetTunai = findViewById(R.id.rTunai);
        SetSaldo = findViewById(R.id.rSaldo);
        jarak = findViewById(R.id.distance);
        estimasi = findViewById(R.id.estimasi);
        cost = findViewById(R.id.cost);
        numdiskon = findViewById(R.id.numdiskon);
        diskon = findViewById(R.id.diskon);
        priceText = findViewById(R.id.price);
        btnOrder = findViewById(R.id.btnorder);
        StepLayout2 = findViewById(R.id.Step2);
        StepLayout3 = findViewById(R.id.Step3);
        mDriverRec = findViewById(R.id.mDriverRec);
        closeDriver = findViewById(R.id.close_driver);
        autoPickUpText = findViewById(R.id.autoPickUpText);
        autoDestionationText = findViewById(R.id.autodestinationText);

        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        Places.initialize(getApplicationContext(), MainActivity.apikey);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), MainActivity.apikey);
        }
        autocompleteSessionToken = AutocompleteSessionToken.newInstance();
        placesClient = Places.createClient(getApplicationContext());

        mGeoDataClient = com.google.android.gms.location.places.Places.getGeoDataClient(this);
        mPlaceAutocompleteAdapter = new PlaceAutoCompletedAdapter(this, placesClient, autocompleteSessionToken);

        autoPickUpText.setAdapter(mPlaceAutocompleteAdapter);
        autoPickUpText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
                final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS);
                final FetchPlaceRequest request = FetchPlaceRequest.newInstance(item.getPlaceId(), placeFields);
                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place place = response.getPlace();
                    LatLng latLng = place.getLatLng();
                    if (latLng != null) {
                        Log.e(TAG, "longitude: " + latLng.longitude + " latidude:" + latLng.latitude);
                        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        onPickUp();
                    }
                    autoPickUpText.setText(place.getName());
                    Log.e(TAG, "Place found: " + place.getName() + " latidude:" + latLng.latitude);
                }).addOnFailureListener((exception) -> {
                    final ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + exception.getMessage());
                    final int statusCode = apiException.getStatusCode();
                    // TODO: Handle error with given status code.
                });
            }
        });

        autoDestionationText.setAdapter(mPlaceAutocompleteAdapter);
        autoDestionationText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
                final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS);
                final FetchPlaceRequest request = FetchPlaceRequest.newInstance(item.getPlaceId(), placeFields);
                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place place = response.getPlace();
                    LatLng latLng = place.getLatLng();
                    if (latLng != null) {
                        Log.e(TAG, "longitude: " + latLng.longitude + " latidude:" + latLng.latitude);
                        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        onDestination();
                    }
                    autoDestionationText.setText(place.getName());
                    Log.e(TAG, "Place found: " + place.getName() + " latidude:" + latLng.latitude);
                }).addOnFailureListener((exception) -> {
                    final ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + exception.getMessage());
                    final int statusCode = apiException.getStatusCode();
                    // TODO: Handle error with given status code.
                });
            }
        });

        setPickUpContainer.setVisibility(View.VISIBLE);
        setDestinationContainer.setVisibility(View.GONE);

        driverAvailable = new ArrayList<>();
        pilihdriver= new ArrayList<>();
        driverMarkers = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
//        get intent
        Intent intent = getIntent();
        FITURID = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");
        Log.e("OPEN", "------------------------------" + FITURID);
//        dLatitude = intent.getDoubleExtra("latitude",0);
//        dLongitude = intent.getDoubleExtra("longitude",0);
//        dAlamat = intent.getStringExtra("alamat");
//        NamaAlamat = intent.getStringExtra("namaalamat");
//        NameTujuan.setText(NamaAlamat);

//        set fitur
        if (FITURID != -1)
            designedFitur = LocalStore.get().getFitur(FITURID);
        List<FiturModel> fiturs = LocalStore.get().getAllFitur();
        for (FiturModel fitur : fiturs) {
            Log.e("ID_FITUR", fitur.getIdFitur() + " " + fitur.getFitur() + " " + fitur.getBiayaAkhir() + " " + ICONFITUR + "----" + fitur.getIsPending());
        }
        fitur = String.valueOf(designedFitur.getIdFitur());
        getbiaya = String.valueOf(designedFitur.getBiaya());
        biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
        biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
        icondriver = designedFitur.getIcon_driver();
        maksimum = Long.parseLong(designedFitur.getMaksimumdist());
        Radius = Double.parseDouble(designedFitur.getMaksimumdist());
        fiturtext.setText(designedFitur.getFitur());
        fiturdesc = designedFitur.getKeterangan();
        updateFitur();

        setPickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickUp();
            }
        });

        setDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestination();
            }
        });

        pickUpText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(OjekNewActivity.this, ActivityAmbil.class);
//                i.putExtra("Lat",Latitude);
//                i.putExtra("Lng",Longitude);
//                i.putExtra("radius",Radius);
//                i.putExtra(ActivityAmbil.FORM_VIEW_INDICATOR, 1);
//                startActivityForResult(i, 1);
                setPickUpContainer.setVisibility(View.VISIBLE);
                setDestinationContainer.setVisibility(View.GONE);
                openAutocompleteActivity(1);
            }
        });
        destinationText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(OjekNewActivity.this, ActivityTujuan.class);
//                i.putExtra("Lat",Latitude);
//                i.putExtra("Lng",Longitude);
//                i.putExtra("radius",Radius);
//                i.putExtra(ActivityTujuan.FORM_VIEW_INDICATOR, 2);
//                startActivityForResult(i, 2);
                setDestinationContainer.setVisibility(View.VISIBLE);
                setPickUpContainer.setVisibility(View.GONE);
                openAutocompleteActivity(2);
            }
        });


        btnpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                } catch (Exception ignored) {

                }
                if (promokode.getText().toString().isEmpty()) {
                    Snackbar snackbar = Snackbar.make(rootLayout, "Voucher Belum Di Pilih!", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    snackbar.show();
                } else {
                    promokodedata(promokode.getText().toString());

                }
                promokode.getText().clear();
            }
        });

//        set current location
        sp = new SettingPreference(this);
        Latitude = Double.parseDouble(sp.getSetting()[6]);
        Latitude = Double.parseDouble(sp.getSetting()[7]);
        double picklat = Double.parseDouble(sp.getSetting()[6]);
        double picklng = Double.parseDouble(sp.getSetting()[7]);

        pickUpLatLang = new LatLng(picklat,picklng);
        pickUpText.setText(sp.getSetting()[8]);

        fetchNearDriver(pickUpLatLang);

        closeDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepLayout3.setVisibility(View.GONE);
                StepLayout2.setVisibility(View.VISIBLE);
            }
        });
//        destinationLatLang = new LatLng(dLatitude,dLongitude);
//        destinationText.setText(dAlamat);
//        if(dLatitude == 0 || dLatitude == 0){
//            notif("Alamat Tujuan Tidak Tersedia.");
//            finish();
//        }else{
//
//            try {
//                MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


    }

    private void onPickUp() {
        setDestinationContainer.setVisibility(View.VISIBLE);
        setPickUpContainer.setVisibility(View.GONE);
        if (pickUpMarker != null) pickUpMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        pickUpMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Pick Up")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup)));
        pickUpLatLang = centerPos;
        requestAddress(centerPos, pickUpText);
        fetchNearDriver(pickUpLatLang);
        requestRoute();

    }

    private void onDestination() {

        if (destinationMarker != null) destinationMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        destinationMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Destination")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
        destinationLatLang = centerPos;
        requestAddress(centerPos, destinationText);
        requestRoute();

        setDestinationContainer.setVisibility(View.GONE);
        if (pickUpText.getText().toString().isEmpty()) {
            setPickUpContainer.setVisibility(View.VISIBLE);
        } else {
            setPickUpContainer.setVisibility(View.GONE);
        }

    }

    private void updateDistance(long distance) {
        checkedpaywallet = "0";
        Log.e("CHECKEDWALLET", checkedpaywallet);
        float km = ((float) (distance)) / 1000f;
        mjarak = km;
        String format = String.format(Locale.US, "%.1f", km);
        jarak.setText(format + "Km");
        String biaya = String.valueOf(biayaminimum);
        Log.e("Waduh", biaya);
        long biayaTotal = Utility.fixPembulatan((long) (Double.parseDouble(getbiaya) * km));
        if (biayaTotal < Double.parseDouble(biaya)) {
            this.harga = Long.parseLong(biaya);
            biayaTotal = Long.parseLong(biaya);
        }
        this.harga = biayaTotal;
        final long finalBiayaTotal = biayaTotal;
        Log.e("Kabeh", String.valueOf(biayaTotal));
        String totalbiaya = String.valueOf(finalBiayaTotal);
        Utility.currencyTXT(cost, totalbiaya, this);
        Utility.currencyTXT(priceText, totalbiaya, this);
        //  TampilPoint(finalBiayaTotal);
        long saldokini = Long.parseLong(saldoWallet);
        //opsi
        if (saldokini < (biayaTotal - (harga * Double.parseDouble(biayaakhir)))) {
            SetSaldo.setEnabled(false);
            SetMetode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int id) {
                    switch (id){
                        case R.id.rTunai:
                            String totalbiaya = String.valueOf(finalBiayaTotal - promocode);
                            //   TampilPoint(mTotalBiaya);
                            Utility.currencyTXT(priceText, totalbiaya, OjekNewActivity.this);
                            String diskontotal = String.valueOf(promocode);
                            Utility.currencyTXT(diskon, diskontotal, OjekNewActivity.this);
                            numdiskon.setText(diskontotal);
                            checkedpaywallet = "0";
                            Log.e("CHECKEDWALLET", checkedpaywallet);
                            break;
                    }
                }
            });
        }else{
            SetSaldo.setEnabled(true);
            final long finalBiayaTotal1 = biayaTotal;
            SetMetode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int id) {
                    String totalbiaya = "0";
                    switch (id){
                        case R.id.rTunai:
                            String diskontotal = String.valueOf(promocode);
                            totalbiaya = String.valueOf(finalBiayaTotal - promocode);
                            // TampilPoint(mTotalBiaya);
                            Utility.currencyTXT(priceText, totalbiaya, OjekNewActivity.this);
                            Utility.currencyTXT(diskon, diskontotal, OjekNewActivity.this);
                            numdiskon.setText(diskontotal);
                            checkedpaywallet = "0";
                            Log.e("CHECKEDWALLET", checkedpaywallet);
                            break;
                        case R.id.rSaldo:
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            long kalkulasi = Utility.fixPembulatan(diskonwallet);
                            String totalwallet = String.valueOf(kalkulasi + promocode);
                            Utility.currencyTXT(diskon, totalwallet, OjekNewActivity.this);
                            totalbiaya = String.valueOf(finalBiayaTotal1 - (kalkulasi + promocode));
                            // TampilPoint(mTotalBiaya);
                            Utility.currencyTXT(priceText, totalbiaya, OjekNewActivity.this);
                            numdiskon.setText(totalwallet);
                            checkedpaywallet = "1";
                            Log.e("CHECKEDWALLET", checkedpaywallet);
                            break;
                    }
                }
            });

        }
        btnOrder.setVisibility(View.VISIBLE);
    }

    private void driverterdekat(LatLng latLng) {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(latLng.latitude);
        param.setLongitude(latLng.longitude);
        param.setFitur(fitur);
        param.setStatus("1");
        service.driverTerdekat(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    driverAvailable = Objects.requireNonNull(response.body()).getData();
                    for (int i = 0; i < driverAvailable.size(); i++) {
                        if(driverAvailable.get(i).getStatus().equals("1")){
                            LinearLayoutManager layoutManager = new LinearLayoutManager(OjekNewActivity.this, LinearLayoutManager.VERTICAL, false);
                            mDriverRec.setLayoutManager(layoutManager);
                            mDriverRec.setNestedScrollingEnabled(false);
                            dAdapter = new ListDriverAdapter(driverAvailable,OjekNewActivity.this);
                            dAdapter.setOnItemClickListener(new ListDriverClick() {
                                @Override
                                public void onItemClick(DriverModel item) {
                                    KlikPesan(item.getRegId(),0);
                                    Log.e("DriverKlik",item.getNamaDriver());
                                }
                            });
                            mDriverRec.setAdapter(dAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void fetchNearDriver(LatLng latLng) {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(latLng.latitude);
        param.setLongitude(latLng.longitude);
        param.setFitur(fitur);
        param.setStatus("1");
        service.driverTerdekat(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    driverAvailable = Objects.requireNonNull(response.body()).getData();
                    createMarker();

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void updateFitur() {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        if (driverMarkers != null) {
            for (Marker m : driverMarkers) {
                m.remove();
            }
            driverMarkers.clear();
        }
    }

    private void notif(String pesan){
        Snackbar snackbar = Snackbar.make(rootLayout, pesan, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        snackbar.show();
    }

    //----------------------------------------- Promo Kode -----------------------------------------
    @SuppressLint("SetTextI18n")
    private void DaftarPromo() {
        final User user = BaseApp.getInstance(this).getLoginUser();
        FiturPromoRequest request = new FiturPromoRequest();
        request.setFitur(fitur);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.FiturPromo(request).enqueue(new Callback<PromoResponse>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponse> call, @NonNull Response<PromoResponse> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        mItems = response.body().getData();
                        for(int i = 0; i < mItems.size(); i++){
                            mAdapter = new FiturPromoAdapter(mItems,OjekNewActivity.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(OjekNewActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            mRecycler.setLayoutManager(layoutManager);
                            mRecycler.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                            mAdapter.setClickListener(new FiturPromoAdapter.ClickListener() {
                                @Override
                                public void click(VoucherModel promomodel) {
                                    promokodedata(promomodel.getKode());
                                    com.rcdriver.cs.utils.Log.d("PromoData", promomodel.getKode());
                                }
                            });
                        }
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(rootLayout, "error!", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PromoResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Snackbar snackbar = Snackbar.make(rootLayout, "error!", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                snackbar.show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void promokodedata(String PromoKode) {
        final User user = BaseApp.getInstance(this).getLoginUser();
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(fitur);
        request.setCode(PromoKode);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponseJson> call, @NonNull Response<PromoResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        if (response.body().getType().equals("persen")) {
                            promocode = Utility.fixPembulatan((Long.parseLong(response.body().getNominal()) * harga) / 100);
                        } else {
                            promocode = Utility.fixPembulatan(Long.parseLong(response.body().getNominal()));
                        }
                        Log.e("", String.valueOf(promocode));
                        Snackbar snackbar = Snackbar.make(rootLayout, "Promo Berhasil Digunakan.", Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                        snackbar.show();
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
                            Utility.currencyTXT(priceText, totalbiaya, OjekNewActivity.this);
                            Utility.currencyTXT(diskon, diskontotal, OjekNewActivity.this);
                            numdiskon.setText(diskontotal);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, OjekNewActivity.this);
                            Utility.currencyTXT(diskon, diskontotal, OjekNewActivity.this);
                            numdiskon.setText(diskontotal);
                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(rootLayout, "Kode Promo Tidak Tersedia!", Snackbar.LENGTH_LONG);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                        snackbar.show();
                        promocode = 0;
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
                            Utility.currencyTXT(priceText, totalbiaya, OjekNewActivity.this);
                            Utility.currencyTXT(diskon, diskontotal, OjekNewActivity.this);
                            numdiskon.setText(diskontotal);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, OjekNewActivity.this);
                            Utility.currencyTXT(diskon, diskontotal, OjekNewActivity.this);
                            numdiskon.setText(diskontotal);
                        }

                    }
                } else {
                    Snackbar snackbar = Snackbar.make(rootLayout, "Tidak Ada Promo Tersedia", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PromoResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                Snackbar snackbar = Snackbar.make(rootLayout, "error", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                snackbar.show();
            }
        });
    }

    //-------------------------------------- Manual Order ---------------------------------
    private void KlikPesan(String token,int auto) {
        User loginUser = BaseApp.getInstance(OjekNewActivity.this).getLoginUser();
        if (checkedpaywallet.equals("1")) {
            RideCarRequestJson param = new RideCarRequestJson();
            User userLogin = BaseApp.getInstance(this).getLoginUser();
            param.setIdPelanggan(userLogin.getId());
            param.setOrderFitur(fitur);
            param.setStartLatitude(pickUpLatLang.latitude);
            param.setStartLongitude(pickUpLatLang.longitude);
            param.setEndLatitude(destinationLatLang.latitude);
            param.setEndLongitude(destinationLatLang.longitude);
            param.setJarak(mjarak);
            param.setEstimasi(fiturtext.getText().toString());
            param.setHarga(this.harga);
            param.setKreditpromo(numdiskon.getText().toString());
            param.setAlamatAsal(pickUpText.getText().toString());
            param.setAlamatTujuan(destinationText.getText().toString());
            param.setPakaiWallet(1);
            RequestOrederan(param, token,1,auto);
        } else if(checkedpaywallet.equals("0")) {
            RideCarRequestJson param = new RideCarRequestJson();
            User userLogin = BaseApp.getInstance(this).getLoginUser();
            param.setIdPelanggan(userLogin.getId());
            param.setOrderFitur(fitur);
            param.setStartLatitude(pickUpLatLang.latitude);
            param.setStartLongitude(pickUpLatLang.longitude);
            param.setEndLatitude(destinationLatLang.latitude);
            param.setEndLongitude(destinationLatLang.longitude);
            param.setJarak(mjarak);
            param.setEstimasi(fiturtext.getText().toString());
            param.setHarga(this.harga);
            param.setKreditpromo(numdiskon.getText().toString());
            param.setAlamatAsal(pickUpText.getText().toString());
            param.setAlamatTujuan(destinationText.getText().toString());
            param.setPakaiWallet(0);
            RequestOrederan(param, token,1,auto);
        }else{
            RideCarRequestJson param = new RideCarRequestJson();
            User userLogin = BaseApp.getInstance(this).getLoginUser();
            param.setIdPelanggan(userLogin.getId());
            param.setOrderFitur(fitur);
            param.setStartLatitude(pickUpLatLang.latitude);
            param.setStartLongitude(pickUpLatLang.longitude);
            param.setEndLatitude(destinationLatLang.latitude);
            param.setEndLongitude(destinationLatLang.longitude);
            param.setJarak(mjarak);
            param.setEstimasi(fiturtext.getText().toString());
            param.setHarga(this.harga);
            param.setKreditpromo(numdiskon.getText().toString());
            param.setAlamatAsal(pickUpText.getText().toString());
            param.setAlamatTujuan(destinationText.getText().toString());
            param.setPakaiWallet(0);
            RequestOrederan(param, token,1,auto);
        }
    }
    private void RequestOrederan(RideCarRequestJson param, String token,int wallet,int auto) {
        Log.d("CekMetode", String.valueOf(param.isPakaiWallet()));
        Intent intent = new Intent(OjekNewActivity.this, RideOrder.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("idpelanggan", param.getIdPelanggan());
        intent.putExtra("fitur", fitur);
        intent.putExtra("idfitur",FITURID);
        intent.putExtra("ikon",ICONFITUR);
        intent.putExtra("fiturdesk",fiturdesc);
        intent.putExtra("biaya",cost.getText().toString());
        intent.putExtra("picklat", param.getStartLatitude());
        intent.putExtra("picklng", param.getStartLongitude());
        intent.putExtra("destlat", param.getEndLatitude());
        intent.putExtra("destlng", param.getEndLongitude());
        intent.putExtra("jarak", param.getJarak());
        intent.putExtra("estimasi", fiturtext.getText().toString());
        intent.putExtra("harga", this.harga);
        intent.putExtra("diskon", numdiskon.getText().toString());
        intent.putExtra("pickaddress", pickUpText.getText().toString());
        intent.putExtra("destkaddress", destinationText.getText().toString());
        intent.putExtra("pakaisaldo", param.isPakaiWallet());
        intent.putExtra("token", token);
        intent.putExtra("auto",auto);
        intent.putExtra("namaalamat",pickUpText.getText().toString());
        startActivity(intent);
        finish();
    }

    private void updateLastLocation(boolean move) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (lastKnownLocation != null) {
            if (move) {
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 15f)
                );

//                gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
            }
            LatLng mlatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            fetchNearDriver(mlatLng);
        }
    }

    private void updateLineDestination(String json) {
        Directions directions = new Directions(OjekNewActivity.this);
        try {
            List<Route> routes = directions.parse(json);
            if (directionLine != null) directionLine.remove();
            if (routes.size() > 0) {
                directionLine = gMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(OjekNewActivity.this, R.color.default_badge_background_color))
                        .width(8));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLastLocation(true);
    }

    @Override
    public void onConnectionSuspended(@NonNull int i) {
        updateLastLocation(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        updateLastLocation(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(TAG, "sampai sini");
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(true);

        isMapReady = true;
//        gMap.setOnMarkerClickListener(this);

        updateLastLocation(true);
    }

    Timer timer = new Timer();
    private final Runnable updateDriverRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(pickUpLatLang != null){
                            fetchNearDriver(pickUpLatLang);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (NetworkManager.isConnectToInternet(OjekNewActivity.this)) {
                                try {
                                    if(pickUpLatLang != null){
                                        fetchNearDriver(pickUpLatLang);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, 0, 4000);
                }
            }).start();
        }
    };

    private void startIsDriver() {
        handler = new Handler();
        handler.postDelayed(updateDriverRunnable, 4000);
    }

    private void stopIsDriver() {
        handler.removeCallbacks(updateDriverRunnable);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
        startIsDriver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if(userLogin.getWalletSaldo() == 0){
            saldoWallet = sp.getSetting()[5];
            Log.e("MySaldo", saldoWallet);
        }else{
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
            Log.e("MySaldo", saldoWallet);
        }
        if(pickUpLatLang != null){
            startIsDriver();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        stopIsDriver();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            Log.e(TAG, "lat: " + Objects.requireNonNull(place.getLatLng()).latitude);
            if(requestCode == 1){
                pickUpText.setText(place.getAddress());
                LatLng latLng = place.getLatLng();

                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(
                            new LatLng(latLng.latitude, latLng.longitude))
                    );
                    onPickUp();
                }
            }else {
                destinationText.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(
                            new LatLng(latLng.latitude, latLng.longitude))
                    );
                    onDestination();
                }
            }

        }


    }

//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        Log.e("MARKER", marker.getTitle().toString());
//        if(marker.getTitle().equalsIgnoreCase("Pick Up")){
//            Intent i = new Intent(OjekNewActivity.this, ActivitySetHome.class);
//            i.putExtra("Lat",Latitude);
//            i.putExtra("Lng",Longitude);
//            i.putExtra("radius",Radius);
//            i.putExtra(ActivityAmbil.FORM_VIEW_INDICATOR, 1);
//            startActivityForResult(i, 1);
//        }else if(marker.getTitle().equalsIgnoreCase("Destination")){
//            Intent i = new Intent(OjekNewActivity.this, ActivitySetHome.class);
//            i.putExtra("Lat",Latitude);
//            i.putExtra("Lng",Longitude);
//            i.putExtra("radius",Radius);
//            i.putExtra(ActivityTujuan.FORM_VIEW_INDICATOR, 2);
//            startActivityForResult(i, 2);
//        }
//
//        return true;
//    }

    private void createMarker() {
        if (!driverAvailable.isEmpty()) {
            for (Marker m : driverMarkers) {
                m.remove();
            }

            driverMarkers.clear();
            for (DriverModel driver : driverAvailable) {
                float nextFloat = (new Random().nextFloat() * 199.0f) - 0.045410156f;
                LatLng currentDriverPos = new LatLng(driver.getLatitude(), driver.getLongitude());

                if (icondriver.equals("1")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icmotor))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("2")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmap))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("3")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("4")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("5")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hatchback))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("6")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.suv))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("7")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.van))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("8")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("9")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bajaj))
                                    .rotation(nextFloat)));


                }
            }
        }
    }

    private void requestRoute() {
        if (pickUpLatLang != null && destinationLatLang != null) {
            rlprogress.setVisibility(View.VISIBLE);
//            textprogress.setText(getString(R.string.waiting_pleaseWait));
            MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
        }
    }

    private void requestAddress(LatLng latlang, final TextView textView) {
        if (latlang != null) {
            MapDirectionAPI.getAddress(latlang).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull okhttp3.Call call, @NonNull final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String json = Objects.requireNonNull(response.body()).string();
                        Log.e("JSON", json.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject Jobject = new JSONObject(json);
                                    JSONArray Jarray = Jobject.getJSONArray("results");
                                    JSONObject userdata = Jarray.getJSONObject(0);
                                    String address = userdata.getString("formatted_address");
                                    textView.setText(address);
                                    Log.e("TESTER", userdata.getString("formatted_address"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void openAutocompleteActivity(int request_code) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, request_code);

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            Log.e("errr", placeId);

            /*
             Issue a request to the Places Geo Data Client to retrieve a Place object with
             additional details about the place.
              */
            Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
            placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);
        }
    };

    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback = new OnCompleteListener<PlaceBufferResponse>() {
        @Override
        public void onComplete(Task<PlaceBufferResponse> task) {
            try {
                PlaceBufferResponse places = task.getResult();

                // Get the Place object from the buffer.
                final com.google.android.gms.location.places.Place place = places.get(0);
                autoPickUpText.setText(place.getAddress().toString());
//                locationDataTv.setText("Latitude : "+String.valueOf(place.getLatLng().latitude)+"\n Longitude : "+String.valueOf(place.getLatLng().longitude));
                places.release();
            } catch (RuntimeRemoteException e) {
                // Request did not complete successfully
                return;
            }
        }
    };

    private void getLocationFromPlaceId(String placeId, ResultCallback<PlaceBuffer> callback) {
        com.google.android.gms.location.places.Places.GeoDataApi.getPlaceById(googleApiClient, placeId).setResultCallback(callback);
    }
}