package com.rcdriver.dr.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.AcceptRequestJson;
import com.rcdriver.dr.json.AcceptResponseJson;
import com.rcdriver.dr.json.DetailRequestJson;
import com.rcdriver.dr.json.DetailTransResponseJson;
import com.rcdriver.dr.json.FcmResponse;
import com.rcdriver.dr.json.SendFcmRequest;
import com.rcdriver.dr.models.OrderFCM;
import com.rcdriver.dr.models.PelangganModel;
import com.rcdriver.dr.models.TransaksiModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.PicassoTrustAll;
import com.rcdriver.dr.utils.SettingPreference;
import com.rcdriver.dr.utils.Utility;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

// Hapus import ButterKnife
// import butterknife.BindView;
// import butterknife.ButterKnife;

public class PendingActivity extends AppCompatActivity {
    // Deklarasi Variabel View (tanpa anotasi @BindView)
    private TextView layanantext;
    private TextView layanandesctext;
    private TextView pickuptext;
    private TextView destinationtext;
    private TextView estimatetext;
    private TextView distancetext;
    private TextView costtext;
    private TextView pricetext;
    private TextView totaltext;
    private ImageView icon;
    private TextView time;
    private TextView distancetextes;
    private TextView costtextes;
    private Button cancel;
    private Button order;
    private RelativeLayout rlprogress;
    private LinearLayout lldistance;
    private LinearLayout LinFitur;
    private TextView Potongan;

    String iconfitur, diskon, layanan, layanandesc, alamatasal, alamattujuan, estimasitime, hargatotal, cost, distance, idtrans, regid, orderfitur, tokenmerchant, idpelanggan, idtransmerchant;
    boolean wallett;
    Date waktuorder;
    SettingPreference sp;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        // Hapus ButterKnife.bind(this);

        // Inisialisasi Views menggunakan findViewById
        layanantext = findViewById(R.id.layanan);
        layanandesctext = findViewById(R.id.layanandes);
        pickuptext = findViewById(R.id.pickUpText);
        destinationtext = findViewById(R.id.destinationText);
        estimatetext = findViewById(R.id.fitur);
        distancetext = findViewById(R.id.distance);
        costtext = findViewById(R.id.cost);
        pricetext = findViewById(R.id.price);
        totaltext = findViewById(R.id.totaltext);
        icon = findViewById(R.id.image);
        time = findViewById(R.id.time);
        distancetextes = findViewById(R.id.distancetext);
        costtextes = findViewById(R.id.costtext);
        cancel = findViewById(R.id.cancel);
        order = findViewById(R.id.order);
        rlprogress = findViewById(R.id.rlprogress);
        lldistance = findViewById(R.id.lldistance);
        LinFitur = findViewById(R.id.linearfitur);
        Potongan = findViewById(R.id.potongan);

        context = this;
        sp = new SettingPreference(this);
        sp.updateNotif("ON");
        Intent intent = getIntent();
        idtrans = intent.getStringExtra("id_transaksi");
        idpelanggan = intent.getStringExtra("id_pelanggan");
        getData(idtrans, idpelanggan);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent toOrder = new Intent(PendingActivity.this, MainActivity.class);
                toOrder.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(toOrder);
            }
        });

        if ("OFF".equals(new SettingPreference(this).getSetting()[0])) {
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getaccept();
                }
            });
        } else {
            getaccept();
        }
    }

    private void getData(final String idtrans, final String idpelanggan) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) {
            Toast.makeText(context, "Sesi login berakhir, silakan masuk kembali.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdPelanggan(idpelanggan);
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful() && responsedata.body() != null) {
                    if (responsedata.body().getData() == null || responsedata.body().getData().isEmpty()
                            || responsedata.body().getPelanggan() == null || responsedata.body().getPelanggan().isEmpty()) {
                        Toast.makeText(context, "Data pesanan kosong dari server.", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                    final TransaksiModel transaksi = responsedata.body().getData().get(0);
                    iconfitur = transaksi.getIcon();
                    layanan = transaksi.getFitur();
                    layanandesc = transaksi.getKeterangan();
                    alamatasal = transaksi.getAlamatAsal();
                    alamattujuan = transaksi.getAlamatTujuan();
                    estimasitime = transaksi.getEstimasi();
                    hargatotal = String.valueOf(transaksi.getHarga());
                    cost = transaksi.getBiaya_akhir();
                    diskon = transaksi.getKreditPromo();
                    distance = String.valueOf(transaksi.getJarak());

                    PelangganModel pelanggan = responsedata.body().getPelanggan().get(0);
                    regid = pelanggan.getToken();
                    wallett = transaksi.isPakaiWallet();
                    orderfitur = transaksi.getOrderFitur();
                    tokenmerchant = transaksi.getToken_merchant();

                    idtransmerchant = transaksi.getIdtransmerchant();
                    waktuorder = transaksi.getWaktuOrder();
                    Utility.currencyTXT(Potongan, diskon, context);
                    if ("4".equalsIgnoreCase(orderfitur)) {
                        LinFitur.setVisibility(View.VISIBLE);
                        estimatetext.setText(estimasitime);
                        time.setText("Mitra");
                        distancetextes.setText("Ongkir");
                        costtextes.setText("Biaya Pesanan");
                        Utility.currencyTXT(distancetext, distance, context);
                        Utility.currencyTXT(costtext, cost, context);
                    } else {
                        lldistance.setVisibility(View.GONE);
                        LinFitur.setVisibility(View.VISIBLE);
                        estimatetext.setText(estimasitime);
                        distancetext.setText(distance);
                        costtext.setText(cost);
                    }
                    layanantext.setText(layanan);
                    layanandesctext.setText(layanandesc);
                    pickuptext.setText(alamatasal);
                    destinationtext.setText(alamattujuan);

                    long StrPotongan = (diskon != null && !diskon.isEmpty()) ? Long.parseLong(diskon) : 0;
                    long HargaTotal = (hargatotal != null && !hargatotal.isEmpty()) ? Long.parseLong(hargatotal) : 0;
                    long HasilPotongan = HargaTotal - StrPotongan;
                    Utility.currencyTXT(pricetext, String.valueOf(HasilPotongan), context);
                    final User currentUser = BaseApp.getInstance(context).getLoginUser();
                    long Saldoku = currentUser.getWalletSaldo();

                    if (wallett) {
                        if (Saldoku < HasilPotongan) {
                            showSaldoNotSufficientDialog();
                        }
                        totaltext.setText("Total (SALDO)");
                    } else {
                        try {
                            if ("4".equalsIgnoreCase(orderfitur) && Saldoku < Double.parseDouble(distance)) {
                                showSaldoNotSufficientDialog();
                            }
                        } catch (NumberFormatException e) {
                            // Handle error jika `distance` tidak valid
                        }
                        totaltext.setText("Total (TUNAI)");
                    }
                    PicassoTrustAll.getInstance(context)
                            .load(Constants.IMAGESFITUR + iconfitur)
                            .placeholder(R.drawable.logo)
                            .resize(100, 100)
                            .into(icon);

                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailTransResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(context, "Gagal memuat data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSaldoNotSufficientDialog() {
        new AlertDialog.Builder(context)
                .setMessage("Mohon Maaf Saldo Anda Tidak Mencukupi\nUntuk Mengambil Pesanan ini!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Intent toOrder = new Intent(PendingActivity.this, MainActivity.class);
                        toOrder.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(toOrder);
                    }
                })
                .create()
                .show();
    }


    private void getaccept() {
        rlprogress.setVisibility(View.VISIBLE);
        final User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) return;

        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);
        userService.accept(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull final Response<AcceptResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sp.updateNotif("OFF");
                    if ("berhasil".equalsIgnoreCase(response.body().getMessage())) {
                        rlprogress.setVisibility(View.GONE);
                        Intent i = new Intent(PendingActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.id_driver = loginUser.getId();
                        orderfcm.id_transaksi = idtrans;
                        orderfcm.response = "2";
                        if ("4".equalsIgnoreCase(orderfitur)) {
                            orderfcm.desc = "Driver Sedang Memesan Pesanan Kamu.";
                            orderfcm.id_pelanggan = idpelanggan;
                            orderfcm.invoice = "INV-" + idtrans + idtransmerchant;
                            orderfcm.ordertime = (waktuorder != null) ? waktuorder.toString() : "";
                            sendMessageToDriver(tokenmerchant, orderfcm);
                        } else {
                            orderfcm.desc = getString(R.string.notification_start);
                        }
                        sendMessageToDriver(regid, orderfcm);
                        finish();
                    } else {
                        Toast.makeText(PendingActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        goToMainActivity();
                    }
                } else {
                    Toast.makeText(PendingActivity.this, "Gagal menerima pesanan.", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(PendingActivity.this, "Error Connection!", Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {
        sp.updateNotif("OFF");
        rlprogress.setVisibility(View.GONE);
        Intent i = new Intent(PendingActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void sendMessageToDriver(final String regIDTujuan, final OrderFCM response) {
        final User login = BaseApp.getInstance(PendingActivity.this).getLoginUser();
        if (login != null) {
            DriverService service = ServiceGenerator.createService(DriverService.class, login.getNoTelepon(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(regIDTujuan);
            param.setData(response);
            service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        android.util.Log.d("ResultFCM", response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FcmResponse> call, @NonNull Throwable t) {
                    android.util.Log.e("TestFCM", "Failure: " + t.getMessage());
                }
            });
        }
    }
}