package com.rcdriver.dr.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.rcdriver.dr.R;
import com.rcdriver.dr.adapter.VehicleAdapter;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.json.GetKendaraanList;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKendaraanActivity extends AppCompatActivity {
    private Context context;
    private User user;
    private ImageView backButton;
    private RecyclerView recyclerView;
    private RelativeLayout rlnodata;
    ShimmerFrameLayout shimmer;
    private VehicleAdapter adapter;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kendaraan);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        shimmer = findViewById(R.id.shimmerwallet);
        recyclerView = findViewById(R.id.rec_history);
        rlnodata = findViewById(R.id.rlnodata);
        backButton = findViewById(R.id.back_btn);
        submit = findViewById(R.id.button);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddKendaraanActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        getdata();
    }

    private void shimmershow() {
        recyclerView.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
    }

    private void shimmertutup() {

        recyclerView.setVisibility(View.VISIBLE);
        shimmer.setVisibility(View.GONE);
        shimmer.stopShimmer();
    }

    private void getdata(){
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        userService.listvehicle(loginUser.getId()).enqueue(new Callback<GetKendaraanList>() {
            @Override
            public void onResponse(Call<GetKendaraanList> call, Response<GetKendaraanList> response) {
                shimmertutup();
                if(response.isSuccessful()){
                    if(response.body().getKendaraanList().size() > 0){
                        recyclerView.setVisibility(View.VISIBLE);
                        rlnodata.setVisibility(View.GONE);
                        adapter = new VehicleAdapter(context, response.body().getKendaraanList());
                        recyclerView.setAdapter(adapter);
                    }else {
                        recyclerView.setVisibility(View.GONE);
                        rlnodata.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<GetKendaraanList> call, Throwable t) {
                shimmertutup();
                t.printStackTrace();
            }
        });
    }
}