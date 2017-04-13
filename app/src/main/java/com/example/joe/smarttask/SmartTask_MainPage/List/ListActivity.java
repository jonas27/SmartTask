package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.joe.smarttask.Calendar.CalendarFragment;
import com.example.joe.smarttask.SmartTask_MainPage.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListFragment;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.SingleFragmentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Class written 100% by us.
 * This is the entry point for the main app
 */

public class ListActivity extends SingleFragmentActivity {

    //TAG for Logs
    private static final String TAG = "Cl_Main_Activity";

    // [Start: get Singletons]
    private FireBase mFireBase;
    private ListTask mListTask;
    // [End: get Singletons]




    @Override
    protected Fragment createFragment() {
        //Return calender instead of listfragment
        //return new ListFragment();
        return new CalendarFragment();
    }

    @Override
    protected void initSingletons(){
        mFireBase = FireBase.fireBase(this);
        mListTask = ListTask.list(this);
    }

}
