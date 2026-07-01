package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public class AllMerchantbyCatRequestJson {

    @SerializedName("latitude")
    @Expose
    private String lat;

    @SerializedName("longitude")
    @Expose
    private String lon;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("no_telepon")
    @Expose
    private String phone;

    @SerializedName("fitur")
    @Expose
    private String fitur;

    @SerializedName("section")
    @Expose
    private String section;

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getFitur() {
        return fitur;
    }

    public void setFitur(String fitur) {
        this.fitur = fitur;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setKategori(String fitur) {
        this.fitur = fitur;
    }
}
