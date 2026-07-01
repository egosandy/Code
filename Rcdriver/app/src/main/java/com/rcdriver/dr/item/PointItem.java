package com.rcdriver.dr.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.models.PointModel;

/**
 * Created by otacodes on 3/24/2019.
 */

public class PointItem extends RecyclerView.Adapter<PointItem.ItemRowHolder> {
    public static String Warna = "#1AC463";
    private List<PointModel> dataList;
    private Context mContext;
    private int rowLayout;
    private ClickListener clickListener;
    private PointModel pointModel;
    public PointItem(Context context, List<PointModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;

    }
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void click(PointModel pointModel);
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
        final PointModel data = dataList.get(position);
        holder.text.setText(data.getNama());
        holder.desc.setText(data.getKeterangan());
        holder.poin.setText(data.getPoin() + "Poin");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expdate = null;
        try {
            expdate = newDateFormat.parse(data.getExpire());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newDateFormat.applyPattern("dd MMM yyyy");
        String myDateString = newDateFormat.format(expdate);
        newDateFormat.setLenient(false);
        Date expiry = null;
        try {
            expiry = newDateFormat.parse(myDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean expired = Objects.requireNonNull(expiry).before(new Date());
        if (expired == true) {
            holder.tanggal.setTextColor(Color.parseColor("#E70B0B"));
            holder.tanggal.setText("Tidak Tersedia.");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.tanggal.setText("Berlaku Sampai " + myDateString);
                holder.tanggal.setTextColor(Color.parseColor("#0bb058"));
            }
        }
        Picasso.get()
                .load(Constants.IMAGESPOIN + data.getFoto())
                .placeholder(R.drawable.nocamera)
                .error(R.drawable.nocamera)
                .into(holder.images);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }
    static class ItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text,desc, tanggal,poin;
        ImageView images;
        LinearLayout IdPoint;
        ItemRowHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.thumbnail);
            desc = itemView.findViewById(R.id.tvDes);
            text = itemView.findViewById(R.id.tvNama);
            poin = itemView.findViewById(R.id.tvPoint);
            tanggal = itemView.findViewById(R.id.tvTanggal);
            IdPoint = itemView.findViewById(R.id.IdPoint);
            IdPoint.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            v.setOnClickListener(this);
        }
    }
}
