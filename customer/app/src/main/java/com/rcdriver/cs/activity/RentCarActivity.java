package com.rcdriver.cs.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.rcdriver.cs.activity.payment.TopupSaldoActivity;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.json.CheckStatusTransaksiRequest;
import com.rcdriver.cs.json.CheckStatusTransaksiResponse;
import com.rcdriver.cs.json.GetNearRideCarRequestJson;
import com.rcdriver.cs.json.GetNearRideCarResponseJson;
import com.rcdriver.cs.json.PromoRequestJson;
import com.rcdriver.cs.json.PromoResponseJson;
import com.rcdriver.cs.json.RideCarRequestJson;
import com.rcdriver.cs.json.RideCarResponseJson;
import com.rcdriver.cs.json.fcm.DriverRequest;
import com.rcdriver.cs.json.fcm.DriverResponse;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.TransaksiModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.MapDirectionAPI;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rcdriver.cs.json.fcm.FCMType.ORDER;

/**
 * Created by Maswend Team on 10/26/2019.
 */

public class RentCarActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String FITUR_KEY = "FiturKey";
    private static final String TAG = "RentCarActivity";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    public static String Warna;
    String ICONFITUR;
    TransaksiModel transaksi;
    Thread thread;
    boolean threadRun = true;
    Context context = RentCarActivity.this;
    LinearLayout setPickUpContainer;
    Button setPickUpButton;
    TextView pickUpText;
    LinearLayout bottomsheet;
    LinearLayout detail;
    TextView priceText;
    TextView topUp;
    Button orderButton;

    TextView layanan;
    TextView layanandesk;
    TextView cost;
    TextView diskontext;
    TextView diskon;
    TextView saldotext;
    ImageButton checkedcash;
    ImageButton checkedwallet;
    TextView cashpayment;
    TextView walletpayment;
    LinearLayout llcheckedwallet;
    LinearLayout llcheckedcash;
    ImageView backbtn;
    RelativeLayout rlprogress;
    RelativeLayout rlnotif;
    TextView textnotif;
    TextView textprogress;
    TextView fiturtext;
    Button enamjam;
    Button duabelasjam;
    Button satuhari;
    EditText promokode;
    Button btnpromo;
    TextView promonotif;

    ProgressBar Bar;
    String fitur, getbiaya, biayaminimum, biayaakhir, icondrver;
    private Location mLastLocation;
    private DriverRequest request;
    private GoogleMap gMap;
    private GoogleApiClient googleApiClient;
    private Location lastKnownLocation;
    private LatLng pickUpLatLang;
    private Marker pickUpMarker;
    private List<DriverModel> driverAvailable;
    private List<Marker> driverMarkers;
    private Realm realm;
    private FiturModel designedFitur;
    private double jarak;
    private long harga, promocode;
    private String saldoWallet;
    private String checkedpaywallet;
    private boolean isMapReady = false;
    private SettingPreference sp;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        setPickUpContainer = findViewById(R.id.pickUpContainer);
        setPickUpButton = findViewById(R.id.pickUpButton);
        pickUpText = findViewById(R.id.pickUpText);
        bottomsheet = findViewById(R.id.bottom_sheet);
        detail = findViewById(R.id.detail);
        priceText = findViewById(R.id.price);
        topUp = findViewById(R.id.topUp);
        orderButton = findViewById(R.id.order);
        layanan = findViewById(R.id.layanan);
        layanandesk = findViewById(R.id.layanandes);
        cost = findViewById(R.id.cost);
        diskontext = findViewById(R.id.ketsaldo);
        diskon = findViewById(R.id.diskon);
        saldotext = findViewById(R.id.saldo);
        checkedcash = findViewById(R.id.checkedcash);
        checkedwallet = findViewById(R.id.checkedwallet);
        cashpayment = findViewById(R.id.cashPayment);
        walletpayment = findViewById(R.id.walletpayment);
        llcheckedwallet = findViewById(R.id.llcheckedwallet);
        llcheckedcash = findViewById(R.id.llcheckedcash);
        backbtn = findViewById(R.id.back_btn);
        rlprogress = findViewById(R.id.rlprogress);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        textprogress = findViewById(R.id.textprogress);
        fiturtext = findViewById(R.id.fitur);
        enamjam = findViewById(R.id.enamjam);
        duabelasjam = findViewById(R.id.duabelasjam);
        satuhari = findViewById(R.id.satuhari);
        promokode = findViewById(R.id.promocode);
        btnpromo = findViewById(R.id.btnpromo);
        promonotif = findViewById(R.id.promonotif);
        Bar = findViewById(R.id.bar);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        promocode = 0;
        updateFitur();
        setPickUpContainer.setVisibility(View.VISIBLE);
        sp = new SettingPreference(this);
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if(userLogin.getWalletSaldo() == 0){
            saldoWallet = sp.getSetting()[5];
        }else{
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        }
        int colorCodeDark = getResources().getColor(R.color.colorPrimary);
//        FiturBG.setColorFilter(getResources().getColor(R.color.colorPrimary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnpromo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            orderButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            Bar.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));
        }
        setPickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickUp();
            }
        });

//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TopupSaldoActivity.class));
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickUpText.getText().toString().isEmpty()) {
                    notif("Location cant be empty!");
                } else {
                    onOrderButton();
                }
            }
        });

        pickUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickUpContainer.setVisibility(View.VISIBLE);
                openAutocompleteActivity();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        driverAvailable = new ArrayList<>();
        driverMarkers = new ArrayList<>();

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        int fiturId = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");
        Log.e("FITUR_ID", fiturId + "");
        if (fiturId != -1)
            designedFitur = realm.where(FiturModel.class).equalTo("idFitur", fiturId).findFirst();

        RealmResults<FiturModel> fiturs = realm.where(FiturModel.class).findAll();

        for (FiturModel fitur : fiturs) {
            Log.e("ID_FITUR", fitur.getIdFitur() + " " + fitur.getFitur() + " " + fitur.getBiayaAkhir() + " " + ICONFITUR);
        }
        fitur = String.valueOf(designedFitur.getIdFitur());
        getbiaya = String.valueOf(designedFitur.getBiaya());
        biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
        biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
        icondrver = designedFitur.getIcon_driver();
        layanan.setText(designedFitur.getFitur());
        layanandesk.setText(designedFitur.getKeterangan());
        enamjam.setSelected(true);
        duabelasjam.setSelected(false);
        satuhari.setSelected(false);
        updateDistance(1);

        enamjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enamjam.setSelected(true);
                duabelasjam.setSelected(false);
                satuhari.setSelected(false);
                updateDistance(1);
            }
        });

        duabelasjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enamjam.setSelected(false);
                duabelasjam.setSelected(true);
                satuhari.setSelected(false);
                updateDistance(2);
            }
        });

        satuhari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enamjam.setSelected(false);
                duabelasjam.setSelected(false);
                satuhari.setSelected(true);
                updateDistance(3);
            }
        });

        diskontext.setText("Discount " + designedFitur.getDiskon() + " with Wallet");
//        PicassoTrustAll.getInstance(this)
//                .load(Constants.IMAGESFITUR + ICONFITUR)
//                .placeholder(R.drawable.logo)
//                .resize(100, 100)
//                .into(icon);


        btnpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                } catch (Exception ignored) {

                }
                if (promokode.getText().toString().isEmpty()) {
                    notif("Promo code cant be empty!");
                } else {
                    promokodedata();
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void promokodedata() {
        btnpromo.setEnabled(false);
        btnpromo.setText("Wait...");
        final User user = BaseApp.getInstance(this).getLoginUser();
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(fitur);
        request.setCode(promokode.getText().toString());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<PromoResponseJson> call, @NonNull Response<PromoResponseJson> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        if (response.body().getType().equals("persen")) {
                            promocode = (Long.parseLong(response.body().getNominal()) * harga) / 100;
                        } else {
                            promocode = Long.parseLong(response.body().getNominal());
                        }
                        Log.e("", String.valueOf(promocode));
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RentCarActivity.this);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RentCarActivity.this);
                        }
                    } else {
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        notif("promo code not available!");
                        promocode = 0;
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RentCarActivity.this);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, RentCarActivity.this);
                        }
                    }
                } else {
                    notif("error!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PromoResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("error");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateDistance(long jam) {

        int colorCodeDark = getResources().getColor(R.color.colorPrimary);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        detail.setVisibility(View.VISIBLE);
        orderButton.setVisibility(View.VISIBLE);
        fiturtext.setText(jam + " hr");
        Utility.currencyTXT(diskon, String.valueOf(promocode), RentCarActivity.this);
        checkedpaywallet = "0";
        Log.e("CHECKEDWALLET", checkedpaywallet);
        checkedcash.setSelected(true);
        checkedwallet.setSelected(false);
        cashpayment.setTextColor(colorCodeDark);
        walletpayment.setTextColor(getResources().getColor(R.color.gray));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkedcash.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
            checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        }
        String biaya = String.valueOf(biayaminimum);
        long biayaTotal = (long) (Double.parseDouble(getbiaya) * jam);
        if (biayaTotal < Double.parseDouble(biayaminimum)) {
            this.harga = Long.parseLong(biayaminimum);
            biayaTotal = Long.parseLong(biayaminimum);
            Utility.currencyTXT(cost, biaya, this);
        } else {
            Utility.currencyTXT(cost, getbiaya, this);
        }
        this.harga = biayaTotal;

        final long finalBiayaTotal = biayaTotal;
        String totalbiaya = String.valueOf(finalBiayaTotal - promocode);
        Utility.currencyTXT(priceText, totalbiaya, this);

        long saldokini = Long.parseLong(saldoWallet);
        if (saldokini < (biayaTotal - (harga * Double.parseDouble(biayaakhir)))) {
            llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String totalbiaya = String.valueOf(finalBiayaTotal - promocode);
                    Utility.currencyTXT(priceText, totalbiaya, getBaseContext());
                    Utility.currencyTXT(diskon, String.valueOf(promocode), RentCarActivity.this);
                    checkedcash.setSelected(true);
                    checkedwallet.setSelected(false);
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    cashpayment.setTextColor(colorCodeDark);
                    walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedcash.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
        } else {
            llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String totalbiaya = String.valueOf(finalBiayaTotal - promocode);
                    Utility.currencyTXT(priceText, totalbiaya, context);
                    Utility.currencyTXT(diskon, String.valueOf(promocode), RentCarActivity.this);
                    checkedcash.setSelected(true);
                    checkedwallet.setSelected(false);
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    cashpayment.setTextColor(colorCodeDark);
                    walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedcash.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });

            final long finalBiayaTotal1 = biayaTotal;
            llcheckedwallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                    String totalwallet = String.valueOf(diskonwallet + promocode);
                    Utility.currencyTXT(diskon, totalwallet, context);
                    String totalbiaya = String.valueOf(finalBiayaTotal1 - (diskonwallet + promocode));
                    Utility.currencyTXT(priceText, totalbiaya, context);
                    checkedcash.setSelected(false);
                    checkedwallet.setSelected(true);
                    checkedpaywallet = "1";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    walletpayment.setTextColor(colorCodeDark);
                    cashpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedwallet.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
        }

    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void openAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                pickUpText.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(latLng.latitude, latLng.longitude), 15f)
                    );
                    onPickUp();
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLastLocation(true);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (mLastLocation != null) {
            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            String sAlamat = getCompleteAddressString(latLng);
            pickUpText.setText(sAlamat);
        }
        updateLastLocation(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
        updateLastLocation(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        updateLastLocation(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(true);
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        isMapReady = true;

        updateLastLocation(true);
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

                gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
            }

            fetchNearDriver(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        }
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
        if (isMapReady) updateLastLocation(false);
    }

    private void onPickUp() {
        setPickUpContainer.setVisibility(View.GONE);
        if (pickUpMarker != null) pickUpMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        pickUpMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Pick Up")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_me)));
        pickUpLatLang = centerPos;
        textprogress.setVisibility(View.VISIBLE);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        requestAddress(centerPos, pickUpText);
        fetchNearDriver(pickUpLatLang.latitude, pickUpLatLang.longitude);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void fetchNearDriver(double latitude, double longitude) {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        if (driverMarkers != null) {
            for (Marker m : driverMarkers) {
                m.remove();
            }
            driverMarkers.clear();
        }
        if (lastKnownLocation != null) {
            User loginUser = BaseApp.getInstance(this).getLoginUser();

            BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
            param.setLatitude(latitude);
            param.setLongitude(longitude);
            param.setFitur(fitur);

            service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                    if (response.isSuccessful()) {
                        driverAvailable = Objects.requireNonNull(response.body()).getData();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

                }
            });
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


    private void onOrderButton() {
        if (checkedpaywallet.equals("1")) {
            if (driverAvailable.isEmpty()) {
                notif("Sorry, there are no drivers around you.");
            } else {
                RideCarRequestJson param = new RideCarRequestJson();
                User userLogin = BaseApp.getInstance(this).getLoginUser();
                param.setIdPelanggan(userLogin.getId());
                param.setOrderFitur(fitur);
                param.setStartLatitude(pickUpLatLang.latitude);
                param.setStartLongitude(pickUpLatLang.longitude);
                param.setEndLatitude(0);
                param.setEndLongitude(0);
                param.setJarak(this.jarak);
                param.setHarga(this.harga);
                param.setEstimasi(fiturtext.getText().toString());
                param.setKreditpromo(String.valueOf(Double.parseDouble(biayaakhir) * this.harga));
                param.setAlamatAsal(pickUpText.getText().toString());
                param.setAlamatTujuan("");
                param.setPakaiWallet(1);
                sendRequestTransaksi(param, driverAvailable);
            }
        } else {
            if (driverAvailable.isEmpty()) {
                notif("Sorry, there are no drivers around you.");
            } else {
                RideCarRequestJson param = new RideCarRequestJson();
                User userLogin = BaseApp.getInstance(this).getLoginUser();
                param.setIdPelanggan(userLogin.getId());
                param.setOrderFitur(fitur);
                param.setStartLatitude(pickUpLatLang.latitude);
                param.setStartLongitude(pickUpLatLang.longitude);
                param.setEndLatitude(0);
                param.setEndLongitude(0);
                param.setJarak(this.jarak);
                param.setHarga(this.harga);
                param.setEstimasi(fiturtext.getText().toString());
                param.setKreditpromo("0");
                param.setAlamatAsal(pickUpText.getText().toString());
                param.setAlamatTujuan("");
                param.setPakaiWallet(0);

                sendRequestTransaksi(param, driverAvailable);
            }
        }
    }

    private void sendRequestTransaksi(RideCarRequestJson param, final List<DriverModel> driverList) {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.requestTransaksi(param).enqueue(new Callback<RideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RideCarResponseJson> call, @NonNull Response<RideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    buildDriverRequest(Objects.requireNonNull(response.body()));
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < driverList.size(); i++) {
                                fcmBroadcast(i, driverList);
                            }

                            try {
                                Thread.sleep(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (threadRun) {
                                CheckStatusTransaksiRequest param = new CheckStatusTransaksiRequest();
                                param.setIdTransaksi(transaksi.getId());
                                service.checkStatusTransaksi(param).enqueue(new Callback<CheckStatusTransaksiResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Response<CheckStatusTransaksiResponse> response) {
                                        if (response.isSuccessful()) {
                                            CheckStatusTransaksiResponse checkStatus = response.body();
                                            if (!Objects.requireNonNull(checkStatus).isStatus()) {
                                                notif("Driver not found!");
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        notif("Driver not found!");
                                                    }
                                                });

                                                new Handler().postDelayed(new Runnable() {
                                                    public void run() {
                                                        finish();
                                                    }
                                                }, 3000);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Throwable t) {
                                        notif("Driver not found!");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                notif("Driver not found!");
                                            }
                                        });

                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                finish();
                                            }
                                        }, 3000);

                                    }
                                });
                            }

                        }
                    });
                    thread.start();


                }
            }

            @Override
            public void onFailure(@NonNull Call<RideCarResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("Your account has a problem, please contact customer service!");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 3000);
            }
        });
    }

    private void buildDriverRequest(RideCarResponseJson response) {
        transaksi = response.getData().get(0);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (request == null) {
            request = new DriverRequest();
            request.setIdTransaksi(transaksi.getId());
            request.setIdPelanggan(transaksi.getIdPelanggan());
            request.setRegIdPelanggan(loginUser.getToken());
            request.setOrderFitur(designedFitur.getHome());
            request.setStartLatitude(transaksi.getStartLatitude());
            request.setStartLongitude(transaksi.getStartLongitude());
            request.setEndLatitude(transaksi.getEndLatitude());
            request.setEndLongitude(transaksi.getEndLongitude());
            request.setJarak(transaksi.getJarak());
            request.setHarga(transaksi.getHarga());
            request.setWaktuOrder(transaksi.getWaktuOrder());
            request.setAlamatAsal(transaksi.getAlamatAsal());
            request.setAlamatTujuan(transaksi.getAlamatTujuan());
            request.setKodePromo(transaksi.getKodePromo());
            request.setKreditPromo(transaksi.getKreditPromo());
            request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
            request.setEstimasi(transaksi.getEstimasi());
            request.setLayanan(layanan.getText().toString());
            request.setLayanandesc(layanandesk.getText().toString());
            request.setIcon(ICONFITUR);
            request.setBiaya(cost.getText().toString());


            String namaLengkap = String.format("%s", loginUser.getFullnama());
            request.setNamaPelanggan(namaLengkap);
            request.setTelepon(loginUser.getNoTelepon());
            request.setType(ORDER);
        }
    }

    private void fcmBroadcast(int index, List<DriverModel> driverList) {
        DriverModel driverToSend = driverList.get(index);
        request.setTime_accept(new Date().getTime() + "");
        final User login = BaseApp.getInstance(context).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(driverToSend.getRegId());
            param.setData(request);
            service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                    if (response.isSuccessful()) {
                        android.util.Log.d("ResultFCM", response.body().getMessage());
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("sukses")) {
                            android.util.Log.d("ResultFCM", response.body().getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<FcmResponse> call, @NonNull Throwable t) {
                    android.util.Log.e("TestFCM", t.getMessage());
                }
            });
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        Log.e("DRIVER RESPONSE (W)", response.getResponse() + " " + response.getId() + " " + response.getIdTransaksi());
        if (response.getResponse().equalsIgnoreCase(DriverResponse.ACCEPT) || response.getResponse().equals("3") || response.getResponse().equals("4")) {
            runOnUiThread(new Runnable() {
                public void run() {
                    threadRun = false;
                    for (DriverModel cDriver : driverAvailable) {
                        if (cDriver.getId().equals(response.getId())) {


                            Intent intent = new Intent(RentCarActivity.this, ActivityProgress.class);
                            intent.putExtra("Working", "true");
                            intent.putExtra("id_driver", cDriver.getId());
                            intent.putExtra("pakai_wallet", request.isPakaiWallet());
                            intent.putExtra("id_transaksi", request.getIdTransaksi());
                            intent.putExtra("response", "2");
                            startActivity(intent);
                            DriverResponse response = new DriverResponse();
                            response.setId("");
                            response.setIdTransaksi("");
                            response.setResponse("");
                            EventBus.getDefault().postSticky(response);
                            finish();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if(userLogin.getWalletSaldo() == 0){
            saldoWallet = sp.getSetting()[5];
            Utility.currencyTXT(saldotext, saldoWallet, this);
        }else{
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
            Utility.currencyTXT(saldotext, saldoWallet, this);
        }
    }


    public void setJarak(double jarak) {
        this.jarak = jarak;
    }

    //----------------------------------------------------------------------------------------------
    private String getCompleteAddressString(LatLng latLng) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("Alamat", strReturnedAddress.toString() + "");
            } else {
                Log.e("Alamat", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Alamat", "Canont get Address!");
        }
        return strAdd;
    }
}
