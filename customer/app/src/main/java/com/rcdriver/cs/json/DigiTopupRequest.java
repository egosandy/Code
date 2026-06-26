package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DigiTopupRequest {
    @Expose
    @SerializedName("id")
    public String idUser;

    @Expose
    @SerializedName("kode_produk")
    public String kodeProduk;

    @Expose
    @SerializedName("nomor_tagihan")
    public String nomorTagihan;

    @Expose
    @SerializedName("ref_id")
    public String ref_id;

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getNomorTagihan() {
        return nomorTagihan;
    }

    public void setNomorTagihan(String nomorTagihan) {
        this.nomorTagihan = nomorTagihan;
    }
}
