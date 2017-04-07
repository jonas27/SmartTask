package com.example.joe.smarttask.SmartTask_MainPage.Task_P;

import android.support.v4.app.Fragment;

import com.example.joe.smarttask.SmartTask_MainPage.SingleFragmentActivity;

/**
 * Created by joe on 05/04/2017.
 */

public class TaskActivity extends SingleFragmentActivity {

    //TAG for Logs
    private static final String TAG = "CLASS_TaskActivity";

    @Override
    protected Fragment createFragment() {
        return new TaskFragment();
    }
}
