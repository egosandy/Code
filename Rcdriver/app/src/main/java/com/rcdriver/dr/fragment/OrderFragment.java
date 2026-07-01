package com.rcdriver.dr.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.ChatActivity;
import com.rcdriver.dr.activity.MainActivity;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.gmap.DirectionParser;
import com.rcdriver.dr.item.ItemPesananOrder;
import com.rcdriver.dr.json.AcceptRequestJson;
import com.rcdriver.dr.json.AcceptResponseJson;
import com.rcdriver.dr.json.DetailRequestJson;
import com.rcdriver.dr.json.DetailTransResponseJson;
import com.rcdriver.dr.json.DriverRequestJson;
import com.rcdriver.dr.json.EditHargaRequest;
import com.rcdriver.dr.json.EditHargaResponse;
import com.rcdriver.dr.json.FcmResponse;
import com.rcdriver.dr.json.JobResponse;
import com.rcdriver.dr.json.ResponseJson;
import com.rcdriver.dr.json.SaveStatusRequest;
import com.rcdriver.dr.json.SaveStatusResponse;
import com.rcdriver.dr.json.SendFcmRequest;
import com.rcdriver.dr.json.UpdateLocationRequestJson;
import com.rcdriver.dr.json.UpdateLocationResponseJson;
import com.rcdriver.dr.json.UpdateStatusRequest;
import com.rcdriver.dr.json.UpdateTotalRequest;
import com.rcdriver.dr.json.UpdateTotalResponse;
import com.rcdriver.dr.json.fcm.CancelBookRequestJson;
import com.rcdriver.dr.json.fcm.CancelBookResponseJson;
import com.rcdriver.dr.json.fcm.DriverResponse;
import com.rcdriver.dr.models.FcmDriver;
import com.rcdriver.dr.models.ItemPesananModel;
import com.rcdriver.dr.models.JobModels;
import com.rcdriver.dr.models.OrderFCM;
import com.rcdriver.dr.models.PelangganModel;
import com.rcdriver.dr.models.TransaksiModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.BackgroundColorTransform;
import com.rcdriver.dr.utils.FloatingViewService;
import com.rcdriver.dr.utils.Log;
import com.rcdriver.dr.utils.SettingPreference;
import com.rcdriver.dr.utils.Utility;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rcdriver.dr.constants.Constants.BASE_JOB;
import static com.rcdriver.dr.json.fcm.FCMType.ORDER;

// Hapus import ButterKnife
// import butterknife.BindView;
// import butterknife.ButterKnife;

@SuppressLint("Registered")
public class OrderFragment extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, ItemPesananOrder.OnItemClickInterface {

    // region Member Variables
    private static final int REQUEST_PERMISSION_CALL = 992;
    private static final int REQUEST_LOCATION = 0;
    private static final String TAG = "OrderFragment";
    private static final long INTERVAL = 2000;
    private static final long FASTEST_INTERVAL = 1000;
    private static final long POLYLINE_DRAW_INTERVAL = 30000;

    private static final String STATUS_ACCEPTED = "2";
    private static final String STATUS_STARTED = "3";
    private static final String STATUS_FINISHED = "4";
    private static final String STATUS_CANCELED = "5";

    public static String LinkIkon;
    public static String mEstimasi;
    public static String mJarak;
    public static String tokenPelanggan, tokenmerchant;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker marker;
    private Marker markerlokasi;
    private int markerCount = 0;
    private LatLng pickUpLatLng;
    private LatLng destinationLatLng;

    private Context context;
    private BottomSheetBehavior sheetBehavior;
    private SettingPreference sp;

    private String idtrans, idpelanggan, response, fitur, onsubmit;
    private String type;

    private Polyline currentPolyline;
    private long lastPolylineDrawTime = 0;
    private boolean isInitialRouteDrawn = false;
    private boolean isStatusActionRunning = false;
    // endregion

    private interface StatusCommitCallback {
        void onSuccess();
        void onFailed(String message);
    }

    // Deklarasi Variabel View (tanpa anotasi @BindView)
    private TextView layanan;
    private TextView namamerchant;
    private TextView OrderID;
    private LinearLayout llchat;
    private CircleImageView foto;
    private TextView pickUpText;
    private TextView destinationText;
    private CardView Navigasi;
    private TextView TxtWaktu;
    private TextView fiturtext;
    private TextView distanceText;
    private TextView priceText;
    private TextView biaya;
    private LinearLayout LayoutBiaya;
    private RelativeLayout rlprogress;
    private TextView textprogress;
    private TextView cost;
    private TextView deliveryfee;
    private LinearLayout phone;
    private LinearLayout chat;
    private LinearLayout phonemerchant;
    private LinearLayout chatmerchant;
    private LinearLayout llorderdetail;
    private LinearLayout lldetailsend;
    private TextView produk;
    private TextView TxtBiaya;
    private TextView sendername;
    private TextView receivername;
    private LinearLayout senderphone;
    private LinearLayout receiverphone;
    private Button submit;
    private LinearLayout llmerchantdetail;
    private ScrollView llmerchantinfo;
    private RecyclerView rvmerchantnear;
    private LinearLayout CancelOrder;
    private TextView Potongan;
    private LinearLayout lwasap;
    private LinearLayout BottomSheet;
    private TextView totaltext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View getView = Objects.requireNonNull(inflater).inflate(R.layout.activity_progress, container, false);
        context = getContext();

        // Hapus ButterKnife.bind(this, getView);

        // Inisialisasi Views (Pengganti ButterKnife)
        layanan = getView.findViewById(R.id.layanan);
        namamerchant = getView.findViewById(R.id.namamerchant);
        OrderID = getView.findViewById(R.id.orderid);
        llchat = getView.findViewById(R.id.llchat);
        foto = getView.findViewById(R.id.background);
        pickUpText = getView.findViewById(R.id.pickUpText);
        destinationText = getView.findViewById(R.id.destinationText);
        Navigasi = getView.findViewById(R.id.navigation);
        TxtWaktu = getView.findViewById(R.id.txtwaktu);
        fiturtext = getView.findViewById(R.id.idlayanan);
        distanceText = getView.findViewById(R.id.distance);
        priceText = getView.findViewById(R.id.price);
        biaya = getView.findViewById(R.id.biaya);
        LayoutBiaya = getView.findViewById(R.id.linBiaya);
        rlprogress = getView.findViewById(R.id.rlprogress);
        textprogress = getView.findViewById(R.id.textprogress);
        cost = getView.findViewById(R.id.cost);
        deliveryfee = getView.findViewById(R.id.deliveryfee);
        phone = getView.findViewById(R.id.phonenumber);
        chat = getView.findViewById(R.id.chat);
        phonemerchant = getView.findViewById(R.id.phonemerchant);
        chatmerchant = getView.findViewById(R.id.chatmerchant);
        llorderdetail = getView.findViewById(R.id.orderdetail);
        lldetailsend = getView.findViewById(R.id.senddetail);
        produk = getView.findViewById(R.id.produk);
        TxtBiaya = getView.findViewById(R.id.txtbiaya);
        sendername = getView.findViewById(R.id.sendername);
        receivername = getView.findViewById(R.id.receivername);
        senderphone = getView.findViewById(R.id.senderphone);
        receiverphone = getView.findViewById(R.id.receiverphone);
        submit = getView.findViewById(R.id.order);
        llmerchantdetail = getView.findViewById(R.id.merchantdetail);
        llmerchantinfo = getView.findViewById(R.id.merchantinfo);
        rvmerchantnear = getView.findViewById(R.id.merchantnear);
        CancelOrder = getView.findViewById(R.id.btncancel);
        Potongan = getView.findViewById(R.id.potongan);
        lwasap = getView.findViewById(R.id.lwasap);
        BottomSheet = getView.findViewById(R.id.bottom_sheet);
        totaltext = getView.findViewById(R.id.totaltext);


        sp = new SettingPreference(context);

        initDataFromBundle();
        initViews();
        initMap();
        initBottomSheet();

        IkonDriver();
        getData(idtrans, idpelanggan);

        return getView;
    }

    // region Initialization Methods
    private void initDataFromBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            idpelanggan = safeString(bundle.getString("id_pelanggan"));
            idtrans = safeString(bundle.getString("id_transaksi"));
            response = normalizeStatus(bundle.getString("response"));
            onsubmit = response;

            double AsalLat = bundle.getDouble("AsalLat", 0);
            double AsalLng = bundle.getDouble("AsalLng", 0);
            double TujuanLat = bundle.getDouble("TujuanLat", 0);
            double TujuanLng = bundle.getDouble("TujuanLng", 0);

            pickUpLatLng = new LatLng(AsalLat, AsalLng);
            destinationLatLng = new LatLng(TujuanLat, TujuanLng);
        }
    }

    private void initViews() {
        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        rlprogress.setVisibility(View.GONE);
        textprogress.setText(getString(R.string.waiting_pleaseWait));

        lwasap.setOnClickListener(v -> {
            String phoneNumber = "+" + sp.getSetting()[18];
            String message = "Halo, Mohon bantuannya";
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        createLocationRequest();

        String apiKey = (MainActivity.apikey != null) ? MainActivity.apikey : getString(R.string.google_maps_key);
        if (!Places.isInitialized()) {
            Places.initialize(context, apiKey);
        }
    }

    private void initBottomSheet() {
        sheetBehavior = BottomSheetBehavior.from(BottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        BottomSheet.setOnClickListener(v -> {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
    // endregion

    // ... (Sisa kode dari sini ke bawah tidak perlu diubah karena tidak berhubungan dengan ButterKnife) ...
    // ... (Salin dan tempel semua metode lain dari kode asli Anda di sini) ...

    // region Data Fetching and Processing
    private void getData(final String idtrans, final String idpelanggan) {
        if (!isAdded() || context == null) return;

        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) {
            Toast.makeText(context, "Sesi login berakhir. Silakan login ulang.", Toast.LENGTH_SHORT).show();
            endActivity();
            return;
        }

        if (TextUtils.isEmpty(idtrans) || TextUtils.isEmpty(idpelanggan)) {
            Toast.makeText(context, "Data order tidak lengkap.", Toast.LENGTH_SHORT).show();
            return;
        }

        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdPelanggan(idpelanggan);

        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responseApi) {
                if (!isAdded() || context == null) return;
                rlprogress.setVisibility(View.GONE);

                if (!responseApi.isSuccessful() || responseApi.body() == null) {
                    Toast.makeText(context, "Gagal memuat detail order dari server.", Toast.LENGTH_SHORT).show();
                    return;
                }

                DetailTransResponseJson body = responseApi.body();
                shimmertutup();

                if (body.getData() == null || body.getData().isEmpty()
                        || body.getPelanggan() == null || body.getPelanggan().isEmpty()) {
                    Toast.makeText(context, "Data order kosong dari server.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final TransaksiModel transaksi = body.getData().get(0);
                final PelangganModel pelanggan = body.getPelanggan().get(0);

                if (transaksi.getStatus() > 0) {
                    onsubmit = normalizeStatus(String.valueOf(transaksi.getStatus()));
                    response = onsubmit;
                }

                updateConstants(transaksi, pelanggan);
                updateUIWithData(transaksi, pelanggan, body);
                handleTransactionStatus(transaksi, pelanggan);
                parsedata(transaksi, pelanggan);
                redrawPolyline();
            }

            @Override
            public void onFailure(@NonNull Call<DetailTransResponseJson> call, @NonNull Throwable t) {
                if (!isAdded() || context == null) return;
                rlprogress.setVisibility(View.GONE);
                Log.e(TAG, "Failed to get transaction details: " + t.getMessage());
                Toast.makeText(context, "Koneksi bermasalah saat memuat order.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateConstants(TransaksiModel transaksi, PelangganModel pelanggan) {
        Constants.mPelanggan = pelanggan;
        Constants.IDTRANS = idtrans;
        Constants.mHome = transaksi.getHome();
        Constants.mToken = transaksi.token_merchant;
        Constants.mFitur = transaksi.getOrderFitur();
        Constants.mWallet = transaksi.isPakaiWallet();
    }

    private void updateUIWithData(TransaksiModel transaksi, PelangganModel pelanggan, DetailTransResponseJson body) {
        type = transaksi.getHome();
        tokenPelanggan = pelanggan.getToken();
        tokenmerchant = safeString(transaksi.getToken_merchant());

        OrderID.setText(String.format("No Pesanan #%s", transaksi.getId()));
        totaltext.setText(transaksi.isPakaiWallet() ? "Pakai Saldo" : "Bayar Ditempat");

        pickUpLatLng = new LatLng(transaksi.getStartLatitude(), transaksi.getStartLongitude());
        destinationLatLng = new LatLng(transaksi.getEndLatitude(), transaksi.getEndLongitude());

        setupNavigationClickListeners();

        if ("4".equals(transaksi.getHome())) LayoutBiaya.setVisibility(View.GONE);

        fitur = transaksi.getOrderFitur();
        Utility.currencyTXT(Potongan, String.valueOf(transaksi.getKreditPromo()), context);

        handleFiturSpecificUI(transaksi, pelanggan, body);
    }

    private void handleTransactionStatus(TransaksiModel transaksi, PelangganModel pelanggan) {
        submit.setEnabled(!isStatusActionRunning);

        if (STATUS_ACCEPTED.equals(onsubmit)) {
            submit.setVisibility(View.VISIBLE);
            llchat.setVisibility(View.VISIBLE);
            setCancelEnabled(true);

            submit.setText(getStartButtonText());
            submit.setOnClickListener(v -> start(pelanggan, transaksi.getToken_merchant(), transaksi.idtransmerchant, String.valueOf(transaksi.getWaktuOrder()), transaksi));
            CancelOrder.setOnClickListener(v -> showCancelConfirmationDialog(transaksi, pelanggan.getToken()));

        } else if (STATUS_STARTED.equals(onsubmit)) {
            submit.setVisibility(View.VISIBLE);
            llchat.setVisibility(View.VISIBLE);
            setCancelEnabled(false);

            submit.setText("SELESAIKAN");
            submit.setOnClickListener(v -> completeOrder(pelanggan, transaksi.getToken_merchant(), transaksi));

        } else if (STATUS_FINISHED.equals(onsubmit) || STATUS_CANCELED.equals(onsubmit)) {
            submit.setVisibility(View.GONE);
            llchat.setVisibility(View.GONE);
            setCancelEnabled(false);

        } else {
            submit.setVisibility(View.GONE);
            llchat.setVisibility(View.GONE);
            setCancelEnabled(false);
        }
    }

    private String getStartButtonText() {
        if ("4".equals(type)) return "MULAI PESANAN";
        if ("4".equals(fitur)) return "AMBIL";
        return "JALAN";
    }

    private void setCancelEnabled(boolean enabled) {
        CancelOrder.setEnabled(enabled);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = enabled ? R.color.red : R.color.gray;
            CancelOrder.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
        }
        CancelOrder.setAlpha(enabled ? 1.0f : 0.7f);
    }

    private void setupNavigationClickListeners() {
        Navigasi.setOnClickListener(v -> {
            LatLng targetLatLng = null;

            if (STATUS_ACCEPTED.equals(onsubmit)) {
                targetLatLng = pickUpLatLng;
            } else if (STATUS_STARTED.equals(onsubmit) || STATUS_FINISHED.equals(onsubmit)) {
                targetLatLng = destinationLatLng;
            }

            if (targetLatLng != null && getActivity() != null) {
                getActivity().stopService(new Intent(context, FloatingViewService.class));
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + targetLatLng.latitude + "," + targetLatLng.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                getActivity().startService(new Intent(context, FloatingViewService.class));
            }
        });
    }

    private void handleFiturSpecificUI(TransaksiModel transaksi, PelangganModel pelanggan, DetailTransResponseJson body) {
        if ("3".equals(type)) {
            fiturtext.setText(transaksi.getEstimasi());
        } else if ("4".equals(type)) {
            llorderdetail.setVisibility(View.VISIBLE);
            llmerchantdetail.setVisibility(View.VISIBLE);
            llmerchantinfo.setVisibility(View.VISIBLE);
            Utility.currencyTXT(deliveryfee, String.valueOf(transaksi.getHarga()), context);
            Utility.currencyTXT(cost, String.valueOf(transaksi.getTotal_biaya()), context);
            namamerchant.setText(transaksi.getNama_merchant());

            ItemPesananOrder itemPesananItem = new ItemPesananOrder(context, body.getItem(), R.layout.item_pesanan, tokenPelanggan, this::onItemClick);
            itemPesananItem.setTransaksi(transaksi.getId());
            rvmerchantnear.setAdapter(itemPesananItem);

            if (itemPesananItem.getItemCount() == 0) showEmptyMenuDialog(transaksi, pelanggan.getToken());

            phonemerchant.setOnClickListener(v -> showCallDialog("Merchant", transaksi.getTeleponmerchant()));
            chatmerchant.setOnClickListener(v -> startChat(transaksi.getId_merchant(), transaksi.getNama_merchant(), Constants.IMAGESMERCHANT + transaksi.getFoto_merchant(), transaksi.getToken_merchant()));

        } else if ("4".equals(fitur) || "5".equalsIgnoreCase(fitur)) {
            lldetailsend.setVisibility(View.VISIBLE);
            produk.setText(transaksi.getNamaBarang());
            sendername.setText(transaksi.namaPengirim);
            receivername.setText(transaksi.namaPenerima);
            senderphone.setOnClickListener(v -> showCallDialog(transaksi.getNamaPengirim(), transaksi.teleponPengirim));
            receiverphone.setOnClickListener(v -> showCallDialog(transaksi.getNamaPenerima(), transaksi.teleponPenerima));
        }
    }

    private void shimmertutup() {
        layanan.setVisibility(View.VISIBLE);
        pickUpText.setVisibility(View.VISIBLE);
        destinationText.setVisibility(View.VISIBLE);
        distanceText.setVisibility(View.VISIBLE);
        fiturtext.setVisibility(View.VISIBLE);
        priceText.setVisibility(View.VISIBLE);
        biaya.setVisibility(View.VISIBLE);
    }

    private void parsedata(TransaksiModel request, final PelangganModel pelanggan) {
        rlprogress.setVisibility(View.GONE);

        Picasso.get()
                .load(Constants.IMAGESUSER + pelanggan.getFoto())
                .resize(160, 200)
                .transform(new BackgroundColorTransform(ContextCompat.getColor(requireContext(), R.color.white)))
                .placeholder(R.drawable.nocamera).error(R.drawable.nocamera).into(foto);

        layanan.setText(pelanggan.getFullnama());
        pickUpText.setText(request.getAlamatAsal());
        destinationText.setText(request.getAlamatTujuan());

        long hargaAwal = request.getHarga();
        long diskon = Long.parseLong(safeNumber(request.getKreditPromo()));
        long hargaSetelahDiskon = Math.max(0, hargaAwal - diskon);
        long totalBiaya = "4".equals(type) ? hargaSetelahDiskon + Long.parseLong(safeNumber(request.getTotal_biaya())) : hargaSetelahDiskon;

        Utility.currencyTXT(biaya, String.valueOf(hargaAwal), context);
        Utility.currencyTXT(priceText, String.valueOf(totalBiaya), context);
        TxtBiaya.setText(String.valueOf(totalBiaya));

        UpdateTransaksi(idtrans, String.valueOf(totalBiaya));

        phone.setOnClickListener(v -> showCallDialog("Customer", pelanggan.getNoTelepon()));
        chat.setOnClickListener(v -> startChat(pelanggan.getId(), pelanggan.getFullnama(), Constants.IMAGESUSER + pelanggan.getFoto(), pelanggan.getToken()));
    }

    private void IkonDriver() {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) return;
        DriverService userService = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        DriverRequestJson param = new DriverRequestJson();
        param.setId(loginUser.getJob());
        userService.CekIkon(param).enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(@NonNull Call<JobResponse> call, @NonNull Response<JobResponse> response) {
                if (response.isSuccessful() && response.body() != null && "sukses".equalsIgnoreCase(response.body().getMessage())) {
                    List<JobModels> moddriver = response.body().getData();
                    if (moddriver != null && !moddriver.isEmpty()) {
                        String[] icons = {"default", "motor", "icmobil", "truck", "deliverybike", "hatchback", "suv", "van", "bicycle", "tuktuk"};
                        int iconIndex = Integer.parseInt(moddriver.get(0).getIcon());
                        if (iconIndex >= 0 && iconIndex < icons.length) {
                            LinkIkon = BASE_JOB + icons[iconIndex] + ".png";
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<JobResponse> call, @NonNull Throwable t) { }
        });
    }
    //endregion

    // region Lifecycle and Service Methods
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            Intent serviceIntent = new Intent(context, FloatingViewService.class);
            ContextCompat.startForegroundService(context, serviceIntent);
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        markerCount = 0;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }
    //endregion

    // region Google API Callbacks
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLastLocation = location;
        if (mLastLocation != null && isAdded()) {
            LatLng driverLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            addMarker(mMap, driverLatLng.latitude, driverLatLng.longitude);
            UpdateLokasiHosting(mLastLocation);
            updateDistanceAndTime(driverLatLng);
            updateDriverLocationOnFirebase();

            long currentTime = System.currentTimeMillis();
            if (!isInitialRouteDrawn || (currentTime - lastPolylineDrawTime > POLYLINE_DRAW_INTERVAL)) {
                redrawPolyline();
                lastPolylineDrawTime = currentTime;
                isInitialRouteDrawn = true;
            }
        }
    }

    private void updateDistanceAndTime(LatLng driverLatLng) {
        // Pastikan lokasi driver tidak null
        if (driverLatLng == null) return;

        LatLng target = null;

        // Tentukan target tujuan berdasarkan status order saat ini
        if (STATUS_ACCEPTED.equals(onsubmit)) {
            // Jika statusnya diterima, target driver adalah lokasi penjemputan/pengambilan.
            target = pickUpLatLng;
        } else if (STATUS_STARTED.equals(onsubmit) || STATUS_FINISHED.equals(onsubmit)) {
            // Jika order sudah berjalan, target driver adalah lokasi tujuan akhir.
            target = destinationLatLng;
        }

        // Jika target sudah ditentukan, hitung jarak dan estimasi waktu
        if (target != null) {
            // Panggil method CekKM untuk menghitung jarak (mJarak) dan waktu (mEstimasi)
            CekKM(driverLatLng, target);

            // Perbarui tampilan UI dengan hasil perhitungan
            distanceText.setText(mJarak);
            TxtWaktu.setText(String.format("%s Menit", mEstimasi));
        }
    }
    private void updateDriverLocationOnFirebase() {
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser != null && mLastLocation != null) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Driver");
            FcmDriver user = new FcmDriver(loginUser.getId(), onsubmit, mLastLocation.getLatitude(), mLastLocation.getLongitude(), mLastLocation.getBearing(), mEstimasi, mJarak);
            mDatabase.child(loginUser.getId()).setValue(user);
        }
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void addMarker(GoogleMap googleMap, double lat, double lon) {
        if (googleMap == null || !isAdded()) return;
        if (markerCount == 1 && marker != null) {
            marker.setPosition(new LatLng(lat, lon));
            return;
        }

        if (markerCount == 0) {
            if (marker != null) marker.remove();
            mMap = googleMap;
            LatLng latLng = new LatLng(lat, lon);

            if (LinkIkon != null && !LinkIkon.isEmpty()) {
                Picasso.get().load(LinkIkon).resize(110, 180).centerCrop().into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if (mMap != null) {
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).flat(true).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                        }
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) { addDefaultMarker(latLng); }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) { }
                });
            } else {
                addDefaultMarker(latLng);
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
            markerCount = 1;
        }
    }

    private void addDefaultMarker(LatLng latLng) {
        if (mMap == null || !isAdded()) return;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icmotor);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 110, 60, false);
        marker = mMap.addMarker(new MarkerOptions().position(latLng).flat(true).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
    }
    //endregion

    // region Network Calls & API Interactions
    private void start(final PelangganModel pelanggan, final String tokenmerchant, final String idtransmerchant, final String waktuorder, final TransaksiModel transaksi) {
        if (!canRunStatusAction()) return;

        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) {
            Toast.makeText(context, "Sesi login berakhir. Silakan login ulang.", Toast.LENGTH_SHORT).show();
            endActivity();
            return;
        }

        setActionLoading(true, "Mengirim status jalan ke server...");

        DriverService userService = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);

        userService.startrequest(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull Response<AcceptResponseJson> responseApi) {
                if (!isAdded() || context == null) return;

                if (isAcceptResponseSuccess(responseApi)) {
                    commitCustomerTransactionStatus(transaksi, pelanggan, STATUS_STARTED, 0, new StatusCommitCallback() {
                        @Override
                        public void onSuccess() {
                            OrderFCM orderFcm = buildOrderFcm(loginUser, STATUS_STARTED, getStartNotificationText());

                            if ("4".equals(type)) {
                                OrderFCM merchantFcm = buildOrderFcm(loginUser, STATUS_STARTED, "Driver sedang memproses dan mengantar pesanan.");
                                merchantFcm.id_pelanggan = idpelanggan;
                                merchantFcm.invoice = "INV-" + idtrans + safeString(idtransmerchant);
                                merchantFcm.ordertime = safeString(waktuorder);
                                sendOrderNotification(tokenmerchant, merchantFcm, "MERCHANT_START");
                            }

                            sendOrderNotification(pelanggan.getToken(), orderFcm, "CUSTOMER_START");

                            onsubmit = STATUS_STARTED;
                            response = STATUS_STARTED;
                            setActionLoading(false, null);
                            Toast.makeText(context, "Status jalan terkirim.", Toast.LENGTH_SHORT).show();
                            getData(idtrans, idpelanggan);
                        }

                        @Override
                        public void onFailed(String message) {
                            setActionLoading(false, null);
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    setActionLoading(false, null);
                    Toast.makeText(context, "Order sudah tidak tersedia atau gagal dijalankan.", Toast.LENGTH_SHORT).show();
                    getData(idtrans, idpelanggan);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                if (!isAdded() || context == null) return;
                setActionLoading(false, null);
                Toast.makeText(context, "Koneksi bermasalah saat mengirim status jalan.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void finish(final PelangganModel pelanggan, final String tokenmerchant, final TransaksiModel transaksi) {
        completeOrder(pelanggan, tokenmerchant, transaksi);
    }

    private void completeOrder(final PelangganModel pelanggan, final String tokenmerchant, final TransaksiModel transaksi) {
        if (!canRunStatusAction()) return;

        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) {
            Toast.makeText(context, "Sesi login berakhir. Silakan login ulang.", Toast.LENGTH_SHORT).show();
            endActivity();
            return;
        }

        setActionLoading(true, "Menyelesaikan order...");

        DriverService userService = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);

        userService.finishrequest(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull Response<AcceptResponseJson> responseApi) {
                if (!isAdded() || context == null) return;

                if (isAcceptResponseSuccess(responseApi)) {
                    commitCustomerTransactionStatus(transaksi, pelanggan, STATUS_FINISHED, 1, new StatusCommitCallback() {
                        @Override
                        public void onSuccess() {
                            OrderFCM orderFcm = buildOrderFcm(loginUser, STATUS_FINISHED, getString(R.string.notification_finish));

                            if ("4".equals(type)) {
                                sendOrderNotification(tokenmerchant, orderFcm, "MERCHANT_FINISH");
                            }
                            sendOrderNotification(pelanggan.getToken(), orderFcm, "CUSTOMER_FINISH");

                            onsubmit = STATUS_FINISHED;
                            response = STATUS_FINISHED;
                            UpdateStatus("1");
                            cleanupAfterFinish(loginUser);
                            setActionLoading(false, null);
                            endActivity();
                        }

                        @Override
                        public void onFailed(String message) {
                            setActionLoading(false, null);
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    setActionLoading(false, null);
                    Toast.makeText(context, "Order gagal diselesaikan atau sudah tidak tersedia.", Toast.LENGTH_SHORT).show();
                    getData(idtrans, idpelanggan);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                if (!isAdded() || context == null) return;
                setActionLoading(false, null);
                Toast.makeText(context, "Koneksi bermasalah saat menyelesaikan order.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelOrder(final TransaksiModel transaksi, String tokenpel) {
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) return;
        CancelBookRequestJson requestcancel = new CancelBookRequestJson();
        requestcancel.id = loginUser.getId();
        requestcancel.id_transaksi = idtrans;
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());

        service.cancelOrder(requestcancel).enqueue(new Callback<CancelBookResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<CancelBookResponseJson> call, @NonNull Response<CancelBookResponseJson> response) {
                rlprogress.setVisibility(View.GONE);
                if (response.isSuccessful() && "canceled".equals(Objects.requireNonNull(response.body()).mesage)) {
                    fcmcancel(tokenpel);
                    if ("4".equals(type)) {
                        fcmcancelmerchant();
                    }
                    UpdateStatus("1");
                    endActivity();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CancelBookResponseJson> call, @NonNull Throwable t) {
                rlprogress.setVisibility(View.GONE);
                Toast.makeText(context, "Failed to cancel order.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateLokasiHosting(Location location) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) return;

        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateLocationRequestJson request = new UpdateLocationRequestJson();
        request.setId(loginUser.getId());
        request.setLatitude(String.valueOf(location.getLatitude()));
        request.setLongitude(String.valueOf(location.getLongitude()));
        request.setBearing(String.valueOf(location.getBearing()));
        request.setStatus(onsubmit);

        service.updatelocation(request).enqueue(new Callback<UpdateLocationResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<UpdateLocationResponseJson> call, @NonNull Response<UpdateLocationResponseJson> response) { }
            @Override
            public void onFailure(@NonNull Call<UpdateLocationResponseJson> call, @NonNull Throwable t) { }
        });
    }

    private void UpdateTransaksi(String idtrans, String Biaya) {
        if (TextUtils.isEmpty(idtrans) || TextUtils.isEmpty(Biaya)) return;

        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) return;
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateTotalRequest request = new UpdateTotalRequest();
        request.setId(idtrans);
        request.setBiaya_akhir(Biaya);

        service.updateTotalHarga(request).enqueue(new Callback<UpdateTotalResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateTotalResponse> call, @NonNull Response<UpdateTotalResponse> response) {}
            @Override
            public void onFailure(@NonNull Call<UpdateTotalResponse> call, @NonNull Throwable t) {}
        });
    }

    private void UpdateStatus(String status) {
        if (TextUtils.isEmpty(status)) return;

        User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) return;
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        SaveStatusRequest request = new SaveStatusRequest();
        request.id = loginUser.getId();
        request.status = status;

        service.updateStatus(request).enqueue(new Callback<SaveStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveStatusResponse> call, @NonNull Response<SaveStatusResponse> response) {}
            @Override
            public void onFailure(@NonNull Call<SaveStatusResponse> call, @NonNull Throwable t) {}
        });
    }

    private void PerbaruiStatus(int rates, String idtrans, String idpel, String biaya, String wallet, String fitur, String respon) {
        // Wrapper lama tetap dipertahankan agar tidak merusak pemanggilan lain.
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) return;

        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateStatusRequest param = new UpdateStatusRequest();
        param.setId(idpel);
        param.setId_transaksi(idtrans);
        param.setId_driver(loginUser.getId());
        param.setTotal_biaya(safeNumber(biaya));
        param.setPakai_wallet(safeString(wallet, "false"));
        param.setFitur(safeString(fitur));
        param.setNama_driver(safeString(loginUser.getFullnama()));
        param.setFoto_driver(safeString(loginUser.getFotodriver()));
        param.setResponse(normalizeStatus(respon));
        param.setIsrate(rates);

        service.updateStatus(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                Log.d(TAG, "PerbaruiStatus response=" + response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Log.e(TAG, "PerbaruiStatus gagal: " + t.getMessage());
            }
        });
    }

    private void finsihStatus(final TransaksiModel transaksi) {
        // Wrapper lama diarahkan ke status selesai.
        PelangganModel pelanggan = Constants.mPelanggan;
        if (pelanggan == null) return;
        commitCustomerTransactionStatus(transaksi, pelanggan, STATUS_FINISHED, 1, null);
    }

    private void commitCustomerTransactionStatus(final TransaksiModel transaksi, final PelangganModel pelanggan,
                                                 final String targetStatus, final int isRate,
                                                 @Nullable final StatusCommitCallback callback) {
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) {
            if (callback != null) callback.onFailed("Sesi login berakhir. Silakan login ulang.");
            return;
        }

        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateStatusRequest param = new UpdateStatusRequest();
        param.setId(!TextUtils.isEmpty(pelanggan.getId()) ? pelanggan.getId() : idpelanggan);
        param.setId_transaksi(!TextUtils.isEmpty(transaksi.getId()) ? transaksi.getId() : idtrans);
        param.setId_driver(loginUser.getId());
        param.setTotal_biaya(resolveTotalBiaya(transaksi));
        param.setPakai_wallet(String.valueOf(transaksi.isPakaiWallet()));
        param.setFitur(safeString(transaksi.getOrderFitur()));
        param.setNama_driver(safeString(loginUser.getFullnama()));
        param.setFoto_driver(safeString(loginUser.getFotodriver()));
        param.setResponse(targetStatus);
        param.setIsrate(isRate);

        Log.d(TAG, "commitCustomerTransactionStatus payload=" + new Gson().toJson(param));

        service.updateStatus(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> responseApi) {
                if (!isAdded() || context == null) return;
                if (responseApi.isSuccessful()) {
                    Log.d(TAG, "Status customer berhasil diupdate ke " + targetStatus);
                    if (callback != null) callback.onSuccess();
                } else {
                    Log.e(TAG, "Status customer gagal. HTTP " + responseApi.code());
                    if (callback != null) callback.onFailed("Gagal update status order ke backend. Coba lagi.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                if (!isAdded() || context == null) return;
                Log.e(TAG, "Status customer gagal: " + t.getMessage());
                if (callback != null) callback.onFailed("Koneksi bermasalah saat update status order.");
            }
        });
    }

    private void edit(String id, String harga, String qty) {
        DriverService service = ServiceGenerator.createService(DriverService.class, "admin", "123456");
        EditHargaRequest param = new EditHargaRequest();
        param.setId(id);
        param.setHarga(harga);
        param.setQty(qty);
        service.editharga(param).enqueue(new Callback<EditHargaResponse>() {
            @Override
            public void onResponse(@NonNull Call<EditHargaResponse> call, @NonNull Response<EditHargaResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    fcmchange();
                    getData(idtrans, idpelanggan);
                }
            }
            @Override
            public void onFailure(@NonNull retrofit2.Call<EditHargaResponse> call, @NonNull Throwable t) {}
        });
    }
    //endregion

    // region FCM and Notifications
    private void sendMessageToDriver(final String regIDTujuan, final OrderFCM response) {
        sendOrderNotification(regIDTujuan, response, "LEGACY_ORDER_FCM");
    }

    private void sendOrderNotification(final String regIDTujuan, final OrderFCM responseData, final String tag) {
        final User login = BaseApp.getInstance(context).getLoginUser();
        if (login == null) return;

        if (TextUtils.isEmpty(regIDTujuan) || "null".equalsIgnoreCase(regIDTujuan)) {
            Log.e(TAG, "FCM " + tag + " batal: token tujuan kosong. Payload=" + new Gson().toJson(responseData));
            return;
        }

        DriverService service = ServiceGenerator.createService(DriverService.class, login.getNoTelepon(), login.getPassword());
        SendFcmRequest param = new SendFcmRequest();
        param.setId("1");
        param.setToken(regIDTujuan);
        param.setData(responseData);

        Log.d(TAG, "Kirim FCM " + tag + " payload=" + new Gson().toJson(param));

        service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
            @Override
            public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> responseApi) {
                if (responseApi.isSuccessful() && responseApi.body() != null) {
                    Log.d(TAG, "FCM " + tag + " terkirim request server: " + responseApi.body().getMessage());
                } else {
                    Log.e(TAG, "FCM " + tag + " gagal HTTP " + responseApi.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FcmResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "FCM " + tag + " gagal koneksi: " + t.getMessage());
            }
        });
    }

    private void fcmchange() {
        DriverResponse fcmResponse = new DriverResponse();
        fcmResponse.type = ORDER;
        fcmResponse.setId(BaseApp.getInstance(context).getLoginUser().getId());
        fcmResponse.setIdTransaksi(idtrans);
        fcmResponse.setResponse("7");
        sendMessageToDriver(tokenPelanggan, fcmResponse);
    }

    private void fcmcancel(String token) {
        DriverResponse fcmResponse = new DriverResponse();
        fcmResponse.type = ORDER;
        fcmResponse.setIdTransaksi(idtrans);
        fcmResponse.setResponse("5");
        sendMessageToDriver(token, fcmResponse);
    }

    private void fcmcancelmerchant() {
        DriverResponse fcmResponse = new DriverResponse();
        fcmResponse.type = ORDER;
        fcmResponse.setIdTransaksi(idtrans);
        fcmResponse.setResponse(String.valueOf(Constants.CANCEL));
        sendMessageToDriver(tokenmerchant, fcmResponse);
    }

    private void sendMessageToDriver(final String regIDTujuan, final DriverResponse response) {
        final User login = BaseApp.getInstance(context).getLoginUser();
        if (login == null) return;
        if (TextUtils.isEmpty(regIDTujuan) || "null".equalsIgnoreCase(regIDTujuan)) {
            Log.e(TAG, "FCM DriverResponse batal: token kosong. Payload=" + new Gson().toJson(response));
            return;
        }
        DriverService service = ServiceGenerator.createService(DriverService.class, login.getNoTelepon(), login.getPassword());
        SendFcmRequest param = new SendFcmRequest();
        param.setId("1");
        param.setToken(regIDTujuan);
        param.setData(response);
        service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
            @Override
            public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> responseApi) {
                Log.d(TAG, "FCM DriverResponse response=" + responseApi.code());
            }

            @Override
            public void onFailure(@NonNull Call<FcmResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "FCM DriverResponse gagal: " + t.getMessage());
            }
        });
    }
    //endregion

    // region Status Helpers
    private boolean canRunStatusAction() {
        if (isStatusActionRunning) {
            Toast.makeText(context, "Proses sebelumnya masih berjalan.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isAdded() || context == null) return false;
        return true;
    }

    private void setActionLoading(boolean loading, @Nullable String message) {
        isStatusActionRunning = loading;
        if (rlprogress != null) rlprogress.setVisibility(loading ? View.VISIBLE : View.GONE);
        if (submit != null) submit.setEnabled(!loading);
        if (textprogress != null && !TextUtils.isEmpty(message)) textprogress.setText(message);
        if (!loading && textprogress != null) textprogress.setText(getString(R.string.waiting_pleaseWait));
    }

    private boolean isAcceptResponseSuccess(@NonNull Response<AcceptResponseJson> responseApi) {
        return responseApi.isSuccessful()
                && responseApi.body() != null
                && "berhasil".equalsIgnoreCase(safeString(responseApi.body().getMessage()));
    }

    private OrderFCM buildOrderFcm(User loginUser, String status, String desc) {
        OrderFCM orderFcm = new OrderFCM();
        orderFcm.id_driver = loginUser.getId();
        orderFcm.id_pelanggan = idpelanggan;
        orderFcm.id_transaksi = idtrans;
        orderFcm.response = status;
        orderFcm.desc = safeString(desc);
        return orderFcm;
    }

    private String getStartNotificationText() {
        if ("4".equals(type)) {
            return "Driver sedang memproses pesanan dan menuju lokasi pengantaran.";
        }
        if ("4".equals(fitur) || "5".equalsIgnoreCase(fitur)) {
            return "Driver sedang mengambil paket dan menuju lokasi tujuan.";
        }
        return getString(R.string.notification_start);
    }

    private void cleanupAfterFinish(User loginUser) {
        try {
            Object service = context.getSystemService(Context.ACTIVITY_SERVICE);
            if (service instanceof ActivityManager) {
                ((ActivityManager) service).killBackgroundProcesses("com.google.android.apps.maps");
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal menutup Google Maps: " + e.getMessage());
        }

        try {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Driver");
            mDatabase.child(loginUser.getId()).removeValue();
        } catch (Exception e) {
            Log.e(TAG, "Gagal hapus lokasi Firebase driver: " + e.getMessage());
        }
    }

    private String normalizeStatus(String value) {
        if (TextUtils.isEmpty(value) || "null".equalsIgnoreCase(value)) return STATUS_ACCEPTED;
        return value.trim();
    }

    private String safeString(String value) {
        return safeString(value, "");
    }

    private String safeString(String value, String fallback) {
        if (TextUtils.isEmpty(value) || "null".equalsIgnoreCase(value)) return fallback;
        return value;
    }

    private String safeNumber(String value) {
        String cleaned = safeString(value, "0").replaceAll("[^0-9-]", "");
        if (TextUtils.isEmpty(cleaned) || "-".equals(cleaned)) return "0";
        return cleaned;
    }

    private String resolveTotalBiaya(TransaksiModel transaksi) {
        if (transaksi == null) return "0";

        String biayaAkhir = safeNumber(transaksi.getBiaya_akhir());
        if (!"0".equals(biayaAkhir)) return biayaAkhir;

        if (TxtBiaya != null && TxtBiaya.getText() != null) {
            String fromText = safeNumber(TxtBiaya.getText().toString());
            if (!"0".equals(fromText)) return fromText;
        }

        try {
            long hargaAwal = transaksi.getHarga();
            long diskon = Long.parseLong(safeNumber(transaksi.getKreditPromo()));
            long total = Math.max(0, hargaAwal - diskon);
            if ("4".equals(type)) {
                total += Long.parseLong(safeNumber(transaksi.getTotal_biaya()));
            }
            return String.valueOf(total);
        } catch (Exception e) {
            return safeNumber(String.valueOf(transaksi.getHarga()));
        }
    }
    // endregion

    // region UI Helpers and Dialogs
    private void showCallDialog(String userType, String phoneNumber) {
        new AlertDialog.Builder(context, R.style.DialogStyle)
                .setTitle("Call " + userType)
                .setMessage("You want to call " + userType + " (+" + phoneNumber + ")?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                        return;
                    }
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + phoneNumber));
                    startActivity(callIntent);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void startChat(String receiverId, String receiverName, String receiverPic, String receiverToken) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) return;
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("senderid", loginUser.getId());
        intent.putExtra("receiverid", receiverId);
        intent.putExtra("tokendriver", loginUser.getToken());
        intent.putExtra("tokenku", receiverToken);
        intent.putExtra("name", receiverName);
        intent.putExtra("pic", receiverPic);
        startActivity(intent);
    }

    private void showCancelConfirmationDialog(TransaksiModel transaksi, String token) {
        new AlertDialog.Builder(context, R.style.DialogStyle)
                .setTitle("Pesanan")
                .setMessage("Apakah Anda Ingin Membatalkan Pesanan?")
                .setPositiveButton("Ya", (dialog, which) -> cancelOrder(transaksi, token))
                .setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showEmptyMenuDialog(TransaksiModel transaksi, String token) {
        new AlertDialog.Builder(context)
                .setMessage("Daftar Menu Kosong Silahkan Batalkan Pesanan!")
                .setCancelable(false)
                .setPositiveButton("OKE", (dialog, id) -> cancelOrder(transaksi, token))
                .show();
    }

    @Override
    public void onItemClick(ItemPesananModel model) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_price);

        EditText edtnama = dialog.findViewById(R.id.amount);
        EditText edtharga = dialog.findViewById(R.id.bank);
        EditText edtperubahan = dialog.findViewById(R.id.namanumber);
        Button submitBtn = dialog.findViewById(R.id.submit);
        Button cancelBtn = dialog.findViewById(R.id.submit2);

        long price = Long.parseLong(model.getTotal_harga()) / Long.parseLong(model.getJumlah_item());
        edtnama.setText(model.getTipe() == 1 ? model.getNama_pesanan() : model.getNama_item());
        edtharga.setText(Utility.toformatRupiah(String.valueOf(price)));

        submitBtn.setOnClickListener(v -> {
            if (edtperubahan.getText().toString().isEmpty()) {
                edtperubahan.setError("Harga perubahan harus diisi");
                edtperubahan.requestFocus();
                return;
            }
            edit(model.getId(), edtperubahan.getText().toString(), model.getJumlah_item());
            dialog.dismiss();
        });

        cancelBtn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void endActivity() {
        if (getActivity() != null) {
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("IsWorking", false);
            startActivity(i);
            getActivity().finish();
        }
    }
    //endregion

    // region Map Drawing and Directions
    private void redrawPolyline() {
        if (mMap == null || mLastLocation == null || !isAdded()) { return; }

        LatLng driverLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        LatLng targetLatLng = null;

        if (STATUS_ACCEPTED.equals(onsubmit)) {
            targetLatLng = pickUpLatLng;
        } else if (STATUS_STARTED.equals(onsubmit) || STATUS_FINISHED.equals(onsubmit)) {
            targetLatLng = destinationLatLng;
        }

        if (targetLatLng != null) {
            if(markerlokasi != null) markerlokasi.remove();

            int iconResId;
            if (STATUS_ACCEPTED.equals(onsubmit)) {
                iconResId = R.drawable.pin_pickup;
            } else {
                iconResId = R.drawable.pin_destinasi;
            }

            Bitmap b = BitmapFactory.decodeResource(getResources(), iconResId);
            Bitmap markerBitmap = Bitmap.createScaledBitmap(b, 150, 150, false);
            markerlokasi = mMap.addMarker(new MarkerOptions().position(targetLatLng).icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));

            new TaskDirectionRequest().execute(buildRequestUrl(driverLatLng, targetLatLng));
        }
    }

    private String buildRequestUrl(LatLng origin, LatLng destination) {
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String strDestination = "destination=" + destination.latitude + "," + destination.longitude;
        String mode = "mode=driving";
        String apiKey = (MainActivity.apikey != null) ? MainActivity.apikey : getString(R.string.google_maps_key);
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + strOrigin + "&" + strDestination + "&" + mode + "&key=" + apiKey;
        return url;
    }

    @SuppressLint("StaticFieldLeak")
    private class TaskDirectionRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String responseString) {
            super.onPostExecute(responseString);
            if (responseString != null && !responseString.isEmpty()) {
                new TaskParseDirection().execute(responseString);
            }
        }
    }

    private String requestDirection(String requestedUrl) throws IOException {
        String responseString = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(requestedUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuffer = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) stringBuffer.append(line);
            responseString = stringBuffer.toString();
        } finally {
            if (inputStream != null) inputStream.close();
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return responseString;
    }

    @SuppressLint("StaticFieldLeak")
    private class TaskParseDirection extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonString) {
            try {
                return new DirectionParser().parse(new JSONObject(jsonString[0]));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            super.onPostExecute(routes);
            if (routes == null || routes.isEmpty() || !isAdded()) { return; }

            if (currentPolyline != null) {
                currentPolyline.remove();
            }

            PolylineOptions polylineOptions = null;
            for (List<HashMap<String, String>> path : routes) {
                ArrayList<LatLng> points = new ArrayList<>();
                polylineOptions = new PolylineOptions();
                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(Objects.requireNonNull(point.get("lat")));
                    double lon = Double.parseDouble(Objects.requireNonNull(point.get("lng")));
                    points.add(new LatLng(lat, lon));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(15f);
                polylineOptions.color(Color.parseColor("#32AA4B"));
                polylineOptions.geodesic(true);
            }

            if (polylineOptions != null && mMap != null) {
                currentPolyline = mMap.addPolyline(polylineOptions);
            }
        }
    }

    private void CekKM(LatLng driver, LatLng tujuan) {
        if (driver == null || tujuan == null) return;

        Location locationA = new Location("Point A");
        locationA.setLatitude(driver.latitude);
        locationA.setLongitude(driver.longitude);
        Location locationB = new Location("Point B");
        locationB.setLatitude(tujuan.latitude);
        locationB.setLongitude(tujuan.longitude);

        float distanceInKm = locationA.distanceTo(locationB) / 1000;
        int estimatedTimeInMinutes = (int) ((distanceInKm / 30) * 60);

        mJarak = String.format(Locale.US, "%.1f Km", distanceInKm);
        mEstimasi = String.valueOf(Math.max(1, estimatedTimeInMinutes));
    }
    //endregion
}