package com.rcdriver.mt.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JenisPayment implements Serializable {
    @Expose
    @SerializedName("jenis")
    private String jenis;

    @Expose
    @SerializedName("method")
    private List<PaymentMethod> paymentMethodList;

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public List<PaymentMethod> getPaymentMethodList() {
        return paymentMethodList;
    }

    public void setPaymentMethodList(List<PaymentMethod> paymentMethodList) {
        this.paymentMethodList = paymentMethodList;
    }
}
