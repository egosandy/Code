package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Maswend Team on 10/17/2019.
 */

public class RatingModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("fotopelanggan")
    private String fotopelanggan;

    @Expose
    @SerializedName("fullnama")
    private String fullnama;

    @Expose
    @SerializedName("update_at")
    private String update_at;

    @Expose
    @SerializedName("catatan")
    private String catatan;

    @Expose
    @SerializedName("rating")
    private String rating;


    public String getFotopelanggan() {
        return fotopelanggan;
    }

    public String getFullnama() {
        return fullnama;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getRating() {
        return rating;
    }


}
