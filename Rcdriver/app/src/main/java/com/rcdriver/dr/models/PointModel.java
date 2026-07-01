package com.rcdriver.dr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class PointModel extends RealmObject implements Serializable{
    @Expose
    @SerializedName("kode")
    private String id;
    @Expose
    @SerializedName("nama")
    private String nama;
    @Expose
    @SerializedName("keterangan")
    private String keterangan;
    @Expose
    @SerializedName("poin")
    private String poin;
    @Expose
    @SerializedName("nilai")
    private String nilai;
    @Expose
    @SerializedName("image_poin")
    private String foto;
    @Expose
    @SerializedName("expire")
    private String expire;
    @Expose
    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getPoin() {
        return poin;
    }
    public void setPoin(String poin) {
        this.poin = poin;
    }

    public String getNilai() {
        return nilai;
    }
    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getExpire() {
        return expire;
    }
    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
