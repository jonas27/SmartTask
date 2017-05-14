package com.example.joe.smarttask.ChooseProfile;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.SharedPrefs;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.SingleFragmentActivity;

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
        getSupportActionBar().setTitle(getString(R.string.menu_change_profile));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if(SharedPrefs.getCurrentProfile(getAppContext()).compareToIgnoreCase("")!=0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        View v = findViewById(R.id.coordinator);
        View root = v.getRootView();
        root.setBackgroundColor(Color.parseColor("#E0E0E0"));
    }

    @Override
    protected Fragment createFragment() {
        return new ChooseProfileFragment();
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
