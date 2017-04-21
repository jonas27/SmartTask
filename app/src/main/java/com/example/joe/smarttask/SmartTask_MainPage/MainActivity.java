package com.example.joe.smarttask.SmartTask_MainPage;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Calendar.CalendarFragment;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListFragment;
import com.example.joe.smarttask.SmartTask_MainPage.NewTask.NewTaskActivity;
import com.example.joe.smarttask.SmartTask_MainPage.NewTask.NewTaskFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by joe on 18/04/2017.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CL_MaAc";

    // When requested, this adapter returns a Fragment,
    // representing an object in the collection.
    MainPagerAdapter mMainPagerAdapter;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private static Context context;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    private FloatingActionButton mActionAdd;

    private static List<Fragment> sFragmentList;
    private static boolean sStartActivity;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mActionAdd = (FloatingActionButton) findViewById(R.id.fab);
        mActionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fm = getSupportFragmentManager();
//                    Fragment fragment = new NewTaskFragment();
//                    fm.beginTransaction()
//                            .add(R.id.fragment_container, fragment)
//                            .commit();
                Intent intent= new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });




        sFragmentList = new ArrayList<>();

        // ViewPager and its adapters use support library
        // layout.layouts.fragments, so use getSupportFragmentManager.
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
//        mViewPager.setAdapter(mMainPagerAdapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //Listener for when new page is selected
            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mActionAdd.setVisibility(View.VISIBLE);
                } else {
                    mActionAdd.setVisibility(View.INVISIBLE);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void setupViewPager(ViewPager viewPager) {
        mMainPagerAdapter.addFragment(new CalendarFragment(), "Calendar");
        mMainPagerAdapter.addFragment(new ListFragment(), "Tasks");
//        mMainPagerAdapter.addFragment(new MessengerFragment(), "Messenger");
        mViewPager.setAdapter(mMainPagerAdapter);
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }


    public class MainPagerAdapter extends FragmentPagerAdapter {
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return sFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return sFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            sFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
