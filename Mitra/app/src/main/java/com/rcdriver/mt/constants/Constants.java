package com.rcdriver.mt.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by ourdevelops Team on 10/23/2020.
 */

public class Constants {

    private static final String BASE_URL = "https://www.rc-drive.com/";
    public static final String CONNECTION = BASE_URL + "api/";
    public static final String IMAGESDRIVER = BASE_URL + "images/fotodriver/";
    public static final String IMAGESMERCHANT = BASE_URL + "images/merchant/";
    public static final String IMAGESBANK = BASE_URL + "images/bank/";
    public static final String IMAGESITEM = BASE_URL + "images/itemmerchant/";
    public static final String IMAGESPELANGGAN = BASE_URL + "images/pelanggan/";
    public static final String IMAGESSLIDER = BASE_URL + "images/promo/";

    public static Double LATITUDE;
    public static Double LONGITUDE;
    public static String LOCATION;

    public static String USERID = "uid";

    public static String PREF_NAME = "pref_name";

    public static int permission_camera_code = 786;
    public static int permission_write_data = 788;
    public static int permission_Read_data = 789;
    public static int permission_Recording_audio = 790;

    public static SimpleDateFormat df =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static String versionname = "1.0";
    public static String DATA = "intentdata";
    public static String METHOD_NAME = "methodName";
    public static String METHOD = "method";
    public static String TOKEN = "token";

    public static String INTENT_ID = "intentid";
    public static String INTENT_METHOD = "intentmethod";
    public static String INTENT_CODE = "intentcode";
    public static String INTENT_AMOUNT= "intentamount";
    public static String INTENT_FEE = "intentfee";

}
