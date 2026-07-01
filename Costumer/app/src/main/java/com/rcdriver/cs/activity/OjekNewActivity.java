package com.rcdriver.cs.activity;

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
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.FiturPromoAdapter;
import com.rcdriver.cs.adapter.ListDriverAdapter;
import com.rcdriver.cs.adapter.PlaceAutoCompletedAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.databinding.ActivityOjekNewBinding;
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OjekNewActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String FITUR_KEY = "FiturKey";
    private static final String TAG = "OjekNewActivity";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    Context context = OjekNewActivity.this;
    private ActivityOjekNewBinding binding;
    private GoogleMap gMap;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation;
    private LatLng pickUpLatLang;
    private LatLng destinationLatLang;
    private Polyline directionLine;
    private Marker pickUpMarker;
    private Marker destinationMarker;
    int FITURID = -1;
    private String fitur, ICONFITUR, checkedpaywallet,
            biayaminimum, saldoWallet, getbiaya, biayaakhir, fiturdesc, icondriver;
    private SettingPreference sp;
    private List<DriverModel> driverAvailable;
    private List<Marker> driverMarkers;
    private FiturModel designedFitur;
    private Realm realm;
    private double mjarak;
    private long harga, promocode, maksimum;
    private double Radius;
    private List<VoucherModel> mItems = new ArrayList<>();
    private FiturPromoAdapter mAdapter;
    private ListDriverAdapter dAdapter;
    Handler handler;
    private PlaceAutoCompletedAdapter mPlaceAutocompleteAdapter;
    AutocompleteSessionToken autocompleteSessionToken;
    PlacesClient placesClient;

    private final okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
            runOnUiThread(() -> {
                binding.destinationContainer.setVisibility(View.VISIBLE);
                binding.rlprogress.setVisibility(View.GONE);
                Snackbar.make(binding.rootLayout, "Koneksi error, silakan pilih tujuan lagi!", Snackbar.LENGTH_LONG).show();
            });
        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = Objects.requireNonNull(response.body()).string();
                final long distance = MapDirectionAPI.getDistance(OjekNewActivity.this, json);
                final String time = MapDirectionAPI.getTimeDistance(OjekNewActivity.this, json);
                if (distance >= 0) {
                    runOnUiThread(() -> {
                        String format = String.format(Locale.US, "%.0f", (double) distance / 1000f);
                        long dist = Long.parseLong(format);
                        if (dist < maksimum) {
                            binding.rlprogress.setVisibility(View.GONE);
                            promocode = 0;
                            binding.promocode.setText("");
                            updateLineDestination(json);
                            updateDistance(distance);
                            binding.estimasi.setText(time);
                            binding.numdiskon.setText(String.valueOf(promocode));
                            Utility.currencyDiskon(binding.diskon, String.valueOf(promocode), OjekNewActivity.this);
                            DaftarPromo();
                            fetchNearDriver(pickUpLatLang);
                            binding.btnorder.setOnClickListener(v -> {
                                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                                    if (which == DialogInterface.BUTTON_POSITIVE) {
                                        KlikPesan("null", 1);
                                    } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                                        binding.Step2.setVisibility(View.GONE);
                                        binding.Step3.setVisibility(View.VISIBLE);
                                        driverterdekat(pickUpLatLang);
                                    }
                                };
                                new AlertDialog.Builder(OjekNewActivity.this)
                                        .setMessage("PILIH METODE PEMESANAN")
                                        .setPositiveButton("Otomatis", dialogClickListener)
                                        .setNegativeButton("Pilih Driver", dialogClickListener).show();
                            });
                        } else {
                            binding.rlprogress.setVisibility(View.GONE);
                            binding.destinationContainer.setVisibility(View.VISIBLE);
                            Snackbar.make(binding.rootLayout, "Jarak Tujuan Terlalu Jauh.", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOjekNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        BottomSheetBehavior.from(binding.bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        Places.initialize(getApplicationContext(), MainActivity.apikey);

        autocompleteSessionToken = AutocompleteSessionToken.newInstance();
        placesClient = Places.createClient(this);
        mPlaceAutocompleteAdapter = new PlaceAutoCompletedAdapter(this, placesClient, autocompleteSessionToken);

        binding.autoPickUpText.setAdapter(mPlaceAutocompleteAdapter);
        binding.autoPickUpText.setOnItemClickListener((parent, view, position, id) -> {
            AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            if (item == null) return;
            final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS);
            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(item.getPlaceId(), placeFields);
            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Place place = response.getPlace();
                if (place.getLatLng() != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                    onPickUp();
                }
                binding.autoPickUpText.setText(place.getName());
            }).addOnFailureListener((exception) -> Log.e(TAG, "Place not found: " + exception.getMessage()));
        });

        binding.autodestinationText.setAdapter(mPlaceAutocompleteAdapter);
        binding.autodestinationText.setOnItemClickListener((parent, view, position, id) -> {
            AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
            if (item == null) return;
            final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS);
            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(item.getPlaceId(), placeFields);
            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                Place place = response.getPlace();
                if (place.getLatLng() != null) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                    onDestination();
                }
                binding.autodestinationText.setText(place.getName());
            }).addOnFailureListener((exception) -> Log.e(TAG, "Place not found: " + exception.getMessage()));
        });

        binding.pickUpContainer.setVisibility(View.VISIBLE);
        binding.destinationContainer.setVisibility(View.GONE);

        realm = Realm.getDefaultInstance();
        driverAvailable = new ArrayList<>();
        driverMarkers = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        Intent intent = getIntent();
        FITURID = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");

        if (FITURID != -1) {
            designedFitur = realm.where(FiturModel.class).equalTo("idFitur", FITURID).findFirst();
        }

        if (designedFitur != null) {
            fitur = String.valueOf(designedFitur.getIdFitur());
            getbiaya = String.valueOf(designedFitur.getBiaya());
            biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
            biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
            icondriver = designedFitur.getIcon_driver();
            maksimum = Long.parseLong(designedFitur.getMaksimumdist());
            Radius = Double.parseDouble(designedFitur.getMaksimumdist());
            binding.fiturtext.setText(designedFitur.getFitur());
            fiturdesc = designedFitur.getKeterangan();
            updateFitur();
        }

        binding.pickUpButton.setOnClickListener(v -> onPickUp());
        binding.destinationButton.setOnClickListener(v -> onDestination());
        binding.pickUpText.setOnClickListener(v -> openAutocompleteActivity(1));
        binding.destinationText.setOnClickListener(v -> openAutocompleteActivity(2));
        binding.btnpromo.setOnClickListener(v -> onPromoButtonCLick());
        binding.closeDriver.setOnClickListener(v -> {
            binding.Step3.setVisibility(View.GONE);
            binding.Step2.setVisibility(View.VISIBLE);
        });
        binding.backBtn.setOnClickListener(v -> finish());

        sp = new SettingPreference(this);
        double picklat = Double.parseDouble(sp.getSetting()[6]);
        double picklng = Double.parseDouble(sp.getSetting()[7]);
        pickUpLatLang = new LatLng(picklat, picklng);
        binding.pickUpText.setText(sp.getSetting()[8]);
        fetchNearDriver(pickUpLatLang);
    }

    private void onPromoButtonCLick() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null && getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception ignored) {}
        if (binding.promocode.getText().toString().isEmpty()) {
            notif("Voucher Belum Di Pilih!");
        } else {
            promokodedata(binding.promocode.getText().toString());
        }
        binding.promocode.getText().clear();
    }

    private void onPickUp() {
        binding.destinationContainer.setVisibility(View.VISIBLE);
        binding.pickUpContainer.setVisibility(View.GONE);
        if (pickUpMarker != null) pickUpMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        pickUpMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Pick Up")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup)));
        pickUpLatLang = centerPos;
        requestAddress(centerPos, binding.pickUpText);
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
        requestAddress(centerPos, binding.destinationText);
        requestRoute();
        binding.destinationContainer.setVisibility(View.GONE);
        binding.pickUpContainer.setVisibility(View.VISIBLE);
    }

    private void updateDistance(long distance) {
        checkedpaywallet = "0";
        float km = ((float) (distance)) / 1000f;
        mjarak = km;
        String format = String.format(Locale.US, "%.1f", km);
        binding.distance.setText(format + "Km");
        String biaya = String.valueOf(biayaminimum);
        long biayaTotal = Utility.fixPembulatan((long) (Double.parseDouble(getbiaya) * km));
        if (biayaTotal < Double.parseDouble(biaya)) {
            this.harga = Long.parseLong(biaya);
            biayaTotal = Long.parseLong(biaya);
        }
        this.harga = biayaTotal;
        String totalbiaya = String.valueOf(biayaTotal);
        Utility.currencyTXT(binding.cost, totalbiaya, this);
        Utility.currencyTXT(binding.price, totalbiaya, this);
        long saldokini = Long.parseLong(saldoWallet);

        if (saldokini < (biayaTotal - (harga * Double.parseDouble(biayaakhir)))) {
            binding.rSaldo.setEnabled(false);
            binding.SetMetode.check(R.id.rTunai);
        } else {
            binding.rSaldo.setEnabled(true);
        }

        binding.SetMetode.setOnCheckedChangeListener((radioGroup, id) -> updatePriceWithPromo());
        updatePriceWithPromo(); // Update harga saat pertama kali
        binding.btnorder.setVisibility(View.VISIBLE);
    }

    private void driverterdekat(LatLng latLng) {
        if (driverAvailable != null) driverAvailable.clear();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) return;
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(latLng.latitude);
        param.setLongitude(latLng.longitude);
        param.setFitur(fitur);
        service.driverTerdekat(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    driverAvailable = response.body().getData();
                    binding.mDriverRec.setLayoutManager(new LinearLayoutManager(OjekNewActivity.this));
                    dAdapter = new ListDriverAdapter(driverAvailable, OjekNewActivity.this);
                    dAdapter.setOnItemClickListener(item -> KlikPesan(item.getRegId(), 0));
                    binding.mDriverRec.setAdapter(dAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {}
        });
    }

    private void fetchNearDriver(LatLng latLng) {
        if (latLng == null) return;
        if (driverAvailable != null) driverAvailable.clear();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) return;
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(latLng.latitude);
        param.setLongitude(latLng.longitude);
        param.setFitur(fitur);
        service.driverTerdekat(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    driverAvailable = response.body().getData();
                    createMarker();
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {}
        });
    }

    private void updateFitur() {
        if (driverAvailable != null) driverAvailable.clear();
        if (driverMarkers != null) {
            for (Marker m : driverMarkers) m.remove();
            driverMarkers.clear();
        }
    }

    private void notif(String pesan) {
        Snackbar.make(binding.rootLayout, pesan, Snackbar.LENGTH_LONG).show();
    }

    private void DaftarPromo() {
        User user = BaseApp.getInstance(this).getLoginUser();
        if(user == null) return;
        FiturPromoRequest request = new FiturPromoRequest();
        request.setFitur(fitur);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.FiturPromo(request).enqueue(new Callback<PromoResponse>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponse> call, @NonNull Response<PromoResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equalsIgnoreCase(response.body().getMessage())) {
                    mItems = response.body().getData();
                    binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(OjekNewActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    mAdapter = new FiturPromoAdapter(mItems, OjekNewActivity.this);
                    mAdapter.setClickListener(promomodel -> promokodedata(promomodel.getKode()));
                    binding.mRecyclerView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<PromoResponse> call, @NonNull Throwable t) {
                notif("Error: " + t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void promokodedata(String PromoKode) {
        User user = BaseApp.getInstance(this).getLoginUser();
        if (user == null) return;
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(fitur);
        request.setCode(PromoKode);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponseJson> call, @NonNull Response<PromoResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equalsIgnoreCase(response.body().getMessage())) {
                        if ("persen".equals(response.body().getType())) {
                            promocode = Utility.fixPembulatan((Long.parseLong(response.body().getNominal()) * harga) / 100);
                        } else {
                            promocode = Utility.fixPembulatan(Long.parseLong(response.body().getNominal()));
                        }
                        notif("Promo Berhasil Digunakan.");
                    } else {
                        notif("Kode Promo Tidak Tersedia!");
                        promocode = 0;
                    }
                } else {
                    notif("Tidak Ada Promo Tersedia");
                    promocode = 0;
                }
                updatePriceWithPromo();
            }
            @Override
            public void onFailure(@NonNull Call<PromoResponseJson> call, @NonNull Throwable t) {
                notif("Error: " + t.getMessage());
                promocode = 0;
                updatePriceWithPromo();
            }
        });
    }

    private void updatePriceWithPromo() {
        checkedpaywallet = binding.rSaldo.isChecked() ? "1" : "0";
        long totalDiscount = promocode;
        if (checkedpaywallet.equals("1")) {
            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
            totalDiscount += Utility.fixPembulatan(diskonwallet);
        }

        String diskonTotalStr = String.valueOf(totalDiscount);
        String hargaAkhirStr = String.valueOf(harga - totalDiscount);

        Utility.currencyTXT(binding.diskon, diskonTotalStr, this);
        Utility.currencyTXT(binding.price, hargaAkhirStr, this);
        binding.numdiskon.setText(diskonTotalStr);
    }

    private void KlikPesan(String token, int auto) {
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if (userLogin == null || pickUpLatLang == null || destinationLatLang == null) return;

        RideCarRequestJson param = new RideCarRequestJson();
        param.setIdPelanggan(userLogin.getId());
        param.setOrderFitur(fitur);
        param.setStartLatitude(pickUpLatLang.latitude);
        param.setStartLongitude(pickUpLatLang.longitude);
        param.setEndLatitude(destinationLatLang.latitude);
        param.setEndLongitude(destinationLatLang.longitude);
        param.setJarak(mjarak);
        param.setEstimasi(binding.fiturtext.getText().toString());
        param.setHarga(this.harga);
        param.setKreditpromo(binding.numdiskon.getText().toString());
        param.setAlamatAsal(binding.pickUpText.getText().toString());
        param.setAlamatTujuan(binding.destinationText.getText().toString());
        param.setPakaiWallet(binding.rSaldo.isChecked() ? 1 : 0);
        RequestOrederan(param, token, auto);
    }

    private void RequestOrederan(RideCarRequestJson param, String token, int auto) {
        Intent intent = new Intent(OjekNewActivity.this, RideOrder.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("idpelanggan", param.getIdPelanggan());
        intent.putExtra("fitur", fitur);
        intent.putExtra("idfitur", FITURID);
        intent.putExtra("ikon", ICONFITUR);
        intent.putExtra("fiturdesk", fiturdesc);
        intent.putExtra("biaya", binding.cost.getText().toString());
        intent.putExtra("picklat", param.getStartLatitude());
        intent.putExtra("picklng", param.getStartLongitude());
        intent.putExtra("destlat", param.getEndLatitude());
        intent.putExtra("destlng", param.getEndLongitude());
        intent.putExtra("jarak", param.getJarak());
        intent.putExtra("estimasi", binding.fiturtext.getText().toString());
        intent.putExtra("harga", this.harga);
        intent.putExtra("diskon", binding.numdiskon.getText().toString());
        intent.putExtra("pickaddress", binding.pickUpText.getText().toString());
        intent.putExtra("destkaddress", binding.destinationText.getText().toString());
        intent.putExtra("pakaisaldo", param.isPakaiWallet());
        intent.putExtra("token", token);
        intent.putExtra("auto", auto);
        intent.putExtra("namaalamat", binding.pickUpText.getText().toString());
        startActivity(intent);
        finish();
    }

    private void updateLastLocation(boolean move) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        gMap.setMyLocationEnabled(true);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                lastKnownLocation = location;
                if (move) {
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 15f));
                }
                fetchNearDriver(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
            }
        });
    }

    private void updateLineDestination(String json) {
        Directions directions = new Directions(this);
        try {
            List<Route> routes = directions.parse(json);
            if (directionLine != null) directionLine.remove();
            if (!routes.isEmpty()) {
                directionLine = gMap.addPolyline(new PolylineOptions()
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(this, R.color.colorgradient))
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
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        updateLastLocation(true);
    }

    private Timer timer;
    private TimerTask timerTask;

    private void startIsDriver() {
        stopIsDriver();
        timer = new Timer();
        handler = new Handler(Looper.getMainLooper());
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (NetworkManager.isConnectToInternet(OjekNewActivity.this) && pickUpLatLang != null) {
                        fetchNearDriver(pickUpLatLang);
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 4000);
    }

    private void stopIsDriver() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        startIsDriver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if (userLogin != null) {
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient.isConnected()) googleApiClient.disconnect();
        stopIsDriver();
        try { EventBus.getDefault().unregister(this); } catch (Exception e) {}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
        stopIsDriver();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            LatLng latLng = place.getLatLng();
            if (latLng != null) {
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                if (requestCode == 1) {
                    binding.pickUpText.setText(place.getAddress());
                    onPickUp();
                } else {
                    binding.destinationText.setText(place.getAddress());
                    onDestination();
                }
            }
        }
    }

    private void createMarker() {
        if (gMap == null || driverAvailable == null || driverAvailable.isEmpty()) return;

        runOnUiThread(() -> {
            if (driverMarkers != null) {
                for (Marker m : driverMarkers) m.remove();
                driverMarkers.clear();
            }
            for (DriverModel driver : driverAvailable) {
                LatLng currentDriverPos = new LatLng(driver.getLatitude(), driver.getLongitude());
                int iconRes = R.drawable.icmotor; // default
                if ("2".equals(icondriver)) iconRes = R.drawable.carmap;
                else if ("3".equals(icondriver)) iconRes = R.drawable.truck;
                // ... tambahkan else if untuk ikon lain

                driverMarkers.add(gMap.addMarker(new MarkerOptions()
                        .position(currentDriverPos)
                        .icon(BitmapDescriptorFactory.fromResource(iconRes))
                        .anchor(0.5f, 0.5f)
                        .rotation(Float.parseFloat(driver.getBearing()))
                        .flat(true)));
            }
        });
    }

    private void requestRoute() {
        if (pickUpLatLang != null && destinationLatLang != null) {
            binding.rlprogress.setVisibility(View.VISIBLE);
            MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
        }
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

    private void openAutocompleteActivity(int request_code) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
        startActivityForResult(intent, request_code);
    }
}