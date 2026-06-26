package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStatusRequest {
    @SerializedName("idtrans")
    @Expose
    private String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
