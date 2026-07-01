package com.rcdriver.cs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.DonasiActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.Donasi;
import com.rcdriver.cs.utils.Utility;
import com.google.gson.Gson;

import java.util.List;

public class DonasiAdapter extends RecyclerView.Adapter<DonasiAdapter.ViewHolder> {
    private List<Donasi> models;
    private Context context;

    public DonasiAdapter(List<Donasi> models, Context context) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donasi menu = models.get(position);

        // Menggunakan Glide atau metode lain untuk mengisi data
        Glide.with(context)
                .load(Constants.IMAGESSLIDER + menu.getGambar())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.imageView);

        holder.judul.setText(menu.getJudul());
        holder.total.setText("Jumlah terkumpul Rp" + Utility.toformatRupiah(menu.getTotal()));
        holder.wd.setText("Jumlah tersalurkan Rp" + Utility.toformatRupiah(menu.getWithdraw()));

        holder.slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonasiActivity.class);
                intent.putExtra(Constants.DATA, new Gson().toJson(menu));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout slider;
        TextView judul, total, wd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            slider = itemView.findViewById(R.id.slider);
            judul = itemView.findViewById(R.id.textView15);
            total = itemView.findViewById(R.id.textView16);
            wd = itemView.findViewById(R.id.textView20);
        }
    }
}
