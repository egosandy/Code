package com.rcdriver.cs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.rcdriver.cs.R;

public class DialogInbok {
    public void showDialog(Context context,String title,String konten){
        final Dialog dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_inbok);
        ImageView btnclose = (ImageView) dialog.findViewById(R.id.close_btn);
        TextView Title = (TextView) dialog.findViewById(R.id.title);
        TextView Konten = (TextView) dialog.findViewById(R.id.konten);
        Title.setText(title);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Konten.setText(Html.fromHtml(konten,Html.FROM_HTML_MODE_LEGACY));
        } else {
            Konten.setText(Html.fromHtml(konten));
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
