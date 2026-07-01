package com.rcdriver.dr.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
// PERBAIKAN: Menggunakan Base64 bawaan Android
import android.util.Base64;
import android.widget.EditText;
import android.widget.TextView;

// HAPUS: Import yang salah dan menyebabkan error
// import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.rcdriver.dr.constants.Constants;

/**
 * Created by Ourdevelops Team on 12/2/2019.
 */

public class Utility {

    public static TextWatcher currencyTW(final EditText editText, final Context context) {
        final SettingPreference sp = new SettingPreference(context);
        return new TextWatcher() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(".")) {
                        originalString = originalString.replaceAll("[$.]", "");
                    }
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    if (originalString.contains(sp.getSetting()[4] + " ")) {
                        originalString = originalString.replaceAll(sp.getSetting()[4] + " ", "");
                    }
                    if (originalString.contains(sp.getSetting()[4])) {
                        originalString = originalString.replaceAll(sp.getSetting()[4], "");
                    }
                    if (originalString.contains(sp.getSetting()[4])) {
                        originalString = originalString.replace(sp.getSetting()[4], "");
                    }
                    if (originalString.contains(sp.getSetting()[4])) {
                        originalString = originalString.replace(sp.getSetting()[4], "");
                    }
                    if (originalString.contains(" ")) {
                        originalString = originalString.replaceAll(" ", "");
                    }

                    longval = Long.parseLong(originalString);
                    if (longval == 0) {
                        editText.setText("");
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 1) {
                        editText.setText(sp.getSetting()[4] + "0.0" + longval);
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 2) {
                        editText.setText(sp.getSetting()[4] + "0." + longval);
                        editText.setSelection(editText.getText().length());
                    } else {
                        Double getprice = Double.valueOf(longval);
                        String zFormat = formatRupiah(getprice);
                        String ValFormat = zFormat.replaceAll(",00", "");
                        editText.setText(ValFormat);
                        editText.setSelection(editText.getText().length());
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
    }

    @SuppressLint("SetTextI18n")
    public static void currencyTXT(TextView text, String nomninal, Context context) {
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[4] + "0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[4] + "0." + nomninal);
        } else {
            try {
                Double getprice = Double.valueOf(nomninal);
                String zFormat = formatRupiah(getprice);
                String ValFormat = zFormat.replaceAll(",00", "");
                text.setText(ValFormat);
            } catch (NumberFormatException e) {
                // Handle the exception here
                e.printStackTrace();
                // Alternatively, you can display an error message or take appropriate action
            }
        }
    }


    private static String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public static Double d(String s){
        Double localDouble1 = Double.valueOf(0.0D);
        try {
            Double localDouble2 = Double.valueOf(Double.parseDouble(s));
            return localDouble2;
        } catch (Exception localException) {
        }
        return localDouble1;
    }

    public static String toformatRupiah(String s){
        Locale localeID = new Locale("in", "ID");
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(localeID);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        decimalFormat.setMaximumFractionDigits(0);
        return decimalFormat.format(d(s)).replace(",", ".");
    }

    public static void Konektor(){
        try{
            String headertext = "aHR0cHM6Ly9saWMuZ29qYXNhLmNvbS9hcGkvdmFsaWRhdGUvbGljZW5zZWtleS8=";

            // PERBAIKAN: Menggunakan metode decode dari android.util.Base64
            byte[] decodedBytes = Base64.decode(headertext, Base64.DEFAULT);
            String decodedString = new String(decodedBytes);

            String sURL = decodedString + Constants.LICENSEKEY + "/2";
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            String syskonek = rootobj.get("status").getAsString(); //just grab the zipcode
            Log.d("siskonek", sURL);
            if(!syskonek.equals("200")){
                android.util.Log.d("Koneksi", "Tidak Terhubung");
            }else{
                Log.d("Koneksi", "Terhubung");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String setFormatDateZ(String originalDate) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        SimpleDateFormat output = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());

        Date d = null;
        try {
            d = input.parse(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (d != null) {
            return output.format(d);
        }
        return originalDate; // return original string if parsing fails
    }
}