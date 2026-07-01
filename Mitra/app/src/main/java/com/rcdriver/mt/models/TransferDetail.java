package com.rcdriver.mt.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class TransferDetail implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("tipe")
    private String tipe;

    @Expose
    @SerializedName("invoice")
    private String invoice;

    @Expose
    @SerializedName("sender_wallet_id")
    private String sender_wallet_id;

    @Expose
    @SerializedName("receiver_wallet_id")
    private String receiver_wallet_id;

    @Expose
    @SerializedName("sender_user_id")
    private String sender_user_id;

    @Expose
    @SerializedName("receiver_user_id")
    private String receiver_user_id;

    @Expose
    @SerializedName("saldo_sender_awal")
    private String saldo_sender_awal;

    @Expose
    @SerializedName("saldo_receiver_awal")
    private String saldo_receiver_awal;

    @Expose
    @SerializedName("nominal")
    private String nominal;

    @Expose
    @SerializedName("fee")
    private String fee;

    @Expose
    @SerializedName("note")
    private String note;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("regtime")
    private Date regtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getSender_wallet_id() {
        return sender_wallet_id;
    }

    public void setSender_wallet_id(String sender_wallet_id) {
        this.sender_wallet_id = sender_wallet_id;
    }

    public String getReceiver_wallet_id() {
        return receiver_wallet_id;
    }

    public void setReceiver_wallet_id(String receiver_wallet_id) {
        this.receiver_wallet_id = receiver_wallet_id;
    }

    public String getSender_user_id() {
        return sender_user_id;
    }

    public void setSender_user_id(String sender_user_id) {
        this.sender_user_id = sender_user_id;
    }

    public String getReceiver_user_id() {
        return receiver_user_id;
    }

    public void setReceiver_user_id(String receiver_user_id) {
        this.receiver_user_id = receiver_user_id;
    }

    public String getSaldo_sender_awal() {
        return saldo_sender_awal;
    }

    public void setSaldo_sender_awal(String saldo_sender_awal) {
        this.saldo_sender_awal = saldo_sender_awal;
    }

    public String getSaldo_receiver_awal() {
        return saldo_receiver_awal;
    }

    public void setSaldo_receiver_awal(String saldo_receiver_awal) {
        this.saldo_receiver_awal = saldo_receiver_awal;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }
}
