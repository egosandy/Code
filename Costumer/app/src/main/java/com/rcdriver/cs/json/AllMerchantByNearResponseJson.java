package com.rcdriver.cs.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.models.CatMerchantModel;
import com.rcdriver.cs.models.MerchantModel;
import com.rcdriver.cs.models.MerchantNearModel;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public class AllMerchantByNearResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("allmerchantnearby")
    @Expose
    private List<MerchantNearModel> data = new ArrayList<>();

    @SerializedName("kategorymerchant")
    @Expose
    private List<CatMerchantModel> kategori = new ArrayList<>();

    @SerializedName("merchantpromo")
    @Expose
    private List<MerchantModel> merchantpromo = new ArrayList<>();

    @SerializedName("merchantnew")
    @Expose
    private List<MerchantNearModel> merchantnew = new ArrayList<>();

    @SerializedName("total_page")
    @Expose
    private int totalPage;

    public List<MerchantNearModel> getMerchantnew() {
        return merchantnew;
    }

    public void setMerchantnew(List<MerchantNearModel> merchantnew) {
        this.merchantnew = merchantnew;
    }

    public List<MerchantModel> getMerchantpromo() {
        return merchantpromo;
    }

    public void setMerchantpromo(List<MerchantModel> merchantpromo) {
        this.merchantpromo = merchantpromo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MerchantNearModel> getData() {
        return data;
    }

    public void setData(List<MerchantNearModel> data) {
        this.data = data;
    }

    public List<CatMerchantModel> getKategori() {
        return kategori;
    }

    public void setKategori(List<CatMerchantModel> kategori) {
        this.kategori = kategori;
    }
}
