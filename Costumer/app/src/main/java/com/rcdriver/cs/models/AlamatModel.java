package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class AlamatModel extends RealmObject implements Serializable {
    @Expose
    @SerializedName("id_pelanggan")
    private String idpelanggan;
    @Expose
    @SerializedName("nama")
    private String nama;
    @Expose
    @SerializedName("latitude")
    private String latitude;
    @Expose
    @SerializedName("longitude")
    private String longitude;
    @Expose
    @SerializedName("alamat")
    private String alamat;
}
