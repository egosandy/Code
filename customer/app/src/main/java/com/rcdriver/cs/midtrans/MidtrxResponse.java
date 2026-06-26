package com.rcdriver.cs.midtrans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MidtrxResponse {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<String> data = new ArrayList<>();
   // private List<Object> data = new ArrayList<>();


    public String getMessage() {
        return message;
    }
    public List<String> getData() {
        return data;
    }
    /*public List<Object> getData() {
        return data;
    }*/
}
