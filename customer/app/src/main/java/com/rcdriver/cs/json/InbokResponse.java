package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.models.InbokModel;

public class InbokResponse {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<InbokModel> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<InbokModel> getData() {
        return data;
    }
    public void setData(List<InbokModel> data) {
        this.data = data;
    }
}
