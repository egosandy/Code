package com.rcdriver.cs.fragment;

import com.rcdriver.cs.utils.LocalStore;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.rcdriver.cs.activity.DonasiallActivity;

import com.rcdriver.cs.activity.MessageActivity;
import com.rcdriver.cs.activity.ProfileActivity;
import com.rcdriver.cs.activity.WalletActivity;
import com.rcdriver.cs.activity.payment.TopupSaldoActivity;

import com.rcdriver.cs.adapter.DonasiAdapter;
import com.rcdriver.cs.adapter.digi.KategoriAdapter;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.Donasi;
import com.rcdriver.cs.models.digi.Kategori;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.ActivitySetHome;
import com.rcdriver.cs.activity.AllBeritaActivity;
import com.rcdriver.cs.activity.IntroActivity;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.item.AllFiturItem;
import com.rcdriver.cs.item.BeritaItem;
import com.rcdriver.cs.item.CatMerchantItem;
import com.rcdriver.cs.item.FiturItem;
import com.rcdriver.cs.item.MerchantItem;
import com.rcdriver.cs.item.MerchantNearItem;
import com.rcdriver.cs.item.ProgressItem;
import com.rcdriver.cs.item.SliderItem;
import com.rcdriver.cs.json.FcmResponse;
import com.rcdriver.cs.json.GetHomeRequestJson;
import com.rcdriver.cs.json.GetHomeResponseJson;
import com.rcdriver.cs.json.GetProgressRequest;
import com.rcdriver.cs.json.GetProgressResponse;
import com.rcdriver.cs.json.ResponseJson;
import com.rcdriver.cs.json.SaveLokasiRequest;
import com.rcdriver.cs.json.SaveLokasiResponse;
import com.rcdriver.cs.json.SendFcmRequest;
import com.rcdriver.cs.json.UpdateTokenRequestJson;

import com.rcdriver.cs.midtrans.models.MidtrxModels;
import com.rcdriver.cs.models.AllFiturModel;
import com.rcdriver.cs.models.FiturDataModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.MerchantModel;
import com.rcdriver.cs.models.MerchantNearModel;
import com.rcdriver.cs.models.Notif;
import com.rcdriver.cs.models.ProgressModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.ppob.json.CekHistoriResponse;
import com.rcdriver.cs.ppob.json.CekRequest;
import com.rcdriver.cs.ppob.json.CekResponse;
import com.rcdriver.cs.ppob.model.StatusModels;
import com.rcdriver.cs.utils.GPSTracker;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.NetworkManager;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.Utility;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import com.onurciner.toastox.ToastOXDialog;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//midtrans


public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static String Warna;
    Timer timer = new Timer();
    private GPSTracker gps;


    //end ppob
    private ImageView Notifikasi;
    public static Location mLastLocation;
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_PERMISSION_LOCATION = 991;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationRequest mLocationRequest;
    private final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    int currentPage = 0;
    private Context context;
    private Handler handler;
    private ViewPager viewPager, donasiViewPager;
    private SliderItem adapter;
    private Integer[] colors = null;
    private RecyclerView rvCategory;
    private RecyclerView rvberita;
    private RecyclerView rvreview;
    private RecyclerView rvmerchantnear;

    //    private RecyclerView rvppob;
    private RelativeLayout llslider;
    private LinearLayout llrating;
    private LinearLayout llberita;

    //    private LinearLayout llppob;
    private LinearLayout lldonasi, message;
    private FiturItem fiturItem;
    private ProgressItem progressItem;
    private BeritaItem beritaItem;
    private MerchantItem merchantItem;
    private MerchantNearItem merchantNearItem;
    private KategoriAdapter ppobAdapter;
    private ShimmerFrameLayout shimberita, shimppob, shimmerdonasi;
    private TextView saldo;
    private TextView nodatanear;
    private TextView alamatku;
    private TextView textDonasiEmpty;
    private HorizontalScrollView alamatscroll;
    private SettingPreference sp;
    private List<Kategori> ppobMenuList = new ArrayList<>();
    private List<Kategori> ppoblist = new ArrayList<>();
    private List<Donasi> donasiList = new ArrayList<>();
    private List<MerchantModel> click;
    private List<MerchantNearModel> clicknear;
    private ArrayList<FiturDataModel> fiturlist;
    private List<FiturModel> fiturdata;
    private List<AllFiturModel> allfiturdata;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView nodatapromo;


    private RelativeLayout promo, transfer, pindahdonasi;

    private ImageView topup, Ikon;

    private CatMerchantItem catMerchantItem;
    private LatLng LokasiSaya;
    private ShimmerFrameLayout mShimmerCat, shimerPromo, shimerreview, shimmerchantpromo, getShimmerchantnear;
    //Digital
    private RelativeLayout digital, ppob, profil;
    private DonasiAdapter donasiAdapter;
    private CircleIndicator circleIndicator;
    Timer timer_donasi;
    int currentDonasi = 0;
    final long DELAY_MS_SLIDER = 1500;
    final long PERIOD_MS_SLIDER = 6000;
    List<ProgressModel> TransaksiList;

    @SuppressLint({"ClickableViewAccessibility", "MissingPermission"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_home, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = getContext();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
        }
        View bottom_sheet = getView.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        llberita = getView.findViewById(R.id.llnews);
        llslider = getView.findViewById(R.id.llslider);
        viewPager = getView.findViewById(R.id.viewPager);
        donasiViewPager = getView.findViewById(R.id.viewPager2);
        circleIndicator = getView.findViewById(R.id.circle);
        rvCategory = getView.findViewById(R.id.category);
        rvberita = getView.findViewById(R.id.berita);
        rvreview = getView.findViewById(R.id.viewPagerreview);
        saldo = getView.findViewById(R.id.txtSaldo);
        topup = getView.findViewById(R.id.topup);
        pindahdonasi = getView.findViewById(R.id.pindah);
        profil = getView.findViewById(R.id.profil);
        message = getView.findViewById(R.id.pesan);
        Notifikasi = getView.findViewById(R.id.Notif);
        llberita = getView.findViewById(R.id.llnews);
        llrating = getView.findViewById(R.id.llrating);

        RelativeLayout showall = getView.findViewById(R.id.showall);
        rvmerchantnear = getView.findViewById(R.id.merchantnear);
        nodatanear = getView.findViewById(R.id.nodatanear);
        sp = new SettingPreference(context);
        promo = getView.findViewById(R.id.promo);
        fiturlist = new ArrayList<>();
        transfer = getView.findViewById(R.id.transfer);

        Notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Firemsg", sp.getSetting()[9]);
                Spanned Pesan = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Pesan = HtmlCompat.fromHtml(sp.getSetting()[9], HtmlCompat.FROM_HTML_MODE_COMPACT);
                } else {
                    Pesan = HtmlCompat.fromHtml(sp.getSetting()[9], 0);
                }

                if (sp.getSetting()[9].equals("Null") || sp.getSetting()[10].equals("Null")) {
                    new ToastOXDialog.Build(getContext())
                            .setTitle("Notifikasi")
                            .setContent("Tidak Ada Notifikasi.")
                            .setPositiveText("Tutup")
                            .setPositiveBackgroundColorResource(R.color.colorPrimaryDark)
                            .setPositiveTextColorResource(R.color.white)
                            .onPositive(new ToastOXDialog.ButtonCallback() {
                                @Override
                                public void onClick(@NonNull ToastOXDialog toastOXDialog) {
                                    Log.i("Click", "Yes");
                                    toastOXDialog.dismiss();
                                }
                            }).show();
                } else {
                    new ToastOXDialog.Build(getContext())
                            .setTitle(sp.getSetting()[10])
                            .setContent(Pesan)
                            .setPositiveText("Tutup")
                            .setPositiveBackgroundColorResource(R.color.colorPrimaryDark)
                            .setPositiveTextColorResource(R.color.white)
                            .onPositive(new ToastOXDialog.ButtonCallback() {
                                @Override
                                public void onClick(@NonNull ToastOXDialog toastOXDialog) {
                                    Log.i("Click", "Yes");
                                    toastOXDialog.dismiss();
                                }
                            }).show();
                }
            }
        });


        Display display = Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth / 300);
        mLayoutManager = new GridLayoutManager(getActivity(), 4);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(mLayoutManager);

        shimerreview = getView.findViewById(R.id.shimreview);
        rvberita.setHasFixedSize(true);
        rvberita.setNestedScrollingEnabled(false);
        rvberita.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvreview.setHasFixedSize(true);
        rvreview.setNestedScrollingEnabled(false);
        rvreview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        Integer[] colors_temp = {
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent)
        };

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TopupSaldoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MessageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WalletActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AllBeritaActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
            settingsrequest();
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        gps = new GPSTracker(context);
        colors = colors_temp;


        //midtrans

        return getView;
    }


    //-----------------------------------------------------------------------------
    private void shimmershow() {
        rvCategory.setVisibility(View.GONE);
        rvreview.setVisibility(View.GONE);
        rvberita.setVisibility(View.GONE);
    }

    private void shimmertutup() {
        rvreview.setVisibility(View.VISIBLE);
        rvCategory.setVisibility(View.VISIBLE);
        rvberita.setVisibility(View.VISIBLE);
        shimmerdonasi.setVisibility(View.GONE);
    }

    private void gethome(final LatLng location) {
        User login = BaseApp.getInstance(context).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, login.getNoTelepon(), login.getPassword());
        GetHomeRequestJson param = new GetHomeRequestJson();
        param.setId(login.getId());
        param.setLat(String.valueOf(location.latitude));
        param.setLon(String.valueOf(location.longitude));
        param.setPhone(login.getNoTelepon());
        userService.home(param).enqueue(new Callback<GetHomeResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetHomeResponseJson> call, @NonNull Response<GetHomeResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {

                        sp.updateCurrency(response.body().getCurrency());
                        sp.updateabout(response.body().getAboutus());
                        sp.updateemail(response.body().getEmail());
                        sp.updatephone(response.body().getPhone());
                        sp.updateweb(response.body().getWebsite());
                        sp.updateSaldo(response.body().getSaldo());
                        sp.updateMinTransfer(response.body().getMinTransfer());
                        sp.updateMinWallet(response.body().getMinWallet());
                        sp.updateWasap(response.body().getSenderWasap());
                        Utility.currencyTXT(saldo, response.body().getSaldo(), context);
                        if (response.body().getSlider().isEmpty()) {
                            llslider.setVisibility(View.GONE);
                        } else {
                            adapter = new SliderItem(response.body().getSlider(), getActivity());
                            viewPager.setAdapter(adapter);
                            viewPager.setCurrentItem(0);
                            viewPager.setOffscreenPageLimit(0);
                            viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getApplicationContext().getResources().getDisplayMetrics()));
                            viewPager.setPadding(0, 0, 180, 0);
                            viewPager.setPageMargin(30);
                            viewPager.setClipToPadding(false);
                        }

                        fiturlist.clear();
                        fiturdata = response.body().getFitur();
                        allfiturdata = response.body().getAllfitur();
                        for (int i = 0; i < fiturdata.size(); i++) {
                            FiturDataModel fiturmodel = new FiturDataModel();
                            fiturmodel.setIdFitur(fiturdata.get(i).getIdFitur());
                            fiturmodel.setFitur(fiturdata.get(i).getFitur());
                            fiturmodel.setBackground(fiturdata.get(i).getBackground());
                            fiturmodel.setIcon(fiturdata.get(i).getIcon());
                            fiturmodel.setHome(fiturdata.get(i).getHome());
                            fiturmodel.setIsPending(fiturdata.get(i).getIsPending());
                            fiturlist.add(fiturmodel);
                        }

                        if (fiturdata.size() > 10) {
                            FiturDataModel fiturmodel = new FiturDataModel();
                            fiturmodel.setIdFitur(100);
                            fiturmodel.setFitur("Lainnya");
                            fiturmodel.setHome("0");
                            fiturlist.add(fiturmodel);
                        }
                        fiturItem = new FiturItem(getActivity(), fiturlist, R.layout.item_fitur, new FiturItem.OnItemClickListener() {
                            @Override
                            public void onItemClick(FiturDataModel item) {
                                sheetlist();
                            }
                        });
                        rvCategory.setAdapter(fiturItem);


                        if (response.body().getPpob().isEmpty()) {

                        } else {


                        }

//------------donasi

                        pindahdonasi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), DonasiallActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Mengirim data donasiList ke halaman DonasiallActivity
                                intent.putExtra(Constants.DONASI_LIST_KEY, new Gson().toJson(response.body().getDonasiList()));
                                startActivity(intent);
                            }
                        });

//batasdonasi-------------------------------------------

                        if (response.body().getBerita().isEmpty()) {
                            llberita.setVisibility(View.GONE);
                        } else {
                            beritaItem = new BeritaItem(getActivity(), response.body().getBerita(), R.layout.item_grid);
                            rvberita.setAdapter(beritaItem);
                        }
                        User user = response.body().getData().get(0);
                        saveUser(user);
//                        if (HomeFragment.this.getActivity() != null) {
//                            login.setWalletSaldo(Long.parseLong(response.body().getSaldo()));
//                        }
                    } else {
                        LocalStore.get().deleteUser();
                        BaseApp.getInstance(context).setLoginUser(null);
                        startActivity(new Intent(context, IntroActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetHomeResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

//-----------mengaturslider--------------//
    Runnable runnable;

    @Override
    public void onResume() {
        super.onResume();


        User login = BaseApp.getInstance(context).getLoginUser();
        if (login != null) {
            Utility.currencyTXT(saldo, String.valueOf(login.getWalletSaldo()), context);
        }
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                cekData();

                if (adapter != null) {
                    int totalItems = adapter.getCount();

                    // Increment currentPage untuk pindah ke gambar berikutnya
                    viewPager.setCurrentItem(currentPage++, true);

                    // Kembali ke gambar awal jika sudah mencapai gambar terakhir
                    if (currentPage >= totalItems) {
                        currentPage = 0;
                    }
                }
                // Mengatur durasi perpindahan gambar
                handler.postDelayed(this, 3 * 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onDestroy() {

        stopCekPPOB();
        super.onDestroy();
    }

    @Override
    public void onPause() {

        stopCekPPOB();
        super.onPause();
    }

    private void saveUser(User user) {
LocalStore.get().saveUser(user);
        BaseApp.getInstance(context).setLoginUser(user);
    }

    private void sheetlist() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        @SuppressLint("InflateParams") final View mDialog = getLayoutInflater().inflate(R.layout.sheet_category, null);
        RecyclerView view = mDialog.findViewById(R.id.category);
        view.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        view.setHasFixedSize(true);
        AllFiturItem allfiturItem = new AllFiturItem(getActivity(), allfiturdata, R.layout.item_fitur);
        view.setAdapter(allfiturItem);

        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(mDialog);
        if (VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Objects.requireNonNull(mBottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

    public void onStart() {
        super.onStart();
        googleApiClient.connect();
        if (sp.getSetting()[6].equals("0") || sp.getSetting()[7].equals("0")) {
            gps = new GPSTracker(context);


// Check if GPS enabled

            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                final LatLng latLng = new LatLng(latitude, longitude);
                LokasiSaya = latLng;
                sp.updatemylat(String.valueOf(latitude));
                sp.updatemylong(String.valueOf(longitude));
                // Reverse-geocode OFF the main thread: Geocoder.getFromLocation is a
                // blocking network call that otherwise freezes the UI (ANR) when
                // returning to Home, especially on slow/loaded devices.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String alamat = getCompleteAddressString(latLng);
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sp.updateAlamat(alamat);
                                    android.util.Log.e("CekAlamat", alamat);
                                }
                            });
                        }
                    }
                }).start();
                gethome(new LatLng(latitude, longitude));
            } else {
                gps.showSettingsAlert();
            }
        } else {
            LatLng latLng = new LatLng(Double.parseDouble(sp.getSetting()[6]), Double.parseDouble(sp.getSetting()[7]));
            LokasiSaya = latLng;
            gethome(latLng);
        }
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //----------------------------------------------------------------------------------------------
    private String getCompleteAddressString(LatLng latLng) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                android.util.Log.e("Alamat", strReturnedAddress.toString() + "");
            } else {
                android.util.Log.e("Alamat", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            android.util.Log.e("Alamat", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String addressset = data.getStringExtra(ActivitySetHome.LOCATION_NAME);
                LatLng latLng = data.getParcelableExtra(ActivitySetHome.LOCATION_LATLNG);
                LokasiSaya = latLng;
                String Lat = String.valueOf(latLng.latitude);
                String Lng = String.valueOf(latLng.longitude);
                sp.updateAlamat(addressset);
                sp.updatemylat(String.valueOf(latLng.latitude));
                sp.updatemylong(String.valueOf(latLng.longitude));
                gethome(latLng);
                android.util.Log.e("UpdatePref", latLng.latitude + "," + latLng.longitude);
                User loginUser = BaseApp.getInstance(context).getLoginUser();
                UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
                SaveLokasiRequest request = new SaveLokasiRequest();
                request.id = loginUser.getId();
                request.id = loginUser.getId();
                request.nama = "Rumah";
                request.latitude = Lat;
                request.longitude = Lng;
                request.alamat = addressset;
                request.utama = "1";
                service.SaveHome(request).enqueue(new Callback<SaveLokasiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SaveLokasiResponse> call, @NonNull Response<SaveLokasiResponse> response) {
                        if (response.isSuccessful()) {
                            if (Objects.requireNonNull(response.body()).mesage.equals("success")) {
                                android.util.Log.e("SaveLokasi", response.body().mesage);
                            }
                        }
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(@NonNull Call<SaveLokasiResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        android.util.Log.e("SaveLokasi", t.getMessage());
                    }
                });


            }
        }
    }

    //---------------------------------------------------------------------------------------------------------
    private void UpdateToken(String Token) {
        final User login = BaseApp.getInstance(context).getLoginUser();
        if (login != null) {
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            UpdateTokenRequestJson param = new UpdateTokenRequestJson();
            param.setId(login.getId());
            param.setToken(Token);
            service.updateToken(param).enqueue(new Callback<ResponseJson>() {
                @Override
                public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                    if (response.isSuccessful()) {
                        android.util.Log.e("UpdateToken", Token);
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<ResponseJson> call, @NonNull Throwable t) {
                    android.util.Log.e("UpdateToken", "Error Token");
                }
            });
        }
    }

    //----------------------------------------- Cek Data -----------------------------------------

    private void cekData() {
        try {
            User login = BaseApp.getInstance(context).getLoginUser();
            android.util.Log.e("MySaldo", String.valueOf(login.getWalletSaldo()));
            UserService userService = ServiceGenerator.createService(
                    UserService.class, login.getNoTelepon(), login.getPassword());
            GetProgressRequest param = new GetProgressRequest();
            param.setId(login.getId());
            userService.progress(param).enqueue(new Callback<GetProgressResponse>() {
                @Override
                public void onResponse(@NonNull Call<GetProgressResponse> call, @NonNull Response<GetProgressResponse> response) {
                    if (response.isSuccessful()) {
                        if (TransaksiList != null) {
                            TransaksiList.clear();
                        }
                        TransaksiList = response.body().getData();
                        // Build the adapter ONCE (previously this ran once per list item,
                        // recreating the adapter N times on the main thread each poll).
                        if (TransaksiList == null || TransaksiList.isEmpty()) {
                            llrating.setVisibility(View.GONE);
                        } else {
                            llrating.setVisibility(View.VISIBLE);
                            progressItem = new ProgressItem(getActivity(), TransaksiList, R.layout.item_review);
                            rvreview.setAdapter(progressItem);
                            progressItem.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GetProgressResponse> call, @NonNull Throwable t) {
                    Log.e("mProgress Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("mTransaksi Error", e.getMessage());
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gps = new GPSTracker(context);
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {

// If User Checked 'Don't Show Again' checkbox for runtime permission, then navigate user to Settings
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Permission Required");
                    dialog.setCancelable(false);
                    dialog.setMessage("You have to Allow permission to access user location");
                    dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",
                                    context.getPackageName(), null));

                            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(i, 1001);
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
                //code for deny
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        switch (requestCode) {
            case 1001:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        gps = new GPSTracker(context);
                        if (gps.canGetLocation()) {
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            sp.updatemylat(String.valueOf(latitude));
                            sp.updatemylong(String.valueOf(longitude));
                            String Alamat = getCompleteAddressString(new LatLng(latitude, longitude));
                            sp.updateAlamat(Alamat);
                        }
                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 10);
                    }
                }
                break;
            default:
                break;
        }
    }
    //--------------------------- Service --------------------------------------------------------

    public void settingsrequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.

                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_LOCATION_PERMISSION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000 * 5);
        mLocationRequest.setFastestInterval(1000 * 3);
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            LocationServices.getFusedLocationProviderClient(context)
                    .requestLocationUpdates(mLocationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(LocationResult locationResult) {
                                    // do work here
                                    onLocationChanged(locationResult.getLastLocation());

                                }
                            },
                            Looper.myLooper());

        }
//        updateLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            mLastLocation = location;
//            updateLastLocation();
            Log.e("Lokasi Saya", location.getLatitude() + "," + location.getLongitude());
        }
    }

    public void getAddress(Context context, double LATITUDE, double LONGITUDE) {
        Locale localeID = new Locale("in", "ID");
        try {
            Geocoder geocoder = new Geocoder(context, localeID);
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                String cityName = addresses.get(0).getAddressLine(0);
                sp.updateAlamat(cityName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        updateLastLocation();
    }
    //------------------------------ FCM ----------------------------------------

    private void TestFcm(String Token, Notif notif) {
        final User login = BaseApp.getInstance(context).getLoginUser();
        if (login != null) {
            UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
            SendFcmRequest param = new SendFcmRequest();
            param.setId("1");
            param.setToken(Token);
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

    //------------------------------------- Mobile Pulsa -----------------------------------------------------
    private void startCekPPOB() {
        handler = new Handler();
        handler.postDelayed(updateCekPPOB, 3000);
    }

    private void stopCekPPOB() {
        if (handler != null) {
            handler.removeCallbacks(updateCekPPOB);
            // Also stop the 3s cekData() poller; previously it was never cancelled,
            // so every onResume stacked another loop -> many concurrent polls.
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
        }
    }

    private final Runnable updateCekPPOB = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        GetHistori();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (NetworkManager.isConnectToInternet(context)) {
                                try {
                                    GetHistori();
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
    public static ArrayList<StatusModels> mTipeList = new ArrayList<StatusModels>();

    private void GetHistori() {
        final User login = BaseApp.getInstance(context).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
        CekRequest request = new CekRequest();
        request.setIduser(login.getId());
        service.cekHistori(request).enqueue(new Callback<CekHistoriResponse>() {
            @Override
            public void onResponse(@NonNull Call<CekHistoriResponse> call, @NonNull Response<CekHistoriResponse> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            StatusModels model = response.body().getData().get(i);
                            mTipeList.add(model);
                            String reffid = mTipeList.get(i).getReff();
                            String sttausid = mTipeList.get(i).getStatus();
                            if (sttausid.equals("PROCESS")) {
                                mpulsa_Cek(reffid);
                            }
                            Log.d("CekHistori", reffid + "," + sttausid);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CekHistoriResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String datatopup;

    private void mpulsa_Cek(String noreff) {
        User login = BaseApp.getInstance(context).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, login.getNoTelepon(), login.getPassword());
        CekRequest param = new CekRequest();
        param.setNoreff(noreff);
        service.cektopup(param).enqueue(new Callback<CekResponse>() {
            @Override
            public void onResponse(@NonNull Call<CekResponse> call, @NonNull Response<CekResponse> response) {
                if (response.isSuccessful()) {
                    datatopup = response.body().getData();
                    datatopup(datatopup);
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<CekResponse> call, @NonNull Throwable t) {
                android.util.Log.e("MpulsaCek", t.getMessage());
            }
        });
    }

    private void datatopup(String response) {
        try {
            JSONObject rootJSONObject = new JSONObject(response).getJSONObject("data");
            String Status = rootJSONObject.getString("message");
            String Reffid = rootJSONObject.getString("ref_id");
            String Harga = rootJSONObject.getString("price");
            if (Status.equals("SUCCESS")) {
                UpdateHistoriPPOB(Reffid, Harga);
            }
            Log.d("MpulsaCek", Reffid + " | " + rootJSONObject.getString("message"));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("MpulsaCekError", e.getMessage());
        }
    }

    private void UpdateHistoriPPOB(String noreff, String harga) {
        final User login = BaseApp.getInstance(context).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, login.getEmail(), login.getPassword());
        CekRequest request = new CekRequest();
        request.setIduser(login.getId());
        request.setReff(noreff);
        request.setHarga(harga);
        request.setNama(login.getFullnama());
        request.setStatus("SUCCESS");
        request.setMysaldo(String.valueOf(login.getWalletSaldo()));
        service.updatehistori(request).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "Transaksi Dengan No Reff [" + noreff + "] Berhasil Terkirim.", Snackbar.LENGTH_LONG).show();
                        Log.d("UpdateHistori", response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Log.d("UpdateHistori", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    //------------------------------------ End Fungsi ppob --------------------------------------
    List<Object> midlisttrx = new ArrayList<>();
    List<MidtrxModels> middata = new ArrayList<>();


}
