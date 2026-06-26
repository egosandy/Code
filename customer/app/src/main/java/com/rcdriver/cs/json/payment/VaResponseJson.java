package com.rcdriver.cs.json.payment;


import com.rcdriver.cs.models.payment.VirtualAccount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


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
