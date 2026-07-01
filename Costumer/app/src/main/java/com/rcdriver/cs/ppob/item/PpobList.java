package com.rcdriver.cs.ppob.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.rcdriver.cs.R;
import com.rcdriver.cs.ppob.model.ListModels;
import com.rcdriver.cs.utils.Utility;

public class PpobList extends RecyclerView.Adapter<PpobList.ItemRowHolder>{
    private final List<ListModels> dataList;
    private final Context mContext;
    private final int rowLayout;
    private final OnItemClickListener listener;
    private int selectedPosition = 0;
    public PpobList(Context context, List<ListModels> dataList, int rowLayout, PpobList.OnItemClickListener listener) {
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
        final ListModels singleItem = dataList.get(position);
        holder.text.setText(singleItem.getOperator());
        Utility.currencyTXT(holder.text2, singleItem.getHarga(), mContext);
        Glide.with(mContext)
                .load(singleItem.getIkon())
                .apply(new RequestOptions().override(512, 512))
                .error(R.drawable.noimages)
                .placeholder(R.drawable.noimages)
                .into(holder.images);
        if (position == selectedPosition) {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.background.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.black));
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
        void onItemClick(ListModels item);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text,text2;
        CardView background;
        ImageView images;
        ItemRowHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            text2 = itemView.findViewById(R.id.text2);
            background = itemView.findViewById(R.id.background);
            images = itemView.findViewById(R.id.images);
        }

        public void bind(final ListModels item, final OnItemClickListener listener) {

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
    private static String formatRupiah(Long number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
