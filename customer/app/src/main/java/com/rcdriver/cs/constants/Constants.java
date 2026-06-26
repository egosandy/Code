package com.rcdriver.cs.constants;


import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Created by Maswend Team on 12/23/2019.
 */

public class Constants {
    public static final int REJECT = 0;
    public static final int ACCEPT = 2;
    public static final int CANCEL = 5;
    public static final int START = 3;
    public static final int FINISH = 4;
    public static final int CHANGE = 7;
    public static final String DONASI_LIST_KEY = "donasi_list_key";

    private static final String BASE_URL = "https://www.rc-drive.com/";
    public static final String MAINTENANCE_URL = BASE_URL + "maintenance.html";
    public static final String CONNECTION = BASE_URL + "api/";
    public static final String BASE_JOB = BASE_URL + "images/icon/";
    public static final String IMAGESPPOB = BASE_URL + "images/ppob/";
    public static final String IMAGESFITUR = BASE_URL + "images/fitur/";
    public static final String IMAGESMERCHANT = BASE_URL + "images/merchant/";
    public static final String IMAGESKATMERCHANT = BASE_URL + "images/kategorimerchant/";
    public static final String IMAGESBANK = BASE_URL + "images/bank/";
    public static final String IMAGESITEM = BASE_URL + "images/itemmerchant/";
    public static final String IMAGESBERITA = BASE_URL + "images/berita/";
    public static final String IMAGESSLIDER = BASE_URL + "images/promo/";
    public static final String IMAGESDRIVER = BASE_URL + "images/fotodriver/";
    public static final String IMAGESUSER = BASE_URL + "images/pelanggan/";
    public static final String IMAGEVOUCHER = BASE_URL + "images/promo/";
    //---------------------------- New Var PPOB -------------------------------------------------
    public static final String IMAGEPPOB = BASE_URL + "images/ppob/";
    //---------------------------- Variable MIDTRANS --------------------------------------------

    //---------------------------- Variable PPOB --------------------------------------------

    public static String Potongan = "0";
    public static Double LATITUDE;
    public static Double LONGITUDE;
    public static String LOCATION;
    public static String METHOD = "method";
    public static String METHOD_NAME = "methodName";
    public static String METHOD_TYPE = "methodType";
    public static String PULSA = "pulsa";
    public static String DATA = "data";
    public static String PLN = "pln";
    public static String ETOLL = "etoll";
    public static String TOKEN = "token";
    public static String USERID = "uid";
    public static String PREF_NAME = "pref_name";
    public static int permission_camera_code = 786;
    public static int permission_write_data = 788;
    public static int permission_Read_data = 789;
    public static int permission_Recording_audio = 790;
    public static int IS_PROSES = 0;
    public static String IS_INQUIRY = "isInquiry";
    public static SimpleDateFormat df =
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);

    public static final String TAG = "chloe-mobile";
    public static String INTENT_ID = "intentid";
    public static String INTENT_METHOD = "intentmethod";
    public static String INTENT_CODE = "intentcode";
    public static String INTENT_AMOUNT= "intentamount";
    public static String INTENT_FEE = "intentfee";
}
