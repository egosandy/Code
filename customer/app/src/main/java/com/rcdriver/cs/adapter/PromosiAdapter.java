package com.rcdriver.cs.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.VoucherModel;
import com.rcdriver.cs.utils.PicassoTrustAll;

public class PromosiAdapter extends RecyclerView.Adapter<PromosiAdapter.PredictionHolder> {
    private final List<VoucherModel> mResultList;
    private Activity mContext;
    private ClickListener clickListener;
    public PromosiAdapter(List<VoucherModel> mResultList, Activity context) {
        this.mResultList = mResultList;
        this.mContext = context;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public interface ClickListener {
        void click(VoucherModel promo);
    }
    @NonNull
    @Override
    public PredictionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.list_promo, viewGroup, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder mPredictionHolder, final int i) {
        mPredictionHolder.titleTextView.setText(mResultList.get(i).getNama());
        PicassoTrustAll.getInstance(mContext)
                .load(Constants.IMAGESSLIDER + mResultList.get(i).getImage())
                .resize(1024, 512)
                .placeholder(R.drawable.image_placeholder)
                .into(mPredictionHolder.coverImageView);
        mPredictionHolder.codeTextView.setText(mResultList.get(i).getKode());
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class PredictionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView coverImageView;
        TextView titleTextView;
        TextView codeTextView;
        LinearLayout mRow;
        PredictionHolder(View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.thumbnail);
            titleTextView = itemView.findViewById(R.id.title);
            codeTextView = itemView.findViewById(R.id.Kode);
            mRow = itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    VoucherModel item = mResultList.get(getAdapterPosition());
                    clickListener.click(item);

                }
            });

        }

        @Override
        public void onClick(View v) {
            VoucherModel item = mResultList.get(getAdapterPosition());
            if (v.getId() == R.id.rootLayout) {
                clickListener.click(item);
            }
        }
    }
}