package com.rcdriver.dr.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedirectUrl {
    @Expose
    @SerializedName("desktop_web_checkout_url")
    private String weburl;

    @Expose
    @SerializedName("mobile_web_checkout_url")
    private String mweburl;

    @Expose
    @SerializedName("mobile_deeplink_checkout_url")
    private String deeplink;

    @Expose
    @SerializedName("qr_checkout_string")
    private String qrstring;

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getMweburl() {
        return mweburl;
    }

    public void setMweburl(String mweburl) {
        this.mweburl = mweburl;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getQrstring() {
        return qrstring;
    }

    public void setQrstring(String qrstring) {
        this.qrstring = qrstring;
    }
}
