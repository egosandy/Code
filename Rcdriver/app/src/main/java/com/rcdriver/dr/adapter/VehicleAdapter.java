package com.rcdriver.dr.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rcdriver.dr.R;
import com.rcdriver.dr.activity.EditKendaraanActivity;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.models.Kendaraan;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VH> {
    private Context context;
    private List<Kendaraan> kendaraanList;

    public VehicleAdapter(Context context, List<Kendaraan> kendaraanList){
        this.context = context;
        this.kendaraanList = kendaraanList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kendaraan, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Kendaraan kendaraan = kendaraanList.get(position);
        holder.merk.setText(kendaraan.getMerek());
        holder.stnk.setText(kendaraan.getNostnk());
        holder.warna.setText(kendaraan.getWarna());
        holder.tipe.setText(kendaraan.getTipe());
        holder.plat.setText(kendaraan.getPlatnomor());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditKendaraanActivity.class);
                i.putExtra(Constants.INTENT_ID, kendaraan.getIdk());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return kendaraanList.size();
    }

    class VH extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView merk, stnk, tipe, warna, plat;
        public VH(View view){
            super(view);
            item = view.findViewById(R.id.item);
            merk = view.findViewById(R.id.merek);
            stnk = view.findViewById(R.id.text_stnk);
            tipe = view.findViewById(R.id.text_tipe);
            warna = view.findViewById(R.id.text_warna);
            plat = view.findViewById(R.id.text_nomor);
        }
    }
}
