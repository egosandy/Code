package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendFcmRequest {
    @Expose
    @SerializedName("id")
    public String id;

    @Expose
    @SerializedName("token")
    public String token;

    @Expose
    @SerializedName("title")
    public String title;

    @Expose
    @SerializedName("body")
    public String notifikasi;

    @Expose
    @SerializedName("nomor")
    public String nomor;

    @Expose
    @SerializedName("type")
    public String tipe;

    @Expose
    @SerializedName("data")
    private Object data;

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNotifikasi(String notifikasi) {
        this.notifikasi = notifikasi;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
