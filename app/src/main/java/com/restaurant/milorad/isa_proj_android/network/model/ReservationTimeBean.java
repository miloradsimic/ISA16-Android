package com.restaurant.milorad.isa_proj_android.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by milorad on 3.3.17..
 */

public class ReservationTimeBean implements Serializable {

    @SerializedName("restaurant_id")
    @Expose
    private long restaurantID;

    @SerializedName("reservation_begin")
    @Expose
    private Date reservationBegin;

    @SerializedName("duration")
    @Expose
    private int duration;

    public long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(long restaurantID) {
        this.restaurantID = restaurantID;
    }

    public Date getReservationBegin() {
        return reservationBegin;
    }

    public void setReservationBegin(Date reservationBegin) {
        this.reservationBegin = reservationBegin;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}