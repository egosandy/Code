package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 10/13/2019.
 */

public class User implements Serializable {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("fullnama")
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

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("created_on")
    @Expose
    private String createdOn;

    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("fotopelanggan")
    @Expose
    private String fotopelanggan;

    @SerializedName("countrycode")
    @Expose
    private String countrycode;

    @SerializedName("istopup")
    @Expose
    private int istopup;

    @SerializedName("noreff")
    @Expose
    private String noreff;

    @SerializedName("saldo")
    @Expose
    private long walletSaldo;

    public String getId() {
        return id;
    }

    public String getFullnama() {
        return fullnama;
    }

    public String getEmail() {
        return email;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTglLahir() {
        return tglLahir;
    }


    public String getToken() {
        return token;
    }

    public long getWalletSaldo() {
        return walletSaldo;
    }

    public void setWalletSaldo(long walletSaldo) {
        this.walletSaldo = walletSaldo;
    }

    public String getFotopelanggan() {
        return fotopelanggan;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public int getIstopup() {
        return istopup;
    }

}
