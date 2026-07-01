package com.rcdriver.dr.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import com.rcdriver.dr.R;
import com.rcdriver.dr.models.WalletModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DialogWallet {
    public void showDialog(Context context, WalletModel wallet){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_wallet);
        TextView txtjumlah = (TextView) dialog.findViewById(R.id.idorder);
        TextView txttanggal = (TextView) dialog.findViewById(R.id.tanggal);
        TextView txtbank = (TextView) dialog.findViewById(R.id.fitur);
        TextView txtnama = (TextView) dialog.findViewById(R.id.metode);
        TextView txttrekening = (TextView) dialog.findViewById(R.id.total);
        TextView txtstatus = (TextView) dialog.findViewById(R.id.status);
        ImageView btnclose = (ImageView) dialog.findViewById(R.id.close_btn);

        Utility.currencyTXT(txtjumlah, wallet.getJumlah(), context);
        Date myDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);
        try {
            myDate = dateFormat.parse(wallet.getWaktu());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String finalDate = timeFormat.format(Objects.requireNonNull(myDate));
        txttanggal.setText(finalDate);
        txtbank.setText(wallet.getBank());
        txtnama.setText(wallet.getNamapemilik());
        txttrekening.setText(wallet.getRekening());

        if(wallet.getStatus().equalsIgnoreCase("0")){
            txtstatus.setText("Ditunda");
        }else if(wallet.getStatus().equalsIgnoreCase("1")){
            txtstatus.setText("Berhasil");
        }else {
            txtstatus.setText("Dibatalkan");
        }

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
