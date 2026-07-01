package com.rcdriver.cs.activity.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.payment.HistoryAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.json.payment.GetHistoryResponse;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.PaymentService;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTopupActivity extends AppCompatActivity {
    private Context context;
    private User user;
    private ImageView backButton;
    private RecyclerView recyclerView;
    private RelativeLayout rlnodata;
    ShimmerFrameLayout shimmer;
    private HistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_topup);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        shimmer = findViewById(R.id.shimmerwallet);
        recyclerView = findViewById(R.id.rec_history);
        rlnodata = findViewById(R.id.rlnodata);
        backButton = findViewById(R.id.back_btn);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        getdata();
    }

    private void shimmershow() {
        recyclerView.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmerAnimation();
    }

    private void shimmertutup() {

        recyclerView.setVisibility(View.VISIBLE);
        shimmer.setVisibility(View.GONE);
        shimmer.stopShimmerAnimation();
    }

    private void getdata(){
        shimmershow();
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        PaymentService userService = ServiceGenerator.createService(
                PaymentService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        userService.riwayat(loginUser.getId()).enqueue(new Callback<GetHistoryResponse>() {
            @Override
            public void onResponse(Call<GetHistoryResponse> call, Response<GetHistoryResponse> response) {
                shimmertutup();
                if(response.isSuccessful()){
                    if(response.body().getTransaksiList().size() > 0){
                        recyclerView.setVisibility(View.VISIBLE);
                        rlnodata.setVisibility(View.GONE);
                        adapter = new HistoryAdapter(context, response.body().getTransaksiList());
                        recyclerView.setAdapter(adapter);
                    }else {
                        recyclerView.setVisibility(View.GONE);
                        rlnodata.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<GetHistoryResponse> call, Throwable t) {
                shimmertutup();
                t.printStackTrace();
            }
        });
    }
}