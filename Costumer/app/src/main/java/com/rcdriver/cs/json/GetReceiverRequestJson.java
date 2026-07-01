package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetReceiverRequestJson {

    @SerializedName("id_user")
    @Expose
    private String idUser;

    @SerializedName("no_penerima")
    @Expose
    private String noReceiver;

    @SerializedName("tipe")
    @Expose
    private String tipe;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNoReceiver() {
        return noReceiver;
    }

    public void setNoReceiver(String noReceiver) {
        this.noReceiver = noReceiver;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
