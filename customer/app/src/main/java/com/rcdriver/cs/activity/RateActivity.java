package com.rcdriver.cs.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import com.rcdriver.cs.json.PoinRequest;
import com.rcdriver.cs.json.RequestJson;
import com.rcdriver.cs.json.TipRequestJson;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.json.DetailRequestJson;
import com.rcdriver.cs.json.DetailTransResponseJson;
import com.rcdriver.cs.json.PointRespon;
import com.rcdriver.cs.json.RateRequestJson;
import com.rcdriver.cs.json.RateResponseJson;
import com.rcdriver.cs.json.ResponseJson;
import com.rcdriver.cs.json.SaldoResponse;
import com.rcdriver.cs.json.UpdateStatusRequest;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.PointModel;
import com.rcdriver.cs.models.SaldoModel;
import com.rcdriver.cs.models.StatusModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.NetworkManager;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateActivity extends AppCompatActivity {
    public static String Warna;
    private static long Kalkulasi;
    private final List<StatusModel> mCek = new ArrayList<StatusModel>();
    String iddriver, idtrans, response,pakewallet, namadriver,totalbiaya, submit, idfitur, fotodriver, pointdriver;
    float rate = 3;
    boolean pakaiwallet;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.namadriver)
    TextView nama;
    @BindView(R.id.addComment)
    EditText comment;
    @BindView(R.id.submit)
    Button button;
    @BindView(R.id.shimmername)
    ShimmerFrameLayout shimmername;
    @BindView(R.id.ratingView)
    RatingBar ratingview;
    @BindView(R.id.rp1000)
    TextView Rp1000;
    @BindView(R.id.rp2000)
    TextView Rp2000;
    @BindView(R.id.rp3000)
    TextView Rp3000;
    @BindView(R.id.rp4000)
    TextView Rp4000;
    @BindView(R.id.rp5000)
    TextView Rp5000;
    @BindView(R.id.txtnominal)
    TextView TxtNominal;
    @BindView(R.id.txtsaldo)
    TextView TxtSaldo;
    @BindView(R.id.TxtPoint)
    TextView TxtPoint;
    @BindView(R.id.txtRating)
    TextView TxtRating;
    @BindView(R.id.txtTotal)
    TextView TxtTotal;
    @BindView(R.id.txtwallet)
    TextView TxtWallet;
    @BindView(R.id.txtfitur)
    TextView TxtFitur;
    @BindView(R.id.Saldoku)
    TextView Saldoku;
    @BindView(R.id.bgrate)
    ImageView BGRate;
    @BindView(R.id.tiplayout)
    LinearLayout tiplayout;
    Timer timer = new Timer();
    private ProgressDialog progress;
    //-------------------------------------- Uang Tips ------------------------------------------------
    //saldo driver
    private List<SaldoModel> mSaldo = new ArrayList<>();
    // private List<PointModel> mPoint = new ArrayList<>();
    private List<PointModel> mPoint = new ArrayList<PointModel>();
    private final Runnable updateStatus = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = getIntent();
                        Bundle bundle = intent.getExtras();
                        if (bundle != null) {
                            Warna = "#4c84ff";
                            iddriver = intent.getStringExtra("id_driver");
                            idtrans = intent.getStringExtra("id_transaksi");
                            namadriver = intent.getStringExtra("namadriver");
                            totalbiaya = intent.getStringExtra("total_biaya");
                            pakewallet = intent.getStringExtra("pake_wallet");
                            //   pakaiwallet = intent.getBooleanExtra("pakai_wallet",false);
                            rate = intent.getFloatExtra("rating",3);
                            idfitur = intent.getStringExtra("fitur");
                            fotodriver = intent.getStringExtra("fotodriver");
                            pointdriver = intent.getStringExtra("pointdriver");
                            nama.setText(namadriver);
                            PointDriver(iddriver);
                            SaldoDriver(iddriver);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (NetworkManager.isConnectToInternet(RateActivity.this)) {
                                try {
                                    Intent intent = getIntent();
                                    Bundle bundle = intent.getExtras();
                                    if (bundle != null) {
                                        Warna = "#4c84ff";
                                        iddriver = intent.getStringExtra("id_driver");
                                        idtrans = intent.getStringExtra("id_transaksi");
                                        namadriver = intent.getStringExtra("namadriver");
                                        totalbiaya = intent.getStringExtra("total_biaya");
                                        rate = intent.getFloatExtra("rating",1);
                                        pakewallet = intent.getStringExtra("pake_wallet");
                                        //      pakaiwallet = intent.getStringExtra("pakai_wallet");
                                        idfitur = intent.getStringExtra("fitur");
                                        fotodriver = intent.getStringExtra("fotodriver");
                                        pointdriver = intent.getStringExtra("pointdriver");
                                        PointDriver(iddriver);
                                        SaldoDriver(iddriver);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, 0, 3000);
                }
            }).start();
        }
    };
    //--------------------------------------------------------------------------------------------
    private Handler handler;

    private static String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Warna = "#4c84ff";
            iddriver = intent.getStringExtra("id_driver");
            idtrans = intent.getStringExtra("id_transaksi");
            response = intent.getStringExtra("response");
            namadriver = intent.getStringExtra("namadriver");
            totalbiaya = intent.getStringExtra("total_biaya");
            pakaiwallet = intent.getBooleanExtra("pakai_wallet",false);
            pakewallet = intent.getStringExtra("pake_wallet");
            idfitur = intent.getStringExtra("fitur");
            fotodriver = intent.getStringExtra("fotodriver");
            pointdriver = intent.getStringExtra("pointdriver");
            rate = intent.getFloatExtra("rating",1);
            //   getrate = intent.getStringExtra("rating");
            PicassoTrustAll.getInstance(RateActivity.this)
                    .load(Constants.IMAGESDRIVER + fotodriver)
                    .placeholder(R.drawable.image_placeholder)
                    .into(image);
            nama.setText(namadriver);
            getData(idtrans, iddriver);
            PointDriver(iddriver);
        }
        TxtPoint.setText("0");
        Kalkulasi = 0;
        submit = "true";
        shimmeractive();
        removeNotif();
       /* if(getrate != null){
            ratingview.setRating(Float.parseFloat(getrate));
        }*/
        ratingview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Float ratingVal = (Float) rating;
                Float ratingvalue = (Float) ratingview.getRating();
                String RatVal = String.valueOf(ratingvalue);
                TxtRating.setText(RatVal);
            }
        });
//        int MainBG = Color.parseColor("#4c84ff");
//        String BGColor = Warna;
//        int colorCodeDark = Color.parseColor(BGColor);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            BGRate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(BGColor)));
//        }
        //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCekrate();
        PointDriver(iddriver);
        SaldoDriver(iddriver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCekRate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCekRate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopCekRate();
    }

    private void shimmeractive() {
        shimmername.startShimmerAnimation();
    }

    private void shimmernonactive() {
        shimmername.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
        nama.setVisibility(View.VISIBLE);
        comment.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        ratingview.setVisibility(View.VISIBLE);
        shimmername.stopShimmerAnimation();
    }

    private void getData(String idtrans, String iddriver) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        Saldoku.setText(String.valueOf(loginUser.getWalletSaldo()));
        long A = Long.parseLong(Saldoku.getText().toString());
        //  String replaces = totalbiaya.replaceAll(".0","");
        long B = Long.parseLong(totalbiaya);
        long hasil = A - B;
        Kalkulasi = hasil;

        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdDriver(iddriver);
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().getDriver().size() > 0) {
                        DriverModel driver = Objects.requireNonNull(response.body()).getDriver().get(0);
                        // Toast.makeText(RateActivity.this, driver.getId(), Toast.LENGTH_LONG).show();
                        parsedata(driver);
                        Double getprice = Double.valueOf(totalbiaya);
                        String zFormat = formatRupiah(getprice);
                        String ValFormat = zFormat.replaceAll(",00", "");
                        TxtTotal.setText(ValFormat);
                        //   TxtWallet.setVisibility(View.GONE);
                        //  TxtWallet.setText(pakaiwallet);
                        TxtFitur.setVisibility(View.GONE);
                        TxtFitur.setText(idfitur);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<DetailTransResponseJson> call, @NonNull Throwable t) {

            }
        });

    }

    private void parsedata(final DriverModel driver) {
        final User userLogin = BaseApp.getInstance(this).getLoginUser();
        ratingview.setRating(rate);
        PicassoTrustAll.getInstance(this)
                .load(Constants.IMAGESDRIVER + driver.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .into(image);
        nama.setText(driver.getNamaDriver());
        PointDriver(driver.getId());
        SaldoDriver(driver.getId());
        if (submit.equals("true")) {
            User loginUser = BaseApp.getInstance(this).getLoginUser();
            Rp1000.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom = Rp1000.getText().toString();
                    String nominal = nom.replaceAll("Rp", "");
                    long nominaltips = Long.parseLong(nominal);
                    if (loginUser.getWalletSaldo() < 1000) {
                        Toast.makeText(RateActivity.this, "Saldo Anda Kurang Untuk Memberikan Tip.",
                                Toast.LENGTH_LONG).show();
                        TxtNominal.setText("0");
                    } else {
                        KirimTip(iddriver,"1000");
                        TxtNominal.setText(nominal);
                        Rp1000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.colorPrimary));
                        Rp2000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp3000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp4000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp5000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                    }
                }
            });
            Rp2000.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom = Rp2000.getText().toString();
                    String nominal = nom.replaceAll("Rp", "");
                    long nominaltips = Long.parseLong(nominal);

                    if (loginUser.getWalletSaldo() < 2000) {
                        Toast.makeText(RateActivity.this, "Saldo Anda Kurang Untuk Memberikan TIps.",
                                Toast.LENGTH_LONG).show();
                        TxtNominal.setText("0");
                    } else {
                        KirimTip(iddriver,"2000");
                        TxtNominal.setText(nominal);
                        Rp1000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp2000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.colorPrimary));
                        Rp3000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp4000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp5000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                    }
                }
            });
            Rp3000.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom = Rp3000.getText().toString();
                    String nominal = nom.replaceAll("Rp", "");
                    long nominaltips = Long.parseLong(nominal);

                    if (loginUser.getWalletSaldo() < 3000) {
                        Toast.makeText(RateActivity.this, "Saldo Anda Kurang Untuk Memberikan TIps.",
                                Toast.LENGTH_LONG).show();
                        TxtNominal.setText("0");
                    } else {
                        KirimTip(iddriver,"3000");
                        TxtNominal.setText(nominal);
                        Rp1000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp2000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp3000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.colorPrimary));
                        Rp4000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp5000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                    }
                }
            });
            Rp4000.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom = Rp4000.getText().toString();
                    String nominal = nom.replaceAll("Rp", "");
                    long nominaltips = Long.parseLong(nominal);

                    if (loginUser.getWalletSaldo() < 4000) {
                        Toast.makeText(RateActivity.this, "Saldo Anda Kurang Untuk Memberikan TIps.",
                                Toast.LENGTH_LONG).show();
                        TxtNominal.setText("0");
                    } else {
                        KirimTip(iddriver,"4000");
                        TxtNominal.setText(nominal);
                        Rp1000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp2000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp3000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp4000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.colorPrimary));
                        Rp5000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                    }
                }
            });
            Rp5000.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom = Rp5000.getText().toString();
                    String nominal = nom.replaceAll("Rp", "");
                    long nominaltips = Long.parseLong(nominal);
                    if (loginUser.getWalletSaldo() < 5000) {
                        Toast.makeText(RateActivity.this, "Saldo Anda Kurang Untuk Memberikan TIps.",
                                Toast.LENGTH_LONG).show();
                        TxtNominal.setText("0");
                    } else {
                        KirimTip(iddriver,"5000");
                        TxtNominal.setText(nominal);
                        Rp1000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp2000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp3000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp4000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.black));
                        Rp5000.setTextColor(ContextCompat.getColor(RateActivity.this, R.color.colorPrimary));
                    }
                }
            });

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (pakaiwallet) {
                        //   PerbaruiSaldo();
                    }
                    PerbaruiStatus("0", "0", "0", "false", "0", "0");
                    RateRequestJson request = new RateRequestJson();
                    request.id_transaksi = idtrans;
                    request.id_pelanggan = userLogin.getId();
                    request.id_driver = iddriver;
                    request.rating = String.valueOf(ratingview.getRating());
                    request.catatan = comment.getText().toString();
                    ratingUser(request);

                    String mrating = "0";
                    float rating = ratingview.getRating();
                    if (rating == 3.0f) {
                        mrating = "1";
                    } else if (rating == 4.0f) {
                        mrating = "2";
                    } else if (rating == 5.0f) {
                        mrating = "3";
                    } else {
                        mrating = "0";
                    }
                    SendPoint(iddriver,mrating);
                 //   HapusDB();
                }
            });

        }
        shimmernonactive();
    }

    private void ratingUser(RateRequestJson request) {
        submit = "false";
        button.setText(getString(R.string.waiting_pleaseWait));
        button.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button));

        User loginUser = BaseApp.getInstance(RateActivity.this).getLoginUser();

        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        service.rateDriver(request).enqueue(new Callback<RateResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<RateResponseJson> call, @NonNull Response<RateResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                        /*if(pakaiwallet){
                            PotongSaldo();
                        }*/
                        Intent i = new Intent(RateActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("Status", "1");
                        startActivity(i);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<RateResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                submit = "true";
                button.setText("Submit");
                button.setBackground(getResources().getDrawable(R.drawable.button_round_1));
            }
        });


    }
    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }
    //----------------------------------- Perbarui Status ------------------------------------------
    private void PerbaruiStatus(String idtrans, String iddriver, String biaya, String wallet, String fitur, String respon) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateStatusRequest param = new UpdateStatusRequest();
        param.setId(loginUser.getId());
        param.setId_transaksi(idtrans);
        param.setId_driver(iddriver);
        param.setTotal_biaya(biaya);
        param.setPakai_wallet(wallet);
        param.setFitur(fitur);
        param.setResponse(respon);
        param.setPoint_driver("0");
        param.setIsrate(0);
        service.updateStatus(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.e("UPDATE STATUS ", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<ResponseJson> call, @NonNull Throwable t) {

            }
        });

    }

    private void startCekrate() {
        handler = new Handler();
        handler.postDelayed(updateStatus, 3000);
    }

    private void stopCekRate() {
        handler.removeCallbacks(updateStatus);
    }
    private void PointDriver(String iddriver){
        final User user = BaseApp.getInstance(this).getLoginUser();
        PoinRequest request = new PoinRequest();
        request.setId(iddriver);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.GetPoin(request).enqueue(new Callback<PointRespon>() {
            @Override
            public void onResponse(@NonNull Call<PointRespon> call, @NonNull Response<PointRespon> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        mPoint = response.body().getData();
                        for(int i = 0; i < mPoint.size(); i++){
                            com.rcdriver.cs.utils.Log.d("CekPoin", mPoint.get(i).getPoint());
                            String CPoint = mPoint.get(i).getPoint();
                            assert CPoint != null;
                            if (CPoint == null | CPoint.equals("")) {
                                TxtPoint.setText("0");
                            } else {
                                TxtPoint.setText(CPoint);
                            }
                        }
                    }
                } else {
                    com.rcdriver.cs.utils.Log.d("CekPoin", "Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PointRespon> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void SaldoDriver(String iddriver){
        final User user = BaseApp.getInstance(this).getLoginUser();
        RequestJson request = new RequestJson();
        request.setId(iddriver);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.SaldoDriver(request).enqueue(new Callback<SaldoResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaldoResponse> call, @NonNull Response<SaldoResponse> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        mSaldo = response.body().getData();
                        for(int i = 0; i < mSaldo.size(); i++){
                            com.rcdriver.cs.utils.Log.d("CekSaldo", mSaldo.get(i).getSaldo());
                            String CSaldo = mSaldo.get(i).getSaldo();
                            TxtSaldo.setText(CSaldo);
                        }
                    }
                } else {
                    com.rcdriver.cs.utils.Log.d("CekSaldo", "Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<SaldoResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void KirimTip(String iddriver,String jumlah){
        final User user = BaseApp.getInstance(this).getLoginUser();
        TipRequestJson request = new TipRequestJson();
        request.setId(user.getId());
        request.setIddriver(iddriver);
        request.setAmount(jumlah);
        request.setNama(user.getFullnama());
        request.setEmail(user.getEmail());
        request.setNo_telepon(user.getNoTelepon());
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.KirimSaldo(request).enqueue(new Callback<SaldoResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaldoResponse> call, @NonNull Response<SaldoResponse> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        tiplayout.setVisibility(View.GONE);

                        com.rcdriver.cs.utils.Log.d("KirimTip", "Berhasil");
                    }
                } else {
                    com.rcdriver.cs.utils.Log.d("KirimTip", "Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<SaldoResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void SendPoint(String iddriver,String poin){
        final User user = BaseApp.getInstance(this).getLoginUser();
        RequestJson request = new RequestJson();
        request.setId(iddriver);
        request.setAmount(poin);
        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.KirimPoin(request).enqueue(new Callback<PointRespon>() {
            @Override
            public void onResponse(@NonNull Call<PointRespon> call, @NonNull Response<PointRespon> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        com.rcdriver.cs.utils.Log.d("KirimPoint", "Berhasil");
                    }
                } else {
                    com.rcdriver.cs.utils.Log.d("KirimPoint", "Error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<PointRespon> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
