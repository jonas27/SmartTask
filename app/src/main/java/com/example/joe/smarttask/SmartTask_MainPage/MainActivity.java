package com.example.joe.smarttask.SmartTask_MainPage;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
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

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Calendar.CalendarFragment;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by joe on 18/04/2017.
 */

public class MainActivity extends AppCompatActivity {

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    MainPagerAdapter mMainPagerAdapter;

    ActionBarDrawerToggle mDrawerToggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;

    private static Context context;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    private static List<Fragment> sFragmentList;
    private static boolean sStartActivity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

//        // Get DrawerLayout ref from layout
//        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
//        // Initialize ActionBarDrawerToggle, which will control toggle of hamburger.
//        // You set the values of R.string.open and R.string.close accordingly.
//        // Also, you can implement drawer toggle listener if you want.
//        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
//        // Setting the actionbarToggle to drawer layout
//        drawerLayout.setDrawerListener(mDrawerToggle);
//        // Calling sync state is necessary to show your hamburger icon...
//        // or so I hear. Doesn't hurt including it even if you find it works
//        // without it on your test device(s)
//        mDrawerToggle.syncState();

        sFragmentList = new ArrayList<>();


        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
//        mViewPager.setAdapter(mMainPagerAdapter);


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


//    private void enableViews(boolean enable) {
//
//        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
//        // when you enable on one, you disable on the other.
//        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
//        if(enable) {
//            // Remove hamburger
//            mDrawerToggle.setDrawerIndicatorEnabled(false);
//            // Show back button
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
//            // clicks are disabled i.e. the UP button will not work.
//            // We need to add a listener, as in below, so DrawerToggle will forward
//            // click events to this listener.
//            if(!mToolBarNavigationListenerIsRegistered) {
//                mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Doesn't have to be onBackPressed
//                        onBackPressed();
//                    }
//                });
//
//                mToolBarNavigationListenerIsRegistered = true;
//            }
//
//        } else {
//            // Remove back button
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            // Show hamburger
//            mDrawerToggle.setDrawerIndicatorEnabled(true);
//            // Remove the/any drawer toggle listener
//            mDrawerToggle.setToolbarNavigationClickListener(null);
//            mToolBarNavigationListenerIsRegistered = false;
//        }
//
//        // So, one may think "Hmm why not simplify to:
//        // .....
//        // getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
//        // mDrawer.setDrawerIndicatorEnabled(!enable);
//        // ......
//        // To re-iterate, the order in which you enable and disable views IS important #dontSimplify.
//    }


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
