package com.rcdriver.cs.ppob.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class StatusModels implements Serializable {
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

    public String getTrx() {
        return trx;
    }
    public String getIduser() {
        return iduser;
    }
    public String getReff() {
        return reff;
    }
    public String getOperator() {
        return operator;
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
}
