package com.rcdriver.dr.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.EditKendaraanRequestJson;
import com.rcdriver.dr.json.GetKendaraan;
import com.rcdriver.dr.json.LoginResponseJson;
import com.rcdriver.dr.models.FirebaseToken;
import com.rcdriver.dr.models.Kendaraan;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.Log;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditKendaraanActivity extends AppCompatActivity {

    ImageView backbtn, imgView;
    Button submit;
    EditText brand, type, platnomor, warna, stnk;
    TextView notiftext;
    RelativeLayout rlnotif, rlprogress;
    String disableback;

    String idk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editkendaraan);
        User driver = BaseApp.getInstance(this).getLoginUser();
        backbtn = findViewById(R.id.back_btn_verify);
        submit = findViewById(R.id.buttonconfirm);
        brand = findViewById(R.id.brand);
        type = findViewById(R.id.type);
        platnomor = findViewById(R.id.platnomor);
        warna = findViewById(R.id.color);
        stnk = findViewById(R.id.stnk);
        notiftext = findViewById(R.id.textnotif2);
        rlnotif = findViewById(R.id.rlnotif2);
        rlprogress = findViewById(R.id.rlprogress);

        disableback = "false";
        idk = getIntent().getStringExtra(Constants.INTENT_ID);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        get();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stnk.getText().toString().isEmpty()) {
                    notif("stnk cant be empty!");

                } else if (brand.getText().toString().isEmpty()) {
                    notif("vehicle brand cant be empty!");
                } else if (type.getText().toString().isEmpty()) {
                    notif("vehicle type cant be empty!");
                } else if (platnomor.getText().toString().isEmpty()) {
                    notif("vehicle number cant be empty!");
                } else if (warna.getText().toString().isEmpty()) {
                    notif("vehicle color cant be empty!");
                } else {
                    post();
                }

            }
        });

    }

    private void get() {
        progressshow();
        Log.e("IDK", idk);
        User loginuser = BaseApp.getInstance(EditKendaraanActivity.this).getLoginUser();


        DriverService service = ServiceGenerator.createService(DriverService.class, loginuser.getNoTelepon(), loginuser.getPassword());
        service.vehicle(idk).enqueue(new Callback<GetKendaraan>() {
            @Override
            public void onResponse(@NonNull Call<GetKendaraan> call, @NonNull Response<GetKendaraan> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Kendaraan kendaraan = response.body().getKendaraan();
                        stnk.setText(kendaraan.getNostnk());
                        brand.setText(kendaraan.getMerek());
                        type.setText(kendaraan.getTipe());
                        platnomor.setText(kendaraan.getPlatnomor());
                        warna.setText(kendaraan.getWarna());
                    } else {
                        notif(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetKendaraan> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error");
            }
        });
    }

    private void post(){
        progressshow();
        EditKendaraanRequestJson request = new EditKendaraanRequestJson();
        User loginuser = BaseApp.getInstance(EditKendaraanActivity.this).getLoginUser();
        request.setNoTelepon(loginuser.getNoTelepon());
        request.setId(loginuser.getId());
        request.setId_kendaraan(loginuser.getIdk());
        request.setMerek(brand.getText().toString());
        request.setTipe(type.getText().toString());
        request.setNo_kendaraan(platnomor.getText().toString());
        request.setWarna(warna.getText().toString());
        request.setNoStnk(stnk.getText().toString());

        DriverService service = ServiceGenerator.createService(DriverService.class, request.getNoTelepon(), loginuser.getPassword());
        service.editKendaraan(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseJson> call, @NonNull Response<LoginResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        finish();
                    } else {
                        notif(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error");
            }
        });
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notiftext.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(EditKendaraanActivity.this).setLoginUser(user);
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirebaseToken response) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(FirebaseToken.class);
        realm.copyToRealm(response);
        realm.commitTransaction();
    }


}
