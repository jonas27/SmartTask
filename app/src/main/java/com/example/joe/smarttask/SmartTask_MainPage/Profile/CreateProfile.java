package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.SingleFragmentActivity;

public class CreateProfile extends SingleFragmentActivity {
        Toolbar toolbar;

    @Override
    protected Fragment createFragment() {

        //        initialise toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return new CreateProfileFragment();
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
}


