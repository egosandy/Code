package com.rcdriver.mt.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.rcdriver.mt.R;
import com.rcdriver.mt.activity.IntroActivity;
import com.rcdriver.mt.constants.BaseApp;
import com.rcdriver.mt.constants.Constants;
import com.rcdriver.mt.item.CategoryItem;
import com.rcdriver.mt.item.CategoryNonItem;
import com.rcdriver.mt.json.AddEditKategoriRequestJson;
import com.rcdriver.mt.json.KategoriRequestJson;
import com.rcdriver.mt.json.KategoriResponseJson;
import com.rcdriver.mt.json.ResponseJson;
import com.rcdriver.mt.models.KategoriItemModel;
import com.rcdriver.mt.models.KategoriItemNonModel;
import com.rcdriver.mt.models.User;
import com.rcdriver.mt.utils.Log;
import com.rcdriver.mt.utils.api.ServiceGenerator;
import com.rcdriver.mt.utils.api.service.MerchantService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {

    private Context context;
    private RecyclerView activecategory, nonactivecategory;
    private TextView itemada, itempromo, itemhabis;
    private ShimmerFrameLayout shimmercaton, shimmercatoff;
    private List<KategoriItemModel> order;
    private List<KategoriItemNonModel> ordernon;
    private CategoryItem categoryItem;
    private CategoryNonItem categorynonItem;
    private LinearLayout llactive, llnonactive;
    private RelativeLayout rlnodata;
    private ImageView menuimage;
    private Button addimage;
    byte[] imageByteArray;
    Bitmap decoded;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_menu, container, false);
        context = getContext();
        Button dialogaddcategory = getView.findViewById(R.id.buttonaddcategory);
        activecategory = getView.findViewById(R.id.activecategory);
        nonactivecategory = getView.findViewById(R.id.nonactivecategory);
        itemada = getView.findViewById(R.id.itemada);
        itempromo = getView.findViewById(R.id.itempromo);
        itemhabis = getView.findViewById(R.id.itemhabis);
        shimmercaton = getView.findViewById(R.id.shimmercaton);
        shimmercatoff = getView.findViewById(R.id.shimmercatoff);
        rlnodata = getView.findViewById(R.id.rlnodata);
        llactive = getView.findViewById(R.id.llactive);
        llnonactive = getView.findViewById(R.id.llnonactive);

        dialogaddcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcategory();
            }
        });

        activecategory.setHasFixedSize(true);
        activecategory.setNestedScrollingEnabled(false);
        activecategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        nonactivecategory.setHasFixedSize(true);
        nonactivecategory.setNestedScrollingEnabled(false);
        nonactivecategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        return getView;
    }
    private void selectImage() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }
    }

    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    if(ContextCompat.checkSelfPermission(context,
                            Manifest.permission.READ_MEDIA_IMAGES ) == PackageManager.PERMISSION_GRANTED){
                        return true;
                    }else{
                        try {
                            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                                    Constants.permission_Read_data);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw e;
                        }
                    }
                }

                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            Constants.permission_Read_data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArray = baos.toByteArray();
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = context.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imageStream);

                String path = getPath(selectedImage);
                Matrix matrix = new Matrix();
                ExifInterface exif;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        exif = new ExifInterface(path);
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                matrix.postRotate(90);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                matrix.postRotate(180);
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                matrix.postRotate(270);
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Bitmap rotatedBitmap = Bitmap.createBitmap(imagebitmap, 0, 0, imagebitmap.getWidth(), imagebitmap.getHeight(), matrix, true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                menuimage.setImageBitmap(rotatedBitmap);
                imageByteArray = baos.toByteArray();
                decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }
    private void shimmershow() {
        activecategory.setVisibility(View.GONE);
        shimmercaton.startShimmer(); // <-- Corrected
        shimmercaton.setVisibility(View.VISIBLE);

        nonactivecategory.setVisibility(View.GONE);
        shimmercatoff.startShimmer(); // <-- Corrected
        shimmercatoff.setVisibility(View.VISIBLE);
    }

    private void shimmertutup() {
        shimmercaton.stopShimmer(); // <-- Corrected
        activecategory.setVisibility(View.VISIBLE);
        shimmercaton.setVisibility(View.GONE);

        shimmercatoff.stopShimmer(); // <-- Corrected
        nonactivecategory.setVisibility(View.VISIBLE);
        shimmercatoff.setVisibility(View.GONE);
    }

    private void getdata() {
        shimmershow();
        if (order != null && ordernon != null) {
            order.clear();
            ordernon.clear();
        }
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        MerchantService merchantService = ServiceGenerator.createService(
                MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        KategoriRequestJson param = new KategoriRequestJson();
        param.setNotelepon(loginUser.getNoTelepon());
        param.setIdmerchant(loginUser.getId_merchant());
        merchantService.kategori(param).enqueue(new Callback<KategoriResponseJson>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<KategoriResponseJson> call, Response<KategoriResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        order = response.body().getData();
                        ordernon = response.body().getDatanon();
                        shimmertutup();

                        itemada.setText(response.body().getTotalitemactive() + "Tersedia");
                        itemhabis.setText(response.body().getTotalitemnonactive() + "Habis");
                        itempromo.setText(response.body().getTotalitempromo() + "Promo");
                        if (response.body().getData().isEmpty() && response.body().getDatanon().isEmpty()) {
                            rlnodata.setVisibility(View.VISIBLE);
                        } else {
                            rlnodata.setVisibility(View.GONE);
                        }
                        categoryItem = new CategoryItem(context, order, R.layout.item_category);
                        activecategory.setAdapter(categoryItem);
                        if (response.body().getData().isEmpty()) {
                            llactive.setVisibility(View.GONE);
                        } else {
                            llactive.setVisibility(View.VISIBLE);
                        }

                        categorynonItem = new CategoryNonItem(context, ordernon, R.layout.item_category);
                        nonactivecategory.setAdapter(categorynonItem);
                        if (response.body().getDatanon().isEmpty()) {
                            llnonactive.setVisibility(View.GONE);
                        } else {
                            llnonactive.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Realm realm = BaseApp.getInstance(context).getRealmInstance();
                        realm.beginTransaction();
                        realm.delete(User.class);
                        realm.commitTransaction();
                        BaseApp.getInstance(context).setLoginUser(null);
                        startActivity(new Intent(context, IntroActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<KategoriResponseJson> call, Throwable t) {

            }
        });
    }


    private String Switchstring;
    private void addcategory() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_addcategory);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView close = dialog.findViewById(R.id.close);
        final EditText text = dialog.findViewById(R.id.textadd);
        SwitchCompat switchadd = dialog.findViewById(R.id.switchactive);
        final Button submit = dialog.findViewById(R.id.submit);
        menuimage = dialog.findViewById(R.id.menuimage);
        addimage = dialog.findViewById(R.id.addimage);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        switchadd.setChecked(true);
        Switchstring = "1";

        switchadd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Switchstring = "1";
                    } else {
                        Switchstring = "0";
                    }

                Log.e("switch",Switchstring);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (text.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Silahkan Masukkan Kategory!", Toast.LENGTH_SHORT).show();
                } else {
                    submit.setEnabled(false);
                    submit.setText("Please Wait...");
                    submit.setBackground(context.getResources().getDrawable(R.drawable.button_round_3));
                    User loginUser = BaseApp.getInstance(context).getLoginUser();
                    MerchantService merchantService = ServiceGenerator.createService(
                            MerchantService.class, loginUser.getNoTelepon(), loginUser.getPassword());
                    AddEditKategoriRequestJson param = new AddEditKategoriRequestJson();
                    param.setNotelepon(loginUser.getNoTelepon());
                    param.setId(loginUser.getId_merchant());
                    param.setNamakategori(text.getText().toString());
                    param.setStatus(Switchstring);
                    param.setFoto(getStringImage(decoded));
                    merchantService.addkategori(param).enqueue(new Callback<ResponseJson>() {
                        @Override
                        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                            if (response.isSuccessful()) {
                                if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                                    getdata();
                                    Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                } else {
                                    submit.setEnabled(true);
                                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                                    submit.setText("Add Category");
                                    submit.setBackground(context.getResources().getDrawable(R.drawable.button_round_1));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseJson> call, Throwable t) {
                            submit.setEnabled(true);
                            Toast.makeText(context, "Error Connection!", Toast.LENGTH_SHORT).show();
                            submit.setText("Add Category");
                            submit.setBackground(context.getResources().getDrawable(R.drawable.button_round_1));

                        }
                    });

                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
