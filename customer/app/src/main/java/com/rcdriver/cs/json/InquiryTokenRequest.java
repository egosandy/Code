package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InquiryTokenRequest {
    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("nomor_tagihan")
    private String customerNo;

    @Expose
    @SerializedName("kode_produk")
    private String kodeProduk;

    @Expose
    @SerializedName("invoice")
    private String invoice;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}
