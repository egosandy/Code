package com.rcdriver.cs.json;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SaveStatusRequest {
    @Expose
    @SerializedName("id_driver")
    public String id;

    @Expose
    @SerializedName("status")
    public String status;

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
