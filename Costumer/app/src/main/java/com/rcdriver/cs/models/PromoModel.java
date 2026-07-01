package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoModel {
    @Expose
    @SerializedName("foto")
    private String foto;

    @Expose
    @SerializedName("fitur_promosi")
    private int fitur_promosi;

    @Expose
    @SerializedName("link_promosi")
    private String link_promosi;

    @Expose
    @SerializedName("type_promosi")
    private String type_promosi;

    @Expose
    @SerializedName("icon")
    private String icon;

    public String getFoto() {
        return foto;
    }

    public int getFiturpromosi() {
        return fitur_promosi;
    }

    public String getLinkpromosi() {
        return link_promosi;
    }

    public String getTypepromosi() {
        return type_promosi;
    }

    public String getIcon() {
        return icon;
    }

}
