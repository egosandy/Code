package com.rcdriver.mt.json.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaldoReceiver implements Serializable {
    @Expose
    @SerializedName("error")
    private String error;

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("nama_penerima")
    private String nama;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
