package com.rcdriver.dr.json.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPaymentJson {
    @Expose
    @SerializedName("id_method")
    private String id;

    @Expose
    @SerializedName("amount")
    private String amount;

    @Expose
    @SerializedName("id_user")
    private String idUser;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("nama_depan")
    private String namaDepan;

    @Expose
    @SerializedName("nama_belakang")
    private String namaBelakang;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }
}
