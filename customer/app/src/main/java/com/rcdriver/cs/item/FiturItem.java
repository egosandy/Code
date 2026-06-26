package com.rcdriver.cs.item;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.AllMerchantActivity;
import com.rcdriver.cs.activity.OjekNewActivity;
import com.rcdriver.cs.activity.PengembanganActivity;
import com.rcdriver.cs.activity.PromoActivity;
import com.rcdriver.cs.activity.RentCarActivity;
import com.rcdriver.cs.activity.SendNewActivity;
import com.rcdriver.cs.activity.digi.HomeDigiActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.FiturDataModel;

/**
 * Created by otacodes on 3/24/2019.
 */

public class FiturItem extends RecyclerView.Adapter<FiturItem.ItemRowHolder> {

    private final List<FiturDataModel> dataList;
    private final Context mContext;
    private final int rowLayout;
    private final OnItemClickListener listener;

    public FiturItem(Context context, List<FiturDataModel> dataList, int rowLayout, OnItemClickListener listener) {
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
        final FiturDataModel singleItem = dataList.get(position);
        if (singleItem.getBackground() == null) {
            holder.background.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.gradient_start_color)));
        } else {
            int colorCodeDark = Color.parseColor(singleItem.getBackground());
            holder.background.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
        }
        holder.text.setText(singleItem.getFitur());
        Glide.with(mContext)
                .load(Constants.IMAGESFITUR + singleItem.getIcon())
                .apply(new RequestOptions().override(512, 512))
                .error(R.drawable.button_round_2)
                .placeholder(R.drawable.button_round_2)
                .into(holder.image);

        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleItemClick(singleItem);
            }
        });

        if (singleItem.getHome().equals("0") && singleItem.getIdFitur() == 100) {
            holder.bind(singleItem, listener);
        }
    }

    private void handleItemClick(FiturDataModel item) {
        Intent intent = null;

        switch (item.getHome()) {
            case "1":
                intent = new Intent(mContext, OjekNewActivity.class);
                intent.putExtra("job", item.getJob());
                break;
            case "2":
                intent = new Intent(mContext, SendNewActivity.class);
                intent.putExtra("job", item.getJob());
                break;
            case "3":
                intent = new Intent(mContext, RentCarActivity.class);
                break;
            case "4":
                intent = new Intent(mContext, AllMerchantActivity.class);
                break;
            case "5":
                intent = new Intent(mContext, HomeDigiActivity.class);
                break;
            case "6":
                intent = new Intent(mContext, PromoActivity.class);
                break;
            case "7":
                intent = new Intent(mContext, PengembanganActivity.class);
                break;
        }

        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("FiturKey", item.getIdFitur());
            intent.putExtra("background", item.getBackground());
            intent.putExtra("icon", item.getIcon());
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public interface OnItemClickListener {
        void onItemClick(FiturDataModel item);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;
        ImageView background;

        ItemRowHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
        }

        public void bind(final FiturDataModel item, final OnItemClickListener listener) {
            if (item.getHome().equals("0")) {
                background.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.defaultfitur)));
                Glide.with(mContext)
                        .load(R.drawable.more)
                        .override(80, 80)
                        .placeholder(R.drawable.nofitur)
                        .error(R.drawable.nofitur)
                        .into(image);
                background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(item);
                    }
                });
            }
        }
    }
}
