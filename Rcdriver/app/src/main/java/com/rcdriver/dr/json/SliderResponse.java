package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.dr.models.SliderModel;

public class SliderResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<SliderModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<SliderModel> getData() {
        return data;
    }

    public void setData(List<SliderModel> data) {
        this.data = data;
    }
}
