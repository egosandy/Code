package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 12/20/2019.
 */

public class WalletModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;

    @SerializedName("waktu")
    @Expose
    private String waktu;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("nama_pemilik")
    @Expose
    private String namapemilik;

    @SerializedName("rekening")
    @Expose
    private String rekening;

    @SerializedName("bank")
    @Expose
    private String bank;

    @SerializedName("status")
    @Expose
    private String status;

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getBank() {
        return bank;
    }

    public String getNamapemilik() {
        return namapemilik;
    }

    public void setNamapemilik(String namapemilik) {
        this.namapemilik = namapemilik;
    }
}
