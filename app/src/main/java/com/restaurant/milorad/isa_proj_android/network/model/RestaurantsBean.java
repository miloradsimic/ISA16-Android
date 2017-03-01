package com.restaurant.milorad.isa_proj_android.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by milorad on 28.2.17..
 */

public class RestaurantsBean implements Serializable{

    @SerializedName("restaurants")
    @Expose
    private ArrayList<RestaurantItemBean> restaurants = new ArrayList<>();

    public ArrayList<RestaurantItemBean> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<RestaurantItemBean> restaurants) {
        this.restaurants = restaurants;
    }
}
