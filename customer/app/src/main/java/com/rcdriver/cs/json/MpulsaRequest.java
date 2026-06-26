package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MpulsaRequest {
    @Expose
    @SerializedName("type")
    public String tipe;

    @Expose
    @SerializedName("operator")
    public String operator;

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
