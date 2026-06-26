package com.rcdriver.cs.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.Locale;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.ActivityInfoMitra;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.MerchantNearModel;

/**
 * Created by otacodes on 3/24/2019.
 */

public class MerchantNearItem extends RecyclerView.Adapter<MerchantNearItem.ItemRowHolder> {

    private final List<MerchantNearModel> dataList;
    private final Context mContext;
    private final int rowLayout;

    public MerchantNearItem(Context context, List<MerchantNearModel> dataList, int rowLayout) {
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

    void clearGlideDiskCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();
            }
        }).start();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final MerchantNearModel singleItem = dataList.get(position);
        holder.name.setText(singleItem.getNama_merchant());
        if (!singleItem.getFoto_merchant().isEmpty()) {
            Glide.get(mContext).clearMemory();
            clearGlideDiskCache();
            String baseurl = Constants.IMAGESMERCHANT + singleItem.getFoto_merchant();
            Glide.with(mContext)
                    .asBitmap()
                    .load(baseurl)
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .override(1080, 600)
                    .listener(new RequestListener<Bitmap>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .apply(RequestOptions.placeholderOf(R.drawable.logo))
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.signatureOf(new ObjectKey(String.valueOf(System.currentTimeMillis()))))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                    .into(holder.images);
        }

        if (singleItem.getStatus_promo().equals("1")) {
            holder.promobadge.setVisibility(View.VISIBLE);
            holder.shimmer.startShimmerAnimation();
        } else {
            holder.promobadge.setVisibility(View.GONE);
            holder.shimmer.stopShimmerAnimation();
        }

        holder.address.setText(singleItem.getAlamat_merchant());
        float km = Float.parseFloat(singleItem.getDistance());
        String format = String.format(Locale.US, "%.1f", km);
        holder.distance.setText(format + "km");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ActivityInfoMitra.class);
                i.putExtra("lat", singleItem.getLatitude_merchant());
                i.putExtra("lon", singleItem.getLongitude_merchant());
                i.putExtra("id", singleItem.getId_merchant());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView name, address, distance;
        ImageView images;
        ShimmerFrameLayout shimmer;
        FrameLayout promobadge;

        ItemRowHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.namakategori);
            shimmer = itemView.findViewById(R.id.shimreview);
            promobadge = itemView.findViewById(R.id.promobadge);
            address = itemView.findViewById(R.id.content);
            distance = itemView.findViewById(R.id.distance);
        }
    }
}
