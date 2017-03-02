package com.restaurant.milorad.isa_proj_android.fragments.user.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.restaurant.milorad.isa_proj_android.App;
import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.activities.LoginActivity;
import com.restaurant.milorad.isa_proj_android.common.AppUtils;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.network.API;
import com.restaurant.milorad.isa_proj_android.network.model.AdminBean;
import com.zerocodeteam.network.ZctNetwork;
import com.zerocodeteam.network.ZctResponse;

import java.util.Map;


public class EditableAdminFragment extends Fragment {
    private static ZctLogger mLogger = new ZctLogger(LoginActivity.class.getSimpleName(), BuildConfig.DEBUG);

    private OnFragmentInteractionListener mListener;
    private AdminBean mData;
    private ProgressDialog mProgressDialog;

    private EditText mEmail;
    private EditText mName;
    private EditText mPassword;

    private Button mCreateButton;


    public EditableAdminFragment() {
        // Required empty public constructor
    }

    public static EditableAdminFragment newInstance() {
        EditableAdminFragment fragment = new EditableAdminFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_editable_admin, container, false);

        mEmail = (EditText) view.findViewById(R.id.email);
        mName = (EditText) view.findViewById(R.id.name);
        mPassword = (EditText) view.findViewById(R.id.password);

        mCreateButton = (Button) view.findViewById(R.id.action_create);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void attemptLogin() {
        AppUtils.hideKeyboard(getActivity());

        // Reset errors.
        mEmail.setError(null);
        mPassword.setError(null);
        mName.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String name = mName.getText().toString();

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
        mProgressDialog = AppUtils.showProgress(getContext());

        mData = new AdminBean();
        mData.setName(name);
        mData.setEmail(email);
        mData.setPassword(password);

        API.getInstance().addNewAdmin(new ZctResponse<String>() {

            @Override
            public void onSuccess(String data, Map<String, String> responseHeaders, Object cookie) {
                AppUtils.hideProgress(mProgressDialog);

                if (data.equals("true")) {
                    Toast.makeText(getContext(), "Admin created!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
            }


            @Override
            public void onError(VolleyError error, ZctNetwork.ErrorType
                    type, Map<String, String> responseHeaders, Object cookie) {
                mLogger.d("Admin NOT created, error" + error.getClass().getSimpleName());
                App.getInstance().logoutUser();
                AppUtils.hideProgress(mProgressDialog);
                Toast.makeText(getContext(), getString(R.string.error_create_admin), Toast.LENGTH_LONG).show();
            }
        }, mData);


    }
}
