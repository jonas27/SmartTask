package com.example.joe.smarttask.SmartTask_MainPage.Task;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.joe.smarttask.R;

import java.util.List;

/**
 * Created by joe on 07/04/2017.
 */

public class TaskPagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private List<TaskObject> mTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_task);
    }

}
