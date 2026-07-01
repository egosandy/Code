package com.rcdriver.cs.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.FiturPromoAdapter;
import com.rcdriver.cs.adapter.ListDriverAdapter;
import com.rcdriver.cs.adapter.ListDriverClick;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.databinding.ActivitySendNewBinding; // <-- IMPORT BARU UNTUK VIEWBINDING
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

    // SEMUA @BindView DIHAPUS dan digantikan dengan satu objek binding
    private ActivitySendNewBinding binding;

    String itemdetail;

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
    private double Latitude, Longitude;
    private String fitur, ICONFITUR, NamaAlamat, NamaAsal, dAlamat, checkedpaywallet,
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
            binding.pickUpContainer.setVisibility(View.VISIBLE);
            binding.rlprogress.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(binding.rootLayout, "error connection, please select destination again!", Snackbar.LENGTH_LONG);
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
                            binding.sendername.setText(senderName);
                            binding.phonenumber.setText(phoneNumber);
                            binding.recievername.setText(receiverName);
                            binding.phonenumberreceiever.setText(phoneNumberReceiver);

                            String format = String.format(Locale.US, "%.0f", (double) distance / 1000f);
                            long dist = Long.parseLong(format);
                            float km = ((float) (distance)) / 1000f;
                            if (dist < maksimum) {
                                binding.rlprogress.setVisibility(View.GONE);
                                promocode = 0;
                                updateLineDestination(json);
                                updateDistance(distance);
                                binding.fitur.setText(time); // estimasi
                                binding.numdiskon.setText(String.valueOf(promocode));
                                Utility.currencyDiskon(binding.diskon, String.valueOf(promocode), SendNewActivity.this);
                                fetchNearDriver(pickUpLatLang);
                                binding.order.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        {
                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    switch (which) {
                                                        case DialogInterface.BUTTON_POSITIVE:
                                                            onOrderButton(true, "");
                                                            break;
                                                        case DialogInterface.BUTTON_NEGATIVE:
                                                            binding.step2.setVisibility(View.GONE);
                                                            binding.Step3.setVisibility(View.VISIBLE);
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
                                    }
                                });
                            } else {
                                binding.rlprogress.setVisibility(View.GONE);
                                binding.destinationContainer.setVisibility(View.VISIBLE);
                                Snackbar snackbar = Snackbar.make(binding.rootLayout, "Jarak Tujuan Terlalu Jauh.", Snackbar.LENGTH_LONG);
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
        // Inisialisasi ViewBinding
        binding = ActivitySendNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomSheetBehavior behavior = BottomSheetBehavior.from(binding.bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Places.initialize(getApplicationContext(), MainActivity.apikey);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), MainActivity.apikey);
        }

        submit = findViewById(R.id.submitt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogConfirm();
            }
        });

        Warna = "#4c84ff";
        binding.pickUpContainer.setVisibility(View.VISIBLE);
        binding.destinationContainer.setVisibility(View.GONE);

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

        Intent intent = getIntent();
        FITURID = intent.getIntExtra(FITUR_KEY, -1);
        ICONFITUR = intent.getStringExtra("icon");

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
        binding.fiturtext.setText(designedFitur.getFitur());
        fiturdesc = designedFitur.getKeterangan();
        updateFitur();

        binding.pickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickUp();
            }
        });

        binding.destinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestination();
            }
        });

        binding.pickUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.pickUpContainer.setVisibility(View.VISIBLE);
                binding.destinationContainer.setVisibility(View.GONE);
                openAutocompleteActivity(1);
            }
        });

        binding.destinationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.destinationContainer.setVisibility(View.VISIBLE);
                binding.pickUpContainer.setVisibility(View.GONE);
                openAutocompleteActivity(2);
            }
        });

        sp = new SettingPreference(this);
        Latitude = Double.parseDouble(sp.getSetting()[6]);
        Longitude = Double.parseDouble(sp.getSetting()[7]);
        double picklat = Double.parseDouble(sp.getSetting()[6]);
        double picklng = Double.parseDouble(sp.getSetting()[7]);

        pickUpLatLang = new LatLng(picklat, picklng);
        binding.pickUpText.setText(sp.getSetting()[8]);

        fetchNearDriver(pickUpLatLang);

        binding.dokumen.setSelected(true);
        binding.fashion.setSelected(false);
        binding.box.setSelected(false);
        binding.other.setSelected(false);
        itemdetail = "document";
        binding.otherdetail.setVisibility(View.GONE);

        binding.dokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.dokumen.setSelected(true);
                binding.fashion.setSelected(false);
                binding.box.setSelected(false);
                binding.other.setSelected(false);
                itemdetail = "document";
                binding.otherdetail.setVisibility(View.GONE);
                binding.otherdetail.setText("");
            }
        });

        binding.fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.dokumen.setSelected(false);
                binding.fashion.setSelected(true);
                binding.box.setSelected(false);
                binding.other.setSelected(false);
                itemdetail = "fashion";
                binding.otherdetail.setVisibility(View.GONE);
                binding.otherdetail.setText("");
            }
        });

        binding.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.dokumen.setSelected(false);
                binding.fashion.setSelected(false);
                binding.box.setSelected(true);
                binding.other.setSelected(false);
                itemdetail = "box";
                binding.otherdetail.setVisibility(View.GONE);
                binding.otherdetail.setText("");
            }
        });

        binding.other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.dokumen.setSelected(false);
                binding.fashion.setSelected(false);
                binding.box.setSelected(false);
                binding.other.setSelected(true);
                binding.otherdetail.setVisibility(View.VISIBLE);
            }
        });

        binding.btnpromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                    if (binding.promocode.getText().toString().isEmpty()) {
                        notif("Promo code cant be empty!");
                    } else {
                        promokodedata();
                    }
                    binding.promocode.getText().clear();
                    sukses("Promo Berhasil Digunakan.");
                } catch (Exception ignored) {

                }
            }
        });

        binding.closeDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.Step3.setVisibility(View.GONE);
                binding.step2.setVisibility(View.VISIBLE);
            }
        });
    }

    private void openDialogConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_send, null);
        builder.setView(dialogView);
        builder.setCancelable(true);

        ImageView close = dialogView.findViewById(R.id.imageView8);
        Button saveButton = dialogView.findViewById(R.id.button1);
        AlertDialog alertDialog = builder.create();

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
                String newSenderName = senderNameEditText.getText().toString();
                String newPhoneNumber = phoneNumberEditText.getText().toString();
                String newReceiverName = receiverNameEditText.getText().toString();
                String newPhoneNumberReceiver = phoneNumberReceiverEditText.getText().toString();

                alertDialog.dismiss();

                senderName = newSenderName;
                phoneNumber = newPhoneNumber;
                receiverName = newReceiverName;
                phoneNumberReceiver = newPhoneNumberReceiver;

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
        binding.sendername.setText(senderName);
        binding.phonenumber.setText(phoneNumber);
        binding.recievername.setText(receiverName);
        binding.phonenumberreceiever.setText(phoneNumberReceiver);
    }

    public void sukses(String text) {
        String BGColor = Warna;
        binding.rlnotif.setVisibility(View.VISIBLE);
        binding.rlnotif.setBackgroundColor(Color.parseColor(BGColor));
        binding.textnotif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                binding.rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    private void onPickUp() {
        binding.destinationContainer.setVisibility(View.VISIBLE);
        binding.pickUpContainer.setVisibility(View.GONE);
        if (pickUpMarker != null) pickUpMarker.remove();
        LatLng centerPos = gMap.getCameraPosition().target;
        pickUpMarker = gMap.addMarker(new MarkerOptions()
                .position(centerPos)
                .title("Pick Up")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup)));
        pickUpLatLang = centerPos;
        requestAddress(centerPos, binding.pickUpText);
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
        requestAddress(centerPos, binding.destinationText);
        requestRoute();

        binding.destinationContainer.setVisibility(View.GONE);
        if (binding.pickUpText.getText().toString().isEmpty()) {
            binding.pickUpContainer.setVisibility(View.VISIBLE);
        } else {
            binding.pickUpContainer.setVisibility(View.GONE);
        }
    }

    private void updateDistance(long distance) {
        checkedpaywallet = "0";
        Log.e("CHECKEDWALLET", checkedpaywallet);
        float km = ((float) (distance)) / 1000f;
        mjarak = km;
        String format = String.format(Locale.US, "%.1f", km);
        binding.distance.setText(format + "Km");
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
        Utility.currencyTXT(binding.cost, totalbiaya, this);
        Utility.currencyTXT(binding.price, totalbiaya, this);
        long saldokini = Long.parseLong(saldoWallet);

        if (saldokini < (biayaTotal - (harga * Double.parseDouble(biayaakhir)))) {
            binding.llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String totalbiaya = String.valueOf(finalBiayaTotal);
                    Utility.currencyTXT(binding.price, totalbiaya, context);
                    Utility.currencyTXT(binding.diskon, String.valueOf(promocode), SendNewActivity.this);
                    binding.numdiskon.setText(String.valueOf(promocode));
                    binding.checkedcash.setSelected(true);
                    binding.checkedwallet.setSelected(false);
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    binding.cashPayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    binding.walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        binding.checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
        } else {
            binding.llcheckedcash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String totalbiaya = String.valueOf(finalBiayaTotal);
                    Utility.currencyTXT(binding.price, totalbiaya, context);
                    Utility.currencyTXT(binding.diskon, String.valueOf(promocode), SendNewActivity.this);
                    binding.numdiskon.setText(String.valueOf(promocode));
                    binding.checkedcash.setSelected(true);
                    binding.checkedwallet.setSelected(false);
                    checkedpaywallet = "0";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    binding.cashPayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    binding.walletpayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        binding.checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
            final long finalBiayaTotal1 = biayaTotal;
            binding.llcheckedwallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                    long totalwallet = diskonwallet + promocode;
                    long kalkulasi = fixPembulatan(totalwallet);
                    Utility.currencyTXT(binding.diskon, String.valueOf(kalkulasi), context);
                    binding.numdiskon.setText(String.valueOf(kalkulasi));
                    String totalbiaya = String.valueOf(finalBiayaTotal1 - kalkulasi);
                    Utility.currencyTXT(binding.price, totalbiaya, context);
                    binding.checkedcash.setSelected(false);
                    binding.checkedwallet.setSelected(true);
                    checkedpaywallet = "1";
                    Log.e("CHECKEDWALLET", checkedpaywallet);
                    binding.walletpayment.setTextColor(getResources().getColor(R.color.colorgradient));
                    binding.cashPayment.setTextColor(getResources().getColor(R.color.gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.checkedwallet.setBackgroundTintList(getResources().getColorStateList(R.color.colorgradient));
                        binding.checkedcash.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    }
                }
            });
        }
        binding.order.setVisibility(View.VISIBLE);
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

    private void notif(String pesan) {
        Snackbar snackbar = Snackbar.make(binding.rootLayout, pesan, Snackbar.LENGTH_LONG);
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
                        if (pickUpLatLang != null) {
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
                                    if (pickUpLatLang != null) {
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
        if (userLogin.getWalletSaldo() == 0) {
            saldoWallet = sp.getSetting()[5];
            Utility.currencyTXT(binding.saldo, saldoWallet, this);
        } else {
            saldoWallet = String.valueOf(userLogin.getWalletSaldo());
            Utility.currencyTXT(binding.saldo, saldoWallet, this);
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
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.pickUpText.setText(place.getAddress());
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
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.destinationText.setText(place.getAddress());
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
            binding.rlprogress.setVisibility(View.VISIBLE);
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
        binding.btnpromo.setEnabled(false);
        binding.btnpromo.setText("Wait...");
        final User user = BaseApp.getInstance(this).getLoginUser();
        PromoRequestJson request = new PromoRequestJson();
        request.setFitur(fitur);
        request.setCode(binding.promocode.getText().toString());

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.promocode(request).enqueue(new Callback<PromoResponseJson>() {
            @Override
            public void onResponse(Call<PromoResponseJson> call, Response<PromoResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        binding.btnpromo.setEnabled(true);
                        binding.btnpromo.setText("Use");
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
                            Utility.currencyTXT(binding.price, totalbiaya, context);
                            Utility.currencyTXT(binding.diskon, diskontotal, SendNewActivity.this);
                            binding.numdiskon.setText(diskontotal);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(binding.price, totalbiaya, context);
                            Utility.currencyTXT(binding.diskon, diskontotal, SendNewActivity.this);
                            binding.numdiskon.setText(diskontotal);
                        }
                    } else {
                        binding.btnpromo.setEnabled(true);
                        binding.btnpromo.setText("Use");
                        notif("promo code not available!");
                        promocode = 0;
                        if (checkedpaywallet.equals("1")) {
                            long diskonwallet = (long) (Double.parseDouble(biayaakhir) * harga);
                            String diskontotal = String.valueOf(diskonwallet + promocode);
                            String totalbiaya = String.valueOf(harga - (diskonwallet + promocode));
                            Utility.currencyTXT(binding.price, totalbiaya, context);
                            Utility.currencyTXT(binding.diskon, diskontotal, SendNewActivity.this);
                            binding.numdiskon.setText(diskontotal);
                        } else {
                            String diskontotal = String.valueOf(promocode);
                            String totalbiaya = String.valueOf(harga - promocode);
                            Utility.currencyTXT(binding.price, totalbiaya, context);
                            Utility.currencyTXT(binding.diskon, diskontotal, SendNewActivity.this);
                            binding.numdiskon.setText(diskontotal);
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
                binding.rlprogress.setVisibility(View.VISIBLE);
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
                param.setEstimasi(binding.fiturtext.getText().toString());
                param.setKreditpromo(binding.numdiskon.getText().toString());
                param.setAlamatAsal(binding.pickUpText.getText().toString());
                param.setAlamatTujuan(binding.destinationText.getText().toString());
                param.setPakaiWallet(1);
                param.setNamaPengirim(binding.sendername.getText().toString());
                param.setTeleponPengirim("+62" + binding.phonenumber.getText().toString());
                param.setNamaPenerima(binding.recievername.getText().toString());
                param.setTeleponPenerima("+62" + binding.phonenumberreceiever.getText().toString());
                if (!binding.otherdetail.getText().toString().isEmpty()) {
                    param.setNamaBarang(binding.otherdetail.getText().toString());
                } else {
                    param.setNamaBarang(itemdetail);
                }

                sendRequestTransaksi(param, driverAvailable, isAuto, token);
            }
        } else {
            if (driverAvailable.isEmpty()) {
                notif("Sorry, there are no drivers around you.");
            } else {
                binding.rlprogress.setVisibility(View.VISIBLE);
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
                param.setEstimasi(binding.fiturtext.getText().toString());
                param.setKreditpromo(binding.numdiskon.getText().toString());
                param.setAlamatAsal(binding.pickUpText.getText().toString());
                param.setAlamatTujuan(binding.destinationText.getText().toString());
                param.setPakaiWallet(0);
                param.setNamaPengirim(binding.sendername.getText().toString());
                param.setTeleponPengirim("+62" + binding.phonenumber.getText().toString());
                param.setNamaPenerima(binding.recievername.getText().toString());
                param.setTeleponPenerima("+62" + binding.phonenumberreceiever.getText().toString());
                if (!binding.otherdetail.getText().toString().isEmpty()) {
                    param.setNamaBarang(binding.otherdetail.getText().toString());
                } else {
                    param.setNamaBarang(itemdetail);
                }

                sendRequestTransaksi(param, driverAvailable, isAuto, token);
            }
        }
    }

    private void sendRequestTransaksi(SendRequestJson param, final List<DriverModel> driverList, boolean isAuto, String token) {
        binding.rlprogress.setVisibility(View.VISIBLE);
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
                            if (isAuto) {
                                for (int i = 0; i < driverList.size(); i++) {
                                    Log.e("OrderDriver", "Jenis: " + driverList.get(i).getRegId());
                                    fcmBroadcast(i, driverList);
                                }
                            } else {
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
                                                binding.rlprogress.setVisibility(View.GONE);
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
                                                binding.rlprogress.setVisibility(View.GONE);
                                            }
                                        });
                                        binding.rlprogress.setVisibility(View.GONE);
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
                binding.rlprogress.setVisibility(View.GONE);
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
            request.setLayanan(binding.fiturtext.getText().toString());
            request.setLayanandesc(designedFitur.getKeterangan());
            request.setIcon(ICONFITUR);
            request.setBiaya(binding.cost.getText().toString());
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
        if (login != null) {
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

    private void manualOrder(String tokenDriver) {
        final User login = BaseApp.getInstance(context).getLoginUser();
        if (login != null) {
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
                        if (driverAvailable.get(i).getStatus().equals("1")) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(SendNewActivity.this, LinearLayoutManager.VERTICAL, false);
                            binding.mDriverRec.setLayoutManager(layoutManager);
                            binding.mDriverRec.setNestedScrollingEnabled(false);
                            dAdapter = new ListDriverAdapter(driverAvailable, SendNewActivity.this);
                            dAdapter.setOnItemClickListener(new ListDriverClick() {
                                @Override
                                public void onItemClick(DriverModel item) {
                                    onOrderButton(false, item.getRegId());
                                }
                            });
                            binding.mDriverRec.setAdapter(dAdapter);
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