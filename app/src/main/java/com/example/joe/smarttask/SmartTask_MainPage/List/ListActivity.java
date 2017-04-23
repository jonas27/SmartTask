package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.support.v4.app.Fragment;

import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Class written 100% by us.
 * This is the entry point for the main app
 */

public class ListActivity extends SingleFragmentActivity {

    //TAG for Logs
    private static final String TAG = "Cl_ListAc";

    // [Start: get Singletons]
    private FireBase mFireBase;
    private ListTask mListTask;
    // [End: get Singletons]




    @Override
    protected Fragment createFragment() {
        //Return calender instead of listfragment
        return new ListFragment();
//        return new CalendarFragment();
    }

    @Override
    protected void initSingletons(){
        mFireBase = FireBase.fireBase(this);
        mListTask = ListTask.list(this);
    }

}
