package com.rcdriver.cs.activity;

import com.rcdriver.cs.utils.LocalStore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rcdriver.cs.activity.payment.TopupSaldoActivity;
import com.rcdriver.cs.adapter.ListDriverAdapter;
import com.rcdriver.cs.adapter.ListDriverClick;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.rcdriver.cs.json.fcm.CancelBookRequestJson;
import com.rcdriver.cs.json.fcm.CancelBookResponseJson;
import com.rcdriver.cs.models.OrderFCM;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.item.ItemItem;
import com.rcdriver.cs.json.CheckStatusTransaksiRequest;
import com.rcdriver.cs.json.CheckStatusTransaksiResponse;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.GetNearRideCarRequestJson;
import com.rcdriver.cs.json.GetNearRideCarResponseJson;
import com.rcdriver.cs.json.ItemRequestJson;
import com.rcdriver.cs.json.PromoRequestJson;
import com.rcdriver.cs.json.PromoResponseJson;
import com.rcdriver.cs.json.RideCarResponseJson;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.json.fcm.DriverRequest;
import com.rcdriver.cs.json.fcm.DriverResponse;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.ItemModel;
import com.rcdriver.cs.models.PesananMerchant;
import com.rcdriver.cs.models.TransaksiModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.models.VoucherModel;
import com.rcdriver.cs.utils.NetworkManager;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.MapDirectionAPI;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rcdriver.cs.json.fcm.FCMType.ORDER;

public class DetailOrderActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ItemItem.OnCalculatePrice {
    public static final String FITUR_KEY = "FiturKey";
    public static String Pesanan;
    private static long finalBiayaTotalpay;
    TextView location, orderprice, deliveryfee, diskon, total, diskontext, topuptext, textnotif, saldotext;
    Button order;
    RecyclerView rvmerchantnear;
    LinearLayout llcheckedcash;
    LinearLayout llcheckedwallet;
    ImageButton checkedcash, checkedwallet;
    RelativeLayout rlprogress;
    Thread thread;
    boolean threadRun = true;
    TransaksiModel transaksi;
    TextView cashpayment, walletpayment,numdiskon, textJarak;
    String biayaminimum;
    String getbiaya;
    String biayaakhir;
    String alamat;
    public static String xTotalBiaya;
    double lat, lon, merlat, merlon, distance;
    ImageView backbtn;
    int fitur;
    SettingPreference sp;
    RelativeLayout rlnotif;
    EditText promokode;
    String home, layanan, keterangan, icon;
    Button btnpromo;
    TextView TxtQR;
    CardView layoutQR;
    Button QuickOrder,btnwhatsapp;
    ProgressBar Bar;
    Handler handler;
    double km;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private DriverRequest request;
    private double jarak;
    private long harga, promocode;
    private FiturModel designedFitur;
    private LatLng pickUpLatLang;
    private LatLng destinationLatLang;
    private FastItemAdapter<ItemItem> itemAdapter;
    private List<DriverModel> driverAvailable;
    private long foodCostLong = 0, maksimum;
    private long deliveryCostLong = 0;
    private String IDFITUR = "-1";
    private String saldoWallet, checkedpaywallet, checkedpaycash, idresto, alamatresto, namamerchant, back;
    //qrcode
    private RecyclerView mRecycler;
    private ScrollView sview;
    private RecyclerView.LayoutManager mManager;
    private List<VoucherModel> mItems = new ArrayList<>();
    private double Radius;
    private LinearLayout layoutpick;
    private String Waktu;
    private LinearLayout driverLayout;
    private RecyclerView mDriverRec;
    private ImageView closeDriver;
    private ListDriverAdapter dAdapter;
    private final okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {
        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {
                final String json = Objects.requireNonNull(response.body()).string();
                final long distancetext = MapDirectionAPI.getDistance(DetailOrderActivity.this, json);
                if (distance >= 0) {
                    DetailOrderActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String format = String.format(Locale.US, "%.0f", ((float) (distancetext)) / 1000f);
                            long dist = Long.parseLong(format);
                            promocode = Long.parseLong(Constants.Potongan);
                            promokode.setText("");
                            distance = ((float) (distancetext)) / 1000f;
                            updateDistance();
                            if (dist < maksimum) {
                                order.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (readyToOrder()) {
                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    switch (which){
                                                        case DialogInterface.BUTTON_POSITIVE:
                                                            sendOrder();
                                                            back = "1";
                                                            break;
                                                        case DialogInterface.BUTTON_NEGATIVE:
                                                            driverLayout.setVisibility(View.VISIBLE);
                                                            break;
                                                    }
                                                }
                                            };

                                            AlertDialog.Builder builder = new AlertDialog.Builder(DetailOrderActivity.this);
                                            builder.setMessage("PILIH METODE PEMESANAN")
                                                    .setPositiveButton("Otomatis", dialogClickListener)
                                                    .setNegativeButton("Pilih Driver", dialogClickListener).show();

                                        }
                                    }
                                });

                                QuickOrder.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (readyToOrder()) {
                                            layoutQR.setVisibility(View.VISIBLE);
                                            back = "1";
                                        }
                                    }
                                });
                            } else {
                                notif("tujuan yang terlalu jauh!");
                                order.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (readyToOrder()) {
                                            notif("tujuan yang terlalu jauh!");
                                            back = "0";
                                        }
                                    }
                                });
                                QuickOrder.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (readyToOrder()) {
                                            notif("tujuan yang terlalu jauh!");
                                            back = "0";
                                        }
                                    }
                                });

                            }

                        }
                    });
                }
            }
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        //   this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setupGoogleAPI();
        Waktu = new Date().getTime() + "";
        Log.d("WaktuOrder",Waktu);
        promocode = Long.parseLong(Constants.Potongan);
        sp = new SettingPreference(this);
        Bar = findViewById(R.id.bar);
        //qrcode
        layoutQR = findViewById(R.id.LayoutQR);
        TxtQR = findViewById(R.id.txtqr);
        QuickOrder = findViewById(R.id.quickorders);
        //qrcode
        btnwhatsapp = findViewById(R.id.whatsapp);
        rvmerchantnear = findViewById(R.id.merchantnear);
        location = findViewById(R.id.pickUpText);
        orderprice = findViewById(R.id.orderprice);
        llcheckedcash = findViewById(R.id.llcheckedcash);
        llcheckedwallet = findViewById(R.id.llcheckedwallet);
        cashpayment = findViewById(R.id.cashPayment);
        walletpayment = findViewById(R.id.walletpayment);
        deliveryfee = findViewById(R.id.cost);
        checkedcash = findViewById(R.id.checkedcash);
        checkedwallet = findViewById(R.id.checkedwallet);
        total = findViewById(R.id.price);
        diskon = findViewById(R.id.diskon);
        numdiskon = findViewById(R.id.numdiskon);
        backbtn = findViewById(R.id.back_btn);
        diskontext = findViewById(R.id.ketsaldo);
        topuptext = findViewById(R.id.topUp);
        order = findViewById(R.id.order);
        rlprogress = findViewById(R.id.rlprogress);
        textnotif = findViewById(R.id.textnotif);
        rlnotif = findViewById(R.id.rlnotif);
        saldotext = findViewById(R.id.saldo);
        promokode = findViewById(R.id.promocode);
        btnpromo = findViewById(R.id.btnpromo);
        textJarak = findViewById(R.id.ongkir);
        back = "0";
        layoutpick = findViewById(R.id.layoutpick);
        Pesanan = "";
        driverAvailable = new ArrayList<>();
        fitur = 0;
        mRecycler = (RecyclerView) findViewById(R.id.recyclerTemp);
        sview = (ScrollView) findViewById(R.id.adpPoint);
        sview.setScrollbarFadingEnabled(false);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemViewCacheSize(20);
        mRecycler.setDrawingCacheEnabled(true);
        mRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        driverLayout = findViewById(R.id.Step3);
        mDriverRec = findViewById(R.id.mDriverRec);
        closeDriver = findViewById(R.id.close_driver);

        closeDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverLayout.setVisibility(View.GONE);
            }
        });

        User userLogin = BaseApp.getInstance(this).getLoginUser();
        topuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TopupSaldoActivity.class));
            }
        });
        //start pick lokasi
        layoutpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailOrderActivity.this, ActivityAmbil.class);
                i.putExtra("Lat",sp.getSetting()[6]);
                i.putExtra("Lng",sp.getSetting()[7]);
                i.putExtra("radius",Radius);
                i.putExtra(ActivityAmbil.FORM_VIEW_INDICATOR, 2);
                startActivityForResult(i, 2);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailOrderActivity.this, ActivityAmbil.class);
                i.putExtra("Lat",sp.getSetting()[6]);
                i.putExtra("Lng",sp.getSetting()[7]);
                i.putExtra("radius",Radius);
                i.putExtra(ActivityAmbil.FORM_VIEW_INDICATOR, 2);
                startActivityForResult(i, 2);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sp = new SettingPreference(this);
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0);
        lon = intent.getDoubleExtra("lon", 0);
        merlat = intent.getDoubleExtra("merlat", 0);
        merlon = intent.getDoubleExtra("merlon", 0);
        distance = intent.getDoubleExtra("distance", 0);
        alamatresto = intent.getStringExtra("alamatresto");
        idresto = intent.getStringExtra("idresto");
        alamat = intent.getStringExtra("alamat");
        namamerchant = intent.getStringExtra("namamerchant");
        fitur = intent.getIntExtra(FITUR_KEY, -1);
        if (fitur != -1)
            designedFitur = LocalStore.get().getFitur(fitur);
        if (designedFitur == null) {
            android.widget.Toast.makeText(this, "Data fitur belum siap. Buka ulang dari beranda.", android.widget.Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        home = designedFitur.getHome();
        IDFITUR = String.valueOf(designedFitur.getIdFitur());
        layanan = designedFitur.getFitur();
        keterangan = designedFitur.getKeterangan();
        icon = designedFitur.getIcon();
        textJarak.setText(distance + "");

        Log.d("CekFitur",IDFITUR);
        List<FiturModel> fiturs = LocalStore.get().getAllFitur();

        for (FiturModel fitur : fiturs) {
            Log.e("ID_FITUR", fitur.getIdFitur() + " " + fitur.getFitur() + " " + fitur.getBiayaAkhir());
        }
        getbiaya = String.valueOf(designedFitur.getBiaya());
        biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
        biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
        maksimum = Long.parseLong(designedFitur.getMaksimumdist());
        Radius = Double.parseDouble(designedFitur.getMaksimumdist());
        diskontext.setText("Dapatkan Potongan " + designedFitur.getDiskon() + " Pakai GoSaldo.");
        total.setText("wait");
        deliveryfee.setText("wait");

        Utility.currencyTXT(diskon, String.valueOf(promocode), DetailOrderActivity.this);
        numdiskon.setText(String.valueOf(promocode));
        if(userLogin.getWalletSaldo() == 0){
            saldoWallet = sp.getSetting()[5];
        }else{
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
        }
        lat = Double.parseDouble(sp.getSetting()[6]);
        lon = Double.parseDouble(sp.getSetting()[7]);
        String Alamat = sp.getSetting()[8];
        location.setText(Alamat);
        pickUpLatLang = new LatLng(merlat, merlon);
        destinationLatLang = new LatLng(lat, lon);
        driverTerdekat(merlat,merlon,IDFITUR);
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
                    sukses("Promo Berhasil Digunakan.");
                }
            }
        });


        itemAdapter = new FastItemAdapter<>();
        itemAdapter.notifyDataSetChanged();
        itemAdapter.withSelectable(true);
        itemAdapter.withItemEvent(new ClickEventHook<ItemItem>() {
            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof ItemItem.ViewHolder) {
                    return ((ItemItem.ViewHolder) viewHolder).itemView;
                }
                return null;
            }

            @Override
            public void onClick(View v, int position, FastAdapter<ItemItem> fastAdapter, ItemItem item) {
                //sheetlist(position);
            }
        });
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this));
        rvmerchantnear.setAdapter(itemAdapter);
        updateEstimatedItemCost();
        loadItem();

        final User user = BaseApp.getInstance(this).getLoginUser();
        btnwhatsapp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
                try {
                    if (isWhatsappInstalled) {
                        String mobile = "628991585001";
                        String lokasi = "http://maps.google.com/maps?daddr=" + lat + "," + lon;
                        String lok = "http://maps.google.com/maps?saddr=" + merlat + "," + merlon + "&daddr=" + lat + "," + lon;
                        String msg = Pesanan + "\nBiaya : " + orderprice.getText().toString() + "\nNama Pemesan : " + user.getFullnama() + "\nKontak Pemesan : " + user.getNoTelepon() + "\nMap : " + lokasi;
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + mobile + "&text=" + msg)));
                    } else {
                        Toast.makeText(DetailOrderActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                        Uri uri = Uri.parse("market://details?id=com.whatsapp");
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(goToMarket);

                    }
                }catch (Exception e){

                }
            }
        });
    }

    public void sukses(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
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

    private boolean readyToOrder() {
        if (destinationLatLang == null) {
            Toast.makeText(this, "Please select your location.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (total.getText().toString().isEmpty() || total.getText().toString().equals("wait")) {
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
            return false;
        }

        List<PesananMerchant> existingFood = LocalStore.get().getAllCart();

        int quantity = 0;
        for (int p = 0; p < existingFood.size(); p++) {
            quantity += existingFood.get(p).getQty();
        }

        if (quantity == 0) {
            Toast.makeText(this, "Please order at least 1 item.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (jarak == -99.0) {
            Toast.makeText(this, "Please wait a moment...", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String addressset = data.getStringExtra(ActivityAmbil.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(ActivityAmbil.LOCATION_LATLNG);
                location.setText(addressset);
                destinationLatLang = new LatLng(Objects.requireNonNull(latLng).latitude, latLng.longitude);
                if (pickUpLatLang != null) {
                    MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
                    updateDistance();
                }
            }
        }
    }
    private static String formatRupiah(Long number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
    private void loadItem() {
        List<ItemModel> makananList = LocalStore.get().getAllItems();
        List<PesananMerchant> pesananFoods = LocalStore.get().getAllCart();
        itemAdapter.clear();
        for (PesananMerchant pesanan : pesananFoods) {
            ItemItem makananItem = new ItemItem(this, this);
            if(pesanan.getTipe() == 1){
                for (ItemModel makanan : makananList) {
                    makananItem.quantity = pesanan.getQty();
                    makananItem.id = pesanan.getIdItem();
                    makananItem.namaMenu = pesanan.getNamaPesanan();
                    makananItem.deskripsiMenu = "";
                    makananItem.foto = "";
                    makananItem.harga = Long.parseLong(String.valueOf(pesanan.getTotalHarga()));
                    makananItem.promo = "0";
                    makananItem.catatan = "";
                    String zFormat = formatRupiah(Long.parseLong(makanan.getHarga_item()));
                    String ValFormat = zFormat.replaceAll(",00", "");
                    Pesanan = "Gojasa Order\n---------------------------------------" + "\nMenu : " + makanan.getNama_item() + "\nJmlh : " + pesanan.getQty() + "\nHarga : " + ValFormat;
                    break;
                }
            }else {
                for (ItemModel makanan : makananList) {
                    if (makanan.getId_item() == pesanan.getIdItem()) {
                        makananItem.quantity = pesanan.getQty();
                        makananItem.id = makanan.getId_item();
                        makananItem.namaMenu = makanan.getNama_item();
                        makananItem.deskripsiMenu = makanan.getDeskripsi_item();
                        makananItem.foto = makanan.getFoto_item();
                        makananItem.harga = Long.parseLong(makanan.getHarga_item());
                        makananItem.promo = makanan.getStatus_promo();
                        if (makanan.getHarga_promo().isEmpty()) {
                            makananItem.hargapromo = 0;
                        } else {
                            makananItem.hargapromo = Long.parseLong(makanan.getHarga_promo());
                        }
                        makananItem.catatan = pesanan.getCatatan();
                        String zFormat = formatRupiah(Long.parseLong(makanan.getHarga_item()));
                        String ValFormat = zFormat.replaceAll(",00", "");
                        Pesanan = "Gojasa Order\n---------------------------------------" + "\nMenu : " + makanan.getNama_item() + "\nJmlh : " + pesanan.getQty() + "\nHarga : " + ValFormat;
                        break;
                    }

                }
            }

            itemAdapter.add(makananItem);

        }
        itemAdapter.notifyDataSetChanged();

    }
    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
    @SuppressLint("SetTextI18n")
    private void promokodedata() {
        btnpromo.setEnabled(false);
        btnpromo.setText("Wait...");
        final User user = BaseApp.getInstance(this).getLoginUser();
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(String.valueOf(fitur));
        request.setCode(promokode.getText().toString());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<PromoResponseJson> call, @NonNull Response<PromoResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        finalBiayaTotalpay = foodCostLong + harga;finalBiayaTotalpay = foodCostLong + harga;
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        if (response.body().getType().equals("persen")) {
                            long Total = (Long.parseLong(response.body().getNominal()) * finalBiayaTotalpay) / 100;
                            long Potongan = Long.parseLong(Constants.Potongan);
                            long Kalkulasi = Total - Potongan;
                            promocode = Kalkulasi;
                        } else {
                            long Total = Long.parseLong(response.body().getNominal());
                            long Potongan = Long.parseLong(Constants.Potongan);
                            long Kalkulasi = Total - Potongan;
                            promocode = Kalkulasi;
                        }
                        updateDistance();
                    } else {
                        notif("promo code not available!");
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        promocode = Long.parseLong(Constants.Potongan);
                        updateDistance();
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

    @Override
    public void calculatePrice() {
        updateEstimatedItemCost();
    }

    private void updateEstimatedItemCost() {
        List<PesananMerchant> existingFood = LocalStore.get().getAllCart();
        long cost = 0;
        for (int p = 0; p < existingFood.size(); p++) {
            cost += existingFood.get(p).getTotalHarga();
        }
        foodCostLong = cost;
        Utility.currencyTXT(orderprice, String.valueOf(foodCostLong), this);
        MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (back.equals("1")) {
            Intent intent = new Intent(DetailOrderActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    private void updateDistance() {
        checkedpaycash = "1";
        checkedpaywallet = "0";
        Log.e("CHECKEDWALLET", checkedpaywallet);
        checkedcash.setSelected(true);
        checkedwallet.setSelected(false);
        cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
        walletpayment.setTextColor(getResources().getColor(R.color.gray));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
            checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
        }
        km = distance;
        this.jarak = km;
        String format = String.format(Locale.US, "%.1f", km);
        textJarak.setText(format + "km");
        String biaya = String.valueOf(biayaminimum);
        long biayaTotal = Utility.fixPembulatan((long) (Double.parseDouble(getbiaya) * km));
        if (biayaTotal < Double.parseDouble(biayaminimum)) {
            this.harga = Long.parseLong(biayaminimum);
            biayaTotal = Long.parseLong(biayaminimum);
        } else {
        }
        this.harga = biayaTotal;
        deliveryCostLong = biayaTotal;
        Log.e("distance", String.valueOf(deliveryCostLong));

        Utility.currencyTXT(deliveryfee, String.valueOf(deliveryCostLong), this);
        final long finalBiayaTotalpay = foodCostLong + harga;

        Utility.currencyTXT(total, String.valueOf(finalBiayaTotalpay-promocode), DetailOrderActivity.this);
        Utility.currencyTXT(diskon, String.valueOf(promocode), DetailOrderActivity.this);
        numdiskon.setText(String.valueOf(promocode));
        long saldokini = Long.parseLong(saldoWallet);
        if (saldokini < ((foodCostLong + harga) - (finalBiayaTotalpay * Double.parseDouble(biayaakhir)))) {
            llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.currencyTXT(total, String.valueOf(finalBiayaTotalpay - promocode), DetailOrderActivity.this);
                    Utility.currencyTXT(diskon, String.valueOf(promocode), DetailOrderActivity.this);
                    numdiskon.setText(String.valueOf(promocode));
                    order.setEnabled(true);
                    QuickOrder.setEnabled(true);
                    checkedcash.setSelected(true);
                    checkedwallet.setSelected(false);
                    checkedpaycash = "1";
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
            order.setEnabled(true);
            QuickOrder.setEnabled(true);
            llcheckedwallet.setEnabled(false);
        } else {
            order.setEnabled(true);
            QuickOrder.setEnabled(true);
            llcheckedwallet.setEnabled(true);
            llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.currencyTXT(total, String.valueOf(finalBiayaTotalpay - promocode), DetailOrderActivity.this);
                    Utility.currencyTXT(diskon, String.valueOf(promocode), DetailOrderActivity.this);
                    numdiskon.setText(String.valueOf(promocode));
                    checkedcash.setSelected(true);
                    checkedwallet.setSelected(false);
                    checkedpaycash = "1";
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });

            llcheckedwallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    order.setEnabled(true);
                    QuickOrder.setEnabled(true);
                    long diskonwallet = (long) ((finalBiayaTotalpay * Double.parseDouble(biayaakhir)) + promocode);
                    long kalkulasi = Utility.fixPembulatan(diskonwallet);
                    Log.e("distance", String.valueOf((foodCostLong + harga) - (finalBiayaTotalpay * Double.parseDouble(biayaakhir))));
                    Utility.currencyTXT(diskon, String.valueOf(kalkulasi), DetailOrderActivity.this);
                    numdiskon.setText(String.valueOf(kalkulasi));
                    Utility.currencyTXT(total, String.valueOf(finalBiayaTotalpay - kalkulasi), DetailOrderActivity.this);
                    checkedcash.setSelected(false);
                    checkedwallet.setSelected(true);
                    checkedpaycash = "0";
                    checkedpaywallet = "1";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    walletpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    cashpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
        }

    }



    private void sendOrder() {
        List<PesananMerchant> existingItem = LocalStore.get().getAllCart();
        for (PesananMerchant pesanan : existingItem) {
            if (pesanan.getCatatan() == null || pesanan.getCatatan().trim().equals("")){
                pesanan.setCatatan("");
            }else{
                pesanan.setCatatan(pesanan.getCatatan());
            }
            ItemRequestJson param = new ItemRequestJson();
            User userLogin = BaseApp.getInstance(this).getLoginUser();
            param.setIdPelanggan(userLogin.getId());
            param.setOrderFitur(String.valueOf(fitur));
            //    param.setStartLatitude(destinationLatLang.latitude);
            //  param.setStartLongitude(destinationLatLang.longitude);
            //  param.setEndLatitude(merlat);
            //  param.setEndLongitude(merlon);
            param.setStartLatitude(merlat);
            param.setStartLongitude(merlon);
            param.setEndLatitude(destinationLatLang.latitude);
            param.setEndLongitude(destinationLatLang.longitude);
            param.setAlamatTujuan(location.getText().toString());
            param.setAlamatAsal(alamatresto);
            param.setJarak(jarak);
            param.setEstimasi(String.valueOf(distance));
            param.setHarga(deliveryCostLong);
            if (checkedpaycash.equals("1")) {
                param.setPakaiWallet(0);
                param.setKreditpromo(String.valueOf(promocode));
            } else {
                param.setPakaiWallet(1);
                param.setKreditpromo(numdiskon.getText().toString());
            }
            param.setIdResto(idresto);
            param.setTotalBiayaBelanja(foodCostLong);
            param.setCatatan(pesanan.getCatatan());
            param.setPesanan(existingItem);
            Log.e("Bookingdata", ServiceGenerator.gson.toJson(param));
            fetchNearDriver(param);
        }
    }

    private void driverTerdekat(double latitude, double longitude, String fitur) {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
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
                    for(int i = 0; i < driverAvailable.size(); i++){
                        if(driverAvailable.get(i).getStatus().equals("1")){
                            LinearLayoutManager layoutManager = new LinearLayoutManager(DetailOrderActivity.this, LinearLayoutManager.VERTICAL, false);
                            mDriverRec.setLayoutManager(layoutManager);
                            mDriverRec.setNestedScrollingEnabled(false);
                            dAdapter = new ListDriverAdapter(driverAvailable,DetailOrderActivity.this);
                            dAdapter.setOnItemClickListener(new ListDriverClick() {
                                @Override
                                public void onItemClick(DriverModel item) {
                                    List<PesananMerchant> existingItem = LocalStore.get().getAllCart();
                                    for (PesananMerchant pesanan : existingItem) {
                                        if (pesanan.getCatatan() == null || pesanan.getCatatan().trim().equals("")){
                                            pesanan.setCatatan("");
                                        }else{
                                            pesanan.setCatatan(pesanan.getCatatan());
                                        }
                                        ItemRequestJson param = new ItemRequestJson();
                                        User userLogin = BaseApp.getInstance(DetailOrderActivity.this).getLoginUser();
                                        param.setIdPelanggan(userLogin.getId());
                                        param.setOrderFitur(String.valueOf(fitur));
                                        //    param.setStartLatitude(destinationLatLang.latitude);
                                        //  param.setStartLongitude(destinationLatLang.longitude);
                                        //  param.setEndLatitude(merlat);
                                        //  param.setEndLongitude(merlon);
                                        param.setStartLatitude(merlat);
                                        param.setStartLongitude(merlon);
                                        param.setEndLatitude(destinationLatLang.latitude);
                                        param.setEndLongitude(destinationLatLang.longitude);
                                        param.setAlamatTujuan(location.getText().toString());
                                        param.setAlamatAsal(alamatresto);
                                        param.setJarak(jarak);
                                        param.setEstimasi(String.valueOf(distance));
                                        param.setHarga(deliveryCostLong);
                                        if (checkedpaycash.equals("1")) {
                                            param.setPakaiWallet(0);
                                            param.setKreditpromo(String.valueOf(promocode));
                                        } else {
                                            param.setPakaiWallet(1);
                                            param.setKreditpromo(numdiskon.getText().toString());
                                        }
                                        param.setIdResto(idresto);
                                        param.setTotalBiayaBelanja(foodCostLong);
                                        param.setCatatan(pesanan.getCatatan());
                                        param.setPesanan(existingItem);
                                        Log.e("Bookingdata", ServiceGenerator.gson.toJson(param));
                                        sendRequestManual(param, item.getRegId());
                                    }
                                }
                            });
                            mDriverRec.setAdapter(dAdapter);
                        }
                    }
                    Log.e("ListDriver", String.valueOf(driverAvailable.size()));
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {
                rlprogress.setVisibility(View.GONE);
                Log.e("ListDriver", String.valueOf(t.getMessage()));
            }
        });
    }

    private void fetchNearDriver(final ItemRequestJson paramdata) {
        rlprogress.setVisibility(View.VISIBLE);
        if (destinationLatLang != null) {
            User loginUser = BaseApp.getInstance(this).getLoginUser();

            BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
            GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
            param.setLatitude(merlat);
            param.setLongitude(merlon);
            param.setFitur(String.valueOf(fitur));

            service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                    if (response.isSuccessful()) {
                        driverAvailable = Objects.requireNonNull(response.body()).getData();
                        if (driverAvailable.isEmpty()) {
                            finish();
                            Toast.makeText(DetailOrderActivity.this, "Tidak Ada Driver Disekitar!", Toast.LENGTH_SHORT).show();
                        } else {
                            sendRequestTransaksi(paramdata, driverAvailable);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

                }
            });

        }
    }

    private void buildDriverRequest(RideCarResponseJson response) {

        for (int i = 0; i < response.getData().size(); i++) {
            transaksi = response.getData().get(i);
            Log.e("wallet", String.valueOf(transaksi.isPakaiWallet()));
            User loginUser = BaseApp.getInstance(this).getLoginUser();
            if (request == null) {
                request = new DriverRequest();
                request.setIdTransaksi(transaksi.getId());
                request.setIdPelanggan(transaksi.getIdPelanggan());
                request.setRegIdPelanggan(loginUser.getToken());
                request.setOrderFitur(home);
                request.setStartLatitude(transaksi.getStartLatitude());
                request.setStartLongitude(transaksi.getStartLongitude());
                request.setEndLatitude(transaksi.getEndLatitude());
                request.setEndLongitude(transaksi.getEndLongitude());
                request.setJarak(transaksi.getJarak());
                request.setHarga(transaksi.getHarga() + foodCostLong);
                request.setWaktuOrder(transaksi.getWaktuOrder());
                request.setAlamatAsal(transaksi.getAlamatAsal());
                request.setAlamatTujuan(transaksi.getAlamatTujuan());
                request.setKodePromo(transaksi.getKodePromo());
                request.setKreditPromo(transaksi.getKreditPromo());
                request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
                request.setEstimasi(namamerchant);
                request.setLayanan(layanan);
                request.setLayanandesc(keterangan);
                request.setIcon(icon);
                request.setBiaya(String.valueOf(foodCostLong));
                request.setTokenmerchant(transaksi.getToken_merchant());
                request.setIdtransmerchant(transaksi.getIdtransmerchant());
                request.setDistance(String.valueOf(deliveryCostLong));
                String namaLengkap = String.format("%s", loginUser.getFullnama());
                request.setNamaPelanggan(namaLengkap);
                request.setTelepon(loginUser.getNoTelepon());
                request.setType(ORDER);
            }
        }
    }

    private void sendRequestTransaksi(ItemRequestJson param, final List<DriverModel> driverList) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        service.requestTransaksiMerchant(param).enqueue(new Callback<RideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RideCarResponseJson> call, @NonNull Response<RideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    buildDriverRequest(Objects.requireNonNull(response.body()));
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < driverList.size(); i++) {
                                Waktu = new Date().getTime() + "";
                                Log.d("WaktuOrder",Waktu);
                                fcmBroadcast(i, driverList,Waktu);
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
                                                notif("Maaf Pengemudi Tidak Ditemukan");
                                                if(designedFitur.getIsPending() == 1){
                                                    pendingOrder();
                                                    finish();
                                                }else{
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            notif("Maaf Pengemudi Tidak Ditemukan");
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
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Throwable t) {
                                        notif("Maaf Pengemudi Tidak Ditemukan");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                notif("Maaf Pengemudi Tidak Ditemukan");
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
                notif("Akun Anda bermasalah, silakan hubungi layanan pelanggan!");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 3000);
            }
        });
    }

    private void sendRequestManual(ItemRequestJson param, String tokenDriver){
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        service.requestTransaksiMerchant(param).enqueue(new Callback<RideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RideCarResponseJson> call, @NonNull Response<RideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    buildDriverRequest(Objects.requireNonNull(response.body()));
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            manualOrder(tokenDriver);
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
                                                notif("Maaf Pengemudi Tidak Ditemukan");
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        notif("Maaf Pengemudi Tidak Ditemukan");

                                                    }
                                                });

                                                new Handler().postDelayed(new Runnable() {
                                                    public void run() {
                                                        finish();
                                                    }
                                                }, 3000);
                                            }
                                            rlprogress.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Throwable t) {
                                        notif("Maaf Pengemudi Tidak Ditemukan");
                                        rlprogress.setVisibility(View.GONE);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                notif("Maaf Pengemudi Tidak Ditemukan");
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
                rlprogress.setVisibility(View.GONE);
                t.printStackTrace();
                notif("Akun Anda bermasalah, silakan hubungi layanan pelanggan!");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 3000);
            }
        });
    }

    private void fcmBroadcast(int index, List<DriverModel> driverList,String Waktu) {
        if(Waktu != null){
            try{
                final User login = BaseApp.getInstance(DetailOrderActivity.this).getLoginUser();
                if(login != null){
                    DriverModel driverToSend = driverList.get(index);
                    request.setTime_accept(Waktu);
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
            } catch (Exception e) {
                Log.e("RequestDriver", e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private void manualOrder(String tokenDriver){
        final User login = BaseApp.getInstance(DetailOrderActivity.this).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(tokenDriver);
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
                            Intent intent = new Intent(DetailOrderActivity.this, ActivityProgress.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("Working", "true");
                            intent.putExtra("id_driver", cDriver.getId());
                            intent.putExtra("id_transaksi", request.getIdTransaksi());
                            intent.putExtra("pakai_wallet", request.isPakaiWallet());
                            intent.putExtra("response", "2");
                            intent.putExtra("complete", "1");
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
        super.onStart();
        mGoogleApiClient.connect();
        EventBus.getDefault().register(this);
        startIsDriver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        EventBus.getDefault().unregister(this);
        stopIsDriver();
    }


    //------------------------------------ QUick Order ------------------------------------------------

    //-------------------------------------------------------------------------------------------------

    private void setupGoogleAPI() {
        // initialize Google API Client
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            String sAlamat = getCompleteAddressString(destinationLatLang);
            location.setText(sAlamat);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //------------------------------- Near Driver ---------------------------
    Timer timer = new Timer();
    private final Runnable updateDriverRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(pickUpLatLang != null){
                            driverTerdekat(merlat, merlon,IDFITUR);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (NetworkManager.isConnectToInternet(DetailOrderActivity.this)) {
                                try {
                                    if(pickUpLatLang != null){
                                        driverTerdekat(merlat, merlon,IDFITUR);
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

    private void pendingOrder() {
        final User user = BaseApp.getInstance(DetailOrderActivity.this).getLoginUser();
        CancelBookRequestJson requestcancel = new CancelBookRequestJson();
        requestcancel.id_transaksi = transaksi.getId();
        BookService service = ServiceGenerator.createService(BookService.class, user.getEmail(), user.getPassword());
        service.pendingOrder(requestcancel).enqueue(new Callback<CancelBookResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<CancelBookResponseJson> call, @NonNull Response<CancelBookResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.d("CancelOrder",Objects.requireNonNull(response.body()).mesage);
                    if (Objects.requireNonNull(response.body()).mesage.equals("true")) {
                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.id_driver = "00";
                        orderfcm.id_transaksi = transaksi.getId();
                        orderfcm.response = "9";

                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                            @Override
                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                String tokenf = instanceIdResult.getToken();
                                broadcastpending(tokenf, orderfcm);

                            }
                        });


                    } else {
                        Toasty.info(DetailOrderActivity.this, "Gagal.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CancelBookResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("FAILURE", t.getMessage());
            }
        });


    }

    private void broadcastpending(String token, OrderFCM orderFCM){
        User login = BaseApp.getInstance(this).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId(transaksi.getId());
            param.setToken(token);
            param.setData(orderFCM);
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
}
