package com.rcdriver.dr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Kendaraan implements Serializable {
    @Expose
    @SerializedName("id_k")
    private String idk;

    @Expose
    @SerializedName("id_driver")
    private String iddriver;

    @Expose
    @SerializedName("merek")
    private String merek;

    @Expose
    @SerializedName("tipe")
    private String tipe;

    @Expose
    @SerializedName("jenis")
    private String jenis;

    @Expose
    @SerializedName("nomor_kendaraan")
    private String platnomor;

    @Expose
    @SerializedName("warna")
    private String warna;

    @Expose
    @SerializedName("no_stnk")
    private String nostnk;

    @Expose
    @SerializedName("foto_stnk")
    private String foto;

    public String getIdk() {
        return idk;
    }

    public void setIdk(String idk) {
        this.idk = idk;
    }

    public String getIddriver() {
        return iddriver;
    }

    public void setIddriver(String iddriver) {
        this.iddriver = iddriver;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getPlatnomor() {
        return platnomor;
    }

    public void setPlatnomor(String platnomor) {
        this.platnomor = platnomor;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getNostnk() {
        return nostnk;
    }

    public void setNostnk(String nostnk) {
        this.nostnk = nostnk;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
