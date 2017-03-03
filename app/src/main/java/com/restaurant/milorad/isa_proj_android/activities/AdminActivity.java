package com.restaurant.milorad.isa_proj_android.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.restaurant.milorad.isa_proj_android.network.model.AdminBean;
import com.restaurant.milorad.isa_proj_android.network.model.AdminsBean;
import com.zerocodeteam.network.ZctNetwork;
import com.zerocodeteam.network.ZctResponse;

import java.util.ArrayList;
import java.util.List;
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
                populatePageContent(index);
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



        mAdminsResponse = new ZctResponse<String>() {
            @Override
            public void onSuccess(String data, Map<String, String> responseHeaders, Object cookie) {
                AppUtils.hideProgress(mProgressDialog);


                ArrayList<AdminBean> adminsList = ZctNetwork.getGson().fromJson(data, new TypeToken<ArrayList<AdminBean>>() {
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
    public Fragment getVisibleFragment(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }


    public void populatePageContent(int position) {

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
    public void onListFragmentInteraction(AdminBean item) {
        Log.e("onListFragmentInteraion", "-----------------------------------------");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.e("onListFragmentInteraion", "+++++++++++++++++++++++++++++++++++++++");

    }
}
