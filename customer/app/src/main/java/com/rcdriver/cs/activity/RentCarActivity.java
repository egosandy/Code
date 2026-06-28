package com.rcdriver.cs.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.payment.TopupSaldoActivity;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.databinding.ActivityRentBinding;
import com.rcdriver.cs.json.CheckStatusTransaksiRequest;
import com.rcdriver.cs.json.CheckStatusTransaksiResponse;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.GetNearRideCarRequestJson;
import com.rcdriver.cs.json.GetNearRideCarResponseJson;
import com.rcdriver.cs.json.PromoRequestJson;
import com.rcdriver.cs.json.PromoResponseJson;
import com.rcdriver.cs.json.RideCarRequestJson;
import com.rcdriver.cs.json.RideCarResponseJson;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.json.fcm.DriverRequest;
import com.rcdriver.cs.json.fcm.DriverResponse;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.TransaksiModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.MapDirectionAPI;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.google.android.libraries.places.api.Places;
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

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rcdriver.cs.json.fcm.FCMType.ORDER;


public class RentCarActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String FITUR_KEY = "FiturKey";
    private static final String TAG = "RentCarActivity";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    String ICONFITUR;
    TransaksiModel transaksi;
    Thread thread;
    boolean threadRun = true;
    Context context = RentCarActivity.this;
    private ActivityRentBinding binding;
    String fitur, getbiaya, biayaminimum, biayaakhir, icondrver;
    private FusedLocationProviderClient fusedLocationProviderClient;
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
    private SettingPreference sp;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        BottomSheetBehavior.from(binding.bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        promocode = 0;
        updateFitur();
        binding.pickUpContainer.setVisibility(View.VISIBLE);
        sp = new SettingPreference(this);
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if (userLogin != null) {
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        } else {
            finish();
            return;
        }

        int colorCodeDark = ContextCompat.getColor(this, R.color.colorPrimary);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.btnpromo.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
            binding.order.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
            binding.bar.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));
        }

        binding.pickUpButton.setOnClickListener(view -> onPickUp());
        binding.backBtn.setOnClickListener(view -> finish());
        binding.topUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TopupSaldoActivity.class)));
        binding.order.setOnClickListener(v -> {
            if (binding.pickUpText.getText().toString().isEmpty()) {
                notif("Lokasi tidak boleh kosong!");
            } else {
                onOrderButton();
            }
        });

        binding.pickUpText.setOnClickListener(v -> {
            binding.pickUpContainer.setVisibility(View.VISIBLE);
            openAutocompleteActivity();
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        driverAvailable = new ArrayList<>();
        driverMarkers = new ArrayList<>();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        int fiturId = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");
        if (fiturId != -1) {
            designedFitur = realm.where(FiturModel.class).equalTo("idFitur", fiturId).findFirst();
        }

        if (designedFitur != null) {
            fitur = String.valueOf(designedFitur.getIdFitur());
            getbiaya = String.valueOf(designedFitur.getBiaya());
            biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
            biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
            icondrver = designedFitur.getIcon_driver();
            binding.layanan.setText(designedFitur.getFitur());
            binding.layanandes.setText(designedFitur.getKeterangan());
            binding.enamjam.setSelected(true);
            binding.duabelasjam.setSelected(false);
            binding.satuhari.setSelected(false);
            updateDistance(1);

            binding.enamjam.setOnClickListener(view -> {
                binding.enamjam.setSelected(true);
                binding.duabelasjam.setSelected(false);
                binding.satuhari.setSelected(false);
                updateDistance(1);
            });

            binding.duabelasjam.setOnClickListener(view -> {
                binding.enamjam.setSelected(false);
                binding.duabelasjam.setSelected(true);
                binding.satuhari.setSelected(false);
                updateDistance(2);
            });

            binding.satuhari.setOnClickListener(view -> {
                binding.enamjam.setSelected(false);
                binding.duabelasjam.setSelected(false);
                binding.satuhari.setSelected(true);
                updateDistance(3);
            });

            binding.ketsaldo.setText("Diskon " + designedFitur.getDiskon() + " dengan Wallet");
        }

        binding.btnpromo.setOnClickListener(v -> {
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null && getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            } catch (Exception ignored) {
            }
            if (binding.promocode.getText().toString().isEmpty()) {
                notif("Kode promo tidak boleh kosong!");
            } else {
                promokodedata();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void promokodedata() {
        binding.btnpromo.setEnabled(false);
        binding.btnpromo.setText("Tunggu...");
        User user = BaseApp.getInstance(this).getLoginUser();
        if (user == null) return;
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(fitur);
        request.setCode(binding.promocode.getText().toString());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponseJson> call, @NonNull Response<PromoResponseJson> response) {
                binding.btnpromo.setEnabled(true);
                binding.btnpromo.setText("Gunakan");
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        if (response.body().getType().equals("persen")) {
                            promocode = (Long.parseLong(response.body().getNominal()) * harga) / 100;
                        } else {
                            promocode = Long.parseLong(response.body().getNominal());
                        }
                        updatePrice();
                    } else {
                        notif("Kode promo tidak tersedia!");
                        promocode = 0;
                        updatePrice();
                    }
                } else {
                    notif("Error!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PromoResponseJson> call, @NonNull Throwable t) {
                binding.btnpromo.setEnabled(true);
                binding.btnpromo.setText("Gunakan");
                notif("Error: " + t.getMessage());
            }
        });
    }

    private void updatePrice() {
        long finalBiayaTotal = harga;
        long totalDiskon = promocode;

        if ("1".equals(checkedpaywallet)) {
            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
            totalDiskon += diskonwallet;
        }

        String diskontotal = String.valueOf(totalDiskon);
        String totalbiaya = String.valueOf(finalBiayaTotal - totalDiskon);

        Utility.currencyTXT(binding.price, totalbiaya, context);
        Utility.currencyTXT(binding.diskon, diskontotal, context);
    }


    @SuppressLint("SetTextI18n")
    private void updateDistance(long jam) {
        int colorCodeDark = ContextCompat.getColor(this, R.color.colorPrimary);
        BottomSheetBehavior.from(binding.bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.detail.setVisibility(View.VISIBLE);
        binding.order.setVisibility(View.VISIBLE);
        binding.fitur.setText(jam + " jam");
        Utility.currencyTXT(binding.diskon, String.valueOf(promocode), this);

        checkedpaywallet = "0";
        binding.checkedcash.setSelected(true);
        binding.checkedwallet.setSelected(false);
        binding.cashPayment.setTextColor(colorCodeDark);
        binding.walletpayment.setTextColor(ContextCompat.getColor(this, R.color.gray));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.checkedcash.setImageTintList(ColorStateList.valueOf(colorCodeDark));
            binding.checkedwallet.setImageTintList(ContextCompat.getColorStateList(this, R.color.gray));
        }

        long biayaTotal = (long) (Double.parseDouble(getbiaya) * jam);
        if (biayaTotal < Long.parseLong(biayaminimum)) {
            this.harga = Long.parseLong(biayaminimum);
            Utility.currencyTXT(binding.cost, biayaminimum, this);
        } else {
            this.harga = biayaTotal;
            Utility.currencyTXT(binding.cost, String.valueOf(biayaTotal), this);
        }

        updatePrice();

        long saldokini = Long.parseLong(saldoWallet);
        binding.llcheckedwallet.setEnabled(saldokini >= (harga - (harga * Double.parseDouble(biayaakhir))));

        View.OnClickListener paymentClickListener = view -> {
            boolean isWallet = view.getId() == R.id.llcheckedwallet;
            checkedpaywallet = isWallet ? "1" : "0";
            binding.checkedcash.setSelected(!isWallet);
            binding.checkedwallet.setSelected(isWallet);
            binding.cashPayment.setTextColor(isWallet ? ContextCompat.getColor(context, R.color.gray) : colorCodeDark);
            binding.walletpayment.setTextColor(isWallet ? colorCodeDark : ContextCompat.getColor(context, R.color.gray));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.checkedcash.setImageTintList(isWallet ? ContextCompat.getColorStateList(context, R.color.gray) : ColorStateList.valueOf(colorCodeDark));
                binding.checkedwallet.setImageTintList(isWallet ? ColorStateList.valueOf(colorCodeDark) : ContextCompat.getColorStateList(context, R.color.gray));
            }
            updatePrice();
        };

        binding.llcheckedcash.setOnClickListener(paymentClickListener);
        if (binding.llcheckedwallet.isEnabled()) {
            binding.llcheckedwallet.setOnClickListener(paymentClickListener);
        }
    }

    public void notif(String text) {
        binding.rlnotif.setVisibility(View.VISIBLE);
        binding.textnotif.setText(text);
        new Handler(Looper.getMainLooper()).postDelayed(() -> binding.rlnotif.setVisibility(View.GONE), 3000);
    }

    private void openAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            binding.pickUpText.setText(place.getAddress());
            if (place.getLatLng() != null) {
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15f));
                onPickUp();
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR && data != null) {
            Log.i(TAG, Objects.requireNonNull(Autocomplete.getStatusFromIntent(data).getStatusMessage()));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateLastLocation(true);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLastLocation(true);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        try {
            if (!googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        updateLastLocation(true);
    }

    private void updateLastLocation(boolean move) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        gMap.setMyLocationEnabled(true);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                lastKnownLocation = location;
                if (move) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));
                }
                fetchNearDriver(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private void updateFitur() {
        if (driverAvailable != null) driverAvailable.clear();
        if (driverMarkers != null) {
            for (Marker m : driverMarkers) m.remove();
            driverMarkers.clear();
        }
        if (gMap != null) updateLastLocation(false);
    }

    private void onPickUp() {
        binding.pickUpContainer.setVisibility(View.GONE);
        if (pickUpMarker != null) pickUpMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        pickUpMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Pick Up")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_me)));
        pickUpLatLang = centerPos;
        binding.textprogress.setVisibility(View.VISIBLE);
        BottomSheetBehavior.from(binding.bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        requestAddress(centerPos, binding.pickUpText);
        fetchNearDriver(pickUpLatLang.latitude, pickUpLatLang.longitude);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
        threadRun = false;
    }

    private void fetchNearDriver(double latitude, double longitude) {
        if (driverAvailable != null) driverAvailable.clear();
        if (driverMarkers != null) {
            for (Marker m : driverMarkers) m.remove();
            driverMarkers.clear();
        }
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) return;

        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(latitude);
        param.setLongitude(longitude);
        param.setFitur(fitur);

        service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    driverAvailable = response.body().getData();
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {}
        });
    }


    private void requestAddress(LatLng latlang, final TextView textView) {
        if (latlang == null) return;
        MapDirectionAPI.getAddress(latlang).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {}
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String json = Objects.requireNonNull(response.body()).string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject Jobject = new JSONObject(json);
                            JSONArray Jarray = Jobject.getJSONArray("results");
                            if (Jarray.length() > 0) {
                                String address = Jarray.getJSONObject(0).getString("formatted_address");
                                textView.setText(address);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }


    private void onOrderButton() {
        if (driverAvailable.isEmpty()) {
            notif("Maaf, tidak ada driver di sekitar Anda.");
            return;
        }

        RideCarRequestJson param = new RideCarRequestJson();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if (userLogin == null) return;
        param.setIdPelanggan(userLogin.getId());
        param.setOrderFitur(fitur);
        param.setStartLatitude(pickUpLatLang.latitude);
        param.setStartLongitude(pickUpLatLang.longitude);
        param.setEndLatitude(0);
        param.setEndLongitude(0);
        param.setJarak(this.jarak);
        param.setHarga(this.harga);
        param.setEstimasi(binding.fitur.getText().toString());
        param.setKreditpromo(checkedpaywallet.equals("1") ? String.valueOf((long)(Double.parseDouble(biayaakhir) * this.harga)) : "0");
        param.setAlamatAsal(binding.pickUpText.getText().toString());
        param.setAlamatTujuan("");
        param.setPakaiWallet("1".equals(checkedpaywallet) ? 1 : 0);
        sendRequestTransaksi(param, driverAvailable);
    }

    private void sendRequestTransaksi(RideCarRequestJson param, final List<DriverModel> driverList) {
        binding.rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.requestTransaksi(param).enqueue(new Callback<RideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RideCarResponseJson> call, @NonNull Response<RideCarResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    buildDriverRequest(response.body());
                    thread = new Thread(() -> {
                        for (int i = 0; i < driverList.size(); i++) {
                            if (!threadRun) break;
                            fcmBroadcast(i, driverList);
                        }
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        if (threadRun) {
                            CheckStatusTransaksiRequest checkParam = new CheckStatusTransaksiRequest();
                            checkParam.setIdTransaksi(transaksi.getId());
                            service.checkStatusTransaksi(checkParam).enqueue(new Callback<CheckStatusTransaksiResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Response<CheckStatusTransaksiResponse> response) {
                                    if (response.isSuccessful() && response.body() != null && !response.body().isStatus()) {
                                        runOnUiThread(() -> notif("Driver tidak ditemukan!"));
                                        new Handler(Looper.getMainLooper()).postDelayed(() -> finish(), 3000);
                                    }
                                }
                                @Override
                                public void onFailure(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Throwable t) {
                                    runOnUiThread(() -> notif("Driver tidak ditemukan!"));
                                    new Handler(Looper.getMainLooper()).postDelayed(() -> finish(), 3000);
                                }
                            });
                        }
                    });
                    thread.start();
                } else {
                    notif("Akun Anda bermasalah, silakan hubungi CS!");
                    new Handler(Looper.getMainLooper()).postDelayed(() -> finish(), 3000);
                }
            }
            @Override
            public void onFailure(@NonNull Call<RideCarResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("Akun Anda bermasalah, silakan hubungi CS!");
                new Handler(Looper.getMainLooper()).postDelayed(() -> finish(), 3000);
            }
        });
    }

    private void buildDriverRequest(RideCarResponseJson response) {
        transaksi = response.getData().get(0);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        request = new DriverRequest();
        request.setIdTransaksi(transaksi.getId());
        request.setIdPelanggan(transaksi.getIdPelanggan());
        request.setRegIdPelanggan(loginUser.getToken());
        request.setOrderFitur(designedFitur.getHome());
        //... set all other request properties
        request.setNamaPelanggan(loginUser.getFullnama());
        request.setTelepon(loginUser.getNoTelepon());
        request.setType(ORDER);
    }

    private void fcmBroadcast(int index, List<DriverModel> driverList) {
        if (index < driverList.size()) {
            DriverModel driverToSend = driverList.get(index);
            request.setTime_accept(new Date().getTime() + "");
            final User login = BaseApp.getInstance(context).getLoginUser();
            if (login != null) {
                UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
                SendFcmRequest param = new SendFcmRequest();
                param.setId("1");
                param.setToken(driverToSend.getRegId());
                param.setData(request);
                service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("ResultFCM", response.body().getMessage());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<FcmResponse> call, @NonNull Throwable t) {
                        Log.e("TestFCM", "Failure: " + t.getMessage());
                    }
                });
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        if (response.getResponse() != null && (response.getResponse().equalsIgnoreCase(DriverResponse.ACCEPT) || response.getResponse().equals("3") || response.getResponse().equals("4"))) {
            threadRun = false;
            for (DriverModel cDriver : driverAvailable) {
                if (cDriver.getId().equals(response.getId())) {
                    Intent intent = new Intent(RentCarActivity.this, ActivityProgress.class);
                    //... put extras
                    //startActivity(intent);

                    DriverResponse emptyResponse = new DriverResponse();
                    //... clear response
                    EventBus.getDefault().postSticky(emptyResponse);
                    finish();
                    break;
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if (userLogin != null) {
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
            Utility.currencyTXT(binding.saldo, saldoWallet, this);
        }
    }

    private String getCompleteAddressString(LatLng latLng) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAdd;
    }
}