package com.example.joe.smarttask.SmartTask_MainPage.Widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.joe.smarttask.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by joe on 20/04/2017.
 */

public class TimePickerFragment extends DialogFragment {
    private static final String TAG = "CL_TPF";

    public static final String EXTRA_TIME = "com.joe.android.smarttask.time";

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);

    private static final String ARG_DATE = "date";

    private TimePicker mTimePicker;


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
                .inflate(R.layout.dialog_time_picker, null);

//        calendar.setTime(date);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(true);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDI, int which) {
                        int hour, min;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            hour = mTimePicker.getHour();
                            min = mTimePicker.getMinute();
                        } else {
                            hour = mTimePicker.getCurrentHour();
                            min = mTimePicker.getCurrentMinute();
                        }
                        Log.d(TAG, "Hour: "+ Long.toString(System.currentTimeMillis()));
                        if(hour==0){hour=24;}
                        long time=hour*100+min;
                        sendResult(Activity.RESULT_OK, time);
                    }
                }).create();
    }


    private void sendResult(int resultCode, Long time) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }


}