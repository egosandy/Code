package com.rcdriver.dr.json.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetReceiverResponJson {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private SaldoReceiver data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SaldoReceiver getData() {
        return data;
    }

    public void setData(SaldoReceiver data) {
        this.data = data;
    }
}
