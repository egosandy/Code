package com.rcdriver.cs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.utils.Utility;

public class DialogHistori {
    public void showDialog(Context activity,String id,String tgl,String fitur,String metode,String biaya,String status,String asal,String tujuan){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_histori);
        TextView txtorder = (TextView) dialog.findViewById(R.id.idorder);
        TextView txttanggal = (TextView) dialog.findViewById(R.id.tanggal);
        TextView txtfitur = (TextView) dialog.findViewById(R.id.fitur);
        TextView txtmetode = (TextView) dialog.findViewById(R.id.metode);
        TextView txttotal = (TextView) dialog.findViewById(R.id.total);
        TextView txtstatus = (TextView) dialog.findViewById(R.id.status);
//        TextView txtasal = (TextView) dialog.findViewById(R.id.Asal);
//        TextView txttujuan = (TextView) dialog.findViewById(R.id.Tujuan);
        ImageView btnclose = (ImageView) dialog.findViewById(R.id.close_btn);
        txtorder.setText("#" + id);
        txttanggal.setText(tgl);
        txtfitur.setText(fitur);
        txtmetode.setText(metode);
        Utility.currencyTXT(txttotal, biaya, activity);
        txtstatus.setText(status);
//        txtasal.setText(asal);
//        txttujuan.setText(tujuan);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
