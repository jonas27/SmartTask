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
import android.widget.TimePicker;

import com.example.joe.smarttask.R;

import java.util.Calendar;

/**
 * Created by joe on 20/04/2017.
 */

public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "com.joe.android.smarttask.time";
    private static final String TAG = "CL_TPF";
    private static final String ARG_DATE = "date";
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);
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

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(true);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
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
                        Log.d(TAG, "Hour: " + hour + " " + min);
                        int time = hour * 60 + min;
                        sendResult(Activity.RESULT_OK, time);
                    }
                }).create();
    }


    private void sendResult(int resultCode, int time) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }


}
