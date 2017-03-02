package com.restaurant.milorad.isa_proj_android.network;

import com.restaurant.milorad.isa_proj_android.App;
import com.restaurant.milorad.isa_proj_android.network.model.AdminBean;
import com.zerocodeteam.network.ZctRequest;
import com.zerocodeteam.network.ZctResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Milorad on 25.2.2017.
 */
public class API {

    //API urls
    private static final String API_BASE_URL = "http://192.168.1.101:8080/";
    private static final String API_LOGIN = "auth/login";
    private static final String API_LOGOUT = "auth/logout";
    private static final String API_GUEST_RESTAURANTS = "guest/restaurants";
    private static final String API_GUEST_FRIENDS = "guest/friends";
    private static final String API_GUEST_REGISTER = "guest/register";
    private static final String API_GUEST_PROFILE = "guest/profile";
    private static final String API_ADMIN_ADMINS = "admin/admins";
    private static final String API_ADMIN_DELETE = "admin/delete";
    private static final String API_ADMIN_CREATE = "admin/create";

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
    public void loginUser(ZctResponse<String> listener) {

//        HashMap<String, String> params = new HashMap<>();
//        params.put("username", App.getInstance().getUserEmail());
//        params.put("password", App.getInstance().getUserPassword());
//        JSONObject obj = new JSONObject(params);

        String token = App.getInstance().getUserToken();
        Map<String, String> headers = new HashMap<>();

        if (token != null) {
            headers.put("Authorization", token);
        } else {
            headers.put("Authorization", "token_not_provided"/*token*/);
        }


        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_LOGIN)
                .responseClass(String.class)
                .method(ZctRequest.Method.POST)
                .response(listener)
                .headers(headers)
//                .bodyContent(obj.toString())
                .build();
        App.getNetwork().sendRequest(request);
    }

    public void logoutUser(ZctResponse<Boolean> listener) {


        String token = App.getInstance().getUserToken();
        Map<String, String> headers = new HashMap<>();

        if (token != null) {
            headers.put("Authorization", token);
        } else {
            headers.put("Authorization", "token_not_provided"/*token*/);
        }

        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_LOGOUT)
                .responseClass(Object.class)
                .method(ZctRequest.Method.GET)
                .response(listener)
                .headers(headers)
                .build();
        App.getNetwork().sendRequest(request);
    }
    public void getRestaurants(ZctResponse<String> listener) {


        String token = App.getInstance().getUserToken();
        Map<String, String> headers = new HashMap<>();

        if (token != null) {
            headers.put("Authorization", token);
        } else {
            headers.put("Authorization", "token_not_provided"/*token*/);
        }

        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_GUEST_RESTAURANTS)
                .method(ZctRequest.Method.GET)
                .response(listener)
                .headers(headers)
                .build();
        App.getNetwork().sendRequest(request);
    }

    public void getProfile(ZctResponse<String> listener) {

        String token = App.getInstance().getUserToken();
        Map<String, String> headers = new HashMap<>();

        if (token != null) {
            headers.put("Authorization", token);
        } else {
            headers.put("Authorization", "token_not_provided"/*token*/);
        }

        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_GUEST_PROFILE)
                .method(ZctRequest.Method.GET)
                .response(listener)
                .headers(headers)
                .build();
        App.getNetwork().sendRequest(request);
    }


    public void getFriends(ZctResponse<String> listener) {


        String token = App.getInstance().getUserToken();
        Map<String, String> headers = new HashMap<>();

        if (token != null) {
            headers.put("Authorization", token);
        } else {
            headers.put("Authorization", "token_not_provided"/*token*/);
        }

        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_GUEST_FRIENDS)
                .method(ZctRequest.Method.GET)
                .response(listener)
                .headers(headers)
                .build();
        App.getNetwork().sendRequest(request);
    }

    public void registerGuest(ZctResponse<String> listener, String name) {

        HashMap<String, String> params = new HashMap<>();
        params.put("email", App.getInstance().getUserEmail());
        params.put("password", App.getInstance().getUserPassword());
        params.put("name", name);
        JSONObject obj = new JSONObject(params);


        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_GUEST_REGISTER)
                .method(ZctRequest.Method.POST)
                .bodyContent(obj.toString())
                .response(listener)
                .build();
        App.getNetwork().sendRequest(request);
    }

    public void getAdmins(ZctResponse<String> listener) {

        String token = App.getInstance().getUserToken();
        Map<String, String> headers = new HashMap<>();

        if (token != null) {
            headers.put("Authorization", token);
        } else {
            headers.put("Authorization", "token_not_provided"/*token*/);
        }

        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_ADMIN_ADMINS)
                .method(ZctRequest.Method.GET)
                .response(listener)
                .headers(headers)
                .build();
        App.getNetwork().sendRequest(request);
    }

    public void deleteAdmin(ZctResponse<String> listener, String id) {
        String token = App.getInstance().getUserToken();
        Map<String, String> headers = new HashMap<>();
        if (token != null) {
            headers.put("Authorization", token);
        } else {
            headers.put("Authorization", "token_not_provided"/*token*/);
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        JSONObject obj = new JSONObject(params);

        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_ADMIN_DELETE)
                .method(ZctRequest.Method.POST)
                .bodyContent(obj.toString())
                .response(listener)
                .headers(headers)
                .build();
        App.getNetwork().sendRequest(request);
    }

    public void addNewAdmin(ZctResponse<String> listener, AdminBean admin) {
        String token = App.getInstance().getUserToken();
        Map<String, String> headers = new HashMap<>();
        if (token != null) {
            headers.put("Authorization", token);
        } else {
            headers.put("Authorization", "token_not_provided"/*token*/);
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("name", admin.getName());
        params.put("email", admin.getEmail());
        params.put("password", admin.getPassword());
        JSONObject obj = new JSONObject(params);

        ZctRequest request = new ZctRequest.Builder(API_BASE_URL + API_ADMIN_CREATE)
                .method(ZctRequest.Method.POST)
                .bodyContent(obj.toString())
                .response(listener)
                .headers(headers)
                .build();
        App.getNetwork().sendRequest(request);
    }



}