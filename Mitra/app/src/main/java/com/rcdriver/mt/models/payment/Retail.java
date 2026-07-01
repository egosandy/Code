package com.rcdriver.mt.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Retail implements Serializable {
    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("external_id")
    private String externalId;

    @Expose
    @SerializedName("retail_outlet_name")
    private String retailName;

    @Expose
    @SerializedName("prefix")
    private String prefix;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("payment_code")
    private String paymentCode;

    @Expose
    @SerializedName("expected_amount")
    private String expectedAmount;

    @Expose
    @SerializedName("expiration_date")
    private String expDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getRetailName() {
        return retailName;
    }

    public void setRetailName(String retailName) {
        this.retailName = retailName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(String expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
}
