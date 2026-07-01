package com.rcdriver.dr.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rcdriver.dr.activity.PendingActivity;
import com.rcdriver.dr.utils.Log;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.OrderDetailActivity;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.models.AllTransaksiModel;
import com.rcdriver.dr.utils.Utility;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by otacodes on 3/24/2019.
 */

public class HistoryItem extends RecyclerView.Adapter<HistoryItem.ItemRowHolder> {

    public static String Warna = "#1AC463";
    private List<AllTransaksiModel> dataList;
    private Context mContext;
    private int rowLayout;

    public HistoryItem(Context context, List<AllTransaksiModel> dataList, int rowLayout) {
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

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final AllTransaksiModel singleItem = dataList.get(position);
        //tema
        Warna = "#000000";
        String BGColor = Warna;
        int colorCodeDark = Color.parseColor(BGColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.text.setText("Order " + singleItem.getFitur());
            if (singleItem.getHome().equals("4")) {
                double totalbiaya = Double.parseDouble(singleItem.getTotalbiaya());
                Utility.currencyTXT(holder.nominal, String.valueOf(singleItem.getHarga() + totalbiaya), mContext);
            } else {
                Utility.currencyTXT(holder.nominal, String.valueOf(singleItem.getHarga()), mContext);
            }
//            holder.keterangan.setText(singleItem.getStatustransaksi());
            SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            String finalDate = timeFormat.format(singleItem.getWaktuOrder());
          //  holder.tanggal.setText(finalDate);
            Picasso.get()
                    .load(Constants.IMAGESFITUR + singleItem.getIcon())
                    .placeholder(R.drawable.nocamera)
                    .error(R.drawable.nocamera)
                    .into(holder.images);

            if (singleItem.status == 4 && singleItem.getRate().isEmpty()) {
                holder.text.setTextColor(colorCodeDark);
             //   holder.keterangan.setTextColor(colorCodeDark);
                holder.nominal.setTextColor(colorCodeDark);
                holder.status.setTextColor(colorCodeDark);
                holder.status.setText(singleItem.getStatustransaksi());
              //  holder.background.setImageTintList(ColorStateList.valueOf(colorCodeDark));
              //  holder.CardBG.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
              //  holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.white_circle));
//                holder.LeftStat.setBackgroundColor(colorCodeDark);

            } else if (singleItem.status == 5) {
                holder.text.setTextColor(mContext.getResources().getColor(R.color.red));
            //    holder.keterangan.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.nominal.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.status.setText(singleItem.getStatustransaksi());
              //  holder.CardBG.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().getColor(R.color.red)));
              //  holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.white_circle));
              //  holder.LeftStat.setBackground(mContext.getResources().getDrawable(R.color.red));

            } else {
                holder.text.setTextColor(colorCodeDark);
              //  holder.keterangan.setTextColor(colorCodeDark);
                holder.nominal.setTextColor(colorCodeDark);
                holder.status.setTextColor(colorCodeDark);
                holder.status.setText(singleItem.getStatustransaksi());
              //  holder.background.setImageTintList(ColorStateList.valueOf(colorCodeDark));
              //  holder.CardBG.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
              //  holder.background.setBackground(mContext.getResources().getDrawable(R.drawable.white_circle));
               // holder.LeftStat.setBackgroundColor(colorCodeDark);
            }
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, OrderDetailActivity.class);
                    i.putExtra("id_pelanggan", singleItem.getIdPelanggan());
                    i.putExtra("id_transaksi", singleItem.getIdTransaksi());
                    i.putExtra("response", String.valueOf(singleItem.status));
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(i);
                }
            });
            holder.MoreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("STATUS", "" + singleItem.getStatus());
                    if(singleItem.getStatus() < 9){
                        Intent i = new Intent(mContext, OrderDetailActivity.class);
                        i.putExtra("id_pelanggan", singleItem.getIdPelanggan());
                        i.putExtra("id_transaksi", singleItem.getIdTransaksi());
                        i.putExtra("response", String.valueOf(singleItem.status));
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(i);
                    }else{
                        Intent toOrder = new Intent(mContext, PendingActivity.class);
                        toOrder.putExtra("id_transaksi", singleItem.getIdTransaksi());
                        toOrder.putExtra("id_pelanggan", singleItem.getIdPelanggan());
                        toOrder.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(toOrder);
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text, tanggal, nominal, keterangan,status;
        ImageView background, images;
        FrameLayout itemlayout;
        View LeftStat;
        LinearLayout MoreInfo, CardBG;

        ItemRowHolder(View itemView) {
            super(itemView);
       //     CardBG = itemView.findViewById(R.id.CardBG);
        //    background = itemView.findViewById(R.id.background);
            images = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
          //  tanggal = itemView.findViewById(R.id.texttanggal);
            nominal = itemView.findViewById(R.id.price);
          //  keterangan = itemView.findViewById(R.id.textket);
            itemlayout = itemView.findViewById(R.id.mainlayout);
            status = itemView.findViewById(R.id.status);
          //  LeftStat = itemView.findViewById(R.id.leftstat);
            MoreInfo = itemView.findViewById(R.id.MoreInfo);
        }
    }


}
