package com.rcdriver.cs.json;

import com.rcdriver.cs.models.digi.Inquiry;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InquiryResponseJson {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private Inquiry inquiry;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }
}
