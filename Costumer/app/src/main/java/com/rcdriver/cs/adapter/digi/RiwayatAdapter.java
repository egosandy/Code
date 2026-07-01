package com.rcdriver.cs.adapter.digi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.digi.ResultTransaksiActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.digi.Transaksi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.VH> {
    private Context context;
    private List<Transaksi> transaksiList;

    public RiwayatAdapter(Context context, List<Transaksi> transaksiList){
        this.context = context;
        this.transaksiList = transaksiList;
    }

    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_digi_riwayat, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Transaksi transaksi = transaksiList.get(position);
        holder.tanggal.setText(transaksi.getRegtime());
        holder.harga.setText(transaksi.getHarga());
        holder.produk.setText(transaksi.getNamaProduk());
        holder.invoice.setText(transaksi.getInvoice());
        holder.status.setText(transaksi.getTrxStatus());
        Glide.with(context)
                .load(transaksi.getIcon())
                .error(R.drawable.nocamera)
                .placeholder(R.drawable.nocamera)
                .into(holder.imgKategori);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResultTransaksiActivity.class);
                intent.putExtra(Constants.METHOD, transaksi.getInvoice());
                intent.putExtra(Constants.METHOD_TYPE, transaksi.getTipe());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transaksiList.size();
    }

    class VH extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView tanggal, harga, produk, invoice, status;
        private ImageView imgKategori;
        public VH(View view){
            super(view);
            item = view.findViewById(R.id.rootLayout);
            tanggal = view.findViewById(R.id.texttanggal2);
            harga = view.findViewById(R.id.textharga);
            produk = view.findViewById(R.id.text);
            invoice = view.findViewById(R.id.texttanggal);
            status = view.findViewById(R.id.textket);
            imgKategori = view.findViewById(R.id.background);

        }
    }
}
