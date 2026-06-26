package com.rcdriver.cs.models.digi;

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
    @SerializedName("id_user")
    private String idUser;

    @Expose
    @SerializedName("kode_produk")
    private String kodeProduk;

    @Expose
    @SerializedName("nama_produk")
    private String namaProduk;

    @Expose
    @SerializedName("brand")
    private String brand;

    @Expose
    @SerializedName("kategori")
    private String kategori;

    @Expose
    @SerializedName("tipe")
    private String tipe;

    @Expose
    @SerializedName("harga")
    private String harga;

    @Expose
    @SerializedName("icon")
    private String icon;

    @Expose
    @SerializedName("admin")
    private String admin;

    @Expose
    @SerializedName("total")
    private String total;

    @Expose
    @SerializedName("nomor_tagihan")
    private String nomorTagihan;

    @Expose
    @SerializedName("nama_pelanggan")
    private String namaPelanggan;

    @Expose
    @SerializedName("detail")
    private String detail;

    @Expose
    @SerializedName("trx_reff")
    private String trxReff;

    @Expose
    @SerializedName("trx_rc")
    private String trxRc;

    @Expose
    @SerializedName("trx_message")
    private String trxMessage;

    @Expose
    @SerializedName("trx_status")
    private String trxStatus;

    @Expose
    @SerializedName("trx_sn")
    private String trxSn;

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("regtime")
    private String regtime;

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNomorTagihan() {
        return nomorTagihan;
    }

    public void setNomorTagihan(String nomorTagihan) {
        this.nomorTagihan = nomorTagihan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTrxReff() {
        return trxReff;
    }

    public void setTrxReff(String trxReff) {
        this.trxReff = trxReff;
    }

    public String getTrxRc() {
        return trxRc;
    }

    public void setTrxRc(String trxRc) {
        this.trxRc = trxRc;
    }

    public String getTrxMessage() {
        return trxMessage;
    }

    public void setTrxMessage(String trxMessage) {
        this.trxMessage = trxMessage;
    }

    public String getTrxStatus() {
        return trxStatus;
    }

    public void setTrxStatus(String trxStatus) {
        this.trxStatus = trxStatus;
    }

    public String getTrxSn() {
        return trxSn;
    }

    public void setTrxSn(String trxSn) {
        this.trxSn = trxSn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
