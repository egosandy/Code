package com.rcdriver.dr.json.transfer;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.rcdriver.dr.models.TransferDetail;

public class TransferResponJson {
    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public TransferDetail data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransferDetail getData() {
        return data;
    }

    public void setData(TransferDetail data) {
        this.data = data;
    }
}
