package com.rcdriver.mt.activity.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rcdriver.mt.R;
import com.rcdriver.mt.activity.MainActivity;
import com.rcdriver.mt.constants.BaseApp;
import com.rcdriver.mt.constants.Constants;
import com.rcdriver.mt.json.ResponseJson;
import com.rcdriver.mt.json.WithdrawRequestJson;
import com.rcdriver.mt.json.fcm.FCMMessage;
import com.rcdriver.mt.models.Notif;
import com.rcdriver.mt.models.User;
import com.rcdriver.mt.models.payment.PaymentMethod;
import com.rcdriver.mt.utils.SettingPreference;
import com.rcdriver.mt.utils.Utility;
import com.rcdriver.mt.utils.api.FCMHelper;
import com.rcdriver.mt.utils.api.ServiceGenerator;
import com.rcdriver.mt.utils.api.service.MerchantService;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferBankActivity extends AppCompatActivity {
    Context context;
    User user;
    ImageView ivMethod, backButton;
    TextView tvNoVa, tvSalinNovA, tvAmount, tvSalinAmount,
            textNominal, textFee, tvMethod, tvNamaRekening;
    EditText amount, bank, accnumber, nama;
    private RelativeLayout rlprogress;
    Button submit;
    PaymentMethod paymentMethod;
    SettingPreference sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_bank);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        sp = new SettingPreference(this);
        tvMethod = findViewById(R.id.textViewMethod2);
        tvNamaRekening = findViewById(R.id.textViewMethod);
        tvNoVa = findViewById(R.id.textViewNoVa);
        tvSalinNovA = findViewById(R.id.textViewSalinNoVa);
        tvAmount = findViewById(R.id.textViewAmount);
        tvSalinAmount = findViewById(R.id.textViewSalinAmount);
        textNominal = findViewById(R.id.textNominal);
        textFee = findViewById(R.id.textFee);
        rlprogress = findViewById(R.id.rlprogress);
        ivMethod = findViewById(R.id.imageViewMethod);
        backButton = findViewById(R.id.back_btn);
        amount = findViewById(R.id.amount);
        bank = findViewById(R.id.bank);
        nama = findViewById(R.id.namanumber);
        accnumber = findViewById(R.id.accnumber);
        submit = findViewById(R.id.submit);

        String intentdata = getIntent().getStringExtra(Constants.INTENT_CODE);
        String nominal = getIntent().getStringExtra(Constants.INTENT_AMOUNT);
        paymentMethod = new Gson().fromJson(intentdata, PaymentMethod.class);

        tvMethod.setText(paymentMethod.getBank());
        tvNamaRekening.setText(paymentMethod.getNamaRekening());
        tvNoVa.setText(paymentMethod.getNoRekening());

        int total = 0;
        total = Integer.parseInt(nominal) + Integer.parseInt(paymentMethod.getBiaya());
        textNominal.setText(Utility.toformatRupiah(nominal));
        textFee.setText(Utility.toformatRupiah(paymentMethod.getBiaya()));
        tvAmount.setText(Utility.toformatRupiah(String.valueOf(total)));
        amount.setText(total + "");
        amount.setFocusable(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bank.setError(null);
                accnumber.setError(null);
                if (bank.getText().toString().isEmpty()) {
                    bank.setError("Bank tidak boleh kosong!");
                    bank.requestFocus();
                    return;
                } else if (accnumber.getText().toString().isEmpty()) {
                    accnumber.setError("nomor rekening tidak boleh kosong!");
                    accnumber.requestFocus();
                    return;
                }

                postData();

            }
        });
    }

    private void postData() {
        rlprogress.setVisibility(View.VISIBLE);
        final User user = BaseApp.getInstance(this).getLoginUser();
        WithdrawRequestJson request = new WithdrawRequestJson();
        request.setId(user.getId());
        request.setBank(bank.getText().toString());
        request.setName(nama.getText().toString());
        request.setAmount(amount.getText().toString().replace(".", "").replace(sp.getSetting()[0], ""));
        request.setCard(accnumber.getText().toString());
        request.setNotelepon(user.getNoTelepon());
        request.setEmail(user.getEmail());
        request.setType("topup");

        MerchantService service = ServiceGenerator.createService(MerchantService.class, user.getNoTelepon(), user.getPassword());
        service.withdraw(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                rlprogress.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Intent intent = new Intent(TransferBankActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                        Notif notif = new Notif();
                        notif.title = "Isisaldo";
                        notif.message = "Permintaan topup telah berhasil, kami akan mengirimkan pemberitahuan setelah kami mengkonfirmasi";
                        sendNotif(user.getToken_merchant(), notif);

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                rlprogress.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });
    }

    private void sendNotif(final String regIDTujuan, final Notif notif) {

        final FCMMessage message = new FCMMessage();
        message.setTo(regIDTujuan);
        message.setData(notif);

        FCMHelper.sendMessage(Constants.TOKEN, message).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
        });
    }
}