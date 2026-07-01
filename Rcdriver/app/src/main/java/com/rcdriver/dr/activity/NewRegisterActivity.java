package com.rcdriver.dr.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.RegisterRequestJson;
import com.rcdriver.dr.json.RegisterResponseJson;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRegisterActivity extends AppCompatActivity {
    EditText phone, nama,password, email, alamat;
    TextView tanggal, countryCode, sendTo, textnotif, textnotif2, privacypolicy;
    Button submit;
    RelativeLayout rlnotif, rlprogress, rlnotif2;
    Spinner gender;
    String phoneNumber;
    String country_iso_code = "en";
    private SimpleDateFormat dateFormatter, dateFormatterview;
    String[] spinnergender;
    String dateview, disableback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int MainBG = Color.parseColor("#1AC463");
        phone = findViewById(R.id.phonenumber);
        nama = findViewById(R.id.nama);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        tanggal = findViewById(R.id.tanggal);
        alamat = findViewById(R.id.address);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        countryCode = findViewById(R.id.countrycode);
        rlprogress = findViewById(R.id.rlprogress);
        rlnotif2 = findViewById(R.id.rlnotif2);
        textnotif2 = findViewById(R.id.textnotif2);
        gender = findViewById(R.id.gender);
        privacypolicy = findViewById(R.id.privacypolice);

        spinnergender = getResources().getStringArray(R.array.gendertype);
        String priv = getResources().getString(R.string.privacy);
        privacypolicy.setText(Html.fromHtml(priv));

        countryCode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        countryCode.setText(dialCode);
                        picker.dismiss();
                        country_iso_code = code;
                    }
                });
                picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormatterview = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewRegisterActivity.this, PrivacyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        ArrayAdapter<String> genderSpinner = new ArrayAdapter<>(this, R.layout.spinner, spinnergender);
        genderSpinner.setDropDownViewResource(R.layout.spinner);
        gender.setAdapter(genderSpinner);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position == 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTanggal();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String emailvalidate = email.getText().toString();


                if (TextUtils.isEmpty(nama.getText().toString())) {
                    notif("Name cant be empty");
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    notif("Password cant be empty");
                } else if (TextUtils.isEmpty(email.getText().toString())) {
                    notif(getString(R.string.emailempty));
                } else if (TextUtils.isEmpty(tanggal.getText().toString())) {
                    notif("birthday cant be empty!");
                } else if (!emailvalidate.matches(emailPattern)) {
                    notif("wrong email format!");
                } else if (gender.getSelectedItemPosition() == 0) {
                    notif("please select gender!");
                } else if (TextUtils.isEmpty(alamat.getText().toString())) {
                    notif("please enter address!");
                }
                upload();
            }
        });
    }

    private void showTanggal() {

        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        tanggal.setText(dateFormatterview.format(date_ship_millis));
                        dateview = dateFormatter.format(date_ship_millis);
                    }
                }
        );
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorgradient));
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }

    private void upload() {
        progressshow();
        RegisterRequestJson request = new RegisterRequestJson();
        request.setNamadriver(nama.getText().toString());
        request.setPassword(password.getText().toString());
        request.setTglLahir(dateview);
        request.setNoTelepon(countryCode.getText().toString().replace("+", "") + phone.getText().toString());
        request.setPhone(phone.getText().toString());
        request.setEmail(email.getText().toString());
        request.setGender(String.valueOf(gender.getSelectedItem()));
        request.setAlamat(alamat.getText().toString());
        request.setCountrycode(countryCode.getText().toString());
        request.setChecked("false");

        DriverService service = ServiceGenerator.createService(DriverService.class, request.getEmail(), request.getNoTelepon());
        service.signup(request).enqueue(new Callback<RegisterResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponseJson> call, @NonNull Response<RegisterResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        User user = response.body().getUser();
                        Intent intent = new Intent(NewRegisterActivity.this, OTPActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Constants.PREF_NAME, "reg");
                        intent.putExtra(Constants.METHOD_NAME, user.getId());
                        intent.putExtra(Constants.METHOD, user.getNoTelepon());
                        startActivity(intent);

                        finish();
                        Toast.makeText(NewRegisterActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();

                    } else {
                        notif(response.body().getMessage());
                    }
                } else {
                    notif("error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error!");
            }
        });
    }
}