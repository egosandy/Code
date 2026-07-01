package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Maswend Team on 12/20/2019.
 */

public class DiskonWalletModel extends RealmObject implements Serializable {

    @SerializedName("diskon")
    @Expose
    private String diskon;
    @SerializedName("biaya_akhir")
    @Expose
    private Double biayaAkhir;

}
