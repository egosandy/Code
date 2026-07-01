package com.rcdriver.mt.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rcdriver.mt.models.FcmKeyModel;

import java.util.ArrayList;
import java.util.List;

public class FcmKeyResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<FcmKeyModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FcmKeyModel> getData() {
        return data;
    }

    public void setData(List<FcmKeyModel> data) {
        this.data = data;
    }
}
