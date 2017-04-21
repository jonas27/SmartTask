package com.example.joe.smarttask.SmartTask_MainPage.NewTask;

import android.support.v4.app.Fragment;

import com.example.joe.smarttask.SmartTask_MainPage.SingleFragmentActivity;

/**
 * Created by joe on 20/04/2017.
 */

public class NewTaskActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {


        return new NewTaskFragment();
    }
}
