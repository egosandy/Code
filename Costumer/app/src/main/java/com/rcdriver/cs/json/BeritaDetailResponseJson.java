package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.models.BeritaModel;

/**
 * Created by Maswend Team on 10/17/2019.
 */

public class BeritaDetailResponseJson {

    @Expose
    @SerializedName("data")
    private List<BeritaModel> data = new ArrayList<>();

    public List<BeritaModel> getData() {
        return data;
    }

    public void setData(List<BeritaModel> data) {
        this.data = data;
    }
}
