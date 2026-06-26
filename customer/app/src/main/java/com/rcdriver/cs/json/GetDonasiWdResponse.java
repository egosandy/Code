package com.rcdriver.cs.json;

import com.rcdriver.cs.models.DonassiWd;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDonasiWdResponse {
    @Expose
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("data")
    @Expose
    private List<DonassiWd> donassiWds;

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

    public List<DonassiWd> getDonassiWds() {
        return donassiWds;
    }

    public void setDonassiWds(List<DonassiWd> donassiWds) {
        this.donassiWds = donassiWds;
    }
}
