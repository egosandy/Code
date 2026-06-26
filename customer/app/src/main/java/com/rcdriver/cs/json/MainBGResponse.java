package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.models.MainBGModel;

public class MainBGResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<MainBGModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MainBGModel> getData() {
        return data;
    }

    public void setData(List<MainBGModel> data) {
        this.data = data;
    }
}
