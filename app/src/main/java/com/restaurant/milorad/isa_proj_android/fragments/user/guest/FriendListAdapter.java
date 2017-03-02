package com.restaurant.milorad.isa_proj_android.fragments.user.guest;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.network.model.FriendItemBean;

import java.util.ArrayList;

/**
 * Created by milorad on 1.3.17..
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private final ArrayList<FriendItemBean> mData;
    private final FriendsListFragment.OnListFragmentInteractionListener mListener;

    public FriendListAdapter(ArrayList<FriendItemBean> items, FriendsListFragment.OnListFragmentInteractionListener listener) {
        mData = items;
        mListener = listener;
    }

    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_friends, parent, false);

        return new FriendListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FriendListAdapter.ViewHolder holder, int position) {
        holder.mItem = mData.get(position);
        holder.mTitleView.setText(mData.get(position).getName());
        holder.mDescriptionView.setText(mData.get(position).getId());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("Adaper: ", "Clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public FriendItemBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            mDescriptionView = (TextView) view.findViewById(R.id.description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}