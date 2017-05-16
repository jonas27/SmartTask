package com.example.joe.smarttask.smartaskMain.calendar;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SingleFragmentActivity;

/**
 * Created by joe on 28/04/2017.
 */

public class SingleDayActivity extends SingleFragmentActivity {


    private Toolbar toolbar;

    private static final String TAG = "SingleDay";
    private static long date;

    public SingleDayActivity() {
        super();
    }

    public SingleDayActivity(Long date) {
        super();
        this.date = date;
        Log.d(TAG,"date "+date);
    }

    @Override
    protected Fragment createFragment() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   SmartTask");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        return new SingleDayFragment();
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
    public static long getDate(){
        return date;
    }
}
