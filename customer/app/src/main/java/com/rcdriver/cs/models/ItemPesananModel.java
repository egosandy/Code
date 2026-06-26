package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Maswend Team on 10/17/2019.
 */

public class ItemPesananModel extends RealmObject implements Serializable {

    @Expose
    @SerializedName("nama_item")
    private String nama_item;

    @Expose
    @SerializedName("jumlah_item")
    private String jumlah_item;

    @Expose
    @SerializedName("total_harga")
    private String total_harga;

    @Expose
    @SerializedName("tipe")
    private int tipe;

    @Expose
    @SerializedName("nama_pesanan")
    private String nama_pesanan;

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public String getNama_pesanan() {
        return nama_pesanan;
    }

    public void setNama_pesanan(String nama_pesanan) {
        this.nama_pesanan = nama_pesanan;
    }

    public String getNama_item() {
        return nama_item;
    }

    public String getJumlah_item() {
        return jumlah_item;
    }

    public String getTotal_harga() {
        return total_harga;
    }

}
