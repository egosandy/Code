package com.rcdriver.cs.json;

import com.rcdriver.cs.models.TransferDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


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
