package com.restaurant.milorad.isa_proj_android;

import android.app.Application;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.webkit.WebViewClient;

import com.restaurant.milorad.isa_proj_android.common.AppConstants;
import com.restaurant.milorad.isa_proj_android.common.AppSettings;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.common.ZctPersistData;
import com.restaurant.milorad.isa_proj_android.db.DatabaseController;
import com.restaurant.milorad.isa_proj_android.network.model.RestaurantItemBean;
import com.zerocodeteam.network.ZctNetwork;

import java.util.ArrayList;

/**
 * Created by milorad on 25.2.2017
 */
public class App extends Application {
    private static ZctLogger mLogger = new ZctLogger(App.class.getSimpleName(), BuildConfig.DEBUG);
    private static App sInstance;


    public static Typeface MYRIAD_PRO_BOLD_TYPEFACE;
    public static Typeface MYRIAD_PRO_REGULAR_TYPEFACE;
    public static Typeface CENTURY_GOTHIC_TYPEFACE;


    // Used for app settings
    private static AppSettings sAppSettings;

    // Used to store notifications
    private static DatabaseController sDatabase;

    // Used to call API
    private static ZctNetwork sNetwork;

    private ZctPersistData mSharedPref;

    private WebViewClient sWebViewClient;

    public static App getInstance() {
        return sInstance;
    }

    public static DatabaseController getDatabase() {
        return sDatabase;
    }

    public static AppSettings getAppSettings() {
        return sAppSettings;
    }

    public static ZctNetwork getNetwork() {
        return sNetwork;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        sDatabase = new DatabaseController(getApplicationContext());
        sAppSettings = new AppSettings(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()), getApplicationContext());

        // Init network module in order to extend default timeout time
        sNetwork = new ZctNetwork.Builder(this).requestTimeout(5000).enableConsoleDebugging().build();

        mSharedPref = new ZctPersistData(getApplicationContext(), false);
        mLogger.d("Shared preferences created");

        CENTURY_GOTHIC_TYPEFACE = Typeface.createFromAsset(getAssets(), "fonts/century-gothic.ttf");
        MYRIAD_PRO_BOLD_TYPEFACE = Typeface.createFromAsset(getAssets(), "fonts/myriad-pro-bold.otf");
        MYRIAD_PRO_REGULAR_TYPEFACE = Typeface.createFromAsset(getAssets(), "fonts/myriad-pro-regular.otf");


        mLogger.d("Font loaded");
    }

    /**
     * If user is logged in return USER TOKEN, else returns NULL
     *
     * @return USER TOKEN or NULL
     */
    public String getUserToken() {
        String token = (String) mSharedPref.readData(ZctPersistData.Type.STRING, AppConstants.PREF_TOKEN_KEY);
        mLogger.d("Read user token: " + token);
        return token;
    }

    public void setUserToken(String token) {
        mLogger.d("Set user token: " + token);
        mSharedPref.storeData(AppConstants.PREF_TOKEN_KEY, token);
    }

    public void logoutUser() {
        mLogger.d("Reset user token");
        mSharedPref.removeData(AppConstants.PREF_TOKEN_KEY);
        mSharedPref.removeData(AppConstants.PREF_PASSWORD_KEY);
        mSharedPref.removeData(AppConstants.PREF_USER_DB_ID);
    }

    public String getUserEmail() {
        String userEmail = (String) mSharedPref.readData(ZctPersistData.Type.STRING, AppConstants.PREF_EMAIL_KEY);
        mLogger.d("Read user email: " + userEmail);
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        mLogger.d("Set user email: " + userEmail);
        mSharedPref.storeData(AppConstants.PREF_EMAIL_KEY, userEmail);
    }

    public String getRole() {
        String role = (String) mSharedPref.readData(ZctPersistData.Type.STRING, AppConstants.PREF_ROLE_KEY);
        mLogger.d("Read user role: " + role);
        return role;
    }

    public void setRole(String role) {
        mLogger.d("Set user role: " + role);
        mSharedPref.storeData(AppConstants.PREF_ROLE_KEY, role);
    }

    public String getUserPassword() {
        String userPassword = (String) mSharedPref.readData(ZctPersistData.Type.STRING, AppConstants.PREF_PASSWORD_KEY);
        mLogger.d("Read user password: " + userPassword);
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        mLogger.d("Set user password: " + userPassword);
        mSharedPref.storeData(AppConstants.PREF_PASSWORD_KEY, userPassword);
    }

    public void resetUserPassword() {
        mLogger.d("Reset user password");
        mSharedPref.removeData(AppConstants.PREF_PASSWORD_KEY);
    }

    public WebViewClient getWebViewClient() {
        return sWebViewClient;
    }

    public void setWebViewClient(WebViewClient sWebViewClient) {
        this.sWebViewClient = sWebViewClient;
    }

    /**
     * USER DB ID
     */
    public long getUserDbId() {
        long userDbId = AppConstants.INVALID_USER_ID;
        Object ret = mSharedPref.readData(ZctPersistData.Type.INTEGER, AppConstants.PREF_USER_DB_ID);
        if (ret != null) {
            userDbId = (long) (int) ret;
        }

        mLogger.d("Read user DB ID: " + userDbId);
        return userDbId;
    }

    public void setUserDbId(long userDbId) {
        mLogger.d("Store user DB id: " + userDbId);
        mSharedPref.storeData(AppConstants.PREF_USER_DB_ID, Integer.valueOf((int) userDbId));
    }

    public void resetUserDbId() {
        mLogger.d("Reset user DB id.");
        mSharedPref.removeData(AppConstants.PREF_USER_DB_ID);
    }

    /**
     * Keep data in session.
     */
    private ArrayList<RestaurantItemBean> restaurants = new ArrayList<>();

    public ArrayList<RestaurantItemBean> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<RestaurantItemBean> restaurants) {
        this.restaurants = restaurants;
    }
}