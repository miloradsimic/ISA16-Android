package com.restaurant.milorad.isa_proj_android.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.restaurant.milorad.isa_proj_android.App;
import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.common.AppUtils;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.network.API;
import com.restaurant.milorad.isa_proj_android.network.model.LoginBean;
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
    private EditText mName;
    private EditText mPassword;
    private Button mSignInButton;
    private Button mRegisterButton;
    private TextView mRegisterTV;
    private TextView mLoginTV;

    public View.OnClickListener mOnClickListener;
    private EditText.OnEditorActionListener mOnEditorActionListener;
    private ZctResponse<String> mLoginResponse;
    private ProgressDialog mProgressDialog;
    private ZctResponse<String> mGetShift;
    private ZctResponse<String> mRegisterResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login);
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
        mName = (EditText) findViewById(R.id.name);
        mSignInButton = (Button) findViewById(R.id.action_login);
        mRegisterButton = (Button) findViewById(R.id.action_register);
        mRegisterTV = (TextView) findViewById(R.id.tv_register);
        mLoginTV = (TextView) findViewById(R.id.tv_login);

        showLogin();
    }

    public void setupListeners() {
        prepareListeners();
        mPassword.setOnEditorActionListener(mOnEditorActionListener);
        mSignInButton.setOnClickListener(mOnClickListener);
        mRegisterButton.setOnClickListener(mOnClickListener);
        mRegisterTV.setOnClickListener(mOnClickListener);
        mLoginTV.setOnClickListener(mOnClickListener);
    }

    public void prepareListeners() {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.action_login:
                        attemptLogin();
                        break;
                    case R.id.action_register: {
                        attemptRegistration();
                        break;
                    }
                    case R.id.tv_register: {
                        showRegister();
                        break;
                    }
                    case R.id.tv_login: {
                        showLogin();
                        break;
                    }
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
        mLoginResponse = new ZctResponse<String>() {
            @Override
            public void onSuccess(String data, Map<String, String> responseHeaders, Object cookie) {
                AppUtils.hideProgress(mProgressDialog);
                LoginBean loginResponse = null;

                loginResponse = ZctNetwork.getGson().fromJson(data, LoginBean.class);


                if(loginResponse==null) {
                    Toast.makeText(getApplicationContext(), "User don't exist!", Toast.LENGTH_SHORT).show();
                    return;
                }

                App.getInstance().setRole(loginResponse.getRole());

                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();

                Log.e("Role from app; ", App.getInstance().getRole());
                switch (loginResponse.getRole()) {
                    case "admin": {}
                    case "superuser":{
                        AppUtils.go2AdminActivity(LoginActivity.this);
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
                        AppUtils.go2MainActivity(LoginActivity.this);
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
                mLogger.d("Login failed: " + error.getClass().getSimpleName());
                App.getInstance().logoutUser();
                AppUtils.hideProgress(mProgressDialog);
                Toast.makeText(getApplicationContext(), getString(R.string.error_login), Toast.LENGTH_LONG).show();
            }
        };

        mRegisterResponse = new ZctResponse<String>() {
            @Override
            public void onSuccess(String data, Map<String, String> responseHeaders, Object cookie) {
                AppUtils.hideProgress(mProgressDialog);

                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                showLogin();

            }

            @Override
            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
                mLogger.d("Registration failed: " + error.getClass().getSimpleName());
                App.getInstance().logoutUser();
                AppUtils.hideProgress(mProgressDialog);
                Toast.makeText(getApplicationContext(), getString(R.string.error_registration), Toast.LENGTH_LONG).show();
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

        App.getInstance().setUserToken(makeToken(email, password));

        /*store db id for further use*/
        long dbId = App.getDatabase().createUser(email);
        App.getInstance().setUserDbId(dbId);
        App.getInstance().setUserEmail(email);
        App.getInstance().setUserPassword(password);

        API.getInstance().loginUser(mLoginResponse);
    }

    private String makeToken(String email, String password) {

        return AppUtils.genBase64(email + ":" + password);
    }

    public void showLogin() {
        mLoginTV.setVisibility(View.GONE);
        mSignInButton.setVisibility(View.VISIBLE);
        mRegisterButton.setVisibility(View.GONE);
        mRegisterTV.setVisibility(View.VISIBLE);
        mName.setVisibility(View.GONE);

    }

    private void showRegister() {
        mLoginTV.setVisibility(View.VISIBLE);
        mSignInButton.setVisibility(View.GONE);
        mRegisterButton.setVisibility(View.VISIBLE);
        mRegisterTV.setVisibility(View.GONE);
        mName.setVisibility(View.VISIBLE);
    }


    private void attemptRegistration() {
        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);
        mName.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String name = mName.getText().toString();
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

        // Check for a valid name.
        if (TextUtils.isEmpty(name)) {
            this.mName.setError(getString(R.string.error_field_required));
            this.mName.requestFocus();
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

        /*store db id for further use*/
        long dbId = App.getDatabase().createUser(email);
        App.getInstance().setUserDbId(dbId);
        App.getInstance().setUserEmail(email);
        App.getInstance().setUserPassword(password);

        API.getInstance().registerGuest(mRegisterResponse, name);
    }


}

