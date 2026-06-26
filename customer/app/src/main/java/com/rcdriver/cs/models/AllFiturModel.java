package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Maswend Team on 10/17/2019.
 */

public class AllFiturModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("id_fitur")
    private int idFitur;

    @Expose
    @SerializedName("fitur")
    private String fitur;

    @Expose
    @SerializedName("biaya")
    private long biaya;

    @Expose
    @SerializedName("biaya_minimum")
    private long biaya_minimum;

    @Expose
    @SerializedName("keterangan_biaya")
    private String keteranganBiaya;

    @Expose
    @SerializedName("keterangan")
    private String keterangan;

    @Expose
    @SerializedName("diskon")
    private String diskon;

    @Expose
    @SerializedName("biaya_akhir")
    private double biayaAkhir;

    @Expose
    @SerializedName("icon")
    private String icon;

    @Expose
    @SerializedName("driver_job")
    private String job;

    @Expose
    @SerializedName("home")
    private String home;

    @Expose
    @SerializedName("background")
    private String background;

    @Expose
    @SerializedName("is_pending")
    private int isPending;

    public int getIsPending() {
        return isPending;
    }

    public void setIsPending(int isPending) {
        this.isPending = isPending;
    }

    public int getIdFitur() {
        return idFitur;
    }

    public String getFitur() {
        return fitur;
    }

    public String getIcon() {
        return icon;
    }

    public String getHome() {
        return home;
    }

    public String getJob() {
        return job;
    }

    public String getBackground() {
        return background;
    }

}
