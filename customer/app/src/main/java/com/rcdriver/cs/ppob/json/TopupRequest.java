package com.rcdriver.cs.ppob.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopupRequest {
    @SerializedName("noreff")
    @Expose
    private String noreff;

    @SerializedName("kode")
    @Expose
    private String kode;

    @SerializedName("nohp")
    @Expose
    private String nohp;

    public void setNoreff(String noreff) {
        this.noreff = noreff;
    }
    public void setKode(String kode) {
        this.kode = kode;
    }
    public void setNohp(String nohp) {
        this.nohp = nohp;
    }
}
