package com.rcdriver.cs.activity.digi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.digi.OperatorAdapter;
import com.rcdriver.cs.adapter.digi.ProdukAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.json.DigiTopupRequest;
import com.rcdriver.cs.json.DigiTopupResponse;
import com.rcdriver.cs.json.InquiryTokenRequest;
import com.rcdriver.cs.json.InquiryTokenResponse;
import com.rcdriver.cs.json.ResponseOperatorDigi;
import com.rcdriver.cs.json.ResponseProdukDigi;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.models.digi.InquiryToken;
import com.rcdriver.cs.models.digi.Operator;
import com.rcdriver.cs.models.digi.Produk;
import com.rcdriver.cs.models.digi.Transaksi;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.OnDenomSelected;
import com.rcdriver.cs.utils.OnItemSelected;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrabayarActivity extends AppCompatActivity implements OnItemSelected, OnDenomSelected {
    private Context context;
    private Toolbar toolbar;
    private TextView caption, textTitle;
    private ImageView backButton;
    private LinearLayout lloperator, llnumber, lldenom;
    private Button submit;
    private RelativeLayout rlprogress;
    private EditText operator, number, denom;
    private AlertDialog dialog;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    public static ArrayList<Operator> mOperatorList = new ArrayList<Operator>();
    public static ArrayList<Produk> mProdukList = new ArrayList<Produk>();
    private OperatorAdapter operatorAdapter;
    private ProdukAdapter produkAdapter;
    private String sKategori, sOperator, sKode, sHarga, sKeterangan, sMetode, sNama, sPower, sTipe;
    private User user;
    private int isInq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prabayar);
        context = this;
        toolbar = findViewById(R.id.toolbar2);
        caption = findViewById(R.id.title);
        textTitle = findViewById(R.id.title_number);
        backButton = findViewById(R.id.back_btn);
        lloperator = findViewById(R.id.lloperator);
        llnumber = findViewById(R.id.llphone);
        lldenom = findViewById(R.id.lldenom);
        submit = findViewById(R.id.submit2);
        rlprogress  =findViewById(R.id.rlprogress);
        operator = findViewById(R.id.operator);
        number = findViewById(R.id.number);
        denom = findViewById(R.id.denom);
        View bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        sMetode = getIntent().getStringExtra(Constants.METHOD_NAME);
        caption.setText(sMetode);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        user = BaseApp.getInstance(PrabayarActivity.this).getLoginUser();
        sKategori = getIntent().getStringExtra(Constants.METHOD);
        sTipe = getIntent().getStringExtra(Constants.METHOD_TYPE);
        isInq = getIntent().getIntExtra(Constants.IS_INQUIRY, 0);

        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOperator ="";
                mProdukList.clear();
                denom.setText("");
                getDataOperator(sKategori);
            }
        });

        denom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataProduk(sOperator);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operator.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Harap pilih operator dahulu", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }else if(number.getText().toString().isEmpty()){
                    number.setError("Nomor harus diisi");
                    number.requestFocus();
                    return;
                }else if(denom.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Harap pilih produk dahulu", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }
                if(isInq > 0){
                    inquiryToken();
                }else {
                    showDetailTransaksi(false);
                }

            }
        });

    }

    private void inquiryToken() {
        InquiryTokenRequest request = new InquiryTokenRequest();
        request.setCustomerNo(number.getText().toString());
        UserService service = ServiceGenerator.createService(UserService.class, user.getId(), user.getPassword());
        service.inqToken(request).enqueue(new Callback<InquiryTokenResponse>() {
            @Override
            public void onResponse(Call<InquiryTokenResponse> call, Response<InquiryTokenResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        InquiryToken inquiryToken = response.body().getInquiryToken();
                        sNama = inquiryToken.getName();
                        sPower = inquiryToken.getSegmentPower();
                        showDetailTransaksi(true);
                    }else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);
                    }
                }
            }

            @Override
            public void onFailure(Call<InquiryTokenResponse> call, Throwable t) {
                t.printStackTrace();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        });
    }

    private void getDataOperator(String cat){
        mOperatorList.clear();
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        service.getOperator(cat).enqueue(new Callback<ResponseOperatorDigi>() {
            @Override
            public void onResponse(Call<ResponseOperatorDigi> call, Response<ResponseOperatorDigi> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        if(response.body().getData().size() > 0){
                            for (int i = 0; i < response.body().getData().size(); i++)
                            {
                                Operator model = response.body().getData().get(i);
                                mOperatorList.add(model);
                            }

                            showOperator();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOperatorDigi> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getDataProduk(String idOperator){
        mProdukList.clear();
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        service.getProduk(idOperator).enqueue(new Callback<ResponseProdukDigi>() {
            @Override
            public void onResponse(Call<ResponseProdukDigi> call, Response<ResponseProdukDigi> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        if(response.body().getData().size() > 0){
                            for (int i = 0; i < response.body().getData().size(); i++){
                                Produk mProduk = response.body().getData().get(i);
                                mProdukList.add(mProduk);
                            }
                            showDenom();
                        }else{
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseProdukDigi> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showDetailTransaksi(boolean isToken){

        long usersaldo = user.getWalletSaldo();
        long cekharga = Long.parseLong(sHarga);

        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        @SuppressLint("InflateParams") final View mDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_ppob, null);

        TextView textNomor = mDialog.findViewById(R.id.text_nomor);
        TextView textKategori = mDialog.findViewById(R.id.text_kategori);
        TextView textBrand = mDialog.findViewById(R.id.text_brand);
        TextView textSaldo = mDialog.findViewById(R.id.text_saldo);
        TextView textHarga = mDialog.findViewById(R.id.text_harga);
        TextView textBiaya = mDialog.findViewById(R.id.text_biaya);
        TextView textTotal = mDialog.findViewById(R.id.text_total);
        TextView textNama = mDialog.findViewById(R.id.text_nama_pelanggan);
        TextView textSegment = mDialog.findViewById(R.id.text_segment);
        LinearLayout lnama = mDialog.findViewById(R.id.lnama);
        LinearLayout lsegment = mDialog.findViewById(R.id.linfo);
        Button ubah = mDialog.findViewById(R.id.btn_submit);
        Button bayar = mDialog.findViewById(R.id.btn_submit2);

        int harga = 0;
        int biaya = 0;
        int total = 0;

        harga = Integer.parseInt(sHarga);
        total = harga + biaya;
        textNomor.setText(number.getText().toString());
        textKategori.setText(sMetode);
        textBrand.setText(sKeterangan);

        Utility.currencyTXT(textHarga, sHarga, context);
        Utility.currencyTXT(textBiaya, String.valueOf(biaya), context);
        Utility.currencyTXT(textTotal, String.valueOf(total), context);
        Utility.currencyTXT(textSaldo, String.valueOf(usersaldo), context);

        if(isToken){
            lnama.setVisibility(View.VISIBLE);
            lsegment.setVisibility(View.VISIBLE);
            textNama.setText(sNama);
            textSegment.setText(sPower);
        }else {
            lnama.setVisibility(View.GONE);
            lsegment.setVisibility(View.GONE);
        }

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usersaldo < cekharga){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Saldo Anda Tidak Cukup Untuk Melakukan Transaksi.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
//                    mpulsa_topup(sKode,sOperator);
                    requestTopup(sKode, number.getText().toString());
                }
                mBottomSheetDialog.dismiss();

            }
        });

        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(mDialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Objects.requireNonNull(mBottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    private void showOperator(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.listdriver);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        operatorAdapter = new OperatorAdapter(context, mOperatorList, PrabayarActivity.this::onItemClick);
        recyclerView.setAdapter(operatorAdapter);
        dialog.setView(dialogView);
        dialog.show();
    }

    private void showDenom(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.listdriver);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        produkAdapter = new ProdukAdapter(context, mProdukList, PrabayarActivity.this::onDenomlick);
        recyclerView.setAdapter(produkAdapter);
        dialog.setView(dialogView);
        dialog.show();
    }

    private void requestTopup(String mProduk, String mNumber){
        DigiTopupRequest requestJson = new DigiTopupRequest();
        requestJson.setIdUser(user.getId());
        requestJson.setKodeProduk(mProduk);
        requestJson.setNomorTagihan(mNumber);
        UserService service = ServiceGenerator.createService(UserService.class, user.getId(), user.getPassword());
        service.topup(requestJson).enqueue(new Callback<DigiTopupResponse>() {
            @Override
            public void onResponse(Call<DigiTopupResponse> call, Response<DigiTopupResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equalsIgnoreCase("00")){
                        final Transaksi transaksi = response.body().getData();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Transaksi Berhasil Diproses.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(context, ResultTransaksiActivity.class);
                                intent.putExtra(Constants.METHOD, transaksi.getInvoice());
                                intent.putExtra(Constants.METHOD_TYPE, sTipe);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);
                    }
                }else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Error connection to server please try again", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onFailure(Call<DigiTopupResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("Tag.Log", t.getMessage());
            }
        });

    }

    @Override
    public void onItemClick(String id, String nama, String cat) {
        operator.setText(nama);
        sOperator = id;
        dialog.dismiss();
    }

    @Override
    public void onDenomlick(String kode, String harga, String nama) {
        denom.setText(nama);
        sKode = kode;
        sHarga = harga;
        sKeterangan = nama;
        dialog.dismiss();
    }
}