package com.example.joe.smarttask.SmartTask_MainPage.Settings;

import android.support.v4.app.Fragment;

import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 23/04/2017.
 */

public class SMSettingsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SMSettingsFragment();
    }
}
