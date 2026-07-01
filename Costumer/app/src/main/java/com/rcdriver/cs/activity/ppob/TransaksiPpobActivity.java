package com.rcdriver.cs.activity.ppob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.rcdriver.cs.adapter.ppob.NominalAdapter;
import com.rcdriver.cs.adapter.ppob.OperatorAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.json.MPulsaResponse;
import com.rcdriver.cs.json.MpulsaRequest;
import com.rcdriver.cs.json.ResponseJson;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.ppob.json.CekRequest;
import com.rcdriver.cs.ppob.json.HistoriRequest;
import com.rcdriver.cs.ppob.json.HistoriResponse;
import com.rcdriver.cs.ppob.json.OperatorRequest;
import com.rcdriver.cs.ppob.json.OperatorResponse;
import com.rcdriver.cs.ppob.json.TopupRequest;
import com.rcdriver.cs.ppob.json.TopupResponse;
import com.rcdriver.cs.ppob.model.ListModels;
import com.rcdriver.cs.ppob.model.TipeModels;
import com.rcdriver.cs.utils.OnDenomSelected;
import com.rcdriver.cs.utils.OnItemSelected;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPpobActivity extends AppCompatActivity implements OnItemSelected, OnDenomSelected {
    private Context context;
    private Toolbar toolbar;
    private TextView caption, textTitle;
    private ImageView backButton;
    private LinearLayout lloperator, llnumber, lldenom;
    private Button submit;
    private RelativeLayout rlprogress;
    private EditText operator, number, denom;
    private String sMetode, sOperator, sKode, sHarga, sKeterangan;
    public static ArrayList<TipeModels> mTipeList = new ArrayList<TipeModels>();
    public static ArrayList<ListModels> mDataList = new ArrayList<ListModels>();
    private OperatorAdapter operatorAdapter;
    private NominalAdapter nominalAdapter;
    private AlertDialog dialog;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_ppob);
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

        sMetode = getIntent().getStringExtra(Constants.METHOD);

        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetOperator(sMetode);
            }
        });

        denom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operator.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Harap pilih operator dahulu", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                getDenom(sMetode, sOperator);
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operator.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Harap pilih operator dahulu", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if(number.getText().toString().isEmpty()){
                    number.setError("Nomor harus diisi");
                    number.requestFocus();
                }else if(denom.getText().toString().isEmpty()){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Harap pilih operator dahulu", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                showDetailTransaksi();

            }
        });


    }

    private void showDetailTransaksi(){
        final User login = BaseApp.getInstance(TransaksiPpobActivity.this).getLoginUser();
        long usersaldo = login.getWalletSaldo();
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
        Button ubah = mDialog.findViewById(R.id.btn_submit);
        Button bayar = mDialog.findViewById(R.id.btn_submit2);



        int harga = 0;
        int biaya = 0;
        int total = 0;

        harga = Integer.parseInt(sHarga);
        total = harga + biaya;
        textNomor.setText(number.getText().toString());
        textKategori.setText(sMetode);
        textBrand.setText(sOperator);
        Utility.currencyTXT(textHarga, sHarga, context);
        Utility.currencyTXT(textBiaya, String.valueOf(biaya), context);
        Utility.currencyTXT(textTotal, String.valueOf(total), context);
        Utility.currencyTXT(textSaldo, String.valueOf(usersaldo), context);

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
                    mpulsa_topup(sKode,sOperator);
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

    private void GetOperator(String tipe){
        mTipeList.clear();
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        OperatorRequest request = new OperatorRequest();
        request.setData(tipe);
        service.ppoblist(request).enqueue(new Callback<OperatorResponse>() {
            @Override
            public void onResponse(@NonNull Call<OperatorResponse> call, @NonNull Response<OperatorResponse> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                        for (int i = 0; i < response.body().getData().size(); i++)
                        {
                            TipeModels model = response.body().getData().get(i);
                            mTipeList.add(model);
                        }

                        showOperator();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OperatorResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getDenom(String tipe, String ops){
        Log.e("GETDENOM", "tipe:" + tipe);
        mDataList.clear();
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        MpulsaRequest param = new MpulsaRequest();
        param.setTipe(tipe);
        param.setOperator(ops);
        service.mpulsa_list(param).enqueue(new Callback<MPulsaResponse>() {
            @Override
            public void onResponse(@NonNull Call<MPulsaResponse> call, @NonNull Response<MPulsaResponse> response) {
                if (response.isSuccessful()) {
                   Log.e("Mpulsa", response.body().getMessage());
                    try {
                        JSONObject rootJSONObject = new JSONObject(response.body().getData());
                        JSONArray employeeJSONArray = rootJSONObject.getJSONArray("data");
                        if(employeeJSONArray.length() > 0){
                            for (int i = 0; i < employeeJSONArray.length(); i++){
                                ListModels mDaftarHarga = new ListModels();
                                JSONObject userDetails = employeeJSONArray.getJSONObject(i);
                                mDaftarHarga.setOperator( userDetails.getString("pulsa_op"));
                                mDaftarHarga.setKeterangan( userDetails.getString("pulsa_nominal"));
                                mDaftarHarga.setHarga( userDetails.getString("pulsa_price"));
                                mDaftarHarga.setStatus( userDetails.getString("status"));
                                mDaftarHarga.setIkon( userDetails.getString("icon_url"));
                                mDaftarHarga.setKode( userDetails.getString("pulsa_code"));
                                mDataList.add(mDaftarHarga);
                            }
                            showDenom();
                        }else{
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Produk tidak ditemukan", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("PPOB", e.getMessage());
                    }


                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<MPulsaResponse> call, @NonNull Throwable t) {
                android.util.Log.e("Mpulsa", t.getMessage());
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

        operatorAdapter = new OperatorAdapter(context, mTipeList, TransaksiPpobActivity.this::onItemClick);
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
        nominalAdapter = new NominalAdapter(context, mDataList, TransaksiPpobActivity.this::onDenomlick);
        recyclerView.setAdapter(nominalAdapter);
        dialog.setView(dialogView);
        dialog.show();
    }

    private void mpulsa_topup(String kode,String Operator){
        Random r = new Random();
        int output = 0;
        for (int i = 0 ; i < 3 ; i++){
            output+=(r.nextInt(10)*Math.pow(10, i));
        }
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        TopupRequest param = new TopupRequest();
        param.setNoreff("order"+ String.valueOf(output));
        param.setKode(kode);
        param.setNohp(number.getText().toString());
        service.ppobtopup(param).enqueue(new Callback<TopupResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopupResponse> call, @NonNull Response<TopupResponse> response) {
                if (response.isSuccessful()) {
                    try
                    {
                        JSONObject rootJSONObject = new JSONObject(response.body().getData()).getJSONObject("data");
                        for (int i = 0; i < rootJSONObject.length(); i++)
                        {
                            String Pesan = rootJSONObject.getString("message");
                            String trxid = rootJSONObject.getString("tr_id");
                            String reffid = rootJSONObject.getString("ref_id");
                            String harga = rootJSONObject.getString("price");
                            String notujuan = rootJSONObject.getString("hp");
                            if(Pesan.equals("PROCESS")){

                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Terimakasih Pesanan Kamu Akan Segera Diproses", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        SaveHistori(trxid,reffid,harga,Operator,notujuan,"PROCESS");
                                    }
                                }, 2000);

                            }else if(Pesan.equals("SUCCESS")){

                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Transaksi Berhasil Diproses.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        SaveHistori(trxid,reffid,harga,Operator,notujuan,"SUCCESS");
                                        UpdateHistoriPPOB(reffid,harga);
                                    }
                                }, 2000);
                            }else{
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), rootJSONObject.getString("message"), Snackbar.LENGTH_LONG);
                                snackbar.show();
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 2000);
                            }

                            com.rcdriver.cs.utils.Log.d("MpulsaTopup", rootJSONObject.getString("message"));
                        }

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 2000);
                        com.rcdriver.cs.utils.Log.e("MpulsaTopupError", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<TopupResponse> call, @NonNull Throwable t) {
                android.util.Log.e("MpulsaTopup", t.getMessage());
            }
        });
    }

    private void SaveHistori(String trxid,String reffid,String harga,String op,String notujuan,String Status){
        User loginUser = BaseApp.getInstance(TransaksiPpobActivity.this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        HistoriRequest request = new HistoriRequest();
        request.id = loginUser.getId();
        request.trx = trxid;
        request.reff = reffid;
        request.biaya = harga;
        request.operator = op;
        request.notujuan = notujuan;
        request.status = Status;
        service.ppobhistori(request).enqueue(new Callback<HistoriResponse>() {
            @Override
            public void onResponse(@NonNull Call<HistoriResponse> call, @NonNull Response<HistoriResponse> response) {
                if (response.isSuccessful()) {
                    android.util.Log.e("HistoriPPOB", response.body().mesage);
                    if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                        android.util.Log.e("HistoriPPOB", response.body().mesage);
                        finish();
                    }
                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<HistoriResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                android.util.Log.e("HistoriPPOB", t.getMessage());
            }
        });
    }
    private void UpdateHistoriPPOB(String noreff,String harga){
        final User login = BaseApp.getInstance(TransaksiPpobActivity.this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
        CekRequest request = new CekRequest();
        request.setIduser(login.getId());
        request.setReff(noreff);
        request.setHarga(harga);
        request.setNama(login.getFullnama());
        request.setStatus("SUCCESS");
        request.setMysaldo(String.valueOf(login.getWalletSaldo()));
        service.updatehistori(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Transaksi Digital Dengan Noreff [" + noreff + "] Berhasil Terkirim.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        com.rcdriver.cs.utils.Log.d("UpdateHistori",response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                com.rcdriver.cs.utils.Log.d("UpdateHistori",t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onItemClick(String tipe, String nama, String kode) {
        Log.e("slectd:", tipe + "----" + kode + "---" + nama);
        operator.setText(nama);
        sOperator = kode;
        dialog.dismiss();
    }

    @Override
    public void onDenomlick(String kode, String harga, String keterangan) {
        Log.e("denom", kode + "----" + harga + "---" + kode);
        denom.setText(keterangan);
        sKode = kode;
        sHarga = harga;
        sKeterangan = keterangan;
        dialog.dismiss();
    }
}