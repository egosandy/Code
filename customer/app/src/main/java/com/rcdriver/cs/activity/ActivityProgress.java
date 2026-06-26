package com.rcdriver.cs.activity;

import com.rcdriver.cs.utils.LocalStore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
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
import com.rcdriver.cs.utils.NetworkManager;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rcdriver.cs.constants.Constants.BASE_JOB;
import static com.rcdriver.cs.utils.api.service.MessagingService.BROADCAST_ORDER;

public class ActivityProgress  extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback{
    private static final int REQUEST_PERMISSION_CALL = 992;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReff;
    Timer timer = new Timer();
    Bundle orderBundle;
    String icondriver;
    String gethome;
    String complete,iddriver,idtrans,isWallet,response,fitur,regdriver,imagedriver;
    float bearing;
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
    private ArrayList<String> list = new ArrayList<String>();
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
    private BitmapDrawable pickdraw,destdraw = null;
    private Bitmap mPick,MarkerPick,mDest,MarkerDest = null;
    public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            orderBundle = intent.getExtras();
            orderHandler(Objects.requireNonNull(orderBundle).getInt("code"));
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_order);
        sp = new SettingPreference(this);
        mDatabase = FirebaseDatabase.getInstance();
        mReff = mDatabase.getReference();
        double lat = Double.parseDouble(sp.getSetting()[6]);
        double lng = Double.parseDouble(sp.getSetting()[7]);
        Lokasiku = new LatLng(lat,lng);
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
        Log.d("CekOrder", "Driver : " + iddriver + " Transaksi : " + idtrans + " Response :" + response + " Metode : " + isWallet);
        registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER), Context.RECEIVER_NOT_EXPORTED);

        user = BaseApp.getInstance(this).getLoginUser();
        rlprogress.setVisibility(View.VISIBLE);
        textprogress.setText(getString(R.string.waiting_pleaseWait));
        getData(idtrans,iddriver);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        lwasap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String ponsel = "+" + sp.getSetting()[13];
                    String semuapesan = "Halo, Mohon bantuannya";
                    String url="https://api.whatsapp.com/send?phone="+ponsel + "&text=" + semuapesan;
                    Intent i= new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);
                    if (resolveInfos != null && !resolveInfos.isEmpty()) {
                        // WhatsApp is installed on the device
                        startActivity(i);
                    } else {
                        // WhatsApp is not installed on the device
                        Toast.makeText(ActivityProgress.this, "Whatsapp not installed", Toast.LENGTH_LONG).show();
                    }



            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getData(final String idtrans, final String iddriver) {
        BookService service = ServiceGenerator.createService(BookService.class, user.getEmail(), user.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdDriver(iddriver);
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful()) {
                    final TransaksiModel transaksi = Objects.requireNonNull(responsedata.body()).getData().get(0);
                    FiturModel designedFitur = LocalStore.get().getFitur(Integer.valueOf(transaksi.getOrderFitur()));
                    icondriver = Objects.requireNonNull(designedFitur).getIcon_driver();
                    //--------------------------------------------------------------------------
                    gethome = designedFitur.getHome();
                    response = String.valueOf(transaksi.status);
                    DriverModel driver = null;
                    if(transaksi.status < 9){
                        driver = responsedata.body().getDriver().get(0);
                        regdriver = driver.getRegId();
                        tokenmerchant = transaksi.getToken_merchant();
                        imagedriver = Constants.IMAGESDRIVER + driver.getFoto();
                    }

                    fitur = transaksi.getOrderFitur();
                    if(transaksi.isPakaiWallet()){
                        metode.setText("Saldo");
                    }else{
                        metode.setText("Tunai");
                    }
                    Utility.currencyTXT(txttotal, String.valueOf(transaksi.getBiaya_akhir()), ActivityProgress.this);
                    Utility.currencyTXT(totalbayar, String.valueOf(transaksi.getBiaya_akhir()), ActivityProgress.this);
                    Utility.currencyTXT(ongkir, String.valueOf(transaksi.getHarga()), ActivityProgress.this);
                    if(transaksi.getKreditPromo().equals("0") || transaksi.getKreditPromo() == null){
                        layoutdiskon.setVisibility(View.GONE);
                    }else{
                        Utility.currencyTXT(diskon, String.valueOf(transaksi.getHarga()), ActivityProgress.this);
                    }
                    parsedata(transaksi, driver, designedFitur);
                    new FetchURL(ActivityProgress.this).execute(getUrl(pickUpLatLng, destinationLatLng, "driving"), "driving");
                    //-------------------------------------- Tracking -------------------------------------
                    if(response.equals("3")){
                        startDriverLocationUpdate();
                    }
                    //--------------------------- End Tracking ------------------------------------------------
                    if(gethome.equals("4")){
                        titlePickup.setText(transaksi.getNama_merchant());
                        alamatPickup.setText(transaksi.getAlamat_merchant());
                        titleAmbil.setText("Pemesanan");
                        desAmbil.setText("Memproses.");
                        alamatAntar.setText(transaksi.getAlamatTujuan());
                        detailorder.setVisibility(View.VISIBLE);
                        LayoutMerchant.setVisibility(View.VISIBLE);
                        LayoutKurir.setVisibility(View.GONE);
                        itemPesananItem = new ItemPesananItem(responsedata.body().getItem(), R.layout.item_pesanan,ActivityProgress.this);
                        rvmerchantnear.setAdapter(itemPesananItem);
                        layoutharga.setVisibility(View.VISIBLE);
                        Utility.currencyTXT(harga, String.valueOf(transaksi.getTotal_biaya()), ActivityProgress.this);
                    }else if(gethome.equals("2")){
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
                        senderphone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityProgress.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Hubungi");
                                alertDialogBuilder.setMessage("Apakah Kamu Ingin Menghubungi " + transaksi.getNamaPengirim() + "(+" + transaksi.teleponPengirim + ")?");
                                alertDialogBuilder.setPositiveButton("Ya",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(ActivityProgress.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(ActivityProgress.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }
                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:+" + transaksi.teleponPengirim));
                                                startActivity(callIntent);
                                            }
                                        });
                                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        });
                        receiverphone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityProgress.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Hubungi");
                                alertDialogBuilder.setMessage("Apakah Kamu Ingin Menghubungi " + transaksi.getNamaPenerima() + "(+" + transaksi.teleponPenerima + ")?");
                                alertDialogBuilder.setPositiveButton("Ya",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                if (ActivityCompat.checkSelfPermission(ActivityProgress.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                    ActivityCompat.requestPermissions(ActivityProgress.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                                    return;
                                                }
                                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                                callIntent.setData(Uri.parse("tel:+" + transaksi.teleponPenerima));
                                                startActivity(callIntent);
                                            }
                                        });
                                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
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
                    if(response.equals("2")){

                      //  MapDriver.setVisibility(View.GONE);
                        if(gethome.equals("4")){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.mitra_aktif, getApplicationContext().getTheme()));
                                ImgPickup.setImageDrawable(getResources().getDrawable(R.drawable.adr_merchant, getApplicationContext().getTheme()));
                            } else {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.mitra_aktif));
                                ImgPickup.setImageDrawable(getResources().getDrawable(R.drawable.adr_merchant));
                            }
                        }else if(gethome.equals("2")){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.paket_aktif, getApplicationContext().getTheme()));
                                ImgPickup.setImageDrawable(getResources().getDrawable(R.drawable.adr_pickup, getApplicationContext().getTheme()));
                            } else {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.paket_aktif));
                                ImgPickup.setImageDrawable(getResources().getDrawable(R.drawable.adr_pickup));
                            }

                        }else{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.pick_aktif, getApplicationContext().getTheme()));
                                ImgPickup.setImageDrawable(getResources().getDrawable(R.drawable.adr_pickup, getApplicationContext().getTheme()));
                            } else {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.pick_aktif));
                                ImgPickup.setImageDrawable(getResources().getDrawable(R.drawable.adr_pickup));
                            }
                        }
                    }else if(response.equals("3")){
                        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                     //   MapDriver.setVisibility(View.VISIBLE);
                        batalkan.setVisibility(View.GONE);
                        if(gethome.equals("4")){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.mitra_nonaktif, getApplicationContext().getTheme()));
                                ImgAntar.setImageDrawable(getResources().getDrawable(R.drawable.antar_aktif, getApplicationContext().getTheme()));
                            } else {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.mitra_nonaktif));
                                ImgAntar.setImageDrawable(getResources().getDrawable(R.drawable.antar_aktif));
                            }
                        }else if(gethome.equals("2")){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.paket_nonaktif, getApplicationContext().getTheme()));
                                ImgAntar.setImageDrawable(getResources().getDrawable(R.drawable.antar_aktif, getApplicationContext().getTheme()));
                            } else {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.paket_nonaktif));
                                ImgAntar.setImageDrawable(getResources().getDrawable(R.drawable.antar_aktif));
                            }
                        }else{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.pick_nonaktif, getApplicationContext().getTheme()));
                                ImgAntar.setImageDrawable(getResources().getDrawable(R.drawable.antar_aktif, getApplicationContext().getTheme()));
                            } else {
                                ImgAmbil.setImageDrawable(getResources().getDrawable(R.drawable.pick_nonaktif));
                                ImgAntar.setImageDrawable(getResources().getDrawable(R.drawable.antar_aktif));
                            }
                        }
                    }
                        //------------------------------------ Finish Transaksi ----------------------------
                        if(transaksi.status == 4 && transaksi.getRate().isEmpty()){
                            UpdateStatus(transaksi.getIdDriver());
                            Toasty.info(ActivityProgress.this, "Orderan Anda Telah Selesai.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ActivityProgress.this, RateActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("id_driver", transaksi.getIdDriver());
                            i.putExtra("id_transaksi", transaksi.getId());
                            i.putExtra("total_biaya", transaksi.biaya_akhir);
                            i.putExtra("pakai_wallet", String.valueOf(transaksi.isPakaiWallet()));
                            i.putExtra("fitur", transaksi.getOrderFitur());
                            i.putExtra("fotodriver", driver.getFoto());
                            i.putExtra("namadriver", driver.getNamaDriver());
                            i.putExtra("response", response);
                            startActivity(i);
                            finish();
                        }
                        //----------------------------- Cancel Transaksi ----------------------------------------
                        if(transaksi.status == 5 || response.equals("5")){
                            UpdateStatus(transaksi.getIdDriver());
                            Toasty.info(ActivityProgress.this, "Orderan Anda Telah Dibatalkan.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ActivityProgress.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("Status", "1");
                            startActivity(i);
                            finish();
                        }
                    }
                }

            @Override
            public void onFailure(@NonNull retrofit2.Call<DetailTransResponseJson> call, @NonNull Throwable t) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void parsedata(TransaksiModel request, final DriverModel driver, FiturModel fiturmodel) {
        rlprogress.setVisibility(View.GONE);
        IntentFilter filter = new IntentFilter(BROADCAST_ORDER);
        registerReceiver(broadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED);


        if(request.status < 9){
            ldriver.setVisibility(View.VISIBLE);
            namadriver.setText(driver.getNamaDriver());
            platnomor.setText(driver.getNomor_kendaraan());
            merk.setText(driver.getMerek());
            tipe.setText(driver.getTipe());
            IkonDriver = BASE_JOB + list.get(Integer.parseInt(icondriver)) + ".png";
            String LinkIkon = BASE_JOB + list.get(Integer.parseInt(icondriver)) + ".png";
            Log.d("IkonDriver", LinkIkon);
            String baseurl = Constants.IMAGESDRIVER + driver.getFoto();
            Glide.with(this)
                    .asBitmap()
                    .load(baseurl)
                    .circleCrop()
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .override(1080, 600)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .apply(RequestOptions.placeholderOf(R.drawable.logo))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .into(fotodriver);


            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityProgress.this, R.style.DialogStyle);
                    alertDialogBuilder.setTitle("Hubungi Driver");
                    alertDialogBuilder.setMessage("Apakah Kamu Ingin Menghubungi Driver (+" + driver.getNoTelepon() + ")?");
                    alertDialogBuilder.setPositiveButton("Ya",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    if (ActivityCompat.checkSelfPermission(ActivityProgress.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(ActivityProgress.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                        return;
                                    }
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:+" + driver.getNoTelepon()));
                                    startActivity(callIntent);
                                }
                            });
                    alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityProgress.this, ChatActivity.class);
                    intent.putExtra("senderid", user.getId());
                    intent.putExtra("receiverid", driver.getId());
                    intent.putExtra("tokendriver", driver.getRegId());
                    intent.putExtra("tokenku", user.getToken());
                    intent.putExtra("name", driver.getNamaDriver());
                    intent.putExtra("pic", Constants.IMAGESDRIVER + driver.getFoto());
                    startActivity(intent);
                }
            });
        }else {
            ldriver.setVisibility(View.GONE);
            StatusDriver.setText("Kami sedang mencarikan driver untuk kamu");
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String finalDate = timeFormat.format(request.getWaktuOrder());
            Date dateEnd = new Date();
            String startDate = timeFormat.format(dateEnd);


            long diff = 0;
            long diffSecond = 0;
            long diffMinute = 0;
            try {
                diff = (Objects.requireNonNull(timeFormat.parse(finalDate)).getTime() + 1000 * 60 * 30) - Objects.requireNonNull(timeFormat.parse(startDate)).getTime();
                diffSecond = diff / 1000;
                diffMinute = diffSecond / 60;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("DIFFF", diffMinute + "---" + diff + "---" + startDate + "----" + finalDate);
            long duration = diffMinute * 60 * 1000;
            new CountDownTimer(duration, 1000) {
                public void onTick(long millisUntilFinished) {
                    waktu.setText(String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }
                public void onFinish() {
                    cancelOrder(driver);
                }
            }.start();
//            if(diffMinute < 30){
//                long duration = diff - (diffMinute * 60 * 1000);

//            }

        }

        pickUpLatLng = new LatLng(request.getStartLatitude(), request.getStartLongitude());
        destinationLatLng = new LatLng(request.getEndLatitude(), request.getEndLongitude());


        if(response != null){
            if(response.equals("2") || response.equals("9")){
                batalkan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isCancelable) {
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityProgress.this, R.style.DialogStyle);
                            alertDialogBuilder.setTitle("Batalkan Pesanan");
                            alertDialogBuilder.setMessage("Apakah Kamu Ingin Membatalkan Pesanan Saat Ini ?");
                            alertDialogBuilder.setPositiveButton("Ya",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            cancelOrder(driver);
                                        }
                                    });

                            alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            Toasty.info(ActivityProgress.this, "Tidak Dapat Membatalkan Pesanan.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
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
                if (response.isSuccessful()) {
                    Log.d("CancelOrder",Objects.requireNonNull(response.body()).mesage);
                    if (Objects.requireNonNull(response.body()).mesage.equals("canceled")) {
                        rlprogress.setVisibility(View.GONE);
                        if(driverModel != null){
                            drivercancel(driverModel.getRegId());
                            UpdateStatus(driverModel.getId());
                        }
                        fcmcancelmerchant();
                        Toasty.info(ActivityProgress.this, "Pesanan Telah Dibatalkan.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toasty.info(ActivityProgress.this, "Gagal.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CancelBookResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
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
    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void orderHandler(int code) {
        switch (code) {
            case Constants.REJECT:
                isCancelable = false;
                break;
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
            if (!complete.equals("true")) {
                if (response.getResponse() != null) {
                    orderHandler(Integer.parseInt(response.getResponse()));
                    DriverResponse responses = new DriverResponse();
                    responses.setId("");
                    responses.setIdTransaksi("");
                    responses.setResponse("");
                    EventBus.getDefault().postSticky(responses);
                }

            }
        } catch (NumberFormatException e) {
            Log.e("onMessageEvent",e.getMessage());
            e.printStackTrace();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        iddriver = intent.getStringExtra("id_driver");
        idtrans = intent.getStringExtra("id_transaksi");
        isWallet = intent.getStringExtra("pakai_wallet");
        response = intent.getStringExtra("response");
        try{
            if (response.equals("2") || response.equals("3") && !fitur.equals("0")) {
                registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));
                EventBus.getDefault().register(this);
            }
            registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ORDER));
            removeNotif();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if (googleMap != null) {
            gMap = googleMap;
            try {
                boolean success = gMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                this, R.raw.style_json));
                if (!success) {
                    Log.e("MapsActivityRaw", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("MapsActivityRaw", "Can't find style.", e);
            }
            gMap.getUiSettings().setAllGesturesEnabled(true);
            gMap.getUiSettings().setScrollGesturesEnabled(true);
            gMap.getUiSettings().setCompassEnabled(false);
            gMap.getUiSettings().setMapToolbarEnabled(false);
            View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            rlp.setMargins(0, 10, 180, 0);
            if (ActivityCompat.checkSelfPermission(ActivityProgress.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityProgress.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            gMap.setMyLocationEnabled(false);
            gMap.getUiSettings().setMyLocationButtonEnabled(true);
            int mHeight = 150;
            int mWidth = 150;
            BitmapDrawable destdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.pin_saya);
            Bitmap mDest = destdraw.getBitmap();
            Bitmap MarkerDest = Bitmap.createScaledBitmap(mDest, mWidth, mHeight, false);
            if(Lokasiku != null){
                if (destinationMarker != null)
                    destinationMarker.remove();
                destinationMarker = gMap.addMarker(new MarkerOptions()
                        .position(Lokasiku)
                        .icon(BitmapDescriptorFactory.fromBitmap(MarkerDest)));
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Lokasiku, 17));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(Lokasiku)
                        .zoom(17)
                        .build();
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }
    //--------------- Tracking -------------------------------------------------------
    private LatLng midPoint(double lat1, double long1, double lat2,double long2)
    {
        return new LatLng((lat1+lat2)/2, (long1+long2)/2);
    }
    private double angleBteweenCoordinate(double lat1, double long1, double lat2, double long2) {
        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        brng = 360 - brng;

        return brng;
    }
    //---------------------- Kebutuhan Map -----------------------------------------------

    private void rotateMarkers(final Marker marker, final float toRotation) {
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
                    // Post again 16ms later.
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
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }
    //--------------------------------------------------------------------------------
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + MainActivity.apikey;
        return url;
    }
    //-----------------------------------------------------------------------------------------
    private void ShowDriver(LatLng latLng,String ikondriver){
        Log.d("IkonDriver", ikondriver);
        Glide.with(getApplicationContext()).asBitmap()
                .load(ikondriver)
                .override(1080, 600)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int height = 150;
                        int width = 150;
                        Bitmap smallMarker = Bitmap.createScaledBitmap(resource, width, height, false);
                        BitmapDescriptor IkonDriver = BitmapDescriptorFactory.fromBitmap(smallMarker);

                        if (currentLocationMarker == null)
                            currentLocationMarker = gMap.addMarker(new MarkerOptions()
                                    .flat(true)
                                    .icon(IkonDriver)
                                    .anchor(0.5f, 0.5f)
                                    .position(latLng));
                        else
                            MarkerAnimation.animateMarkerToGB(currentLocationMarker,latLng, new LatLngInterpolators.Spherical());
                        double bearing = bearingBetweenLocations(currentLocationMarker.getPosition(), latLng);
                        rotateMarkers(currentLocationMarker, (float) bearing);
                        CameraPosition cameraPosition2 = new CameraPosition.Builder()
                                .target(driverlatlng)
                                .zoom(17)
                                .bearing( (float) bearing)
                                .build();
                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition2 ),
                                8000, null);
                    }
                });
    }
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = gMap.addPolyline((PolylineOptions) values[0]);
        int mHeight = 90;
        int mWidth = 90;
        if(response != null){
            if(gethome.equals("4")){
                if(fitur.equals("21")){
                    pickdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.map_food);
                    mPick = pickdraw.getBitmap();
                    MarkerPick = Bitmap.createScaledBitmap(mPick, mWidth, mHeight, false);
                    if (pickUpMarker != null)
                        pickUpMarker.remove();
                    pickUpMarker = gMap.addMarker(new MarkerOptions()
                            .position(currentPolyline.getPoints().get(0))
                            .icon(BitmapDescriptorFactory.fromBitmap(MarkerPick)));
                }else{
                    pickdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.map_ambil);
                    mPick = pickdraw.getBitmap();
                    MarkerPick = Bitmap.createScaledBitmap(mPick, mWidth, mHeight, false);
                    if (pickUpMarker != null)
                        pickUpMarker.remove();
                    pickUpMarker = gMap.addMarker(new MarkerOptions()
                            .position(currentPolyline.getPoints().get(0))
                            .icon(BitmapDescriptorFactory.fromBitmap(MarkerPick)));
                }
            }else if(gethome.equals("2")){
                if(fitur.equals("21")){
                    pickdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.map_delivery);
                    mPick = pickdraw.getBitmap();
                    MarkerPick = Bitmap.createScaledBitmap(mPick, mWidth, mHeight, false);
                    if (pickUpMarker != null)
                        pickUpMarker.remove();
                    pickUpMarker = gMap.addMarker(new MarkerOptions()
                            .position(currentPolyline.getPoints().get(0))
                            .icon(BitmapDescriptorFactory.fromBitmap(MarkerPick)));
                }else{
                    pickdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.map_ambil);
                    mPick = pickdraw.getBitmap();
                    MarkerPick = Bitmap.createScaledBitmap(mPick, mWidth, mHeight, false);
                    if (pickUpMarker != null)
                        pickUpMarker.remove();
                    pickUpMarker = gMap.addMarker(new MarkerOptions()
                            .position(currentPolyline.getPoints().get(0))
                            .icon(BitmapDescriptorFactory.fromBitmap(MarkerPick)));
                }
            }else {
                pickdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.map_ambil);
                mPick = pickdraw.getBitmap();
                MarkerPick = Bitmap.createScaledBitmap(mPick, mWidth, mHeight, false);
                if (pickUpMarker != null)
                    pickUpMarker.remove();
                pickUpMarker = gMap.addMarker(new MarkerOptions()
                        .position(currentPolyline.getPoints().get(0))
                        .icon(BitmapDescriptorFactory.fromBitmap(MarkerPick)));
            }

            destdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.map_tujuan);
            mDest = destdraw.getBitmap();
            MarkerDest = Bitmap.createScaledBitmap(mDest, mWidth, mHeight, false);
            if (destinationMarker != null)
                destinationMarker.remove();
            destinationMarker = gMap.addMarker(new MarkerOptions()
                    .position(currentPolyline.getPoints().get(currentPolyline.getPoints().size() - 1))
                    .icon(BitmapDescriptorFactory.fromBitmap(MarkerDest)));
        }
    }
    //-----------------------------------------------------------------------------------------
    private String buildRequestUrl(LatLng origin, LatLng destination) {
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String strDestination = "destination=" + destination.latitude + "," + destination.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";

        String param = strOrigin + "&" + strDestination + "&" + sensor + "&" + mode;
        String output = "json";
        String APIKEY = MainActivity.apikey;

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param + "&key="+APIKEY;
        Log.d("TAG", url);
        return url;
    }

    private String requestDirection(String requestedUrl) {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(requestedUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            responseString = stringBuffer.toString();
            JSONObject json = new JSONObject(responseString);
            JSONArray jArray = json.getJSONArray("routes");
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                JSONArray jarray = json_data.getJSONArray("legs");
                JSONObject jobjek = jarray.getJSONObject(0).getJSONObject("duration");
                Log.e("RuteMap", "Leg : " + jobjek.get("text"));
                String mWaktu = jobjek.get("text").toString();
                String finalwaktu = mWaktu.replaceAll("mins","Menit");
                waktu.setText(mWaktu);
            }
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        httpURLConnection.disconnect();
        return responseString;
    }

    //Get JSON data from Google Direction
    public class TaskDirectionRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String responseString) {
            super.onPostExecute(responseString);
            TaskParseDirection parseResult = new TaskParseDirection();
            parseResult.execute(responseString);
        }
    }
    //Parse JSON Object from Google Direction API & display it on Map
    public class TaskParseDirection extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonString) {
            List<List<HashMap<String, String>>> routes = null;
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(jsonString[0]);
                DirectionParser parser = new DirectionParser();
                routes = parser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);
            ArrayList points = null;
            ArrayList<LatLng> latLngs = new ArrayList<>();
            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lng"));
                    points.add(new LatLng(lat, lon));
                }
                latLngs.addAll(points);
                if(latLngs.size() < 5) {
                    ShowDriver(latLngs.get(0), IkonDriver);
                    StatusDriver.setText("Driver Sudah Ditempat Tujuan.");
                }else if(latLngs.size() < 10){
                    ShowDriver(latLngs.get(0), IkonDriver);
                    StatusDriver.setText("Driver Sudah Dekat Ditempat Tujuan.");
                }else{
                    ShowDriver(latLngs.get(0),IkonDriver);
                    StatusDriver.setText("Mohon Ditunggu Yah ...");
                }
            }
        }
    }
    private void startDriverLocationUpdate() {
        handler = new Handler();
        handler.postDelayed(updateDriverRunnable, 3000);
    }

    private void stopDriverLocationUpdate() {
        handler.removeCallbacks(updateDriverRunnable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDriverLocationUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopDriverLocationUpdate();
    }
    //------------------------------------- Lokasi Driver -----------------------------------------
    private final Runnable updateDriverRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LokasiDriverRequest param = new LokasiDriverRequest();
                        final BookService service = ServiceGenerator.createService(BookService.class, "admin", "12345");
                        param.setId(iddriver);
                        service.liatLokasiDriver(param).enqueue(new Callback<LokasiDriverResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<LokasiDriverResponse> call, @NonNull Response<LokasiDriverResponse> response) {
                                if (response.isSuccessful()) {
                                    final LokasiDriverModel latlang = Objects.requireNonNull(response.body()).getData().get(0);
                                    final LatLng location = new LatLng(latlang.getLatitude(), latlang.getLongitude());
                                    driverlatlng = location;
                                    Log.d("LokasiDriver",latlang.getLatitude() + "," + latlang.getLongitude());
                                    if(driverlatlng == null){
                                        int mHeight = 150;
                                        int mWidth = 150;
                                        BitmapDrawable destdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.pin_saya);
                                        Bitmap mDest = destdraw.getBitmap();
                                        Bitmap MarkerDest = Bitmap.createScaledBitmap(mDest, mWidth, mHeight, false);
                                        if (destinationMarker != null)
                                            destinationMarker.remove();
                                        destinationMarker = gMap.addMarker(new MarkerOptions()
                                                .position(Lokasiku)
                                                .icon(BitmapDescriptorFactory.fromBitmap(MarkerDest)));
                                        CameraPosition cameraPosition2 = new CameraPosition.Builder()
                                                .target(Lokasiku)
                                                .zoom(17)
                                                .bearing(latlang.getBearing())
                                                .build();
                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition2 ),
                                                8000, null);
                                    }
                                    if(gethome.equals("4")){
                                        new TaskDirectionRequest().execute(buildRequestUrl(driverlatlng,pickUpLatLng));
                                    }else{
                                        new TaskDirectionRequest().execute(buildRequestUrl(driverlatlng,destinationLatLng));
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<LokasiDriverResponse> call, @NonNull Throwable t) {
                                Log.e("TrackDriver","Error Driver");
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (NetworkManager.isConnectToInternet(ActivityProgress.this)) {
                                try {
                                    LokasiDriverRequest param = new LokasiDriverRequest();
                                    final BookService service = ServiceGenerator.createService(BookService.class, "admin", "12345");
                                    param.setId(iddriver);
                                    service.liatLokasiDriver(param).enqueue(new Callback<LokasiDriverResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<LokasiDriverResponse> call, @NonNull Response<LokasiDriverResponse> response) {
                                            if (response.isSuccessful()) {
                                                final LokasiDriverModel latlang = Objects.requireNonNull(response.body()).getData().get(0);
                                                final LatLng location = new LatLng(latlang.getLatitude(), latlang.getLongitude());
                                                driverlatlng = location;
                                                if(driverlatlng == null){
                                                    int mHeight = 150;
                                                    int mWidth = 150;
                                                    BitmapDrawable destdraw = (BitmapDrawable) ContextCompat.getDrawable(ActivityProgress.this, R.drawable.pin_saya);
                                                    Bitmap mDest = destdraw.getBitmap();
                                                    Bitmap MarkerDest = Bitmap.createScaledBitmap(mDest, mWidth, mHeight, false);
                                                    if (destinationMarker != null)
                                                        destinationMarker.remove();
                                                    destinationMarker = gMap.addMarker(new MarkerOptions()
                                                            .position(Lokasiku)
                                                            .icon(BitmapDescriptorFactory.fromBitmap(MarkerDest)));
                                                    CameraPosition cameraPosition2 = new CameraPosition.Builder()
                                                            .target(Lokasiku)
                                                            .zoom(17)
                                                            .bearing(latlang.getBearing())
                                                            .build();
                                                    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition2 ),
                                                            8000, null);
                                                }
                                                if(gethome.equals("4")){
                                                    new TaskDirectionRequest().execute(buildRequestUrl(driverlatlng,pickUpLatLng));
                                                }else{
                                                    new TaskDirectionRequest().execute(buildRequestUrl(driverlatlng,destinationLatLng));
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<LokasiDriverResponse> call, @NonNull Throwable t) {
                                            Log.e("TrackDriver","Error Driver");
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, 0, 3000);
                }
            }).start();
        }
    };
//---------------------------------- Update status ------------------------------------
    private void UpdateStatus(String iddriver){
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        SaveStatusRequest request = new SaveStatusRequest();
        request.id = iddriver;
        request.status = "1";
        service.updateStatus(request).enqueue(new Callback<SaveStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveStatusResponse> call, @NonNull Response<SaveStatusResponse> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                        Log.d("DriverStatus","Sukses");
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<SaveStatusResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                android.util.Log.e("DriverStatus", t.getMessage());
            }
        });
    }

    //Create method appInstalledOrNot
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
}
