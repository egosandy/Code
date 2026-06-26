package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class KodePromoModel {
    @Expose
    @SerializedName("nama_promo")
    private String namapromo;

    @Expose
    @SerializedName("kode_promo")
    private String kodepromo;

    @Expose
    @SerializedName("expired")
    private Date expired;

    @Expose
    @SerializedName("image_promo")
    private String imagepromo;


    public String getNamapromo() {
        return namapromo;
    }


    public String getImagepromo() {
        return imagepromo;
    }

    public String getKodepromo() {
        return kodepromo;
    }

    public Date getExpired() {
        return expired;
    }

}
