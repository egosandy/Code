package com.rcdriver.dr.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.JobResponseJson;
import com.rcdriver.dr.json.RegisterRequestJson;
import com.rcdriver.dr.json.RegisterResponseJson;
import com.rcdriver.dr.models.JobModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
// HAPUS IMPORT LAMA
// import com.google.firebase.iid.FirebaseInstanceId;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.ybs.countrypicker.CountryPicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    public static final int SIGNUP_ID = 110;
    public static final String USER_KEY = "UserKey";
    ImageView foto, gantifoto, backbtn, backButtonverify, fotosim, fotoktp;
    EditText phone, nama, password, email, numOne, numTwo, numThree, numFour, numFive, numSix, alamat, brand, type, vehiclenumber, color, idcardtext, driverlicensetext;
    TextView tanggal, countryCode, sendTo, textnotif, textnotif2, privacypolicy;
    Button submit, confirmButton;
    RelativeLayout rlnotif, rlprogress, rlnotif2;
    Spinner gender, job;
    String phoneNumber;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    byte[] imageByteArray, imageByteArrayktp, imageByteArraysim;
    Bitmap decoded, decodedktp, decodedsim;
    String dateview, disableback;
    String[] spinnergender;
    String[] spinnerjob;
    ViewFlipper viewFlipper;
    String country_iso_code = "en";
    String verify;
    // VARIABEL TOKEN DIHAPUS KARENA TIDAK DIGUNAKAN
    // String token;
    List<JobModel> joblist;
    ArrayList<JobModel> fiturlist;
    ArrayList<String> jobdata;
    private SimpleDateFormat dateFormatter, dateFormatterview;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fiturlist = new ArrayList<>();
        jobdata = new ArrayList<>();
        fbAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        foto = findViewById(R.id.foto);
        gantifoto = findViewById(R.id.editfoto);
        backbtn = findViewById(R.id.back_btn);
        phone = findViewById(R.id.phonenumber);
        nama = findViewById(R.id.nama);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        tanggal = findViewById(R.id.tanggal);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        countryCode = findViewById(R.id.countrycode);
        viewFlipper = findViewById(R.id.viewflipper);
        backButtonverify = findViewById(R.id.back_btn_verify);
        rlprogress = findViewById(R.id.rlprogress);
        rlnotif2 = findViewById(R.id.rlnotif2);
        textnotif2 = findViewById(R.id.textnotif2);
        confirmButton = findViewById(R.id.buttonconfirm);

        // BARIS INI DIHAPUS KARENA MENYEBABKAN ERROR DAN TIDAK DIGUNAKAN
        // token = FirebaseInstanceId.getInstance().getToken();

        numOne = findViewById(R.id.numone);
        numTwo = findViewById(R.id.numtwo);
        numThree = findViewById(R.id.numthree);
        numFour = findViewById(R.id.numfour);
        numFive = findViewById(R.id.numfive);
        numSix = findViewById(R.id.numsix);
        sendTo = findViewById(R.id.sendtotxt);
        privacypolicy = findViewById(R.id.privacypolice);
        gender = findViewById(R.id.gender);
        alamat = findViewById(R.id.address);
        brand = findViewById(R.id.merek);
        type = findViewById(R.id.tipe);
        vehiclenumber = findViewById(R.id.nomorkendaraan);
        color = findViewById(R.id.warna);
        idcardtext = findViewById(R.id.noktp);
        driverlicensetext = findViewById(R.id.sim);
        fotosim = findViewById(R.id.fotosim);
        fotoktp = findViewById(R.id.fotoktp);

        spinnergender = getResources().getStringArray(R.array.gendertype);
        job = findViewById(R.id.job);

        gantifoto.setOnClickListener(v -> selectImage());
        fotosim.setOnClickListener(v -> selectImagesim());
        fotoktp.setOnClickListener(v -> selectImagektp());

        String priv = getResources().getString(R.string.privacy);
        privacypolicy.setText(Html.fromHtml(priv));

        countryCode.setOnClickListener(v -> {
            final CountryPicker picker = CountryPicker.newInstance("Select Country");
            picker.setListener((name, code, dialCode, flagDrawableResID) -> {
                countryCode.setText(dialCode);
                picker.dismiss();
                country_iso_code = code;
            });
            picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
            picker.show(getSupportFragmentManager(), "Select Country");
        });

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormatterview = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        privacypolicy.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, PrivacyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        ArrayAdapter<String> genderSpinner = new ArrayAdapter<>(this, R.layout.spinner, spinnergender);
        genderSpinner.setDropDownViewResource(R.layout.spinner);
        gender.setAdapter(genderSpinner);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(view != null) {
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(getResources().getColor(R.color.gray));
                        tv.setTextSize(14);
                    } else {
                        tv.setTextColor(getResources().getColor(R.color.black));
                        tv.setTextSize(14);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        submit.setOnClickListener(v -> {
            final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            final String emailvalidate = email.getText().toString();

            if (imageByteArray == null) notif("Please add photo!");
            else if (TextUtils.isEmpty(phone.getText().toString())) notif(getString(R.string.phoneempty));
            else if (TextUtils.isEmpty(nama.getText().toString())) notif("Name can't be empty");
            else if (TextUtils.isEmpty(password.getText().toString())) notif("Password can't be empty");
            else if (TextUtils.isEmpty(email.getText().toString())) notif(getString(R.string.emailempty));
            else if (TextUtils.isEmpty(tanggal.getText().toString())) notif("Birthday can't be empty!");
            else if (!emailvalidate.matches(emailPattern)) notif("Wrong email format!");
            else if (gender.getSelectedItemPosition() == 0) notif("Please select gender!");
            else if (job.getSelectedItemPosition() == 0) notif("Please select job!");
            else if (TextUtils.isEmpty(alamat.getText().toString())) notif("Please enter address!");
            else if (TextUtils.isEmpty(brand.getText().toString())) notif("Please enter vehicle brand!");
            else if (TextUtils.isEmpty(type.getText().toString())) notif("Please enter vehicle type!");
            else if (TextUtils.isEmpty(vehiclenumber.getText().toString())) notif("Please enter vehicle number!");
            else if (TextUtils.isEmpty(color.getText().toString())) notif("Please enter vehicle color!");
            else if (TextUtils.isEmpty(idcardtext.getText().toString())) notif("Please enter ID Card number!");
            else if (TextUtils.isEmpty(driverlicensetext.getText().toString())) notif("Please enter Driver License!");
            else if (imageByteArrayktp == null) notif("Please upload Image ID Card!");
            else if (imageByteArraysim == null) notif("Please upload Image SIM Card!");
            else upload("false");
        });

        confirmButton.setOnClickListener(this::verifyCode);
        backButtonverify.setOnClickListener(v -> finish());
        tanggal.setOnClickListener(v -> showTanggal());
        disableback = "false";
        codenumber();
        verify = "false";
        get();
    }

    private void get() {
        JobModel jobs = new JobModel();
        jobs.setId(0);
        jobs.setJob("Select Job");
        fiturlist.add(jobs);
        jobdata.add("Select Job");
        DriverService service = ServiceGenerator.createService(DriverService.class, "admin", "12345");
        service.job().enqueue(new Callback<JobResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<JobResponseJson> call, @NonNull Response<JobResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equalsIgnoreCase("found")) {
                        joblist = response.body().getData();
                        for (int i = 0; i < joblist.size(); i++) {
                            jobdata.add(joblist.get(i).getJob());
                        }
                        fiturlist.addAll(joblist);

                        ArrayAdapter<String> jobSpinner = new ArrayAdapter<>(RegisterActivity.this, R.layout.spinner, jobdata);
                        jobSpinner.setDropDownViewResource(R.layout.spinner);
                        job.setAdapter(jobSpinner);
                        job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(view != null) {
                                    TextView tv = (TextView) view;
                                    if (position == 0) {
                                        tv.setTextColor(getResources().getColor(R.color.gray));
                                        tv.setTextSize(14);
                                    } else {
                                        tv.setTextColor(getResources().getColor(R.color.black));
                                        tv.setTextSize(14);
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JobResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showTanggal() {
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    long date_ship_millis = calendar.getTimeInMillis();
                    tanggal.setText(dateFormatterview.format(date_ship_millis));
                    dateview = dateFormatter.format(date_ship_millis);
                }
        );
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorgradient));
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }

    public void progressshow() {
        rlprogress.setVisibility(View.VISIBLE);
        disableback = "true";
    }

    public void progresshide() {
        rlprogress.setVisibility(View.GONE);
        disableback = "false";
    }

    @Override
    public void onBackPressed() {
        if (!"true".equals(disableback)) {
            finish();
        }
    }

    public void Nextbtn(View view) {
        phoneNumber = countryCode.getText().toString() + phone.getText().toString();
        String ccode = countryCode.getText().toString();

        if ((!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(ccode)) && phoneNumber.length() > 5) {
            progressshow();
            Send_Number_tofirebase(phoneNumber);
        } else {
            notif("Please enter phone correctly");
        }
    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);
        new Handler().postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }

    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.permission_Read_data);
            }
            return false;
        }
    }

    private void selectImage() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
    }

    private void selectImagektp() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        }
    }

    private void selectImagesim() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 4);
        }
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        return result != null ? result : "Not found";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage == null) return;

            try (InputStream imageStream = getContentResolver().openInputStream(selectedImage)) {
                Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);
                if (imagebitmap == null) return;

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try (InputStream exifStream = getContentResolver().openInputStream(selectedImage)) {
                        ExifInterface exif = new ExifInterface(exifStream);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90: matrix.postRotate(90); break;
                            case ExifInterface.ORIENTATION_ROTATE_180: matrix.postRotate(180); break;
                            case ExifInterface.ORIENTATION_ROTATE_270: matrix.postRotate(270); break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);

                if (requestCode == 2) {
                    foto.setImageBitmap(rotatedBitmap);
                    imageByteArray = baos.toByteArray();
                    decoded = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                } else if (requestCode == 3) {
                    fotoktp.setImageBitmap(rotatedBitmap);
                    imageByteArrayktp = baos.toByteArray();
                    decodedktp = BitmapFactory.decodeByteArray(imageByteArrayktp, 0, imageByteArrayktp.length);
                } else if (requestCode == 4) {
                    fotosim.setImageBitmap(rotatedBitmap);
                    imageByteArraysim = baos.toByteArray();
                    decodedsim = BitmapFactory.decodeByteArray(imageByteArraysim, 0, imageByteArraysim.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArray = baos.toByteArray();
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    public String getStringImagektp(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArrayktp = baos.toByteArray();
        return Base64.encodeToString(imageByteArrayktp, Base64.DEFAULT);
    }

    public String getStringImagesim(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArraysim = baos.toByteArray();
        return Base64.encodeToString(imageByteArraysim, Base64.DEFAULT);
    }

    public void notif2(String text) {
        rlnotif2.setVisibility(View.VISIBLE);
        textnotif2.setText(text);
        new Handler().postDelayed(() -> rlnotif2.setVisibility(View.GONE), 3000);
    }

    public void Send_Number_tofirebase(String phoneNumber) {
        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
                verify = "true";
            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progresshide();
                Log.d("respon", e.toString());
                notif2("Verifikasi Gagal!");
                if (e instanceof FirebaseTooManyRequestsException) {
                    notif2("Terlalu banyak permintaan!");
                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationId = verificationId;
                resendToken = token;
                sendTo.setText("Send to ( " + phoneNumber + " )");
                progresshide();
                viewFlipper.setInAnimation(RegisterActivity.this, R.anim.from_right);
                viewFlipper.setOutAnimation(RegisterActivity.this, R.anim.to_left);
                viewFlipper.setDisplayedChild(1);
            }
        };
    }

    public void verifyCode(View view) {
        String code = numOne.getText().toString() + numTwo.getText().toString() + numThree.getText().toString() + numFour.getText().toString() + numFive.getText().toString() + numSix.getText().toString();
        if (!code.isEmpty()) {
            progressshow();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else {
            notif2("Enter your verification code!");
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        upload("false");
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
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, verificationCallbacks, resendToken);
    }

    public void codenumber() {
        numOne.addTextChangedListener(new GenericTextWatcher(numOne, numTwo));
        numTwo.addTextChangedListener(new GenericTextWatcher(numTwo, numThree));
        numThree.addTextChangedListener(new GenericTextWatcher(numThree, numFour));
        numFour.addTextChangedListener(new GenericTextWatcher(numFour, numFive));
        numFive.addTextChangedListener(new GenericTextWatcher(numFive, numSix));
        numSix.addTextChangedListener(new GenericTextWatcher(numSix, null));
    }

    private void upload(final String check) {
        progressshow();
        RegisterRequestJson request = new RegisterRequestJson();
        request.setNamadriver(nama.getText().toString());
        request.setPassword(password.getText().toString());
        request.setNoktp(idcardtext.getText().toString());
        request.setTglLahir(dateview);
        request.setNoTelepon(countryCode.getText().toString().replace("+", "") + phone.getText().toString());
        request.setPhone(phone.getText().toString());
        request.setEmail(email.getText().toString());
        request.setFoto(getStringImage(decoded));
        request.setJob(String.valueOf(fiturlist.get(job.getSelectedItemPosition()).getId()));
        request.setGender(String.valueOf(gender.getSelectedItem()));
        request.setAlamat(alamat.getText().toString());
        request.setMerek(brand.getText().toString());
        request.setTipe(type.getText().toString());
        request.setNomorkendaraan(vehiclenumber.getText().toString());
        request.setWarna(color.getText().toString());
        request.setFotoktp(getStringImagektp(decodedktp));
        request.setFotosim(getStringImagesim(decodedsim));
        request.setIdsim(driverlicensetext.getText().toString());
        request.setCountrycode(countryCode.getText().toString());
        request.setChecked(check);

        DriverService service = ServiceGenerator.createService(DriverService.class, request.getEmail(), request.getNoTelepon());
        service.register(request).enqueue(new Callback<RegisterResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponseJson> call, @NonNull Response<RegisterResponseJson> response) {
                progresshide();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getMessage().equalsIgnoreCase("next")) {
                        Nextbtn(viewFlipper);
                    } else if (response.body().getMessage().equalsIgnoreCase("success")) {
                        User user = response.body().getUser();
                        Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Constants.PREF_NAME, "reg");
                        intent.putExtra(Constants.METHOD_NAME, user.getId());
                        intent.putExtra(Constants.METHOD, user.getNoTelepon());
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();
                    } else {
                        notif(response.body().getMessage());
                    }
                } else {
                    notif("Error");
                }
            }
            @Override
            public void onFailure(@NonNull Call<RegisterResponseJson> call, @NonNull Throwable t) {
                progresshide();
                t.printStackTrace();
                notif("Error!");
            }
        });
    }

    // Class helper untuk OTP
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
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    }
}