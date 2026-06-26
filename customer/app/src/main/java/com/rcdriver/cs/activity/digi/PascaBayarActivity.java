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
import com.rcdriver.cs.json.DigiTopupResponse;
import com.rcdriver.cs.json.InquiryResponseJson;
import com.rcdriver.cs.json.InquiryTokenRequest;
import com.rcdriver.cs.json.ResponseOperatorDigi;
import com.rcdriver.cs.json.ResponseProdukDigi;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.models.digi.Inquiry;
import com.rcdriver.cs.models.digi.Operator;
import com.rcdriver.cs.models.digi.Produk;
import com.rcdriver.cs.models.digi.Transaksi;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.OnDenomSelected;
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

public class PascaBayarActivity extends AppCompatActivity implements OnDenomSelected {
    private Context context;
    private Toolbar toolbar;
    private TextView caption, textTitle;
    private ImageView backButton;
    private LinearLayout lloperator, llnumber;
    private Button submit;
    private RelativeLayout rlprogress;
    private EditText operator, number, denom;
    private AlertDialog dialog;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    public static ArrayList<Operator> mOperatorList = new ArrayList<Operator>();
    private OperatorAdapter operatorAdapter;
    private String sKategori, sOperator, sKode, sHarga, sKeterangan, sMetode, sNama, sPower, sAdmin, sTipe;
    private User user;
    private int isInq;
    private Produk produk;
    public static ArrayList<Produk> mProdukList = new ArrayList<Produk>();
    private ProdukAdapter produkAdapter;
    private Operator mOperator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasca_bayar);
        context = this;
        toolbar = findViewById(R.id.toolbar2);
        caption = findViewById(R.id.title);
        textTitle = findViewById(R.id.title_number);
        backButton = findViewById(R.id.back_btn);
        lloperator = findViewById(R.id.lloperator);
        llnumber = findViewById(R.id.llphone);
        submit = findViewById(R.id.submit2);
        rlprogress  =findViewById(R.id.rlprogress);
        operator = findViewById(R.id.operator);
        number = findViewById(R.id.number);
        View bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        sMetode = getIntent().getStringExtra(Constants.METHOD_NAME);
        user = BaseApp.getInstance(PascaBayarActivity.this).getLoginUser();
        sKategori = getIntent().getStringExtra(Constants.METHOD);
        sTipe = getIntent().getStringExtra(Constants.METHOD_TYPE);
        denom = findViewById(R.id.denom);
        caption.setText(sMetode);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        denom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataProduk(mOperator.getId());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number.getText().toString().isEmpty()){
                    number.setError("Nomor atau ID Pelanggan harus diisi");
                    number.requestFocus();
                    return;
                }else if(denom.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Harap pilih layanan produk dahulu", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }
                inquiry();
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        getDataOperator(sKategori);
    }

    private void getDataOperator(String cat){
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        service.getOperator(cat).enqueue(new Callback<ResponseOperatorDigi>() {
            @Override
            public void onResponse(Call<ResponseOperatorDigi> call, Response<ResponseOperatorDigi> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        if(response.body().getData().size() > 0){
                            mOperator = response.body().getData().get(0);

                        }else{
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
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Error connection to server", Snackbar.LENGTH_LONG);
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
            public void onFailure(Call<ResponseOperatorDigi> call, Throwable t) {
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

    private void showDenom(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.listdriver);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        produkAdapter = new ProdukAdapter(context, mProdukList, PascaBayarActivity.this::onDenomlick);
        recyclerView.setAdapter(produkAdapter);
        dialog.setView(dialogView);
        dialog.show();
    }

    private void inquiry() {
        InquiryTokenRequest request = new InquiryTokenRequest();
        request.setCustomerNo(number.getText().toString());
        request.setKodeProduk(sKode);
        UserService service = ServiceGenerator.createService(UserService.class, user.getId(), user.getPassword());
        service.inqPasca(request).enqueue(new Callback<InquiryResponseJson>() {
            @Override
            public void onResponse(Call<InquiryResponseJson> call, Response<InquiryResponseJson> response) {
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        Inquiry inquiry = response.body().getInquiry();
                        showDetailTransaksi(inquiry);
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
            public void onFailure(Call<InquiryResponseJson> call, Throwable t) {
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

    private void showDetailTransaksi(Inquiry inq){

        long usersaldo = user.getWalletSaldo();
        long cekharga = Long.parseLong(inq.getNominal());

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
        TextView titleTagihan = mDialog.findViewById(R.id.title_tagihan);
        TextView titleAdmin = mDialog.findViewById(R.id.title_admin);
        LinearLayout lnama = mDialog.findViewById(R.id.lnama);
        LinearLayout lsegment = mDialog.findViewById(R.id.linfo);
        Button ubah = mDialog.findViewById(R.id.btn_submit);
        Button bayar = mDialog.findViewById(R.id.btn_submit2);

        titleTagihan.setText("Jumlah Tagihan");
        titleAdmin.setText("Admin");

        int harga = 0;
        int biaya = 0;
        int total = 0;

        harga = Integer.parseInt(inq.getNominal());
        biaya = Integer.parseInt(inq.getAdmin());
        total = harga + biaya;
        textNomor.setText(number.getText().toString());
        textKategori.setText(sKeterangan);
        textBrand.setText(sMetode);

        Utility.currencyTXT(textHarga, inq.getNominal(), context);
        Utility.currencyTXT(textBiaya, String.valueOf(biaya), context);
        Utility.currencyTXT(textTotal, String.valueOf(total), context);
        Utility.currencyTXT(textSaldo, String.valueOf(usersaldo), context);
        lsegment.setVisibility(View.GONE);
        lnama.setVisibility(View.VISIBLE);
        textNama.setText(inq.getNamaPelanggan());

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
                    payment(inq.getKodeProduk(), inq.getNomorTagihan(), inq.getInvoice());
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

    private void payment(String mProduk, String mNumber, String mInv) {
       InquiryTokenRequest request = new InquiryTokenRequest();
       request.setId(user.getId());
       request.setKodeProduk(mProduk);
       request.setCustomerNo(mNumber);
       request.setInvoice(mInv);
       UserService service = ServiceGenerator.createService(UserService.class, user.getId(), user.getPassword());
       service.payPasca(request).enqueue(new Callback<DigiTopupResponse>() {
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
    public void onDenomlick(String kode, String harga, String nama) {
        denom.setText(nama);
        sKode = kode;
        sHarga = harga;
        sKeterangan = nama;
        dialog.dismiss();
    }
}