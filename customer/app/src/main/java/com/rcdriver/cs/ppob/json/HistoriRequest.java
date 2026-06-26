package com.rcdriver.cs.ppob.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoriRequest {
    @Expose
    @SerializedName("iduser")
    public String id;

    @Expose
    @SerializedName("trx")
    public String trx;

    @Expose
    @SerializedName("reff")
    public String reff;

    @Expose
    @SerializedName("operator")
    public String operator;

    @Expose
    @SerializedName("biaya")
    public String biaya;

    @Expose
    @SerializedName("notujuan")
    public String notujuan;

    @Expose
    @SerializedName("status")
    public String status;
}
