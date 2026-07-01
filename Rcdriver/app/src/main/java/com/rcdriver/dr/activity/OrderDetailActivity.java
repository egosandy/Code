package com.rcdriver.dr.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.item.ItemPesananItem;
import com.rcdriver.dr.json.DetailRequestJson;
import com.rcdriver.dr.json.DetailTransResponseJson;
import com.rcdriver.dr.libs.RatingView;
import com.rcdriver.dr.models.PelangganModel;
import com.rcdriver.dr.models.TransaksiModel;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.BackgroundColorTransform;
import com.rcdriver.dr.utils.Log;
import com.rcdriver.dr.utils.Utility;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Hapus import ButterKnife
// import butterknife.BindView;
// import butterknife.ButterKnife;
// import butterknife.Optional;

public class OrderDetailActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_PERMISSION_CALL = 992;
    public static String Warna;

    // Deklarasi Variabel View
    private LinearLayout bottomsheet;
    private TextView infopesanan;
    private TextView layanan;
    private TextView layanandesk;
    private LinearLayout llchat;
    private CircleImageView foto;
    private TextView pickUpText;
    private TextView destinationText;
    private TextView fiturtext;
    private TextView priceText;
    private TextView namamerchant;
    private RelativeLayout rlprogress;
    private TextView textprogress;
    private ImageView phone;
    private ImageView chat;
    private LinearLayout llchatmerchant;
    private LinearLayout lldestination;
    private LinearLayout lldetailsend;
    private TextView produk;
    private TextView sendername;
    private TextView receivername;
    private Button senderphone;
    private TextView Ongkir;
    private Button receiverphone;
    private NestedScrollView scrollView;
    private RatingView ratingView;
    private TextView cost;
    private LinearLayout llorderdetail;
    private LinearLayout llmerchantdetail;
    private LinearLayout llmerchantinfo;
    private ShimmerFrameLayout shimmerlayanan;
    private ShimmerFrameLayout shimmerpickup;
    private ShimmerFrameLayout shimmerdestination;
    private ShimmerFrameLayout shimmerfitur;
    private ShimmerFrameLayout shimmerprice;
    private RecyclerView rvmerchantnear;

    String idtrans, idpelanggan, response, fitur;
    ItemPesananItem itemPesananItem;
    TextView totaltext;
    private GoogleApiClient googleApiClient;
    private LatLng pickUpLatLng;
    private LatLng destinationLatLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        // Hapus ButterKnife.bind(this);

        // Inisialisasi Views menggunakan findViewById
        bottomsheet = findViewById(R.id.bottom_sheet);
        infopesanan = findViewById(R.id.DetailPesanan);
        layanan = findViewById(R.id.layanan);
        layanandesk = findViewById(R.id.layanandes);
        llchat = findViewById(R.id.llchat);
        foto = findViewById(R.id.background);
        pickUpText = findViewById(R.id.pickUpText);
        destinationText = findViewById(R.id.destinationText);
        fiturtext = findViewById(R.id.fitur);
        priceText = findViewById(R.id.price);
        namamerchant = findViewById(R.id.namamerchant);
        rlprogress = findViewById(R.id.rlprogress);
        textprogress = findViewById(R.id.textprogress);
        phone = findViewById(R.id.phonenumber);
        chat = findViewById(R.id.chat);
        llchatmerchant = findViewById(R.id.llchatmerchant);
        lldestination = findViewById(R.id.lldestination);
        lldetailsend = findViewById(R.id.senddetail);
        produk = findViewById(R.id.produk);
        sendername = findViewById(R.id.sendername);
        receivername = findViewById(R.id.receivername);
        senderphone = findViewById(R.id.senderphone);
        Ongkir = findViewById(R.id.txtongkir);
        receiverphone = findViewById(R.id.receiverphone);
        scrollView = findViewById(R.id.scroller);
        ratingView = findViewById(R.id.ratingView);
        cost = findViewById(R.id.cost);
        llorderdetail = findViewById(R.id.orderdetail);
        llmerchantdetail = findViewById(R.id.merchantdetail);
        llmerchantinfo = findViewById(R.id.merchantinfo);
        shimmerlayanan = findViewById(R.id.shimmerlayanan);
        shimmerpickup = findViewById(R.id.shimmerpickup);
        shimmerdestination = findViewById(R.id.shimmerdestination);
        shimmerfitur = findViewById(R.id.shimmerfitur);
        shimmerprice = findViewById(R.id.shimmerprice);
        rvmerchantnear = findViewById(R.id.merchantnear);
        totaltext = findViewById(R.id.totaltext);

        ratingView.setVisibility(View.VISIBLE);
        llchat.setVisibility(View.GONE);
        llchatmerchant.setVisibility(View.GONE);

        shimmerload();
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        idpelanggan = intent.getStringExtra("id_pelanggan");
        idtrans = intent.getStringExtra("id_transaksi");
        response = intent.getStringExtra("response");
        if (Objects.equals(response, "2")) {
            llchat.setVisibility(View.VISIBLE);
            layanandesk.setText(getString(R.string.notification_accept));
        } else if (Objects.equals(response, "3")) {
            llchat.setVisibility(View.VISIBLE);
            layanandesk.setText(getString(R.string.notification_start));
        } else if (Objects.equals(response, "4")) {
            scrollView.setPadding(0, 0, 0, 10);
            llchat.setVisibility(View.GONE);
            layanandesk.setText(getString(R.string.notification_finish));
        } else if (Objects.equals(response, "5")) {
            scrollView.setPadding(0, 0, 0, 10);
            llchat.setVisibility(View.GONE);
            layanandesk.setText(getString(R.string.notification_cancel));
        }
        Warna = "#1AC463";
    }

    private void getData(final String idtrans, final String idpelanggan) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (loginUser == null) return;
        DriverService service = ServiceGenerator.createService(DriverService.class, loginUser.getEmail(), loginUser.getPassword());
        DetailRequestJson param = new DetailRequestJson();
        param.setId(idtrans);
        param.setIdPelanggan(idpelanggan);
        service.detailtrans(param).enqueue(new Callback<DetailTransResponseJson>() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<DetailTransResponseJson> call, @NonNull Response<DetailTransResponseJson> responsedata) {
                if (responsedata.isSuccessful() && responsedata.body() != null) {
                    shimmertutup();
                    if (responsedata.body().getData() == null || responsedata.body().getData().isEmpty()
                            || responsedata.body().getPelanggan() == null || responsedata.body().getPelanggan().isEmpty()) {
                        Toast.makeText(OrderDetailActivity.this, "Data detail pesanan kosong dari server.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.e("", String.valueOf(responsedata.body().getData().get(0)));
                    final TransaksiModel transaksi = responsedata.body().getData().get(0);
                    PelangganModel pelanggan = responsedata.body().getPelanggan().get(0);
                    pickUpLatLng = new LatLng(transaksi.getStartLatitude(), transaksi.getStartLongitude());
                    destinationLatLng = new LatLng(transaksi.getEndLatitude(), transaksi.getEndLongitude());
                    infopesanan.setText("Detail Pesanan #" + transaksi.getId());
                    fitur = transaksi.getOrderFitur();
                    Utility.currencyTXT(Ongkir, String.valueOf(transaksi.getHarga()), OrderDetailActivity.this);
                    switch (transaksi.getHome()) {
                        case "3":
                            lldestination.setVisibility(View.GONE);
                            fiturtext.setText(transaksi.getEstimasi());
                            break;
                        case "4":
                            llorderdetail.setVisibility(View.VISIBLE);
                            llmerchantdetail.setVisibility(View.VISIBLE);
                            llmerchantinfo.setVisibility(View.VISIBLE);
                            Utility.currencyTXT(cost, String.valueOf(transaksi.getTotal_biaya()), OrderDetailActivity.this);
                            namamerchant.setText(transaksi.getNama_merchant());
                            itemPesananItem = new ItemPesananItem(responsedata.body().getItem(), R.layout.item_pesanan);
                            rvmerchantnear.setAdapter(itemPesananItem);
                            break;
                        case "2":
                            lldetailsend.setVisibility(View.VISIBLE);
                            produk.setText(transaksi.getNamaBarang());
                            sendername.setText(transaksi.namaPengirim);
                            receivername.setText(transaksi.namaPenerima);

                            senderphone.setOnClickListener(v -> {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Pengirim");
                                alertDialogBuilder.setMessage("Anda ingin menghubungi " + transaksi.getNamaPengirim() + " (" + transaksi.teleponPengirim + ")?");
                                alertDialogBuilder.setPositiveButton("Ya", (arg0, arg1) -> {
                                    if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                        return;
                                    }
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPengirim));
                                    startActivity(callIntent);
                                });
                                alertDialogBuilder.setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
                                alertDialogBuilder.create().show();
                            });

                            receiverphone.setOnClickListener(v -> {
                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderDetailActivity.this, R.style.DialogStyle);
                                alertDialogBuilder.setTitle("Call Penerima");
                                alertDialogBuilder.setMessage("Anda ingin menghubungi " + transaksi.getNamaPenerima() + " (" + transaksi.teleponPenerima + ")?");
                                alertDialogBuilder.setPositiveButton("Ya", (arg0, arg1) -> {
                                    if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_CALL);
                                        return;
                                    }
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + transaksi.teleponPenerima));
                                    startActivity(callIntent);
                                });
                                alertDialogBuilder.setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
                                alertDialogBuilder.create().show();
                            });
                            break;
                    }
                    parsedata(transaksi, pelanggan);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailTransResponseJson> call, @NonNull Throwable t) {
                // Handle failure
            }
        });
    }

    private void shimmerload() {
        shimmerlayanan.startShimmer();
        shimmerpickup.startShimmer();
        shimmerdestination.startShimmer();
        shimmerfitur.startShimmer();
        shimmerprice.startShimmer();

        layanan.setVisibility(View.GONE);
        layanandesk.setVisibility(View.GONE);
        pickUpText.setVisibility(View.GONE);
        destinationText.setVisibility(View.GONE);
        fiturtext.setVisibility(View.GONE);
        priceText.setVisibility(View.GONE);
    }

    private void shimmertutup() {
        shimmerlayanan.stopShimmer();
        shimmerpickup.stopShimmer();
        shimmerdestination.stopShimmer();
        shimmerfitur.stopShimmer();
        shimmerprice.stopShimmer();

        shimmerlayanan.setVisibility(View.GONE);
        shimmerpickup.setVisibility(View.GONE);
        shimmerdestination.setVisibility(View.GONE);
        shimmerfitur.setVisibility(View.GONE);
        shimmerprice.setVisibility(View.GONE);

        layanan.setVisibility(View.VISIBLE);
        layanandesk.setVisibility(View.VISIBLE);
        pickUpText.setVisibility(View.VISIBLE);
        destinationText.setVisibility(View.VISIBLE);
        fiturtext.setVisibility(View.VISIBLE);
        priceText.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void parsedata(TransaksiModel request, final PelangganModel pelanggan) {
        rlprogress.setVisibility(View.GONE);
        pickUpLatLng = new LatLng(request.getStartLatitude(), request.getStartLongitude());
        destinationLatLng = new LatLng(request.getEndLatitude(), request.getEndLongitude());
        Picasso.get()
                .load(Constants.IMAGESUSER + pelanggan.getFoto())
                .transform(new BackgroundColorTransform(ContextCompat.getColor(getApplicationContext(), R.color.white)))
                .placeholder(R.drawable.nocamera)
                .error(R.drawable.nocamera)
                .into(foto);

        totaltext.setText(request.isPakaiWallet() ? "Pakai Saldo" : "Bayar Ditempat");

        if (request.getRate() != null && !request.getRate().isEmpty()) {
            ratingView.setRating(Float.parseFloat(request.getRate()));
        }

        layanan.setText(pelanggan.getFullnama());
        pickUpText.setText(request.getAlamatAsal());
        destinationText.setText(request.getAlamatTujuan());
        if ("4".equals(request.getHome())) {
            double totalbiaya = Double.parseDouble(request.getTotal_biaya());
            Utility.currencyTXT(priceText, String.valueOf(request.getHarga() + totalbiaya), this);
        } else {
            Utility.currencyTXT(priceText, String.valueOf(request.getHarga()), this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(idtrans, idpelanggan);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Handle connection
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Handle suspension
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Handle failed connection
    }
}