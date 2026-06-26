package com.rcdriver.cs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.SaveLokasiModel;

public class AlamatAdapter extends RecyclerView.Adapter<AlamatAdapter.PredictionHolder> {
    private final List<SaveLokasiModel> mResultList;
    private Context mContext;
    private ClickListener clickListener;
    public AlamatAdapter(List<SaveLokasiModel> mResultList, Context context) {
        this.mResultList = mResultList;
        this.mContext = context;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public interface ClickListener {
        void click(SaveLokasiModel lokasiModel);
    }
    @NonNull
    @Override
    public PredictionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.view_placesearch, viewGroup, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder mPredictionHolder, final int i) {
        mPredictionHolder.address.setText(mResultList.get(i).getAlamat());
        mPredictionHolder.area.setText(mResultList.get(i).getNama());
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class PredictionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView address,area;
        private RelativeLayout mRow;
        PredictionHolder(View itemView) {
            super(itemView);
            area = itemView.findViewById(R.id.place_area);
            address = itemView.findViewById(R.id.place_address);
            mRow = itemView.findViewById(R.id.place_item_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SaveLokasiModel item = mResultList.get(getAdapterPosition());
            if (v.getId() == R.id.place_item_view) {
                clickListener.click(item);
            }
        }
    }
}