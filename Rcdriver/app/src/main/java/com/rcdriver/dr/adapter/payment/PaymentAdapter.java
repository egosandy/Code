package com.rcdriver.dr.adapter.payment;

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

import com.rcdriver.dr.R;
import com.rcdriver.dr.models.payment.PaymentMethod;
import com.rcdriver.dr.utils.Log;
import com.rcdriver.dr.utils.OnItemPaymentSelected;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

        Picasso.get().load(method.getImage())
                .placeholder(R.drawable.image_placeholder)
                .resize(80, 80)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("TAG", "onError:" + e.getMessage());
                    }
                });

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
