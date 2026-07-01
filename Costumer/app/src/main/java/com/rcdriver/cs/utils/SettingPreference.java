package com.rcdriver.cs.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.rcdriver.cs.constants.Constants;


public class SettingPreference {
    private static final String CURRENCY = "Rp";
    private static final String ABOUTUS = "ABOUTUS";
    private static final String EMAIL = "EMAIL";
    private static final String PHONE = "PHONE";
    private static final String WEBSITE = "WEBSITE";
    private static final String MYSALDO = "0";
    private static final String MYLATITUDE = "MYLATITUDE";
    private static final String MYLONGITUDE = "MYLONGITUDE";
    private static final String ALAMAT = "ALAMAT";
    private static final String NOTIFIKASI = "NOTIFIKASI";
    private static final String NOTIFIKASI_TITLE = "NOTIFIKASI_TITLE";
    private static String MINTRANSFER = "MINTRANSFER";
    private static String MINWALLET = "MINWALLET";
    private static String WASAP = "WASAP";
    private final SharedPreferences pref;

    private SharedPreferences.Editor editor;

    public SettingPreference(Context context) {
        pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void updateTitle(String string) {
        editor = pref.edit();
        editor.putString(NOTIFIKASI_TITLE, string);
        editor.commit();
    }

    public void updateNotif(String string) {
        editor = pref.edit();
        editor.putString(NOTIFIKASI, string);
        editor.commit();
    }

    public void updatemylat(String string) {
        editor = pref.edit();
        editor.putString(MYLATITUDE, string);
        editor.commit();
    }

    public void updatemylong(String string) {
        editor = pref.edit();
        editor.putString(MYLONGITUDE, string);
        editor.commit();
    }

    public void updateAlamat(String string) {
        editor = pref.edit();
        editor.putString(ALAMAT, string);
        editor.commit();
    }

    public void updateSaldo(String string) {
        editor = pref.edit();
        editor.putString(MYSALDO, string);
        editor.commit();
    }

    public void updateCurrency(String string) {
        editor = pref.edit();
        editor.putString(CURRENCY, string);
        editor.commit();
    }

    public void updateabout(String string) {
        editor = pref.edit();
        editor.putString(ABOUTUS, string);
        editor.commit();
    }

    public void updateemail(String string) {
        editor = pref.edit();
        editor.putString(EMAIL, string);
        editor.commit();
    }

    public void updatephone(String string) {
        editor = pref.edit();
        editor.putString(PHONE, string);
        editor.commit();
    }

    public void updateweb(String string) {
        editor = pref.edit();
        editor.putString(WEBSITE, string);
        editor.commit();
    }

    public void updateMinTransfer(String string){
        editor = pref.edit();
        editor.putString(MINTRANSFER, string);
        editor.commit();
    }

    public void updateMinWallet(String string){
        editor = pref.edit();
        editor.putString(MINWALLET, string);
        editor.commit();
    }

    public void updateWasap(String string){
        editor = pref.edit();
        editor.putString(WASAP, string);
        editor.commit();
    }

    public String[] getSetting() {

        String[] settingan = new String[14];
        settingan[0] = pref.getString(CURRENCY, "Rp");
        settingan[1] = pref.getString(ABOUTUS, "");
        settingan[2] = pref.getString(EMAIL, "");
        settingan[3] = pref.getString(PHONE, "");
        settingan[4] = pref.getString(WEBSITE, "");
        settingan[5] = pref.getString(MYSALDO, "0");
        settingan[6] = pref.getString(MYLATITUDE, "0");
        settingan[7] = pref.getString(MYLONGITUDE, "0");
        settingan[8] = pref.getString(ALAMAT, "null");
        settingan[9] = pref.getString(NOTIFIKASI, "Null");
        settingan[10] = pref.getString(NOTIFIKASI_TITLE, "Null");
        settingan[11] = pref.getString(MINTRANSFER, "0");
        settingan[12] = pref.getString(MINWALLET, "0");
        settingan[13] = pref.getString(WASAP, "");
        return settingan;
    }
}