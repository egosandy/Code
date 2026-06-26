package com.rcdriver.cs.ppob.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.ppob.model.TipeModels;

public class OperatorResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<TipeModels> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TipeModels> getData() {
        return data;
    }

    public void setData(List<TipeModels> data) {
        this.data = data;
    }
}
