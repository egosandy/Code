package com.rcdriver.cs.json;

import com.rcdriver.cs.models.Donatur;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDonaturJson {

    @Expose
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("data")
    @Expose
    private List<Donatur> donaturList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Donatur> getDonaturList() {
        return donaturList;
    }

    public void setDonaturList(List<Donatur> donaturList) {
        this.donaturList = donaturList;
    }
}
