package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class ConfigDriver implements Serializable {
    @Expose
    @SerializedName("id_driver")
    private String idDriver;

    @Expose
    @SerializedName("status")
    private int status;

}
