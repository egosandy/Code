package com.rcdriver.cs.activity.digi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.digi.KategoriAdapter;
import com.rcdriver.cs.json.ResponseDigiKategori;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDigiActivity extends AppCompatActivity {
    private Context context;
    LinearLayout lhistori;
    RecyclerView recyclerView;
    private TextView texterror;
    KategoriAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_digi);
        context = this;
        lhistori = findViewById(R.id.Histori);
        texterror = findViewById(R.id.textView7);
        recyclerView = findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));

        lhistori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RiwayatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        getdata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getdata();
    }

    private void getdata(){
        texterror.setVisibility(View.GONE);
        UserService service = ServiceGenerator.createService(UserService.class, "admin", "12345");
        service.getKategori().enqueue(new Callback<ResponseDigiKategori>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDigiKategori> call, @NonNull Response<ResponseDigiKategori> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        if(response.body().getData().size() > 0){
                            adapter = new KategoriAdapter(context, response.body().getData());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else{
                            texterror.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDigiKategori> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

}