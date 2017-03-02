package com.restaurant.milorad.isa_proj_android.fragments.user.guest;

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
import com.restaurant.milorad.isa_proj_android.network.model.FriendsBean;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FriendsListFragment extends Fragment {

    private static final ZctLogger mLogger = new ZctLogger(FriendsListFragment.class.getSimpleName(), BuildConfig.DEBUG);
    private static final String FRIENDS_LIST_BUNDLE_KEY = "friends_list_data";

    private OnListFragmentInteractionListener mListener;
    private FriendListAdapter mFriendListAdapter;
    private View mEmptyView;
    private FriendsBean mFriendsData;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendsListFragment() {
    }

    public static FriendsListFragment newInstance() {
        FriendsListFragment fragment = new FriendsListFragment();
        return fragment;
    }

    public static FriendsListFragment newInstance(FriendsBean friends) {
        FriendsListFragment fragment = new FriendsListFragment();
        Bundle args = new Bundle();

        args.putSerializable(FRIENDS_LIST_BUNDLE_KEY, friends);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFriendsData = (FriendsBean) getArguments().getSerializable(FRIENDS_LIST_BUNDLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_friends, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_friends);
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
//
//        ArrayList<FriendItemBean> restaurants = new ArrayList<>();
//        FriendItemBean r = new FriendItemBean();
//        r.setId("1");
//        r.setName("name");
////        r.setVisits("desc");
//        restaurants.add(r);
//        restaurants.add(r);
//        restaurants.add(r);
//        restaurants.add(r);


//        mFriendListAdapter = new FriendListAdapter(restaurants, mListener);
        mFriendListAdapter = new FriendListAdapter(mFriendsData.getFriends(), mListener);
        mFriendListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mLogger.d("onChanged called");
                if (mFriendListAdapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                }
            }
        });
        recyclerView.setAdapter(mFriendListAdapter);
        mFriendListAdapter.notifyDataSetChanged();
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
        void onListFragmentInteraction(FriendsListFragment item);
    }
}
