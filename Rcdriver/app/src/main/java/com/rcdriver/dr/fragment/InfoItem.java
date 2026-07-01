package com.rcdriver.dr.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.MainActivity;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.DriverRequestJson;
import com.rcdriver.dr.json.FcmResponse;
import com.rcdriver.dr.json.HapusMenuRequest;
import com.rcdriver.dr.json.HapusMenuRespon;
import com.rcdriver.dr.json.ItemRespon;
import com.rcdriver.dr.json.LayananRequest;
import com.rcdriver.dr.json.SendFcmRequest;
import com.rcdriver.dr.json.UpdateMenuRequestJson;
import com.rcdriver.dr.json.UpdateMenuResponseJson;
import com.rcdriver.dr.models.MenuModel;
import com.rcdriver.dr.models.Notif;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoItem {
    public static String IdMenu = "0";
    public static String IdTrans = "0";
    public static String TotJumlah = "0";
    public static String strHarga = "0";
    public static String strSubTotal = "0";
    public static String strHargaPromo = "0";
    public static String CekPromo = "0";
    public static String setHitung = "Kurang";
    public static String tokenpelanggan = "0";
    public static int setHarga = 0;
    Context context;
    private List<MenuModel> mMenu = new ArrayList<MenuModel>();

    private static String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
    //-------------------------------- cek Menu Item ---------------------------------------------

    public void showPopupWindow(final View view, String idMenu, String Layanan, String Transaksi, String Jumlah,String token) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.layout_menu, null);
        context = view.getContext();
        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        IdMenu = idMenu;
        IdTrans = Transaksi;
        tokenpelanggan = token;
        ImageView imgClose = (ImageView) popupView.findViewById(R.id.imgclose);
        TextView tNama = (TextView) popupView.findViewById(R.id.txtnama);
        TextView tHarga = (TextView) popupView.findViewById(R.id.txtharga);
        ImageView imgmenu = (ImageView) popupView.findViewById(R.id.imagemenu);
        TextView tJumlah = (TextView) popupView.findViewById(R.id.txtjumlah);
        Button BtnMin = (Button) popupView.findViewById(R.id.btnMin);
        // Button BtnPlus = (Button) popupView.findViewById(R.id.btnPlus);
        Button BtnUpdate = (Button) popupView.findViewById(R.id.btnUpdate);
        Button BtnHapus = (Button) popupView.findViewById(R.id.btnHapus);
        int backgroundColor = ContextCompat.getColor(context, R.color.gray);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            BtnUpdate.setEnabled(false);
//            BtnUpdate.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
//        }
        //-------------------------------------- Load Menu ----------------------------------
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService userService = ServiceGenerator.createService(DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        DriverRequestJson param = new DriverRequestJson();
        param.setId(idMenu);
        userService.LoadMenu(param).enqueue(new Callback<ItemRespon>() {
            @Override
            public void onResponse(@NonNull Call<ItemRespon> call, @NonNull final Response<ItemRespon> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("sukses")) {
                        mMenu = response.body().getData();
                        for (int i = 0; i < mMenu.size(); ++i) {
                            String sNama = mMenu.get(i).getItem();
                            String sHarga = mMenu.get(i).getHarga();
                            String sHargaPromo = mMenu.get(i).getPromo();
                            String sFoto = mMenu.get(i).getFoto();
                            String sStatus = mMenu.get(i).getStatus();
                            CekPromo = mMenu.get(i).getStatus();
                            String sJumlah = Jumlah;
                            tNama.setText(sNama);
                            tJumlah.setText(sJumlah);
                            TotJumlah = tJumlah.getText().toString();
                            if (sStatus.equals("0")) {
                                strHarga = sHarga;
                                int Harga = Integer.parseInt(strHarga);
                                int Qty = Integer.parseInt(sJumlah);
                                ;
                                int Hasils = Harga * Qty;
                                Double getprice = Double.valueOf(Hasils);
                                String zFormat = formatRupiah(getprice);
                                String ValFormat = zFormat.replaceAll(",00", "");
                                tHarga.setText(ValFormat);
                                strSubTotal = String.valueOf(Hasils);
                            } else {
                                strHarga = sHargaPromo;
                                strHargaPromo = sHargaPromo;
                                int Harga = Integer.parseInt(strHarga);
                                int Qty = Integer.parseInt(sJumlah);
                                ;
                                int Hasils = Harga * Qty;
                                Double getprice = Double.valueOf(Hasils);
                                String zFormat = formatRupiah(getprice);
                                String ValFormat = zFormat.replaceAll(",00", "");
                                tHarga.setText(ValFormat);
                                strSubTotal = String.valueOf(Hasils);
                            }

                            Glide.get(context).clearMemory();
                            clearGlideDiskCache();
                            String baseurl = Constants.FOTO_MENU + sFoto;
                            Glide.with(context)
                                    .asBitmap()
                                    .load(baseurl)
                                    .apply(RequestOptions.skipMemoryCacheOf(true))
                                    .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                                    .circleCrop()
                                    .override(200, 200)
                                    .listener(new RequestListener<Bitmap>() {

                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                                            return false;
                                        }
                                    })
                                    .apply(RequestOptions.placeholderOf(R.drawable.logo))
                                    .apply(RequestOptions.skipMemoryCacheOf(true))
                                    .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                                    .into(imgmenu);
                            android.util.Log.d("CekMenu", "Menu " + mMenu.get(i).getItem());
                        }

                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemRespon> call, @NonNull Throwable t) {
                android.util.Log.d("CekMenu", t.getMessage());
            }
        });
        //------------------------------------------------------------------------------------
        BtnMin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                setHitung = "Kurang";
                // Snackbar.make(view, setHitung, Snackbar.LENGTH_LONG).show();
                int currentID = Integer.parseInt(tJumlah.getText().toString());
                currentID--;
                String Changed = String.valueOf(currentID--);
                tJumlah.setText(Changed);
                TotJumlah = tJumlah.getText().toString();
                int Harga = Integer.parseInt(strHarga);
                if (tJumlah.length() < 1) {
                    setHarga = Harga;
                } else {
                    setHarga = Harga * Integer.parseInt(tJumlah.getText().toString());
                    int backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        BtnUpdate.setEnabled(true);
                        BtnUpdate.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
                    }
                }
                if (currentID < 0) {
                    tJumlah.setText("1");
                    TotJumlah = "1";
                    setHarga = Harga;
                    int backgroundColor = ContextCompat.getColor(context, R.color.gray);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        BtnUpdate.setEnabled(false);
                        BtnUpdate.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
                    }
                } else {
                    int backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        BtnUpdate.setEnabled(true);
                        BtnUpdate.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
                    }
                }

                Double getprice = Double.valueOf(setHarga);
                String zFormat = formatRupiah(getprice);
                String ValFormat = zFormat.replaceAll(",00", "");
                tHarga.setText(ValFormat);
                strSubTotal = String.valueOf(setHarga);
            }
        });

        /*BtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHitung = "Tambah";
                Snackbar.make(view, setHitung, Snackbar.LENGTH_LONG).show();
                int currentID  = Integer.parseInt(tJumlah.getText().toString());
                currentID++;
                String Changed = String.valueOf(currentID++);
                tJumlah.setText(Changed);

                int Harga = Integer.parseInt(strHarga);
                setHarga = Harga * Integer.parseInt(tJumlah.getText().toString());
                TotJumlah = tJumlah.getText().toString();
                Double getprice = Double.valueOf(setHarga);
                String zFormat = formatRupiah(getprice);
                String ValFormat = zFormat.replaceAll(",00","");
                tHarga.setText(ValFormat);
                int backgroundColor = ContextCompat.getColor(context, R.color.colorPrimary);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    BtnUpdate.setEnabled(true);
                    BtnUpdate.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
                }
                strSubTotal = String.valueOf(setHarga);
            }
        });
*/

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateTransaksi(Transaksi, strHarga, Layanan);
                Snackbar.make(view, "Menu Berhasil Diperbarui.", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
        BtnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHitung = "Kurang";
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Notif notif = new Notif();
                                notif.title = "Pesanan";
                                notif.message = "Driver Telah Menghapus Pesanan Kamu.";
                                kirimnotif(tokenpelanggan, notif);
                                HapusByItem(Transaksi, strSubTotal, Layanan);
                                HapusItem();
                                Snackbar.make(view, "Menu Berhasil Dihapus.", Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Apakah Anda Yakin ?").setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener).show();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    void clearGlideDiskCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    //----------------------------------------- Update Harga ------------------------------------
    private void UpdateTransaksi(String idtrans, String Biaya, String CurHarga) {
        String Saldo = CurHarga;
        int angka1;
        int angka2;
        int hasil;
        angka1 = Integer.parseInt(Saldo);
        angka2 = Integer.parseInt(Biaya);
        switch (setHitung) {
            case "Kurang":
                hasil = angka1 - angka2;
                break;
            case "Tambah":
                hasil = angka1 + angka2;
                break;
            default:
                hasil = Integer.parseInt(strHarga);
                break;
        }

        String UpdateBiaya = String.valueOf(hasil);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        LayananRequest request = new LayananRequest();
        request.setId_transaksi(idtrans);
        request.setTotal_biaya(UpdateBiaya);
        service.updateHarga(request).enqueue(new Callback<UpdateMenuResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<UpdateMenuResponseJson> call, @NonNull Response<UpdateMenuResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                        android.util.Log.e("updateharga", response.body().mesage);
                        UpdateItem();
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<UpdateMenuResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                android.util.Log.e("updateharga", t.getMessage());
            }
        });
    }
    private void UpdateItem(){
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateMenuRequestJson request = new UpdateMenuRequestJson();
        request.setId(IdMenu);
        request.setIdtrans(IdTrans);
        request.setJumlah(TotJumlah);
        request.setHarga(strSubTotal);
        service.updateMenu(request).enqueue(new Callback<UpdateMenuResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<UpdateMenuResponseJson> call, @NonNull Response<UpdateMenuResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                        Notif notif = new Notif();
                        notif.title = "Pesanan";
                        notif.message = "Driver Telah Mengubah Pesanan Kamu.";
                        kirimnotif(tokenpelanggan, notif);
                        android.util.Log.e("updatemenu", response.body().mesage);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<UpdateMenuResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                android.util.Log.e("updatemenu", t.getMessage());
            }
        });
    }

    private void HapusByItem(String idtrans, String Biaya, String CurHarga) {
        String Saldo = CurHarga;
        int angka1;
        int angka2;
        int hasil;
        angka1 = Integer.parseInt(Saldo);
        angka2 = Integer.parseInt(Biaya);
        hasil = angka1 - angka2;
        String UpdateBiaya = String.valueOf(hasil);
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        LayananRequest request = new LayananRequest();
        request.setId_transaksi(idtrans);
        request.setTotal_biaya(UpdateBiaya);
        service.updateHarga(request).enqueue(new Callback<UpdateMenuResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<UpdateMenuResponseJson> call, @NonNull Response<UpdateMenuResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                        android.util.Log.e("hapusharga", response.body().mesage);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<UpdateMenuResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                android.util.Log.e("hapusharga", t.getMessage());
            }
        });
    }
    private void HapusItem(){
        final User loginUser = BaseApp.getInstance(context).getLoginUser();
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        HapusMenuRequest param = new HapusMenuRequest();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setId_item(IdMenu);
        param.setId_transaksi(IdTrans);
        service.hapusMenu(param).enqueue(new Callback<HapusMenuRespon>() {
            @Override
            public void onResponse(Call<HapusMenuRespon> call, Response<HapusMenuRespon> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        android.util.Log.d("HapusMenu", response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<HapusMenuRespon> call, Throwable t) {
                android.util.Log.d("HapusMenu", t.getMessage());
            }
        });
    }
    private void kirimnotif(final String regIDTujuan, final Notif notif) {
        final User login = BaseApp.getInstance(context).getLoginUser();
        if(login != null){
            DriverService service = ServiceGenerator.createService(DriverService.class, login.getNoTelepon(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(regIDTujuan);
            param.setData(notif);
            service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                    if (response.isSuccessful()) {
                        android.util.Log.d("ResultFCM", response.body().getMessage());
                        if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("sukses")) {
                            android.util.Log.d("ResultFCM", response.body().getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<FcmResponse> call, @NonNull Throwable t) {
                    android.util.Log.e("TestFCM", t.getMessage());
                }
            });
        }
    }
}
