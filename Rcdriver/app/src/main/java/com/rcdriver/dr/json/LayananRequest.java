package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LayananRequest {
    @SerializedName("id_transaksi")
    @Expose
    private String id_transaksi;

    @SerializedName("total_biaya")
    @Expose
    private String total_biaya;

    public String getId_transaksi() {
        return id_transaksi;
    }
    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getTotal_biaya() {
        return total_biaya;
    }
    public void setTotal_biaya(String total_biaya) {
        this.total_biaya = total_biaya;
    }
}
