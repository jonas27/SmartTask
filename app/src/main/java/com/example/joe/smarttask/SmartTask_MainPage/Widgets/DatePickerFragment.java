package com.example.joe.smarttask.SmartTask_MainPage.Widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.joe.smarttask.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by joe on 20/04/2017.
 */

public class DatePickerFragment extends DialogFragment {

    private static final String TAG = "CL_DPF";

    public static final String EXTRA_DATE = "com.joe.android.smarttask.date";

//    Date date = (Date) getArguments().getSerializable(ARG_DATE);
    Date date;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;


    public static DatePickerFragment newInstance() {
        Bundle args = new Bundle();
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog
    onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date_picker, null);

//        calendar.setTime(date);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
            @Override
                    public void onClick(DialogInterface mDI, int which){

                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                long date=year*10000+month*100+day;
                sendResult(Activity.RESULT_OK, date);
                Log.d(TAG, "Year Month Day: " + year + month + day);
            }
        }).create();
    }


    private void sendResult(int resultCode, Long date) {
        if (getTargetFragment() == null) {
            return; }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
