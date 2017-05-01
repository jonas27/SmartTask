package com.example.joe.smarttask.SmartTask_MainPage.Task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SettingsObject;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.google.android.gms.tasks.Task;

import java.util.List;

/**
 * Created by joe on 07/04/2017.
 */

public class TaskPagerActivity extends AppCompatActivity {

    public static final String TASK_ID = "com.example.joe.smarttask.task_id";
    private static final String TAG = "CL_TaPagerAc";
    private ViewPager mViewPager;
    private List<TaskObject> mTasksList;
    private Toolbar toolbar;
    private TaskObject separator;

    public static int separatorPosition;

    public static Intent newIntent(Context packageContext, String mId) {
        Intent intent = new Intent(packageContext, TaskPagerActivity.class);
        intent.putExtra(TASK_ID, mId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        //        initialise toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String mId = (String) getIntent().getSerializableExtra(TASK_ID);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTasksList = ListTask.getTaskList();

        //                if pro user remove ads (separator line)
        if (SharedPrefs.getProUser()) {
            for (int c = 0; c < mTasksList.size(); c++) {
                if (mTasksList.get(c).getPriority().equals("-1")) {
                    separator = mTasksList.get(c);
                    mTasksList.remove(c);
                    separatorPosition = c;
                    break;
                }
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {


            @Override
            public Fragment getItem(int position) {
Log.d(TAG, Integer.toString(position));
                TaskObject task;
//                if (position > separatorPosition && separatorPosition != 0 && !SharedPrefs.getProUser()) {
//                    task = mTasksList.get(position - 1);
//                } else {
                    task = mTasksList.get(position);
//                }
//                    TaskObject task = mTasksList.get(position);
                Log.d(TAG, "positiion " + position);
                return TaskFragment.newInstance(task.getId());
            }

            //            minus one because of separator line
            @Override
            public int getCount() {
                return mTasksList.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //Listener for when new page is selected
//            set action bar title to task title
            @Override
            public void onPageSelected(int position) {
                if (position > separatorPosition && separatorPosition != 0 && !SharedPrefs.getProUser()) {
                    getSupportActionBar().setTitle(mTasksList.get(position - 1).getName());
                } else {
                    Log.d(TAG, "pageselected " + position);
                    getSupportActionBar().setTitle(mTasksList.get(position).getName());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        Set the Object for the clicked holder
//        the intent puts in the Task id and this get search in the list
        for (int i = 0; i < mTasksList.size(); i++) {
            if (mTasksList.get(i).getId().equals(mId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

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

//    add the separator again for consistency. I had some weird errors when this was not there
//    public void onStop() {
//        super.onStop();
//        mTasksList.add(separatorPosition,separator);
//    }
}
