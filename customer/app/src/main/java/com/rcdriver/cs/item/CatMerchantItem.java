package com.rcdriver.cs.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.CatMerchantModel;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by otacodes on 3/24/2019.
 */

public class CatMerchantItem extends RecyclerView.Adapter<CatMerchantItem.ItemRowHolder> {

    private List<CatMerchantModel> dataList;
    private Context mContext;
    private int rowLayout;
    private int selectedPosition=0;
    private CatMerchantItem.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(CatMerchantModel item);
    }

    public CatMerchantItem(Context context, List<CatMerchantModel> dataList, int rowLayout, CatMerchantItem.OnItemClickListener listener) {
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
        final CatMerchantModel singleItem = dataList.get(position);

        holder.text.setText(singleItem.getNama_kategori());
       /* PicassoTrustAll.getInstance(mContext)
                .load(Constants.IMAGESKATMERCHANT + singleItem.getFoto_kategori())
                .resize(100, 100)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.images);*/
        Glide.with(mContext)
                .load(Constants.IMAGESKATMERCHANT + singleItem.getFoto_kategori())
                .transform(new CenterCrop(),new RoundedCorners(25))
                .apply(new RequestOptions().override(512, 512))
                .error(R.drawable.nocamera)
                .placeholder(R.drawable.nocamera)
                .into(holder.images);
        if (position == selectedPosition) {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.background.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.text.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.background.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
        holder.bind(singleItem,listener);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text;
        CardView background;
        CircleImageView images;
        ItemRowHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            background = itemView.findViewById(R.id.background);
            images = itemView.findViewById(R.id.images);
        }
        public void bind(final CatMerchantModel item, final CatMerchantItem.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                    selectedPosition=getAdapterPosition();
                    notifyDataSetChanged();
                }
            });


        }
    }


}
