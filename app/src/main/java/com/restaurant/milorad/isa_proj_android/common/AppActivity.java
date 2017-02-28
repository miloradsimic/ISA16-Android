package com.restaurant.milorad.isa_proj_android.common;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rade on 9/11/2015.
 */
public abstract class AppActivity extends AppCompatActivity {

    protected abstract void setupAll(Bundle savedInstanceState);

    protected abstract void setupViews();

    protected abstract void setupListeners();

    protected abstract void prepareListeners();

}
