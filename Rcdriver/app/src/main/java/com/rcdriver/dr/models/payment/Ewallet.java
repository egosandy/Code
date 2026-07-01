package com.rcdriver.dr.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ewallet {
    @Expose
    @SerializedName("trxid")
    private String trxid;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("nominal")
    private int nominal;

    @Expose
    @SerializedName("method")
    private String method;

    @Expose
    @SerializedName("is_redirect")
    private boolean isRedirect;

    @Expose
    @SerializedName("redirect_url")
    private RedirectUrl redirectUrl;

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public RedirectUrl getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(RedirectUrl redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
