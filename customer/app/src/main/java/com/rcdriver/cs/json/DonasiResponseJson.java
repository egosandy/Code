package com.rcdriver.cs.json;

import com.rcdriver.cs.models.Donasi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonasiResponseJson {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private Donasi data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Donasi getData() {
        return data;
    }

    public void setData(Donasi data) {
        this.data = data;
    }
}
