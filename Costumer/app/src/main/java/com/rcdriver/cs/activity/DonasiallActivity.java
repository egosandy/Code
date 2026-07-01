package com.rcdriver.cs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rcdriver.cs.R;
import com.rcdriver.cs.adapter.DonasiAdapter;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.Donasi;

import java.util.List;

public class DonasiallActivity extends AppCompatActivity {

    private List<Donasi> donasiList;
    private DonasiAdapter donasiAdapter;
    private RecyclerView donasiRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasiall);

        // Inisialisasi RecyclerView
        donasiRecyclerView = findViewById(R.id.donasiRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        donasiRecyclerView.setLayoutManager(layoutManager);

        // Ambil data donasiList dari Intent
        String jsonDonasiList = getIntent().getStringExtra(Constants.DONASI_LIST_KEY);
        donasiList = new Gson().fromJson(jsonDonasiList, new TypeToken<List<Donasi>>() {}.getType());

        // Cek apakah donasiList kosong
        if (donasiList == null || donasiList.isEmpty()) {
            // Handle case where donasiList is empty
            LinearLayout noDonasiLayout = findViewById(R.id.noDonasiLayout);
            noDonasiLayout.setVisibility(View.VISIBLE);
        } else {
            // Inisialisasi adapter untuk RecyclerView
            donasiAdapter = new DonasiAdapter(donasiList, this);
            donasiRecyclerView.setAdapter(donasiAdapter);
        }
    }
}
