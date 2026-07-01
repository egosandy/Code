package com.rcdriver.cs.models.digi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InquiryToken implements Serializable {
    @Expose
    @SerializedName("customer_no")
    private String customerNo;

    @Expose
    @SerializedName("meter_no")
    private String meterNo;

    @Expose
    @SerializedName("subscriber_id")
    private String subscriberId;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("segment_power")
    private String segmentPower;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSegmentPower() {
        return segmentPower;
    }

    public void setSegmentPower(String segmentPower) {
        this.segmentPower = segmentPower;
    }
}
