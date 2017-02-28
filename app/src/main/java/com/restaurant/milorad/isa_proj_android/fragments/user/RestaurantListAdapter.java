package com.restaurant.milorad.isa_proj_android.fragments.user;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.fragments.user.RestaurantListFragment.OnListFragmentInteractionListener;
import com.restaurant.milorad.isa_proj_android.network.model.RestaurantItemBean;

import java.util.ArrayList;

/**
 * Created by milorad on 28.2.17..
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private final ArrayList<RestaurantItemBean> mData;
    private final OnListFragmentInteractionListener mListener;

    public RestaurantListAdapter(ArrayList<RestaurantItemBean> items, OnListFragmentInteractionListener listener) {
        mData = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_restaurant, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mData.get(position);
        holder.mTitleView.setText(mData.get(position).getName());
        holder.mDescriptionView.setText(mData.get(position).getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("Adaper: ", "Clicked");
            }
        });

//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppUtils.go2RestaurantActivity();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public RestaurantItemBean mItem;

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