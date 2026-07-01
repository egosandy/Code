package com.rcdriver.mt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.rcdriver.mt.R;
import com.rcdriver.mt.models.payment.JenisPayment;
import com.rcdriver.mt.utils.OnItemPaymentSelected;

import java.util.List;

public class JenisPaymentAdapter extends RecyclerView.Adapter<JenisPaymentAdapter.VH> {
    private Context context;
    private List<JenisPayment> jenisPaymentList;
    private OnItemPaymentSelected onItemPaymentSelected;
    public JenisPaymentAdapter(Context context, List<JenisPayment> jenisPaymentList, OnItemPaymentSelected onItemPaymentSelected){
        this.context = context;
        this.jenisPaymentList = jenisPaymentList;
        this.onItemPaymentSelected = onItemPaymentSelected;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_method, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final JenisPayment jenisPayment = jenisPaymentList.get(position);
        holder.textView.setText(jenisPayment.getJenis());
        PaymentAdapter adapter = new PaymentAdapter(context, jenisPayment.getPaymentMethodList(), onItemPaymentSelected);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return jenisPaymentList.size();
    }


    class VH extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private RecyclerView recyclerView;
        private TextView textView;
        public VH(View view){
            super(view);
            item = view.findViewById(R.id.item);
            textView = view.findViewById(R.id.textView);
            recyclerView = view.findViewById(R.id.rec_payment);
        }
    }
}
