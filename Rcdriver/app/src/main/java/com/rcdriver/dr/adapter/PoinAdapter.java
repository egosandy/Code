package com.rcdriver.dr.adapter;


import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class PoinAdapter extends RecyclerView.Adapter<PoinAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(PointModel item);
    }
    private final List<PointModel> items;
    private final OnItemClickListener listener;

    public PoinAdapter(List<PointModel> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_point, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text,desc, tanggal,poin;
        ImageView images;
        LinearLayout IdPoint;
        public ViewHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.thumbnail);
            desc = itemView.findViewById(R.id.tvDes);
            text = itemView.findViewById(R.id.tvNama);
            poin = itemView.findViewById(R.id.tvPoint);
            tanggal = itemView.findViewById(R.id.tvTanggal);
            IdPoint = itemView.findViewById(R.id.IdPoint);
        }

        public void bind(final PointModel item, final OnItemClickListener listener) {
            text.setText(item.getNama());
            desc.setText(item.getKeterangan());
            poin.setText("Tukar " + item.getPoin() + "Poin");
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date expdate = null;
            try {
                expdate = newDateFormat.parse(item.getExpire());
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
                tanggal.setTextColor(Color.parseColor("#E70B0B"));
                tanggal.setText("Tidak Tersedia.");
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tanggal.setText("Berlaku Sampai " + myDateString);
                    tanggal.setTextColor(Color.parseColor("#4c84ff"));
                }
            }
            Picasso.get()
                   .load(Constants.IMAGESPOIN + item.getFoto())
//                   .placeholder(R.drawable.nocamera)
//                    .error(R.drawable.nocamera)
                    .into(images);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}