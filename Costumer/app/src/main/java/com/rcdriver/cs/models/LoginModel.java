package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("islogin")
    @Expose
    private int islogin;

    public int getIslogin() {
        return islogin;
    }

}
