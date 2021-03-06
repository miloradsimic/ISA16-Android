package com.restaurant.milorad.isa_proj_android.fragments.user.guest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.network.model.RestaurantItemBean;
import com.restaurant.milorad.isa_proj_android.network.model.RestaurantsBean;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RestaurantListFragment extends Fragment implements RestaurantListAdapter.MRAItemClickedListener{

    private static final ZctLogger mLogger = new ZctLogger(RestaurantListFragment.class.getSimpleName(), BuildConfig.DEBUG);
    private static final String RESTAURANTS_LIST_BUNDLE_KEY = "restaurant_list_data";

    private OnListFragmentInteractionListener mListener;
    private RestaurantListAdapter.MRAItemClickedListener mraListener;
    private RestaurantListAdapter mRestaurantListAdapter;
    private View mEmptyView;
    private RestaurantsBean mRestaurantsData;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantListFragment() {
    }

    public static RestaurantListFragment newInstance() {
        RestaurantListFragment fragment = new RestaurantListFragment();
        return fragment;
    }

    @SuppressWarnings("unused")
    public static RestaurantListFragment newInstance(RestaurantsBean restaurants) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();

        args.putSerializable(RESTAURANTS_LIST_BUNDLE_KEY, restaurants);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
                mRestaurantsData = (RestaurantsBean) getArguments().getSerializable(RESTAURANTS_LIST_BUNDLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_restaurants, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_restaurants);
        mEmptyView = view.findViewById(R.id.empty_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        mraListener = new RestaurantListAdapter.MRAItemClickedListener() {
            @Override
            public void onItemClicked(RestaurantItemBean item) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.fragment_container, ReservationTimeFragment.newInstance(item), "reservationTime");
                ft.commit();
            }
        };


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


        mRestaurantListAdapter = new RestaurantListAdapter(mRestaurantsData.getRestaurants(), mListener, mraListener);
        mRestaurantListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mLogger.d("onChanged called");
                if (mRestaurantListAdapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        });
        recyclerView.setAdapter(mRestaurantListAdapter);
        mRestaurantListAdapter.notifyDataSetChanged();
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    @Override
    public void onItemClicked(RestaurantItemBean item) {

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
        void onListFragmentInteraction(RestaurantItemBean item);
    }
}
