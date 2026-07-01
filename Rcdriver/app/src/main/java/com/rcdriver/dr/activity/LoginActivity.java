package com.rcdriver.dr.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.LoginRequestJson;
import com.rcdriver.dr.json.LoginResponseJson;
import com.rcdriver.dr.json.ResponseJson;
import com.rcdriver.dr.json.SettingResponse;
import com.rcdriver.dr.json.UpdateLoginRequest;
import com.rcdriver.dr.models.FirebaseToken;
import com.rcdriver.dr.models.LoginModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.NetworkUtils;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ybs.countrypicker.CountryPicker;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    NotificationManager notificationManager;
    NotificationCompat.Builder mbuilder;
    private static final String CHANNEL_ID = "Gojasa_Id";
    private static final String CHANNEL_NAME = "Gojasa_Channel";
    List<LoginModel> loginModels;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    EditText phoneText, password, numOne, numTwo, numThree, numFour, numFive, numSix;
    TextView countryCode, sendTo, privacypolicy, textnotif, daftar, textnotif2, lupapass;
    Button confirmButton;
    ImageView backButton, backButtonverify;
    ViewFlipper viewFlipper;
    String phoneNumber, disableback;
    FirebaseAuth mAuth;
    RelativeLayout rlprogress, buttonLogin, rlnotif, rlnotif2, rldaftar;
    String verify;
    SharedPreferences sharedPreferences;
    String country_iso_code = "en";
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    private int cekOtp = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fbAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        numOne = findViewById(R.id.numone);
        numTwo = findViewById(R.id.numtwo);
        numThree = findViewById(R.id.numthree);
        numFour = findViewById(R.id.numfour);
        numFive = findViewById(R.id.numfive);
        numSix = findViewById(R.id.numsix);
        privacypolicy = findViewById(R.id.privacypolice);
        daftar = findViewById(R.id.clickdaftar);
        rldaftar = findViewById(R.id.rldaftar);
        lupapass = findViewById(R.id.lupapass);

        phoneText = findViewById(R.id.phonenumber);
        countryCode = findViewById(R.id.countrycode);
        buttonLogin = findViewById(R.id.buttonlogin);
        backButton = findViewById(R.id.back_btn);
        confirmButton = findViewById(R.id.buttonconfirm);
        sendTo = findViewById(R.id.sendtotxt);
        viewFlipper = findViewById(R.id.viewflipper);
        backButtonverify = findViewById(R.id.back_btn_verify);
        rlprogress = findViewById(R.id.rlprogress);
        password = findViewById(R.id.password);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        rlnotif2 = findViewById(R.id.rlnotif2);
        textnotif2 = findViewById(R.id.textnotif2);

        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

        String priv = getResources().getString(R.string.privacy);
        privacypolicy.setText(Html.fromHtml(priv));

        getSetting();

        verify = "false";
        buttonLogin.setOnClickListener(v -> {
            phoneNumber = countryCode.getText().toString() + phoneText.getText().toString();
            String phonetext = phoneText.getText().toString();
            String pass = password.getText().toString();
            if (TextUtils.isEmpty(phonetext) || TextUtils.isEmpty(pass)) {
                notif(getString(R.string.phonepass));
            } else {
                if (NetworkUtils.isConnected(LoginActivity.this)) {
                    onSignInClick();
                } else {
                    notif(getString(R.string.text_noInternet));
                }
            }
        });

        countryCode.setOnClickListener(v -> {
            final CountryPicker picker = CountryPicker.newInstance("Select Country");
            picker.setListener((name, code, dialCode, flagDrawableResID) -> {
                countryCode.setText(dialCode);
                picker.dismiss();
                country_iso_code = code;
            });
            picker.setStyle(DialogFragment.STYLE_NORMAL, R.style.countrypicker_style);
            picker.show(getSupportFragmentManager(), "Select Country");
        });

        lupapass.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, LupapassActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        daftar.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, NewRegisterActivity.class);
            startActivity(i);
        });

        privacypolicy.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, PrivacyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        backButton.setOnClickListener(v -> finish());
        backButtonverify.setOnClickListener(v -> finish());
        confirmButton.setOnClickListener(this::verifyCode);
        disableback = "false";
        codenumber();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!"true".equals(disableback)) {
            finish();
        }
    }

    private void onSignInClick() {
        progressshow();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String token = task.getResult();
                proceedToLogin(token);
            } else {
                Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                proceedToLogin("");
            }
        });
    }

    private void proceedToLogin(String fcmToken) {
        LoginRequestJson request = new LoginRequestJson();
        request.setNotelepon(countryCode.getText().toString().replace("+", "") + phoneText.getText().toString());
        request.setPassword(password.getText().toString());
        request.setRegId(fcmToken);

        DriverService service = ServiceGenerator.createService(DriverService.class, request.getNotelepon(), request.getPassword());
        service.login(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseJson> call, @NonNull Response<LoginResponseJson> response) {
                progresshide();
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponseJson body = response.body();
                    String message = body.getMessage();
                    if ("found".equalsIgnoreCase(message)) {
                        User user = getFirstUser(body);
                        handleLoginSuccess(user);
                    } else if ("pending".equalsIgnoreCase(message)) {
                        User user = getFirstUser(body);
                        goToRegistration(user);
                    } else {
                        notif(getSafeLoginMessage(body));
                    }
                } else {
                    notif("Login gagal. Silakan periksa nomor, password, dan koneksi server.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("Error: " + t.getMessage());
            }
        });
    }

    private User getFirstUser(LoginResponseJson body) {
        if (body == null || body.getData() == null || body.getData().isEmpty()) {
            return null;
        }
        return body.getData().get(0);
    }

    private String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private String getSafeLoginMessage(LoginResponseJson body) {
        String message = body == null ? null : body.getMessage();
        if (message == null || message.trim().isEmpty()) {
            return getString(R.string.phoneemailwrong);
        }
        return message;
    }

    private void handleLoginSuccess(User user) {
        if (user == null) {
            notif("Login berhasil, tetapi data akun driver kosong dari server. Silakan cek API driver/loginew.");
            return;
        }

        String code = countryCode.getText().toString().trim();
        String number = phoneText.getText().toString().trim();
        if (number.isEmpty() || number.length() < 9) {
            phoneText.setError("Valid number is required");
            phoneText.requestFocus();
            return;
        }
        String completePhoneNumber = code + number;

        if (cekOtp > 0 && !"true".equals(verify)) {
            String otpPhone = safeText(user.getNoTelepon());
            if (otpPhone.isEmpty()) {
                otpPhone = completePhoneNumber.replace("+", "");
            }

            Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
            intent.putExtra(Constants.PREF_NAME, "log");
            intent.putExtra(Constants.METHOD_NAME, safeText(user.getId()));
            intent.putExtra(Constants.METHOD, otpPhone);
            intent.putExtra("phoneNumber", completePhoneNumber);
            intent.putExtra("user", user);
            startActivityAndFinish(intent);
        } else {
            saveUser(user);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivityAndFinish(intent);
        }
    }

    private void goToRegistration(User user) {
        if (user == null) {
            notif("Akun pending, tetapi data driver kosong dari server. Silakan cek API driver/loginew.");
            return;
        }
        Intent intent = new Intent(LoginActivity.this, RegKendaraanActivity.class);
        intent.putExtra("user", user);
        startActivityAndFinish(intent);
    }

    private void startActivityAndFinish(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);
        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    public void notif2(String text) {
        rlnotif2.setVisibility(View.VISIBLE);
        textnotif2.setText(text);
        new Handler().postDelayed(() -> rlnotif2.setVisibility(View.GONE), 3000);
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }

    public void codenumber() {
        numOne.addTextChangedListener(new GenericTextWatcher(numOne, numTwo));
        numTwo.addTextChangedListener(new GenericTextWatcher(numTwo, numThree));
        numThree.addTextChangedListener(new GenericTextWatcher(numThree, numFour));
        numFour.addTextChangedListener(new GenericTextWatcher(numFour, numFive));
        numFive.addTextChangedListener(new GenericTextWatcher(numFive, numSix));
        numSix.addTextChangedListener(new GenericTextWatcher(numSix, null));
    }

    public void Nextbtn(View view) {
        phoneNumber = countryCode.getText().toString() + phoneText.getText().toString();
        String ccode = countryCode.getText().toString();

        if ((!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(ccode)) && phoneNumber.length() > 5) {
            progressshow();
            Send_Number_tofirebase(phoneNumber);
        } else {
            notif(getString(R.string.wrongnumber));
        }
    }

    public void Send_Number_tofirebase(String phoneNumber) {
        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                120,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progresshide();
                Log.d("respon", e.toString());
                notif2("Verifikasi Gagal!");
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    notif2("Wrong code!");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    notif2("Too Many Requests!");
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationId = verificationId;
                resendToken = token;
                sendTo.setText("Send To ( " + phoneNumber + " )");
                progresshide();
                viewFlipper.setInAnimation(LoginActivity.this, R.anim.from_right);
                viewFlipper.setOutAnimation(LoginActivity.this, R.anim.to_left);
                viewFlipper.setDisplayedChild(1);
            }
        };
    }

    public void verifyCode(View view) {
        backButton.setVisibility(View.GONE);
        rldaftar.setVisibility(View.GONE);
        String code = numOne.getText().toString() + numTwo.getText().toString() + numThree.getText().toString() + numFour.getText().toString() + numFive.getText().toString() + numSix.getText().toString();
        if (!code.isEmpty()) {
            progressshow();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else {
            notif2("Kode verifikasi masih kosong");
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        verify = "true";
                        onSignInClick();
                    } else {
                        progresshide();
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            notif2("Wrong code!");
                        }
                    }
                });
    }

    public void resendCode(View view) {
        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                120,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegisterActivity.SIGNUP_ID && resultCode == Activity.RESULT_OK) {
            if ("true".equals(verify)) {
                User user = (User) data.getSerializableExtra(RegisterActivity.USER_KEY);
                saveUser(user);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityAndFinish(intent);
            }
        }
    }

    private void saveUser(User user) {
        if (user == null) {
            notif("Data user kosong, login dibatalkan.");
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(User.class);
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
            BaseApp.getInstance(LoginActivity.this).setLoginUser(user);
            SetLogin(1);
        } catch (Exception e) {
            if (realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            Log.e("LOGIN_SAVE_USER", "Gagal menyimpan user", e);
            notif("Login gagal menyimpan data akun.");
        } finally {
            realm.close();
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirebaseToken response) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(FirebaseToken.class);
        realm.copyToRealm(response);
        realm.commitTransaction();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void NotifikasiHandle(String Pesan) {
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.notifpoint);
        mbuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(Pesan)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notif))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Pesan))
                .setAutoCancel(true)
                .setSound(soundUri);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(0, mbuilder.build());
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
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Log.e("UpdateLogin", t.getMessage());
            }
        });
    }

    private void getSetting() {
        DriverService service = ServiceGenerator.createService(DriverService.class, "admin", "12345");
        service.setting().enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(@NonNull Call<SettingResponse> call, @NonNull Response<SettingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ("found".equalsIgnoreCase(response.body().getMessage())
                            && response.body().getData() != null
                            && !response.body().getData().isEmpty()) {
                        cekOtp = response.body().getData().get(0).getIsOtp();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<SettingResponse> call, @NonNull Throwable t) {
                Log.e("GET_SETTING", "Failed to get settings", t);
            }
        });
    }

    // --- KELAS HELPER YANG HILANG DITAMBAHKAN DI SINI ---
    public static class GenericTextWatcher implements TextWatcher {
        private final View currentView;
        private final View nextView;

        public GenericTextWatcher(View currentView, View nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (text.length() == 1 && nextView != null) {
                nextView.requestFocus();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Tidak perlu implementasi
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Tidak perlu implementasi
        }
    }
}