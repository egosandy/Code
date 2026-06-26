package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class SaveLokasiModel extends RealmObject implements Serializable {
    @Expose
    @SerializedName("id_pelanggan")
    private String id;

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

    @Expose
    @SerializedName("utama")
    private String utama;

    public String getNama() {
        return nama;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAlamat() {
        return alamat;
    }


}
