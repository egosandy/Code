package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 10/17/2019.
 */

public class MerchantModel implements Serializable {

    @Expose
    @SerializedName("id_merchant")
    private String id_merchant;

    @Expose
    @SerializedName("nama_merchant")
    private String nama_merchant;

    @Expose
    @SerializedName("alamat_merchant")
    private String alamat_merchant;

    @Expose
    @SerializedName("latitude_merchant")
    private String latitude_merchant;

    @Expose
    @SerializedName("longitude_merchant")
    private String longitude_merchant;

    @Expose
    @SerializedName("jam_buka")
    private String jam_buka;

    @Expose
    @SerializedName("jam_tutup")
    private String jam_tutup;

    @Expose
    @SerializedName("deskripsi_merchant")
    private String deskripsi_merchant;

    @Expose
    @SerializedName("category_merchant")
    private String category_merchant;

    @Expose
    @SerializedName("foto_merchant")
    private String foto_merchant;

    @Expose
    @SerializedName("telepon_merchant")
    private String telepon_merchant;

    @Expose
    @SerializedName("status_promo")
    private String status_promo;

    @Expose
    @SerializedName("distance")
    private String distance;

    public String getId_merchant() {
        return id_merchant;
    }

    public String getNama_merchant() {
        return nama_merchant;
    }

    public String getAlamat_merchant() {
        return alamat_merchant;
    }

    public String getLatitude_merchant() {
        return latitude_merchant;
    }

    public String getLongitude_merchant() {
        return longitude_merchant;
    }

    public String getFoto_merchant() {
        return foto_merchant;
    }


    public String getStatus_promo() {
        return status_promo;
    }

    public String getDistance() {
        return distance;
    }

}
