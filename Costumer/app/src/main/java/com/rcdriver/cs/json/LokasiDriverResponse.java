package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.models.LokasiDriverModel;

/**
 * Created by Maswend Team on 24/02/2019.
 */

public class LokasiDriverResponse {

    @SerializedName("data")
    @Expose
    private final List<LokasiDriverModel> data = new ArrayList<>();

    public List<LokasiDriverModel> getData() {
        return data;
    }

}
