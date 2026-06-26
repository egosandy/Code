package com.rcdriver.cs.json;

import com.rcdriver.cs.models.digi.Transaksi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DigiTopupResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private Transaksi data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Transaksi getData() {
        return data;
    }

    public void setData(Transaksi data) {
        this.data = data;
    }
}
