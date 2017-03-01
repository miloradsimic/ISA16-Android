package com.restaurant.milorad.isa_proj_android.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by milorad on 1.3.17..
 */

public class FriendsBean implements Serializable {

    @SerializedName("friends")
    @Expose
    private ArrayList<FriendItemBean> friends = new ArrayList<>();

    public ArrayList<FriendItemBean> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<FriendItemBean> friends) {
        this.friends = friends;
    }
}