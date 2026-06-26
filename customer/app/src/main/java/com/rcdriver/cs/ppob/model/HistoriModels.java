package com.rcdriver.cs.ppob.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class HistoriModels implements Serializable {
    @Expose
    @SerializedName("trx")
    private String trx;

    @Expose
    @SerializedName("iduser")
    private String iduser;

    @Expose
    @SerializedName("reff")
    private String reff;

    @Expose
    @SerializedName("operator")
    private String operator;

    @Expose
    @SerializedName("biaya")
    private String biaya;

    @Expose
    @SerializedName("notujuan")
    private String notujuan;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("tanggal")
    private String tanggal;

    public String getTrx() {
        return trx;
    }
    public String getOperator() {
        return operator;
    }
    public String getIduser() {
        return iduser;
    }
    public String getReff() {
        return reff;
    }
    public String getBiaya() {
        return biaya;
    }
    public String getNotujuan() {
        return notujuan;
    }
    public String getStatus() {
        return status;
    }
    public String getTanggal() {
        return tanggal;
    }

    public void setTrx(String trx) {
        this.trx = trx;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
    public void setReff(String reff) {
        this.reff = reff;
    }
    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }
    public void setNotujuan(String notujuan) {
        this.notujuan = notujuan;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
