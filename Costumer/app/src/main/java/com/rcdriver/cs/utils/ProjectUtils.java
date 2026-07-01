package com.rcdriver.cs.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.widget.Toast;


/**
 * Created by otacodes on 27/12/2018.
 */
public class ProjectUtils {

    public static Bitmap bmp;
    private static AlertDialog dialog;
    private static Toast toast;
    private static ProgressDialog mProgressDialog;


    /**
     * Static method to hide the dialog if visible
     */
    public static void hideDialog() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }
    }


    /* public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }*/


}