package com.rcdriver.mt.activity;

// Impor yang diperlukan untuk Activity Result API dan Photo Picker
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;

// Impor Android standar dan library lain
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
// import androidx.core.content.ContextCompat; // Tidak digunakan di sini, bisa dihapus jika tidak perlu
import androidx.exifinterface.media.ExifInterface;

import com.rcdriver.mt.json.AddEditKategoriRequestJson;
import com.rcdriver.mt.utils.Log; // Pastikan ini import Log Anda
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// import android.Manifest; // Tidak perlu
import android.annotation.SuppressLint;
import android.content.Intent;
// import android.content.pm.PackageManager; // Tidak perlu
// import android.database.Cursor; // Tidak perlu
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
// import android.provider.MediaStore; // Tidak perlu
import android.util.Base64;
// import android.view.View; // View digunakan
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rcdriver.mt.R;
import com.rcdriver.mt.constants.BaseApp;
import com.rcdriver.mt.json.ResponseJson;
import com.rcdriver.mt.models.User;
import com.rcdriver.mt.utils.SettingPreference;
import com.rcdriver.mt.utils.api.ServiceGenerator;
import com.rcdriver.mt.utils.api.service.MerchantService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
// import java.io.FileNotFoundException; // Diganti IOException
import java.io.FileNotFoundException; // Masih digunakan di catch
import java.io.IOException;
import java.io.InputStream;

public class AddkategoriActivity extends AppCompatActivity {

    private static final String TAG = "AddkategoriActivity"; // TAG untuk logging

    ImageView backbtn, menuimage;
    EditText namamenu;
    // SwitchCompat activepromo; // Variabel ini tidak diinisialisasi/digunakan? Hapus jika tidak perlu
    Button submit, addimage;
    String idkategori;
    byte[] imageByteArray; // Hasil kompresi untuk dikirim
    Bitmap decoded; // Bitmap preview (opsional)
    SettingPreference sp;
    private String switchStatus = "1"; // Default status aktif

    // --- Deklarasi ActivityResultLauncher ---
    private ActivityResultLauncher<PickVisualMediaRequest> pickMediaLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addkategori);

        // Inisialisasi Views
        backbtn = findViewById(R.id.close);
        menuimage = findViewById(R.id.menuimage);
        namamenu = findViewById(R.id.textadd);
        submit = findViewById(R.id.submit);
        addimage = findViewById(R.id.addimage);
        SwitchCompat switchadd = findViewById(R.id.switchactive); // Pastikan ID ini ada di XML
        sp = new SettingPreference(this);

        // --- Inisialisasi Photo Picker Launcher ---
        setupImagePicker();

        Intent intent = getIntent();
        idkategori = intent.getStringExtra("idkategori"); // Ini digunakan di mana? Jika tidak, hapus.

        backbtn.setOnClickListener(v -> finish());
        addimage.setOnClickListener(v -> launchPhotoPicker()); // Panggil launcher baru

        // Setup Switch
        switchadd.setChecked(true); // Default aktif
        switchStatus = "1";
        switchadd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchStatus = isChecked ? "1" : "0";
            Log.d(TAG, "Switch status: " + switchStatus); // Ganti Log.e jadi Log.d
        });

        submit.setOnClickListener(v -> {
            if (namamenu.getText().toString().trim().isEmpty()) { // Trim spasi
                Toast.makeText(AddkategoriActivity.this, "Category name cannot be empty!", Toast.LENGTH_SHORT).show();
            } else if (imageByteArray == null) {
                Toast.makeText(AddkategoriActivity.this, "Image cannot be empty!", Toast.LENGTH_SHORT).show();
            } else {
                addmenu();
            }
        });

    }

    // --- Inisialisasi Photo Picker Launcher ---
    private void setupImagePicker() {
        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    // Callback dipanggil setelah user memilih gambar
                    if (uri != null) {
                        Log.d(TAG, "Photo Picker - Selected URI: " + uri);
                        processImageUri(uri); // Proses URI gambar
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
    // --- HAPUS metode selectImage() yang lama ---
    // --- HAPUS metode getPath(Uri) ---
    // --- HAPUS metode onActivityResult() ---

    // Memproses URI gambar: decode, rotasi, kompresi, dan tampilkan preview
    // Ini berisi logika dari onActivityResult LAMA Anda
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
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos); // Kualitas 80
                imageByteArray = baos.toByteArray(); // Simpan byte array hasil kompresi

                // Tampilkan preview
                menuimage.setImageBitmap(rotatedBitmap);
                Log.d(TAG, "Image processed successfully from URI: " + imageUri + ", size: " + imageByteArray.length + " bytes");

                // Decode ulang hanya jika Anda perlu variabel 'decoded' (Bitmap) di tempat lain
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(imageByteArray));


            } else {
                Toast.makeText(this, "Failed to decode image", Toast.LENGTH_SHORT).show();
                imageByteArray = null;
                decoded = null; // Reset decoded juga
            }

        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found for URI: " + imageUri, e);
            Toast.makeText(this, "Image file not found", Toast.LENGTH_SHORT).show();
            imageByteArray = null;
            decoded = null;
        } catch (IOException e) {
            Log.e(TAG, "IOException during image processing: " + imageUri, e);
            Toast.makeText(this, "Error processing image", Toast.LENGTH_SHORT).show();
            imageByteArray = null;
            decoded = null;
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "OutOfMemoryError decoding image: " + imageUri, e);
            Toast.makeText(this, "Image too large, please select a smaller one", Toast.LENGTH_SHORT).show();
            imageByteArray = null;
            decoded = null;
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
            return source;
        }
    }

    // Konversi Bitmap (decoded) ke Base64 - Perhatikan kualitas kompresi
    public String getStringImage(Bitmap bmp) {
        if (bmp == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos); // Kualitas kompresi bisa disesuaikan
        byte[] bitmapBytes = baos.toByteArray(); // Dapatkan byte array dari bitmap decoded
        return Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
    }

    // Konversi byte array (imageByteArray) ke Base64 (LEBIH EFISIEN jika sudah punya byte array)
    public String getStringImageFromByteArray(byte[] imageData) {
        if (imageData == null) return null;
        return Base64.encodeToString(imageData, Base64.DEFAULT);
    }


    @SuppressLint("UseCompatLoadingForDrawables") // Anotasi ini mungkin tidak relevan lagi
    private void addmenu() {
        // Nonaktifkan tombol submit
        setSubmitState(false, "Please Wait");

        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) {
            Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show();
            resetSubmitButton();
            return;
        }

        MerchantService merchantService = ServiceGenerator.createService(MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AddEditKategoriRequestJson param = new AddEditKategoriRequestJson();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setIdmerchant(loginUser.getId_merchant());
        param.setNamakategori(namamenu.getText().toString().trim());
        param.setStatus(switchStatus); // Gunakan variabel status switch

        // Gunakan imageByteArray langsung (hasil kompresi)
        if (imageByteArray != null) {
            param.setFoto(getStringImageFromByteArray(imageByteArray));
        } else {
            // Handle jika imageByteArray null (meskipun validasi sebelumnya mencegah ini)
            Toast.makeText(this, "Image data is missing!", Toast.LENGTH_SHORT).show();
            resetSubmitButton();
            return;
        }

        merchantService.addkategori(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equalsIgnoreCase(response.body().getMessage())) {
                        Toast.makeText(AddkategoriActivity.this, "Category added successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Tutup activity setelah berhasil
                    } else {
                        Toast.makeText(AddkategoriActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        resetSubmitButton();
                    }
                } else {
                    // Handle error HTTP
                    String errorMsg = "Error: " + response.code();
                    try {
                        if (response.errorBody() != null) errorMsg += " - " + response.errorBody().string();
                    } catch (IOException e) { /* Abaikan */ }
                    Log.e(TAG, "API Error: " + errorMsg);
                    Toast.makeText(AddkategoriActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    resetSubmitButton();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Log.e(TAG, "Network Failure: ", t);
                Toast.makeText(AddkategoriActivity.this, "Error Connection", Toast.LENGTH_SHORT).show();
                resetSubmitButton();
            }
        });
    }

    private void setSubmitState(boolean b, String pleaseWait) {

    }

    // Fungsi untuk mereset tombol submit
    private void resetSubmitButton() {
        submit.setEnabled(true);
        submit.setText("Save Category"); // Sesuaikan teks
        // Kembalikan background jika perlu
        // submit.setBackground(ContextCompat.getDrawable(this, R.drawable.button_round_1));
    }
}