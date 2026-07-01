package com.rcdriver.cs.mpmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class PriceListModels extends RealmObject implements Serializable {
    @Expose
    @SerializedName("pulsa_code")
    private String kodepulsa;

    public String getKodepulsa() {
        return kodepulsa;
    }
}
