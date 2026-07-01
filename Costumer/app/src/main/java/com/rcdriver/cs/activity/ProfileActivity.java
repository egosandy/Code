package com.rcdriver.cs.activity;

// Impor yang diperlukan untuk Activity Result API dan Photo Picker
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;

// Impor Android standar dan library lain
import android.app.Activity; // Import Activity untuk RESULT_OK
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager; // Masih diperlukan untuk checkSelfPermission
import android.net.Uri;
import android.os.Bundle;
// import android.os.Handler; // Tidak digunakan lagi di versi ini?
// import android.os.Looper; // Tidak digunakan lagi di versi ini?
// import android.provider.MediaStore; // Tidak perlu lagi
import android.util.Log; // Import Log
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout; // Import RelativeLayout
import android.widget.TextView;
import android.widget.Toast; // Import Toast

import androidx.annotation.NonNull;
import androidx.annotation.Nullable; // Import Nullable
import androidx.appcompat.app.AppCompatActivity;

// import java.io.ByteArrayInputStream; // Tidak digunakan lagi?
// import java.io.FileNotFoundException; // Diganti IOException

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.intro.WalkthroughActivity;
import com.rcdriver.cs.json.ResponseJson;
import com.rcdriver.cs.json.UpdateLoginRequest;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity"; // TAG untuk logging

    private Context context;
    private ImageView foto;
    private TextView nama, Saldo, lwasap;
    private SettingPreference sp;
    private User login;
    private final int REQUEST_PHONE_CALL = 1; // Definisikan konstanta
    private static final int EDIT_PROFILE_REQUEST = 101; // Kode request

    // Deklarasikan view lain sebagai member class
    private RelativeLayout editprofile;
    private TextView aboutus, privacy, logout, favorit, llpassword, rating, shareapp;

    // --- Deklarasi ActivityResultLauncher untuk Photo Picker ---
    private ActivityResultLauncher<PickVisualMediaRequest> pickMediaLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        context = this;

        // Inisialisasi Views
        foto = findViewById(R.id.userphoto);
        lwasap = findViewById(R.id.wasap);
        nama = findViewById(R.id.username);
        Saldo = findViewById(R.id.txtSaldo);
        aboutus = findViewById(R.id.llaboutus);
        privacy = findViewById(R.id.llprivacypolicy);
        editprofile = findViewById(R.id.lleditprofile);
        logout = findViewById(R.id.lllogout);
        favorit = findViewById(R.id.favorit);
        llpassword = findViewById(R.id.llpassword);
        rating = findViewById(R.id.rating);
        shareapp = findViewById(R.id.shareapp);

        sp = new SettingPreference(context);
        login = BaseApp.getInstance(context).getLoginUser();

        // --- Inisialisasi Photo Picker Launcher ---
        setupImagePicker(); // Panggil metode setup

        // Load data awal (jika login ada)
        if (login != null) {
            reloadData(); // Panggil reloadData untuk memuat data awal
        } else {
            handleNullUser();
            return;
        }

        setupClickListeners(); // Panggil metode setup listener
    }

    // Metode baru untuk setup semua click listener
    private void setupClickListeners() {
        privacy.setOnClickListener(v -> {
            Intent i = new Intent(context, PrivacyActivity.class);
            startActivity(i);
        });

        aboutus.setOnClickListener(v -> aboutus());

        editprofile.setOnClickListener(v -> {
            Intent i = new Intent(context, EditProfileActivity.class);
            startActivityForResult(i, EDIT_PROFILE_REQUEST); // Gunakan startActivityForResult
        });

        favorit.setOnClickListener(v -> {
            Intent i = new Intent(context, FavouriteActivity.class);
            startActivity(i);
        });

        shareapp.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage = "Let me recommend you this application ";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        });

        lwasap.setOnClickListener(v -> {
            String ponsel = null;
            if (sp != null && sp.getSetting() != null && sp.getSetting().length > 13) {
                ponsel = "+" + sp.getSetting()[13];
            }
            if (ponsel != null && !ponsel.isEmpty()) {
                String semuapesan = "Halo, Mohon bantuannya";
                String url = "https://api.whatsapp.com/send?phone=" + ponsel + "&text=" + Uri.encode(semuapesan);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.setPackage("com.whatsapp");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivitySafely(browserIntent);
                }
            } else {
                Toast.makeText(context, "Nomor bantuan tidak tersedia.", Toast.LENGTH_SHORT).show();
            }
        });

        rating.setOnClickListener(v -> {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });

        llpassword.setOnClickListener(v -> {
            Intent i = new Intent(context, ChangepassActivity.class);
            startActivity(i);
        });

        logout.setOnClickListener(v -> clickDone());
    }

    // Menangani jika data user tidak ditemukan
    private void handleNullUser() {
        Toast.makeText(context, "User data not found, please login again.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, WalkthroughActivity.class); // Ganti ke activity awal
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    private void clickDone() {
        new AlertDialog.Builder(context, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    dialog.dismiss();
                    SetLogin(0);
                    Realm realm = BaseApp.getInstance(context).getRealmInstance();
                    if (realm != null && !realm.isClosed()) {
                        try {
                            realm.executeTransaction(realmDb -> realmDb.delete(User.class));
                        } catch (Exception e) { Log.e(TAG, "Error deleting user", e); }
                    }
                    removeNotif();
                    BaseApp.getInstance(context).setLoginUser(null);
                    Intent intent = new Intent(context, WalkthroughActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void aboutus() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_aboutus);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (dialog.getWindow() != null) {
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);
        }

        final ImageView close = dialog.findViewById(R.id.bt_close);
        final LinearLayout email = dialog.findViewById(R.id.email);
        final LinearLayout phone = dialog.findViewById(R.id.phone);
        final LinearLayout website = dialog.findViewById(R.id.website);
        final WebView about = dialog.findViewById(R.id.aboutus);

        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = "";
        if (sp != null && sp.getSetting() != null && sp.getSetting().length > 1) {
            htmlText = sp.getSetting()[1];
            if (htmlText == null) htmlText = "";
        }
        String fontPath = "file:///android_asset/fonts/dmsans_regular.ttf";
        String text = "<html dir=\"ltr\"><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"" + fontPath + "\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                + "</style></head><body>" + htmlText + "</body></html>";

        if (about != null) {
            about.loadDataWithBaseURL("file:///android_asset/", text, mimeType, encoding, null);
        }

        if (phone != null) {
            phone.setOnClickListener(view -> {
                String phoneNumber = null;
                if (sp != null && sp.getSetting() != null && sp.getSetting().length > 3) {
                    phoneNumber = sp.getSetting()[3];
                }
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivitySafely(callIntent);
                } else {
                    Toast.makeText(context, "Phone number not available.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (email != null) {
            email.setOnClickListener(view -> {
                String emailAddress = null;
                if (sp != null && sp.getSetting() != null && sp.getSetting().length > 2) {
                    emailAddress = sp.getSetting()[2];
                }
                if (emailAddress != null && !emailAddress.isEmpty()) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Halo");
                    startActivitySafely(emailIntent);
                } else {
                    Toast.makeText(context, "Email address not available.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (website != null) {
            website.setOnClickListener(v -> {
                String url = null;
                if (sp != null && sp.getSetting() != null && sp.getSetting().length > 4) {
                    url = sp.getSetting()[4];
                }
                if (url != null && !url.isEmpty()) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    if (!url.startsWith("http://") && !url.startsWith("https://")) url = "https://" + url;
                    i.setData(Uri.parse(url));
                    startActivitySafely(i);
                } else {
                    Toast.makeText(context, "Website URL not available.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (close != null) {
            close.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    private void SetLogin(int loginStatus) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        if (loginUser == null) {
            Log.w(TAG, "SetLogin called but loginUser is null.");
            return;
        }
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateLoginRequest param = new UpdateLoginRequest();
        param.setId(loginUser.getId());
        param.setIslogin(loginStatus);
        service.updatelogin(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.d("UpdateLogin", "Server login status updated successfully.");
                } else {
                    Log.e("UpdateLogin", "Failed to update server login status: " + response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Log.e("UpdateLogin", "Network error updating server login status: " + t.getMessage());
            }
        });
    }

    // --- METODE BARU: Menangani hasil dari EditProfileActivity ---
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == Activity.RESULT_OK) {
            // Edit profile berhasil, muat ulang data di sini
            Log.d(TAG, "onActivityResult: Profile updated, reloading data.");
            reloadData(); // Panggil metode baru untuk memuat ulang
        }
    }

    // --- METODE BARU: Memuat ulang data profil ---
    private void reloadData() {
        login = BaseApp.getInstance(context).getLoginUser(); // Ambil data user terbaru
        if (login != null) {
            nama.setText(login.getFullnama());
            Saldo.setText(login.getEmail()); // Pastikan ini benar menampilkan email

            // Invalidate cache Picasso untuk URL gambar LAMA SEBELUM load yang baru
            String imageUrl = Constants.IMAGESUSER + login.getFotopelanggan();
            if (imageUrl != null) { // Cek null URL gambar
                PicassoTrustAll.getInstance(context).invalidate(imageUrl); // Hapus cache
                Log.d(TAG, "Picasso cache invalidated for: " + imageUrl);

                // Muat ulang gambar
                PicassoTrustAll.getInstance(context)
                        .load(imageUrl) // Muat URL yang sama (Picasso akan ambil yang baru)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder) // Gambar jika error
                        .resize(250, 250) // Sesuaikan ukuran
                        .centerCrop() // Tambahkan crop/fit
                        .into(foto);
                Log.d(TAG, "Profile data reloaded, image load requested.");
            } else {
                Log.w(TAG, "Image URL is null, cannot reload image.");
                foto.setImageResource(R.drawable.image_placeholder); // Tampilkan placeholder
            }

        } else {
            // Handle jika user menjadi null (seharusnya sudah ditangani di onCreate)
            handleNullUser();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Cukup panggil reloadData di onResume.
        // onActivityResult akan menangani reload spesifik setelah edit.
        reloadData();
    }

    // Helper method untuk start activity dengan aman
    private void startActivitySafely(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "No activity found to handle intent: " + intent.getAction(), e);
            Toast.makeText(context, "No app found to handle this action.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) { // Tangkap exception umum lainnya
            Log.e(TAG, "Error starting activity: " + e.getMessage(), e);
            Toast.makeText(context, "Could not perform action.", Toast.LENGTH_SHORT).show();
        }
    }

    // Menangani hasil permintaan izin (hanya untuk CALL_PHONE sekarang)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Call permission granted. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Call permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // --- Inisialisasi Photo Picker Launcher --- (Pindah dari onCreate)
    private void setupImagePicker() {
        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    // Callback dipanggil setelah user memilih gambar (atau menutup picker)
                    if (uri != null) {
                        Log.d(TAG, "Photo Picker - Selected URI: " + uri);
                        // Langsung panggil processImageUri dari EditProfileActivity
                        // Jika Anda ingin memproses di sini, Anda perlu
                        // menyalin/memindahkan logika processImageUri ke sini juga.
                        // Untuk saat ini, kita anggap EditProfileActivity yang memprosesnya.
                        // Jika ingin memproses di sini:
                        // processImageUriFromPicker(uri);
                    } else {
                        Log.d(TAG, "Photo Picker - No media selected");
                    }
                });
    }

    // --- Meluncurkan Photo Picker --- (Pindah dari onCreate / OnClickListener)
    // Metode ini seharusnya dipanggil dari EditProfileActivity, bukan di sini.
    // private void launchPhotoPicker() {
    //     Log.d(TAG, "Launching Photo Picker...");
    //     pickMediaLauncher.launch(new PickVisualMediaRequest.Builder()
    //             .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
    //             .build());
    // }

    // Metode ini juga seharusnya ada di EditProfileActivity
    // private void processImageUriFromPicker(Uri imageUri) { ... }

}