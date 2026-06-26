package com.rcdriver.cs.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.DriverAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.json.CheckStatusTransaksiRequest;
import com.rcdriver.cs.json.CheckStatusTransaksiResponse;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.GetNearRideCarRequestJson;
import com.rcdriver.cs.json.GetNearRideCarResponseJson;
import com.rcdriver.cs.json.RideCarRequestJson;
import com.rcdriver.cs.json.RideCarResponseJson;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.json.fcm.DriverRequest;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.TransaksiModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.BooleanSerializerDeserializer;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rcdriver.cs.json.fcm.FCMType.ORDER;

public class DialogDriver{
    private List<DriverModel> driverAvailable;
    private DriverAdapter driverAdapter;
    private RecyclerView recyclerView;
    private Activity context;
    TransaksiModel transaksi;
    private DriverRequest request;
    public int counter;
    private Dialog dialog;
    private RelativeLayout rlprogress;
    private TextView textprogress,waktu;
    private FiturModel fiturModel;
    private String fiturikon,fitur,keterangan,home;
    private RideCarRequestJson parameters;
    private String mongkir,mjarak;

    public void showDialog(Activity activity, LatLng latLng, FiturModel desainfitur, RideCarRequestJson request, String ongkir, String jarak){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_driver);
        context = activity;
        driverAvailable = new ArrayList<>();
        recyclerView = dialog.findViewById(R.id.listdriver);
        rlprogress = dialog.findViewById(R.id.rlprogress);
        //Bar = dialog.findViewById(R.id.bar);
        textprogress = dialog.findViewById(R.id.textprogress);
        waktu = dialog.findViewById(R.id.timer);
        ImageView btnclose = dialog.findViewById(R.id.close_btn);
        parameters = request;
        fiturModel = desainfitur;
        fiturikon = fiturModel.getIcon();
        fitur = String.valueOf(fiturModel.getIdFitur());
        keterangan = fiturModel.getKeterangan();
        home = String.valueOf(fiturModel.getHome());
        mongkir = ongkir;
        mjarak = jarak;
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        Log.e("IkonFitur", fiturModel.getIcon() + "");
        User loginUser = BaseApp.getInstance(activity).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(latLng.latitude);
        param.setLongitude(latLng.longitude);
        param.setFitur(fitur);
        service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    driverAvailable = Objects.requireNonNull(response.body()).getData();
                    for(int i = 0; i < driverAvailable.size(); i++){
                        driverAdapter = new DriverAdapter(driverAvailable,activity,latLng);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(driverAdapter);
                        driverAdapter.notifyDataSetChanged();
                        driverAdapter.setClickListener(new DriverAdapter.ClickListener() {
                            @Override
                            public void click(DriverModel lokasiModel) {
                                sendRequestTransaksi(activity,lokasiModel.getRegId());
                            }
                        });
                    }
                    android.util.Log.e("ListDriver", String.valueOf(driverAvailable.size()));
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {
                Log.e("ListDriver", String.valueOf(t.getMessage()));
            }
        });
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void sendRequestTransaksi(Activity context, final String tokendriver) {
        rlprogress.setVisibility(View.VISIBLE);
        textprogress.setText("Menunggu Respon Driver....");
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        service.requestTransaksi(parameters).enqueue(new Callback<RideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RideCarResponseJson> call, @NonNull Response<RideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    buildDriverRequest(Objects.requireNonNull(response.body()));
                    fcmBroadcast(tokendriver);
                    new CountDownTimer(30000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            waktu.setText(String.valueOf(counter));
                            counter++;
                        }

                        public void onFinish() {
                            CheckStatusTransaksiRequest param = new CheckStatusTransaksiRequest();
                            param.setIdTransaksi(transaksi.getId());
                            service.checkStatusTransaksi(param).enqueue(new Callback<CheckStatusTransaksiResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Response<CheckStatusTransaksiResponse> response) {
                                    if (response.isSuccessful()) {
                                        CheckStatusTransaksiResponse checkStatus = response.body();
                                        if (!Objects.requireNonNull(checkStatus).isStatus()) {
                                            Toast.makeText(context, "Driver Sedang Sibuk.", Toast.LENGTH_SHORT).show();
                                            context.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "Driver Sedang Sibuk.", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            });
                                            new Handler().postDelayed(new Runnable() {
                                                public void run() {
                                                    dialog.dismiss();
                                                }
                                            }, 3000);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Throwable t) {
                                    Toast.makeText(context, "Driver Sedang Sibuk.", Toast.LENGTH_SHORT).show();
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "Driver Sedang Sibuk.", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });
                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            dialog.dismiss();
                                        }
                                    }, 3000);

                                }
                            });
                        }
                    }.start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RideCarResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Your account has a problem, please contact customer service!", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                    }
                }, 3000);
            }
        });
    }
    private void buildDriverRequest(RideCarResponseJson respon) {
        transaksi = respon.getData().get(0);
        Log.e("wallet", String.valueOf(transaksi.isPakaiWallet()));
        User loginUser = BaseApp.getInstance(context).getLoginUser();
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
            request.setHarga(Long.parseLong(transaksi.getBiaya_akhir()));
            request.setWaktuOrder(transaksi.getWaktuOrder());
            request.setAlamatAsal(transaksi.getAlamatAsal());
            request.setAlamatTujuan(transaksi.getAlamatTujuan());
            request.setKodePromo(transaksi.getKodePromo());
            request.setKreditPromo(transaksi.getKreditPromo());
            request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
            request.setEstimasi(transaksi.getEstimasi());
            request.setLayanan(fitur);
            request.setLayanandesc(keterangan);
            request.setIcon(fiturikon);
            request.setBiaya(mongkir);
            request.setDistance(mjarak);
            String namaLengkap = String.format("%s", loginUser.getFullnama());
            request.setNamaPelanggan(namaLengkap);
            request.setTelepon(loginUser.getNoTelepon());
            request.setType(ORDER);
        }
    }

    private static final BooleanSerializerDeserializer booleanSerializerDeserializer = new BooleanSerializerDeserializer();
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .serializeNulls()
            .registerTypeAdapter(Boolean.class, booleanSerializerDeserializer)
            .registerTypeAdapter(boolean.class, booleanSerializerDeserializer)
            .create();
    private void fcmBroadcast(String tokendriver) {
        request.setTime_accept(new Date().getTime() + "");
        final User login = BaseApp.getInstance(context).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(tokendriver);
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
}
