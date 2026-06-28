package com.rcdriver.cs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Maswend Team on 10/19/2019.
 */

public class ProgressModel extends RealmObject implements Serializable {

    @Expose
    @SerializedName("status")
    public int status;
    @Expose
    @SerializedName("nama_pengirim")
    public String namaPengirim;
    @Expose
    @SerializedName("telepon_pengirim")
    public String teleponPengirim;
    @Expose
    @SerializedName("nama_penerima")
    public String namaPenerima;
    @Expose
    @SerializedName("nama_barang")
    public String namaBarang;
    @Expose
    @SerializedName("biaya_akhir")
    public String biaya_akhir;
    @Expose
    @SerializedName("total_biaya")
    public String total_biaya;
    @Expose
    @SerializedName("nama_merchant")
    public String nama_merchant;
    @PrimaryKey
    @Expose
    @SerializedName("com")
    private String id;
    @Expose
    @SerializedName("id_pelanggan")
    private String idPelanggan;
    @Expose
    @SerializedName("id_driver")
    private String idDriver;
    @Expose
    @SerializedName("order_fitur")
    private String orderFitur;
    @Expose
    @SerializedName("start_latitude")
    private double startLatitude;
    @Expose
    @SerializedName("start_longitude")
    private double startLongitude;
    @Expose
    @SerializedName("end_latitude")
    private double endLatitude;
    @Expose
    @SerializedName("end_longitude")
    private double endLongitude;
    @Expose
    @SerializedName("jarak")
    private double jarak;
    @Expose
    @SerializedName("harga")
    private long harga;
    @Expose
    @SerializedName("waktu_order")
    private Date waktuOrder;
    @Expose
    @SerializedName("waktu_selesai")
    private Date waktuSelesai;
    @Expose
    @SerializedName("alamat_asal")
    private String alamatAsal;
    @Expose
    @SerializedName("alamat_tujuan")
    private String alamatTujuan;
    @Expose
    @SerializedName("kode_promo")
    private String kodePromo;
    @Expose
    @SerializedName("kredit_promo")
    private String kreditPromo;
    @Expose
    @SerializedName("pakai_wallet")
    private boolean pakaiWallet;
    @Expose
    @SerializedName("rate")
    private String rate;
    @Expose
    @SerializedName("estimasi_time")
    private String estimasi;

    @Expose
    @SerializedName("icon")
    private String icon;

    @Expose
    @SerializedName("fitur")
    private String fitur;

    @Expose
    @SerializedName("id_transaksi")
    private String idtrans;

    @Expose
    @SerializedName("foto")
    private String fotodriver;

    @Expose
    @SerializedName("nama_driver")
    private String namadriver;

    public int getStatus() {
        return status;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public String getOrderFitur() {
        return orderFitur;
    }

    public String getAlamatAsal() {
        return alamatAsal;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public boolean isPakaiWallet() {
        return pakaiWallet;
    }

    public String getRate() {
        return rate;
    }

    public String getBiaya_akhir() {
        return biaya_akhir;
    }

    public String getFitur() {
        return fitur;
    }

    public String getIcon() {
        return icon;
    }

    public String getIdtrans() {
        return idtrans;
    }

    public String getFotodriver() {
        return fotodriver;
    }

    public String getNamadriver() {
        return namadriver;
    }

}
