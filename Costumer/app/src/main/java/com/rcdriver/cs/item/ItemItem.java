package com.rcdriver.cs.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.rcdriver.cs.databinding.ItemTransaksiBinding; // 1. Import class binding
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mikepenz.fastadapter.items.AbstractItem;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;
import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.models.PesananMerchant;
import com.rcdriver.cs.utils.Utility;
import io.realm.Realm;

// Hapus semua import butterknife

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

        // 2. Akses semua view melalui holder.binding
        holder.binding.namalayanan.setText(namaMenu);
        holder.binding.deskripsi.setText(deskripsiMenu);

        if (!foto.isEmpty()) {
            Glide.with(this.context)
                    .load(Constants.IMAGESITEM + foto)
                    .override(250, 250)
                    .placeholder(R.drawable.nocamera)
                    .error(R.drawable.nocamera)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                    .into(holder.binding.icon);
        }

        holder.binding.quantityText.setText(String.valueOf(quantity));
        holder.binding.catatan.setEnabled(quantity > 0);
        holder.binding.catatan.setText(catatan);

        holder.binding.catatan.addTextChangedListener(catatanUpdater);

        if (promo.equals("1")) {
            holder.binding.promobadge.setVisibility(View.VISIBLE);
            holder.binding.shimreview.setVisibility(View.VISIBLE);
            holder.binding.shimreview.startShimmerAnimation();
            holder.binding.hargapromo.setVisibility(View.VISIBLE);
            Utility.currencyTXT(holder.binding.hargapromo, String.valueOf(harga), context);
            Utility.currencyTXT(holder.binding.harga, String.valueOf(hargapromo), context);
            holder.binding.hargapromo.setPaintFlags(holder.binding.hargapromo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.binding.deskripsi.setMinLines(2);
        } else {
            holder.binding.promobadge.setVisibility(View.GONE);
            holder.binding.shimreview.setVisibility(View.GONE);
            holder.binding.shimreview.stopShimmerAnimation();
            holder.binding.hargapromo.setVisibility(View.GONE);
            Utility.currencyTXT(holder.binding.harga, String.valueOf(harga), context);
        }

        holder.binding.addQuantity.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                quantity++;
                holder.binding.quantityText.setText("" + quantity);
                holder.binding.catatan.setEnabled(true);
                CalculateCost();
                if (quantity == 1) {
                    AddPesanan(id, cost, quantity, catatan);
                } else if (quantity > 1) {
                    UpdatePesanan(id, cost, quantity, catatan);
                }

                if (calculatePrice != null) calculatePrice.calculatePrice();
            }
        });


        holder.binding.removeQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity - 1 >= 0) {
                    quantity--;
                    holder.binding.quantityText.setText(String.valueOf(quantity));
                    CalculateCost();
                    UpdatePesanan(id, cost, quantity, catatan);

                    if (quantity == 0) {
                        DeletePesanan(id);
                        holder.binding.catatan.setText("");
                        holder.binding.catatan.setEnabled(false);
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
        // 3. Deklarasikan satu variabel binding
        ItemTransaksiBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            // 4. Inisialisasi binding dari itemView
            binding = ItemTransaksiBinding.bind(itemView);
        }
    }
}