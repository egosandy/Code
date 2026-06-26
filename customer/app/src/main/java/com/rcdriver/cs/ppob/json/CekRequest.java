package com.rcdriver.cs.ppob.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CekRequest {
    @SerializedName("trx")
    @Expose
    private String trx;

    @SerializedName("noreff")
    @Expose
    private String noreff;

    @SerializedName("reff")
    @Expose
    private String reff;

    @SerializedName("iduser")
    @Expose
    private String iduser;

    @SerializedName("harga")
    @Expose
    private String harga;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("mysaldo")
    @Expose
    private String mysaldo;

    public void setTrx(String trx) {
        this.trx = trx;
    }
    public void setNoreff(String noreff) {
        this.noreff = noreff;
    }
    public void setReff(String reff) {
        this.reff = reff;
    }
    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setMysaldo(String mysaldo) {
        this.mysaldo = mysaldo;
    }
}
