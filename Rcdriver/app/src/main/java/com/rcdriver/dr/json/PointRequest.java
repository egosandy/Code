package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointRequest {
    @SerializedName("isdriver")
    @Expose
    private String isdriver;
    public String getIsdriver() {
        return isdriver;
    }

    public void setIsdriver(String isdriver) {
        this.isdriver = isdriver;
    }
}
