package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ConfigDriver extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    @SerializedName("id_driver")
    private String idDriver;

    @Expose
    @SerializedName("status")
    private int status;

}
