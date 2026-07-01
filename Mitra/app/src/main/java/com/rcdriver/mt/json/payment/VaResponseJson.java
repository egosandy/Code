package com.rcdriver.mt.json.payment;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rcdriver.mt.models.payment.VirtualAccount;


public class VaResponseJson {
    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private VirtualAccount vaModel;

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

    public VirtualAccount getVaModel() {
        return vaModel;
    }

    public void setVaModel(VirtualAccount vaModel) {
        this.vaModel = vaModel;
    }
}
