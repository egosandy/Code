package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MPulsaResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private String data;

    public String getMessage() {
        return message;
    }
    public String getData() {
        return data;
    }
}
