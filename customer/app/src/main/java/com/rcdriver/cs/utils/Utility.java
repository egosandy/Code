package com.rcdriver.cs.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Maswend Team on 12/2/2019.
 */

public class Utility {


    public static TextWatcher currencyTW(final EditText editText, final Context context) {
        final SettingPreference sp = new SettingPreference(context);
        return new TextWatcher() {

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
                    if (originalString.contains(sp.getSetting()[0] + " ")) {
                        originalString = originalString.replaceAll(sp.getSetting()[0] + " ", "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replaceAll(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replace(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replace(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(" ")) {
                        originalString = originalString.replaceAll(" ", "");
                    }
                    longval = Long.parseLong(originalString);
                    if (longval == 0) {
                        editText.setText("");
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 1) {
                        editText.setText(sp.getSetting()[0] + "0.0" + longval);
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 2) {
                        editText.setText(sp.getSetting()[0] + "0." + longval);
                        editText.setSelection(editText.getText().length());
                    } else {
                        Long getprice = longval;
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


    public static void currencyTXT(TextView text, String nomninal, Context context) {
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[0] + "0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[0] + "0." + nomninal);
        } else {
            Long getprice = Long.parseLong(nomninal);
            String ValFormat = "0";
            String zFormat = "";
            zFormat = formatRupiah(getprice);
            ValFormat = zFormat.replaceAll(",00", "");
            text.setText(ValFormat);
        }

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

    public static void currencyDiskon(TextView text, String nomninal, Context context) {
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[0] + "0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[0] + "0." + nomninal);
        } else {
            Long getprice = Long.parseLong(nomninal);
            String ValFormat = "0";
            String zFormat = "";
            zFormat = formatRupiah(getprice);
            ValFormat = zFormat.replaceAll(",00", "");
            text.setText(ValFormat);
        }

    }

    public static long fixPembulatan(long nomninal) {
        String mTotal = strPembulatan(String.valueOf(nomninal));
        String mCekTotal = mTotal.replace(".0","");
        long CekTotal = Long.parseLong(mCekTotal);
        int result = 0;
        if(CekTotal < 0) {
            long pembulatan = nomninal - CekTotal;
            result = Integer.parseInt(String.valueOf(pembulatan));
        }else if(CekTotal < 500){
            long pembulatan = nomninal - CekTotal;
            long finalPembulatan = pembulatan + 500;
            result = Integer.parseInt(String.valueOf(finalPembulatan));
        }else if(CekTotal < 1000) {
            long pembulatan = nomninal - CekTotal;
            long finalPembulatan = pembulatan + 1000;
            result = Integer.parseInt(String.valueOf(finalPembulatan));
        }else{
            result = Integer.parseInt(String.valueOf(nomninal));
        }
        return result;
    }
    public static String strPembulatan(String myString) {
        if(myString.length() > 3)
            return myString.substring(myString.length()-3);
        else
            return myString;
    }
    private static String formatRupiah(Long number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public static String setFormatDateZ(String originalDate) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

        Date d = null;
        try {
            d = input.parse(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(d);
        return formatted;
    }


}
