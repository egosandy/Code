package com.rcdriver.mt.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rcdriver.mt.R;
import com.rcdriver.mt.constants.BaseApp;
import com.rcdriver.mt.constants.Constants;
import com.rcdriver.mt.json.GetOtpResponse;
import com.rcdriver.mt.json.VerifyCodeRequest;
import com.rcdriver.mt.json.VerifyCodeResponse;
import com.rcdriver.mt.models.User;
import com.rcdriver.mt.utils.api.ServiceGenerator;
import com.rcdriver.mt.utils.api.service.MerchantService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCodeActivity extends AppCompatActivity {
    private Context context;
    private ImageView backButton;
    private EditText kodeOtp;
    private Button submit;
    private TextView resend, timerText, numberText;
    private static final String FORMAT = "%02d:%02d";
    private String id, number;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        context = this;
        backButton = findViewById(R.id.back_btn_verify3);
        kodeOtp = findViewById(R.id.numone);
        submit = findViewById(R.id.buttonconfirm);
        resend = findViewById(R.id.resend);
        numberText = findViewById(R.id.sendtotxt);
        timerText = findViewById(R.id.text_timer);
        progressBar = findViewById(R.id.progressbar2);

        id = getIntent().getStringExtra(Constants.METHOD_NAME);
        number = getIntent().getStringExtra(Constants.METHOD);

        numberText.setText("Masukkan kode otp yang sudah dikirim melalui whatsapp ke nomor +" + number);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        request(id, number);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kodeOtp.getText().toString().isEmpty()){
                    kodeOtp.setError("Kode otp harus diisi");
                    kodeOtp.requestFocus();
                    return;
                }else if(kodeOtp.getText().length() < 6){
                    kodeOtp.setError("Kode otp minimal 6 character");
                    kodeOtp.requestFocus();
                    return;
                }

                verify(kodeOtp.getText().toString(), id, number);
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(id, number);
            }
        });

    }

    private void startCountDown(){
        new CountDownTimer(180000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resend.setVisibility(View.GONE);
                timerText.setVisibility(View.VISIBLE);
                timerText.setText(context.getString(R.string.waiting_otp) + " " + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                timerText.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void request(String idUser, String phoneNumber){
        startCountDown();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("id", idUser);
        headers.put("nomor", phoneNumber);
        MerchantService service = ServiceGenerator.createService(MerchantService.class, idUser, phoneNumber);
        service.getOtp(headers).enqueue(new Callback<GetOtpResponse>() {
            @Override
            public void onResponse(Call<GetOtpResponse> call, Response<GetOtpResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, "Error connection to server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void verify(String code, String idUser, String phoneNumber){
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("id", idUser);
        headers.put("nomor", phoneNumber);
        VerifyCodeRequest requestJson = new VerifyCodeRequest();
        requestJson.setOtp(code);
        MerchantService service = ServiceGenerator.createService(MerchantService.class, idUser, phoneNumber);
        service.verifyOtp(headers, requestJson).enqueue(new Callback<VerifyCodeResponse>() {
            @Override
            public void onResponse(Call<VerifyCodeResponse> call, Response<VerifyCodeResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getStatus().equalsIgnoreCase("00")){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if(getIntent().getStringExtra(Constants.PREF_NAME).equalsIgnoreCase("log")){
                            User user = response.body().getData().get(0);
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            saveUser(user);
                            finish();
                        }else{
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Toast.makeText(context, "Pendaftaran Berhasil, Mohon Tunggu Untuk Dikonfirmasi!", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }else{
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(context, "Error connection to server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyCodeResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(VerifyCodeActivity.this).setLoginUser(user);
        // SetLogin(1);
    }
}