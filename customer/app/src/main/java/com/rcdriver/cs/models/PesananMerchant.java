package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Balqis Studio on 1/5/2017.
 */

public class PesananMerchant extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("id_item")
    private int idItem;

    @Expose
    @SerializedName("tipe")
    private int tipe;

    @Expose
    @SerializedName("total_harga")
    private long totalHarga;

    @Expose
    @SerializedName("qty")
    private int qty;

    @Expose
    @SerializedName("nama_pesanan")
    private String namaPesanan = "";

    @Expose
    @SerializedName("catatan")
    private String catatan = "";

    public String getNamaPesanan() {
        return namaPesanan;
    }

    public void setNamaPesanan(String namaPesanan) {
        this.namaPesanan = namaPesanan;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public long getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(long totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
