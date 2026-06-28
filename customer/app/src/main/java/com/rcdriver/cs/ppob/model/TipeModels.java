package com.rcdriver.cs.ppob.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TipeModels extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    @SerializedName("tipe")
    private String tipe;

    @Expose
    @SerializedName("nama")
    private String operator;

    @Expose
    @SerializedName("kode")
    private String kode;

    @Expose
    @SerializedName("ikon")
    private String ikon;

    @Expose
    @SerializedName("status")
    private String status;

    public String getTipe() {
        return tipe;
    }
    public String getOperator() {
        return operator;
    }
    public String getIkon() {
        return ikon;
    }
    public String getStatus() {
        return status;
    }
    public String getKode() {
        return kode;
    }
}
