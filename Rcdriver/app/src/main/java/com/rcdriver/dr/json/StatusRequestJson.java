package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusRequestJson {
    @SerializedName("id_driver")
    @Expose
    private String id_driver;

    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id_driver;
    }
    public void setId(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
