package com.rcdriver.dr.models;

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
    @SerializedName("isotp")
    private int isOtp;

    public int getIsOtp() {
        return isOtp;
    }

    public void setIsOtp(int isOtp) {
        this.isOtp = isOtp;
    }

    //----------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMapkey() {
        return mapkey;
    }

    public void setMapkey(String mapkey) {
        this.mapkey = mapkey;
    }
    //------------------------------ Midtrans ------------------------------------------
    public String getBaseurl() {
        return baseurl;
    }
    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }
    public String getClientkey() {
        return clientkey;
    }
    public void setClientkey(String clientkey) {
        this.clientkey = clientkey;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    //------------------------------ MobilePulsa ------------------------------------------
    public String getMpurl() {
        return mpurl;
    }
    public void setMpurl(String mpurl) {
        this.mpurl = mpurl;
    }
    public String getMpuser() {
        return mpuser;
    }
    public void setMpuser(String mpuser) {
        this.mpuser = mpuser;
    }
    public String getMppass() {
        return mppass;
    }
    public void setMppass(String mppass) {
        this.mppass = mppass;
    }
    public String getMpstatus() {
        return mpstatus;
    }
    public void setMpstatus(String mpstatus) {
        this.mpstatus = mpstatus;
    }
}
