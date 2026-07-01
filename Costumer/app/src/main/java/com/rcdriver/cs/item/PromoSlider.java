package com.rcdriver.cs.item;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rcdriver.cs.activity.OjekNewActivity;
import com.rcdriver.cs.activity.SendNewActivity;
import com.squareup.picasso.Transformation;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.AllMerchantActivity;
import com.rcdriver.cs.activity.RentCarActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.FiturDataModel;
import com.rcdriver.cs.models.PromoModel;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.RoundedCornersTransformation;

/**
 * Created by otacodes on 3/24/2019.
 */

public class PromoSlider extends RecyclerView.Adapter<PromoSlider.ItemRowHolder> {

    private final List<PromoModel> dataList;
    private final Context mContext;
    private final int rowLayout;
    private final OnItemClickListener listener;

    public PromoSlider(Context context, List<PromoModel> dataList, int rowLayout, OnItemClickListener listener) {
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
        final PromoModel propertyModels = dataList.get(position);
        final int radius = 0;
        final int margin = 0;
        final Transformation transformation = new RoundedCornersTransformation(radius, margin);
        PicassoTrustAll.getInstance(mContext)
                .load(Constants.IMAGESSLIDER + propertyModels.getFoto())
                .resize(100, 100)
                .transform(transformation)
                .into(holder.imageView);
        if (propertyModels.getTypepromosi().equals("link")) {

            holder.slider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        String url = (propertyModels.getLinkpromosi());
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        mContext.startActivity(i);
                    } catch (Exception e) {
                        android.util.Log.e("Link", e.getMessage());
                        e.printStackTrace();
                    }

                }
            });

        } else {
            if (propertyModels.getFiturpromosi() == 1 || propertyModels.getFiturpromosi() == 2) {
                holder.slider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, OjekNewActivity.class);
                        i.putExtra("FiturKey", propertyModels.getFiturpromosi());
                        i.putExtra("icon", propertyModels.getIcon());
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(i);
                        android.util.Log.e("SliderKlik", String.valueOf(propertyModels.getFiturpromosi()));
                    }
                });
            } else if (propertyModels.getFiturpromosi() == 5) {
                holder.slider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, SendNewActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("FiturKey", propertyModels.getFiturpromosi());
                        i.putExtra("icon", propertyModels.getIcon());
                        mContext.startActivity(i);
                        android.util.Log.e("SliderKlik", String.valueOf(propertyModels.getFiturpromosi()));

                    }
                });
            } else if (propertyModels.getFiturpromosi() == 6) {
                holder.slider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, RentCarActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("FiturKey", propertyModels.getFiturpromosi());
                        i.putExtra("icon", propertyModels.getIcon());
                        mContext.startActivity(i);
                        android.util.Log.e("SliderKlik", String.valueOf(propertyModels.getFiturpromosi()));

                    }
                });
            } else if (propertyModels.getFiturpromosi() == 10 || propertyModels.getFiturpromosi() == 11 || propertyModels.getFiturpromosi() == 12 || propertyModels.getFiturpromosi() == 13) {
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, AllMerchantActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("FiturKey", propertyModels.getFiturpromosi());
                        mContext.startActivity(i);
                        android.util.Log.e("SliderKlik", String.valueOf(propertyModels.getFiturpromosi()));

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public interface OnItemClickListener {
        void onItemClick(PromoModel item);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView slider;

        ItemRowHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            slider = itemView.findViewById(R.id.slider);
        }

        public void bind(final FiturDataModel item, final OnItemClickListener listener) {

        }
    }
}
