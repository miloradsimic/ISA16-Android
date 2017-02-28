package com.restaurant.milorad.isa_proj_android.network;

import com.google.gson.internal.LinkedTreeMap;
import com.restaurant.milorad.isa_proj_android.App;
import com.zerocodeteam.network.ZctRequest;
import com.zerocodeteam.network.ZctResponse;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Milorad on 25.2.2017.
 */
public class API {

    //API urls
    private static final String API_BASE_URL = "http://192.168.1.101:8080/";
    private static final String API_LOGIN = "auth/login";
    private static final String API_LOGOUT = "auth/logout";
    private static final String API_SIGN_UP = "authorize/sign-up";

    private static API sInstance;

    public static API getInstance() {
        if (sInstance == null) {
            sInstance = new API();
        }
        return sInstance;
    }

    /**
     * Performs login procedure for given loginData
     *
     * @param listener
     */
    public void loginUser(ZctResponse<LinkedTreeMap<String, String>> listener) {

        HashMap<String, String> params = new HashMap<>();
        params.put("username", App.getInstance().getUserEmail());
        params.put("password", App.getInstance().getUserPassword());
        JSONObject obj = new JSONObject(params);


        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_LOGIN)
                .responseClass(Object.class)
                .method(ZctRequest.Method.POST)
                .response(listener)
                .bodyContent(obj.toString())
                .build();
        App.getNetwork().sendRequest(request);
    }

    public void logoutUser(ZctResponse<Boolean> listener) {

        String token = App.getInstance().getUserToken();

        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_LOGOUT)
                .responseClass(Object.class)
                .method(ZctRequest.Method.GET)
                .response(listener)
                .cookie(token)
                .build();
        App.getNetwork().sendRequest(request);
    }
}
