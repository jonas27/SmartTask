package com.example.joe.smarttask.SmartTask_MainPage.Settings;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 23/04/2017.
 */

public class SettingsActivity extends SingleFragmentActivity {
    Toolbar toolbar;

    @Override
    protected Fragment createFragment() {

//        initialise toolbar
        toolbar = (Toolbar) findViewById(R.id.fragment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return new SettingsFragment();
    }

    //    Set Toolbar back button action equal to system back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
