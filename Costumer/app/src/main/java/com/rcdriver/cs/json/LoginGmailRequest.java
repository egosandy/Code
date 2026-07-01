package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public class LoginGmailRequest {

    @SerializedName("email")
    @Expose
    private String email;


    @SerializedName("token")
    @Expose
    private String regId;


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }
}
