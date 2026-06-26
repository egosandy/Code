package com.rcdriver.cs.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.ItemPesananModel;
import com.rcdriver.cs.utils.Utility;

/**
 * Created by Maswend team on 3/24/2019.
 */

public class ItemPesananItem extends RecyclerView.Adapter<ItemPesananItem.ItemRowHolder> {

    private final List<ItemPesananModel> dataList;
    private final int rowLayout;
    private Context mContext;

    public ItemPesananItem(List<ItemPesananModel> dataList, int rowLayout,Context context) {
        this.dataList = dataList;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final ItemPesananModel singleItem = dataList.get(position);
        if(singleItem.getTipe() == 1){
            holder.name.setText(singleItem.getNama_pesanan());
            holder.harga.setText("(est) " + Utility.toformatRupiah(singleItem.getTotal_harga()));
        }else{
            holder.name.setText(singleItem.getNama_item());
            holder.harga.setText(Utility.toformatRupiah(singleItem.getTotal_harga()));
        }

        holder.qty.setText(singleItem.getJumlah_item() + "x");



    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView name, qty,harga;

        ItemRowHolder(View itemView) {
            super(itemView);
            qty = itemView.findViewById(R.id.qty);
            name = itemView.findViewById(R.id.namaitem);
            harga = itemView.findViewById(R.id.harga);
        }
    }
}
