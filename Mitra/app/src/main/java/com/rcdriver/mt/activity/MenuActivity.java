package com.rcdriver.mt.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import Log
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull; // Import NonNull
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat; // Import ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

// Import ItemClickListener dari MenusItem
// Pastikan path ini benar:

// import com.asia.mitra.item.CategoryItem; // Import CategoryItem jika digunakan (saat ini tidak)
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.rcdriver.mt.R;
import com.rcdriver.mt.constants.BaseApp;
// import com.asia.mitra.item.MenusItem; // Duplikat import, hapus salah satu
import com.rcdriver.mt.json.AddEditItemRequestJson;
import com.rcdriver.mt.json.AddEditKategoriRequestJson;
import com.rcdriver.mt.json.ItemRequestJson;
import com.rcdriver.mt.json.ItemResponseJson;
import com.rcdriver.mt.json.ResponseJson;
import com.rcdriver.mt.models.ItemModel;
import com.rcdriver.mt.models.User;
import com.rcdriver.mt.utils.api.ServiceGenerator;
import com.rcdriver.mt.utils.api.service.MerchantService;
// Import MenusItem
import com.rcdriver.mt.item.MenusItem;


import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MenuActivity"; // TAG untuk logging

    // Deklarasi Views
    private ImageView editcategory, backbtn, hapuscategory;
    private Button addmenu;
    private RecyclerView menu;
    private ShimmerFrameLayout shimmermenu;
    private TextView namakategori;

    // Data
    private String idkategori, active, namakat;
    private MenusItem menuItem;
    private List<ItemModel> itemmenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inisialisasi Views
        editcategory = findViewById(R.id.editcategory);
        addmenu = findViewById(R.id.buttonaddmenu);
        backbtn = findViewById(R.id.back_btn);
        hapuscategory = findViewById(R.id.hapuscategory);
        menu = findViewById(R.id.menu);
        shimmermenu = findViewById(R.id.shimmermenu);
        namakategori = findViewById(R.id.namacategory);

        // Ambil data dari Intent
        Intent intent = getIntent();
        idkategori = intent.getStringExtra("idkategori");
        active = intent.getStringExtra("active"); // Status kategori (aktif/tidak)
        namakat = intent.getStringExtra("nama");

        // Set nama kategori awal
        if (namakat != null) {
            namakategori.setText(namakat);
        } else {
            namakategori.setText("Menu"); // Default jika nama null
        }

        // Setup Listeners
        editcategory.setOnClickListener(v -> editcategory());
        backbtn.setOnClickListener(v -> finish());
        addmenu.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, AddmenuActivity.class);
            i.putExtra("idkategori", idkategori);
            startActivity(i);
        });
        hapuscategory.setOnClickListener(v -> clickDonekat());

        // Setup RecyclerView
        menu.setHasFixedSize(true);
        menu.setNestedScrollingEnabled(false); // Bisa true jika layout mengizinkan scrolling terpisah
        menu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getdata(); // Muat data saat activity resumed
    }

    // Menampilkan Shimmer
    private void shimmershow() {
        if (menu != null) menu.setVisibility(View.GONE);
        if (shimmermenu != null) {
            shimmermenu.setVisibility(View.VISIBLE);
            shimmermenu.startShimmer();
        }
    }

    // Menyembunyikan Shimmer
    private void shimmertutup() {
        if (shimmermenu != null) {
            shimmermenu.stopShimmer();
            shimmermenu.setVisibility(View.GONE);
        }
        if (menu != null) menu.setVisibility(View.VISIBLE);
    }

    // Mengambil data item menu dari API
    private void getdata() {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        // Cek null loginUser
        if (loginUser == null) {
            Log.e(TAG, "User is not logged in!");
            Toast.makeText(this, "Sesi login berakhir.", Toast.LENGTH_SHORT).show();
            shimmertutup();
            // Pertimbangkan redirect ke Login Activity
            // finish();
            return;
        }

        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        ItemRequestJson parameter = new ItemRequestJson();
        parameter.setNotelepon(loginUser.getNoTelepon());
        parameter.setIdmerchant(loginUser.getId_merchant());
        parameter.setIdkategori(idkategori);

        merchantService.itemlist(parameter).enqueue(new Callback<ItemResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ItemResponseJson> call, @NonNull Response<ItemResponseJson> response) {
                shimmertutup();
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equalsIgnoreCase(response.body().getMessage())) {
                        itemmenu = response.body().getData();
                        if (itemmenu != null && !itemmenu.isEmpty()) { // Cek null dan empty
                            // Membuat adapter dengan lambda untuk click listener
                            menuItem = new MenusItem(MenuActivity.this, itemmenu, R.layout.item_menus,
                                    (item, v) -> {
                                        // Pastikan item tidak null sebelum akses ID
                                        if (item != null) {
                                            clickDone(String.valueOf(item.getId_item()), item.getFoto_item());
                                        }
                                    }
                            );
                            menu.setAdapter(menuItem);
                            Log.d(TAG, "Menu items loaded successfully: " + itemmenu.size());
                        } else {
                            Log.w(TAG, "Menu items list is null or empty.");
                            // Kosongkan adapter jika data kosong
                            if (menuItem != null) {
                                itemmenu.clear(); // Kosongkan list
                                menuItem.notifyDataSetChanged(); // Beritahu adapter
                            }
                            // Tampilkan pesan kosong jika perlu
                            // Toast.makeText(MenuActivity.this, "Tidak ada menu dalam kategori ini.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.w(TAG, "API Error (message != success): " + response.body().getMessage());
                        Toast.makeText(MenuActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "API Error (Unsuccessful Response): " + response.code());
                    Toast.makeText(MenuActivity.this, "Gagal memuat menu: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemResponseJson> call, @NonNull Throwable t) {
                shimmertutup();
                Log.e(TAG, "Network Failure: ", t);
                Toast.makeText(MenuActivity.this, "Kesalahan Jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Dialog konfirmasi hapus item menu
    public void clickDone(final String iditem, final String fotolama) {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Hapus Menu")
                .setMessage("Apakah Anda yakin ingin menghapus menu ini?")
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> deleteitem(iditem, fotolama))
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Dialog konfirmasi hapus kategori
    public void clickDonekat() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Hapus Kategori")
                .setMessage("Apakah Anda yakin ingin menghapus kategori ini beserta semua menunya?")
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> deletekat())
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Menghapus item menu via API
    private void deleteitem(String iditem, String fotolama) {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) { shimmershow(); return; }

        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AddEditItemRequestJson param = new AddEditItemRequestJson();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setId(iditem);
        param.setFotolama(fotolama); // API mungkin perlu ini untuk hapus file di server

        merchantService.deleteitem(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                // shimmertutup() akan dipanggil oleh getdata() setelah refresh
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equalsIgnoreCase(response.body().getMessage())) {
                        Toast.makeText(MenuActivity.this, "Menu dihapus.", Toast.LENGTH_SHORT).show();
                        getdata(); // Muat ulang data
                    } else {
                        Toast.makeText(MenuActivity.this, "Gagal menghapus: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        shimmertutup();
                    }
                } else {
                    Log.e(TAG, "Delete item API Error: " + response.code());
                    Toast.makeText(MenuActivity.this, "Error hapus item: " + response.code(), Toast.LENGTH_SHORT).show();
                    shimmertutup();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                shimmertutup();
                Log.e(TAG, "Delete item Network Failure: ", t);
                Toast.makeText(MenuActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Menghapus kategori via API
    private void deletekat() {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) { shimmershow(); return; }

        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AddEditKategoriRequestJson param = new AddEditKategoriRequestJson();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setId(idkategori); // ID Kategori yang akan dihapus

        merchantService.deletekategori(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                // Tidak perlu shimmertutup() karena activity akan di-finish
                if (response.isSuccessful() && response.body() != null) {
                    if ("success".equalsIgnoreCase(response.body().getMessage())) {
                        Toast.makeText(MenuActivity.this, "Kategori dihapus.", Toast.LENGTH_SHORT).show();
                        finish(); // Tutup activity
                    } else {
                        Toast.makeText(MenuActivity.this, "Gagal menghapus kategori: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        shimmertutup();
                    }
                } else {
                    Log.e(TAG, "Delete category API Error: " + response.code());
                    Toast.makeText(MenuActivity.this, "Error hapus kategori: " + response.code(), Toast.LENGTH_SHORT).show();
                    shimmertutup();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                shimmertutup();
                Log.e(TAG, "Delete category Network Failure: ", t);
                Toast.makeText(MenuActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Menampilkan dialog untuk edit kategori
    @SuppressLint("SetTextI18n")
    private void editcategory() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addcategory); // Gunakan layout yang sama
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (dialog.getWindow() != null) {
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);
        }

        // Inisialisasi view di dalam dialog
        ImageView close = dialog.findViewById(R.id.close);
        final EditText text = dialog.findViewById(R.id.textadd);
        SwitchCompat switchadd = dialog.findViewById(R.id.switchactive);
        final Button submit = dialog.findViewById(R.id.submit);
        ImageView menuimageDialog = dialog.findViewById(R.id.menuimage);
        Button addimageDialog = dialog.findViewById(R.id.addimage);

        // Sembunyikan elemen gambar untuk edit kategori
        addimageDialog.setVisibility(View.GONE);
        menuimageDialog.setVisibility(View.GONE);

        // Set nilai awal
        text.setText(namakat);
        submit.setText("Edit Kategori");

        // Status switch
        boolean isActive = "1".equals(active);
        switchadd.setChecked(isActive);
        // Update 'active' saat switch berubah
        switchadd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            active = isChecked ? "1" : "0";
            Log.d(TAG, "Edit Category - Switch status changed: " + active);
        });

        submit.setOnClickListener(v -> {
            String newName = text.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(MenuActivity.this, "Nama Kategori tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                setDialogSubmitState(submit, false, "Mohon Tunggu...");

                User loginUser = BaseApp.getInstance(MenuActivity.this).getLoginUser();
                if (loginUser == null) {
                    Toast.makeText(MenuActivity.this, "Sesi login berakhir.", Toast.LENGTH_SHORT).show();
                    setDialogSubmitState(submit, true, "Edit Kategori");
                    dialog.dismiss();
                    return;
                }

                MerchantService merchantService = ServiceGenerator.createService(
                        MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
                AddEditKategoriRequestJson param = new AddEditKategoriRequestJson();
                param.setNotelepon(loginUser.getNoTelepon());
                param.setId(idkategori); // ID Kategori yang diedit
                param.setIdmerchant(loginUser.getId_merchant());
                param.setNamakategori(newName); // Nama baru
                param.setStatus(active); // Status baru

                merchantService.editkategori(param).enqueue(new Callback<ResponseJson>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if ("success".equalsIgnoreCase(response.body().getMessage())) {
                                // Update UI di Activity
                                namakat = newName;
                                namakategori.setText(newName);
                                Toast.makeText(MenuActivity.this, "Berhasil!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                // getdata(); // Opsional: reload data jika perlu
                            } else {
                                Toast.makeText(MenuActivity.this, "Error: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                setDialogSubmitState(submit, true, "Edit Kategori");
                            }
                        } else {
                            Log.e(TAG, "Edit category API Error: " + response.code());
                            Toast.makeText(MenuActivity.this, "Gagal mengedit!", Toast.LENGTH_SHORT).show();
                            setDialogSubmitState(submit, true, "Edit Kategori");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                        Log.e(TAG, "Edit category Network Failure: ", t);
                        Toast.makeText(MenuActivity.this, "Kesalahan Jaringan!", Toast.LENGTH_SHORT).show();
                        setDialogSubmitState(submit, true, "Edit Kategori");
                    }
                });
            }
        });

        close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // Helper untuk set state tombol submit di dialog
    private void setDialogSubmitState(Button button, boolean enabled, String text) {
        if (button != null) {
            button.setEnabled(enabled);
            button.setText(text);
            int backgroundRes = enabled ? R.drawable.button_round_1 : R.drawable.button_round_3; // Ganti drawable
            button.setBackground(ContextCompat.getDrawable(this, backgroundRes));
        }
    }
}