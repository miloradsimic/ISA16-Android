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
     * Shared fragments keys
     */
    public static final int FRAG_PROFILE = 0;
    public static final int FRAG_RESTAURANTS = 1;
    public static final int FRAG_FRIENDS = 2;


    /**
     * Share intent constants
     */

    public static final String EXTRAS_RESTAURANTS = "restaurants";

    public static long INVALID_USER_ID = -1;
}
