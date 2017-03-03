package com.restaurant.milorad.isa_proj_android.fragments.user.admin;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.restaurant.milorad.isa_proj_android.BuildConfig;
import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.activities.MainActivity;
import com.restaurant.milorad.isa_proj_android.common.AppUtils;
import com.restaurant.milorad.isa_proj_android.common.ZctLogger;
import com.restaurant.milorad.isa_proj_android.network.API;
import com.restaurant.milorad.isa_proj_android.network.model.AdminBean;
import com.zerocodeteam.network.ZctNetwork;
import com.zerocodeteam.network.ZctResponse;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by milorad on 2.3.17..
 */
public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    private final ArrayList<AdminBean> mData;
    private final AdminFragment.OnListFragmentInteractionListener mListener;
    private ProgressDialog mProgressDialog;
    private static final ZctLogger mLogger = new ZctLogger(MainActivity.class.getSimpleName(), BuildConfig.DEBUG);
    private MRAItemClickedListener mOnItemClickListener;

    public AdminAdapter(ArrayList<AdminBean> items, AdminFragment.OnListFragmentInteractionListener listener, MRAItemClickedListener mraListener) {
        mData = items;
        mListener = listener;
        mOnItemClickListener = mraListener;
    }

    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_admins_crud, parent, false);

        return new AdminAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdminAdapter.ViewHolder holder, final int position) {
        holder.mItem = mData.get(position);
        holder.mTitleView.setText(mData.get(position).getName());
        holder.mDescriptionView.setText(mData.get(position).getEmail());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClicked(holder.mItem);

            }
        });
        ImageButton deleteButton = (ImageButton) holder.mView.findViewById(R.id.delete_admin);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + mData.get(position).getEmail());
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mProgressDialog = AppUtils.showProgress(holder.itemView.getContext());
                        API.getInstance().deleteAdmin(new ZctResponse<String>() {
                            @Override
                            public void onSuccess(String responseObject, Map<String, String> responseHeaders, Object cookie) {
                                AppUtils.hideProgress(mProgressDialog);

                                if(responseObject.equals("true")) {
                                    mData.remove(positionToRemove);
                                    notifyDataSetChanged();
                                    Toast.makeText(holder.itemView.getContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(holder.itemView.getContext(), "Only superuser can delete admins!", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onError(VolleyError error, ZctNetwork.ErrorType type, Map<String, String> responseHeaders, Object cookie) {
                                AppUtils.hideProgress(mProgressDialog);
                                Toast.makeText(holder.itemView.getContext(), "Profile data fetch failed", Toast.LENGTH_SHORT).show();
                            }
                        }, holder.mItem.getId());
                    }
                });
                adb.show();
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
        public AdminBean mItem;

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
    interface MRAItemClickedListener {
        void onItemClicked(AdminBean item);
    }

}