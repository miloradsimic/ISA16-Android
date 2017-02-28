package com.restaurant.milorad.isa_proj_android.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.restaurant.milorad.isa_proj_android.App;
import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.common.AppUtils;
import com.restaurant.milorad.isa_proj_android.common.IIrregularPage;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.fragments.user.RestaurantListFragment;
import com.restaurant.milorad.isa_proj_android.network.API;
import com.restaurant.milorad.isa_proj_android.network.model.RestaurantItemBean;
import com.zerocodeteam.network.ZctNetwork;
import com.zerocodeteam.network.ZctResponse;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements IIrregularPage, RestaurantListFragment.OnListFragmentInteractionListener{

    private static final ZctLogger mLogger = new ZctLogger(MainActivity.class.getSimpleName(), BuildConfig.DEBUG);

    public RestaurantListFragment mRestaurantListFragment;

    private TabLayout mTabMenu;
    private TabLayout.OnTabSelectedListener mOnTabSelectedListener;
    private ProgressDialog mProgressDialog;
    private ZctResponse<Boolean> mLogoutResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        setupViews();
        setupListeners();

        populatePageContent();

//        Intent mBootIntent = getIntent();
//        String menuJson = mBootIntent.getStringExtra(AppConstants.EXTRAS_RESTAURANTS);


        /*TODO: kreiraj menu*/

        mTabMenu.addOnTabSelectedListener(mOnTabSelectedListener);
    }

    public void setupViews() {
        mTabMenu = (TabLayout) findViewById(R.id.main_menu);
    }

    public void setupListeners() {
        prepareListeners();
    }

    public void prepareListeners() {

        mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };

        mLogoutResponse = new ZctResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean responseObject, Map<String, String> responseHeaders, Object cookie) {
                AppUtils.hideProgress(mProgressDialog);
                App.getInstance().setUserToken(null);
                Toast.makeText(getApplicationContext(), "Logout successful! Goodbye! Response: " + responseObject, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
                mLogger.d("Logout failed: " + error.getClass().getSimpleName());
                AppUtils.hideProgress(mProgressDialog);
                Toast.makeText(getApplicationContext(), getString(R.string.error_login), Toast.LENGTH_LONG).show();
            }
        };
    }

    private void populatePageContent() {

        mRestaurantListFragment = RestaurantListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mRestaurantListFragment).commitAllowingStateLoss();

    }

    public void toolbarLogOut(View v) {
        API.getInstance().logoutUser(mLogoutResponse);
    }

    @Override
    public void onPageLoadingFinished() {
        AppUtils.hideProgress(mProgressDialog);
    }

    @Override
    public void onSubmenuItemSelected() {
        mProgressDialog = AppUtils.showProgress(MainActivity.this);
    }


    @Override
    public void onListFragmentInteraction(RestaurantItemBean item) {

    }
}
