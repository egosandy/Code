package com.rcdriver.dr.json;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.dr.models.SettingModel;

public class SettingResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<SettingModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SettingModel> getData() {
        return data;
    }

    public void setData(List<SettingModel> data) {
        this.data = data;
    }
}
