package com.rcdriver.cs.models.digi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Kategori implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("nama")
    private String nama;

    @Expose
    @SerializedName("tipe")
    private String tipe;

    @Expose
    @SerializedName("inq")
    private int inq;

    @Expose
    @SerializedName("icon")
    private String icon;

    public int getInq() {
        return inq;
    }

    public void setInq(int inq) {
        this.inq = inq;
    }

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

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
