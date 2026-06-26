package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 10/17/2019.
 */

public class ItemModel implements Serializable {

    @Expose
    @SerializedName("id_item")
    private int id_item;

    @Expose
    @SerializedName("nama_item")
    private String nama_item;

    @Expose
    @SerializedName("harga_item")
    private String harga_item;

    @Expose
    @SerializedName("harga_promo")
    private String harga_promo;

    @Expose
    @SerializedName("nama_kategori_item")
    private String kategori_item;

    @Expose
    @SerializedName("deskripsi_item")
    private String deskripsi_item;

    @Expose
    @SerializedName("foto_item")
    private String foto_item;

    @Expose
    @SerializedName("status_promo")
    private String status_promo;


    public int getId_item() {
        return id_item;
    }

    public String getNama_item() {
        return nama_item;
    }

    public String getHarga_item() {
        return harga_item;
    }

    public String getHarga_promo() {
        return harga_promo;
    }

    public String getKategori_item() {
        return kategori_item;
    }

    public String getDeskripsi_item() {
        return deskripsi_item;
    }

    public String getFoto_item() {
        return foto_item;
    }

    public String getStatus_promo() {
        return status_promo;
    }


}
