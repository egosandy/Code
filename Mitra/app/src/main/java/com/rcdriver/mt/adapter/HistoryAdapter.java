package com.rcdriver.mt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rcdriver.mt.R;
import com.rcdriver.mt.activity.payment.PaymentFixedActivity;
import com.rcdriver.mt.constants.Constants;
import com.rcdriver.mt.models.payment.Transaksi;
import com.rcdriver.mt.utils.Utility;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {
    private Context context;
    private List<Transaksi> transaksiList;

    public HistoryAdapter(Context context, List<Transaksi> transaksiList){
        this.context = context;
        this.transaksiList = transaksiList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topup_history, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Transaksi transaksi = transaksiList.get(position);
        holder.metode.setText(transaksi.getMetodeNama());
        holder.tanggal.setText(transaksi.getRegtime());
        holder.invoice.setText(transaksi.getInvoice());
        holder.total.setText(Utility.toformatRupiah(transaksi.getTotal()));
        if(transaksi.getStatus() == 0){
            holder.status.setText("Created");
        }else if(transaksi.getStatus() == 1){
            holder.status.setText("Pending");
        }else if(transaksi.getStatus() == 2){
            holder.status.setText("Berhasil");
        }else {
            holder.status.setText("Gagal");
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transaksi.getMetodeJenis() == 2 || transaksi.getMetodeJenis() == 3){
                    Intent intent = new Intent(context, PaymentFixedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(Constants.INTENT_ID, transaksi.getInvoice());
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return transaksiList.size();
    }

    class VH extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView metode, tanggal, invoice, status, total;
        public VH(View view){
            super(view);
            item = view.findViewById(R.id.item);
            metode = view.findViewById(R.id.reference);
            tanggal = view.findViewById(R.id.text_tanggal);
            invoice = view.findViewById(R.id.text_downline);
            status = view.findViewById(R.id.text_fitur);
            total = view.findViewById(R.id.text_fee);
        }
    }
}
