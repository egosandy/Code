package com.rcdriver.mt.activity.transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.rcdriver.mt.R;
import com.rcdriver.mt.constants.Constants;
import com.rcdriver.mt.models.TransferDetail;
import com.rcdriver.mt.utils.Utility;

public class ResultTransferActivity extends AppCompatActivity {
    private Context context;
    private TextView textTrx, textNominal, textTanggal, textStatus, textNama,
            textNote, textSaldoAwal, textSaldoAkhir, textDetail;
    private ImageView img_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_transfer);
        context = this;
        textTrx = findViewById(R.id.textViewNoTransaksi);
        textNominal = findViewById(R.id.text_amount);
        textStatus = findViewById(R.id.text_status2);
        textTanggal = findViewById(R.id.text_datetime);
        textNama = findViewById(R.id.text_nama);
        textNote = findViewById(R.id.text_bank);
        textSaldoAwal = findViewById(R.id.textViewSaldoAwal);
        textSaldoAkhir = findViewById(R.id.textViewSaldoAkhir);
        textDetail = findViewById(R.id.text_detail);
        img_status = findViewById(R.id.imageView5);

        textDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String intentData = getIntent().getStringExtra(Constants.DATA);

        TransferDetail detail = new Gson().fromJson(intentData, TransferDetail.class);
        if(detail.getStatus().equalsIgnoreCase("1")){
            textStatus.setText("Berhasil");
            textStatus.setTextColor(context.getResources().getColor(R.color.green));
            img_status.setImageDrawable(context.getResources().getDrawable(R.drawable.img_sukses));
        }else {
            textStatus.setText("Gagal");
            textStatus.setTextColor(context.getResources().getColor(R.color.red));
            img_status.setImageDrawable(context.getResources().getDrawable(R.drawable.img_gagal));
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.US);
        String finalDate = timeFormat.format(detail.getRegtime());
        textTanggal.setText(finalDate);
        textTrx.setText(detail.getInvoice());
        textNama.setText(detail.getReceiver_user_id());
        textNote.setText(detail.getNote());
        textNominal.setText(Utility.toformatRupiah(detail.getNominal()));
    }
}