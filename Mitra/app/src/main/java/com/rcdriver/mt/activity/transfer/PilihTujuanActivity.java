package com.rcdriver.mt.activity.transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rcdriver.mt.R;
import com.rcdriver.mt.constants.Constants;

public class PilihTujuanActivity extends AppCompatActivity {
    private Context context;
    private LinearLayout luser, ldriver, lmerchant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_tujuan);
        context = this;
        luser = findViewById(R.id.luser);
        ldriver = findViewById(R.id.ldriver);
        lmerchant = findViewById(R.id.lmerchant);

        luser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TransferSaldoActivity.class);
                intent.putExtra(Constants.METHOD_NAME, "user");
                startActivity(intent);
            }
        });

        ldriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TransferSaldoActivity.class);
                intent.putExtra(Constants.METHOD_NAME, "driver");
                startActivity(intent);
            }
        });

        lmerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TransferSaldoActivity.class);
                intent.putExtra(Constants.METHOD_NAME, "merchant");
                startActivity(intent);
            }
        });
    }
}