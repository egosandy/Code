package com.rcdriver.cs.activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.databinding.ActivityRateBinding;
import com.rcdriver.cs.json.DetailRequestJson;
import com.rcdriver.cs.json.DetailTransResponseJson;
import com.rcdriver.cs.json.PointRespon;
import com.rcdriver.cs.json.PoinRequest;
import com.rcdriver.cs.json.RateRequestJson;
import com.rcdriver.cs.json.RateResponseJson;
import com.rcdriver.cs.json.RequestJson;
import com.rcdriver.cs.json.ResponseJson;
import com.rcdriver.cs.json.SaldoResponse;
import com.rcdriver.cs.json.TipRequestJson;
import com.rcdriver.cs.json.UpdateStatusRequest;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.PointModel;
import com.rcdriver.cs.models.SaldoModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.NetworkManager;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateActivity extends AppCompatActivity {
    String iddriver, idtrans, response, pakewallet, namadriver, totalbiaya, submit, idfitur, fotodriver;
    float rate = 3;
    private ActivityRateBinding binding;
    Timer timer = new Timer();
    private List<SaldoModel> mSaldo = new ArrayList<>();
    private List<PointModel> mPoint = new ArrayList<>();
    private Handler handler;
    private Runnable updateStatus;

    private static String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        iddriver = intent.getStringExtra("id_driver");
        idtrans = intent.getStringExtra("id_transaksi");
        response = intent.getStringExtra("response");
        namadriver = intent.getStringExtra("namadriver");
        totalbiaya = intent.getStringExtra("total_biaya");
        pakewallet = intent.getStringExtra("pake_wallet");
        idfitur = intent.getStringExtra("fitur");
        fotodriver = intent.getStringExtra("fotodriver");
        rate = intent.getFloatExtra("rating", 3);

        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESDRIVER + fotodriver)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.image);
        binding.namadriver.setText(namadriver);
        getData(idtrans, iddriver);
        PointDriver(iddriver);

        binding.TxtPoint.setText("0");
        submit = "true";
        shimmeractive();
        removeNotif();

        binding.ratingView.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            String ratingValue = String.valueOf(rating);
            binding.txtRating.setText(ratingValue);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCekrate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCekRate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCekRate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopCekRate();
    }

    private void shimmeractive() {
        binding.shimmername.startShimmerAnimation();
    }

    private void shimmernonactive() {
        binding.shimmername.setVisibility(View.GONE);
        binding.image.setVisibility(View.VISIBLE);
        binding.namadriver.setVisibility(View.VISIBLE);
        binding.addComment.setVisibility(View.VISIBLE);
        binding.submit.setVisibility(View.VISIBLE);
        binding.ratingView.setVisibility(View.VISIBLE);
        binding.shimmername.stopShimmerAnimation();
    }

    private void getData(String idtrans, String iddriver) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) return;
        binding.Saldoku.setText(String.valueOf(loginUser.getWalletSaldo()));

        long transactionCost = 0;
        if (totalbiaya != null && !totalbiaya.isEmpty()) {
            try {
                transactionCost = (long) Double.parseDouble(totalbiaya);
            } catch (NumberFormatException e) {
                Log.e("RateActivity", "Gagal parsing totalbiaya: " + totalbiaya, e);
            }
        }

        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdDriver(iddriver);
        long finalTransactionCost = transactionCost;
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getDriver() != null && !response.body().getDriver().isEmpty()) {
                    DriverModel driver = response.body().getDriver().get(0);
                    parsedata(driver);
                    String formattedPrice = formatRupiah((double) finalTransactionCost).replaceAll(",00", "");
                    binding.txtTotal.setText(formattedPrice);
                    binding.txtfitur.setVisibility(View.GONE);
                    binding.txtfitur.setText(idfitur);
                }
            }
            @Override
            public void onFailure(@NonNull Call<DetailTransResponseJson> call, @NonNull Throwable t) {
                Log.e("RateActivity", "Gagal panggil API detailtrans.", t);
            }
        });
    }

    private void parsedata(final DriverModel driver) {
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        binding.ratingView.setRating(rate);

        PointDriver(driver.getId());
        SaldoDriver(driver.getId());

        if ("true".equals(submit)) {
            View.OnClickListener tipClickListener = v -> {
                long tipAmount = 0;
                if (v.getId() == R.id.rp1000) tipAmount = 1000;
                else if (v.getId() == R.id.rp2000) tipAmount = 2000;
                else if (v.getId() == R.id.rp3000) tipAmount = 3000;
                else if (v.getId() == R.id.rp4000) tipAmount = 4000;
                else if (v.getId() == R.id.rp5000) tipAmount = 5000;

                if (userLogin.getWalletSaldo() < tipAmount) {
                    Toast.makeText(this, "Saldo Anda Kurang Untuk Memberikan Tip.", Toast.LENGTH_LONG).show();
                    binding.txtnominal.setText("0");
                } else {
                    KirimTip(iddriver, String.valueOf(tipAmount));
                    binding.txtnominal.setText(String.valueOf(tipAmount));
                    // Reset all text colors to black
                    binding.rp1000.setTextColor(ContextCompat.getColor(this, R.color.black));
                    binding.rp2000.setTextColor(ContextCompat.getColor(this, R.color.black));
                    binding.rp3000.setTextColor(ContextCompat.getColor(this, R.color.black));
                    binding.rp4000.setTextColor(ContextCompat.getColor(this, R.color.black));
                    binding.rp5000.setTextColor(ContextCompat.getColor(this, R.color.black));
                    // Set selected text color to primary
                    ((TextView) v).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                }
            };

            binding.rp1000.setOnClickListener(tipClickListener);
            binding.rp2000.setOnClickListener(tipClickListener);
            binding.rp3000.setOnClickListener(tipClickListener);
            binding.rp4000.setOnClickListener(tipClickListener);
            binding.rp5000.setOnClickListener(tipClickListener);

            binding.submit.setOnClickListener(v -> {
                PerbaruiStatus("0", "0", "0", "false", "0", "0");
                RateRequestJson request = new RateRequestJson();
                request.id_transaksi = idtrans;
                request.id_pelanggan = userLogin.getId();
                request.id_driver = iddriver;
                request.rating = String.valueOf(binding.ratingView.getRating());
                request.catatan = binding.addComment.getText().toString();
                ratingUser(request);

                float currentRating = binding.ratingView.getRating();
                String pointToSend = "0";
                if (currentRating == 3.0f) pointToSend = "1";
                else if (currentRating == 4.0f) pointToSend = "2";
                else if (currentRating == 5.0f) pointToSend = "3";
                SendPoint(iddriver, pointToSend);
            });
        }
        shimmernonactive();
    }

    private void ratingUser(RateRequestJson request) {
        submit = "false";
        binding.submit.setText(getString(R.string.waiting_pleaseWait));
        binding.submit.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corners_button));

        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        service.rateDriver(request).enqueue(new Callback<RateResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RateResponseJson> call, @NonNull Response<RateResponseJson> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().mesage)) {
                    Intent i = new Intent(RateActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("Status", "1");
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RateResponseJson> call, @NonNull Throwable t) {
                Toast.makeText(RateActivity.this, "Gagal mengirim rating.", Toast.LENGTH_SHORT).show();
                submit = "true";
                binding.submit.setText("Submit");
                binding.submit.setBackground(ContextCompat.getDrawable(RateActivity.this, R.drawable.button_round_1));
            }
        });
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(0);
        }
    }

    private void PerbaruiStatus(String idtrans, String iddriver, String biaya, String wallet, String fitur, String respon) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateStatusRequest param = new UpdateStatusRequest();
        param.setId(loginUser.getId());
        param.setId_transaksi(idtrans);
        // ... set other params
        service.updateStatus(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) Log.d("UPDATE STATUS", "Status berhasil direset.");
            }
            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Log.e("UPDATE STATUS", "Gagal panggil API update status.", t);
            }
        });
    }

    private void startCekrate() {
        handler = new Handler(Looper.getMainLooper());
        updateStatus = () -> {
            if (NetworkManager.isConnectToInternet(RateActivity.this)) {
                PointDriver(iddriver);
                SaldoDriver(iddriver);
            }
            handler.postDelayed(updateStatus, 3000);
        };
        handler.postDelayed(updateStatus, 3000);
    }

    private void stopCekRate() {
        if (handler != null && updateStatus != null) {
            handler.removeCallbacks(updateStatus);
        }
    }

    private void PointDriver(String iddriver){
        if (iddriver == null || iddriver.isEmpty()) return;
        User user = BaseApp.getInstance(this).getLoginUser();
        PoinRequest request = new PoinRequest();
        request.setId(iddriver);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.GetPoin(request).enqueue(new Callback<PointRespon>() {
            @Override
            public void onResponse(@NonNull Call<PointRespon> call, @NonNull Response<PointRespon> response) {
                if (response.isSuccessful() && response.body() != null && "success".equalsIgnoreCase(response.body().getMessage())) {
                    mPoint = response.body().getData();
                    String currentPoint = (mPoint != null && !mPoint.isEmpty()) ? mPoint.get(0).getPoint() : "0";
                    binding.TxtPoint.setText(Objects.requireNonNullElse(currentPoint, "0"));
                }
            }
            @Override
            public void onFailure(@NonNull Call<PointRespon> call, @NonNull Throwable t) {}
        });
    }

    private void SaldoDriver(String iddriver){
        if (iddriver == null || iddriver.isEmpty()) return;
        User user = BaseApp.getInstance(this).getLoginUser();
        RequestJson request = new RequestJson();
        request.setId(iddriver);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.SaldoDriver(request).enqueue(new Callback<SaldoResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaldoResponse> call, @NonNull Response<SaldoResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equalsIgnoreCase(response.body().getMessage())) {
                    mSaldo = response.body().getData();
                    if (mSaldo != null && !mSaldo.isEmpty()) {
                        binding.txtsaldo.setText(mSaldo.get(0).getSaldo());
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<SaldoResponse> call, @NonNull Throwable t) {}
        });
    }

    private void KirimTip(String iddriver, String jumlah){
        if (iddriver == null || iddriver.isEmpty()) return;
        User user = BaseApp.getInstance(this).getLoginUser();
        TipRequestJson request = new TipRequestJson();
        request.setId(user.getId());
        request.setIddriver(iddriver);
        request.setAmount(jumlah);
        //... set other request params
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.KirimSaldo(request).enqueue(new Callback<SaldoResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaldoResponse> call, @NonNull Response<SaldoResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equalsIgnoreCase(response.body().getMessage())) {
                    binding.tiplayout.setVisibility(View.GONE);
                    Toast.makeText(RateActivity.this, "Tip berhasil dikirim!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RateActivity.this, "Gagal mengirim tip.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<SaldoResponse> call, @NonNull Throwable t) {
                Toast.makeText(RateActivity.this, "Gagal mengirim tip, terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SendPoint(String iddriver, String poin){
        if (iddriver == null || iddriver.isEmpty()) return;
        User user = BaseApp.getInstance(this).getLoginUser();
        RequestJson request = new RequestJson();
        request.setId(iddriver);
        request.setAmount(poin);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.KirimPoin(request).enqueue(new Callback<PointRespon>() {
            @Override
            public void onResponse(@NonNull Call<PointRespon> call, @NonNull Response<PointRespon> response) {
                if (response.isSuccessful() && response.body() != null && "success".equalsIgnoreCase(response.body().getMessage())) {
                    Log.d("KirimPoint", "Berhasil");
                }
            }
            @Override
            public void onFailure(@NonNull Call<PointRespon> call, @NonNull Throwable t) {}
        });
    }
}