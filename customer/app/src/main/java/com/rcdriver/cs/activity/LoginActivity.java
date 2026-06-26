package com.rcdriver.cs.activity;

import com.rcdriver.cs.utils.LocalStore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.rcdriver.cs.json.SettingResponse;
import com.rcdriver.cs.models.SettingModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.json.LoginGmailRequest;
import com.rcdriver.cs.json.LoginRequest;
import com.rcdriver.cs.json.LoginRequestJson;
import com.rcdriver.cs.json.LoginResponse;
import com.rcdriver.cs.json.LoginResponseJson;
import com.rcdriver.cs.models.FirebaseToken;
import com.rcdriver.cs.models.LoginModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.NetworkUtils;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="GoogleLogin" ;
    NotificationManager notificationManager;
    NotificationCompat.Builder mbuilder;
    private static final String CHANNEL_ID = "Gojasa_Id";
    private static final String CHANNEL_NAME = "Gojasa_Channel";
    List<LoginModel> loginModels;
    EditText phoneText, password, numOne, numTwo, numThree, numFour, numFive, numSix;
    TextView countryCode;
    TextView sendTo;
    TextView textnotif;
    TextView textnotif2;
    TextView lupapass;
    Button confirmButton;
    LinearLayout buttonLogin, daftar;
    ImageView backButton, backButtonverify;
    ViewFlipper viewFlipper;
    String phoneNumber, disableback;
    RelativeLayout rlprogress, rlnotif, rlnotif2, rldaftar;
    String verify;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;
    FirebaseAuth mAuth;
    private ProgressDialog progress;
    boolean value=false;
    LoginButton connectWithFbButton;
    CallbackManager mCallbackManager;
    SharedPreferences sharedPreferences;
    private String devicetoken = null;
    private String cekOtp = "1";
    List<SettingModel> SettingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.setApplicationId(getString(R.string.facebookId));
        FacebookSdk.sdkInitialize(this);
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_masuk);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                devicetoken = instanceIdResult.getToken();
            }
        });
        progress=new ProgressDialog(this);
        progress.setMessage("Loding");
        GetSetting();
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fbAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        // progressbar = findViewById(R.id.progressbar);
        daftar = findViewById(R.id.clickdaftar);
     //   rldaftar = findViewById(R.id.rldaftar);
        lupapass = findViewById(R.id.lupapass);

        phoneText = findViewById(R.id.phonenumber);
        countryCode = findViewById(R.id.countrycode);
        buttonLogin = findViewById(R.id.buttonlogin);
      //  backButton = findViewById(R.id.back_btn);
       // confirmButton = findViewById(R.id.buttonconfirm);
        sendTo = findViewById(R.id.sendtotxt);
        viewFlipper = findViewById(R.id.viewflipper);
      //  backButtonverify = findViewById(R.id.back_btn_verify);
        rlprogress = findViewById(R.id.rlprogress);
        password = findViewById(R.id.password);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        //facebook sdk
        connectWithFbButton= findViewById(R.id.loginbutton);

        mCallbackManager = CallbackManager.Factory.create();
        connectWithFbButton.setReadPermissions("email", "public_profile");
        connectWithFbButton.setClickable(true);
        connectWithFbButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                progress.show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }
            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
        //---------------------------------------------------------------------------
        verify = "false";
        phoneText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                for (int i = 0; i < s.length(); i++) {
                    String cek = s.toString();
                    char c = cek.charAt(0);
                    if(c == '0'){
                        phoneText.setText(cek.substring(1));
                    }else if(cek.trim().startsWith("62")){
                        phoneText.setText(cek.substring(2,s.length()));
                    }
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = countryCode.getText().toString() + phoneText.getText().toString();
                String phonetext = phoneText.getText().toString();
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(phonetext) || TextUtils.isEmpty(pass)) {
                    notif(getString(R.string.phonepass));
                } else if (TextUtils.isEmpty(phonetext)) {
                    notif(getString(R.string.phoneempty));
                } else if (TextUtils.isEmpty(pass)) {
                    notif(getString(R.string.passempty));
                } else {
                    if (NetworkUtils.isConnected(LoginActivity.this)) {
                        progressshow();
                        onSignInClick();
                    } else {
                        progresshide();
                        notif(getString(R.string.text_noInternet));
                    }

                }

            }
        });

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
                    }
                });
                picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
                picker.show(getSupportFragmentManager(), "Select Country");
            }
        });

        lupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, LupapassActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });
        disableback = "false";
    }

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
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

    public void notif2(String text) {
        rlnotif2.setVisibility(View.VISIBLE);
        textnotif2.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif2.setVisibility(View.GONE);
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



    private void onSignInClick() {
        progressshow();
        LoginRequestJson request = new LoginRequestJson();
        request.setNotelepon(countryCode.getText().toString().replace("+", "") + phoneText.getText().toString());
        request.setPassword(password.getText().toString());
        request.setRegId(devicetoken);
        UserService service = ServiceGenerator.createService(UserService.class, request.getNotelepon(), request.getPassword());
        service.login(request).enqueue(new Callback<LoginResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponseJson> call, @NonNull Response<LoginResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                        User user = response.body().getData().get(0);
                        if (verify.equals("false")) {
//                            saveUser(user);
                           if(cekOtp.equalsIgnoreCase("1")){
                               Intent intent = new Intent(LoginActivity.this, VerifyCodeActivity.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                               intent.putExtra(Constants.METHOD_NAME, user.getId());
                               intent.putExtra(Constants.METHOD, user.getNoTelepon());
                               startActivity(intent);
                               finish();
                           }else {
                               saveUser(user);
                               Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(intent);
                               finish();
                           }
                        } else {
//                            saveUser(user);
                            Log.d("SaveUser", "Id : " + user.getId() + " Token : " + user.getToken() + " Nohp : " + user.getNoTelepon() + " Pass : " + user.getPassword());
                            if(cekOtp.equalsIgnoreCase("1")){
                                Intent intent = new Intent(LoginActivity.this, VerifyCodeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(Constants.METHOD_NAME, user.getId());
                                intent.putExtra(Constants.METHOD, user.getNoTelepon());
                                startActivity(intent);
                                finish();
                            }else {
                                saveUser(user);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }

                    } else {
                        notif(getString(R.string.phoneemailwrong));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        connectWithFbButton.setVisibility(View.INVISIBLE);
        if (requestCode == RegisterActivity.SIGNUP_ID) {
            if (resultCode == Activity.RESULT_OK) {
                if (verify.equals("true")) {
                    User user = (User) data.getSerializableExtra(RegisterActivity.USER_KEY);
//                    saveUser(user);
                }

            }
        }
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void saveUser(User user) {
        LocalStore.get().saveUser(user);
        BaseApp.getInstance(LoginActivity.this).setLoginUser(user);
       // SetLogin(1);
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FirebaseToken response) {
        LocalStore.get().saveToken(response);
    }
    private void CekLogin(User user) {
        try {
            UserService userService = ServiceGenerator.createService(
                    UserService.class, user.getNoTelepon(), user.getPassword());
            LoginRequest param = new LoginRequest();
            param.setId(user.getId());
            userService.CekLogin(param).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (loginModels != null) {
                            loginModels.clear();
                        }
                        loginModels = response.body().getData();
                        if(loginModels.get(0).getIslogin() == 1){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotifikasiHandle("Akun Kamu Sedang Aktif.\nKeluar Terlebih Dahulu Di Perangkat Yang Sedang Aktif.");
                            }
                            notif("Akun Kamu Sedang Aktif.\nKeluar Terlebih Dahulu Di Perangkat Yang Sedang Aktif.");
                            phoneText.setText("");
                            password.setText("");
                        }else{
                            saveUser(user);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        com.rcdriver.cs.utils.Log.d("CekLogin", "Alamat: " + loginModels.get(0).getIslogin());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    com.rcdriver.cs.utils.Log.e("CekLogin Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            com.rcdriver.cs.utils.Log.e("mLokasi Error", e.getMessage());
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void NotifikasiHandle(String Pesan){
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.notifpoint);
        mbuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(Pesan)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notif))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Pesan))
                .setAutoCancel(true)
                .setSound(soundUri);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        mbuilder.setChannelId(CHANNEL_ID);
        Objects.requireNonNull(notificationManager).notify(0, mbuilder.build());
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        progressshow();
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getAccount());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            value=true;
                            String email = task.getResult().getUser().getEmail();
                            String nohp = task.getResult().getUser().getPhoneNumber();
                            LoginGmailRequest request = new LoginGmailRequest();
                            request.setEmail(email);
                            request.setRegId(devicetoken);
                            UserService service = ServiceGenerator.createService(UserService.class, "admin", "1234");
                            service.logingmail(request).enqueue(new Callback<LoginResponseJson>() {
                                @Override
                                public void onResponse(@NonNull Call<LoginResponseJson> call, @NonNull Response<LoginResponseJson> response) {
                                    if (response.isSuccessful()) {
                                        progresshide();
                                        Log.d("GoogleLogin", response.body().getMessage());
                                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                                            for (int i = 0; i < response.body().getData().size(); i++) {
                                                User user = response.body().getData().get(i);
                                                saveUser(user);
                                                Log.d("SaveUser", "Id : " + user.getId() + " Token : " + user.getToken() + " Nohp : " + user.getNoTelepon() + " Pass : " + user.getPassword());
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            progresshide();
                                            notif(getString(R.string.phoneemailwrong));
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<LoginResponseJson> call, @NonNull Throwable t) {
                                    progresshide();
                                    t.printStackTrace();
                                    Log.e("GoogleLogin", t.getMessage());
                                    notif("error");
                                }
                            });
                            Log.d("GoogleLogin", "Email : " + email + " Hp : " + nohp);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
    //---------------------------------- Facebook Sign --------------------------------------
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user!=null) {
                                String email = user.getEmail();
                                String nohp = user.getPhoneNumber();
                                LoginGmailRequest request = new LoginGmailRequest();
                                request.setEmail(email);
                                request.setRegId(devicetoken);
                                UserService service = ServiceGenerator.createService(UserService.class, "admin", "1234");
                                service.logingmail(request).enqueue(new Callback<LoginResponseJson>() {
                                    @Override
                                    public void onResponse(@NonNull Call<LoginResponseJson> call, @NonNull Response<LoginResponseJson> response) {
                                        if (response.isSuccessful()) {
                                            progresshide();
                                            Log.d("FacebookLogin", response.body().getMessage());
                                            if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                                                for (int i = 0; i < response.body().getData().size(); i++) {
                                                    User user = response.body().getData().get(i);
                                                    saveUser(user);
                                                    Log.d("SaveUser", "Id : " + user.getId() + " Token : " + user.getToken() + " Nohp : " + user.getNoTelepon() + " Pass : " + user.getPassword());
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } else {
                                                progresshide();
                                                notif(getString(R.string.phoneemailwrong));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<LoginResponseJson> call, @NonNull Throwable t) {
                                        progresshide();
                                        t.printStackTrace();
                                        Log.e("FacebookLogin", t.getMessage());
                                        notif("error");
                                    }
                                });
                                Log.d("FacebookLogin", "Email : " + email + " Hp : " + nohp);
                            }
                        }
                    }
        });
    }

    private void GetSetting() {
        try {
            final User user = BaseApp.getInstance(this).getLoginUser();
            UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
            service.setting().enqueue(new Callback<SettingResponse>() {
                @Override
                public void onResponse(@NonNull Call<SettingResponse> call, @NonNull Response<SettingResponse> response) {
                    if (response.isSuccessful()) {
                        com.rcdriver.cs.utils.Log.d("AppSetting", response.body().getMessage());
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                            SettingList = response.body().getData();
                            cekOtp = SettingList.get(0).getIsotp();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SettingResponse> call, @NonNull Throwable t) {
                    com.rcdriver.cs.utils.Log.d("AppSetting", t.getMessage());
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
