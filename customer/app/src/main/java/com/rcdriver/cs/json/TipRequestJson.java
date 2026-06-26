package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipRequestJson {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("iddriver")
    @Expose
    private String iddriver;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("no_telepon")
    @Expose
    private String no_telepon;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getIddriver() {
        return iddriver;
    }
    public void setIddriver(String iddriver) {
        this.iddriver = iddriver;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telepon() {
        return no_telepon;
    }
    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }
}
