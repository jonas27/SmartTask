package com.smarttask17.joe.smarttask.smartaskMain.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;

import java.util.List;

/**
 * Created by joe on 07/04/2017.
 */

public class TaskPagerActivity extends AppCompatActivity {

    public static final String TASK_ID = "com.example.joe.smarttask.task_id";
    private static final String TAG = "CL_TaPagerAc";
    private ViewPager mViewPager;
    private static List<TaskObject> mTasksList;
    private Toolbar toolbar;
    private TaskObject separator;

    public static int separatorPosition;

    public static Intent newIntent(Context packageContext, String mId,List<TaskObject> list) {
        Intent intent = new Intent(packageContext, TaskPagerActivity.class);
        mTasksList = list;
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
        //mTasksList = ListOfTasks.getTaskList();

        //                if pro user remove ads (separator line)
        if (SharedPrefs.getProUser()) {
            for (int c = 0; c < mTasksList.size(); c++) {
                if (mTasksList.get(c).getPriority()==-1) {
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
                TaskObject task;
                    task = mTasksList.get(position);
                Log.d(TAG, "position " + position);
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
                    getSupportActionBar().setTitle(mTasksList.get(position).getName());
                }
                if(mTasksList.get(position).getPriority()==-1){
                    getSupportActionBar().setTitle("EasyWash");
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
