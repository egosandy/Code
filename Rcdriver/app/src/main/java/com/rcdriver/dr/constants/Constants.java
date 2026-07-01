package com.rcdriver.dr.constants;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.rcdriver.dr.models.PelangganModel;

/**
 * Created by ourdevelops Team on 10/23/2020.
 */

public class Constants {
    public static final String MAPBOX_ACCESS_TOKEN = "YOUR_MAPBOX_ACCESS_TOKEN";
    public static final int REJECT = 0;
    public static final int ACCEPT = 2;
    public static final int CANCEL = 5;
    public static final int START = 3;
    public static final int FINISH = 4;
    public static final int CHANGE = 9;
    private static final String BASE_URL = "https://www.rc-drive.com/";
    public static final String CONNECTION = BASE_URL + "api/";
    public static final String IMAGESSLIDER = BASE_URL + "images/promo/";
    public static final String IMAGESPOIN = BASE_URL + "images/poin/";
    public static final String LICENSEKEY = "PML-5L17-1Z55-XJ51-4638";
    //start mwapi
    public static final String BASE_MWAPI = BASE_URL + "mwapi/";
    public static final String URL_TOPUP = BASE_MWAPI + "topup.php";
    public static final String FOTO_MENU = BASE_URL + "images/itemmerchant/";
    public static final String BASE_JOB = BASE_URL + "images/icon/";
    //end mwapi
    public static final String IMAGESFITUR = BASE_URL + "images/fitur/";
    public static final String IMAGESBANK = BASE_URL + "images/bank/";
    public static final String IMAGESDRIVER = BASE_URL + "images/fotodriver/";
    public static final String IMAGESUSER = BASE_URL + "images/pelanggan/";
    public static final String IMAGEVOUCHER = BASE_URL + "images/promo/";
    public static final String IMAGESMERCHANT = BASE_URL + "images/merchant/";

    //---------------------------- Variable cc --------------------------------------------
    public static String URL_MIDTRANS = BASE_URL + "payment/checkout.php/";
    public static String KEY_MIDTRANS = "Mid-client-2qOwIMAx3XetGiWj";
    public static String STATUS_MIDTRANS = "1";
    //-------------------------------Variable APIKEY-----------------------------------------

    //---------------------------------------------------------------------------------------

    public static String CURRENCY = "";
    public static Double LATITUDE;
    public static Double LONGITUDE;
    public static String LOCATION;
    public static String TOKEN = "token";

    public static String USERID = "uid";

    public static String PREF_NAME = "pref_name";

    public static int permission_camera_code = 786;
    public static int permission_write_data = 788;
    public static int permission_Read_data = 789;
    public static int permission_Recording_audio = 790;

    public static SimpleDateFormat df =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static String versionname = "1.0";
    public static String ITEM_SELECT = null; //"#1AC463"
    public static int OPENMAP = 0;
    public static LatLng BASE_LOKASI;
    public static String BASE_STATUS;
    public static String IDTRANS;
    public static String mHome;
    public static PelangganModel mPelanggan;
    public static String mToken;
    public static String mFitur;
    public static boolean mWallet;
    public static String mBiaya;
    public static String mStatus;
    public static String mPoint;
    public static boolean isBackground = false;
    public static String mAktivitas = "Offline";
    public static String DATA = "intentdata";
    public static String METHOD_NAME = "methodName";
    public static String METHOD = "method";
    public static String INTENT_ID = "intentid";
    public static String INTENT_METHOD = "intentmethod";
    public static String INTENT_CODE = "intentcode";
    public static String INTENT_AMOUNT= "intentamount";
    public static String INTENT_FEE = "intentfee";
}
