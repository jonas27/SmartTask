package com.example.joe.smarttask.ChooseProfile;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 06/05/2017.
 */

public class ChooseProfileActivity extends SingleFragmentActivity{

    @Override
    public void onResume() {
        super.onResume();
        //        initialise toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   SmartTask");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    protected Fragment createFragment() {
        return new ChooseProfileFragment();
    }
}
