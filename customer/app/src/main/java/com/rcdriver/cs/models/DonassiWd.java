package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class DonassiWd implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("id_donasi")
    private String idDonasi;

    @Expose
    @SerializedName("invoice")
    private String invoice;

    @Expose
    @SerializedName("jumlah")
    private String jumlah;

    @Expose
    @SerializedName("keterangan")
    private String keterangan;

    @Expose
    @SerializedName("foto")
    private String foto;

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("regalias")
    private String regalias;

    @Expose
    @SerializedName("regtime")
    private Date regtime;

    @Expose
    @SerializedName("judul")
    private String judul;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDonasi() {
        return idDonasi;
    }

    public void setIdDonasi(String idDonasi) {
        this.idDonasi = idDonasi;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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

    public String getRegalias() {
        return regalias;
    }

    public void setRegalias(String regalias) {
        this.regalias = regalias;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
