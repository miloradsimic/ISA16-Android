package com.restaurant.milorad.isa_proj_android.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.activities.AdminActivity;
import com.restaurant.milorad.isa_proj_android.activities.MainActivity;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Rade on 5/22/2015.
 */
public class AppUtils {

    private static Calendar cal;
    private static ZctLogger mLogger = new ZctLogger(AppUtils.class.getSimpleName(), BuildConfig.DEBUG);

    public static boolean isEmailValid(String pEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(pEmail).matches();
    }

    public static boolean isPasswordValid(String pPassword) {
        return pPassword.length() >= 3;
    }

    public static void hideKeyboard(Activity pActivity) {
        // Check if no view has focus:
        View view = pActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) pActivity.getSystemService(pActivity.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String genBase64(String pBaseString) {
        byte[] mData = new byte[0];
        try {
            mData = pBaseString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            mLogger.e(e.toString());
            return "";
        }
        return "Basic " + Base64.encodeToString(pBaseString.getBytes(), Base64.NO_WRAP);
    }

    public static ProgressDialog showProgress(Context context) {
        return ProgressDialog.show(context, "", context.getString(R.string.g_loading_msg), true);
    }

    public static void hideProgress(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void go2MainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("restaurants", data);
        context.startActivity(intent);
    }


    public static void go2AdminActivity(Context context) {
        Intent intent = new Intent(context, AdminActivity.class);
        context.startActivity(intent);
    }

    public static String dateToString(Date date) {
        cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH) + "-" +
                (cal.get(Calendar.MONTH)+1) + "-" +
                cal.get(Calendar.YEAR);
    }
    public static String timeToString(Date time) {
        cal = GregorianCalendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.HOUR_OF_DAY) + ":" +
                cal.get(Calendar.MINUTE);
    }


    public static Long getTimestampInMs() {
        Long retTime = System.currentTimeMillis();
        mLogger.e("getTimestampInMs: " + AppConstants.sDateTimeFormat.format(retTime));
        return retTime;
    }
}
