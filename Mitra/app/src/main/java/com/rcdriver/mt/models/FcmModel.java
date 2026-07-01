package com.rcdriver.mt.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class FcmModel extends RealmObject implements Serializable {
    @Expose
    @SerializedName("results")
    public String message;

    public String getMessage() {
        return message;
    }
}
