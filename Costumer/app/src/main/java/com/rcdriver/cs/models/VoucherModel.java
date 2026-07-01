package com.rcdriver.cs.models;

import com.google.gson.annotations.SerializedName;

public class VoucherModel {
    @SerializedName("nama_promo")
    private String nama;
    @SerializedName("kode_promo")
    private String kode;
    @SerializedName("expired")
    private String expired;
    @SerializedName("image_promo")
    private String image;
    @SerializedName("status")
    private String status;

    public VoucherModel(String nama, String kode, String image, String expired,String status) {
        this.nama = nama;
        this.kode = kode;
        this.image = image;
        this.expired = expired;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        nama = nama;
    }

    public String getKode() {
        return kode;
    }
    public void setKode(String kode) {
        kode = kode;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        image = image;
    }

    public String getExpired() {
        return expired;
    }
    public void setExpired(String expired) {
        expired = expired;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        status = status;
    }
}
