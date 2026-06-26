package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 10/17/2019.
 */

public class FiturDataModel implements Serializable {

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

    public void setIdFitur(int idFitur) {
        this.idFitur = idFitur;
    }

    public String getFitur() {
        return fitur;
    }

    public void setFitur(String fitur) {
        this.fitur = fitur;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getJob() {
        return job;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
