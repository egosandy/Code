package com.rcdriver.cs.item;

import com.rcdriver.cs.utils.LocalStore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.rcdriver.cs.R;
import com.rcdriver.cs.activity.ActivityProgress;
import com.rcdriver.cs.activity.RateActivity;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.ProgressModel;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.Utility;
import de.hdodenhof.circleimageview.CircleImageView;
public class ProgressItem extends RecyclerView.Adapter<ProgressItem.ItemRowHolder> {

    private final List<ProgressModel> dataList;
    private final Context mContext;
    private final int rowLayout;
    private static String TeksStatus = "Pesanan Di Terima";
    private static String Alamat = "Alamat";
    private String gethome;
    public ProgressItem(Context context, List<ProgressModel> dataList, int rowLayout) {
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

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final ProgressModel singleItem = dataList.get(position);
        holder.name.setText(singleItem.getFitur() + "#" + singleItem.getIdtrans());
        holder.namadriver.setText(singleItem.getNamadriver());
        Utility.currencyTXT(holder.biaya, singleItem.getBiaya_akhir(), mContext);
        FiturModel designedFitur = LocalStore.get().getFitur(Integer.valueOf(singleItem.getOrderFitur()));
        android.util.Log.e("GetHome ", String.valueOf(singleItem.getStatus()));
        //--------------------------------------------------------------------------
        gethome = designedFitur.getHome();
        if (!singleItem.getIcon().isEmpty()) {
            PicassoTrustAll.getInstance(mContext)
                    .load(Constants.IMAGESDRIVER + singleItem.getFotodriver())
                    .resize(250, 250)
                    .into(holder.images);
        }
        String Status = String.valueOf(singleItem.getStatus());
        if(Status.equals("2")){
            if(gethome.equals("2")) {
                Alamat = singleItem.getAlamatTujuan();
                TeksStatus = "Mengambil Paket.";
            }else if(gethome.equals("4")){
                Alamat = singleItem.getAlamatTujuan();
                TeksStatus = "Memesan Pesanan.";
            }else{
                Alamat = singleItem.getAlamatAsal();
                TeksStatus = "Ketempat Jemputan.";
            }
        }else if(Status.equals("3")){
            if(gethome.equals("2")) {
                Alamat = singleItem.getAlamatAsal();
                TeksStatus = "Pengantaran Paket.";
            }else if(gethome.equals("4")){
                Alamat = singleItem.getAlamatAsal();
                TeksStatus = "Pesanan Di Antar.";
            }else{
                Alamat = singleItem.getAlamatTujuan();
                TeksStatus = "Ketempat Tujuan.";
            }
        }else if(Status.equals("4")){
            if(gethome.equals("2")) {
                Alamat = singleItem.getAlamatTujuan();
                TeksStatus = "Paket Sudah Diterima";
            }else if(gethome.equals("4")){
                Alamat = singleItem.getAlamatTujuan();
                TeksStatus = "Pesanan Sudah Diterima.";
            }else{
                Alamat = singleItem.getAlamatTujuan();
                TeksStatus = "Sudah Sampai.";
            }
        }else if(Status.equals("9")){
            TeksStatus = "Kami sedang mencarikan driver";
        }else {
            Alamat = singleItem.getAlamatTujuan();
            TeksStatus = "Pesanan Telah Selesai.";
        }
        holder.mstatus.setText(TeksStatus);
        holder.alamat.setText(Alamat);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mKlik", singleItem.getIdtrans() + "," + Status + "," + singleItem.getRate());
                if(Status.equals("4") && singleItem.getRate().isEmpty() || singleItem.getRate() == null) {
                    Intent i = new Intent(mContext, RateActivity.class);
                    i.putExtra("id_driver", singleItem.getIdDriver());
                    i.putExtra("id_transaksi", singleItem.getIdtrans());
                    i.putExtra("total_biaya", singleItem.biaya_akhir);
                    i.putExtra("pakai_wallet", String.valueOf(singleItem.isPakaiWallet()));
                    i.putExtra("fitur", singleItem.getOrderFitur());
                    i.putExtra("fotodriver", singleItem.getFotodriver());
                    i.putExtra("namadriver", singleItem.getNamadriver());
                    i.putExtra("response", Status);
                    mContext.startActivity(i);
                }else{
                    Intent intent = new Intent(mContext, ActivityProgress.class);
                    intent.putExtra("id_driver", singleItem.getIdDriver());
                    intent.putExtra("id_transaksi", singleItem.getIdtrans());
                    intent.putExtra("pakai_wallet", String.valueOf(singleItem.isPakaiWallet()));
                    intent.putExtra("response", Status);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView name, alamat,biaya,mstatus,namadriver;
        CircleImageView images;
        LinearLayout content;
        ItemRowHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.imagefitur);
            name = itemView.findViewById(R.id.txtFitur);
            biaya = itemView.findViewById(R.id.txtBiaya);
            content = itemView.findViewById(R.id.content);
            mstatus = itemView.findViewById(R.id.txtStatus);
            alamat = itemView.findViewById(R.id.txtalamat);
            namadriver = itemView.findViewById(R.id.txtnamadriver);
        }
    }
}
