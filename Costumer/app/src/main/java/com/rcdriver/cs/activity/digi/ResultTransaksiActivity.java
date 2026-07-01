package com.rcdriver.cs.activity.digi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.json.DigiTopupRequest;
import com.rcdriver.cs.json.DigiTopupResponse;
import com.rcdriver.cs.json.GetRiwayatDigiResponse;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.models.digi.Transaksi;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultTransaksiActivity extends AppCompatActivity {
    private Context context;
    private User user;
    private Toolbar toolbar;
    private TextView invoice, custNumber, kodeProduk,namaProduk, catProduk, brandProduk, tanggal,
            harga, status, btnCheck, statusDesc, textError, textTitleSn, textSn;
    private String reffId;
    private LinearLayout lcontent;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipe;
    private String reference, sTipe;
    private LinearLayout lerror;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_transaksi);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        toolbar = findViewById(R.id.toolbar2);
        invoice = findViewById(R.id.text_invoice);
        custNumber = findViewById(R.id.text_customer_nomor);
        kodeProduk = findViewById(R.id.text_kode_produk);
        namaProduk = findViewById(R.id.text_nama_produk);
        catProduk = findViewById(R.id.text_kategori_produk);
        brandProduk = findViewById(R.id.text_brand);
        tanggal = findViewById(R.id.text_tanggal);
        harga = findViewById(R.id.text_harga);
        status = findViewById(R.id.textView70);
        btnCheck = findViewById(R.id.textView71);
        lcontent = findViewById(R.id.lcontent);
        statusDesc = findViewById(R.id.textView28);
        swipe = findViewById(R.id.swipe);
        progressBar = findViewById(R.id.progressBar2);
        lerror = findViewById(R.id.lerror);
        textError = findViewById(R.id.textView10);
        textTitleSn = findViewById(R.id.text_title_sn);
        textSn = findViewById(R.id.text_sn);

        reference = getIntent().getStringExtra(Constants.METHOD);
        sTipe = getIntent().getStringExtra(Constants.METHOD_TYPE);

        detail(reference);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                detail(reference);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sTipe.equalsIgnoreCase("1")){
                    check_status_topup();
                }else {
                    check_status_pasca();
                }
            }
        });
    }

    private void detail(String reff){
        progressBar.setVisibility(View.VISIBLE);
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        service.detailTransaksi(reff).enqueue(new Callback<GetRiwayatDigiResponse>() {
            @Override
            public void onResponse(Call<GetRiwayatDigiResponse> call, Response<GetRiwayatDigiResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        final Transaksi transaksi = response.body().getTransaksiList().get(0);
                        lcontent.setVisibility(View.VISIBLE);
                        invoice.setText(transaksi.getInvoice());
                        custNumber.setText(transaksi.getNomorTagihan());
                        kodeProduk.setText(transaksi.getKodeProduk());
                        namaProduk.setText(transaksi.getNamaProduk());
                        catProduk.setText(transaksi.getKategori());
                        brandProduk.setText(transaksi.getBrand());
                        tanggal.setText(transaksi.getRegtime());

                        harga.setText(transaksi.getTotal());
                        status.setText(transaksi.getTrxStatus());
                        statusDesc.setText(transaksi.getTrxMessage());

                        if(transaksi.getKategori().equalsIgnoreCase("Token")){
                            textTitleSn.setText("Token");
                            
                        }else {
                            textTitleSn.setText("SN");
                        }
                        
                        String sn = transaksi.getTrxSn();
                        final String[] tokens = sn.split("/", 2);

                        textSn.setText(tokens[0]);


                        if(transaksi.getStatus() > 0){
                            btnCheck.setVisibility(View.GONE);
                            statusDesc.setVisibility(View.VISIBLE);
                        }else {
                            status.setVisibility(View.VISIBLE);
                            btnCheck.setVisibility(View.GONE);
                            statusDesc.setVisibility(View.GONE);
                        }

                    }else {
                        lerror.setVisibility(View.VISIBLE);
                        textError.setText(response.body().getMessage());
                    }
                }else {
                    lerror.setVisibility(View.VISIBLE);
                    textError.setText("Error connection to server");
                }
            }

            @Override
            public void onFailure(Call<GetRiwayatDigiResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
                lerror.setVisibility(View.VISIBLE);
                textError.setText(t.getMessage());
            }
        });
    }

    private void check_status_topup(){
        progressBar.setVisibility(View.VISIBLE);
        lcontent.setVisibility(View.GONE);
        DigiTopupRequest digiTopupRequest = new DigiTopupRequest();
        digiTopupRequest.setIdUser(user.getId());
        digiTopupRequest.setNomorTagihan(custNumber.getText().toString());
        digiTopupRequest.setKodeProduk(kodeProduk.getText().toString());
        digiTopupRequest.setRef_id(reference);
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        service.checkTopup(digiTopupRequest).enqueue(new Callback<DigiTopupResponse>() {
            @Override
            public void onResponse(Call<DigiTopupResponse> call, Response<DigiTopupResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        detail(reference);

                    }else {
                        lerror.setVisibility(View.VISIBLE);
                        textError.setText(response.body().getMessage());
                    }
                }else {
                    lerror.setVisibility(View.VISIBLE);
                    textError.setText("Error connection to server");
                }
            }

            @Override
            public void onFailure(Call<DigiTopupResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
                lerror.setVisibility(View.VISIBLE);
                textError.setText(t.getMessage());
            }
        });
    }

    private void check_status_pasca(){
        progressBar.setVisibility(View.VISIBLE);
        lcontent.setVisibility(View.GONE);
        lerror.setVisibility(View.GONE);
        DigiTopupRequest digiTopupRequest = new DigiTopupRequest();
        digiTopupRequest.setIdUser(user.getId());
        digiTopupRequest.setNomorTagihan(custNumber.getText().toString());
        digiTopupRequest.setKodeProduk(kodeProduk.getText().toString());
        digiTopupRequest.setRef_id(reference);
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        service.checkTPasca(digiTopupRequest).enqueue(new Callback<DigiTopupResponse>() {
            @Override
            public void onResponse(Call<DigiTopupResponse> call, Response<DigiTopupResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        detail(reference);

                    }else {
                        lerror.setVisibility(View.VISIBLE);
                        textError.setText(response.body().getMessage());
                    }
                }else {
                    lerror.setVisibility(View.VISIBLE);
                    textError.setText("Error connection to server");
                }
            }

            @Override
            public void onFailure(Call<DigiTopupResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
                lerror.setVisibility(View.VISIBLE);
                textError.setText(t.getMessage());
            }
        });
    }
}