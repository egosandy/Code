package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class InbokModel  extends RealmObject implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("idpesan")
    private String idpesan;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("konten")
    private String konten;
    @Expose
    @SerializedName("date")
    private String date;

    public String getTitle() {
        return title;
    }

    public String getKonten() {
        return konten;
    }

    public String getDate() {
        return date;
    }
}
