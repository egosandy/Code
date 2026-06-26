package com.rcdriver.cs.adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.DriverModel;
import com.rcdriver.cs.utils.PicassoTrustAll;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.PredictionHolder> {
    private final List<DriverModel> mResultList;
    private Context mContext;
    private ClickListener clickListener;
    private LatLng lokasics;
    public DriverAdapter(List<DriverModel> mResultList, Context context, LatLng lokcs) {
        this.mResultList = mResultList;
        this.mContext = context;
        this.lokasics = lokcs;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public interface ClickListener {
        void click(DriverModel lokasiModel);
    }
    @NonNull
    @Override
    public PredictionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(R.layout.item_driver, viewGroup, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionHolder mPredictionHolder, final int i) {
        mPredictionHolder.nama.setText(mResultList.get(i).getNamaDriver());
        PicassoTrustAll.getInstance(mContext)
                .load(mResultList.get(i).getFoto())
                .resize(140, 140)
                .into(mPredictionHolder.image);
        //-------------------------------------------------------------------
        LatLng driver = new LatLng(mResultList.get(i).getLatitude(),mResultList.get(i).getLongitude());
        Location locationA = new Location("Point A");
        locationA.setLatitude(lokasics.latitude);
        locationA.setLongitude(lokasics.longitude);
        Location locationB = new Location("Point B");
        locationB.setLatitude(driver.latitude);
        locationB.setLongitude(driver.longitude);
        double distance = locationA.distanceTo(locationB) / 1000;   // in km
        String jarak = String.valueOf(distance);
        String cutString = jarak.substring(0, 4);
        mPredictionHolder.jarak.setText("Jarak " + cutString + "Km");
        mPredictionHolder.kendaraan.setText(mResultList.get(i).getMerek() + " | " + mResultList.get(i).getNomor_kendaraan());
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class PredictionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nama,jarak,kendaraan;
        private ImageView image;
        private CardView mRow;
        PredictionHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            image = itemView.findViewById(R.id.image);
            jarak = itemView.findViewById(R.id.jarak);
            mRow = itemView.findViewById(R.id.rootLayout);
            kendaraan = itemView.findViewById(R.id.kendaraan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DriverModel item = mResultList.get(getAdapterPosition());
            if (v.getId() == R.id.rootLayout) {
                clickListener.click(item);
            }
        }
    }
}