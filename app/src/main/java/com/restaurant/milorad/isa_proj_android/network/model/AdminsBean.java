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
    private ArrayList<AdminBean> admins = new ArrayList<>();


    public ArrayList<AdminBean> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<AdminBean> admins) {
        this.admins = admins;
    }
}