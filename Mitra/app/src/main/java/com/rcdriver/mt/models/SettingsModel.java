package com.rcdriver.mt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ourdevelops Team on 12/01/2019.
 */

public class SettingsModel implements Serializable {
    @Expose
    @SerializedName("app_privacy_policy")
    public String privacy;

    public String getPrivacy() {
        return privacy;
    }

    @Expose
    @SerializedName("versi_mitra")
    private int versionCode;

    @Expose
    @SerializedName("force_update_mitra")
    private int forceUpdate;

    @Expose
    @SerializedName("isotp")
    private int isOtp;

    public int getIsOtp() {
        return isOtp;
    }

    public void setIsOtp(int isOtp) {
        this.isOtp = isOtp;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }


}
