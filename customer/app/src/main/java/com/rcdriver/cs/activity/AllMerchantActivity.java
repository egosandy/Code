package com.rcdriver.cs.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.item.BannerItem;
import com.rcdriver.cs.item.CatMerchantNearItem;
import com.rcdriver.cs.item.KategoriMerchant;
import com.rcdriver.cs.item.MerchantItem;
import com.rcdriver.cs.item.MerchantNearItem;
import com.rcdriver.cs.json.AllMerchantByNearResponseJson;
import com.rcdriver.cs.json.AllMerchantbyCatRequestJson;
import com.rcdriver.cs.json.GetAllMerchantbyCatRequestJson;
import com.rcdriver.cs.json.SearchMerchantbyCatRequestJson;
import com.rcdriver.cs.json.SliderRequest;
import com.rcdriver.cs.json.SliderResponse;
import com.rcdriver.cs.models.MerchantModel;
import com.rcdriver.cs.models.MerchantNearModel;
import com.rcdriver.cs.models.SliderModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMerchantActivity extends AppCompatActivity {
    EditText search;
    ShimmerFrameLayout shimmerchantnear, shimmercat, shimmerLaris, shimmerBaru;
    RecyclerView rvcatmerchantnear, rvmerchantnear, rvmerchantlaris, rvmerchantbaru;
    MerchantNearItem merchantNearItem;
    MerchantItem merchantLarisItem;
    MerchantNearItem merchantNew;
    CatMerchantNearItem catMerchantNearItem;
    KategoriMerchant kategoriMerchant;
    List<MerchantNearModel> clicknear;
    List<MerchantNearModel> merchantBaruList;
    List<MerchantModel> merchantLaris;
    LinearLayout shimlistnear;
    LinearLayout shimlistcatnear;
    LinearLayout shimlistlaris;
    LinearLayout shimListBaru;
    LinearLayout llterdekat, llterlaris, llterbaru;
    LinearLayout textTerdekat, textTerlaris, textTerbaru;
    TextView nodatadekat, nodatalaris, nodatabaru;
    RelativeLayout nodatanear;
    int fiturId;
    //banner
    private BannerItem adapter;
    List<SliderModel> listslider;
    private ViewPager viewPager;
    private SettingPreference sp;
    private double Lat = 0;
    private double Lng = 0;
    private LinearLayout llslider,llsearch;
    private LinearLayout llmerchantnear;
    private Context context;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_merchant);
        viewPager = findViewById(R.id.viewPager);
        sp = new SettingPreference(this);
        context = this;
        Intent intent = getIntent();
        Lat = Double.parseDouble(sp.getSetting()[6]);
        Lng = Double.parseDouble(sp.getSetting()[7]);
        fiturId = intent.getIntExtra("FiturKey", -1);
        // backbtn = findViewById(R.id.Goback);
        //  address = findViewById(R.id.address);
        shimmerchantnear = findViewById(R.id.shimmerchantnear);
        shimmercat = findViewById(R.id.shimmercat);
        shimmerLaris = findViewById(R.id.shimmerchantlaris);
        shimmerBaru = findViewById(R.id.shimmerbaru);
        rvcatmerchantnear = findViewById(R.id.catmerchantnear);
        rvmerchantnear = findViewById(R.id.merchantnear);
        rvmerchantlaris = findViewById(R.id.merchantlaris);
        rvmerchantbaru = findViewById(R.id.merchantbaru);
        llmerchantnear = findViewById(R.id.llmerchantnear);
        shimlistnear = findViewById(R.id.shimlistnear);
        shimlistcatnear = findViewById(R.id.shimlistcatnear);
        shimlistlaris = findViewById(R.id.shimlistlaris);
        shimListBaru = findViewById(R.id.shimlistBaru);
        nodatanear = findViewById(R.id.rlnodata);
        search = findViewById(R.id.searchtext);
        llslider = findViewById(R.id.Slider);
        llsearch = findViewById(R.id.search);
        llterdekat = findViewById(R.id.llterdekat);
        llterlaris = findViewById(R.id.lllaris);
        llterbaru = findViewById(R.id.llbaru);
        textTerdekat = findViewById(R.id.textView3);
        textTerlaris = findViewById(R.id.textView5);
        textTerbaru = findViewById(R.id.textView6);
        nodatabaru = findViewById(R.id.nodatabaru);
        nodatalaris = findViewById(R.id.nodatalaris);
        nodatadekat = findViewById(R.id.nodatanear);
        // address.setSelected(true);
        rvcatmerchantnear.setHasFixedSize(true);
        rvcatmerchantnear.setNestedScrollingEnabled(false);
        rvcatmerchantnear.setLayoutManager(new GridLayoutManager(AllMerchantActivity.this, 4));

        rvmerchantlaris.setHasFixedSize(true);
        rvmerchantlaris.setNestedScrollingEnabled(false);
        rvmerchantlaris.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rvmerchantbaru.setHasFixedSize(true);
        rvmerchantbaru.setNestedScrollingEnabled(false);
        rvmerchantbaru.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        /*backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        textTerdekat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MerchantCatActivity.class);
                intent.putExtra("idcat", "0");
                intent.putExtra("namacat", "Terdekat");
                intent.putExtra("mode", "section");
                intent.putExtra("section", "1");
                intent.putExtra("fiturId", fiturId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        textTerlaris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MerchantCatActivity.class);
                intent.putExtra("idcat", "0");
                intent.putExtra("namacat", "Terlaris");
                intent.putExtra("mode", "section");
                intent.putExtra("section", "2");
                intent.putExtra("fiturId", fiturId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        textTerbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MerchantCatActivity.class);
                intent.putExtra("idcat", "0");
                intent.putExtra("namacat", "Terbaru");
                intent.putExtra("mode", "section");
                intent.putExtra("section", "3");
                intent.putExtra("fiturId", fiturId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MerchantCatActivity.class);
                intent.putExtra("idcat", "0");
                intent.putExtra("namacat", "Cari Merchant");
                intent.putExtra("mode", "search");
                intent.putExtra("section", "0");
                intent.putExtra("fiturId", fiturId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        shimmershow();
        gethome();
    }
    @Override
    public void onResume() {
        Lat = Double.parseDouble(sp.getSetting()[6]);
        Lng = Double.parseDouble(sp.getSetting()[7]);
        com.rcdriver.cs.utils.Log.d("MerTerdekat", Lat + "," + Lng);
        LatLng latLng = new LatLng(Lat,Lng);
        getmerchant(latLng);
        super.onResume();
    }
    private void gethome() {
        try {
            User login = BaseApp.getInstance(this).getLoginUser();
            UserService userService = ServiceGenerator.createService(UserService.class, login.getNoTelepon(), login.getPassword());
            SliderRequest param = new SliderRequest();
            param.setFitur_promosi(String.valueOf(fiturId));
            userService.SliderApp(param).enqueue(new Callback<SliderResponse>() {
                @Override
                public void onResponse(@NonNull Call<SliderResponse> call, @NonNull Response<SliderResponse> response) {
                    if (response.isSuccessful()) {
                        if (listslider != null) {
                            listslider.clear();
                        }
                        listslider = response.body().getData();
                        for (int i = 0; i < listslider.size(); ) {
                            if (listslider != null && listslider.size() > 0) {
                                if (llslider.getVisibility() == View.GONE) {llslider.setVisibility(View.VISIBLE);}
                                adapter = new BannerItem(listslider, AllMerchantActivity.this);
                                viewPager.setAdapter(adapter);
                                viewPager.setCurrentItem(1);
                                viewPager.setOffscreenPageLimit(4);
                                viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                                viewPager.setPadding(0, 0, 0, 0);
                                viewPager.setPageMargin(0);
                                com.rcdriver.cs.utils.Log.d("mBanner", listslider.get(i).getFoto());
                            }else{
                                if (llslider.getVisibility() == View.VISIBLE) {llslider.setVisibility(View.GONE);}
                            }
                            com.rcdriver.cs.utils.Log.d("mBanner", listslider.get(i).getFoto());
                            i++;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SliderResponse> call, @NonNull Throwable t) {
                    com.rcdriver.cs.utils.Log.e("mLokasi Error", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            com.rcdriver.cs.utils.Log.e("mLokasi Error", e.getMessage());
        }

    }
    private void shimmershow() {
        rvmerchantnear.setVisibility(View.GONE);
        rvmerchantlaris.setVisibility(View.GONE);
        rvmerchantbaru.setVisibility(View.GONE);
        llsearch.setVisibility(View.GONE);
        shimmerchantnear.startShimmerAnimation();
        shimmercat.startShimmerAnimation();
        shimmerLaris.startShimmerAnimation();
        shimmerBaru.startShimmerAnimation();
        llterdekat.setVisibility(View.GONE);
        llterbaru.setVisibility(View.GONE);
        llterlaris.setVisibility(View.GONE);

    }

    private void shimmertutup() {
        rvcatmerchantnear.setVisibility(View.VISIBLE);
        llsearch.setVisibility(View.VISIBLE);
        rvmerchantnear.setVisibility(View.VISIBLE);
        rvmerchantlaris.setVisibility(View.VISIBLE);
        rvmerchantbaru.setVisibility(View.VISIBLE);
        shimmerchantnear.stopShimmerAnimation();
        shimmerchantnear.setVisibility(View.GONE);
        shimmercat.stopShimmerAnimation();
        shimmercat.setVisibility(View.GONE);
        shimmerLaris.stopShimmerAnimation();
        shimmerLaris.setVisibility(View.GONE);
        shimmerBaru.stopShimmerAnimation();
        shimmerBaru.setVisibility(View.GONE);
        llterdekat.setVisibility(View.VISIBLE);
        llterbaru.setVisibility(View.VISIBLE);
        llterlaris.setVisibility(View.VISIBLE);

    }

    private void getdata(final LatLng location) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AllMerchantbyCatRequestJson param = new AllMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.latitude));
        param.setLon(String.valueOf(location.longitude));
        param.setPhone(loginUser.getNoTelepon());
        param.setKategori(String.valueOf(fiturId));
        userService.allmerchant(param).enqueue(new Callback<AllMerchantByNearResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Response<AllMerchantByNearResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        shimmertutup();

                        if(response.body().getKategori().isEmpty()){
                            rvcatmerchantnear.setVisibility(View.GONE);
                            nodatanear.setVisibility(View.VISIBLE);
                        }else {
                            // update 090422
                            // change kategori to gridview
                            kategoriMerchant = new KategoriMerchant(AllMerchantActivity.this, response.body().getKategori(), R.layout.kategori_mitra);
                            rvcatmerchantnear.setAdapter(kategoriMerchant);
                        }

                        if (response.body().getData().isEmpty()) {
                            rvmerchantnear.setVisibility(View.GONE);
                            textTerdekat.setVisibility(View.GONE);
                            nodatadekat.setVisibility(View.VISIBLE);
                        } else {
                            textTerdekat.setVisibility(View.VISIBLE);
                            nodatadekat.setVisibility(View.GONE);
                            clicknear = response.body().getData();
                            merchantNearItem = new MerchantNearItem(AllMerchantActivity.this, clicknear, R.layout.item_merchant_grid);
                            rvmerchantnear.setAdapter(merchantNearItem);
                        }

                        if(response.body().getMerchantpromo().isEmpty()){
                            rvmerchantlaris.setVisibility(View.GONE);
                            textTerlaris.setVisibility(View.GONE);
                            nodatalaris.setVisibility(View.VISIBLE);
                        }else {
                            textTerlaris.setVisibility(View.VISIBLE);
                            nodatalaris.setVisibility(View.GONE);
                            merchantLaris = response.body().getMerchantpromo();
                            merchantLarisItem = new MerchantItem(AllMerchantActivity.this, merchantLaris, R.layout.item_merchant_grid);
                            rvmerchantlaris.setAdapter(merchantLarisItem);
                        }

                        if(response.body().getMerchantnew().isEmpty()){
                            rvmerchantbaru.setVisibility(View.GONE);
                            textTerbaru.setVisibility(View.VISIBLE);
                            nodatabaru.setVisibility(View.VISIBLE);
                        }else {
                            textTerbaru.setVisibility(View.VISIBLE);
                            nodatabaru.setVisibility(View.VISIBLE);
                            merchantBaruList = response.body().getMerchantnew();
                            merchantNew = new MerchantNearItem(AllMerchantActivity.this, merchantBaruList, R.layout.item_merchant_grid);
                            rvmerchantbaru.setAdapter(merchantNew);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void getmerchntbycatnear(final LatLng location, String cat) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetAllMerchantbyCatRequestJson param = new GetAllMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.latitude));
        param.setLon(String.valueOf(location.longitude));
        param.setPhone(loginUser.getNoTelepon());
        param.setKategori(cat);
        param.setFitur(String.valueOf(fiturId));
        userService.getallmerchanbynear(param).enqueue(new Callback<AllMerchantByNearResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Response<AllMerchantByNearResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        clicknear = response.body().getData();
                        shimmerchantnear.setVisibility(View.GONE);
                        rvmerchantnear.setVisibility(View.VISIBLE);
                        shimmerchantnear.stopShimmerAnimation();
                        if (response.body().getData().isEmpty()) {
                            nodatanear.setVisibility(View.VISIBLE);
                        } else {
                            nodatanear.setVisibility(View.GONE);
                            merchantNearItem = new MerchantNearItem(AllMerchantActivity.this, clicknear, R.layout.item_merchant_list);
                            rvmerchantnear.setAdapter(merchantNearItem);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void searchmerchant(final LatLng location, String like) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        SearchMerchantbyCatRequestJson param = new SearchMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.latitude));
        param.setLon(String.valueOf(location.longitude));
        param.setPhone(loginUser.getNoTelepon());
        param.setFitur(String.valueOf(fiturId));
        param.setLike(like);
        userService.searchmerchant(param).enqueue(new Callback<AllMerchantByNearResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Response<AllMerchantByNearResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.e("Pencarian", Objects.requireNonNull(response.body()).getMessage());
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        Log.e("Pencarian", Objects.requireNonNull(response.body()).getMessage());
                        clicknear = response.body().getData();
                        shimmerchantnear.setVisibility(View.GONE);
                        rvmerchantnear.setVisibility(View.VISIBLE);
                        shimmerchantnear.stopShimmerAnimation();
                        if (response.body().getData().isEmpty()) {
                            nodatanear.setVisibility(View.VISIBLE);
                        } else {
                            nodatanear.setVisibility(View.GONE);
                            merchantNearItem = new MerchantNearItem(AllMerchantActivity.this, clicknear, R.layout.item_merchant_list);
                            rvmerchantnear.setAdapter(merchantNearItem);
                        }
                    }
                } else {
                    Log.e("Pencarian", "Gagal");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    private void getmerchant(LatLng latLng){
        if (latLng != null) {
            LatLng mylat = new LatLng(Lat,Lng);
            //   requestAddress(latLng, address);
            //   String Alamat = getCompleteAddressString(latLng);
            // address.setText(Alamat);
            getdata(mylat);
            search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (clicknear != null) {
                        clicknear.clear();
                    }
                    shimlistnear.setVisibility(View.VISIBLE);
                    shimmerchantnear.setVisibility(View.VISIBLE);
                    shimlistcatnear.setVisibility(View.GONE);
                    rvmerchantnear.setVisibility(View.GONE);
                    nodatanear.setVisibility(View.GONE);
                    shimmerchantnear.startShimmerAnimation();
                    String sSearch = search.getText().toString().trim();
                    if (TextUtils.isEmpty(sSearch)) {
                        getmerchntbycatnear(latLng, "1");
                    } else {
                        searchmerchant(latLng, search.getText().toString());
                    }
                    return false;
                }

            });
        }
    }

}
