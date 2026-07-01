package com.rcdriver.dr.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
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

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.JobResponseJson;
import com.rcdriver.dr.json.RegisterRequestJson;
import com.rcdriver.dr.json.RegisterResponseJson;
import com.rcdriver.dr.models.JobModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddKendaraanActivity extends AppCompatActivity {
    ImageView fotostnk;
    EditText brand, type, vehiclenumber, color, edtstnk;
    TextView textnotif, textnotif2;
    Button submit;
    RelativeLayout rlnotif, rlprogress, rlnotif2;
    Spinner job;
    byte[] imageByteArraystnk;
    Bitmap decodedstnk;
    String[] spinnerjob;
    List<JobModel> joblist;
    ArrayList<JobModel> fiturlist;
    ArrayList<String> jobdata;
    String disableback;
    private User user;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kendaraan);
        Intent intent = getIntent();
        int MainBG = Color.parseColor("#1AC463");
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        fiturlist = new ArrayList<>();
        jobdata = new ArrayList<>();
        brand = findViewById(R.id.merek);
        type = findViewById(R.id.tipe);
        vehiclenumber = findViewById(R.id.nomorkendaraan);
        color = findViewById(R.id.warna);
        edtstnk = findViewById(R.id.nostnk);
        fotostnk = findViewById(R.id.fotostnk);
        submit = findViewById(R.id.submit);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        rlprogress = findViewById(R.id.rlprogress);
        rlnotif2 = findViewById(R.id.rlnotif2);
        textnotif2 = findViewById(R.id.textnotif2);
        disableback = "false";
        get();
        job = findViewById(R.id.job);
        spinnerjob = getResources().getStringArray(R.array.jobtype);

        fotostnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImagestnk();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (job.getSelectedItemPosition() == 0) {
                    notif("please select job!");
                } else if (TextUtils.isEmpty(brand.getText().toString())) {
                    notif("please enter vehicle brand!");
                } else if (TextUtils.isEmpty(type.getText().toString())) {
                    notif("please enter vehicle type!");
                } else if (TextUtils.isEmpty(vehiclenumber.getText().toString())) {
                    notif("please enter vehicle number!");
                } else if (TextUtils.isEmpty(color.getText().toString())) {
                    notif("please enter vehicle color!");
                } else if (TextUtils.isEmpty(edtstnk.getText().toString())) {
                    notif("please enter STNK number!");
                } else if (imageByteArraystnk == null) {
                    notif("please upload Image STNK!");
                }
                upload();

            }
        });
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

                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("found")) {
                        joblist = response.body().getData();
                        for (int i = 0; i < joblist.size(); i++) {
                            JobModel jobber = new JobModel();
                            jobber.setId(joblist.get(i).getId());
                            jobber.setJob(joblist.get(i).getJob());
                            fiturlist.add(jobber);
                            jobdata.add(joblist.get(i).getJob());
                        }
                        ArrayAdapter<String> jobSpinner = new ArrayAdapter<>(AddKendaraanActivity.this, R.layout.spinner, jobdata);
                        jobSpinner.setDropDownViewResource(R.layout.spinner);
                        job.setAdapter(jobSpinner);
                        job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                // TODO Auto-generated method stub
                                if (position == 0) {
                                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.gray));
                                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                                    Log.e("tes", String.valueOf(fiturlist.get(job.getSelectedItemPosition()).getId()));
                                } else {
                                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                                    Log.e("tes", String.valueOf(fiturlist.get(job.getSelectedItemPosition()).getId()));

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
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

    private boolean check_ReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
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

    private void selectImagestnk() {
        if (check_ReadStoragepermission()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 5);
        }
    }

    public String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
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

    public String getStringImagestnk(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        imageByteArraystnk = baos.toByteArray();
        return Base64.encodeToString(imageByteArraystnk, Base64.DEFAULT);
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

    @Override
    public void onBackPressed() {
        if (!disableback.equals("true")) {
            finish();
        }
    }

    private void upload() {
        progressshow();
        RegisterRequestJson request = new RegisterRequestJson();
        request.setIddriver(user.getId());
        request.setJob(String.valueOf(fiturlist.get(job.getSelectedItemPosition()).getId()));
        request.setMerek(brand.getText().toString());
        request.setTipe(type.getText().toString());
        request.setNomorkendaraan(vehiclenumber.getText().toString());
        request.setWarna(color.getText().toString());
        request.setNoStnk(edtstnk.getText().toString());
        request.setFotostnk(getStringImagestnk(decodedstnk));
        DriverService service = ServiceGenerator.createService(DriverService.class, "admin", "123456");
        service.vehicleadd(request).enqueue(new Callback<RegisterResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponseJson> call, @NonNull Response<RegisterResponseJson> response) {
                progresshide();
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        finish();
                        Toast.makeText(AddKendaraanActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 5) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(Objects.requireNonNull(selectedImage));
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
                fotostnk.setImageBitmap(rotatedBitmap);
                imageByteArraystnk = baos.toByteArray();
                decodedstnk = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));

            }
        }
    }
}