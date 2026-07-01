package com.rcdriver.cs.json;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UpdateTokenRequestJson {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("token")
    @Expose
    private String token;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
