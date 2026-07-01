package com.rcdriver.mt.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.mt.models.FcmModel;

public class FcmResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<FcmModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public List<FcmModel> getData() {
        return data;
    }
}
