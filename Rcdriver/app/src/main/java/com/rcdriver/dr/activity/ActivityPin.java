package com.rcdriver.dr.activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

import com.rcdriver.dr.R;
import com.rcdriver.dr.json.LoginRequest;
import com.rcdriver.dr.json.LoginResponse;
import com.rcdriver.dr.json.ResponseJson;
import com.rcdriver.dr.json.UpdateLoginRequest;
import com.rcdriver.dr.models.LoginModel;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityPin extends AppCompatActivity {
    List<LoginModel> loginModels;
    EditText edt1, edt2, edt3, edt4;
    String id,email,nomor,password;
    Button Konfirmasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        email = intent.getStringExtra("email");
        nomor = intent.getStringExtra("nomor");
        password = intent.getStringExtra("password");
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        edt4 = findViewById(R.id.edt4);
        Konfirmasi = findViewById(R.id.konfirmasi);
        Konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CekLogin();
            }
        });
        edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(edt1.getText().toString().trim())) {
                    edt1.setBackgroundResource(R.drawable.round_edittext_background);
                    edt1.requestFocus();
                    edt1.setTextColor(ContextCompat.getColor(ActivityPin.this, R.color.black));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt1.setInputType(InputType.TYPE_CLASS_TEXT );
                        }
                    },500);
                } else {
                    edt1.setBackgroundResource(R.drawable.edittextbg);
                    edt1.setTextColor(ContextCompat.getColor(ActivityPin.this, R.color.red));

                    edt2.requestFocus();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt1.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                    },500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(edt2.getText().toString().trim())) {
                    edt2.setBackgroundResource(R.drawable.round_edittext_background);
                    edt2.requestFocus();
                    edt2.setTextColor(ContextCompat.getColor(ActivityPin.this, R.color.black));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt2.setInputType(InputType.TYPE_CLASS_TEXT );
                        }
                    },500);
                } else {
                    edt2.setBackgroundResource(R.drawable.edittextbg);
                    edt3.requestFocus();
                    edt2.setTextColor(ContextCompat.getColor(ActivityPin.this, R.color.red));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt2.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                    },500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(edt3.getText().toString().trim())) {
                    edt3.setBackgroundResource(R.drawable.round_edittext_background);
                    edt3.requestFocus();
                    edt3.setTextColor(ContextCompat.getColor(ActivityPin.this, R.color.black));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt3.setInputType(InputType.TYPE_CLASS_TEXT );
                        }
                    },500);
                } else {
                    edt3.setBackgroundResource(R.drawable.edittextbg);
                    edt3.setTextColor(ContextCompat.getColor(ActivityPin.this, R.color.red));
                    edt4.requestFocus();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt3.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                    },500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(edt4.getText().toString().trim())) {
                    edt4.setBackgroundResource(R.drawable.round_edittext_background);
                    edt4.setTextColor(ContextCompat.getColor(ActivityPin.this, R.color.black));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt4.setInputType(InputType.TYPE_CLASS_TEXT );
                        }
                    },500);
                } else {
                    edt4.setBackgroundResource(R.drawable.edittextbg);
                    edt4.setTextColor(ContextCompat.getColor(ActivityPin.this, R.color.red));
                    edt4.clearFocus();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            edt4.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                    },500);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void CekLogin() {
        try {
            DriverService driverService = ServiceGenerator.createService(
                    DriverService.class, nomor, password);
            LoginRequest param = new LoginRequest();
            param.setId(id);
            driverService.CekLogin(param).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (loginModels != null) {
                            loginModels.clear();
                        }
                        loginModels = response.body().getData();
                        String num1 = edt1.getText().toString();
                        String num2 = edt2.getText().toString();
                        String num3 = edt3.getText().toString();
                        String num4 = edt4.getText().toString();
                        String CekPIN = String.valueOf(loginModels.get(0).getPin());
                        if(CekPIN.equals(num1 + num2 + num3 + num4)){
                            SetLogin(0);
                            Intent intent = new Intent(ActivityPin.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ActivityPin.this, "Pin Yang Anda Masukan Salah.", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("CekPin", String.valueOf(loginModels.get(0).getPin()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Log.e("CekPin Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CekPin Error", e.getMessage());
        }

    }
    private void SetLogin(int login) {
        DriverService service = ServiceGenerator.createService(DriverService.class, email, password);
        UpdateLoginRequest param = new UpdateLoginRequest();
        param.setId(id);
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