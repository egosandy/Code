package com.rcdriver.cs.adapter.digi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rcdriver.cs.R;
import com.rcdriver.cs.models.digi.Operator;
import com.rcdriver.cs.utils.OnItemSelected;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.VH> {
    private Context context;
    private int lastSelectedPosition = -1;
    private List<Operator> operatorList;
    private OnItemSelected onItemSelected;

    public OperatorAdapter(Context context, List<Operator> operatorList, OnItemSelected onItemSelected){
        this.context = context;
        this.onItemSelected = onItemSelected;
        this.operatorList = operatorList;
    }

    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operator, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Operator models = operatorList.get(position);
        holder.nama.setText(models.getNama());
        holder.check.setChecked(lastSelectedPosition == position);
        holder.check.setTag(position);
        holder.item.setTag(position);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChange(v);
                onItemSelected.onItemClick(models.getId(), models.getNama(), models.getNamaKategori());
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
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
