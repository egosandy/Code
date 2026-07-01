package com.rcdriver.mt.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rcdriver.mt.models.User;

import java.util.ArrayList;
import java.util.List;

public class VerifyCodeResponse {
    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private List<User> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
