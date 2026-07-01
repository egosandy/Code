package com.rcdriver.cs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.DonassiWd;
import com.rcdriver.cs.utils.Utility;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DonasiWdAdapter extends RecyclerView.Adapter<DonasiWdAdapter.VH> {
    private Context context;
    private List<DonassiWd> donassiWdList;

    public DonasiWdAdapter(Context context, List<DonassiWd> donassiWdList){
        this.context = context;
        this.donassiWdList = donassiWdList;
    }

    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donasi_wd, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final DonassiWd wd = donassiWdList.get(position);
        Glide.with(context)
                .load(Constants.IMAGESSLIDER + wd.getFoto())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.images);
        holder.title.setText(wd.getJudul());
        holder.nominal.setText(Utility.toformatRupiah(wd.getJumlah()));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mm", Locale.getDefault());
        holder.tanggal.setText(df.format(wd.getRegtime()));
    }

    @Override
    public int getItemCount() {
        return donassiWdList.size();
    }


    static class VH extends RecyclerView.ViewHolder {
        ImageView images;
        TextView title, nominal, tanggal;

        VH(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.imageView9);
            title = itemView.findViewById(R.id.textView22);
            nominal = itemView.findViewById(R.id.textView23);
            tanggal = itemView.findViewById(R.id.textView25);
        }
    }
}
