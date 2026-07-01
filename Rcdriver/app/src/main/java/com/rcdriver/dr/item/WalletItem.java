package com.rcdriver.dr.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.rcdriver.dr.R;
import com.rcdriver.dr.models.WalletModel;
import com.rcdriver.dr.utils.DialogWallet;
import com.rcdriver.dr.utils.Utility;

/**
 * Created by otacodes on 3/24/2019.
 */

public class WalletItem extends RecyclerView.Adapter<WalletItem.ItemRowHolder> {

    private List<WalletModel> dataList;
    private Context mContext;
    private int rowLayout;


    public WalletItem(Context context, List<WalletModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
    }


    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final WalletModel singleItem = dataList.get(position);


        if (singleItem.getType().equals("withdraw")) {
            holder.text.setText(singleItem.getType());
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);

            switch (singleItem.getStatus()) {
                case "1":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.red));
                    holder.keterangan.setText("Saldo Keluar");
                    break;
                case "0":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
                    holder.keterangan.setText("Ditunda");
                    break;
                case "2":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.gray));
                    holder.keterangan.setText("Dibatalkan");
                    break;
            }

        } else if (singleItem.getType().equals("Order-")) {
            holder.text.setText("Order " + singleItem.getBank());
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            switch (singleItem.getStatus()) {
                case "1":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.red));
                    holder.keterangan.setText("Saldo Keluar");
                    break;
                case "0":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
                    holder.keterangan.setText("Ditunda");
                    break;
                case "2":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.gray));
                    holder.keterangan.setText("Dibatalkan");
                    break;
            }
            } else if (singleItem.getType().equals("redeem")) {
            holder.text.setText("redeem poin");
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            switch (singleItem.getStatus()) {
                case "1":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
                    holder.keterangan.setText("Redeem Berhasil");
                    break;
                case "0":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
                    holder.keterangan.setText("Ditunda");
                    break;
                case "2":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.gray));
                    holder.keterangan.setText("Dibatalkan");
                    break;
            }
        } else if (singleItem.getType().equals("topup") && singleItem.getStatus().equals("1")) {
            holder.text.setText(singleItem.getType() + " " + singleItem.getBank());
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            holder.text.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.green));
            holder.keterangan.setText("Saldo Masuk");

        } else if (singleItem.getType().equals("topup") && singleItem.getStatus().equals("2")) {
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            holder.text.setText(singleItem.getType() + " " + singleItem.getBank());
            holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.gray));
            holder.keterangan.setText("Dibatalkan");

        } else if (singleItem.getType().equals("topup") && singleItem.getStatus().equals("0")) {
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            holder.text.setText(singleItem.getType() + " " + singleItem.getBank());
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
            holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
            holder.keterangan.setText("Ditunda");

        } else if (singleItem.getType().equals("Order+")) {
            holder.text.setText("Order " + singleItem.getBank());
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            holder.text.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.green));
            holder.keterangan.setText("Saldo Masuk");

        } else if (singleItem.getType().equalsIgnoreCase("Transfer+")) {
            holder.text.setText(singleItem.getBank());
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            holder.text.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.green));
            holder.keterangan.setText("Saldo masuk");

        } else if (singleItem.getType().equalsIgnoreCase("Transfer-")) {
            holder.text.setText(singleItem.getBank());
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            holder.text.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.red));
            holder.keterangan.setText("Saldo Keluar");

        } else {
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
            holder.text.setText(singleItem.getType() + " " + singleItem.getBank());
            holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);

            holder.keterangan.setText("Ditunda");


        }
        Date myDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);
        try {
            myDate = dateFormat.parse(singleItem.getWaktu());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String finalDate = timeFormat.format(Objects.requireNonNull(myDate));
        holder.tanggal.setText(finalDate);
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogWallet alert = new DialogWallet();
                alert.showDialog(mContext, singleItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        LinearLayout rootLayout;
        TextView text, tanggal, nominal, keterangan;
        ImageView background;

        ItemRowHolder(View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            background = itemView.findViewById(R.id.background);
            text = itemView.findViewById(R.id.text);
            tanggal = itemView.findViewById(R.id.texttanggal);
            nominal = itemView.findViewById(R.id.textharga);
            keterangan = itemView.findViewById(R.id.textket);
        }
    }


}
