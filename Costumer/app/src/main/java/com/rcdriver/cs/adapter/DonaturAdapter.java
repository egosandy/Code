package com.rcdriver.cs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.Donatur;
import com.rcdriver.cs.utils.Utility;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DonaturAdapter extends RecyclerView.Adapter<DonaturAdapter.VH> {
    private Context context;
    private List<Donatur> donaturList;

    public DonaturAdapter(Context context, List<Donatur> donaturList){
        this.context = context;
        this.donaturList = donaturList;
    }

    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donatur, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Donatur donatur = donaturList.get(position);
        holder.nama.setText(donatur.getNama_pengirim());
        holder.jumlah.setText(Utility.toformatRupiah(donatur.getMasuk()));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mm", Locale.getDefault());
        holder.tanggal.setText(df.format(donatur.getRegtime()));

    }

    @Override
    public int getItemCount() {
        return donaturList.size();
    }


    class VH extends RecyclerView.ViewHolder{
        private RelativeLayout item;
        private TextView nama, jumlah, tanggal;

        public VH(View view){
            super(view);
            item = view.findViewById(R.id.item);
            nama = view.findViewById(R.id.text_nama);
            jumlah = view.findViewById(R.id.text_jumlah);
            tanggal = view.findViewById(R.id.text_tanggal);

        }
    }
}
