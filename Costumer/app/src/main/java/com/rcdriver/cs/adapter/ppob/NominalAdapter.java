package com.rcdriver.cs.adapter.ppob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.ppob.model.ListModels;
import com.rcdriver.cs.utils.OnDenomSelected;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NominalAdapter extends RecyclerView.Adapter<NominalAdapter.VH> {
    private Context context;
    private int lastSelectedPosition = -1;
    private List<ListModels> listModels;
    private OnDenomSelected onDenomSelected;

    public NominalAdapter(Context context, List<ListModels> listModels, OnDenomSelected onDenomSelected){
        this.context = context;
        this.listModels = listModels;
        this.onDenomSelected = onDenomSelected;
    }

    @NotNull
    @Override
    public VH onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operator, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NotNull VH holder, int position) {
        final ListModels models = listModels.get(position);
        holder.nama.setText(models.getKeterangan());
        holder.check.setChecked(lastSelectedPosition == position);
        holder.check.setTag(position);
        holder.item.setTag(position);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChange(v);
                onDenomSelected.onDenomlick(models.getKode(), models.getHarga(), models.getKeterangan());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    private void itemCheckChange(View view) {
        lastSelectedPosition =(Integer)view.getTag();
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder{
        private RelativeLayout item;
        private TextView nama;
        private RadioButton check;
        public VH(View view){
            super(view);
            item = view.findViewById(R.id.item);
            nama = view.findViewById(R.id.caption);
            check = view.findViewById(R.id.check);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check.setChecked(lastSelectedPosition == getAdapterPosition());
                }
            });
        }
    }
}
