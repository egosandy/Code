package com.rcdriver.mt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rcdriver.mt.R;
import com.rcdriver.mt.models.payment.PaymentMethod;
import com.rcdriver.mt.utils.OnItemPaymentSelected;
import com.rcdriver.mt.utils.PicassoTrustAll;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.VH> {
    private Context context;
    private List<PaymentMethod> paymentMethodList;
    private OnItemPaymentSelected onItemPaymentSelected;
    public PaymentAdapter(Context context, List<PaymentMethod> paymentMethodList, OnItemPaymentSelected onItemPaymentSelected){
        this.context = context;
        this.paymentMethodList = paymentMethodList;
        this.onItemPaymentSelected = onItemPaymentSelected;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final PaymentMethod method = paymentMethodList.get(position);
        PicassoTrustAll.getInstance(context)
                .load(method.getImage())
                .error(R.drawable.logo)
                .into(holder.imageView);
        holder.nama.setText(method.getNama());
        holder.keterangan.setText(method.getKeterangan());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemPaymentSelected.onItemSelected(method);
            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentMethodList.size();
    }



    class VH extends RecyclerView.ViewHolder{
        private RelativeLayout item;
        private TextView nama, keterangan;
        private ImageView imageView;
        private RadioButton check;
        public VH(View view){
            super(view);
            item = view.findViewById(R.id.rootLayout);
            nama = view.findViewById(R.id.namabank);
            keterangan = view.findViewById(R.id.norekening);
            imageView = view.findViewById(R.id.images);
            check = view.findViewById(R.id.check);

        }
    }
}
