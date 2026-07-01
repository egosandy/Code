package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.dr.models.AreaModels;

public class AreaResponseJson {
    @SerializedName("data")
    @Expose
    private List<AreaModels> data = new ArrayList<>();

    public List<AreaModels> getData() {
        return data;
    }
}
