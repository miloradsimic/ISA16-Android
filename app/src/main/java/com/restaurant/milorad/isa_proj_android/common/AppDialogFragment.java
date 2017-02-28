package com.restaurant.milorad.isa_proj_android.common;

import android.app.DialogFragment;
import android.os.Bundle;

public abstract class AppDialogFragment extends DialogFragment {

    protected abstract void setupAll(Bundle savedInstanceState);

    protected abstract void setupViews();

    protected abstract void setupListeners();

    protected abstract void prepareListeners();

}