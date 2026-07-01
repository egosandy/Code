package com.rcdriver.mt.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rcdriver.mt.models.MapKeyModel;

import java.util.ArrayList;
import java.util.List;

public class MapKeyResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<MapKeyModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MapKeyModel> getData() {
        return data;
    }

    public void setData(List<MapKeyModel> data) {
        this.data = data;
    }
}
