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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SettingsObject;

import java.util.List;

/**
 * Created by joe on 07/04/2017.
 */

public class TaskPagerActivity extends AppCompatActivity {

    public static final String TASK_ID = "com.example.joe.smarttask.task_id";
    private static final String TAG = "CL_PrAc";
    private ViewPager mViewPager;
    private List<TaskObject> mTasksList;
    private Toolbar toolbar;

    public static Intent newIntent(Context packageContext, String mId) {
        Intent intent = new Intent(packageContext, TaskPagerActivity.class);
        intent.putExtra(TASK_ID, mId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        String mId = (String) getIntent().getSerializableExtra(TASK_ID);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTasksList = ListTask.getTaskList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            private boolean skip = false;
            @Override
            public Fragment getItem(int position) {
                TaskObject task = mTasksList.get(position);
                Log.d(TAG,"positiion "+position);
                if(task.getPriority()=="-1"||skip){
                    skip = true;
                    task = mTasksList.get(position+1);
                }

//                set the title of action bar to the title of the item clicked
//                -1 as viewpager loads the next page in memory and would set the the title to -1
//                CODE DOES NOT WORK PROPERLY AS THE VIEWPAGER LOADS MORE THAN FRAGMENT AT ONCE
                /*if (mTasksList.size() == 1) {
                    getSupportActionBar().setTitle(mTasksList.get(position).getName());
                }
                else if (position == mTasksList.size()-1 && mTasksList.size()>2) {
                    getSupportActionBar().setTitle(mTasksList.get(position).getName());
                    lastPageSelected=true;
                    Log.d(TAG,"exc");
                }
                else if (position > 0) {
                    getSupportActionBar().setTitle(mTasksList.get(position - 1).getName());
                }*/

                return TaskFragment.newInstance(task.getId());
            }

            @Override
            public int getCount() {
                return mTasksList.size()-1;
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

        });

        for (int i = 0; i < mTasksList.size()-1; i++) {
            if (mTasksList.get(i).getId().equals(mId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //Listener for when new page is selected
//            set action bar title to task title
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle(mTasksList.get(position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private static class Adapter extends RecyclerView.Adapter<Holder> {
        private List<SettingsObject> mListSettings;

        public Adapter(List<SettingsObject> mListSettings) {
            this.mListSettings = mListSettings;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SMMainActivity.getAppContext());
            View view = layoutInflater.inflate(R.layout.fragment_settings, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

            SettingsObject mObject = mListSettings.get(position);
            holder.bindTask(mObject);
        }

        @Override
        public int getItemCount() {
            return mListSettings.size()-1;
        }
    }

    // Provide a reference to the views for each data item
    private static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView describtion;
        ImageView icon;


        //        bind views here
        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        //    specify individual tasks behaviour on layout
        public void bindTask(SettingsObject mObject) {
            title.setText(mObject.getmTitle());
            describtion.setText(mObject.getmDescription());

        }


    }
}
