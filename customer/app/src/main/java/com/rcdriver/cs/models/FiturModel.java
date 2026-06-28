package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Maswend Team on 10/17/2019.
 */

public class FiturModel extends RealmObject implements Serializable {

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
    @SerializedName("icon_driver")
    private String icon_driver;

    @Expose
    @SerializedName("home")
    private String home;

    @Expose
    @SerializedName("maks_distance")
    private String maksimumdist;

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

    public long getBiaya() {
        return biaya;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public long getBiaya_minimum() {
        return biaya_minimum;
    }

    public String getDiskon() {
        return diskon;
    }

    public double getBiayaAkhir() {
        return biayaAkhir;
    }

    public String getIcon() {
        return icon;
    }


    public String getHome() {
        return home;
    }

    public String getIcon_driver() {
        return icon_driver;
    }

    public String getMaksimumdist() {
        return maksimumdist;
    }

    public String getBackground() {
        return background;
    }

}
