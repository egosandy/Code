package com.rcdriver.dr.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.rcdriver.dr.constants.Constants;


public class SettingPreference {

    private static final String JOB = "0";
    private static String KEY_AUTO_BID = "AUTO_BID";
    private static String KEY_MAKSIMAL_BELANJA = "MAKSIMAL_BELANJA";
    private static String KEY_KERJA = "KERJA";
    private static String KEY_NOTIF = "NOTIF";
    private static String CURRENCY = "Rp";
    private static String ABOUTUS = "ABOUTUS";
    private static String EMAIL = "EMAIL";
    private static String PHONE = "PHONE";
    private static String WEBSITE = "WEBSITE";
    private static String STATUSDRIVER = "statusdriver";
    private static String MAPAPIKEY = "mapkey";
    private static String RESPON = "mapkey";
    private static String FLOATING = "OFF";
    private static String ORDERAN = "OFF";
    private static String ISHOME = "TRUE";
    private static String MINTRANSFER = "MINTRANSFER";
    private static String MINWALLET = "MINWALLET";
    private static String WASAP = "WASAP";
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    public SettingPreference(Context context) {
        pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void updateHome(String string) {
        editor = pref.edit();
        editor.putString(ISHOME, string);
        editor.commit();
    }

    public void updateFloat(String autofloat) {
        editor = pref.edit();
        editor.putString(FLOATING, autofloat);
        editor.commit();
    }

    public void updateAutoBid(String autoBid) {
        editor = pref.edit();
        editor.putString(KEY_AUTO_BID, autoBid);
        editor.commit();
    }

    public void updateMaksimalBelanja(String max) {
        editor = pref.edit();
        editor.putString(KEY_MAKSIMAL_BELANJA, max);
        editor.commit();
    }

    public void updateKerja(String kerja) {
        editor = pref.edit();
        editor.putString(KEY_KERJA, kerja);
        editor.commit();
    }

    public void updateCurrency(String kerja) {
        editor = pref.edit();
        editor.putString(CURRENCY, kerja);
        editor.commit();
    }

    public void updateNotif(String version) {
        editor = pref.edit();
        editor.putString(KEY_NOTIF, version);
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

    public void updateJob(String string) {
        editor = pref.edit();
        editor.putString(JOB, string);
        editor.commit();
    }

    public void updateMapKey(String mapkey) {
        editor = pref.edit();
        editor.putString(MAPAPIKEY, mapkey);
        editor.commit();
    }
    public void updateRespon(String respon) {
        editor = pref.edit();
        editor.putString(RESPON, respon);
        editor.commit();
    }
    public void updateorderan(String string) {
        editor = pref.edit();
        editor.putString(ORDERAN, string);
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

        String[] settingan = new String[19];
        settingan[0] = pref.getString(KEY_AUTO_BID, "OFF");
        settingan[1] = pref.getString(KEY_MAKSIMAL_BELANJA, "Unlimited");
        settingan[2] = pref.getString(KEY_KERJA, "OFF");
        settingan[3] = pref.getString(KEY_NOTIF, "OFF");
        settingan[4] = pref.getString(CURRENCY, "Rp");
        settingan[5] = pref.getString(ABOUTUS, "");
        settingan[6] = pref.getString(EMAIL, "");
        settingan[7] = pref.getString(PHONE, "");
        settingan[8] = pref.getString(WEBSITE, "");
        settingan[9] = pref.getString(STATUSDRIVER, "0");
        settingan[10] = pref.getString(JOB, "0");
        settingan[11] = pref.getString(MAPAPIKEY, "0");
        settingan[12] = pref.getString(RESPON, "1");
        settingan[13] = pref.getString(FLOATING, "OFF");
        settingan[14] = pref.getString(ORDERAN, "OFF");
        settingan[15] = pref.getString(ISHOME, "TRUE");
        settingan[16] = pref.getString(MINTRANSFER, "0");
        settingan[17] = pref.getString(MINWALLET, "0");
        settingan[18] = pref.getString(WASAP, "");
        return settingan;
    }

    public void logout() {
        editor = pref.edit();
        editor.putString(KEY_AUTO_BID, "");
        editor.putString(KEY_MAKSIMAL_BELANJA, "");
        editor.putString(KEY_KERJA, "");
        editor.commit();
    }
}