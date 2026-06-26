package com.rcdriver.cs.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rcdriver.cs.adapter.ListDriverClick;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.FiturPromoAdapter;
import com.rcdriver.cs.adapter.ListDriverAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.gmap.directions.Directions;
import com.rcdriver.cs.gmap.directions.Route;
import com.rcdriver.cs.json.CheckStatusTransaksiRequest;
import com.rcdriver.cs.json.CheckStatusTransaksiResponse;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.GetNearRideCarRequestJson;
import com.rcdriver.cs.json.GetNearRideCarResponseJson;
import com.rcdriver.cs.json.PromoRequestJson;
import com.rcdriver.cs.json.PromoResponseJson;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.json.SendRequestJson;
import com.rcdriver.cs.json.SendResponseJson;
import com.rcdriver.cs.json.fcm.DriverRequest;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.TransaksiSendModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.models.VoucherModel;
import com.rcdriver.cs.utils.NetworkManager;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.MapDirectionAPI;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.BookService;
import com.rcdriver.cs.utils.api.service.UserService;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.text.InputFilter;
import android.text.Spanned;

import static com.rcdriver.cs.json.fcm.FCMType.ORDER;
import static com.rcdriver.cs.utils.Utility.fixPembulatan;
public class SendNewActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String FITUR_KEY = "FiturKey";
    private static final String TAG = "SendNewActivity";
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    Context context = SendNewActivity.this;
    private boolean isMapReady = false;
    Thread thread;
    boolean threadRun = true;
    TransaksiSendModel transaksi;
    CoordinatorLayout rootLayout;

    LinearLayout setPickUpContainer;

    LinearLayout setDestinationContainer;

    Button setPickUpButton;

    Button setDestinationButton;

    TextView fiturtext;

    ImageView backbtn;

    LinearLayout bottomsheet;

    RelativeLayout rlprogress;

    TextView NameAsal;

    TextView pickUpText;

    TextView NameTujuan;

    TextView destinationText;

    TextView jarak;

    TextView estimasi;

    TextView cost;

    TextView numdiskon;

    TextView diskon;

    TextView priceText;

    Button btnOrder;

    Button dokument;
    Button fashion;
    Button box;
    Button other;

    EditText othertext;

    String itemdetail;

    EditText promokode;

    Button btnpromo;

    TextView promonotif;

    ImageButton checkedcash;

    ImageButton checkedwallet;

    TextView cashpayment;

    TextView walletpayment;

    RadioButton llcheckedwallet;

    RadioButton llcheckedcash;

    RelativeLayout rlnotif;

    TextView textnotif;

    EditText sendername;

    EditText recievername;

    EditText senderphone;

    EditText recieverphone;

    TextView saldotext;

    LinearLayout stepLayout2;

    LinearLayout stepLayout3;

    RecyclerView mDriverRec;

    ImageView closeDriver;

    //    @BindView(R.id.Step2)
//    LinearLayout StepLayout2;
    private Button submit;


    private String senderName = "";
    private String phoneNumber = "";
    private String receiverName = "";
    private String phoneNumberReceiver = "";

    private GoogleMap gMap;
    private GoogleApiClient googleApiClient;
    private Location lastKnownLocation;
    private LatLng pickUpLatLang;
    private LatLng destinationLatLang;
    private Polyline directionLine;
    private Marker pickUpMarker;
    private EditText senderNameEditText, senderPhoneEditText, receiverNameEditText, receiverPhoneEditText;
    private Marker destinationMarker;
    int FITURID = -1;
    double dLatitude = 0;
    double dLongitude = 0;
    private double Latitude,Longitude;
    private String fitur,ICONFITUR, NamaAlamat, NamaAsal, dAlamat, checkedpaywallet,
            biayaminimum, saldoWallet, getbiaya, biayaakhir, fiturdesc, icondriver;
    private SettingPreference sp;

    private ArrayList<DriverModel> pilihdriver;
    private List<Marker> driverMarkers;
    private ArrayList<DriverModel> driverAvailable;
    private FiturModel designedFitur;
    private Realm realm;

    private double mjarak;
    private long harga, promocode, maksimum;
    private double Radius;

    private List<VoucherModel> mItems = new ArrayList<>();
    private FiturPromoAdapter mAdapter;
    private ListDriverAdapter dAdapter;
    Handler handler;
    public static String Warna;
    private DriverRequest request;





    private final okhttp3.Callback updateRouteCallback = new okhttp3.Callback() {

        @Override
        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
            setDestinationContainer.setVisibility(View.VISIBLE);
            rlprogress.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(rootLayout, "error connection, please select destination again!", Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            snackbar.show();
        }

        @Override
        public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response.isSuccessful()) {

                final String json = Objects.requireNonNull(response.body()).string();

                final long distance = MapDirectionAPI.getDistance(SendNewActivity.this, json);
                final String time = MapDirectionAPI.getTimeDistance(SendNewActivity.this, json);
                if (distance >= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            sendername.setText(senderName);
                            senderphone.setText(phoneNumber);
                            recievername.setText(receiverName);
                            recieverphone.setText(phoneNumberReceiver);

                            String format = String.format(Locale.US, "%.0f", (double) distance / 1000f);
                            long dist = Long.parseLong(format);
                            float km = ((float) (distance)) / 1000f;
                            if (dist < maksimum) {
                                rlprogress.setVisibility(View.GONE);
                                promocode = 0;
                                updateLineDestination(json);
                                updateDistance(distance);
                                estimasi.setText(time);
                                numdiskon.setText(String.valueOf(promocode));
                                Utility.currencyDiskon(diskon, String.valueOf(promocode), SendNewActivity.this);
                                fetchNearDriver(pickUpLatLang);
                                btnOrder.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        {
                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    switch (which){
                                                        case DialogInterface.BUTTON_POSITIVE:
                                                            onOrderButton(true, "");
                                                            break;
                                                        case DialogInterface.BUTTON_NEGATIVE:
                                                            stepLayout2.setVisibility(View.GONE);
                                                            stepLayout3.setVisibility(View.VISIBLE);
                                                            driverterdekat(pickUpLatLang);
                                                            break;
                                                    }
                                                }
                                            };





                                            AlertDialog.Builder builder = new AlertDialog.Builder(SendNewActivity.this);
                                            builder.setMessage("PILIH METODE PEMESANAN")
                                                    .setPositiveButton("Otomatis", dialogClickListener)
                                                    .setNegativeButton("Pilih Driver", dialogClickListener).show();

                                        }





//                                        Intent intent = new Intent(context, DetailSendNewActivity.class);
//                                        intent.putExtra("distance", km);//double
//                                        intent.putExtra("price", getbiaya);//long
//                                        intent.putExtra("pickup_latlng", pickUpLatLang);
//                                        intent.putExtra("destination_latlng", destinationLatLang);
//                                        intent.putExtra("pickup", sp.getSetting()[8]);
//                                        intent.putExtra("destination", destinationText.getText().toString());
//                                        intent.putExtra("driver", driverAvailable);
//                                        intent.putExtra("biaya_minimum", biayaminimum);
//                                        intent.putExtra("time_distance", time);
//                                        intent.putExtra("driver", driverAvailable);
//                                        intent.putExtra("icon", ICONFITUR);
//                                        intent.putExtra("layanan", fiturtext.getText().toString());
//                                        intent.putExtra("layanandesk", designedFitur.getKeterangan());
//                                        intent.putExtra(FITUR_KEY, FITURID);
//                                        startActivity(intent);
//                                        finish();
                                    }
                                });
                            }
                            else {
                                rlprogress.setVisibility(View.GONE);
                                setDestinationContainer.setVisibility(View.VISIBLE);
                                Snackbar snackbar = Snackbar.make(rootLayout, "Jarak Tujuan Terlalu Jauh.", Snackbar.LENGTH_LONG);
                                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                                snackbar.show();

                            }
                        }
                    });
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_send_new);
        rootLayout = findViewById(R.id.rootLayout);
        setPickUpContainer = findViewById(R.id.pickUpContainer);
        setDestinationContainer = findViewById(R.id.destinationContainer);
        setPickUpButton = findViewById(R.id.pickUpButton);
        setDestinationButton = findViewById(R.id.destinationButton);
        fiturtext = findViewById(R.id.fiturtext);
        backbtn = findViewById(R.id.back_btn);
        bottomsheet = findViewById(R.id.bottom_sheet);
        rlprogress = findViewById(R.id.rlprogress);
        NameAsal = findViewById(R.id.NameAsal);
        pickUpText = findViewById(R.id.pickUpText);
        NameTujuan = findViewById(R.id.NameTujuan);
        destinationText = findViewById(R.id.destinationText);
        jarak = findViewById(R.id.distance);
        estimasi = findViewById(R.id.fitur);
        cost = findViewById(R.id.cost);
        numdiskon = findViewById(R.id.numdiskon);
        diskon = findViewById(R.id.diskon);
        priceText = findViewById(R.id.price);
        btnOrder = findViewById(R.id.order);
        dokument = findViewById(R.id.dokumen);
        fashion = findViewById(R.id.fashion);
        box = findViewById(R.id.box);
        other = findViewById(R.id.other);
        othertext = findViewById(R.id.otherdetail);
        promokode = findViewById(R.id.promocode);
        btnpromo = findViewById(R.id.btnpromo);
        promonotif = findViewById(R.id.promonotif);
        checkedcash = findViewById(R.id.checkedcash);
        checkedwallet = findViewById(R.id.checkedwallet);
        cashpayment = findViewById(R.id.cashPayment);
        walletpayment = findViewById(R.id.walletpayment);
        llcheckedwallet = findViewById(R.id.llcheckedwallet);
        llcheckedcash = findViewById(R.id.llcheckedcash);
        rlnotif = findViewById(R.id.rlnotif);
        textnotif = findViewById(R.id.textnotif);
        sendername = findViewById(R.id.sendername);
        recievername = findViewById(R.id.recievername);
        senderphone = findViewById(R.id.phonenumber);
        recieverphone = findViewById(R.id.phonenumberreceiever);
        saldotext = findViewById(R.id.saldo);
        stepLayout2 = findViewById(R.id.step2);
        stepLayout3 = findViewById(R.id.Step3);
        mDriverRec = findViewById(R.id.mDriverRec);
        closeDriver = findViewById(R.id.close_driver);



        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomsheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Places.initialize(getApplicationContext(), MainActivity.apikey);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), MainActivity.apikey);
        }


//----------batas dialog floating--------------------------
        submit = findViewById(R.id.submitt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogConfirm();
            }
        });
//----------batas dialog floating--------------------------



        Warna = "#4c84ff";
        setPickUpContainer.setVisibility(View.VISIBLE);
        setDestinationContainer.setVisibility(View.GONE);

        realm = Realm.getDefaultInstance();

        driverAvailable = new ArrayList<>();
        pilihdriver = new ArrayList<>();
        driverMarkers = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
//        get intent
        Intent intent = getIntent();
        FITURID = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");

//        set fitur
        if (FITURID != -1)
            designedFitur = realm.where(FiturModel.class).equalTo("idFitur", FITURID).findFirst();
        RealmResults<FiturModel> fiturs = realm.where(FiturModel.class).findAll();
        for (FiturModel fitur : fiturs) {
            Log.e("ID_FITUR", fitur.getIdFitur() + " " + fitur.getFitur() + " " + fitur.getBiayaAkhir() + " " + ICONFITUR);
        }
        fitur = String.valueOf(designedFitur.getIdFitur());
        getbiaya = String.valueOf(designedFitur.getBiaya());
        biayaminimum = String.valueOf(designedFitur.getBiaya_minimum());
        biayaakhir = String.valueOf(designedFitur.getBiayaAkhir());
        icondriver = designedFitur.getIcon_driver();
        maksimum = Long.parseLong(designedFitur.getMaksimumdist());
        Radius = Double.parseDouble(designedFitur.getMaksimumdist());
        fiturtext.setText(designedFitur.getFitur());
        fiturdesc = designedFitur.getKeterangan();
        updateFitur();





        setPickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickUp();
            }
        });

        setDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestination();
            }
        });

        pickUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(OjekNewActivity.this, ActivityAmbil.class);
//                i.putExtra("Lat",Latitude);
//                i.putExtra("Lng",Longitude);
//                i.putExtra("radius",Radius);
//                i.putExtra(ActivityAmbil.FORM_VIEW_INDICATOR, 1);
//                startActivityForResult(i, 1);
                setPickUpContainer.setVisibility(View.VISIBLE);
                setDestinationContainer.setVisibility(View.GONE);
                openAutocompleteActivity(1);
            }
        });
        destinationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(OjekNewActivity.this, ActivityTujuan.class);
//                i.putExtra("Lat",Latitude);
//                i.putExtra("Lng",Longitude);
//                i.putExtra("radius",Radius);
//                i.putExtra(ActivityTujuan.FORM_VIEW_INDICATOR, 2);
//                startActivityForResult(i, 2);
                setDestinationContainer.setVisibility(View.VISIBLE);
                setPickUpContainer.setVisibility(View.GONE);
                openAutocompleteActivity(2);
            }
        });


//        set current location
        sp = new SettingPreference(this);
        Latitude = Double.parseDouble(sp.getSetting()[6]);
        Latitude = Double.parseDouble(sp.getSetting()[7]);
        double picklat = Double.parseDouble(sp.getSetting()[6]);
        double picklng = Double.parseDouble(sp.getSetting()[7]);

        pickUpLatLang = new LatLng(picklat, picklng);
        pickUpText.setText(sp.getSetting()[8]);

        fetchNearDriver(pickUpLatLang);
//        destinationLatLang = new LatLng(dLatitude,dLongitude);
//        destinationText.setText(dAlamat);
//        if(dLatitude == 0 || dLatitude == 0){
//            notif("Alamat Tujuan Tidak Tersedia.");
//            finish();
//        }else{
//
//            try {
//                MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        dokument.setSelected(true);
        fashion.setSelected(false);
        box.setSelected(false);
        other.setSelected(false);
        itemdetail = "document";
        othertext.setVisibility(View.GONE);

        dokument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokument.setSelected(true);
                fashion.setSelected(false);
                box.setSelected(false);
                other.setSelected(false);
                itemdetail = "document";
                othertext.setVisibility(View.GONE);
                othertext.setText("");
            }
        });

        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokument.setSelected(false);
                fashion.setSelected(true);
                box.setSelected(false);
                other.setSelected(false);
                itemdetail = "fashion";
                othertext.setVisibility(View.GONE);
                othertext.setText("");
            }
        });

        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokument.setSelected(false);
                fashion.setSelected(false);
                box.setSelected(true);
                other.setSelected(false);
                itemdetail = "box";
                othertext.setVisibility(View.GONE);
                othertext.setText("");
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokument.setSelected(false);
                fashion.setSelected(false);
                box.setSelected(false);
                other.setSelected(true);
                othertext.setVisibility(View.VISIBLE);
            }
        });

        btnpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                    if (promokode.getText().toString().isEmpty()) {
                        notif("Promo code cant be empty!");
                    } else {
                        promokodedata();
                    }
                    promokode.getText().clear();
                    sukses("Promo Berhasil Digunakan.");
                } catch (Exception ignored) {

                }

            }
        });

        closeDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepLayout3.setVisibility(View.GONE);
                stepLayout2.setVisibility(View.VISIBLE);
            }
        });

//        btnOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

//----------batas awal dialog floating---------------

    private void openDialogConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_send, null);
        builder.setView(dialogView);
        builder.setCancelable(true);

        ImageView close = dialogView.findViewById(R.id.imageView8);

        Button saveButton = dialogView.findViewById(R.id.button1);
        AlertDialog alertDialog = builder.create();

        // Set nilai awal input dialog dengan nilai dari variabel global
        EditText senderNameEditText = dialogView.findViewById(R.id.sendername);
        senderNameEditText.setText(senderName);

        EditText phoneNumberEditText = dialogView.findViewById(R.id.phonenumber);
        phoneNumberEditText.setText(phoneNumber);
        phoneNumberEditText.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(10),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (dstart == 0 && source.toString().equals("0")) {
                            return "";
                        }
                        return null;
                    }
                }
        });





        EditText receiverNameEditText = dialogView.findViewById(R.id.recievername);
        receiverNameEditText.setText(receiverName);

        EditText phoneNumberReceiverEditText = dialogView.findViewById(R.id.phonenumberreceiever);
        phoneNumberReceiverEditText.setText(phoneNumberReceiver);
        phoneNumberReceiverEditText.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(10),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (dstart == 0 && source.toString().equals("0")) {
                            return "";
                        }
                        return null;
                    }
                }
        });






        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengambil data dari input dialog
                String newSenderName = senderNameEditText.getText().toString();
                String newPhoneNumber = phoneNumberEditText.getText().toString();
                String newReceiverName = receiverNameEditText.getText().toString();
                String newPhoneNumberReceiver = phoneNumberReceiverEditText.getText().toString();

                // Tutup dialog setelah menyimpan nilai teks
                alertDialog.dismiss();

                // Menyimpan data ke variabel global setelah dialog ditutup
                senderName = newSenderName;
                phoneNumber = newPhoneNumber;
                receiverName = newReceiverName;
                phoneNumberReceiver = newPhoneNumberReceiver;

                // Update tampilan atau melakukan tindakan lainnya dengan data yang diperbarui
                updateUIWithData();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogConfirm();
            }
        });

        alertDialog.show();
    }

    private void updateUIWithData() {

        sendername.setText(senderName);
        senderphone.setText(phoneNumber);
        recievername.setText(receiverName);
        recieverphone.setText(phoneNumberReceiver);
    }

//-----------batas dialog floating-------------





    public void sukses(String text) {
        String BGColor = Warna;
        rlnotif.setVisibility(View.VISIBLE);
        rlnotif.setBackgroundColor(Color.parseColor(BGColor));
        textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void onPickUp() {
        setDestinationContainer.setVisibility(View.VISIBLE);
        setPickUpContainer.setVisibility(View.GONE);
        if (pickUpMarker != null) pickUpMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        pickUpMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Pick Up")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup)));
        pickUpLatLang = centerPos;
        requestAddress(centerPos, pickUpText);
        fetchNearDriver(pickUpLatLang);
        requestRoute();

    }

    private void onDestination() {

        if (destinationMarker != null) destinationMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        destinationMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Destination")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)));
        destinationLatLang = centerPos;
        requestAddress(centerPos, destinationText);
        requestRoute();

        setDestinationContainer.setVisibility(View.GONE);
        if (pickUpText.getText().toString().isEmpty()) {
            setPickUpContainer.setVisibility(View.VISIBLE);
        } else {
            setPickUpContainer.setVisibility(View.GONE);
        }

    }

    private void updateDistance(long distance) {
        checkedpaywallet = "0";
        Log.e("CHECKEDWALLET", checkedpaywallet);
        float km = ((float) (distance)) / 1000f;
        mjarak = km;
        String format = String.format(Locale.US, "%.1f", km);
        jarak.setText(format + "Km");
        String biaya = String.valueOf(biayaminimum);
        Log.e("Waduh", biaya);
        long biayaTotal = Utility.fixPembulatan((long) (Double.parseDouble(getbiaya) * km));
        if (biayaTotal < Double.parseDouble(biaya)) {
            this.harga = Long.parseLong(biaya);
            biayaTotal = Long.parseLong(biaya);
        }
        this.harga = biayaTotal;
        final long finalBiayaTotal = biayaTotal;
        Log.e("Kabeh", String.valueOf(biayaTotal));
        String totalbiaya = String.valueOf(finalBiayaTotal);
        Utility.currencyTXT(cost, totalbiaya, this);
        Utility.currencyTXT(priceText, totalbiaya, this);
        //  TampilPoint(finalBiayaTotal);
        long saldokini = Long.parseLong(saldoWallet);
        //opsi
        if (saldokini < (biayaTotal - (harga * Double.parseDouble(biayaakhir)))) {
            llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String totalbiaya = String.valueOf(finalBiayaTotal);
                    Utility.currencyTXT(priceText, totalbiaya, context);
                    Utility.currencyTXT(diskon, String.valueOf(promocode), SendNewActivity.this);
                    numdiskon.setText(String.valueOf(promocode));
                    checkedcash.setSelected(true);
                    checkedwallet.setSelected(false);
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
        } else {
            llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String totalbiaya = String.valueOf(finalBiayaTotal);
                    Utility.currencyTXT(priceText, totalbiaya, context);
                    Utility.currencyTXT(diskon, String.valueOf(promocode), SendNewActivity.this);
                    numdiskon.setText(String.valueOf(promocode));
                    checkedcash.setSelected(true);
                    checkedwallet.setSelected(false);
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    cashpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
            final long finalBiayaTotal1 = biayaTotal;
            llcheckedwallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                    long totalwallet = diskonwallet + promocode;
                    long kalkulasi = fixPembulatan(totalwallet);
                    Utility.currencyTXT(diskon, String.valueOf(kalkulasi), context);
                    numdiskon.setText(String.valueOf(kalkulasi));
                    String totalbiaya = String.valueOf(finalBiayaTotal1 - kalkulasi);
                    Utility.currencyTXT(priceText, totalbiaya, context);
                    checkedcash.setSelected(false);
                    checkedwallet.setSelected(true);
                    checkedpaywallet = "1";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    walletpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    cashpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
        }
        btnOrder.setVisibility(View.VISIBLE);
    }

    private void fetchNearDriver(LatLng latLng) {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(latLng.latitude);
        param.setLongitude(latLng.longitude);
        param.setFitur(fitur);
        param.setStatus("1");
        service.getNearRide(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    driverAvailable = Objects.requireNonNull(response.body()).getData();
                    createMarker();

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void updateFitur() {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        if (driverMarkers != null) {
            for (Marker m : driverMarkers) {
                m.remove();
            }
            driverMarkers.clear();
        }
    }

    private void notif(String pesan){
        Snackbar snackbar = Snackbar.make(rootLayout, pesan, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        snackbar.show();
    }

    private void updateLastLocation(boolean move) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }
        lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        gMap.setMyLocationEnabled(true);

        if (lastKnownLocation != null) {
            if (move) {
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 15f)
                );

//                gMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
            }
            LatLng mlatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            fetchNearDriver(mlatLng);
        }
    }

    private void updateLineDestination(String json) {
        Directions directions = new Directions(SendNewActivity.this);
        try {
            List<Route> routes = directions.parse(json);

            if (directionLine != null) directionLine.remove();
            if (routes.size() > 0) {
                directionLine = gMap.addPolyline((new PolylineOptions())
                        .addAll(routes.get(0).getOverviewPolyLine())
                        .color(ContextCompat.getColor(SendNewActivity.this, R.color.default_badge_background_color))
                        .width(8));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLastLocation(true);
    }

    @Override
    public void onConnectionSuspended(@NonNull int i) {
        updateLastLocation(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        updateLastLocation(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(true);

        isMapReady = true;
//        gMap.setOnMarkerClickListener(this);

        updateLastLocation(true);
    }

    Timer timer = new Timer();
    private final Runnable updateDriverRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(pickUpLatLang != null){
                            fetchNearDriver(pickUpLatLang);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (NetworkManager.isConnectToInternet(SendNewActivity.this)) {
                                try {
                                    if(pickUpLatLang != null){
                                        fetchNearDriver(pickUpLatLang);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, 0, 4000);
                }
            }).start();
        }
    };

    private void startIsDriver() {
        handler = new Handler();
        handler.postDelayed(updateDriverRunnable, 4000);
    }

    private void stopIsDriver() {
        handler.removeCallbacks(updateDriverRunnable);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
        startIsDriver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        User userLogin = BaseApp.getInstance(this).getLoginUser();
        if(userLogin.getWalletSaldo() == 0){
            saldoWallet = sp.getSetting()[5];
            Utility.currencyTXT(saldotext, saldoWallet, this);
        }else{
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
            Utility.currencyTXT(saldotext, saldoWallet, this);
        }
        startIsDriver();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        stopIsDriver();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
//                String addressasal = data.getStringExtra(ActivityAmbil.LOCATION_TITLE);
//                String addressset = data.getStringExtra(ActivityAmbil.LOCATION_NAME);
//                LatLng latLng = data.getParcelableExtra(ActivityAmbil.LOCATION_LATLNG);
//
//                pickUpLatLang = latLng;
//                pickUpText.setText(addressset);
//                NameAsal.setText(addressasal);
//                onPickUp(latLng);
//                try {
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
//                        }
//                    }, 5000);
//                    //CekKM(latLng,destinationLatLang);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    pickUpText.setText(place.getAddress());
                    LatLng latLng = place.getLatLng();
                    if (latLng != null) {
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(latLng.latitude, latLng.longitude), 15f)
                        );
                        onPickUp();
                    }
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
                }
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
//                String addressset = data.getStringExtra(ActivityTujuan.LOCATION_NAME);
//                LatLng latLng = data.getParcelableExtra(ActivityTujuan.LOCATION_LATLNG);
//                String NameStreet = data.getParcelableExtra(ActivityTujuan.LOCATION_TITLE);
//                destinationLatLang = latLng;
//                destinationText.setText(addressset);
//                NameTujuan.setText(NameStreet);
//                onDestination(destinationLatLang);
//                try {
//                    // CekKM(pickUpLatLang, latLng);
//                    StepLayout2.setVisibility(View.VISIBLE);
//                    MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
//
//                    Log.d("KlikTujuan",latLng.latitude + "," + latLng.longitude);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    destinationText.setText(place.getAddress());
                    LatLng latLng = place.getLatLng();
                    if (latLng != null) {
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(latLng.latitude, latLng.longitude), 15f)
                        );
                        onDestination();
                    }
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i(TAG, Objects.requireNonNull(status.getStatusMessage()));
                }
            }
        }
    }

//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        Log.e("MARKER", marker.getTitle().toString());
//        if(marker.getTitle().equalsIgnoreCase("Pick Up")){
//            Intent i = new Intent(OjekNewActivity.this, ActivitySetHome.class);
//            i.putExtra("Lat",Latitude);
//            i.putExtra("Lng",Longitude);
//            i.putExtra("radius",Radius);
//            i.putExtra(ActivityAmbil.FORM_VIEW_INDICATOR, 1);
//            startActivityForResult(i, 1);
//        }else if(marker.getTitle().equalsIgnoreCase("Destination")){
//            Intent i = new Intent(OjekNewActivity.this, ActivitySetHome.class);
//            i.putExtra("Lat",Latitude);
//            i.putExtra("Lng",Longitude);
//            i.putExtra("radius",Radius);
//            i.putExtra(ActivityTujuan.FORM_VIEW_INDICATOR, 2);
//            startActivityForResult(i, 2);
//        }
//
//        return true;
//    }

    private void createMarker() {
        if (!driverAvailable.isEmpty()) {
            for (Marker m : driverMarkers) {
                m.remove();
            }

            driverMarkers.clear();
            for (DriverModel driver : driverAvailable) {
                float nextFloat = (new Random().nextFloat() * 199.0f) - 0.045410156f;
                LatLng currentDriverPos = new LatLng(driver.getLatitude(), driver.getLongitude());

                if (icondriver.equals("1")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icmotor))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("2")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.carmap))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("3")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("4")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("5")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hatchback))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("6")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.suv))
                                    .anchor((float) 0.5, (float) 0.5)
                                    .rotation(Float.parseFloat(driver.getBearing()))
                                    .flat(true)
                            )
                    );
                } else if (icondriver.equals("7")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.van))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("8")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle))
                                    .rotation(nextFloat)));

                } else if (icondriver.equals("9")) {
                    driverMarkers.add(
                            gMap.addMarker(new MarkerOptions()
                                    .position(currentDriverPos)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bajaj))
                                    .rotation(nextFloat)));


                }
            }
        }
    }

    private void requestRoute() {
        if (pickUpLatLang != null && destinationLatLang != null) {
            rlprogress.setVisibility(View.VISIBLE);
//            textprogress.setText(getString(R.string.waiting_pleaseWait));
            MapDirectionAPI.getDirection(pickUpLatLang, destinationLatLang).enqueue(updateRouteCallback);
        }
    }

    private void requestAddress(LatLng latlang, final TextView textView) {
        if (latlang != null) {
            MapDirectionAPI.getAddress(latlang).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull okhttp3.Call call, @NonNull final okhttp3.Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String json = Objects.requireNonNull(response.body()).string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject Jobject = new JSONObject(json);
                                    JSONArray Jarray = Jobject.getJSONArray("results");
                                    JSONObject userdata = Jarray.getJSONObject(0);
                                    String address = userdata.getString("formatted_address");
                                    textView.setText(address);
                                    Log.e("TESTER", userdata.getString("formatted_address"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void promokodedata() {
        btnpromo.setEnabled(false);
        btnpromo.setText("Wait...");
        final User user = BaseApp.getInstance(this).getLoginUser();
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(fitur);
        request.setCode(promokode.getText().toString());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(Call<PromoResponseJson> call, Response<PromoResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        if (response.body().getType().equals("persen")) {
                            promocode = Utility.fixPembulatan((Long.parseLong(response.body().getNominal()) * harga) / 100);
                        } else {
                            promocode = Utility.fixPembulatan(Long.parseLong(response.body().getNominal()));
                        }
                        Log.e("", String.valueOf(promocode));
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, SendNewActivity.this);
                            numdiskon.setText(diskontotal);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, SendNewActivity.this);
                            numdiskon.setText(diskontotal);
                        }
                    } else {
                        btnpromo.setEnabled(true);
                        btnpromo.setText("Use");
                        notif("promo code not available!");
                        promocode = 0;
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, SendNewActivity.this);
                            numdiskon.setText(diskontotal);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(priceText, totalbiaya, context);
                            Utility.currencyTXT(diskon, diskontotal, SendNewActivity.this);
                            numdiskon.setText(diskontotal);
                        }
                    }
                } else {
                    notif("error!");
                }
            }

            @Override
            public void onFailure(Call<PromoResponseJson> call, Throwable t) {
                t.printStackTrace();
                notif("error");
            }
        });
    }

    private void onOrderButton(boolean isAuto, String token) {
        if (checkedpaywallet.equals("1")) {
            if (driverAvailable.isEmpty()) {
                notif("Sorry, there are no drivers around you.");
            } else {
                rlprogress.setVisibility(View.VISIBLE);
                SendRequestJson param = new SendRequestJson();
                User userLogin = BaseApp.getInstance(this).getLoginUser();
                param.setIdPelanggan(userLogin.getId());
                param.setOrderFitur(String.valueOf(FITURID));
                param.setStartLatitude(pickUpLatLang.latitude);
                param.setStartLongitude(pickUpLatLang.longitude);
                param.setEndLatitude(destinationLatLang.latitude);
                param.setEndLongitude(destinationLatLang.longitude);
                param.setJarak(mjarak);
                param.setHarga(this.harga);
                param.setEstimasi(fiturtext.getText().toString());
                param.setKreditpromo(numdiskon.getText().toString());
                param.setAlamatAsal(pickUpText.getText().toString());
                param.setAlamatTujuan(destinationText.getText().toString());
                param.setPakaiWallet(1);
                param.setNamaPengirim(sendername.getText().toString());
                param.setTeleponPengirim("+62" + senderphone.getText().toString());
                param.setNamaPenerima(recievername.getText().toString());
                param.setTeleponPenerima("+62" + recieverphone.getText().toString());
                if (!othertext.getText().toString().isEmpty()) {
                    param.setNamaBarang(othertext.getText().toString());
                } else {
                    param.setNamaBarang(itemdetail);
                }

                sendRequestTransaksi(param, driverAvailable, isAuto, token);
            }
        }

        else {
            if (driverAvailable.isEmpty()) {
                notif("Sorry, there are no drivers around you.");
            } else {
                rlprogress.setVisibility(View.VISIBLE);
                SendRequestJson param = new SendRequestJson();
                User userLogin = BaseApp.getInstance(this).getLoginUser();
                param.setIdPelanggan(userLogin.getId());
                param.setOrderFitur(String.valueOf(FITURID));
                param.setStartLatitude(pickUpLatLang.latitude);
                param.setStartLongitude(pickUpLatLang.longitude);
                param.setEndLatitude(destinationLatLang.latitude);
                param.setEndLongitude(destinationLatLang.longitude);
                param.setJarak(mjarak);
                param.setHarga(this.harga);
                param.setEstimasi(fiturtext.getText().toString());
                param.setKreditpromo(numdiskon.getText().toString());
                param.setAlamatAsal(pickUpText.getText().toString());
                param.setAlamatTujuan(destinationText.getText().toString());
                param.setPakaiWallet(0);
                param.setNamaPengirim(sendername.getText().toString());
                param.setTeleponPengirim("+62" + senderphone.getText().toString());
                param.setNamaPenerima(recievername.getText().toString());
                param.setTeleponPenerima("+62" + recieverphone.getText().toString());
                if (!othertext.getText().toString().isEmpty()) {
                    param.setNamaBarang(othertext.getText().toString());
                } else {
                    param.setNamaBarang(itemdetail);
                }

                sendRequestTransaksi(param, driverAvailable, isAuto, token);
            }
        }
    }

    private void sendRequestTransaksi(SendRequestJson param, final List<DriverModel> driverList, boolean isAuto, String token) {
        rlprogress.setVisibility(View.VISIBLE);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        final BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());

        service.requestTransaksisend(param).enqueue(new Callback<SendResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<SendResponseJson> call, @NonNull Response<SendResponseJson> response) {
                if (response.isSuccessful()) {
                    buildDriverRequest(Objects.requireNonNull(response.body()));

                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(isAuto){

                                for (int i = 0; i < driverList.size(); i++) {
                                    Log.e("OrderDriver", "Jenis: " + driverList.get(i).getRegId());
                                    fcmBroadcast(i, driverList);
                                }
                            }else{
                                manualOrder(token);
                            }


                            try {
                                Thread.sleep(30000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (threadRun) {
                                CheckStatusTransaksiRequest param = new CheckStatusTransaksiRequest();
                                param.setIdTransaksi(transaksi.getId());
                                service.checkStatusTransaksi(param).enqueue(new Callback<CheckStatusTransaksiResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Response<CheckStatusTransaksiResponse> response) {
                                        if (response.isSuccessful()) {
                                            CheckStatusTransaksiResponse checkStatus = response.body();
                                            if (!Objects.requireNonNull(checkStatus).isStatus()) {
                                                notif("Driver not found!");
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        notif("Driver not found!");
                                                    }
                                                });

                                                rlprogress.setVisibility(View.GONE);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<CheckStatusTransaksiResponse> call, @NonNull Throwable t) {
                                        notif("Driver not found!");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                notif("Driver not found!");
                                                rlprogress.setVisibility(View.GONE);
                                            }
                                        });

                                        rlprogress.setVisibility(View.GONE);

                                    }
                                });
                            }

                        }
                    });
                    thread.start();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendResponseJson> call, @NonNull Throwable t) {
                t.printStackTrace();
                notif("Your account has a problem, please contact customer service!");
                rlprogress.setVisibility(View.GONE);
            }
        });
    }

    private void buildDriverRequest(SendResponseJson response) {
        transaksi = response.getData().get(0);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        if (request == null) {
            request = new DriverRequest();
            request.setIdTransaksi(transaksi.getId());
            request.setIdPelanggan(transaksi.getIdPelanggan());
            request.setRegIdPelanggan(loginUser.getToken());
            request.setOrderFitur(String.valueOf(FITURID));
            request.setStartLatitude(transaksi.getStartLatitude());
            request.setStartLongitude(transaksi.getStartLongitude());
            request.setEndLatitude(transaksi.getEndLatitude());
            request.setEndLongitude(transaksi.getEndLongitude());
            request.setJarak(transaksi.getJarak());
            request.setHarga(transaksi.getHarga());
            request.setWaktuOrder(transaksi.getWaktuOrder());
            request.setAlamatAsal(transaksi.getAlamatAsal());
            request.setAlamatTujuan(transaksi.getAlamatTujuan());
            request.setKodePromo(transaksi.getKodePromo());
            request.setKreditPromo(transaksi.getKreditPromo());
            request.setPakaiWallet(String.valueOf(transaksi.isPakaiWallet()));
            request.setEstimasi(transaksi.getEstimasi());
            request.setLayanan(fiturtext.getText().toString());
            request.setLayanandesc(designedFitur.getKeterangan());
            request.setIcon(ICONFITUR);
            request.setBiaya(cost.getText().toString());
            request.setDistance(String.valueOf(mjarak));


            String namaLengkap = String.format("%s", loginUser.getFullnama());
            request.setNamaPelanggan(namaLengkap);
            request.setTelepon(loginUser.getNoTelepon());
            request.setType(ORDER);
        }
    }

    private void fcmBroadcast(int index, List<DriverModel> driverList) {
        DriverModel driverToSend = driverList.get(index);
        request.setTime_accept(new Date().getTime() + "");
        final User login = BaseApp.getInstance(context).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(driverToSend.getRegId());
            param.setData(request);
            service.fcmnotif(param).enqueue(new Callback<FcmResponse>() {
                @Override
                public void onResponse(@NonNull Call<FcmResponse> call, @NonNull Response<FcmResponse> response) {
                    Log.e("TAGDRIVER", response.body().getMessage());
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

    private void manualOrder(String tokenDriver){
        final User login = BaseApp.getInstance(context).getLoginUser();
        if(login != null){
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(tokenDriver);
            param.setData(request);
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

    private void openAutocompleteActivity(int request_code) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, request_code);

    }

    private void driverterdekat(LatLng latLng) {
        if (driverAvailable != null) {
            driverAvailable.clear();
        }
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        BookService service = ServiceGenerator.createService(BookService.class, loginUser.getEmail(), loginUser.getPassword());
        GetNearRideCarRequestJson param = new GetNearRideCarRequestJson();
        param.setLatitude(latLng.latitude);
        param.setLongitude(latLng.longitude);
        param.setFitur(fitur);
        param.setStatus("1");
        service.driverTerdekat(param).enqueue(new Callback<GetNearRideCarResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Response<GetNearRideCarResponseJson> response) {
                if (response.isSuccessful()) {
                    driverAvailable = Objects.requireNonNull(response.body()).getData();
                    for (int i = 0; i < driverAvailable.size(); i++) {
                        if(driverAvailable.get(i).getStatus().equals("1")){
                            LinearLayoutManager layoutManager = new LinearLayoutManager(SendNewActivity.this, LinearLayoutManager.VERTICAL, false);
                            mDriverRec.setLayoutManager(layoutManager);
                            mDriverRec.setNestedScrollingEnabled(false);
                            dAdapter = new ListDriverAdapter(driverAvailable,SendNewActivity.this);
                            dAdapter.setOnItemClickListener(new ListDriverClick() {
                                @Override
                                public void onItemClick(DriverModel item) {
                                    onOrderButton(false, item.getRegId());
                                }
                            });
                            mDriverRec.setAdapter(dAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetNearRideCarResponseJson> call, @NonNull Throwable t) {

            }
        });
    }
}