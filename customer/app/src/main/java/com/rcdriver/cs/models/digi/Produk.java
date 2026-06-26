package com.rcdriver.cs.models.digi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Produk implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("operator")
    private String operator;

    @Expose
    @SerializedName("kode")
    private String kode;

    @Expose
    @SerializedName("nama")
    private String nama;

    @Expose
    @SerializedName("deskripsi")
    private String deskripsi;

    @Expose
    @SerializedName("harga")
    private String harga;

    @Expose
    @SerializedName("admin")
    private String admin;

    @Expose
    @SerializedName("nama_operator")
    private String namaOperator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getNamaOperator() {
        return namaOperator;
    }

    public void setNamaOperator(String namaOperator) {
        this.namaOperator = namaOperator;
    }
}
