package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class Donatur implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("invoice")
    private String invoice;

    @Expose
    @SerializedName("id_donasi")
    private String id_donasi;

    @Expose
    @SerializedName("id_user")
    private String id_user;

    @Expose
    @SerializedName("nama_pengirim")
    private String nama_pengirim;

    @Expose
    @SerializedName("masuk")
    private String masuk;

    @Expose
    @SerializedName("keluar")
    private String keluar;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("regtime")
    private Date regtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getId_donasi() {
        return id_donasi;
    }

    public void setId_donasi(String id_donasi) {
        this.id_donasi = id_donasi;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_pengirim() {
        return nama_pengirim;
    }

    public void setNama_pengirim(String nama_pengirim) {
        this.nama_pengirim = nama_pengirim;
    }

    public String getMasuk() {
        return masuk;
    }

    public void setMasuk(String masuk) {
        this.masuk = masuk;
    }

    public String getKeluar() {
        return keluar;
    }

    public void setKeluar(String keluar) {
        this.keluar = keluar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }
}
