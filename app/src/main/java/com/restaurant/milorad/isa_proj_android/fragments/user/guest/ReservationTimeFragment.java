package com.restaurant.milorad.isa_proj_android.fragments.user.guest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.restaurant.milorad.isa_proj_android.R;
import com.restaurant.milorad.isa_proj_android.common.AppUtils;
import com.restaurant.milorad.isa_proj_android.network.model.RestaurantItemBean;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservationTimeFragment extends Fragment {

    private static final String RESTAURANTS_LIST_BUNDLE_KEY = "restaurant_list_data";
    private OnFragmentInteractionListener mListener;

    private Button btnChangeDate;
    private Button btnChangeTime;


    private static TextView mName;
    private static EditText mTime;
    private static EditText mDate;
    private static EditText mDuration;
    private Button mSubmit;

    private RestaurantItemBean mRestaurantData;

    public ReservationTimeFragment() {
        // Required empty public constructor
    }

    public static ReservationTimeFragment newInstance(RestaurantItemBean restaurant) {
        ReservationTimeFragment fragment = new ReservationTimeFragment();
        Bundle args = new Bundle();

        args.putSerializable(RESTAURANTS_LIST_BUNDLE_KEY, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mRestaurantData = (RestaurantItemBean) getArguments().getSerializable(RESTAURANTS_LIST_BUNDLE_KEY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.f_rezervation_time, container, false);

        btnChangeTime = (Button) view.findViewById(R.id.btnChangeTime);
        btnChangeDate = (Button) view.findViewById(R.id.btnChangeDate);

        Calendar cal = GregorianCalendar.getInstance();
        mName = (TextView) view.findViewById(R.id.tv_restaurant_name);
        mTime = (EditText) view.findViewById(R.id.edit_reservation_start_time);
        mDate = (EditText) view.findViewById(R.id.edit_reservation_start_date);
        mDuration = (EditText) view.findViewById(R.id.edit_reservation_duration);
        mSubmit = (Button) view.findViewById(R.id.action_next);


        mName.setText(mRestaurantData.getName());
        mDate.setText(AppUtils.dateToString(new Date(cal.getTimeInMillis())));
        mTime.setText(AppUtils.timeToString(new Date(cal.getTimeInMillis())));


        btnChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                newFragment.show(fm, "timePicker");

            }
        });

        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

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

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTime.setText(hourOfDay + ":" + minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            mDate.setText(day + "-" + (month + 1) + "-" + year);
        }
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
        void onFragmentInteraction();
    }
}
