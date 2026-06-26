package com.rcdriver.cs.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.VoucherModel;

public class FiturPromoAdapter extends RecyclerView.Adapter<FiturPromoAdapter.PredictionHolder> {
    private final List<VoucherModel> mResultList;
    private Activity mContext;
    private ClickListener clickListener;
    public FiturPromoAdapter(List<VoucherModel> mResultList, Activity context) {
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
        View convertView = layoutInflater.inflate(R.layout.list_fiturpromo, viewGroup, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder mPredictionHolder, final int i) {
        mPredictionHolder.titleTextView.setText(mResultList.get(i).getNama());
        mPredictionHolder.codeTextView.setText(mResultList.get(i).getKode());
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class PredictionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView codeTextView;
        LinearLayout mRow;
        PredictionHolder(View itemView) {
            super(itemView);
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