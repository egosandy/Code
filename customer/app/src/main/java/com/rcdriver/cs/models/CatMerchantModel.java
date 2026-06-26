package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 10/17/2019.
 */

public class CatMerchantModel implements Serializable {

    @Expose
    @SerializedName("id_kategori_merchant")
    private String id_kategori_merchant;

    @Expose
    @SerializedName("nama_kategori")
    private String nama_kategori;

    @Expose
    @SerializedName("foto_kategori")
    private String foto_kategori;

    @Expose
    @SerializedName("id_fitur")
    private String id_fitur;


    public String getId_kategori_merchant() {
        return id_kategori_merchant;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public String getFoto_kategori() {
        return foto_kategori;
    }


}
