package com.rcdriver.cs.json;

import com.rcdriver.cs.models.Donasi;
import com.rcdriver.cs.models.digi.Kategori;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rcdriver.cs.models.AllFiturModel;
import com.rcdriver.cs.models.BeritaModel;
import com.rcdriver.cs.models.CatMerchantModel;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.MerchantModel;
import com.rcdriver.cs.models.MerchantNearModel;
import com.rcdriver.cs.models.PayuModel;
import com.rcdriver.cs.models.PromoModel;
import com.rcdriver.cs.models.RatingModel;
import com.rcdriver.cs.models.User;

/**
 * Created by Maswend Team on 10/13/2019.
 */

public class GetHomeResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("saldo")
    @Expose
    private String saldo;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("currency_text")
    @Expose
    private String currency_text;

    @SerializedName("app_aboutus")
    @Expose
    private String aboutus;

    @SerializedName("app_email")
    @Expose
    private String email;

    @SerializedName("app_contact")
    @Expose
    private String phone;

    @SerializedName("app_website")
    @Expose
    private String website;

    @SerializedName("sender_wasap")
    @Expose
    private String senderWasap;

    @SerializedName("main_background")
    @Expose
    private String MainBG;

    @SerializedName("saldo_background")
    @Expose
    private String SaldoBG;


    @SerializedName("stripe_active")
    @Expose
    private String stripeactive;

    @SerializedName("paypal_key")
    @Expose
    private String paypalkey;

    @SerializedName("paypal_mode")
    @Expose
    private String paypalmode;

    @SerializedName("paypal_active")
    @Expose
    private String paypalactive;

    @SerializedName("minimum_transfer")
    @Expose
    private String minTransfer;

    @SerializedName("minimum_wallet")
    @Expose
    private String minWallet;

    @SerializedName("fitur")
    @Expose
    private List<FiturModel> fitur = new ArrayList<>();

    @SerializedName("allfitur")
    @Expose
    private List<AllFiturModel> allfitur = new ArrayList<>();

    @SerializedName("ratinghome")
    @Expose
    private List<RatingModel> rating = new ArrayList<>();

    @SerializedName("beritahome")
    @Expose
    private List<BeritaModel> berita = new ArrayList<>();

    @SerializedName("slider")
    @Expose
    private List<PromoModel> slider = new ArrayList<>();

    @SerializedName("data")
    @Expose
    private List<User> data = new ArrayList<>();

    @SerializedName("merchantpromo")
    @Expose
    private List<MerchantModel> merchantpromo = new ArrayList<>();

    @SerializedName("merchantnearby")
    @Expose
    private List<MerchantNearModel> merchantnear = new ArrayList<>();

    @SerializedName("kategorymerchanthome")
    @Expose
    private List<CatMerchantModel> catmerchant = new ArrayList<>();

    @SerializedName("payu")
    @Expose
    private List<PayuModel> payu = new ArrayList<>();

    @SerializedName("ppob")
    @Expose
    private List<Kategori> ppob = new ArrayList<>();

    @SerializedName("donasi")
    @Expose
    private List<Donasi> donasiList = new ArrayList<>();

    public List<Donasi> getDonasiList() {
        return donasiList;
    }

    public void setDonasiList(List<Donasi> donasiList) {
        this.donasiList = donasiList;
    }

    public List<Kategori> getPpob() {
        return ppob;
    }

    public void setPpob(List<Kategori> ppob) {
        this.ppob = ppob;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencytext() {
        return currency_text;
    }

    public void setCurrencytext(String currencytext) {
        this.currency_text = currencytext;
    }

    public List<FiturModel> getFitur() {
        return fitur;
    }

    public void setFitur(List<FiturModel> fitur) {
        this.fitur = fitur;
    }

    public List<PromoModel> getSlider() {
        return slider;
    }

    public void setSlider(List<PromoModel> slider) {
        this.slider = slider;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<RatingModel> getRating() {
        return rating;
    }

    public void setRating(List<RatingModel> rating) {
        this.rating = rating;
    }

    public List<BeritaModel> getBerita() {
        return berita;
    }

    public void setBerita(List<BeritaModel> berita) {
        this.berita = berita;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public String getStripeactive() {
        return stripeactive;
    }

    public void setStripeactive(String stripeactive) {
        this.stripeactive = stripeactive;
    }

    public String getPaypalkey() {
        return paypalkey;
    }

    public void setPaypalkey(String paypalkey) {
        this.paypalkey = paypalkey;
    }

    public String getPaypalmode() {
        return paypalmode;
    }

    public void setPaypalmode(String paypalmode) {
        this.paypalmode = paypalmode;
    }

    public String getPaypalactive() {
        return paypalactive;
    }

    public void setPaypalactive(String paypalactive) {
        this.paypalactive = paypalactive;
    }

    public List<MerchantModel> getMerchantpromo() {
        return merchantpromo;
    }

    public void setMerchantpromo(List<MerchantModel> merchantpromo) {
        this.merchantpromo = merchantpromo;
    }

    public List<CatMerchantModel> getCatmerchant() {
        return catmerchant;
    }

    public void setCatmerchant(List<CatMerchantModel> catmerchant) {
        this.catmerchant = catmerchant;
    }

    public List<MerchantNearModel> getMerchantnear() {
        return merchantnear;
    }

    public void setMerchantnear(List<MerchantNearModel> merchantnear) {
        this.merchantnear = merchantnear;
    }

    public List<AllFiturModel> getAllfitur() {
        return allfitur;
    }

    public void setAllfitur(List<AllFiturModel> allfitur) {
        this.allfitur = allfitur;
    }

    public List<PayuModel> getPayu() {
        return payu;
    }

    public void setPayu(List<PayuModel> payu) {
        this.payu = payu;
    }

    public String getMainBG() {
        return MainBG;
    }

    public void setMainBG(String MainBG) {
        this.MainBG = MainBG;
    }

    public String getSaldoBG() {
        return SaldoBG;
    }

    public void setSaldoBG(String SaldoBG) {
        this.SaldoBG = SaldoBG;
    }

    public String getMinTransfer() {
        return minTransfer;
    }

    public void setMinTransfer(String minTransfer) {
        this.minTransfer = minTransfer;
    }

    public String getMinWallet() {
        return minWallet;
    }

    public void setMinWallet(String minWallet) {
        this.minWallet = minWallet;
    }

    public String getSenderWasap() {
        return senderWasap;
    }

    public void setSenderWasap(String senderWasap) {
        this.senderWasap = senderWasap;
    }
}
