package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SettingModel extends RealmObject implements Serializable {
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("map_key")
    private String mapkey;

    @Expose
    @SerializedName("app_name")
    private String namapp;

    @Expose
    @SerializedName("maintenance")
    private String maintenance;
    //---------------------------- Ads Admob --------------------------------
    @Expose
    @SerializedName("bannerid")
    private String bannerid;

    @Expose
    @SerializedName("bannerunit")
    private String bannerunit;

    @Expose
    @SerializedName("banneraktif")
    private int banneraktif;
    //---------------------------- Midtrans --------------------------------
    @Expose
    @SerializedName("midtrans_url")
    private String baseurl;

    @Expose
    @SerializedName("midtrans_key")
    private String clientkey;

    @Expose
    @SerializedName("midtrans_aktif")
    private String status;
    //--------------------------- MobilePulsa ------------------------------
    @Expose
    @SerializedName("mobilepulsa_url")
    private String mpurl;

    @Expose
    @SerializedName("mobilepulsa_user")
    private String mpuser;

    @Expose
    @SerializedName("mobilepulsa_pass")
    private String mppass;

    @Expose
    @SerializedName("mobilepulsa_aktif")
    private String mpstatus;

    @Expose
    @SerializedName("app_contact")
    private String kontak;

    @Expose
    @SerializedName("isotp")
    private String isotp;

    @Expose
    @SerializedName("versi_cs")
    private int versionCode;

    @Expose
    @SerializedName("force_update_user")
    private int forceUpdate;





    //----------------------------------------------------------------------

    public String getNamapp() {
        return namapp;
    }

    public String getMapkey() {
        return mapkey;
    }

    public String getMaintenance() {
        return maintenance;
    }

    //------------------------------ Midtrans ------------------------------------------
    public String getBaseurl() {
        return baseurl;
    }

    public String getClientkey() {
        return clientkey;
    }

    public String getStatus() {
        return status;
    }

    //------------------------------ Admob ------------------------------------------
    public String getBannerid() {
        return bannerid;
    }

    public String getBannerunit() {
        return bannerunit;
    }

    public int getBanneraktif() {
        return banneraktif;
    }

    //------------------------------ MobilePulsa ------------------------------------------
    public String getMpurl() {
        return mpurl;
    }

    public String getMpuser() {
        return mpuser;
    }

    public String getMppass() {
        return mppass;
    }

    public String getMpstatus() {
        return mpstatus;
    }

    public String getKontak() {
        return kontak;
    }

    public String getIsotp() {
        return isotp;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
