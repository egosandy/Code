package com.rcdriver.mt.activity.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rcdriver.mt.R;
import com.rcdriver.mt.constants.BaseApp;
import com.rcdriver.mt.constants.Constants;
import com.rcdriver.mt.json.payment.CheckResponseJson;
import com.rcdriver.mt.models.User;
import com.rcdriver.mt.models.payment.Transaksi;
import com.rcdriver.mt.utils.PicassoTrustAll;
import com.rcdriver.mt.utils.Utility;
import com.rcdriver.mt.utils.api.ServiceGenerator;
import com.rcdriver.mt.utils.api.service.PaymentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFixedActivity extends AppCompatActivity {
    Context context;
    User user;
    TextView tvExpired, tvNoVa, tvSalinNovA, tvAmount, tvSalinAmount,
            textNominal, textFee, tvCaption, tvMethod;
    CardView cvExpired;
    ProgressBar progressBar;
    Button button;
    View progressBarView, containerView;
    String invoice;
    ImageView ivMethod, backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_fixed);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        tvExpired = findViewById(R.id.textViewExpired);
        tvNoVa = findViewById(R.id.textViewNoVa);
        tvSalinNovA = findViewById(R.id.textViewSalinNoVa);
        tvAmount = findViewById(R.id.textViewAmount);
        tvSalinAmount = findViewById(R.id.textViewSalinAmount);
        textNominal = findViewById(R.id.textNominal);
        textFee = findViewById(R.id.textFee);
        tvCaption = findViewById(R.id.caption_code);
        progressBar = findViewById(R.id.progressBar);
        progressBarView = findViewById(R.id.progress_bar_view);
        containerView = findViewById(R.id.layout_pembayaran);
        button = findViewById(R.id.button_check);
        cvExpired = findViewById(R.id.cardViewExpired);
        tvMethod = findViewById(R.id.textViewMethod);
        ivMethod = findViewById(R.id.imageViewMethod);
        backButton = findViewById(R.id.back_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        invoice = getIntent().getStringExtra(Constants.INTENT_ID);
        check(invoice);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(invoice);
            }
        });
    }

    private void check(String inv){
        PaymentService service = ServiceGenerator.createService(PaymentService.class, user.getId(), user.getPhone());
        service.check(inv).enqueue(new Callback<CheckResponseJson>() {
            @Override
            public void onResponse(Call<CheckResponseJson> call, Response<CheckResponseJson> response) {
                progressBarView.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    if(response.body().getCode().equalsIgnoreCase("200")){
                        Transaksi transaksi = response.body().getTransaksi();
                        containerView.setVisibility(View.VISIBLE);
                        setData(transaksi);
                    }
                }

            }

            @Override
            public void onFailure(Call<CheckResponseJson> call, Throwable t) {
                progressBarView.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    private void setData(Transaksi transaksi){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateNow = new Date();
        Date dateExpired = null;
        try {
            dateExpired = input.parse(transaksi.getExpDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvExpired.setText(Utility.setFormatDateZ(transaksi.getExpDate()));
        if (dateNow.after(dateExpired)) {
            cvExpired.setVisibility(View.VISIBLE);
        }

        PicassoTrustAll.getInstance(context)
                .load(Constants.IMAGESSLIDER + transaksi.getMetodeImage())
                .error(R.drawable.logo)
                .into(ivMethod);
        tvMethod.setText(transaksi.getMetodeNama());

        if(transaksi.getMetodeJenis() == 2){
            tvCaption.setText("Nomor Virtual Account");
            tvNoVa.setText(transaksi.getAccountNumber());
        }else {
            tvCaption.setText("Kode pembayaran");
            tvNoVa.setText(transaksi.getPaymentCode());
        }
        textNominal.setText(Utility.toformatRupiah(transaksi.getNominal()));
        textFee.setText(Utility.toformatRupiah(transaksi.getBiaya()));
        tvAmount.setText(Utility.toformatRupiah(transaksi.getTotal()));

        tvSalinNovA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomor = "";
                if(transaksi.getMetodeJenis() == 2){
                    nomor = transaksi.getAccountNumber();
                }else {
                    nomor = transaksi.getPaymentCode();
                }
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Berhasil disalin", nomor);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(PaymentFixedActivity.this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
            }
        });
        tvSalinAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Berhasil disalin", tvAmount.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(PaymentFixedActivity.this, "Berhasil disalin", Toast.LENGTH_SHORT).show();

            }
        });

    }
}