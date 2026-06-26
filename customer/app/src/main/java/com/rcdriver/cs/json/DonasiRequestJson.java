package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonasiRequestJson {
    @Expose
    @SerializedName("id_donasi")
    private String idDonasi;

    @Expose
    @SerializedName("id_user")
    private String idUser;

    @Expose
    @SerializedName("nominal")
    private String nominal;

    @Expose
    @SerializedName("nama")
    private String nama;

    public String getIdDonasi() {
        return idDonasi;
    }

    public void setIdDonasi(String idDonasi) {
        this.idDonasi = idDonasi;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
