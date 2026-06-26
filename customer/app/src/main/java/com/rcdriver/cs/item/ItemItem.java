package com.rcdriver.cs.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.PesananMerchant;
import com.rcdriver.cs.utils.Utility;
import io.realm.Realm;

/**
 * Created by Maswend Team on 01/03/2020.
 */

public class ItemItem extends AbstractItem<ItemItem, ItemItem.ViewHolder> {

    private final Context context;
    private final OnCalculatePrice calculatePrice;
    private final TextWatcher catatanUpdater;
    public int id;
    public String namaMenu;
    public String deskripsiMenu;
    public long harga;
    public long hargapromo;
    public long cost;
    public String foto;
    public String promo;
    public int quantity;
    public String catatan;
    private Realm realm;

    public ItemItem(Context context, OnCalculatePrice calculatePrice) {
        this.context = context;
        this.calculatePrice = calculatePrice;

        catatanUpdater = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                catatan = s.toString();
                if (quantity > 0) UpdatePesanan(id, cost, quantity, catatan);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    @Override
    public int getType() {
        return R.id.list_item;
    }

    @Override
    public void bindView(final ItemItem.ViewHolder holder, List payloads) {
        super.bindView(holder, payloads);
        realm = BaseApp.getInstance(context).getRealmInstance();
        holder.namaText.setText(namaMenu);
        holder.deskripsiText.setText(deskripsiMenu);

        if (!foto.isEmpty()) {
            Glide.with(this.context)
                    .load(Constants.IMAGESITEM + foto)
                    .override(250, 250)
                    .placeholder(R.drawable.nocamera)
                    .error(R.drawable.nocamera)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(holder.image);


           /* PicassoTrustAll.getInstance(context)
                    .load(Constants.IMAGESITEM + foto)
                    .resize(250, 250)
                    .into(holder.image);*/
        }

        holder.quantityText.setText(String.valueOf(quantity));
        holder.notesText.setEnabled(quantity > 0);
        holder.notesText.setText(catatan);

        holder.notesText.addTextChangedListener(catatanUpdater);

        if (promo.equals("1")) {
            holder.shimmerbadgeicon.setVisibility(View.VISIBLE);
            holder.shimmerbadge.setVisibility(View.VISIBLE);
            holder.shimmerbadge.startShimmerAnimation();
            holder.hargadasar.setVisibility(View.VISIBLE);
            Utility.currencyTXT(holder.hargadasar, String.valueOf(harga), context);
            Utility.currencyTXT(holder.hargaText, String.valueOf(hargapromo), context);
            holder.hargadasar.setPaintFlags(holder.hargadasar.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.deskripsiText.setMinLines(2);
        } else {
            holder.shimmerbadgeicon.setVisibility(View.GONE);
            holder.shimmerbadge.setVisibility(View.GONE);
            holder.shimmerbadge.stopShimmerAnimation();
            holder.hargadasar.setVisibility(View.GONE);
            Utility.currencyTXT(holder.hargaText, String.valueOf(harga), context);
        }

        holder.addQuantity.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                quantity++;
                holder.quantityText.setText("" + quantity);
                holder.notesText.setEnabled(true);
                CalculateCost();
                if (quantity == 1) {
                    AddPesanan(id, cost, quantity, catatan);
                } else if (quantity > 1) {
                    UpdatePesanan(id, cost, quantity, catatan);
                }

                if (calculatePrice != null) calculatePrice.calculatePrice();
            }
        });


        holder.removeQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity - 1 >= 0) {
                    quantity--;
                    holder.quantityText.setText(String.valueOf(quantity));
                    CalculateCost();
                    UpdatePesanan(id, cost, quantity, catatan);

                    if (quantity == 0) {
                        DeletePesanan(id);
                        holder.notesText.setText("");
                        holder.notesText.setEnabled(false);
                    }
                }

                if (calculatePrice != null) calculatePrice.calculatePrice();
            }
        });
    }

    private void CalculateCost() {
        if (promo.equals("1")) {
            cost = quantity * hargapromo;
        } else {
            cost = quantity * harga;
        }
    }

    private void AddPesanan(int idMakanan, long totalHarga, int qty, String notes) {
        PesananMerchant pesananfood = new PesananMerchant();
        pesananfood.setIdItem(idMakanan);
        pesananfood.setTotalHarga(totalHarga);
        pesananfood.setQty(qty);
        pesananfood.setCatatan(notes);
        realm.beginTransaction();
        realm.copyToRealm(pesananfood);
        realm.commitTransaction();

    }

    private void UpdatePesanan(int idMakanan, long totalHarga, int qty, String notes) {
        realm.beginTransaction();
        PesananMerchant updateFood = realm.where(PesananMerchant.class).equalTo("idItem", idMakanan).findFirst();
        Objects.requireNonNull(updateFood).setTotalHarga(totalHarga);
        updateFood.setQty(qty);
        updateFood.setCatatan(notes);
        realm.copyToRealm(updateFood);
        realm.commitTransaction();

    }

    private void DeletePesanan(int idMakanan) {
        realm.beginTransaction();
        PesananMerchant deleteFood = realm.where(PesananMerchant.class).equalTo("idItem", idMakanan).findFirst();
        Objects.requireNonNull(deleteFood).deleteFromRealm();
        realm.commitTransaction();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.item_transaksi;
    }

    public interface OnCalculatePrice {
        void calculatePrice();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.namalayanan)
        TextView namaText;

        @BindView(R.id.deskripsi)
        TextView deskripsiText;

        @BindView(R.id.harga)
        TextView hargaText;

        @BindView(R.id.catatan)
        EditText notesText;

        @BindView(R.id.add_quantity)
        TextView addQuantity;

        @BindView(R.id.quantity_text)
        TextView quantityText;

        @BindView(R.id.icon)
        RoundedImageView image;

        @BindView(R.id.remove_quantity)
        TextView removeQuantity;

        @BindView(R.id.hargapromo)
        TextView hargadasar;

        @BindView(R.id.list_item)
        LinearLayout itemButton;

        @BindView(R.id.shimreview)
        ShimmerFrameLayout shimmerbadge;

        @BindView(R.id.promobadge)
        FrameLayout shimmerbadgeicon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
