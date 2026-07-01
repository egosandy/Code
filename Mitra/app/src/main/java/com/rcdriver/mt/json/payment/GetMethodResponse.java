package com.rcdriver.mt.json.payment;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rcdriver.mt.models.payment.JenisPayment;

import java.util.List;

public class GetMethodResponse {
    @Expose
    @SerializedName("code")
    private int code;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<JenisPayment> jenisPaymentList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<JenisPayment> getJenisPaymentList() {
        return jenisPaymentList;
    }

    public void setJenisPaymentList(List<JenisPayment> jenisPaymentList) {
        this.jenisPaymentList = jenisPaymentList;
    }
}
