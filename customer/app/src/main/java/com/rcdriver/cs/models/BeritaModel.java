package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 10/17/2019.
 */

public class BeritaModel implements Serializable {

    @Expose
    @SerializedName("id_berita")
    private String id_berita;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("content")
    private String content;

    @Expose
    @SerializedName("foto_berita")
    private String foto_berita;

    @Expose
    @SerializedName("created_berita")
    private String created_berita;

    @Expose
    @SerializedName("kategori")
    private String kategori;


    public String getIdberita() {
        return id_berita;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFotoberita() {
        return foto_berita;
    }

    public String getCreatedberita() {
        return created_berita;
    }

    public String getKategori() {
        return kategori;
    }


}
