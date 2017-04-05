package com.example.joe.smarttask.SmartTask_MainPage;

import android.support.v4.app.Fragment;

/**
 * Created by joe on 05/04/2017.
 */

public class TaskActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new TaskFragment();
    }
}
