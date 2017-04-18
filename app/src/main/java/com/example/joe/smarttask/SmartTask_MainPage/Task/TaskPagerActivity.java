package com.example.joe.smarttask.SmartTask_MainPage.Task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;

import java.util.List;

/**
 * Created by joe on 07/04/2017.
 */

public class TaskPagerActivity extends FragmentActivity {

    public static final String TASK_ID = "com.example.joe.smarttask.task_id";

    private ViewPager mViewPager;
    private List<TaskObject> mTasksList;

    public static Intent newIntent(Context packageContext, String mId) {
        Intent intent = new Intent(packageContext, TaskPagerActivity.class);
        intent.putExtra(TASK_ID, mId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        String mId = (String) getIntent().getSerializableExtra(TASK_ID);


        mViewPager = (ViewPager) findViewById(R.id.viewpager_task);
        mTasksList = ListTask.getmTaskList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                TaskObject task = mTasksList.get(position);
                return TaskFragment.newInstance(task.getId());
//                return TaskFragment.newInstance(,task.getId());
            }
            @Override
            public int getCount() {
                return mTasksList.size();
            }
        });

        for (int i = 0; i < mTasksList.size(); i++) {
            if (mTasksList.get(i).getId().equals(mId)) {
                mViewPager.setCurrentItem(i);
                break; }
        }
    }

}
