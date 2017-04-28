package com.example.joe.smarttask.SmartTask_MainPage.Calendar;

import android.support.v4.app.Fragment;

import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 28/04/2017.
 */

public class SingleDayActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SingleDayFragment();
    }
}
