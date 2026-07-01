package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rcdriver.dr.models.Kendaraan;

import java.util.List;

public class GetKendaraanList {
    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<Kendaraan> kendaraanList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Kendaraan> getKendaraanList() {
        return kendaraanList;
    }

    public void setKendaraanList(List<Kendaraan> kendaraanList) {
        this.kendaraanList = kendaraanList;
    }
}
