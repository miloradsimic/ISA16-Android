package com.restaurant.milorad.isa_proj_android.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.restaurant.milorad.isa_proj_android.BuildConfig;


/**
 * Created by Core Station on 10/29/2016.
 */

public class AppSettings {

    private final ZctLogger mLogger = new ZctLogger(AppSettings.class.getSimpleName(), BuildConfig.DEBUG);

    private Context mContext;
    private SharedPreferences mSettingsPreference;

    public AppSettings(SharedPreferences settingsPreference, Context context) {
        this.mSettingsPreference = settingsPreference;
        this.mContext = context;
        mLogger.d("Object created");
    }


}
