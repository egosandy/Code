package com.rcdriver.cs.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.DonaturAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.json.DonasiRequestJson;
import com.rcdriver.cs.json.DonasiResponseJson;
import com.rcdriver.cs.json.GetDonaturJson;
import com.rcdriver.cs.models.Donasi;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonasiActivity extends AppCompatActivity {
    private Context context;
    private User user;
    private ImageView backButton, imageHeader;
    private TextView judul, deskripsi, nama, alamat, phone, jumlah, wd, riwayat;
    private RecyclerView recyclerView;
    private DonaturAdapter adapter;
    private String intentdata;
    private Donasi donasi;
    private ProgressBar progressBar;
    private LinearLayout submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        backButton = findViewById(R.id.back_btn);
        judul = findViewById(R.id.text_judul);
        deskripsi = findViewById(R.id.text_deskripsi);
        nama = findViewById(R.id.text_nama_lembaga);
        alamat = findViewById(R.id.text_alamat);
        phone = findViewById(R.id.text_phone);
        jumlah = findViewById(R.id.text_total);
        wd = findViewById(R.id.text_wd);
        imageHeader = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressBar3);
        submit = findViewById(R.id.lsubmit);
        recyclerView = findViewById(R.id.recycler);
        riwayat = findViewById(R.id.textView21);

        intentdata = getIntent().getStringExtra(Constants.DATA);
        donasi = new Gson().fromJson(intentdata, Donasi.class);

        Glide.with(context)
                .load(Constants.IMAGESSLIDER + donasi.getGambar())
                .placeholder(R.drawable.image_placeholder)
                .into(imageHeader);
        judul.setText(donasi.getJudul());
        deskripsi.setText(donasi.getDeskripsi());
        nama.setText(donasi.getNamaLembaga());
        alamat.setText(donasi.getAlamat());
        phone.setText(donasi.getPhone());
        jumlah.setText(Utility.toformatRupiah(donasi.getTotal()));
        wd.setText(Utility.toformatRupiah(donasi.getWithdraw()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        getData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogConfirm();
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonasiWdActivity.class);
                intent.putExtra(Constants.METHOD, donasi.getId());
                startActivity(intent);
            }
        });

    }

    private void getData() {

        progressBar.setVisibility(View.VISIBLE);
        UserService service = ServiceGenerator.createService(UserService.class, user.getId(), user.getPassword());
        service.getDonatur(donasi.getId()).enqueue(new Callback<GetDonaturJson>() {
            @Override
            public void onResponse(Call<GetDonaturJson> call, Response<GetDonaturJson> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        adapter = new DonaturAdapter(context, response.body().getDonaturList());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDonaturJson> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });
    }

    private void openDialogConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alert = builder.create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_donasi, null);
        builder.setView(dialogView);
        builder.setCancelable(true);

        EditText nama = (EditText)dialogView.findViewById(R.id.edt_nama);
        EditText jumlah = (EditText)dialogView.findViewById(R.id.edt_jumlah);
        Button btnSubmit = (Button)dialogView.findViewById(R.id.button4);
        ImageView close = (ImageView)dialogView.findViewById(R.id.imageView8);

        nama.setText(user.getFullnama());
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama.getText().toString().isEmpty()){
                    nama.setError("Nama harus diisi");
                    nama.requestFocus();
                    return;
                }else if(jumlah.getText().toString().isEmpty()){
                    jumlah.setError("Jumlah harus diisi");
                    jumlah.requestFocus();
                    return;
                }
                postDonasi(nama.getText().toString(), jumlah.getText().toString());
                alert.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });


        alert.setView(dialogView);
        alert.show();
    }

    private void postDonasi(String nama, String nominal){
        progressBar.setVisibility(View.VISIBLE);
        DonasiRequestJson requestJson = new DonasiRequestJson();
        requestJson.setIdDonasi(donasi.getId());
        requestJson.setIdUser(user.getId());
        requestJson.setNominal(nominal);
        requestJson.setNama(nama);
        UserService service = ServiceGenerator.createService(UserService.class, user.getId(), user.getPassword());
        service.donasi(requestJson).enqueue(new Callback<DonasiResponseJson>() {
            @Override
            public void onResponse(Call<DonasiResponseJson> call, Response<DonasiResponseJson> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        Toast.makeText(context,"Donasi berhasil, Terima kasih sudah ikut berdonasi", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    getData();
                }

            }

            @Override
            public void onFailure(Call<DonasiResponseJson> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });
    }


}