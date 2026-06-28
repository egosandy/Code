package com.rcdriver.cs.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.directionhelpers.FetchURL;
import com.rcdriver.cs.directionhelpers.TaskLoadedCallback;
import com.rcdriver.cs.gmap.DirectionParser;
import com.rcdriver.cs.item.ItemPesananItem;
import com.rcdriver.cs.json.DetailRequestJson;
import com.rcdriver.cs.json.DetailTransResponseJson;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.LokasiDriverRequest;
import com.rcdriver.cs.json.LokasiDriverResponse;
import com.rcdriver.cs.json.SaveStatusRequest;
import com.rcdriver.cs.json.SaveStatusResponse;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.json.fcm.CancelBookRequestJson;
import com.rcdriver.cs.json.fcm.CancelBookResponseJson;
import com.rcdriver.cs.json.fcm.DriverResponse;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.LokasiDriverModel;
import com.rcdriver.cs.models.TransaksiModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.mwsdk.LatLngInterpolators;
import com.rcdriver.cs.mwsdk.MarkerAnimation;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rcdriver.cs.constants.Constants.BASE_JOB;
import static com.rcdriver.cs.utils.api.service.MessagingService.BROADCAST_ORDER;

public class ActivityProgress  extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback{
    private static final int REQUEST_PERMISSION_CALL = 992;
    Timer timer = new Timer();
    Bundle orderBundle;
    Realm realm;
    String icondriver;
    String gethome;
    String complete,iddriver,idtrans,isWallet,response,fitur,regdriver,imagedriver;
    String tokenmerchant;
    private boolean isCancelable = true;
    private User user;
    ImageView ImgAmbil,ImgAntar;
    TextView titleAmbil , desAmbil,StatusDriver;

    Button batalkan;
    ImageView chat,phone;
    RelativeLayout rlprogress;
    TextView textprogress,titlePickup,waktu;
    TextView namadriver,platnomor,alamatPickup,alamatAntar,metode,totalbayar,harga,ongkir,diskon,txttotal,merk,tipe;
    CircleImageView fotodriver;
    ImageView ImgPickup;
    LinearLayout layoutdiskon,LayoutMerchant,LayoutKurir,detailorder,layoutharga, ldriver, lwasap;
    TextView produk,receivername,sendername;
    RecyclerView rvmerchantnear;
    Button receiverphone;
    Button senderphone;
    ItemPesananItem itemPesananItem;
    private Handler handler = new Handler();
    private ArrayList<String> list = new ArrayList<>();
    private LatLng pickUpLatLng;
    private LatLng destinationLatLng;
    private LatLng driverlatlng;
    private Marker currentLocationMarker;
    private Polyline currentPolyline;
    private Marker pickUpMarker = null;
    private Marker destinationMarker = null;
    private String IkonDriver = null;
    SettingPreference sp;
    GoogleMap gMap;
    CoordinatorLayout bottomsheet;
    private LatLng Lokasiku;

    public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras() != null){
                orderBundle = intent.getExtras();
                orderHandler(orderBundle.getInt("code"));
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_order);
        realm = Realm.getDefaultInstance();
        sp = new SettingPreference(this);

        if (sp.getSetting()[6] != null && sp.getSetting()[7] != null) {
            double lat = Double.parseDouble(sp.getSetting()[6]);
            double lng = Double.parseDouble(sp.getSetting()[7]);
            Lokasiku = new LatLng(lat, lng);
        }

        fitur = "0";
        icondriver = "0";
        gethome = "0";
        list.add("default");
        list.add("icmotor");
        list.add("icmobil");
        list.add("truck");
        list.add("deliverybike");
        list.add("hatchback");
        list.add("suv");
        list.add("van");
        list.add("bicycle");
        list.add("tuktuk");
        Intent intent = getIntent();
        iddriver = intent.getStringExtra("id_driver");
        idtrans = intent.getStringExtra("id_transaksi");
        isWallet = intent.getStringExtra("pakai_wallet");
        response = intent.getStringExtra("response");
        if (intent.getStringExtra("complete") == null) {
            complete = "false";
        } else {
            complete = intent.getStringExtra("complete");
        }
        bottomsheet = findViewById(R.id.bottom_sheet);
        ImgAmbil = findViewById(R.id.ImgAmbil);
        ImgAntar = findViewById(R.id.ImgAntar);
        titleAmbil = findViewById(R.id.titleAmbil);
        desAmbil = findViewById(R.id.desAmbil);
        batalkan = findViewById(R.id.cancelorder);
        chat = findViewById(R.id.Chat);
        phone = findViewById(R.id.Telepon);
        rlprogress = findViewById(R.id.rlprogress);
        textprogress  = findViewById(R.id.textprogress);
        namadriver = findViewById(R.id.namadriver);
        platnomor = findViewById(R.id.platnomor);
        merk = findViewById(R.id.merk);
        tipe = findViewById(R.id.tip);
        fotodriver = findViewById(R.id.fotodriver);
        ImgPickup = findViewById(R.id.ImgPickup);
        titlePickup = findViewById(R.id.titlePickup);
        alamatPickup = findViewById(R.id.alamatPickup);
        alamatAntar = findViewById(R.id.alamatAntar);
        metode = findViewById(R.id.metode);
        totalbayar = findViewById(R.id.totalbayar);
        harga = findViewById(R.id.harga);
        ongkir = findViewById(R.id.ongkir);
        diskon = findViewById(R.id.diskon);
        txttotal = findViewById(R.id.txttotal);
        layoutdiskon = findViewById(R.id.layoutdiskon);
        detailorder = findViewById(R.id.detailorder);
        layoutharga = findViewById(R.id.layoutharga);
        StatusDriver = findViewById(R.id.StatusDriver);
        ldriver = findViewById(R.id.ldriver);
        waktu = findViewById(R.id.Waktu);
        lwasap = findViewById(R.id.lwasap);
        //Merchant
        rvmerchantnear = findViewById(R.id.daftaritem);
        LayoutMerchant = findViewById(R.id.LayoutMerchant);
        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //Kurir
        LayoutKurir = findViewById(R.id.LayoutKurir);
        receiverphone = findViewById(R.id.kontakpenerima);
        senderphone = findViewById(R.id.kontakpengirim);
        receivername = findViewById(R.id.namapenerima);
        sendername = findViewById(R.id.namapengirim);
        produk = findViewById(R.id.produk);

        user = BaseApp.getInstance(this).getLoginUser();
        rlprogress.setVisibility(View.VISIBLE);
        textprogress.setText(getString(R.string.waiting_pleaseWait));
        getData(idtrans,iddriver);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }

        lwasap.setOnClickListener(v -> {
            String ponsel = "+" + sp.getSetting()[13];
            String semuapesan = "Halo, Mohon bantuannya";
            String url="https://api.whatsapp.com/send?phone="+ponsel + "&text=" + semuapesan;
            Intent i= new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getData(final String idtrans, final String iddriver) {
        if(user == null) return;
        BookService service = ServiceGenerator.createService(BookService.class, user.getEmail(), user.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdDriver(iddriver);
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful() && responsedata.body() != null) {
                    if (responsedata.body().getData() == null || responsedata.body().getData().isEmpty()) {
                        Toast.makeText(ActivityProgress.this, "Transaction data not found.", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }

                    final TransaksiModel transaksi = responsedata.body().getData().get(0);
                    FiturModel designedFitur = realm.where(FiturModel.class).equalTo("idFitur", Integer.parseInt(transaksi.getOrderFitur())).findFirst();
                    if(designedFitur != null){
                        icondriver = designedFitur.getIcon_driver();
                        gethome = designedFitur.getHome();
                    }

                    response = String.valueOf(transaksi.status);
                    DriverModel driver = null;
                    if(transaksi.status < 9 && responsedata.body().getDriver() != null && !responsedata.body().getDriver().isEmpty()){
                        driver = responsedata.body().getDriver().get(0);
                        regdriver = driver.getRegId();
                        tokenmerchant = transaksi.getToken_merchant();
                        imagedriver = Constants.IMAGESDRIVER + driver.getFoto();
                    }

                    fitur = transaksi.getOrderFitur();
                    metode.setText(transaksi.isPakaiWallet() ? "Saldo" : "Tunai");

                    Utility.currencyTXT(txttotal, String.valueOf(transaksi.getBiaya_akhir()), ActivityProgress.this);
                    Utility.currencyTXT(totalbayar, String.valueOf(transaksi.getBiaya_akhir()), ActivityProgress.this);
                    Utility.currencyTXT(ongkir, String.valueOf(transaksi.getHarga()), ActivityProgress.this);

                    if("0".equals(transaksi.getKreditPromo()) || transaksi.getKreditPromo() == null){
                        layoutdiskon.setVisibility(View.GONE);
                    }else{
                        layoutdiskon.setVisibility(View.VISIBLE);
                        Utility.currencyTXT(diskon, String.valueOf(transaksi.getKreditPromo()), ActivityProgress.this);
                    }

                    parsedata(transaksi, driver);
                    new FetchURL(ActivityProgress.this).execute(getUrl(pickUpLatLng, destinationLatLng, "driving"), "driving");

                    if(response.equals("3")){
                        startDriverLocationUpdate();
                    }

                    if("4".equals(gethome)){
                        titlePickup.setText(transaksi.getNama_merchant());
                        alamatPickup.setText(transaksi.getAlamat_merchant());
                        titleAmbil.setText("Pemesanan");
                        desAmbil.setText("Memproses.");
                        alamatAntar.setText(transaksi.getAlamatTujuan());
                        detailorder.setVisibility(View.VISIBLE);
                        LayoutMerchant.setVisibility(View.VISIBLE);
                        LayoutKurir.setVisibility(View.GONE);
                        if(responsedata.body().getItem() != null){
                            itemPesananItem = new ItemPesananItem(responsedata.body().getItem(), R.layout.item_pesanan,ActivityProgress.this);
                            rvmerchantnear.setAdapter(itemPesananItem);
                        }
                        layoutharga.setVisibility(View.VISIBLE);
                        Utility.currencyTXT(harga, String.valueOf(transaksi.getTotal_biaya()), ActivityProgress.this);
                    }else if("2".equals(gethome)){
                        titlePickup.setText("Alamat Pengambilan");
                        alamatPickup.setText(transaksi.getAlamatAsal());
                        titleAmbil.setText("Pengambilan");
                        desAmbil.setText("Ke lokasi");
                        alamatAntar.setText(transaksi.getAlamatTujuan());
                        detailorder.setVisibility(View.VISIBLE);
                        LayoutMerchant.setVisibility(View.GONE);
                        LayoutKurir.setVisibility(View.VISIBLE);
                        produk.setText(transaksi.getNamaBarang());
                        sendername.setText(transaksi.namaPengirim);
                        receivername.setText(transaksi.namaPenerima);
                        senderphone.setOnClickListener(v -> {
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityProgress.this, R.style.DialogStyle);
                            alertDialogBuilder.setTitle("Hubungi");
                            alertDialogBuilder.setMessage("Apakah Kamu Ingin Menghubungi " + transaksi.getNamaPengirim() + "(+" + transaksi.teleponPengirim + ")?");
                            alertDialogBuilder.setPositiveButton("Ya", (arg0, arg1) -> {
                                if (ActivityCompat.checkSelfPermission(ActivityProgress.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ActivityProgress.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                    return;
                                }
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:+" + transaksi.teleponPengirim));
                                startActivity(callIntent);
                            });
                            alertDialogBuilder.setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        });
                        receiverphone.setOnClickListener(v -> {
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityProgress.this, R.style.DialogStyle);
                            alertDialogBuilder.setTitle("Hubungi");
                            alertDialogBuilder.setMessage("Apakah Kamu Ingin Menghubungi " + transaksi.getNamaPenerima() + "(+" + transaksi.teleponPenerima + ")?");
                            alertDialogBuilder.setPositiveButton("Ya", (arg0, arg1) -> {
                                if (ActivityCompat.checkSelfPermission(ActivityProgress.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ActivityProgress.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                    return;
                                }
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:+" + transaksi.teleponPenerima));
                                startActivity(callIntent);
                            });
                            alertDialogBuilder.setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        });
                        layoutharga.setVisibility(View.GONE);
                    }else{
                        layoutharga.setVisibility(View.GONE);
                        detailorder.setVisibility(View.GONE);
                        titlePickup.setText("Alamat Penjemputan");
                        alamatPickup.setText(transaksi.getAlamatAsal());
                        titleAmbil.setText("Penjemputan");
                        desAmbil.setText("Ke Lokasi");
                        alamatAntar.setText(transaksi.getAlamatTujuan());
                    }

                    updateUIBasedOnResponse(driver);

                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<DetailTransResponseJson> call, @NonNull Throwable t) {
                rlprogress.setVisibility(View.GONE);
                Toast.makeText(ActivityProgress.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateUIBasedOnResponse(DriverModel driver) {
        if ("2".equals(response)) {
            if ("4".equals(gethome)) {
                ImgAmbil.setImageResource(R.drawable.mitra_aktif);
                ImgPickup.setImageResource(R.drawable.adr_merchant);
            } else if ("2".equals(gethome)) {
                ImgAmbil.setImageResource(R.drawable.paket_aktif);
                ImgPickup.setImageResource(R.drawable.adr_pickup);
            } else {
                ImgAmbil.setImageResource(R.drawable.pick_aktif);
                ImgPickup.setImageResource(R.drawable.adr_pickup);
            }
        } else if ("3".equals(response)) {
            BottomSheetBehavior.from(bottomsheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            batalkan.setVisibility(View.GONE);
            if ("4".equals(gethome)) {
                ImgAmbil.setImageResource(R.drawable.mitra_nonaktif);
                ImgAntar.setImageResource(R.drawable.antar_aktif);
            } else if ("2".equals(gethome)) {
                ImgAmbil.setImageResource(R.drawable.paket_nonaktif);
                ImgAntar.setImageResource(R.drawable.antar_aktif);
            } else {
                ImgAmbil.setImageResource(R.drawable.pick_nonaktif);
                ImgAntar.setImageResource(R.drawable.antar_aktif);
            }
        }

        // Finish Transaction
        if ("4".equals(response)) {
            goToRateActivity(driver);
        }

        // Cancel Transaction
        if ("5".equals(response)) {
            if(driver != null) UpdateStatus(driver.getId());
            Toast.makeText(this, "Orderan Anda Telah Dibatalkan.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }


    private void goToRateActivity(DriverModel driver) {
        if (driver == null) return;
        UpdateStatus(driver.getId());
        Toast.makeText(this, "Orderan Anda Telah Selesai.", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, RateActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("id_driver", driver.getId());
        i.putExtra("id_transaksi", idtrans);
        i.putExtra("pakai_wallet", isWallet);
        i.putExtra("fotodriver", imagedriver);
        i.putExtra("namadriver", driver.getNamaDriver());
        startActivity(i);
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void parsedata(TransaksiModel request, final DriverModel driver) {
        rlprogress.setVisibility(View.GONE);

        // HAPUS pendaftaran receiver dari sini
        // registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));

        if(driver != null){
            ldriver.setVisibility(View.VISIBLE);
            namadriver.setText(driver.getNamaDriver());
            platnomor.setText(driver.getNomor_kendaraan());
            merk.setText(driver.getMerek());
            tipe.setText(driver.getTipe());
            IkonDriver = BASE_JOB + list.get(Integer.parseInt(icondriver)) + ".png";

            String baseurl = Constants.IMAGESDRIVER + driver.getFoto();
            Glide.with(this)
                    .asBitmap()
                    .load(baseurl)
                    .circleCrop()
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                    .placeholder(R.drawable.logo)
                    .into(fotodriver);

            phone.setOnClickListener(v -> {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityProgress.this, R.style.DialogStyle);
                alertDialogBuilder.setTitle("Hubungi Driver");
                alertDialogBuilder.setMessage("Apakah Kamu Ingin Menghubungi Driver (+" + driver.getNoTelepon() + ")?");
                alertDialogBuilder.setPositiveButton("Ya", (arg0, arg1) -> {
                    if (ActivityCompat.checkSelfPermission(ActivityProgress.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ActivityProgress.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                        return;
                    }
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + driver.getNoTelepon()));
                    startActivity(callIntent);
                });
                alertDialogBuilder.setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });

            chat.setOnClickListener(v -> {
                Intent intent = new Intent(ActivityProgress.this, ChatActivity.class);
                intent.putExtra("senderid", user.getId());
                intent.putExtra("receiverid", driver.getId());
                intent.putExtra("tokendriver", driver.getRegId());
                intent.putExtra("tokenku", user.getToken());
                intent.putExtra("name", driver.getNamaDriver());
                intent.putExtra("pic", Constants.IMAGESDRIVER + driver.getFoto());
                startActivity(intent);
            });
        }else {
            ldriver.setVisibility(View.GONE);
            StatusDriver.setText("Kami sedang mencarikan driver untuk kamu");
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date orderTime = request.getWaktuOrder();
                long startTime = orderTime.getTime();
                long endTime = System.currentTimeMillis();
                long diff = 30 * 60 * 1000 - (endTime - startTime); // 30 minutes countdown

                if (diff > 0) {
                    new CountDownTimer(diff, 1000) {
                        public void onTick(long millisUntilFinished) {
                            waktu.setText(String.format(Locale.getDefault(), "%02d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                        }
                        public void onFinish() {
                            waktu.setText("00:00");
                            cancelOrder(null);
                        }
                    }.start();
                } else {
                    waktu.setText("00:00");
                    cancelOrder(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pickUpLatLng = new LatLng(request.getStartLatitude(), request.getStartLongitude());
        destinationLatLng = new LatLng(request.getEndLatitude(), request.getEndLongitude());

        if("2".equals(response) || "9".equals(response)){
            batalkan.setOnClickListener(view -> {
                if (isCancelable) {
                    new AlertDialog.Builder(ActivityProgress.this, R.style.DialogStyle)
                            .setTitle("Batalkan Pesanan")
                            .setMessage("Apakah Kamu Ingin Membatalkan Pesanan Saat Ini ?")
                            .setPositiveButton("Ya", (arg0, arg1) -> cancelOrder(driver))
                            .setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss())
                            .show();
                } else {
                    Toast.makeText(ActivityProgress.this, "Tidak Dapat Membatalkan Pesanan.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void cancelOrder(DriverModel driverModel) {
        rlprogress.setVisibility(View.VISIBLE);
        CancelBookRequestJson requestcancel = new CancelBookRequestJson();
        requestcancel.id_transaksi = idtrans;
        if(driverModel != null){
            requestcancel.id_driver = driverModel.getId();
        }

        BookService service = ServiceGenerator.createService(BookService.class, user.getEmail(), user.getPassword());
        service.cancelOrder(requestcancel).enqueue(new Callback<CancelBookResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<CancelBookResponseJson> call, @NonNull Response<CancelBookResponseJson> response) {
                rlprogress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    if ("canceled".equals(response.body().mesage)) {
                        if(driverModel != null){
                            drivercancel(driverModel.getRegId());
                            UpdateStatus(driverModel.getId());
                        }
                        fcmcancelmerchant();
                        Toast.makeText(ActivityProgress.this, "Pesanan Telah Dibatalkan.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ActivityProgress.this, "Gagal membatalkan pesanan.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CancelBookResponseJson> call, @NonNull Throwable t) {
                rlprogress.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(ActivityProgress.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void drivercancel(String token) {
        DriverResponse response = new DriverResponse();
        response.setIdTransaksi(idtrans);
        response.setResponse(DriverResponse.REJECT);
        final User login = BaseApp.getInstance(ActivityProgress.this).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(token);
            param.setData(response);
            service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("ResultFCM", response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<FcmResponse> call, @NonNull Throwable t) {
                    Log.e("TestFCM", t.getMessage());
                }
            });
        }
    }
    private void fcmcancelmerchant() {
        DriverResponse response = new DriverResponse();
        response.setIdTransaksi(idtrans);
        response.setResponse(String.valueOf(Constants.CANCEL));
        final User login = BaseApp.getInstance(ActivityProgress.this).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(tokenmerchant);
            param.setData(response);
            service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("ResultFCM", response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<FcmResponse> call, @NonNull Throwable t) {
                    Log.e("TestFCM", t.getMessage());
                }
            });
        }
    }
    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.cancel(0);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void orderHandler(int code) {
        switch (code) {
            case Constants.REJECT:
            case Constants.CANCEL:
                isCancelable = false;
                break;
            case Constants.ACCEPT:
                isCancelable = true;
                break;
            case Constants.CHANGE:
                isCancelable = true;
                getData(idtrans,iddriver);
                Toast.makeText(ActivityProgress.this, "Harga pesanan kamu sudah disesuaikan oleh driver", Toast.LENGTH_LONG).show();
                break;
            case Constants.START:
                isCancelable = false;
                getData(idtrans,iddriver);
                break;
            case Constants.FINISH:
                isCancelable = false;
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        Log.e("onMessageEvent1", response.getResponse() + " " + response.getId() + " " + response.getIdTransaksi());
        try{
            if (!"true".equals(complete)) {
                if (response.getResponse() != null && !response.getResponse().isEmpty()) {
                    orderHandler(Integer.parseInt(response.getResponse()));
                    EventBus.getDefault().removeStickyEvent(DriverResponse.class);
                }
            }
        } catch (NumberFormatException e) {
            Log.e("onMessageEvent", e.getMessage());
        }
    }

    // =================================== PERBAIKAN UTAMA DI SINI ===================================
// GANTI METODE onResume() LAMA ANDA DENGAN YANG INI

    @Override
    protected void onResume() {
        super.onResume();

        // BENAR: Tambahkan flag keamanan untuk Android 13 (API 33) ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER), Context.RECEIVER_NOT_EXPORTED);
        } else {
            // Untuk versi Android di bawah 13, gunakan metode lama
            registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));
        }

        EventBus.getDefault().register(this);
        removeNotif();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // BENAR: Lepaskan receiver HANYA di onPause
        unregisterReceiver(broadcastReceiver);
        EventBus.getDefault().unregister(this);
        stopDriverLocationUpdate();
    }

    // HAPUS onStop, karena sudah ditangani oleh onPause
    // @Override
    // protected void onStop() {
    //     super.onStop();
    // }

    // ===============================================================================================


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if (googleMap != null) {
            try {
                gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
            } catch (Resources.NotFoundException e) {
                Log.e("MapsActivityRaw", "Can't find style.", e);
            }
            gMap.getUiSettings().setAllGesturesEnabled(true);
            gMap.getUiSettings().setCompassEnabled(false);
            gMap.getUiSettings().setMapToolbarEnabled(false);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gMap.setMyLocationEnabled(false);
                gMap.getUiSettings().setMyLocationButtonEnabled(true);
            }

            if(Lokasiku != null){
                int mHeight = 150;
                int mWidth = 150;
                BitmapDrawable destdraw = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.pin_saya);
                if (destdraw != null) {
                    Bitmap mDest = destdraw.getBitmap();
                    Bitmap MarkerDest = Bitmap.createScaledBitmap(mDest, mWidth, mHeight, false);
                    if (destinationMarker != null) destinationMarker.remove();
                    destinationMarker = gMap.addMarker(new MarkerOptions()
                            .position(Lokasiku)
                            .icon(BitmapDescriptorFactory.fromBitmap(MarkerDest)));
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Lokasiku, 17));
                }
            }
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + MainActivity.apikey;
        return url;
    }

    private void ShowDriver(LatLng latLng,String ikondriver){
        if(isDestroyed() || ikondriver == null) return;
        Glide.with(getApplicationContext()).asBitmap()
                .load(ikondriver)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int height = 150;
                        int width = 150;
                        Bitmap smallMarker = Bitmap.createScaledBitmap(resource, width, height, false);
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                        if (currentLocationMarker == null)
                            currentLocationMarker = gMap.addMarker(new MarkerOptions()
                                    .flat(true)
                                    .icon(icon)
                                    .anchor(0.5f, 0.5f)
                                    .position(latLng));
                        else
                            MarkerAnimation.animateMarkerToGB(currentLocationMarker,latLng, new LatLngInterpolators.Spherical());

                        if(driverlatlng != null){
                            double bearing = bearingBetweenLocations(currentLocationMarker.getPosition(), latLng);
                            rotateMarkers(currentLocationMarker, (float) bearing);
                            CameraPosition cameraPosition2 = new CameraPosition.Builder()
                                    .target(driverlatlng)
                                    .zoom(17)
                                    .bearing( (float) bearing)
                                    .build();
                            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition2 ), 1000, null);
                        }
                    }
                });
    }

    private void rotateMarkers(final Marker marker, final float toRotation) {
        if(marker == null) return;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final float startRotation = marker.getRotation();
        final long duration = 1000;
        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                float rot = t * toRotation + (1 - t) * startRotation;
                marker.setRotation(-rot > 180 ? rot / 2 : rot);
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    private double bearingBetweenLocations(LatLng latLng1, LatLng latLng2) {
        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;
        double dLon = (long2 - long1);
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double brng = Math.toDegrees(Math.atan2(y, x));
        return (brng + 360) % 360;
    }


    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        if(values.length > 0 && values[0] instanceof PolylineOptions){
            currentPolyline = gMap.addPolyline((PolylineOptions) values[0]);
        }
    }
    private String buildRequestUrl(LatLng origin, LatLng destination) {
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String strDestination = "destination=" + destination.latitude + "," + destination.longitude;
        String param = strOrigin + "&" + strDestination + "&sensor=false&mode=driving";
        String output = "json";
        String APIKEY = MainActivity.apikey;
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param + "&key="+APIKEY;
    }

    private String requestDirection(String requestedUrl) {
        String responseString = "";
        try {
            URL url = new URL(requestedUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            responseString = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray routes = jsonObject.getJSONArray("routes");
            if (routes.length() > 0) {
                JSONObject route = routes.getJSONObject(0);
                JSONArray legs = route.getJSONArray("legs");
                JSONObject leg = legs.getJSONObject(0);
                JSONObject duration = leg.getJSONObject("duration");
                String durationText = duration.getString("text");
                runOnUiThread(() -> waktu.setText(durationText.replace("mins", "Menit")));
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public class TaskDirectionRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return requestDirection(strings[0]);
        }
        @Override
        protected void onPostExecute(String responseString) {
            super.onPostExecute(responseString);
            new TaskParseDirection().execute(responseString);
        }
    }

    public class TaskParseDirection extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonString) {
            List<List<HashMap<String, String>>> routes = null;
            try {
                JSONObject jsonObject = new JSONObject(jsonString[0]);
                routes = new DirectionParser().parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);
            if(lists == null) return;
            ArrayList<LatLng> latLngs = new ArrayList<>();
            for (List<HashMap<String, String>> path : lists) {
                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lng"));
                    latLngs.add(new LatLng(lat, lon));
                }
            }
            if (!latLngs.isEmpty()) {
                ShowDriver(latLngs.get(0), IkonDriver);
                if (latLngs.size() < 10) {
                    StatusDriver.setText("Driver Sudah Dekat.");
                } else {
                    StatusDriver.setText("Mohon Ditunggu Yah ...");
                }
            }
        }
    }

    private void startDriverLocationUpdate() {
        handler = new Handler();
        handler.post(updateDriverRunnable);
    }

    private void stopDriverLocationUpdate() {
        if(handler != null){
            handler.removeCallbacks(updateDriverRunnable);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDriverLocationUpdate();
        if (realm != null) {
            realm.close();
        }
    }

    private final Runnable updateDriverRunnable = new Runnable() {
        @Override
        public void run() {
            if (iddriver == null || isDestroyed()) {
                return;
            }

            LokasiDriverRequest param = new LokasiDriverRequest();
            param.setId(iddriver);
            BookService service = ServiceGenerator.createService(BookService.class, "admin", "12345");
            service.liatLokasiDriver(param).enqueue(new Callback<LokasiDriverResponse>() {
                @Override
                public void onResponse(@NonNull Call<LokasiDriverResponse> call, @NonNull Response<LokasiDriverResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                            final LokasiDriverModel latlang = response.body().getData().get(0);
                            driverlatlng = new LatLng(latlang.getLatitude(), latlang.getLongitude());

                            if("4".equals(gethome)){
                                new TaskDirectionRequest().execute(buildRequestUrl(driverlatlng, pickUpLatLng));
                            } else {
                                new TaskDirectionRequest().execute(buildRequestUrl(driverlatlng, destinationLatLng));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LokasiDriverResponse> call, @NonNull Throwable t) {
                    Log.e("TrackDriver","Error: " + t.getMessage());
                }
            });

            // Schedule the next update
            handler.postDelayed(this, 5000); // update every 5 seconds
        }
    };

    private void UpdateStatus(String iddriver){
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) return;
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        SaveStatusRequest request = new SaveStatusRequest();
        request.id = iddriver;
        request.status = "1";
        service.updateStatus(request).enqueue(new Callback<SaveStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveStatusResponse> call, @NonNull Response<SaveStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equals(response.body().mesage)) {
                        Log.d("DriverStatus","Sukses");
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<SaveStatusResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}