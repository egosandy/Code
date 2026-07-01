package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiturPromoRequest {
    @SerializedName("fitur")
    @Expose
    private String fitur;
    public String getFitur() {
        return fitur;
    }
    public void setFitur(String fitur) {
        this.fitur = fitur;
    }
}
