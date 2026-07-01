package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateMenuRequestJson {
    @SerializedName("id_item")
    @Expose
    private String id;

    @SerializedName("id_transaksi")
    @Expose
    private String idtrans;

    @SerializedName("jumlah_item")
    @Expose
    private String jumlah;

    @SerializedName("total_harga")
    @Expose
    private String harga;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getIdtrans() {
        return idtrans;
    }
    public void setIdtrans(String idtrans) {
        this.idtrans = idtrans;
    }

    public String getJumlah() {
        return jumlah;
    }
    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }
}
