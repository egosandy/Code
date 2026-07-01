package com.rcdriver.dr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class IkonModel extends RealmObject implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("driver_job")
    @Expose
    private String job;

    @SerializedName("icon")
    @Expose
    private String ikon;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }

    public String getIkon() {
        return ikon;
    }
    public void setIkon(String ikon) {
        this.ikon = ikon;
    }
}
