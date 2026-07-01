package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public class ResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @Expose
    @SerializedName("data")
    public List<String> data = new ArrayList<>();

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getData() {
        return data;
    }
}
