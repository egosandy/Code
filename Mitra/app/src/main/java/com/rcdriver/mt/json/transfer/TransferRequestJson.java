package com.rcdriver.mt.json.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferRequestJson {
    @SerializedName("id_user")
    @Expose
    private String id_user;

    @SerializedName("id_receiver")
    @Expose
    private String id_receiver;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("nominal")
    @Expose
    private String nominal;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(String id_receiver) {
        this.id_receiver = id_receiver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }
}
