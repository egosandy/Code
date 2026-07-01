package com.rcdriver.dr.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rcdriver.dr.R;
import com.rcdriver.dr.adapter.PoinAdapter;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.item.PointItem;
import com.rcdriver.dr.json.FcmResponse;
import com.rcdriver.dr.json.PointRequest;
import com.rcdriver.dr.json.PointResponse;
import com.rcdriver.dr.json.RedeemRequestJson;
import com.rcdriver.dr.json.RedeemResponseJson;
import com.rcdriver.dr.json.SendFcmRequest;
import com.rcdriver.dr.models.Notif;
import com.rcdriver.dr.models.PointModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPoint extends AppCompatActivity implements PointItem.ClickListener {
    private PointItem adapter;
    List<PointModel> mPoint;
    private RecyclerView recycle;
    private LinearLayout llslider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        recycle = findViewById(R.id.inboxlist);
        llslider = findViewById(R.id.Slider);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new GridLayoutManager(this, 1));
        Intent intent = getIntent();
        double poinku = intent.getExtras().getDouble("poin");
        //--------------------------------- List Point -----------------------------------------------
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        PointRequest param = new PointRequest();
        param.setIsdriver("1");
        userService.PointApp(param).enqueue(new Callback<PointResponse>() {
            @Override
            public void onResponse(@NonNull Call<PointResponse> call, @NonNull Response<PointResponse> response) {
                if (response.isSuccessful()) {
                    if (mPoint != null) {
                        mPoint.clear();
                    }
                    recycle.setAdapter(new PoinAdapter(Objects.requireNonNull(response.body()).getData(), new PoinAdapter.OnItemClickListener() {
                        @Override public void onItemClick(PointModel item) {
                            double cekpoin = Double.parseDouble(item.getPoin());
                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPoint.this);
                            builder.setTitle("Redeem Poin");
                            builder.setMessage("Apakah Kamu Ingin Menukar " + item.getPoin() + "Poin ?");
                            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if(poinku < cekpoin){
                                        Notif notif = new Notif();
                                        notif.title = "Redeem Poin";
                                        notif.message = "Poin Kamu Kurang Untuk Di Tukar.";
                                        sendNotif(loginUser.getToken(), notif);
                                    }else{
                                        RedeemPoin(item.getNama(),item.getPoin(),item.getNilai());
                                    }
                                }

                            });

                            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            //Toast.makeText(ActivityPoint.this, item.getNilai(), Toast.LENGTH_LONG).show();
                        }
                    }));
                    if (response.body().getData().isEmpty()) {
                        recycle.setVisibility(View.GONE);
                    } else {
                        recycle.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PointResponse> call, @NonNull Throwable t) {
                Log.e("mPoint Error", t.getMessage());
            }
        });
    }
    //------------------------------------ Redeem poin -----------------------------------------------------
    private void RedeemPoin(String nama,String poin,String nominal){
        final User user = BaseApp.getInstance(this).getLoginUser();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        RedeemRequestJson request = new RedeemRequestJson();
        request.setId(user.getId());
        request.setNama(nama);
        request.setPoin(poin);
        request.setNominal(nominal);
        request.setTanggal(dateFormat.format(date));
        DriverService service = ServiceGenerator.createService(DriverService.class, user.getNoTelepon(), user.getPassword());
        service.Redeempoin(request).enqueue(new Callback<RedeemResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RedeemResponseJson> call, @NonNull Response<RedeemResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Notif notif = new Notif();
                        notif.title = "Redeem Poin";
                        notif.message = poin + "Poin Berhasil Ditukar.";
                        sendNotif(user.getToken(), notif);
                        Intent intent = new Intent(ActivityPoint.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Notif notif = new Notif();
                        notif.title = "Redeem Poin";
                        notif.message = "Poin Gagal Di Tukar.";
                        sendNotif(user.getToken(), notif);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedeemResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("RedeemPoin", t.getMessage());
            }
        });
    }
    //notifikasi
    private void sendNotif(final String regIDTujuan, final Notif notif) {
        final User login = BaseApp.getInstance(ActivityPoint.this).getLoginUser();
        if(login != null){
            DriverService service = ServiceGenerator.createService(DriverService.class, login.getNoTelepon(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(regIDTujuan);
            param.setData(notif);
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void click(PointModel pointModel) {
        Toast.makeText(ActivityPoint.this, pointModel.getNilai(), Toast.LENGTH_SHORT).show();
        Log.d("KlikPoin", pointModel.getNilai());
    }
}