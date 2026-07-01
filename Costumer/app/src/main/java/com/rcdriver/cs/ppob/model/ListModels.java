package com.rcdriver.cs.ppob.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
public class ListModels extends RealmObject implements Serializable{
    @PrimaryKey
    @Expose
    @SerializedName("pulsa_code")
    private String kode;

    @Expose
    @SerializedName("pulsa_op")
    private String operator;

    @Expose
    @SerializedName("pulsa_nominal")
    private String keterangan;

    @Expose
    @SerializedName("pulsa_price")
    private String harga;

    @Expose
    @SerializedName("pulsa_type")
    private String tipe;

    @Expose
    @SerializedName("masaaktif")
    private String masaaktif;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("icon_url")
    private String ikon;

    public String getKode() {
        return kode;
    }
    public String getOperator() {
        return operator;
    }
    public String getKeterangan() {
        return keterangan;
    }
    public String getHarga() {
        return harga;
    }
    public String getTipe() {
        return tipe;
    }
    public String getMasaaktif() {
        return masaaktif;
    }
    public String getStatus() {
        return status;
    }
    public String getIkon() {
        return ikon;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }
    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
    public void setMasaaktif(String masaaktif) {
        this.masaaktif = masaaktif;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setIkon(String ikon) {
        this.ikon = ikon;
    }
}
