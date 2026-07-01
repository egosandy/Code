package com.rcdriver.mt.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VirtualAccount implements Serializable {
    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("external_id")
    private String externalId;

    @Expose
    @SerializedName("bank_code")
    private String bankCode;

    @Expose
    @SerializedName("merchant_code")
    private String merchantCode;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("account_number")
    private String accountNumber;

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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
