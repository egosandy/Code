package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MidtransApiModel extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("midtrans_url")
    private String baseurl;

    @Expose
    @SerializedName("midtrans_key")
    private String clientkey;

    @Expose
    @SerializedName("midtrans_aktif")
    private String status;


}
