package com.rcdriver.cs.json;

import com.rcdriver.cs.models.digi.InquiryToken;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InquiryTokenResponse {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private InquiryToken inquiryToken;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InquiryToken getInquiryToken() {
        return inquiryToken;
    }

    public void setInquiryToken(InquiryToken inquiryToken) {
        this.inquiryToken = inquiryToken;
    }
}
