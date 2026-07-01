package com.rcdriver.dr.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.ActivityPoint;
import com.rcdriver.dr.activity.LoginActivity;
import com.rcdriver.dr.activity.transfer.PilihTujuanActivity;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.item.BanklistItem;
import com.rcdriver.dr.item.BannerItem;
import com.rcdriver.dr.json.LoginRequest;
import com.rcdriver.dr.json.LoginResponse;
import com.rcdriver.dr.json.PointRequestJson;
import com.rcdriver.dr.json.PointResponseJson;
import com.rcdriver.dr.json.ResponseJson;
import com.rcdriver.dr.json.SaveStatusRequest;
import com.rcdriver.dr.json.SaveStatusResponse;
import com.rcdriver.dr.json.SliderRequest;
import com.rcdriver.dr.json.SliderResponse;
import com.rcdriver.dr.json.UpdateLocationRequestJson;
import com.rcdriver.dr.json.UpdateLocationResponseJson;
import com.rcdriver.dr.json.UpdateLoginRequest;
import com.rcdriver.dr.json.UpdateTokenRequestJson;
import com.rcdriver.dr.models.BankModels;
import com.rcdriver.dr.models.DriverModel;
import com.rcdriver.dr.models.LoginModel;
import com.rcdriver.dr.models.SliderModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.CircleTransform;
import com.rcdriver.dr.utils.FloatingViewService;
import com.rcdriver.dr.utils.SettingPreference;
import com.rcdriver.dr.utils.Utility;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import com.github.angads25.toggle.LabeledSwitch;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "HomeFragment";
    private Context context;
    private GoogleMap gMap;
    private SettingPreference sp;
    private User loginUser;

    // UI Components
    private TextView saldo, namadriver, tArea, mStatus, TxtPoint, limitsaldo;
    private RelativeLayout rlprogress;
    private LabeledSwitch autofloat, autobid;
    private ImageView fotoprofile, background;
    private RatingBar DriverRate;
    private SwitchCompat SwithOn;
    private ViewPager viewPager;
    private LinearLayout llslider, SetBelanja, ViewPoint, qrdriver, btnscan, laktif, lwaiting;

    // Data
    private String uangbelanja;
    private List<BankModels> mList;
    private List<LoginModel> loginModels;

    // API Lokasi Modern
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    // Handler untuk tugas periodik
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable cekLoginRunnable;

    // Activity Result Launcher
    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                if (Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_FINE_LOCATION))) {
                    checkGpsSettings();
                } else {
                    Toast.makeText(context, "Izin lokasi diperlukan untuk menampilkan peta.", Toast.LENGTH_LONG).show();
                }
            });

    private final ActivityResultLauncher<IntentSenderRequest> gpsLauncher =
            registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(context, "GPS harus aktif untuk pembaruan lokasi.", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> overlayPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(context)) {
                        if (autofloat.isOn()) {
                            handleFloatingWidget(true);
                        }
                    } else {
                        Toast.makeText(context, "Izin overlay diperlukan untuk fitur ini.", Toast.LENGTH_SHORT).show();
                        autofloat.setOn(false);
                    }
                }
            });

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);

        sp = new SettingPreference(context);
        loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) {
            logout();
            return null; // Hindari crash jika user null
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        initializeViews(view);
        setupMap();
        setupUIListeners();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadInitialDataFromBundle();
        updateProfilUI();
        CekPoint();
        fetchBannerSlider();
        updateFcmToken();
    }

    private void initializeViews(View view) {
        rlprogress = view.findViewById(R.id.rlprogress);
        saldo = view.findViewById(R.id.saldo);
        namadriver = view.findViewById(R.id.namadriver);
        mStatus = view.findViewById(R.id.mStatus);
        SwithOn = view.findViewById(R.id.SwithOn);
        background = view.findViewById(R.id.background);
        fotoprofile = view.findViewById(R.id.fotoprofile);
        DriverRate = view.findViewById(R.id.driverrate);
        llslider = view.findViewById(R.id.Slider);
        viewPager = view.findViewById(R.id.viewPager);
        TxtPoint = view.findViewById(R.id.TxtPoint);
        ViewPoint = view.findViewById(R.id.viewPoint);
        limitsaldo = view.findViewById(R.id.limitsaldo);
        autofloat = view.findViewById(R.id.autofloat);
        autobid = view.findViewById(R.id.autobid);
        SetBelanja = view.findViewById(R.id.setbelanja);
        qrdriver = view.findViewById(R.id.myqrcode);
        btnscan = view.findViewById(R.id.btnscan);
        laktif = view.findViewById(R.id.laktif);
        lwaiting = view.findViewById(R.id.lwaiting);
        mList = getPeopleData(context);
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void setupUIListeners() {
        SwithOn.setOnCheckedChangeListener(this);

        autobid.setOnToggledListener((labeledSwitch, isOn) -> {
            sp.updateAutoBid(isOn ? "ON" : "OFF");
            Toast.makeText(context, "Auto Bid " + (isOn ? "Aktif" : "Nonaktif"), Toast.LENGTH_SHORT).show();
        });

        autofloat.setOnToggledListener((labeledSwitch, isOn) -> {
            sp.updateFloat(isOn ? "ON" : "OFF");
            handleFloatingWidget(isOn);
        });

        SetBelanja.setOnClickListener(v -> showMaxBelanjaDialog());
        ViewPoint.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityPoint.class);
            startActivity(intent);
        });

        qrdriver.setOnClickListener(v -> {
            // Logika untuk menampilkan popup QR Code
        });

        btnscan.setOnClickListener(v -> {
            Intent i = new Intent(context, PilihTujuanActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }

    private void loadInitialDataFromBundle() {
        Bundle args = getArguments();
        if (args != null) {
            Utility.currencyTXT(saldo, args.getString("saldo"), context);
            TxtPoint.setText(String.format("%s Poin", args.getString("point", "0")));
        }

        // Atur status switch dari SharedPreferences, ini lebih andal
        boolean isOnline = sp.getSetting()[2].equalsIgnoreCase("ON");
        SwithOn.setChecked(isOnline);
        updateStatusUI(isOnline);

        autobid.setOn(sp.getSetting()[0].equalsIgnoreCase("ON"));
        autofloat.setOn(sp.getSetting()[13].equalsIgnoreCase("ON"));

        uangbelanja = sp.getSetting()[1].isEmpty() ? "10000" : sp.getSetting()[1];
        updateLimitSaldoText();
    }

    private void updateLimitSaldoText() {
        if ("Unlimited".equalsIgnoreCase(uangbelanja)) {
            limitsaldo.setText("Unlimited");
        } else {
            try {
                double value = Double.parseDouble(uangbelanja);
                String formatted = formatRupiah(value).replaceAll(",00", "");
                limitsaldo.setText(formatted);
            } catch (NumberFormatException e) {
                limitsaldo.setText(uangbelanja);
            }
        }
    }

    private void updateProfilUI() {
        if (loginUser != null && loginUser.isValid()) {
            namadriver.setText(loginUser.getFullnama());
            DriverRate.setRating(Float.parseFloat(loginUser.getRating()));
            Picasso.get().load(Constants.IMAGESDRIVER + loginUser.getFotodriver())
                    .transform(new CircleTransform()).placeholder(R.drawable.nocamera).error(R.drawable.nocamera).into(fotoprofile);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        try {
            gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json));
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Gagal memuat style peta.", e);
        }
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(false);
        startLocationFlow();
    }

    private void startLocationFlow() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkGpsSettings();
        } else {
            permissionLauncher.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        }
    }

    private void checkGpsSettings() {
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000) // Interval 5 detik
                .setMinUpdateIntervalMillis(3000) // Interval tercepat 3 detik
                .build();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(requireActivity()).checkLocationSettings(builder.build());

        task.addOnSuccessListener(requireActivity(), response -> startLocationUpdates());
        task.addOnFailureListener(requireActivity(), e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    IntentSenderRequest senderRequest = new IntentSenderRequest.Builder(((ResolvableApiException) e).getResolution()).build();
                    gpsLauncher.launch(senderRequest);
                } catch (Exception sendEx) {
                    Log.e(TAG, "Error saat menampilkan dialog GPS", sendEx);
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        if (gMap != null) gMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) moveCameraToLocation(location, 16f);
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location newLocation = locationResult.getLastLocation();
                if (newLocation != null) {
                    // Hanya pindahkan kamera jika pengguna belum menggeser peta secara manual
                    // (Ini adalah logika opsional untuk UX yang lebih baik)
                    // if (!gMap.isUiGesturesInProgress()) {
                    //    moveCameraToLocation(newLocation, gMap.getCameraPosition().zoom);
                    // }
                    updateLocationToServer(newLocation);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void moveCameraToLocation(Location location, float zoom) {
        if (gMap != null && location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
        cekLoginRunnable = this::CekLogin;
        handler.postDelayed(cekLoginRunnable, 5000);
        updateProfilUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
        handler.removeCallbacks(cekLoginRunnable);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.SwithOn) {
            sp.updateKerja(isChecked ? "ON" : "OFF");
            sp.updateRespon(isChecked ? "1" : "4");
            updateStatusUI(isChecked);
            updateStatusToServer(isChecked ? "1" : "4", "dari check");
        }
    }

    private void updateStatusUI(boolean isOnline) {
        mStatus.setText(isOnline ? "Online" : "Offline");
        int color = isOnline ? R.color.navy : R.color.red;
        if (isAdded()) { // Pastikan fragment masih terpasang
            background.setBackgroundColor(ContextCompat.getColor(context, color));
            setStatusBarColor(color);
        }
    }

    private void setStatusBarColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getActivity() != null) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, colorResId));
        }
    }

    // --- Network Calls & Data Handling ---

    private void updateLocationToServer(Location location) {
        if (loginUser == null || !loginUser.isValid()) return;

        String status = SwithOn.isChecked() ? "1" : "4";
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateLocationRequestJson request = new UpdateLocationRequestJson();
        request.setId(loginUser.getId());
        request.setLatitude(String.valueOf(location.getLatitude()));
        request.setLongitude(String.valueOf(location.getLongitude()));
        request.setBearing(String.valueOf(location.getBearing()));
        request.setStatus(status);
        service.updatelocation(request).enqueue(new Callback<UpdateLocationResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<UpdateLocationResponseJson> call, @NonNull Response<UpdateLocationResponseJson> response) {
                if (response.isSuccessful()) Log.d(TAG, "Update lokasi ke server: success");
            }
            @Override
            public void onFailure(@NonNull Call<UpdateLocationResponseJson> call, @NonNull Throwable t) {
                Log.e(TAG, "Gagal update lokasi", t);
            }
        });
    }

    private void updateStatusToServer(String status, String logSource) {
        if (loginUser == null || !loginUser.isValid()) return;

        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        SaveStatusRequest request = new SaveStatusRequest();
        request.setId(loginUser.getId());
        request.setStatus(status);
        service.updateStatus(request).enqueue(new Callback<SaveStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveStatusResponse> call, @NonNull Response<SaveStatusResponse> response) {
                if(response.isSuccessful()) Log.d("SaveStatus", "Status berhasil diupdate " + logSource);
            }
            @Override
            public void onFailure(@NonNull Call<SaveStatusResponse> call, @NonNull Throwable t) {
                Log.e("SaveStatus", "Gagal update status", t);
            }
        });
    }

    private void CekPoint() {
        if (loginUser == null) return;
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        PointRequestJson param = new PointRequestJson();
        param.setId(loginUser.getId());
        service.CekPoint(param).enqueue(new Callback<PointResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PointResponseJson> call, @NonNull Response<PointResponseJson> response) {
                if (response.isSuccessful() && response.body() != null && "sukses".equalsIgnoreCase(response.body().getMessage())) {
                    List<DriverModel> points = response.body().getData();
                    if (points != null && !points.isEmpty()) {
                        TxtPoint.setText(String.format("%s Poin", points.get(0).getPoint()));
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<PointResponseJson> call, @NonNull Throwable t) {
                Log.e(TAG, "Gagal CekPoint", t);
            }
        });
    }

    private void CekLogin() {
        if (loginUser == null || !loginUser.isValid()) {
            if (isAdded()) logout();
            return;
        }
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        LoginRequest param = new LoginRequest();
        param.setId(loginUser.getId());
        service.CekLogin(param).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(isAdded() && response.isSuccessful() && response.body() != null) {
                    loginModels = response.body().getData();
                    if (loginModels != null && !loginModels.isEmpty()) {
                        LoginModel data = loginModels.get(0);
                        if(data.getIslogin() == 0) {
                            Toast.makeText(context, "Akun ini telah dikeluarkan dari perangkat lain.", Toast.LENGTH_LONG).show();
                            logoutpaksa();
                        } else {
                            boolean isAktif = data.getStatus() == 1;
                            laktif.setVisibility(isAktif ? View.VISIBLE : View.GONE);
                            lwaiting.setVisibility(isAktif ? View.GONE : View.VISIBLE);
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Gagal CekLogin", t);
            }
        });
    }

    private void fetchBannerSlider() {
        if (loginUser == null) return;
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        SliderRequest param = new SliderRequest();
        param.setFitur_promosi("17");
        service.SliderApp(param).enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(@NonNull Call<SliderResponse> call, @NonNull Response<SliderResponse> response) {
                if (isAdded() && response.isSuccessful() && response.body() != null) {
                    List<SliderModel> sliders = response.body().getData();
                    if (sliders != null && !sliders.isEmpty()) {
                        llslider.setVisibility(View.VISIBLE);
                        BannerItem adapter = new BannerItem(sliders, context);
                        viewPager.setAdapter(adapter);
                        viewPager.setPadding(30, 0, 30, 0);
                        viewPager.setPageMargin(10);
                    } else {
                        llslider.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<SliderResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Gagal mengambil data banner", t);
            }
        });
    }

    private void updateFcmToken() {
        // Pastikan loginUser tidak null sebelum digunakan
        final User loginUser = BaseApp.getInstance(getContext()).getLoginUser();
        if (loginUser == null) {
            Log.w(TAG, "User tidak login, update token dibatalkan.");
            return;
        }

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "Gagal mengambil token FCM dari Firebase", task.getException());
                return;
            }

            String token = task.getResult();
            Log.d(TAG, "Mengirim token FCM baru ke server: " + token);

            DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());

            // Sesuaikan nama kelas request jika berbeda
            UpdateTokenRequestJson param = new UpdateTokenRequestJson();
            param.setId(loginUser.getId());
            param.setReg_id(token);

            // --- PERBAIKAN DI SINI ---
            // Mengubah Callback<ResponseJson> menjadi Callback<Void>
            service.updateToken(param).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    // Cukup periksa apakah response.isSuccessful()
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Token FCM berhasil diupdate ke server.");
                    } else {
                        Log.e(TAG, "Server gagal memproses token, Kode: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    // Error EOFException tidak akan muncul lagi di sini
                    Log.e(TAG, "Gagal update token FCM (Koneksi bermasalah)", t);
                }
            });
        });
    }

    // --- Fitur Lainnya (Dialog, Logout, dll.) ---

    private void handleFloatingWidget(boolean enable) {
        if (getActivity() == null) return;
        Intent intent = new Intent(context, FloatingViewService.class);
        if (enable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {
                Intent overlayIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getActivity().getPackageName()));
                overlayPermissionLauncher.launch(overlayIntent);
            } else {
                getActivity().startService(intent);
            }
        } else {
            getActivity().stopService(intent);
        }
    }

    private void showMaxBelanjaDialog() {
        if (context == null) return;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bank);
        dialog.setCancelable(true);

        ImageView close = dialog.findViewById(R.id.close);
        RecyclerView list = dialog.findViewById(R.id.recycleview);

        list.setHasFixedSize(true);
        list.setLayoutManager(new GridLayoutManager(context, 1));

        BanklistItem bankItem = new BanklistItem(context, (ArrayList<BankModels>) mList, R.layout.item_petunjuk, item -> {
            uangbelanja = item.getText();
            sp.updateMaksimalBelanja(uangbelanja);
            updateLimitSaldoText();
            dialog.dismiss();
        });
        list.setAdapter(bankItem);
        close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void logout() {
        if (getActivity() == null || !isAdded()) return;

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> r.delete(User.class));
        }
        BaseApp.getInstance(context).setLoginUser(null);

        Intent intent = new Intent(context, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void logoutpaksa() {
        if (loginUser == null || !loginUser.isValid()) {
            logout();
            return;
        }
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateLoginRequest param = new UpdateLoginRequest();
        param.setId(loginUser.getId());
        param.setIslogin(0);
        service.updatelogin(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                logout();
            }
            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                logout();
            }
        });
    }

    private static String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        return NumberFormat.getCurrencyInstance(localeID).format(number);
    }

    private static List<BankModels> getPeopleData(Context ctx) {
        List<BankModels> items = new ArrayList<>();
        // Gunakan try-with-resources untuk memastikan TypedArray di-recycle
        try (TypedArray name_arr = ctx.getResources().obtainTypedArray(R.array.list_maximum)) {
            for (int i = 0; i < name_arr.length(); i++) {
                BankModels obj = new BankModels();
                obj.setText(name_arr.getString(i));
                items.add(obj);
            }
        }
        return items;
    }
}