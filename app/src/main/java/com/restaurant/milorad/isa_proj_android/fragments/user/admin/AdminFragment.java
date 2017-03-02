package com.restaurant.milorad.isa_proj_android.fragments.user.admin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.network.model.AdminsBean;
import com.restaurant.milorad.isa_proj_android.network.model.UserProfileBean;
import com.zerocodeteam.network.ZctResponse;


public class AdminFragment extends Fragment {

    private static final ZctLogger mLogger = new ZctLogger(AdminFragment.class.getSimpleName(), BuildConfig.DEBUG);
    private static final String ADMINS_LIST_BUNDLE_KEY = "admin_list_data";

    private AdminFragment.OnListFragmentInteractionListener mListener;
    private AdminAdapter mAdminAdapter;
    private View mEmptyView;
    private AdminsBean mAdminsData;
    private ZctResponse<String> mDeleteResponse;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AdminFragment() {
    }

    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        return fragment;
    }

    public static AdminFragment newInstance(AdminsBean admins) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();

        args.putSerializable(ADMINS_LIST_BUNDLE_KEY, admins);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAdminsData = (AdminsBean) getArguments().getSerializable(ADMINS_LIST_BUNDLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_admins, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_admins);
        mEmptyView = view.findViewById(R.id.empty_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 8;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

            }
        });

        mAdminAdapter = new AdminAdapter(mAdminsData.getAdmins(), mListener);
        mAdminAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mLogger.d("onChanged called");
                if (mAdminAdapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        });
        recyclerView.setAdapter(mAdminAdapter);
        mAdminAdapter.notifyDataSetChanged();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AdminFragment.OnListFragmentInteractionListener) {
            mListener = (AdminFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(UserProfileBean item);
    }
}
