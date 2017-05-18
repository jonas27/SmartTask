package com.smarttask17.joe.smarttask.smartaskMain.profile;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SingleFragmentActivity;

public class ProfileCreatorActivity extends SingleFragmentActivity {
        Toolbar toolbar;

    @Override
    protected Fragment createFragment() {

        //        initialise toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return new ProfileCreatorFragment();
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


