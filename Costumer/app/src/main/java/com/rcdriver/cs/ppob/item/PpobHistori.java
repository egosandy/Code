package com.rcdriver.cs.ppob.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.ppob.model.HistoriModels;
import com.rcdriver.cs.utils.Utility;

public class PpobHistori extends RecyclerView.Adapter<PpobHistori.ItemRowHolder>{
    private final List<HistoriModels> dataList;
    private final Context mContext;
    private final int rowLayout;
    private final OnItemClickListener listener;
    private int selectedPosition = 0;
    public PpobHistori(Context context, List<HistoriModels> dataList, int rowLayout, OnItemClickListener listener) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final HistoriModels singleItem = dataList.get(position);
        holder.noreff.setText(singleItem.getReff());
        holder.tgl.setText(singleItem.getTanggal());
        holder.operator.setText(singleItem.getOperator());
        Utility.currencyTXT(holder.harga, singleItem.getBiaya(), mContext);
        holder.status.setText(singleItem.getStatus());
        if (singleItem.getStatus().equals("SUCCESS")) {
            holder.noreff.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.noreff.setTextColor(mContext.getResources().getColor(R.color.orange));
        }
        if (position == selectedPosition) {
            holder.noreff.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.background.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.noreff.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.background.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
        holder.bind(singleItem, listener);
        holder.setIsRecyclable(false);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public interface OnItemClickListener {
        void onItemClick(HistoriModels item);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView noreff,tgl,operator,harga,status;
        CardView background;
        ItemRowHolder(View itemView) {
            super(itemView);
            noreff = itemView.findViewById(R.id.noreff);
            tgl = itemView.findViewById(R.id.tgl);
            operator = itemView.findViewById(R.id.operator);
            harga = itemView.findViewById(R.id.harga);
            status = itemView.findViewById(R.id.status);
            background = itemView.findViewById(R.id.background);
        }

        public void bind(final HistoriModels item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                    selectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });


        }
    }
}
