package com.restaurant.milorad.isa_proj_android.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by milorad on 2.3.17..
 */

public class AdminsBean implements Serializable {


    @SerializedName("admins")
    @Expose
    private ArrayList<UserProfileBean> admins = new ArrayList<>();


    public ArrayList<UserProfileBean> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<UserProfileBean> admins) {
        this.admins = admins;
    }
}