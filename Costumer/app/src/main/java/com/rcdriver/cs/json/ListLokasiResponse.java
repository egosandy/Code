package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import com.rcdriver.cs.models.SaveLokasiModel;

public class ListLokasiResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<SaveLokasiModel> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<SaveLokasiModel> getData() {
        return data;
    }

    public void setData(List<SaveLokasiModel> data) {
        this.data = data;
    }
}
