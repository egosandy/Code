package com.rcdriver.cs.midtrans.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MidtrxModels extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    @SerializedName("token")
    private String token;

    @Expose
    @SerializedName("redirect_url")
    private String url;

    public String getToken() {
        return token;
    }
    public String getUrl() {
        return url;
    }
}
