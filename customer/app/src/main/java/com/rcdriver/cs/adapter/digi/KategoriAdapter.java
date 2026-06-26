package com.rcdriver.cs.adapter.digi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.digi.HomeDigiActivity;
import com.rcdriver.cs.activity.digi.PascaBayarActivity;
import com.rcdriver.cs.activity.digi.PrabayarActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.digi.Kategori;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.VH> {
    private Context context;
    private List<Kategori> kategoriList;

    public KategoriAdapter(Context context, List<Kategori> kategoriList){
        this.context = context;
        this.kategoriList = kategoriList;
    }

    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_digi_kategori, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        final Kategori kategori = kategoriList.get(position);
        Glide.with(context)
                .load(kategori.getIcon())
                .error(R.drawable.nocamera)
                .placeholder(R.drawable.nocamera)
                .into(holder.imageView);
        holder.nama.setText(kategori.getNama());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kategori.getTipe().equalsIgnoreCase("1")){
                    Intent intent = new Intent(context, PrabayarActivity.class);
                    intent.putExtra(Constants.METHOD, kategori.getId());
                    intent.putExtra(Constants.METHOD_NAME, kategori.getNama());
                    intent.putExtra(Constants.METHOD_TYPE, kategori.getTipe());
                    intent.putExtra(Constants.IS_INQUIRY, kategori.getInq());
                    context.startActivity(intent);
                }else if(kategori.getTipe().equalsIgnoreCase("2")){
                    Intent intent = new Intent(context, PascaBayarActivity.class);
                    intent.putExtra(Constants.METHOD, kategori.getId());
                    intent.putExtra(Constants.METHOD_NAME, kategori.getNama());
                    intent.putExtra(Constants.METHOD_TYPE, kategori.getTipe());
                    intent.putExtra(Constants.IS_INQUIRY, kategori.getInq());
                    context.startActivity(intent);
                }

            }
        });

        if(kategori.getId().equalsIgnoreCase("0")){
            holder.bind(kategori);
        }
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    class VH extends RecyclerView.ViewHolder{
        private LinearLayout item;
        private TextView nama;
        private ImageView imageView;
        public VH(View view){
            super(view);
            item = view.findViewById(R.id.item);
            nama = view.findViewById(R.id.text_nama);
            imageView = view.findViewById(R.id.imageView3);

        }

        public void bind(final Kategori menu){
            if(menu.getId().equalsIgnoreCase("0")){
                Glide.with(context)
                        .load(menu.getIcon())
                        .placeholder(R.drawable.more_ppob)
                        .into(imageView);

                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inten = new Intent(context, HomeDigiActivity.class);
                        inten.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(inten);
                    }
                });
            }
        }
    }



}
