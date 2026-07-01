package com.rcdriver.cs.activity;

// Impor yang diperlukan untuk Activity Result API dan Photo Picker
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;

// Impor Android standar dan library lain
import android.annotation.SuppressLint;
import android.content.Intent;
// import android.content.pm.PackageManager; // Tidak perlu lagi
// import android.database.Cursor; // Tidak perlu lagi
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper; // Import Looper
// import android.provider.MediaStore; // Tidak perlu lagi
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log; // Import Log
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast; // Import Toast

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat; // Import ContextCompat
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.DialogFragment; // Import DialogFragment

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.ybs.countrypicker.CountryPicker;

import java.io.ByteArrayOutputStream;
// import java.io.FileNotFoundException; // Diganti IOException
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.json.EditprofileRequestJson;
import com.rcdriver.cs.json.RegisterResponseJson;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity"; // TAG untuk logging

    ImageView foto, backButtonverify;
    ImageView gantifoto;
    ImageView backbtn;
    EditText phone, nama, email;
    TextView tanggal, countryCode, textnotif;
    Button submit;
    RelativeLayout rlnotif;
    byte[] imageByteArray; // Hasil kompresi untuk dikirim
    Bitmap decoded; // Bitmap hasil decode untuk preview (opsional)
    String dateview; // Format yyyy-MM-dd
    String onsubmit;
    private SimpleDateFormat dateFormatter, dateFormatterview;

    // --- Deklarasi ActivityResultLauncher untuk Photo Picker ---
    private ActivityResultLauncher<PickVisualMediaRequest> pickMediaLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        // Inisialisasi Views
        foto = findViewById(R.id.foto);
        gantifoto = findViewById(R.id.editfoto);
        backbtn = findViewById(R.id.back_btn);
        phone = findViewById(R.id.phonenumber);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        tanggal = findViewById(R.id.tanggal);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        countryCode = findViewById(R.id.countrycode);
        // backButtonverify = findViewById(R.id.back_btn_verify); // Pastikan ID ini ada

        // --- Inisialisasi Photo Picker Launcher ---
        setupImagePicker();

        backbtn.setOnClickListener(v -> finish());
        onsubmit = "true";

        // Inisialisasi formatter tanggal
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormatterview = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        User loginUser = BaseApp.getInstance(this).getLoginUser();

        // Tampilkan data user yang ada
        if (loginUser != null) {
            loadProfileData(loginUser);
        } else {
            handleNullUser();
            return; // Hentikan onCreate jika user null
        }

        // Setup Listener
        gantifoto.setOnClickListener(v -> launchPhotoPicker()); // Panggil launcher
        countryCode.setOnClickListener(v -> showCountryPicker());
        submit.setOnClickListener(v -> handleSubmit());
        tanggal.setOnClickListener(v -> showTanggal());
    }

    // Memuat data profil user ke UI
    private void loadProfileData(User loginUser) {
        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESUSER + loginUser.getFotopelanggan())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder) // Gambar jika error
                .resize(250, 250) // Sesuaikan ukuran
                .centerCrop() // Tambah crop/fit
                .into(foto);

        phone.setText(loginUser.getPhone());
        nama.setText(loginUser.getFullnama());
        email.setText(loginUser.getEmail());
        countryCode.setText(loginUser.getCountrycode());
        dateview = loginUser.getTglLahir(); // Simpan format yyyy-MM-dd

        // Format dan tampilkan tanggal lahir
        if (dateview != null && !dateview.isEmpty()) {
            try {
                Date myDate = dateFormatter.parse(dateview);
                if (myDate != null) {
                    tanggal.setText(dateFormatterview.format(myDate));
                }
            } catch (ParseException e) {
                Log.e(TAG, "Error parsing initial date: " + dateview, e);
                tanggal.setText(""); // Kosongkan jika error
            }
        } else {
            tanggal.setText(""); // Kosongkan jika null
        }
    }

    // Menangani jika data user tidak ditemukan
    private void handleNullUser() {
        Toast.makeText(this, "User data not found, please login again.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginActivity.class); // Ganti ke LoginActivity Anda
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Menampilkan Country Picker
    private void showCountryPicker() {
        final CountryPicker picker = CountryPicker.newInstance("Select Country");
        picker.setListener((name, code, dialCode, flagDrawableResID) -> {
            countryCode.setText(dialCode);
            picker.dismiss();
        });
        // Perbaikan: setStyle membutuhkan style resource ID
        picker.setStyle(DialogFragment.STYLE_NORMAL, R.style.countrypicker_style); // Pastikan R.style.countrypicker_style ada
        picker.show(getSupportFragmentManager(), "Select Country"); // Gunakan SupportFragmentManager
    }

    // Menangani klik tombol submit
    private void handleSubmit() {
        if (validateInput()) {
            if ("true".equals(onsubmit)) { // Gunakan equals()
                setSubmitState(false, getString(R.string.waiting_pleaseWait));
                editprofile();
            }
        }
    }

    // Mengubah state tombol submit (aktif/nonaktif, teks)
    private void setSubmitState(boolean enabled, String text) {
        onsubmit = String.valueOf(enabled); // Update flag onsubmit
        submit.setEnabled(enabled);
        submit.setText(text);
        // int backgroundRes = enabled ? R.drawable.button_round_1 : R.drawable.rounded_corners_button_disabled; // Ganti nama drawable jika perlu
        // submit.setBackground(ContextCompat.getDrawable(this, backgroundRes));
    }

    // Fungsi validasi input
    private boolean validateInput() {
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String emailvalidate = email.getText().toString().trim(); // Trim spasi
        final String phonevalidate = phone.getText().toString().trim();
        final String namavalidate = nama.getText().toString().trim();
        final String tanggalvalidate = tanggal.getText().toString(); // Ambil dari TextView

        if (TextUtils.isEmpty(phonevalidate)) {
            notif(getString(R.string.phoneempty)); return false;
        } else if (TextUtils.isEmpty(namavalidate)) {
            notif("Name cannot be empty"); return false;
        } else if (TextUtils.isEmpty(emailvalidate)) {
            notif(getString(R.string.emailempty)); return false;
        } else if (TextUtils.isEmpty(tanggalvalidate) || dateview == null || dateview.isEmpty()) {
            notif("Birthday cannot be empty!"); return false;
        } else if (!emailvalidate.matches(emailPattern)) {
            notif("Wrong email format!"); return false;
        }
        return true;
    }


    private void showTanggal() {
        Calendar now = Calendar.getInstance();
        if (dateview != null && !dateview.isEmpty()) {
            try {
                Date currentDate = dateFormatter.parse(dateview);
                if (currentDate != null) now.setTime(currentDate);
            } catch (ParseException e) {
                Log.w(TAG, "Could not parse initial date for picker: " + dateview); // Tambahkan Log
            }
        }

        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth); // Cara set lebih sederhana
                    tanggal.setText(dateFormatterview.format(calendar.getTimeInMillis()));
                    dateview = dateFormatter.format(calendar.getTimeInMillis()); // Simpan format yyyy-MM-dd
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(ContextCompat.getColor(this, R.color.colorgradient)); // Gunakan ContextCompat
        datePicker.setMaxDate(Calendar.getInstance()); // Set tanggal maksimal hari ini

        // Gunakan getFragmentManager() karena library wdullaer mungkin belum support AndroidX FragmentManager
        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }


    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        textnotif.setText(text);
        new Handler(Looper.getMainLooper()).postDelayed(() -> rlnotif.setVisibility(View.GONE), 3000);
    }


    /**
     * uploadfoto-------------start.
     */

    // --- Inisialisasi Photo Picker Launcher ---
    private void setupImagePicker() {
        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    // Callback dipanggil setelah user memilih gambar (atau menutup picker)
                    if (uri != null) {
                        Log.d(TAG, "Photo Picker - Selected URI: " + uri);
                        processImageUri(uri); // Proses URI yang didapat
                    } else {
                        Log.d(TAG, "Photo Picker - No media selected");
                    }
                });
    }

    // --- Meluncurkan Photo Picker ---
    private void launchPhotoPicker() {
        Log.d(TAG, "Launching Photo Picker...");
        pickMediaLauncher.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    // --- HAPUS metode check_ReadStoragepermission() ---
    // --- HAPUS metode onRequestPermissionsResult() ---
    // --- HAPUS metode selectImage() yang lama ---
    // --- HAPUS metode getPath(Uri) ---
    // --- HAPUS metode onActivityResult() ---


    // Memproses URI gambar: decode, rotasi, kompresi, dan tampilkan preview
    private void processImageUri(Uri imageUri) {
        InputStream imageStream = null;
        try {
            imageStream = getContentResolver().openInputStream(imageUri);
            if (imageStream == null) throw new IOException("Unable to open input stream for URI");

            // Decode bitmap
            Bitmap selectedBitmap = BitmapFactory.decodeStream(imageStream);

            if (selectedBitmap != null) {
                // Cek dan Rotasi Gambar berdasarkan EXIF
                Bitmap rotatedBitmap = rotateImageIfRequired(selectedBitmap, imageUri);

                // Kompres Gambar
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // Sesuaikan kualitas kompresi (misal: 70-80) untuk ukuran file yang lebih baik
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                imageByteArray = baos.toByteArray(); // Simpan byte array hasil kompresi

                // Tampilkan preview
                foto.setImageBitmap(rotatedBitmap); // Tampilkan bitmap yang sudah dirotasi
                Log.d(TAG, "Image processed successfully from URI: " + imageUri + ", size: " + imageByteArray.length + " bytes");

            } else {
                Toast.makeText(this, "Failed to decode image", Toast.LENGTH_SHORT).show();
                imageByteArray = null; // Reset jika decode gagal
            }

        } catch (FileNotFoundException e) { // Tangkap FileNotFoundException secara spesifik jika perlu
            Log.e(TAG, "File not found for URI: " + imageUri, e);
            Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show();
            imageByteArray = null;
        } catch (IOException e) {
            Log.e(TAG, "IOException during image processing: " + imageUri, e);
            Toast.makeText(this, "Error processing image", Toast.LENGTH_SHORT).show();
            imageByteArray = null;
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "OutOfMemoryError decoding image: " + imageUri, e);
            Toast.makeText(this, "Image too large, please select a smaller one", Toast.LENGTH_SHORT).show();
            imageByteArray = null;
        } finally {
            if (imageStream != null) {
                try {
                    imageStream.close(); // Selalu tutup stream
                } catch (IOException e) {
                    Log.e(TAG, "Error closing InputStream", e);
                }
            }
        }
    }


    // Fungsi untuk rotasi gambar jika diperlukan berdasarkan EXIF
    private Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImageUri) throws IOException {
        InputStream input = null;
        try {
            input = getContentResolver().openInputStream(selectedImageUri);
            if (input == null) return img; // Kembalikan asli jika stream null

            ExifInterface ei = new ExifInterface(input); // Baca EXIF dari InputStream
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Log.d(TAG, "EXIF Orientation: " + orientation); // Log orientasi

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90: return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180: return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270: return rotateImage(img, 270);
                default: return img; // Tidak perlu rotasi
            }
        } finally {
            if (input != null) {
                try { input.close(); } catch (IOException e) { /* Abaikan */ }
            }
        }
    }

    // Fungsi helper untuk melakukan rotasi bitmap
    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "OutOfMemoryError rotating image", e);
            // Jika OOM saat rotasi, coba kembalikan source asli
            return source;
        }
    }

    // Mengirim data profil yang diedit ke server
    private void editprofile() {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) {
            Toast.makeText(this, "User data not available.", Toast.LENGTH_SHORT).show();
            resetSubmitButton();
            return;
        }

        EditprofileRequestJson request = new EditprofileRequestJson();
        request.setFullNama(nama.getText().toString().trim());
        request.setEmail(email.getText().toString().trim());
        request.setEmaillama(loginUser.getEmail());
        request.setId(loginUser.getId());
        String cleanPhoneNumber = phone.getText().toString().replaceAll("[^0-9]", "");
        String countryCodeClean = countryCode.getText().toString().replace("+", "");
        request.setNoTelepon(countryCodeClean + cleanPhoneNumber);
        request.setPhone(cleanPhoneNumber);
        request.setPhonelama(loginUser.getNoTelepon());
        request.setCountrycode(countryCode.getText().toString());

        // Kirim gambar hanya jika imageByteArray tidak null (ada gambar baru dipilih)
        if (imageByteArray != null) {
            request.setFotopelangganlama(loginUser.getFotopelanggan());
            request.setFotopelanggan(getStringImageFromByteArray(imageByteArray));
        } else {
            request.setFotopelangganlama(null);
            request.setFotopelanggan(null);
        }
        request.setTglLahir(dateview); // Format yyyy-MM-dd

        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        service.editProfile(request).enqueue(new Callback<RegisterResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<RegisterResponseJson> call, @NonNull Response<RegisterResponseJson> response) {
                // Cek null response.body() sebelum akses
                if (response.isSuccessful() && response.body() != null) {
                    // Gunakan equalsIgnoreCase untuk message
                    if ("success".equalsIgnoreCase(response.body().getMessage())) {
                        // Cek null getData() dan get(0)
                        if(response.body().getData() != null && !response.body().getData().isEmpty()){
                            User user = response.body().getData().get(0);
                            if(user != null){ // Cek null user
                                saveUser(user);
                                Toast.makeText(EditProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                                finish(); // Kembali ke halaman sebelumnya
                                return; // Keluar dari onResponse setelah berhasil
                            } else {
                                Log.e(TAG, "API Error: User data is null after success.");
                                notif("Failed to process user data.");
                            }
                        } else {
                            Log.e(TAG, "API Error: User data list is null or empty after success.");
                            notif("Failed to retrieve updated user data.");
                        }
                    } else {
                        // Tampilkan pesan error dari server jika tidak success
                        notif(response.body().getMessage());
                    }
                } else {
                    // Handle error HTTP
                    String errorMsg = "Error: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            errorMsg += " - " + response.errorBody().string();
                        }
                    } catch (IOException e) { /* Abaikan error baca body */ }
                    Log.e(TAG, "API Error: " + errorMsg);
                    notif(errorMsg);
                }
                // Jika sampai sini, berarti ada error atau belum finish()
                resetSubmitButton();
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponseJson> call, @NonNull Throwable t) {
                Log.e(TAG, "Network Failure: ", t);
                notif("Network error, please try again!");
                resetSubmitButton();
            }
        });
    }


    // Fungsi untuk mereset tombol submit ke state awal
    private void resetSubmitButton() {
        onsubmit = "true";
        submit.setEnabled(true);
        submit.setText("Submit");
        // Kembalikan background jika diubah saat loading (opsional)
        submit.setBackground(ContextCompat.getDrawable(this, R.drawable.button_round_1)); // Ganti drawable jika perlu
    }

    // Konversi byte array gambar (hasil kompresi) ke Base64
    public String getStringImageFromByteArray(byte[] imageData) {
        if (imageData == null) return null;
        return Base64.encodeToString(imageData, Base64.DEFAULT);
    }

    // Menyimpan data user ke Realm dan BaseApp
    private void saveUser(User user) {
        if (user == null) return;
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance(); // Dapatkan instance Realm
            realm.executeTransaction(realmDb -> {
                // Gunakan copyToRealmOrUpdate jika User punya @PrimaryKey
                realmDb.copyToRealmOrUpdate(user);
                Log.d(TAG, "User saved/updated in Realm.");
            });
            BaseApp.getInstance(EditProfileActivity.this).setLoginUser(user);
        } catch (Exception e) {
            Log.e(TAG, "Error saving user to Realm", e);
        } finally {
            // Jangan close instance default Realm
        }
    }
}