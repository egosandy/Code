package com.rcdriver.dr.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.GetOtpResponse;
import com.rcdriver.dr.json.ResponseJson;
import com.rcdriver.dr.json.UpdateLoginRequest;
import com.rcdriver.dr.json.VerifyCodeRequest;
import com.rcdriver.dr.json.VerifyCodeResponse;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {
    private TextView textNumber, textTimer, resend;
    private LinearLayout lresend;
    private EditText mEt1, mEt2, mEt3, mEt4, mEt5, mEt6;
    private Context mContext;
    private Button submit;
    private static final String FORMAT = "%02d:%02d";
    private String id, number;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initialize();

        addTextWatcher(mEt1);
        addTextWatcher(mEt2);
        addTextWatcher(mEt3);
        addTextWatcher(mEt4);
        addTextWatcher(mEt5);
        addTextWatcher(mEt6);

        id = getIntent().getStringExtra(Constants.METHOD_NAME);
        number = getIntent().getStringExtra(Constants.METHOD);

        User intentUser = null;
        Object userExtra = getIntent().getSerializableExtra("user");
        if (userExtra instanceof User) {
            intentUser = (User) userExtra;
        }
        if (isEmpty(id) && intentUser != null) {
            id = safeText(intentUser.getId());
        }
        if (isEmpty(number) && intentUser != null) {
            number = safeText(intentUser.getNoTelepon());
        }
        if (isEmpty(number)) {
            number = safeText(getIntent().getStringExtra("phoneNumber")).replace("+", "");
        }

        if (isEmpty(id) || isEmpty(number)) {
            Toast.makeText(mContext, "Data OTP tidak lengkap. Silakan login ulang.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        textNumber.setText("Sent to +" + number);
        request(id, number);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codes = "" + mEt1.getText().toString() + mEt2.getText().toString() + mEt3.getText().toString() + mEt4.getText().toString() + mEt5.getText().toString() + mEt6.getText().toString();

                if(codes.length() < 6){
                    Toast.makeText(mContext, "Kode OTP harus 6 digit", Toast.LENGTH_LONG).show();
                }else {
                    verify(codes, id, number);
                }

            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(id, number);
            }
        });
    }

    private void initialize() {
        mEt1 = findViewById(R.id.otp_edit_text1);
        mEt2 = findViewById(R.id.otp_edit_text2);
        mEt3 = findViewById(R.id.otp_edit_text3);
        mEt4 = findViewById(R.id.otp_edit_text4);
        mEt5 = findViewById(R.id.otp_edit_text5);
        mEt6 = findViewById(R.id.otp_edit_text6);
        mContext = OTPActivity.this;
        textNumber = findViewById(R.id.textView2);
        textTimer = findViewById(R.id.text_timer);
        resend = findViewById(R.id.resend);
        lresend = findViewById(R.id.lresend);
        submit = findViewById(R.id.btn_verify);
        progressBar = findViewById(R.id.progressBar2);

    }

    private void addTextWatcher(final EditText one) {
        one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (one.getId()) {
                    case R.id.otp_edit_text1:
                        if (one.length() == 1) {
                            mEt2.requestFocus();
                        }
                        break;
                    case R.id.otp_edit_text2:
                        if (one.length() == 1) {
                            mEt3.requestFocus();
                        } else if (one.length() == 0) {
                            mEt1.requestFocus();
                        }
                        break;
                    case R.id.otp_edit_text3:
                        if (one.length() == 1) {
                            mEt4.requestFocus();
                        } else if (one.length() == 0) {
                            mEt2.requestFocus();
                        }
                        break;
                    case R.id.otp_edit_text4:
                        if (one.length() == 1) {
                            mEt5.requestFocus();
                        } else if (one.length() == 0) {
                            mEt3.requestFocus();
                        }
                        break;
                    case R.id.otp_edit_text5:
                        if (one.length() == 1) {
                            mEt6.requestFocus();
                        } else if (one.length() == 0) {
                            mEt4.requestFocus();
                        }
                        break;
                    case R.id.otp_edit_text6:
                        if (one.length() == 1) {
                            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                            View currentFocus = OTPActivity.this.getCurrentFocus();
                            if (inputManager != null && currentFocus != null) {
                                inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        } else if (one.length() == 0) {
                            mEt5.requestFocus();
                        }
                        break;
                }
            }
        });
    }

    private void startCountDown(){
        new CountDownTimer(180000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                lresend.setVisibility(View.GONE);
                textTimer.setVisibility(View.VISIBLE);
                textTimer.setText("Permintaan kode baru dalam " + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                textTimer.setVisibility(View.GONE);
                lresend.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    private void request(String idUser, String phoneNumber){
        startCountDown();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("id", idUser);
        headers.put("nomor", phoneNumber);
        DriverService service = ServiceGenerator.createService(DriverService.class, idUser, phoneNumber);
        service.getOtp(headers).enqueue(new Callback<GetOtpResponse>() {
            @Override
            public void onResponse(Call<GetOtpResponse> call, Response<GetOtpResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(mContext, safeText(response.body().getMessage()), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, "Error connection to server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
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
        DriverService service = ServiceGenerator.createService(DriverService.class, idUser, phoneNumber);
        service.verifyOtp(headers, requestJson).enqueue(new Callback<VerifyCodeResponse>() {
            @Override
            public void onResponse(Call<VerifyCodeResponse> call, Response<VerifyCodeResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null){
                    VerifyCodeResponse body = response.body();
                    if("00".equalsIgnoreCase(safeText(body.getStatus()))){
                        Toast.makeText(mContext, safeText(body.getMessage()), Toast.LENGTH_LONG).show();
                        String otpFlow = safeText(getIntent().getStringExtra(Constants.PREF_NAME));
                        if("log".equalsIgnoreCase(otpFlow)){
                            User user = getFirstUser(body.getData());
                            if (user == null) {
                                Toast.makeText(mContext, "OTP benar, tetapi data user kosong dari server.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            saveUser(user);
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("phoneNumber", phoneNumber);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();

                        }else {
                            Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }


                    }else{
                        Toast.makeText(mContext, safeText(body.getMessage()), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(mContext, "Error connection to server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyCodeResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private User getFirstUser(List<User> users) {
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    private void saveUser(User user) {
        if (user == null) {
            Toast.makeText(mContext, "Data user kosong, login dibatalkan.", Toast.LENGTH_LONG).show();
            return;
        }
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(User.class);
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
            BaseApp.getInstance(OTPActivity.this).setLoginUser(user);
            SetLogin(1);
        } catch (Exception e) {
            if (realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            Log.e("OTP_SAVE_USER", "Gagal menyimpan user", e);
            Toast.makeText(mContext, "Login gagal menyimpan data akun.", Toast.LENGTH_LONG).show();
        } finally {
            realm.close();
        }
    }

    private void SetLogin(int login) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) {
            Log.e("UpdateLogin", "Login user kosong, update login dilewati");
            return;
        }
        DriverService service = ServiceGenerator.createService(DriverService.class, safeText(loginUser.getEmail()), safeText(loginUser.getPassword()));
        UpdateLoginRequest param = new UpdateLoginRequest();
        param.setId(loginUser.getId());
        param.setIslogin(login);
        service.updatelogin(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.e("UpdateLogin", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<ResponseJson> call, @NonNull Throwable t) {
                Log.e("UpdateLogin", t.getMessage());
            }
        });

    }
}