package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Maswend Team on 10/17/2019.
 */

public class BankModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Expose
    @SerializedName("id_bank")
    private String id_bank;

    @Expose
    @SerializedName("nama_bank")
    private String nama_bank;

    @Expose
    @SerializedName("image_bank")
    private String image_bank;

    @Expose
    @SerializedName("rekening_bank")
    private String rekening_bank;


    public String getNama_bank() {
        return nama_bank;
    }

    public String getRekening_bank() {
        return rekening_bank;
    }

    public String getImage_bank() {
        return image_bank;
    }

}
