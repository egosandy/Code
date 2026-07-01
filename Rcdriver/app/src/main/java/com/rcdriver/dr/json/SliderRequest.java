package com.rcdriver.dr.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderRequest {
    @SerializedName("fitur_promosi")
    @Expose
    private String fitur_promosi;
    public String getFitur_promosi() {
        return fitur_promosi;
    }

    public void setFitur_promosi(String fitur_promosi) {
        this.fitur_promosi = fitur_promosi;
    }
}
