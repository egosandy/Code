package com.rcdriver.cs.models.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaksi implements Serializable {
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("invoice")
    private String invoice;

    @Expose
    @SerializedName("tipe")
    private int tipe;

    @Expose
    @SerializedName("metode")
    private int metode;

    @Expose
    @SerializedName("metode_jenis")
    private int metodeJenis;

    @Expose
    @SerializedName("note")
    private String note;

    @Expose
    @SerializedName("reff")
    private String reff;

    @Expose
    @SerializedName("nominal")
    private String nominal;

    @Expose
    @SerializedName("biaya")
    private String biaya;

    @Expose
    @SerializedName("total")
    private String total;

    @Expose
    @SerializedName("bank_code")
    private String bankCode;

    @Expose
    @SerializedName("account_number")
    private String accountNumber;

    @Expose
    @SerializedName("payment_code")
    private String paymentCode;

    @Expose
    @SerializedName("payment_reff")
    private String paymentReff;

    @Expose
    @SerializedName("expired_date")
    private String expDate;

    @Expose
    @SerializedName("metode_image")
    private String metodeImage;

    @Expose
    @SerializedName("metode_nama")
    private String metodeNama;

    @Expose
    @SerializedName("regtime")
    private String regtime;

    @Expose
    @SerializedName("status")
    private int status;

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getMetodeNama() {
        return metodeNama;
    }

    public void setMetodeNama(String metodeNama) {
        this.metodeNama = metodeNama;
    }

    public String getMetodeImage() {
        return metodeImage;
    }

    public void setMetodeImage(String metodeImage) {
        this.metodeImage = metodeImage;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentReff() {
        return paymentReff;
    }

    public void setPaymentReff(String paymentReff) {
        this.paymentReff = paymentReff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public int getMetode() {
        return metode;
    }

    public void setMetode(int metode) {
        this.metode = metode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReff() {
        return reff;
    }

    public void setReff(String reff) {
        this.reff = reff;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMetodeJenis() {
        return metodeJenis;
    }

    public void setMetodeJenis(int metodeJenis) {
        this.metodeJenis = metodeJenis;
    }
}
