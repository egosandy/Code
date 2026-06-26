package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Maswend Team on 10/17/2019.
 */

public class FcmKeyModel implements Serializable {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("fcm_key")
    private String fcmkey;


}
