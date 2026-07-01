package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Maswend Team on 12/01/2019.
 */

public class SettingsModel implements Serializable {
    @Expose
    @SerializedName("app_privacy_policy")
    public String privacy;

    public String getPrivacy() {
        return privacy;
    }


}
