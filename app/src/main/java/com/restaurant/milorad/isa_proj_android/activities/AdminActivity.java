package com.restaurant.milorad.isa_proj_android.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.restaurant.milorad.isa_proj_android.App;
import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.common.AppConstants;
import com.restaurant.milorad.isa_proj_android.common.AppUtils;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.fragments.user.admin.AdminFragment;
import com.restaurant.milorad.isa_proj_android.fragments.user.admin.EditableAdminFragment;
import com.restaurant.milorad.isa_proj_android.network.API;
import com.restaurant.milorad.isa_proj_android.network.model.AdminsBean;
import com.restaurant.milorad.isa_proj_android.network.model.UserProfileBean;
import com.zerocodeteam.network.ZctNetwork;
import com.zerocodeteam.network.ZctResponse;

import java.util.ArrayList;
import java.util.Map;

public class AdminActivity extends AppCompatActivity implements
        AdminFragment.OnListFragmentInteractionListener,
        EditableAdminFragment.OnFragmentInteractionListener {

    private static final ZctLogger mLogger = new ZctLogger(MainActivity.class.getSimpleName(), BuildConfig.DEBUG);

    private TabLayout mTabMenu;
    private TabLayout.OnTabSelectedListener mOnTabSelectedListener;
    private ProgressDialog mProgressDialog;
    private ZctResponse<Boolean> mLogoutResponse;
    private ZctResponse<String> mAdminsResponse;

//    private ZctResponse<String> mAdminsResponse;
//    private ZctResponse<String> mProfilePageResponse;
//    private ZctResponse<String> mFriendsResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_admin);
        setupViews();
        setupListeners();

        populatePageContent(AppConstants.FRAG_ADMINS);


        mTabMenu.addOnTabSelectedListener(mOnTabSelectedListener);
    }

    public void setupViews() {

        mTabMenu = (TabLayout) findViewById(R.id.main_menu);
        mTabMenu.addTab(mTabMenu.newTab().setText("Admins"));
        mTabMenu.addTab(mTabMenu.newTab().setText("Restaurants"));
        mTabMenu.addTab(mTabMenu.newTab().setText("Managers"));
    }

    public void setupListeners() {
        prepareListeners();
    }

    public void prepareListeners() {

        mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Integer index = tab.getPosition();
                mLogger.d("Tab index: " + index);
                populatePageContent(index);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Integer index = tab.getPosition();
                mLogger.d("Tab ReSelected " + index);
            }
        };

        mLogoutResponse = new ZctResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean responseObject, Map<String, String> responseHeaders, Object cookie) {
                AppUtils.hideProgress(mProgressDialog);
                App.getInstance().setUserToken(null);
                Toast.makeText(getApplicationContext(), "Logout successful! Goodbye!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
                mLogger.d("Logout failed: " + error.getClass().getSimpleName());
                AppUtils.hideProgress(mProgressDialog);
                Toast.makeText(getApplicationContext(), getString(R.string.error_logout), Toast.LENGTH_LONG).show();
            }
        };


//        mAdminsResponse = new ZctResponse<String>() {
//            @Override
//            public void onSuccess(String data, Map<String, String> responseHeaders, Object cookie) {
//                AppUtils.hideProgress(mProgressDialog);
//
//
//                ArrayList<RestaurantItemBean> restaurantList = ZctNetwork.getGson().fromJson(data, new TypeToken<ArrayList<RestaurantItemBean>>() {
//                }.getType());
//                RestaurantsBean restaurants = new RestaurantsBean();
//                restaurants.setRestaurants(restaurantList);
////                App.getInstance().setRestaurants(restaurantList);
//                RestaurantListFragment restaurantsFrag = RestaurantListFragment.newInstance(restaurants);
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, restaurantsFrag).commitAllowingStateLoss();
//
//                Toast.makeText(getApplicationContext(), "Got restaurant list! ", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
//                mLogger.d("Restaurants download failed: " + error.getClass().getSimpleName());
//                App.getInstance().logoutUser();
//                AppUtils.hideProgress(mProgressDialog);
//                Toast.makeText(getApplicationContext(), getString(R.string.error_on_server), Toast.LENGTH_LONG).show();
//            }
//        };


//        mFriendsResponse = new ZctResponse<String>() {
//            @Override
//            public void onSuccess(String data, Map<String, String> responseHeaders, Object cookie) {
//                AppUtils.hideProgress(mProgressDialog);
//                ArrayList<FriendItemBean> friendsList = ZctNetwork.getGson().fromJson(data, new TypeToken<ArrayList<FriendItemBean>>() {
//                }.getType());
//                FriendsBean friends = new FriendsBean();
//                friends.setFriends(friendsList);
//                FriendsListFragment friendsFrag = FriendsListFragment.newInstance(friends);
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, friendsFrag).commitAllowingStateLoss();
//
//                Toast.makeText(getApplicationContext(), "Got friend list! ", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
//                mLogger.d("Friends download failed: " + error.getClass().getSimpleName());
//                App.getInstance().logoutUser();
//                AppUtils.hideProgress(mProgressDialog);
//                Toast.makeText(getApplicationContext(), getString(R.string.error_on_server), Toast.LENGTH_LONG).show();
//            }
//        };

//        mProfilePageResponse = new ZctResponse<String>() {
//            @Override
//            public void onSuccess(String responseObject, Map<String, String> responseHeaders, Object cookie) {
//                AppUtils.hideProgress(mProgressDialog);
//                UserProfileBean profile = ZctNetwork.getGson().fromJson(responseObject, UserProfileBean.class);
//
//                mLogger.e("mPageContentResponse, response obj: " + responseObject);
//                ProfileFragment profilePage = ProfileFragment.newInstance(profile);
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profilePage).commitAllowingStateLoss();
//            }
//
//            @Override
//            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
//                AppUtils.hideProgress(mProgressDialog);
//                Toast.makeText(getApplicationContext(), "Profile data fetch failed", Toast.LENGTH_LONG).show();
//            }
//        };

        mAdminsResponse = new ZctResponse<String>() {
            @Override
            public void onSuccess(String data, Map<String, String> responseHeaders, Object cookie) {
                AppUtils.hideProgress(mProgressDialog);


                ArrayList<UserProfileBean> adminsList = ZctNetwork.getGson().fromJson(data, new TypeToken<ArrayList<UserProfileBean>>() {
                }.getType());
                AdminsBean admins = new AdminsBean();
                admins.setAdmins(adminsList);
                AdminFragment adminsFrag = AdminFragment.newInstance(admins);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, adminsFrag).commitAllowingStateLoss();

                Toast.makeText(getApplicationContext(), "Got admins list! ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
                mLogger.d("Admins download failed: " + error.getClass().getSimpleName());
                App.getInstance().logoutUser();
                AppUtils.hideProgress(mProgressDialog);
                Toast.makeText(getApplicationContext(), getString(R.string.error_on_server), Toast.LENGTH_LONG).show();
            }
        };


    }

    private void populatePageContent(int position) {

        switch (position) {
            case AppConstants.FRAG_ADMINS: {
                API.getInstance().getAdmins(mAdminsResponse);
                break;
            }
            case AppConstants.FRAG_RESTAURANTS: {
                //API.getInstance().getRestaurants(mRestaurantsResponse);
                break;
            }
            case AppConstants.FRAG_FRIENDS: {
//                API.getInstance().getFriends(mFriendsResponse);
                break;
            }
        }


    }

    public void toolbarLogOut(View v) {
        API.getInstance().logoutUser(mLogoutResponse);
        finish();
    }

    @Override
    public void onListFragmentInteraction(UserProfileBean item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
