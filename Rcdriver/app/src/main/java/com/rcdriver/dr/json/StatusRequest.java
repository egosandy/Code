package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusRequest {
    @SerializedName("id_driver")
    @Expose
    private String id_driver;

    @SerializedName("status")
    @Expose
    private String status;

    public String getId_driver() {
        return id_driver;
    }
    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
