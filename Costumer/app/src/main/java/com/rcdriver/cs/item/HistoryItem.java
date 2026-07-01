package com.rcdriver.cs.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.AllTransaksiModel;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.Utility;

import cz.msebera.android.httpclient.util.TextUtils;

public class HistoryItem extends RecyclerView.Adapter<HistoryItem.ItemRowHolder> {

    public static String IsSaldo;
    private final List<AllTransaksiModel> dataList;
    private final Context mContext;
    private final int rowLayout;

    public HistoryItem(Context context, List<AllTransaksiModel> dataList, int rowLayout) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final AllTransaksiModel singleItem = dataList.get(position);
        holder.text.setText("Order " + singleItem.getFitur());
        Utility.currencyTXT(holder.nominal, singleItem.getBiayaakhir(), mContext);
        holder.status.setText(singleItem.getStatustransaksi());
        int year = Calendar.getInstance().get(Calendar.YEAR);
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM", Locale.US);
        String finalDate = timeFormat.format(singleItem.getWaktuOrder()) + " " + year;
        holder.rating.setText(singleItem.getRate());
        PicassoTrustAll.getInstance(mContext)
                .load(Constants.IMAGESFITUR + singleItem.getIcon())
                .into(holder.images);

        holder.MoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistoriDialog(singleItem.getIdTransaksi(), finalDate, singleItem.getFitur(), singleItem.isPakaiWallet(), singleItem.getBiayaakhir(), singleItem.getStatus(), singleItem.getAlamatAsal(), singleItem.getAlamatTujuan());
            }
        });

        // Mengatur jumlah gambar bintang berdasarkan angka rate
        double rating = 0.0;
        if (!TextUtils.isEmpty(singleItem.getRate())) {
            rating = Double.parseDouble(singleItem.getRate());
        }
        setStarRating(holder, rating);
    }

    private void setStarRating(ItemRowHolder holder, double rating) {
        ImageView[] stars = { holder.star1, holder.star2, holder.star3, holder.star4, holder.star5 };

        // Mengatur semua gambar bintang menjadi tidak terlihat
        for (ImageView star : stars) {
            star.setVisibility(View.GONE);
        }

        // Memeriksa apakah rating bukan String kosong
        if (rating > 0) {
            // Mengatur gambar bintang sesuai dengan rating
            for (int i = 0; i < (int) rating; i++) {
                stars[i].setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text, status, nominal, rating;
        ImageView images;
        LinearLayout MoreInfo;
        FrameLayout itemlayout;
        ImageView star1, star2, star3, star4, star5;

        ItemRowHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            status = itemView.findViewById(R.id.status);
            nominal = itemView.findViewById(R.id.price);
            itemlayout = itemView.findViewById(R.id.mainlayout);
            rating = itemView.findViewById(R.id.rate);
            MoreInfo = itemView.findViewById(R.id.MoreInfo);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
        }
    }

    private void showHistoriDialog(String idTransaksi, String finalDate, String fitur, boolean isPakaiWallet, String biayaAkhir, String status, String alamatAsal, String alamatTujuan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_histori, null);

        // Set up the dialog view
        TextView idTransaksiTextView = dialogView.findViewById(R.id.id_transaksi);
        TextView tanggalTextView = dialogView.findViewById(R.id.tanggal);
        TextView fiturTextView = dialogView.findViewById(R.id.fitur);
        TextView pembayaranTextView = dialogView.findViewById(R.id.pembayaran);
        TextView biayaAkhirTextView = dialogView.findViewById(R.id.biaya_akhir);
        ImageView btnclose = dialogView.findViewById(R.id.close);
        TextView alamatAsalTextView = dialogView.findViewById(R.id.alamat_asal);
        TextView alamatTujuanTextView = dialogView.findViewById(R.id.alamat_tujuan);
        TextView txtstatus = dialogView.findViewById(R.id.status_transaksi);
        idTransaksiTextView.setText(idTransaksi);
        tanggalTextView.setText(finalDate);
        fiturTextView.setText(fitur);
        String jenisPembayaran = isPakaiWallet ? "Saldo" : "Tunai";
        pembayaranTextView.setText(jenisPembayaran);
        biayaAkhirTextView.setText(biayaAkhir);

        txtstatus.setText(status);



        alamatAsalTextView.setText(alamatAsal);
        alamatTujuanTextView.setText(alamatTujuan);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
