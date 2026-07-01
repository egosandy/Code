package com.rcdriver.cs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.item.AllMerchantNearItem;
import com.rcdriver.cs.item.MerchantNearItem;
import com.rcdriver.cs.json.AllMerchantByNearResponseJson;
import com.rcdriver.cs.json.AllMerchantBySection;
import com.rcdriver.cs.json.AllMerchantbyCatRequestJson;
import com.rcdriver.cs.json.GetAllMerchantbyCatRequestJson;
import com.rcdriver.cs.json.SearchMerchantbyCatRequestJson;
import com.rcdriver.cs.models.MerchantNearModel;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.api.MapDirectionAPI;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantCatActivity extends AppCompatActivity {
    private Context context;
    ImageView backbtn;
    TextView address, namakategori;
    ShimmerFrameLayout shimmerchantnear;
    RecyclerView rvcatmerchantnear, rvmerchantnear;
    AllMerchantNearItem merchantNearItem;
    MerchantNearItem nearItem;
    List<MerchantNearModel> clicknear = new ArrayList<>();
    LinearLayout llmerchantnear, shimlistnear;
    RelativeLayout nodatanear;
    int fiturId;
    String mode = "";
    String section = "";
    String idcat;
    int page = 1;
    int total = 0;
    private ProgressBar progressBar;
    private Location loc;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_cat);
        Intent intent = getIntent();
        context = this;
        idcat = intent.getStringExtra("idcat");
        fiturId = intent.getIntExtra("fiturId", 0);
        section = intent.getStringExtra("section");
        mode = intent.getStringExtra("mode");

        backbtn = findViewById(R.id.Goback);
        address = findViewById(R.id.address);
        shimmerchantnear = findViewById(R.id.shimmerchantnear);
        rvmerchantnear = findViewById(R.id.merchantnear);
        search = findViewById(R.id.searchtext);

        shimlistnear = findViewById(R.id.shimlistnear);
        nodatanear = findViewById(R.id.rlnodata);
        namakategori = findViewById(R.id.text_kategori);
        progressBar = findViewById(R.id.progressBar);

        namakategori.setText(intent.getStringExtra("namacat"));

        address.setSelected(true);

        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng centerPos = new LatLng(location.getLatitude(), location.getLongitude());
                    requestAddress(centerPos, address);
                    loc = location;
                    if(mode.equalsIgnoreCase("kategori")){
                        getmerchntbycatnear(location, idcat, 1);
                    }else if(mode.equalsIgnoreCase("section")){
                        getmerchntbysection(location, section);
                    }else {
                        shimmertutup();
                    }


                }
            }
        });
        shimmershow();

        rvmerchantnear.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(isLastItemDisplaying(rvmerchantnear)){
                    if(page == 1){
                        page = page + 1;
                    }
                    if(page <= total){
                        getmerchntbycatnear(loc, idcat, page++);
                    }

                }

            }

        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                clicknear.clear();
                shimmershow();
                String sSearch = search.getText().toString().trim();
                if (!TextUtils.isEmpty(sSearch)) {
                    searchmerchant(loc, search.getText().toString());
                }
                return false;
            }
        });

    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (Objects.requireNonNull(recyclerView.getAdapter()).getItemCount() >= 10) {
            int lastVisibleItemPosition = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    private void shimmershow() {
        shimmerchantnear.startShimmerAnimation();
    }

    private void shimmertutup() {
        shimmerchantnear.stopShimmerAnimation();
        shimmerchantnear.setVisibility(View.GONE);
    }

    private void getmerchntbycatnear(final Location location, String cat, int page) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetAllMerchantbyCatRequestJson param = new GetAllMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        param.setKategori(cat);
        param.setFitur(String.valueOf(fiturId));
        param.setPage(page);
        if(page > 1){
            progressBar.setVisibility(View.VISIBLE);
        }
        userService.getallmerchanbynearpage(param).enqueue(new Callback<AllMerchantByNearResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Response<AllMerchantByNearResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        total = response.body().getTotalPage();
                        clicknear.addAll(response.body().getData());

                        shimmertutup();
                        if (response.body().getData().isEmpty()) {
                            nodatanear.setVisibility(View.VISIBLE);
                        } else {
                            nodatanear.setVisibility(View.GONE);
                            rvmerchantnear.setVisibility(View.VISIBLE);
                            if(page == 1){
                                merchantNearItem = new AllMerchantNearItem(MerchantCatActivity.this, clicknear, R.layout.item_merchant_list);
                                rvmerchantnear.setAdapter(merchantNearItem);
                            }else{

                                merchantNearItem.notifyDataSetChanged();
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Throwable t) {

            }
        });
        Log.e("PAGE", String.valueOf(page));
    }

    private void getmerchntbysection(final Location location, String section) {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        AllMerchantbyCatRequestJson param = new AllMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        param.setSection(section);
        param.setFitur(String.valueOf(fiturId));

        userService.allmerchantbysection(param).enqueue(new Callback<AllMerchantBySection>() {
            @Override
            public void onResponse(@NonNull Call<AllMerchantBySection> call, @NonNull Response<AllMerchantBySection> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        clicknear.addAll(response.body().getData());
                        shimmertutup();
                        if (response.body().getData().isEmpty()) {
                            nodatanear.setVisibility(View.VISIBLE);
                        } else {
                            nodatanear.setVisibility(View.GONE);
                            rvmerchantnear.setVisibility(View.VISIBLE);
                            merchantNearItem = new AllMerchantNearItem(MerchantCatActivity.this, clicknear, R.layout.item_merchant_list);
                            rvmerchantnear.setAdapter(merchantNearItem);
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllMerchantBySection> call, @NonNull Throwable t) {

            }
        });
        Log.e("PAGE", String.valueOf(page));
    }

    private void searchmerchant(final Location location, String like) {
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        SearchMerchantbyCatRequestJson param = new SearchMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        param.setFitur(String.valueOf(fiturId));
        param.setLike(like);
        userService.searchmerchant(param).enqueue(new Callback<AllMerchantByNearResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Response<AllMerchantByNearResponseJson> response) {
                shimmertutup();
                if (response.isSuccessful()) {
                    android.util.Log.e("Pencarian", Objects.requireNonNull(response.body()).getMessage());
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        android.util.Log.e("Pencarian", Objects.requireNonNull(response.body()).getMessage());
                        clicknear = response.body().getData();
                        shimmerchantnear.setVisibility(View.GONE);
                        rvmerchantnear.setVisibility(View.VISIBLE);
                        shimmerchantnear.stopShimmerAnimation();
                        if (response.body().getData().isEmpty()) {
                            nodatanear.setVisibility(View.VISIBLE);
                        } else {
                            nodatanear.setVisibility(View.GONE);
                            merchantNearItem = new AllMerchantNearItem(context, clicknear, R.layout.item_merchant_list);
                            rvmerchantnear.setAdapter(merchantNearItem);
                        }
                    }
                } else {
                    android.util.Log.e("Pencarian", "Gagal");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllMerchantByNearResponseJson> call, @NonNull Throwable t) {

            }
        });
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
}