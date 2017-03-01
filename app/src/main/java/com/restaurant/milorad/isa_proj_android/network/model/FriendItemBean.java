package com.restaurant.milorad.isa_proj_android.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by milorad on 1.3.17..
 */

public class FriendItemBean implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;
//
//    @SerializedName("visits")
//    @Expose
//    private String visits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public String getVisits() {
//        return visits;
//    }
//
//    public void setVisits(String visits) {
//        this.visits = visits;
//    }
}
