// LOKASI: com.asia.pengemudi.activity.NewOrderActivity.java
package com.rcdriver.dr.activity;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.AcceptRequestJson;
import com.rcdriver.dr.json.AcceptResponseJson;
import com.rcdriver.dr.json.FcmResponse;
import com.rcdriver.dr.json.SendFcmRequest;
import com.rcdriver.dr.models.OrderFCM;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.PicassoTrustAll;
import com.rcdriver.dr.utils.SettingPreference;
import com.rcdriver.dr.utils.Utility;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NewOrderActivity extends AppCompatActivity {

    private static final String TAG = "NewOrderActivity";
    private static final String NOTIF_REPLY_TAG = "NotifBalasan";

    private TextView layanantext, layanandesctext, pickuptext, destinationtext, estimatetext;
    private TextView distancetext, costtext, pricetext, totaltext, timer, time, distancetextes;
    private TextView costtextes, Potongan;
    private ImageView icon;
    private Button cancel, order;
    private RelativeLayout rlprogress;
    private LinearLayout lldistance, LinFitur;

    private String waktuorder, iconfitur, diskon, layanan, layanandesc, alamatasal, alamattujuan, estimasitime;
    private String hargatotal, cost, distance, idtrans, regid, orderfitur, tokenmerchant, idpelanggan, idtransmerchant;
    private String wallett;

    private MediaPlayer BG;
    private SettingPreference sp;
    private CountDownTimer timerplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_pesanan);

        initViews();
        initTimer();
        removeNotif();
        setScreenOnFlags();

        sp = new SettingPreference(this);
        sp.updateNotif("ON");

        Intent intent = getIntent();
        getDataFromIntent(intent);

        Log.i(TAG, "------------------------------------------");
        Log.i(TAG, "onCreate: ACTIVITY DIMULAI");
        Log.i(TAG, "------------------------------------------");
        Log.d(TAG, "   Data Intent Diterima:");
        Log.d(TAG, "      id_transaksi: " + idtrans);
        Log.d(TAG, "      reg_id (customer): '" + regid + "'"); // Perhatikan nilai ini!
        Log.d(TAG, "      token_merchant: '" + tokenmerchant + "'");
        Log.d(TAG, "      order_fitur: " + orderfitur);

        if (TextUtils.isEmpty(idtrans)) {
            Log.e(TAG, "FATAL ERROR: id_transaksi kosong! Activity ditutup.");
            Toast.makeText(this, "Error: Data order tidak lengkap (ID).", Toast.LENGTH_LONG).show();
            goToMainActivity();
            return;
        }

        // --- VALIDASI AWAL REGID ---
        if (TextUtils.isEmpty(regid) || regid.equalsIgnoreCase("null")) {
            Log.e(TAG, "!!! PERINGATAN PENTING !!!: reg_id customer KOSONG atau 'null' saat onCreate! Notifikasi balasan TIDAK AKAN terkirim.");
        }


        playSound();
        updateUI();
        timerplay.start();

        cancel.setOnClickListener(view -> rejectOrder());

        String[] settings = sp.getSetting();
        if (settings != null && settings.length > 0 && "OFF".equals(settings[0])) {
            order.setOnClickListener(view -> acceptOrder());
            Log.d(TAG, "Mode Manual Accept.");
        } else {
            Log.d(TAG, "Mode Auto Accept. Memanggil acceptOrder.");
            acceptOrder();
        }
    }

    private void initViews() {
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
        timer = findViewById(R.id.timer);
        time = findViewById(R.id.time);
        distancetextes = findViewById(R.id.distancetext);
        costtextes = findViewById(R.id.costtext);
        cancel = findViewById(R.id.cancel);
        order = findViewById(R.id.order);
        rlprogress = findViewById(R.id.rlprogress);
        lldistance = findViewById(R.id.lldistance);
        LinFitur = findViewById(R.id.linearfitur);
        Potongan = findViewById(R.id.potongan);
    }

    private void getDataFromIntent(Intent intent) {
        iconfitur = intent.getStringExtra("icon");
        layanan = intent.getStringExtra("layanan");
        layanandesc = intent.getStringExtra("layanandesc");
        alamatasal = intent.getStringExtra("alamat_asal");
        alamattujuan = intent.getStringExtra("alamat_tujuan");
        estimasitime = intent.getStringExtra("estimasi_time");
        hargatotal = intent.getStringExtra("harga");
        cost = intent.getStringExtra("biaya");
        diskon = intent.getStringExtra("kredit_promo");
        distance = intent.getStringExtra("distance");
        idtrans = intent.getStringExtra("id_transaksi");
        regid = intent.getStringExtra("reg_id"); // Token FCM Customer
        wallett = intent.getStringExtra("pakai_wallet");
        orderfitur = intent.getStringExtra("order_fitur");
        tokenmerchant = intent.getStringExtra("token_merchant"); // Token FCM Merchant (jika ada)
        idpelanggan = intent.getStringExtra("id_pelanggan");
        idtransmerchant = intent.getStringExtra("id_trans_merchant");
        waktuorder = intent.getStringExtra("waktu_order");
    }

    private void initTimer() {
        timerplay = new CountDownTimer(20000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                if(!isFinishing() && !isDestroyed()){
                    Log.w(TAG, "Timer habis ("+ idtrans +"), order ditolak otomatis.");
                    rejectOrder();
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        Log.d(TAG, "Memulai updateUI...");
        Utility.currencyTXT(Potongan, diskon, this);

        if ("4".equalsIgnoreCase(orderfitur)) {
            Log.d(TAG, "updateUI: Tipe order 4 (merchant).");
            LinFitur.setVisibility(View.VISIBLE);
            estimatetext.setText(estimasitime);
            time.setText("Mitra");
            distancetextes.setText("Ongkir");
            costtextes.setText("Biaya Pesanan");
            Utility.currencyTXT(distancetext, distance, this);
            Utility.currencyTXT(costtext, cost, this);
        } else {
            Log.d(TAG, "updateUI: Tipe order biasa.");
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

        try {
            long strPotongan = (!TextUtils.isEmpty(diskon)) ? Long.parseLong(diskon) : 0;
            long hargaTotal = (!TextUtils.isEmpty(hargatotal)) ? Long.parseLong(hargatotal) : 0;
            long hasilPotongan = hargaTotal - strPotongan;
            Utility.currencyTXT(pricetext, String.valueOf(hasilPotongan), this);
            Log.d(TAG, "updateUI: Harga final = " + hasilPotongan);

            final User loginUser = BaseApp.getInstance(this).getLoginUser();
            if (loginUser != null) {
                long saldoku = loginUser.getWalletSaldo();
                Log.d(TAG, "updateUI: Saldo driver = " + saldoku);

                if ("true".equalsIgnoreCase(wallett)) {
                    Log.d(TAG, "updateUI: Bayar via SALDO.");
                    totaltext.setText("Total (SALDO)");
                    if (saldoku < hasilPotongan) {
                        Log.w(TAG, "updateUI: Saldo tidak cukup (Saldo: " + saldoku + ", Harga: " + hasilPotongan + ")");
                        showSaldoNotSufficientDialog();
                    }
                } else {
                    Log.d(TAG, "updateUI: Bayar via TUNAI.");
                    totaltext.setText("Total (TUNAI)");
                    if ("4".equalsIgnoreCase(orderfitur)) {
                        Log.d(TAG, "updateUI: Cek saldo merchant (TUNAI).");
                        double costValue = (!TextUtils.isEmpty(cost)) ? Double.parseDouble(cost) : 0.0;
                        long costLong = Math.round(costValue);
                        Log.d(TAG, "   Biaya pesanan (cost) = " + costLong);
                        if (saldoku < costLong) {
                            Log.w(TAG, "updateUI: Saldo tidak cukup (Saldo: " + saldoku + ", Biaya: " + costLong + ")");
                            showSaldoNotSufficientDialog();
                        }
                    }
                }
            } else {
                Log.w(TAG, "updateUI: Gagal dapat user login.");
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, "ERROR PARSING saat updateUI!", e);
            Toast.makeText(this, "Format data harga/jarak tidak valid.", Toast.LENGTH_LONG).show();
            rejectOrder();
            return;
        }

        Log.d(TAG, "updateUI: Memuat gambar: " + Constants.IMAGESFITUR + iconfitur);
        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESFITUR + iconfitur)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .resize(100, 100)
                .into(icon);
        Log.d(TAG, "updateUI selesai.");
    }

    private void rejectOrder() {
        Log.w(TAG, "Order DITOLAK. ID Transaksi: " + idtrans);
        stopSound();
        if (timerplay != null) {
            timerplay.cancel();
        }
        goToMainActivity();
    }

    private void acceptOrder() {
        Log.i(TAG, "------------------------------------------");
        Log.i(TAG, "Memulai proses ACCEPT ORDER");
        Log.i(TAG, "------------------------------------------");
        Log.d(TAG, "   ID Transaksi: " + idtrans);
        stopSound();
        if (timerplay != null) {
            timerplay.cancel();
        }
        rlprogress.setVisibility(View.VISIBLE);

        final User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) {
            rlprogress.setVisibility(View.GONE);
            Toast.makeText(this, "Sesi login berakhir.", Toast.LENGTH_LONG).show();
            goToMainActivity();
            return;
        }

        Log.d(TAG, "acceptOrder: Data Kunci SEBELUM PANGGIL API accept:");
        Log.d(TAG, "   -> ID Driver: " + loginUser.getId());
        Log.d(TAG, "   -> ID Transaksi: " + idtrans);
        Log.d(TAG, "   -> Token Customer (regid): '" + regid + "'");
        Log.d(TAG, "   -> Token Merchant: '" + tokenmerchant + "'");
        Log.d(TAG, "   -> Order Fitur: " + orderfitur);

        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AcceptRequestJson param = new AcceptRequestJson();
        param.setId(loginUser.getId());
        param.setIdtrans(idtrans);

        Log.d(TAG, "Memanggil API endpoint 'accept'...");
        userService.accept(param).enqueue(new Callback<AcceptResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AcceptResponseJson> call, @NonNull Response<AcceptResponseJson> response) {
                rlprogress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    sp.updateNotif("OFF");
                    if ("berhasil".equalsIgnoreCase(response.body().getMessage())) {
                        Log.i(TAG, "API 'accept' order BERHASIL. Memulai pengiriman NOTIFIKASI BALASAN...");

                        OrderFCM orderfcm = new OrderFCM();
                        orderfcm.id_driver = loginUser.getId();
                        orderfcm.id_transaksi = idtrans;
                        orderfcm.response = "2"; // Kode diterima

                        // Kirim notif balasan ke merchant jika tipe 4
                        if ("4".equalsIgnoreCase(orderfitur)) {
                            Log.d(TAG, "   Order tipe 4, menyiapkan notif ke MERCHANT.");
                            OrderFCM merchantFcmData = createFcmDataForMerchant(loginUser);
                            sendMessageToCustomer(tokenmerchant, merchantFcmData); // Kirim ke merchant
                        }

                        // Kirim notif balasan ke customer (selalu)
                        Log.d(TAG, "   Menyiapkan notif ke CUSTOMER.");
                        orderfcm.desc = getString(R.string.notification_start); // Deskripsi standar untuk customer
                        sendMessageToCustomer(regid, orderfcm); // Kirim ke customer

                        goToMainActivityAfterAccept();

                    } else {
                        Log.e(TAG, "API 'accept' order DITOLAK oleh server: " + response.body().getMessage());
                        Toast.makeText(NewOrderActivity.this, "Gagal menerima: " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        goToMainActivity();
                    }
                } else {
                    Log.e(TAG, "API 'accept' order GAGAL (Server Error): Code " + response.code());
                    Toast.makeText(NewOrderActivity.this, "Gagal menerima pesanan (Server Error " + response.code() + ").", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AcceptResponseJson> call, @NonNull Throwable t) {
                rlprogress.setVisibility(View.GONE);
                Log.e(TAG, "API 'accept' order GAGAL (Koneksi Error): ", t);
                Toast.makeText(NewOrderActivity.this, "Koneksi Bermasalah!", Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }
        });
    }

    // Helper method untuk membuat data FCM khusus merchant
    private OrderFCM createFcmDataForMerchant(User loginUser) {
        OrderFCM fcmData = new OrderFCM();
        fcmData.id_driver = loginUser.getId();
        fcmData.id_transaksi = idtrans;
        fcmData.response = "2"; // Kode diterima
        fcmData.desc = "Driver sedang memesan pesanan Kamu.";
        fcmData.id_pelanggan = idpelanggan;
        fcmData.invoice = "INV-" + idtrans + idtransmerchant;
        fcmData.ordertime = waktuorder;
        return fcmData;
    }


    private void sendMessageToCustomer(final String regIDTujuan, final OrderFCM responseData) {
        // --- PENGECEKAN KEAMANAN YANG LEBIH KETAT ---
        if (TextUtils.isEmpty(regIDTujuan) || regIDTujuan.equalsIgnoreCase("null")) {
            Log.e(NOTIF_REPLY_TAG, "!!! GAGAL KIRIM NOTIF BALASAN !!!");
            Log.e(NOTIF_REPLY_TAG, "   Alasan: Token tujuan (regIDTujuan) KOSONG atau 'null'. Tidak mengirim.");
            Log.e(NOTIF_REPLY_TAG, "   Data yang seharusnya dikirim: " + new Gson().toJson(responseData));
            return;
        }

        Log.i(NOTIF_REPLY_TAG, "==> MEMULAI PENGIRIMAN NOTIFIKASI BALASAN");
        Log.d(NOTIF_REPLY_TAG, "   Tujuan Token: " + regIDTujuan);

        final User login = BaseApp.getInstance(this).getLoginUser();
        if (login != null) {
            DriverService service = ServiceGenerator.createService(DriverService.class, login.getNoTelepon(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(regIDTujuan);
            param.setData(responseData);

            Gson gson = new Gson();
            String requestJson = gson.toJson(param);
            Log.d(NOTIF_REPLY_TAG, "   Request Body JSON ke API fcmnotif: " + requestJson);

            Log.d(NOTIF_REPLY_TAG, "   Memanggil API endpoint 'fcmnotif'...");
            service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.i(NOTIF_REPLY_TAG, "   [OK] BERHASIL meminta server mengirim notif ke " + regIDTujuan + ". Respon Server API fcmnotif: " + response.body().getMessage());
                    } else {
                        String errorBody = "";
                        try {
                            if (response.errorBody() != null) errorBody = response.errorBody().string();
                        } catch (Exception e) { Log.e(NOTIF_REPLY_TAG, "   Error membaca error body", e);}
                        Log.e(NOTIF_REPLY_TAG, "   [GAGAL] Gagal meminta server mengirim notif ke " + regIDTujuan + ". Code: " + response.code() + ". Error Body: " + errorBody);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FcmResponse> call, @NonNull Throwable t) {
                    Log.e(NOTIF_REPLY_TAG, "   [GAGAL] Gagal koneksi ke API fcmnotif untuk " + regIDTujuan, t);
                }
            });
        } else {
            Log.w(NOTIF_REPLY_TAG, "   [GAGAL] Gagal kirim notif balasan, user driver tidak login saat ini.");
        }
    }

    private void showSaldoNotSufficientDialog() {
        Log.w(TAG, "Saldo tidak cukup. Menampilkan dialog.");
        stopSound();
        if (timerplay != null) timerplay.cancel();

        new AlertDialog.Builder(this)
                .setMessage("Mohon maaf, saldo Anda tidak mencukupi untuk mengambil pesanan ini!")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> goToMainActivity())
                .create()
                .show();
    }

    private void playSound() {
        stopSound();
        try {
            BG = MediaPlayer.create(this, R.raw.orderan);
            if (BG != null) {
                BG.setLooping(true);
                BG.setVolume(1.0f, 1.0f);
                BG.start();
                Log.d(TAG, "Suara notifikasi dimulai (looping).");
            } else {
                Log.e(TAG, "Gagal membuat MediaPlayer dari R.raw.orderan.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error saat playSound", e);
        }
    }

    private void stopSound() {
        try {
            if (BG != null) {
                if (BG.isPlaying()) {
                    BG.stop();
                    Log.d(TAG, "Suara notifikasi dihentikan.");
                }
                BG.release();
                BG = null;
                Log.d(TAG, "MediaPlayer di-release.");
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, "Error (IllegalStateException) saat stopSound/release MediaPlayer", e);
            BG = null;
        } catch (Exception e) {
            Log.e(TAG, "Error umum saat stopSound", e);
        }
    }

    private void goToMainActivity() {
        Log.d(TAG, "Kembali ke MainActivity (finish activity ini).");
        sp.updateNotif("OFF");
        Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void goToMainActivityAfterAccept() {
        Log.d(TAG, "Order diterima, kembali ke MainActivity (finish activity ini).");
        Intent i = new Intent(NewOrderActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
            Log.d(TAG, "Semua notifikasi dari status bar dibersihkan.");
        }
    }

    private void setScreenOnFlags() {
        Log.d(TAG, "Mengatur flags agar layar menyala dan tampil di lockscreen.");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null) {
                keyguardManager.requestDismissKeyguard(this, null);
            }
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "Tombol back ditekan, diabaikan.");
        // Sengaja dibiarkan kosong
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "------------------------------------------");
        Log.i(TAG, "onDestroy: ACTIVITY DIHANCURKAN");
        Log.i(TAG, "------------------------------------------");
        stopSound();
        if (timerplay != null) {
            timerplay.cancel();
            Log.d(TAG, "Timer dihentikan.");
        }
    }
}