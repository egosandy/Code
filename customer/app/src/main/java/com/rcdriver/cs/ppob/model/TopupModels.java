package com.rcdriver.cs.ppob.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class TopupModels implements Serializable {
    @Expose
    @SerializedName("ref_id")
    private String noreff;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("code")
    private String kode;

    @Expose
    @SerializedName("hp")
    private String nohp;

    @Expose
    @SerializedName("price")
    private String harga;

    @Expose
    @SerializedName("message")
    private String pesan;

    @Expose
    @SerializedName("balance")
    private String saldo;

    @Expose
    @SerializedName("tr_id")
    private String idtrx;

    public String getNoreff() {
        return noreff;
    }
    public String getKode() {
        return kode;
    }
    public String getStatus() {
        return status;
    }
    public String getHarga() {
        return harga;
    }
    public String getPesan() {
        return pesan;
    }
    public String getNohp() {
        return nohp;
    }
    public String getSaldo() {
        return saldo;
    }
    public String getIdtrx() {
        return idtrx;
    }
}
