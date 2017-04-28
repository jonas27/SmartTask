package com.example.joe.smarttask.SmartTask_MainPage.Settings.List;

import android.support.v4.app.Fragment;

import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 27/04/2017.
 */

public class LActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LFragment();
    }
}
