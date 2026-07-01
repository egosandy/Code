package com.rcdriver.cs.activity.digi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.digi.RiwayatAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.json.GetRiwayatDigiResponse;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatActivity extends AppCompatActivity {
    private Context context;
    private User user;
    private RecyclerView recyclerView;
    private RelativeLayout rlProgress;
    private RiwayatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        recyclerView = findViewById(R.id.ListPPOB);
        rlProgress = findViewById(R.id.rlprogress);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        getData();
    }

    private void getData() {
        rlProgress.setVisibility(View.VISIBLE);
        UserService service = ServiceGenerator.createService(UserService.class, user.getId(), user.getPassword());
        service.riwayatTransaksi(user.getId()).enqueue(new Callback<GetRiwayatDigiResponse>() {
            @Override
            public void onResponse(Call<GetRiwayatDigiResponse> call, Response<GetRiwayatDigiResponse> response) {
                rlProgress.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        adapter = new RiwayatAdapter(context, response.body().getTransaksiList());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRiwayatDigiResponse> call, Throwable t) {
                rlProgress.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });
    }
}