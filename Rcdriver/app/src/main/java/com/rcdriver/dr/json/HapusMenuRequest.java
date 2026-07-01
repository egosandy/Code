package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HapusMenuRequest {
    @SerializedName("id_item")
    @Expose
    private String id_item;

    @SerializedName("no_telepon")
    @Expose
    private String notelepon;

    @SerializedName("id_transaksi")
    @Expose
    private String id_transaksi;

    public String getId_item() {
        return id_item;
    }
    public void setId_item(String id_item) {
        this.id_item = id_item;
    }
    public String getId_transaksi() {
        return id_transaksi;
    }
    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }
    public String getNotelepon() {
        return notelepon;
    }
    public void setNotelepon(String notelepon) {
        this.notelepon = notelepon;
    }
}
