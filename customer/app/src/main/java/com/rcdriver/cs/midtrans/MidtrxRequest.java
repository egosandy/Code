package com.rcdriver.cs.midtrans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MidtrxRequest {
    @Expose
    @SerializedName("id")
    public String id;

    @Expose
    @SerializedName("jumlah")
    public double jumlah;

    public void setId(String id) {
        this.id = id;
    }
    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }
}
