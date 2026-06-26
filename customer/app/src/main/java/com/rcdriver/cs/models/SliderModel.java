package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class SliderModel implements Serializable {
    @Expose
    @SerializedName("fitur_promosi")
    private String fitur;
    @Expose
    @SerializedName("foto")
    private String foto;

    public String getFoto() {
        return foto;
    }
}
