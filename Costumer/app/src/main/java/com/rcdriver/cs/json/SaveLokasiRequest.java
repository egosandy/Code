package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveLokasiRequest {
    @Expose
    @SerializedName("id_pelanggan")
    public String id;

    @Expose
    @SerializedName("nama")
    public String nama;

    @Expose
    @SerializedName("latitude")
    public String latitude;

    @Expose
    @SerializedName("longitude")
    public String longitude;

    @Expose
    @SerializedName("alamat")
    public String alamat;

    @Expose
    @SerializedName("utama")
    public String utama;
}
