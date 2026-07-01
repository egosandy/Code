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
import com.rcdriver.cs.activity.RentCarActivity;
import com.rcdriver.cs.activity.SendNewActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.AllFiturModel;

/**
 * Created by otacodes on 3/24/2019.
 */

public class AllFiturItem extends RecyclerView.Adapter<AllFiturItem.ItemRowHolder> {

    private final List<AllFiturModel> dataList;
    private final Context mContext;
    private final int rowLayout;

    public AllFiturItem(Context context, List<AllFiturModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final AllFiturModel singleItem = dataList.get(position);
        if (singleItem.getBackground() == null) {
            holder.background.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.defaultfitur)));
        } else {
            int colorCodeDark = Color.parseColor(singleItem.getBackground());
            holder.background.setBackgroundTintList(ColorStateList.valueOf(colorCodeDark));
        }
        holder.text.setText(singleItem.getFitur());
        /*PicassoTrustAll.getInstance(mContext)
                .load(Constants.IMAGESFITUR + singleItem.getIcon())
                .resize(100, 100)
                .placeholder(R.drawable.nofitur)
                .error(R.drawable.nofitur)
                .into(holder.image);*/
        Glide.with(mContext)
                .load(Constants.IMAGESFITUR + singleItem.getIcon())
                .apply(new RequestOptions().override(512, 512))
                .error(R.drawable.nocamera)
                .placeholder(R.drawable.nocamera)
                .into(holder.image);
        if (!singleItem.getHome().equals("1")) {
            if (!singleItem.getHome().equals("2")) {
                if (singleItem.getHome().equals("3")) {
                    holder.background.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(mContext, RentCarActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("FiturKey", singleItem.getIdFitur());
                            i.putExtra("background", singleItem.getBackground());
                            i.putExtra("icon", singleItem.getIcon());
                            mContext.startActivity(i);

                        }
                    });
                } else if (singleItem.getHome().equals("4")) {
                    holder.background.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(mContext, AllMerchantActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("FiturKey", singleItem.getIdFitur());
                            mContext.startActivity(i);

                        }
                    });
                }
            } else {
                holder.background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, SendNewActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("FiturKey", singleItem.getIdFitur());
                        i.putExtra("job", singleItem.getJob());
                        i.putExtra("background", singleItem.getBackground());
                        i.putExtra("icon", singleItem.getIcon());
                        mContext.startActivity(i);

                    }
                });
            }
        } else {
            holder.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(mContext, OjekNewActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("FiturKey", singleItem.getIdFitur());
                    i.putExtra("job", singleItem.getJob());
                    i.putExtra("background", singleItem.getBackground());
                    i.putExtra("icon", singleItem.getIcon());
                    mContext.startActivity(i);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;
        ImageView background;
        ItemRowHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
        }
    }
}
