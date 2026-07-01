package com.rcdriver.dr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiturModel {
    @Expose
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("komisi")
    public String komisi;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getKomisi() {
        return komisi;
    }
    public void setKomisi(String komisi) {
        this.komisi = komisi;
    }
}
