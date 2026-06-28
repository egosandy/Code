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

public class TransaksiSendModel extends RealmObject implements Serializable {

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
    @PrimaryKey
    @Expose
    @SerializedName("id")
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

    public String getId() {
        return id;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public double getJarak() {
        return jarak;
    }

    public long getHarga() {
        return harga;
    }

    public Date getWaktuOrder() {
        return waktuOrder;
    }

    public String getAlamatAsal() {
        return alamatAsal;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public String getKodePromo() {
        return kodePromo;
    }

    public String getKreditPromo() {
        return kreditPromo;
    }

    public boolean isPakaiWallet() {
        return pakaiWallet;
    }

    public String getEstimasi() {
        return estimasi;
    }

}
