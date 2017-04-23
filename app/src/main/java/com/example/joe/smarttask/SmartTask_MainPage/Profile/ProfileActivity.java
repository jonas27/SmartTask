package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.support.v4.app.Fragment;

import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 23/04/2017.
 */

public class ProfileActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ProfileFragment();
    }
}
