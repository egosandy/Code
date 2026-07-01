package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Maswend Team on 10/17/2019.
 */

public class CatItemModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("nama_kategori_item")
    private String nama_kategori_item;

    @Expose
    @SerializedName("id_kategori_item")
    private String id_kategori_item;

    @Expose
    @SerializedName("foto_kategori_item")
    private String foto_kategori;


    public String getId_kategori_item() {
        return id_kategori_item;
    }

    public String getNama_kategori() {
        return nama_kategori_item;
    }

    public String getFoto_kategori() {
        return foto_kategori;
    }

}
