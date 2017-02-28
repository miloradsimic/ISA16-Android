package com.restaurant.milorad.isa_proj_android.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.internal.LinkedTreeMap;
import com.restaurant.milorad.isa_proj_android.App;
import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.common.AppUtils;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.network.API;
import com.zerocodeteam.network.ZctNetwork;
import com.zerocodeteam.network.ZctResponse;

import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static ZctLogger mLogger = new ZctLogger(LoginActivity.class.getSimpleName(), BuildConfig.DEBUG);

    // UI references.
    private EditText mEmail;
    private EditText mPassword;
    private Button mSignInButton;

    private View.OnClickListener mOnClickListener;
    private EditText.OnEditorActionListener mOnEditorActionListener;
    private ZctResponse<LinkedTreeMap<String, String>> mLoginResponse;
    private ProgressDialog mProgressDialog;
    private ZctResponse<String> mGetShift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
        setupListeners();

        String email = App.getInstance().getUserEmail();
        String pass = App.getInstance().getUserPassword();
        if (email != null) {
            mEmail.setText(email);
            mPassword.setText(pass);

        }
    }
    public void setupViews() {
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mSignInButton = (Button) findViewById(R.id.action_login);
    }

    public void setupListeners() {
        prepareListeners();
        mPassword.setOnEditorActionListener(mOnEditorActionListener);
        mSignInButton.setOnClickListener(mOnClickListener);
    }

    public void prepareListeners() {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.action_login:
                        attemptLogin();
                        break;
                    default:
                        break;
                }
            }
        };

        mOnEditorActionListener = new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    handled = true;
                }
                return handled;
            }
        };
        mLoginResponse = new ZctResponse<LinkedTreeMap<String, String>>() {
            @Override
            public void onSuccess(LinkedTreeMap<String, String> data, Map<String, String> responseHeaders, Object cookie) {
                AppUtils.hideProgress(mProgressDialog);
                App.getInstance().setRole(data.get("role"));

                Toast.makeText(getApplicationContext(), "Login successful! Welcome " + data.get("name"), Toast.LENGTH_SHORT).show();

                switch (data.get("role")){
                    case "admin": {
                        /* TODO: To be implemented*/
                        break;
                    }
                    case "bartender": {
                        /* TODO: To be implemented*/
                        break;
                    }
                    case "cook": {
                        /* TODO: To be implemented*/
                        break;
                    }
                    case "guest": {
                        AppUtils.go2MainActivity(LoginActivity.this, data);
                        break;
                    }
                    case "manager": {
                        /* TODO: To be implemented*/
                        break;
                    }
                    case "supplier": {
                        /* TODO: To be implemented*/
                        break;
                    }
                    case "waiter": {
                        /* TODO: To be implemented*/
                        break;
                    }
                }
            }

            @Override
            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
                mLogger.d("Menu items download failed: " + error.getClass().getSimpleName());
                App.getInstance().logoutUser();
                AppUtils.hideProgress(mProgressDialog);
                Toast.makeText(getApplicationContext(), getString(R.string.error_login), Toast.LENGTH_LONG).show();
            }
        };
    }
    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        AppUtils.hideKeyboard(this);

        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            this.mEmail.setError(getString(R.string.error_field_required));
            this.mEmail.requestFocus();
            return;
        } else if (!AppUtils.isEmailValid(email)) {
            this.mEmail.setError(getString(R.string.error_invalid_email));
            this.mEmail.requestFocus();
            return;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            this.mPassword.setError(getString(R.string.error_field_required));
            this.mPassword.requestFocus();
            return;
        } else if (!AppUtils.isPasswordValid(password)) {
            this.mPassword.setError(getString(R.string.error_invalid_password));
            this.mPassword.requestFocus();
            return;
        }

        // Perform the user login attempt.
        mProgressDialog = AppUtils.showProgress(LoginActivity.this);

        String token = AppUtils.genBase64(email + ":" + password);
        mLogger.d("Attempt login, generated token: " + token);

        /*store db id for further use*/
        long dbId = App.getDatabase().createUser(email);
        App.getInstance().setUserDbId(dbId);
        App.getInstance().setUserEmail(email);
        App.getInstance().setUserPassword(password);

        API.getInstance().loginUser(mLoginResponse);
    }
}

