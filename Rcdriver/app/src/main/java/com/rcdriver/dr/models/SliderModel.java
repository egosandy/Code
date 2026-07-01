package com.rcdriver.dr.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class SliderModel extends RealmObject implements Serializable {
    @Expose
    @SerializedName("fitur_promosi")
    private String fitur;
    @Expose
    @SerializedName("foto")
    private String foto;

    public String getFitur() {
        return fitur;
    }
    public void setFitur(String fitur) {
        this.fitur = fitur;
    }

    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
