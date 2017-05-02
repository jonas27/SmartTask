package com.example.joe.smarttask.SmartTask_MainPage.Calendar;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 28/04/2017.
 */

public class SingleDayActivity extends SingleFragmentActivity {


    private static final String TAG = "SingleDay";
    private static long date;

    public SingleDayActivity() {
        super();
    }

    public SingleDayActivity(Long date) {
        super();
        this.date = date;
        Log.d(TAG,"date "+date);
    }

    @Override
    protected Fragment createFragment() {
        return new SingleDayFragment();
    }

    public static long getDate(){
        return date;
    }
}
