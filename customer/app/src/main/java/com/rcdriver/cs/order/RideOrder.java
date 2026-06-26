package com.rcdriver.cs.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.json.CheckStatusTransaksiRequest;
import com.rcdriver.cs.json.CheckStatusTransaksiResponse;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.GetNearRideCarRequestJson;
import com.rcdriver.cs.json.GetNearRideCarResponseJson;
import com.rcdriver.cs.json.RideCarRequestJson;
import com.rcdriver.cs.json.RideCarResponseJson;
import com.rcdriver.cs.json.SaveLokasiRequest;
import com.rcdriver.cs.json.SaveLokasiResponse;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.json.fcm.CancelBookRequestJson;
import com.rcdriver.cs.json.fcm.CancelBookResponseJson;
import com.rcdriver.cs.json.fcm.DriverRequest;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.OrderFCM;
import com.rcdriver.cs.models.StatusTransaksiModel;
import com.rcdriver.cs.models.TransaksiModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.NetworkManager;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;
import es.dmoral.toasty.Toasty;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rcdriver.cs.json.fcm.FCMType.ORDER;

public class RideOrder extends AppCompatActivity {
    private Realm realm;
    private String idpelanggan,biaya,fiturdesc,ICONFITUR,fitur,estimasi,diskon,alamatasal,alamattujuan,token;
    private int pakaisaldo = 0;
    private double jarak = 0;
    private double picklat = 0;
    private double picklng = 0;
    private double destlat = 0;
    private double destlng = 0;
    private long harga = 0;
    int counter = 0;
    int auto = 1;
    int FITURID = -1;
    private LatLng pickuplatlng,destinationlng;
    TransaksiModel transaksi;
    private FiturModel designedFitur;
    private DriverRequest request;
    private List<DriverModel> driverAvailable;
    private List<StatusTransaksiModel> availablestatus;
    private String NamaAlamat;
    Handler handler;
    ImageView imganimasi;
    TextView waktu;
    RelativeLayout rootLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_ride);
        imganimasi = findViewById(R.id.imganimasi);
        waktu = findViewById(R.id.waktu);
        rootLayout = findViewById(R.id.rootLayout);
        realm = Realm.getDefaultInstance();
        driverAvailable = new ArrayList<>();
        Intent intent = getIntent();
        idpelanggan = intent.getStringExtra("idpelanggan");
        fitur = intent.getStringExtra("fitur");
        biaya = intent.getStringExtra("biaya");
        ICONFITUR = intent.getStringExtra("ikon");
        fiturdesc = intent.getStringExtra("fiturdesk");
        FITURID = intent.getIntExtra("idfitur", -1);
        jarak = intent.getDoubleExtra("jarak",-1);
        auto = intent.getIntExtra("auto",1);
        estimasi = intent.getStringExtra("estimasi");
        harga = intent.getLongExtra("harga",0);
        diskon = intent.getStringExtra("diskon");
        alamatasal = intent.getStringExtra("pickaddress");
        alamattujuan = intent.getStringExtra("destkaddress");
        pakaisaldo = intent.getIntExtra("pakaisaldo",0);
        token = intent.getStringExtra("token");
        picklat = intent.getDoubleExtra("picklat", 0);
        picklng = intent.getDoubleExtra("picklng", 0);
        destlat = intent.getDoubleExtra("destlat", 0);
        destlng = intent.getDoubleExtra("destlng", 0);
        pickuplatlng = new LatLng(picklat,picklng);
        destinationlng = new LatLng(destlat,destlng);
        NamaAlamat = intent.getStringExtra("namaalamat");
        Log.d("NamaAlamat",NamaAlamat);
        fetchNearDriver(picklat,picklng,fitur);
        if (FITURID != -1)
            designedFitur = realm.where(FiturModel.class).equalTo("idFitur", FITURID).findFirst();
            if(designedFitur.getHome() != null){
                if(designedFitur.getHome().equals("4")){
                    SaveLok(picklat,picklng,alamatasal,NamaAlamat);
                }else{
                    SaveLok(destlat,destlng,alamattujuan,NamaAlamat);
                }
            }
        RealmResults<FiturModel> fiturs = realm.where(FiturModel.class).findAll();
        for (FiturModel fitur : fiturs) {
            Log.e("ID_FITUR", fitur.getIdFitur() + " " + fitur.getFitur() + " " + fitur.getBiayaAkhir() + " " + ICONFITUR);
        }
        Log.e("RideOrder",token);
        Glide.with(this)
                .asGif()
                .load(R.drawable.waiting_order)
                .placeholder(ResourcesCompat.getDrawable(getResources(), R.drawable.waiting_order, null))
                .centerCrop()
                .into(new ImageViewTarget<GifDrawable>(imganimasi) {
                    @Override
                    protected void setResource(@Nullable GifDrawable resource) {
                        imganimasi.setImageDrawable(resource);
                    }
        });
        updateFitur();
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
                        if(pickuplatlng != null){
                            fetchNearDriver(pickuplatlng.latitude, pickuplatlng.longitude, fitur);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (NetworkManager.isConnectToInternet(RideOrder.this)) {
                                try {
                                    if(pickuplatlng != null){
                                        fetchNearDriver(pickuplatlng.latitude, pickuplatlng.longitude, fitur);
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
    private void updateFitur() {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
    }
    private void fetchNearDriver(double latitude, double longitude, String fitur) {
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
                        Log.e("Terdekat", driverAvailable.get(i).getNamaDriver());
                    }
                    Log.e("ListDriver", String.valueOf(driverAvailable.size()));
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {
                Log.e("ListDriver", String.valueOf(t.getMessage()));
            }
        });
    }
    private void startIsDriver() {
        handler = new Handler();
        handler.postDelayed(updateDriverRunnable, 4000);
    }

    private void stopIsDriver() {
        handler.removeCallbacks(updateDriverRunnable);
    }
    //------------------------------- Respon order ----------------------------
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
                request.setOrderFitur(designedFitur.getHome());
                request.setStartLatitude(transaksi.getStartLatitude());
                request.setStartLongitude(transaksi.getStartLongitude());
                request.setEndLatitude(transaksi.getEndLatitude());
                request.setEndLongitude(transaksi.getEndLongitude());
                request.setJarak(transaksi.getJarak());
                request.setHarga(Long.parseLong(transaksi.getBiaya_akhir()));
                request.setWaktuOrder(transaksi.getWaktuOrder());
                request.setAlamatAsal(transaksi.getAlamatAsal());
                request.setAlamatTujuan(transaksi.getAlamatTujuan());
                request.setKodePromo(transaksi.getKodePromo());
                request.setKreditPromo(transaksi.getKreditPromo());
                request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
                request.setEstimasi(transaksi.getEstimasi());
                request.setLayanan(estimasi);
                request.setLayanandesc(fiturdesc);
                request.setIcon(ICONFITUR);
                request.setBiaya(biaya);
                request.setDistance(String.valueOf(jarak));
                String namaLengkap = String.format("%s", loginUser.getFullnama());
                request.setNamaPelanggan(namaLengkap);
                request.setTelepon(loginUser.getNoTelepon());
                request.setType(ORDER);

            }
        }
    }

    private void fcmBroadcast(int index, List<DriverModel> driverList) {

        User login = BaseApp.getInstance(this).getLoginUser();
        if(login != null){
            DriverModel driverToSend = driverList.get(index);
            request.setTime_accept(new Date().getTime() + "");
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
    //------------------------------- call order -----------------------------
    private void kirimpesanan(){
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        //------------------------- Parameters -------------------------------------------
        RideCarRequestJson param = new RideCarRequestJson();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        param.setIdPelanggan(userLogin.getId());
        param.setOrderFitur(fitur);
        param.setStartLatitude(picklat);
        param.setStartLongitude(picklng);
        param.setEndLatitude(destlat);
        param.setEndLongitude(destlng);
        param.setJarak(jarak);
        param.setEstimasi(fitur);
        param.setHarga(harga);
        param.setKreditpromo(diskon);
        param.setAlamatAsal(alamatasal);
        param.setAlamatTujuan(alamattujuan);
        param.setPakaiWallet(pakaisaldo);
        service.requestTransaksi(param).enqueue(new Callback<RideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RideCarResponseJson> call, @NonNull Response<RideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    buildDriverRequest(Objects.requireNonNull(response.body()));
                    Log.d("TipeOrder", String.valueOf(auto));
                    if(auto == 1){
                        try{
                        for (int i = 0; i < driverAvailable.size(); i++) {
                            Log.d("OrderDriver", "Jenis: " + driverAvailable.get(i).getJenis());
                            fcmBroadcast(i, driverAvailable);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                            Log.e("OrderDriver", e.getMessage());
                    }
                    }else{
                        broadcastdriver(token);
                    }

                    new CountDownTimer(30000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            waktu.setText(String.valueOf(counter));
                            counter++;
                        }
                        public void onFinish() {
                            try{
                                if(transaksi != null){
                                    Log.d("Idtrx", transaksi.getId());
                                    CheckStatusTransaksiRequest param = new CheckStatusTransaksiRequest();
                                    param.setIdTransaksi(transaksi.getId());
                                    service.checkStatusTransaksi(param).enqueue(new Callback<CheckStatusTransaksiResponse>() {
                                        @Override
                                        public void onResponse(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Response<CheckStatusTransaksiResponse> response) {
                                            if (response.isSuccessful()) {
                                                CheckStatusTransaksiResponse checkStatus = response.body();
                                                Log.d("Tag.check", response.body().getMessage());
                                                Log.d("Tag.check", checkStatus.getMessage());
//                                                if(checkStatus.getMessage().equals("check status")){
//                                                    finish();
//                                                }

                                                if (!Objects.requireNonNull(checkStatus).isStatus()) {
                                                    if(designedFitur.getIsPending() == 1 && auto == 1){
                                                        Log.e("Tag.Pending", "masuk orderan pending");
                                                        pendingOrder();
                                                    }else{
                                                        notif("Pengemudi tidak ditemukan!");
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                notif("Pengemudi tidak ditemukan!");
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
                                            notif("Driver Tidak Ditemukan!");
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    notif("Driver Tidak Ditemukan!");
                                                    finish();
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            finish();
                        }
                    }.start();
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
    //--------------------------------------- Broadcast Order ---------------
    private void broadcastdriver(String token){
        User login = BaseApp.getInstance(this).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(token);
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

    //--------------------------------------- notif -------------------------
    private void notif(String pesan){
        Snackbar snackbar = Snackbar.make(rootLayout, pesan, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        snackbar.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        startIsDriver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        stopIsDriver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pickuplatlng != null){
            startIsDriver();
            kirimpesanan();
        }
    }
    //------------------------------- save lokasi --------------------------------
    private void SaveLok(double lat,double lng,String alamat,String nama){
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        SaveLokasiRequest request = new SaveLokasiRequest();
        request.id = loginUser.getId();
        request.nama = nama;
        request.latitude = String.valueOf(lat);
        request.longitude = String.valueOf(lng);
        request.alamat = alamat;
        request.utama = "0";
        service.SaveLokasi(request).enqueue(new Callback<SaveLokasiResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveLokasiResponse> call, @NonNull Response<SaveLokasiResponse> response) {
                if (response.isSuccessful()) {
                    android.util.Log.e("SaveLokasi", response.body().mesage);
                    if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                        android.util.Log.e("SaveLokasi", response.body().mesage);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<SaveLokasiResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                android.util.Log.e("SaveLokasi", t.getMessage());
            }
        });
    }

    private void pendingOrder() {
        final User user = BaseApp.getInstance(RideOrder.this).getLoginUser();
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
                        Toasty.info(RideOrder.this, "Gagal.", Toast.LENGTH_SHORT).show();
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

}
