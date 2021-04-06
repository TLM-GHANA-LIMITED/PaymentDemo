package com.tlmghana.paymentdemo.utils;

import android.app.AlertDialog;
import android.content.Context;

public class constant {


    // FILL IN YOUR OWN DETAILS

    public static String ENDPOINT = "https://sandbox.expresspaygh.com/api/sdk/php/server.php";
    public static String TAGS = "TAG";
    public static String SUBMIT = "submit";
    public static String ORDER_ID = "";
    public static String CURRENCY = "GHS";
    public static String AMOUNT = "";
    public static String ORDER_DESC = "";
    public static String USER_NAME = "";
    public static String FIRST_NAME = "";
    public static String LAST_NAME = "";
    public static String EMAIL = "";
    public static String PHONE = "";
    public static String ACCOUNT_NUMBER = "";


    public static void initDialog(String message, Context mCtx) {
        new AlertDialog.Builder(mCtx)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

}


