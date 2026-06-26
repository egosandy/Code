package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class FcmModel implements Serializable {
    @Expose
    @SerializedName("results")
    public String message;

    public String getMessage() {
        return message;
    }
}
