package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UpdatePasswordRequestJson {
    @SerializedName("no_telepon")
    @Expose
    private String no_telepon;

    @SerializedName("password")
    @Expose
    private String password;


    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
