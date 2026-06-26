package com.rcdriver.cs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.DonasiWdAdapter;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.json.GetDonasiWdResponse;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonasiWdActivity extends AppCompatActivity {
    private Context context;
    private User user;
    private ImageView backButton;
    private RecyclerView recyclerView;
    private RelativeLayout rlnodata;
    private ProgressBar progressBar;
    private String id;
    private DonasiWdAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_wd);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        backButton = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.recycler);
        rlnodata = findViewById(R.id.rlnodata);
        progressBar = findViewById(R.id.progressBar4);

        id = getIntent().getStringExtra(Constants.METHOD);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        getData();
    }

    private void getData() {

        progressBar.setVisibility(View.VISIBLE);
        UserService service = ServiceGenerator.createService(UserService.class, user.getId(), user.getPassword());
        service.getDonasiWd(id).enqueue(new Callback<GetDonasiWdResponse>() {
            @Override
            public void onResponse(Call<GetDonasiWdResponse> call, Response<GetDonasiWdResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        adapter = new DonasiWdAdapter(context, response.body().getDonassiWds());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetDonasiWdResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();

            }
        });
    }
}