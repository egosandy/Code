package com.rcdriver.dr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MenuModel extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("nama_item")
    private String item;
    @Expose
    @SerializedName("harga_item")
    private String harga;
    @Expose
    @SerializedName("harga_promo")
    private String promo;
    @Expose
    @SerializedName("foto_item")
    private String foto;
    @Expose
    @SerializedName("status_promo")
    private String status;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getPromo() {
        return promo;
    }
    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
