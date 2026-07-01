package com.rcdriver.dr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("islogin")
    @Expose
    private int islogin;
    @SerializedName("pin")
    @Expose
    private int pin;

    @SerializedName("status")
    @Expose
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getIslogin() {
        return islogin;
    }
    public void setIslogin(int islogin) {
        this.islogin = islogin;
    }

    public int getPin() {
        return pin;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }
}
