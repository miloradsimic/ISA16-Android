package com.restaurant.milorad.isa_proj_android.network.model;

import java.util.Collection;

/**
 * Created by milorad on 28.2.17..
 */

public class RestaurantsBean {

    private Collection<RestaurantsBean> restaurants;

    public Collection<RestaurantsBean> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Collection<RestaurantsBean> restaurants) {
        this.restaurants = restaurants;
    }
}
