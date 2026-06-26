package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 10/17/2019.
 */

public class PayuModel implements Serializable {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("payu_key")
    private String payukey;

    @Expose
    @SerializedName("payu_id")
    private String payuid;

    @Expose
    @SerializedName("payu_salt")
    private String payusalt;

    @Expose
    @SerializedName("payu_debug")
    private String payudebug;

    @Expose
    @SerializedName("active")
    private String active;


}
