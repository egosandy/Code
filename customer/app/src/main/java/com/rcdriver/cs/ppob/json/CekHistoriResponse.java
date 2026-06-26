package com.rcdriver.cs.ppob.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.ppob.model.StatusModels;

public class CekHistoriResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<StatusModels> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StatusModels> getData() {
        return data;
    }

    public void setData(List<StatusModels> data) {
        this.data = data;
    }
}
