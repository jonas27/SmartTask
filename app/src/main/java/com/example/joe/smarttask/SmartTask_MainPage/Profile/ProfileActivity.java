package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.SharedPrefs;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.SingleFragmentActivity;

import java.util.List;

/**
 * Created by joe on 23/04/2017.
 */

public class ProfileActivity extends SingleFragmentActivity {
    public static final String PROFILE_ID = "com.example.joe.smarttask.profile_id";

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private List<ProfileObject> mProfileList;

    @Override
    protected Fragment createFragment() {

        return ProfileFragment.newInstance(SharedPrefs.getCurrentProfile(getAppContext()));
    }

    //    Set Toolbar back button action equal to system back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onResume(){
        super.onResume();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

  }



