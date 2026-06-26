package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Maswend Team on 10/17/2019.
 */

public class DriverModel implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("nama_driver")
    private String namaDriver;

    @Expose
    @SerializedName("latitude")
    private double latitude;

    @Expose
    @SerializedName("longitude")
    private double longitude;

    @Expose
    @SerializedName("update_at")
    private Date updateAt;

    @Expose
    @SerializedName("no_telepon")
    private String noTelepon;

    @Expose
    @SerializedName("foto")
    private String foto;

    @Expose
    @SerializedName("reg_id")
    private String regId;

    @Expose
    @SerializedName("driver_job")
    private String driverJob;

    @Expose
    @SerializedName("distance")
    private String distance;

    @Expose
    @SerializedName("merek")
    private String merek;

    @Expose
    @SerializedName("nomor_kendaraan")
    private String nomor_kendaraan;

    @Expose
    @SerializedName("warna")
    private String warna;

    @Expose
    @SerializedName("tipe")
    private String tipe;

    @Expose
    @SerializedName("bearing")
    private String bearing;

    @Expose
    @SerializedName("gender")
    private String jenis;

    @Expose
    @SerializedName("rating")
    private float rating;

    @Expose
    @SerializedName("status")
    private String status;


    public String getNamaDriver() {
        return namaDriver;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public String getFoto() {
        return foto;
    }

    public String getRegId() {
        return regId;
    }

    public String getMerek() {
        return merek;
    }

    public String getNomor_kendaraan() {
        return nomor_kendaraan;
    }

    public String getTipe() {
        return tipe;
    }

    public String getId() {
        return id;
    }

    public String getBearing() {
        return bearing;
    }

    public String getJenis() {
        return jenis;
    }

    public float getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

}
