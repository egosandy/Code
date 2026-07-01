package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.dr.models.DriverModel;

public class PointResponseJson {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<DriverModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DriverModel> getData() {
        return data;
    }

    public void setData(List<DriverModel> data) {
        this.data = data;
    }
}
