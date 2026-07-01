package com.rcdriver.cs.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.DriverModel;

public class ListDriverAdapter extends RecyclerView.Adapter<ListDriverAdapter.PredictionHolder>{
    private List<DriverModel> mResultList = new ArrayList<>();
    private Activity mContext;
    private ListDriverClick listDriverClick;
    public ListDriverAdapter(List<DriverModel> mResultList, Activity context) {
        this.mResultList = mResultList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public PredictionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.list_driver, viewGroup, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder mPredictionHolder, final int i) {
        final DriverModel driverModel = mResultList.get(i);
        mPredictionHolder.namadriver.setText(mResultList.get(i).getNamaDriver());
        String url = mResultList.get(i).getFoto();
        Log.e("FotoDriver", url);
        Glide.with(mContext)
                .load(url)
                .error(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder)
                .apply(RequestOptions.circleCropTransform())
                .apply(new RequestOptions().override(1024, 512))
                .into(mPredictionHolder.coverImageView);
        mPredictionHolder.ratebar.setRating(mResultList.get(i).getRating());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDriverClick.onItemClick(driverModel);
            }
        };
        mPredictionHolder.mRow.setOnClickListener(listener);
        mPredictionHolder.coverImageView.setOnClickListener(listener);
        mPredictionHolder.namadriver.setOnClickListener(listener);
    }

    /*@Override
    public int getItemCount() {
        return mResultList.size();
    }*/
    @Override
    public int getItemCount() {
        return (null != mResultList ? mResultList.size() : 0);
    }

    public class PredictionHolder extends RecyclerView.ViewHolder{
        ImageView coverImageView;
        TextView namadriver;
        RatingBar ratebar;
        LinearLayout mRow;
        PredictionHolder(View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.thumbnail);
            namadriver = itemView.findViewById(R.id.namadriver);
            mRow = itemView.findViewById(R.id.card_view);
            ratebar = itemView.findViewById(R.id.RateBar);
        }
    }

    public ListDriverClick getOnItemClickListener() {
        return listDriverClick;
    }

    public void setOnItemClickListener(ListDriverClick onItemClickListener) {
        this.listDriverClick = onItemClickListener;
    }
}