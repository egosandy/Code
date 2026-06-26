package com.rcdriver.cs.ppob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.ppob.item.PpobHistori;
import com.rcdriver.cs.ppob.json.CekRequest;
import com.rcdriver.cs.ppob.json.CekResponse;
import com.rcdriver.cs.ppob.json.DataHistoriResponse;
import com.rcdriver.cs.ppob.model.HistoriModels;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHistoriP extends AppCompatActivity {
    RecyclerView RecListPPOB;
    PpobHistori itemListPPOB;
    private String listharga;
    public static ArrayList<HistoriModels> mDataList = new ArrayList<HistoriModels>();
    RelativeLayout rlprogress,rltransaksi;
    //Detail
    TextView notujuan,deskripsi,code,serial,DetailTrx;
    ImageView SetClose;
    EditText noWA;
    Button BtnWA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historippob);
        RecListPPOB = findViewById(R.id.ListPPOB);
        rlprogress = findViewById(R.id.rlprogress);
        rltransaksi = findViewById(R.id.rltransaksi);
        SetClose = findViewById(R.id.SetClose);
        DetailTrx = findViewById(R.id.DetailTrx);
        noWA = findViewById(R.id.noWA);
        BtnWA = findViewById(R.id.BtnWA);
        //Layanan
        notujuan = findViewById(R.id.notujuan);
        deskripsi = findViewById(R.id.deskripsi);
        code = findViewById(R.id.code);
        serial = findViewById(R.id.serial);
        rltransaksi.setVisibility(View.GONE);
        //List PPOB
        RecListPPOB.setLayoutManager(new GridLayoutManager(this, 1));
        RecListPPOB.setHasFixedSize(true);
        RecListPPOB.setNestedScrollingEnabled(false);
        mDataList.clear();
    }
    @Override
    protected void onResume(){
        super.onResume();
        daftarhistori();
    }
    private void daftarhistori(){
        final User login = BaseApp.getInstance(ActivityHistoriP.this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
        CekRequest request = new CekRequest();
        request.setIduser(login.getId());
        service.datahistori(request).enqueue(new Callback<DataHistoriResponse>() {
            @Override
            public void onResponse(@NonNull Call<DataHistoriResponse> call, @NonNull Response<DataHistoriResponse> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        for (int i = 0; i < response.body().getData().size(); i++)
                        {
                            HistoriModels model = response.body().getData().get(i);
                            mDataList.add(model);
                            itemListPPOB = new PpobHistori(ActivityHistoriP.this, mDataList, R.layout.ppob_listhistori, new PpobHistori.OnItemClickListener() {
                                @Override
                                public void onItemClick(final HistoriModels item) {
                                    rltransaksi.setVisibility(View.GONE);
                                    mpulsa_Cek(item.getReff());
                                    Log.d("ppobklik",item.getReff());
                                    SetClose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            rltransaksi.setVisibility(View.GONE);
                                        }
                                    });
                                    //   mpulsa_topup(item.getKode());
                                }
                            });

                            RecListPPOB.setAdapter(itemListPPOB);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataHistoriResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //--------------------------------------- Cek Mpulsa ----------------------------------------
    private String datatopup;
    private void mpulsa_Cek(String noreff){
        User login = BaseApp.getInstance(ActivityHistoriP.this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, login.getNoTelepon(), login.getPassword());
        CekRequest param = new CekRequest();
        param.setNoreff(noreff);
        service.cektopup(param).enqueue(new Callback<CekResponse>() {
            @Override
            public void onResponse(@NonNull Call<CekResponse> call, @NonNull Response<CekResponse> response) {
                if (response.isSuccessful()) {
                    rltransaksi.setVisibility(View.VISIBLE);
                    datatopup = response.body().getData();
                    datatopup(datatopup);
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<CekResponse> call, @NonNull Throwable t) {
                android.util.Log.e("MpulsaCek", t.getMessage());
            }
        });
    }
    private void datatopup(String response)
    {
        try
        {
            JSONObject rootJSONObject = new JSONObject(response).getJSONObject("data");
            for (int i = 0; i < rootJSONObject.length(); i++)
            {
                String Notujuan = rootJSONObject.getString("hp");
                String Reffid = rootJSONObject.getString("ref_id");
                String Kode = rootJSONObject.getString("code");
                String Harga = rootJSONObject.getString("price");
                String Pesan = rootJSONObject.getString("message");
                String Pin = rootJSONObject.getString("sn");
                DetailTrx.setText("Detail Transaksi [" + Reffid + "]");
                notujuan.setText(Notujuan);
                code.setText(Kode);
                deskripsi.setText(Pesan);
                if(Pin.equals("") || Pin.isEmpty()){
                    serial.setText("-");
                }else{
                    serial.setText(Pin);
                }
                BtnWA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(noWA.getText().equals("")){
                            Toast.makeText(ActivityHistoriP.this, "Nomor Tidak Boleh Kosong.", Toast.LENGTH_SHORT).show();
                        }else{
                            Long setharga = Long.parseLong(Harga);
                            sendwa(noWA.getText().toString(),Reffid,Notujuan,formatRupiah(setharga),Pin);
                        }

                    }
                });

                Log.d("MpulsaCek", Reffid + " | " + rootJSONObject.getString("message"));
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
            Log.e("MpulsaCekError", e.getMessage());
        }
    }
    private void sendwa(String nomor,String reff,String tujuan,String harga,String Serial){
        try{
            String Pesan = "GOJASA TOPUP DIGITAL\n" + "No Reff: " + reff + "\nTujuan : " + tujuan + "\nBiaya : "+harga+"\nSerial : "+Serial;
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + nomor + "&text=" + Pesan;
            sendIntent.setData(Uri.parse(url));
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static String formatRupiah(Long number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
