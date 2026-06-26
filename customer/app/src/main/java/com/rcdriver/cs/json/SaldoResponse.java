package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.models.SaldoModel;

public class SaldoResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<SaldoModel> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<SaldoModel> getData() {
        return data;
    }
    public void setData(List<SaldoModel> data) {
        this.data = data;
    }
}
