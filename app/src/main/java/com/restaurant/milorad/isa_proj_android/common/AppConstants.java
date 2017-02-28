package com.restaurant.milorad.isa_proj_android.common;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppConstants {

    public static SimpleDateFormat sDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
    /**
     * Shared preference keys
     */
    public static final String PREF_TOKEN_KEY = "token";
    public static final String PREF_EMAIL_KEY = "email";
    public static final String PREF_ROLE_KEY = "role";
    public static final String PREF_PASSWORD_KEY = "password";
    public static final String PREF_USER_DB_ID = "user_db_id";


    /**
     * Share intent constants
     */

    public static long INVALID_USER_ID = -1;
}
