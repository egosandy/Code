package com.rcdriver.cs.item;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.MerchantCatActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.CatMerchantModel;

public class KategoriMerchant extends RecyclerView.Adapter<KategoriMerchant.ItemRowHolder> {
    private List<CatMerchantModel> dataList;
    private Context mContext;
    private int rowLayout;

    public KategoriMerchant(Context context, List<CatMerchantModel> dataList, int rowLayout){
        this.mContext = context;
        this.dataList = dataList;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
        final CatMerchantModel model = dataList.get(position);
        holder.text.setText(model.getNama_kategori().toString());
        Glide.with(mContext)
                .load(Constants.IMAGESKATMERCHANT + model.getFoto_kategori())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.image);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MerchantCatActivity.class);
                intent.putExtra("idcat", model.getId_kategori_merchant());
                intent.putExtra("namacat", model.getNama_kategori());
                intent.putExtra("mode", "kategori");
                intent.putExtra("section", "0");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView image;
        RelativeLayout layout;
        ItemRowHolder(View itemView){
            super(itemView);
            layout = itemView.findViewById(R.id.main);
            image = itemView.findViewById(R.id.images);
            text = itemView.findViewById(R.id.text);
        }
    }
}
