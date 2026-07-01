package com.rcdriver.mt.json.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rcdriver.mt.models.payment.Retail;


public class RetailResponseJson {
    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private Retail retailModel;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Retail getRetailModel() {
        return retailModel;
    }

    public void setRetailModel(Retail retailModel) {
        this.retailModel = retailModel;
    }
}
