package com.rcdriver.cs.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public class ResultDriver extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nama_driver")
    @Expose
    private String fullnama;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("no_telepon")
    @Expose
    private String noTelepon;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("alamat_driver")
    @Expose
    private String alamat;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("reg_id")
    @Expose
    private String token;

    @SerializedName("foto")
    @Expose
    private String fotodriver;
    ////
    @SerializedName("id_k")
    @Expose
    private String idk;

    @SerializedName("merek")
    @Expose
    private String merek;

    @SerializedName("tipe")
    @Expose
    private String tipe;

    @SerializedName("gender")
    @Expose
    private String jenis;

    @SerializedName("nomor_kendaraan")
    @Expose
    private String nomor_kendaraan;

    @SerializedName("warna")
    @Expose
    private String warna;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;

    @SerializedName("saldo")
    @Expose
    private long walletSaldo;

    @SerializedName("job")
    @Expose
    private String job;

    @SerializedName("map_key")
    @Expose
    private String mapkey;

    @SerializedName("point")
    @Expose
    private String point;

    @SerializedName("Kota")
    @Expose
    private String kota;

    public String getFullnama() {
        return fullnama;
    }


    public String getToken() {
        return token;
    }

    public String getFotodriver() {
        return fotodriver;
    }

    public String getJenis() {
        return jenis;
    }

}