package com.example.joe.smarttask.SmartTask_MainPage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.joe.smarttask.LogInActivity;
import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Calendar.CalendarFragment;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListFragment;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.NewTask.NewTaskActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileObject;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SettingsActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FetchAdds;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class written 100% by us.
 * This is the entry point for the main app
 */

public class SMMainActivity extends AppCompatActivity {

    private static final String TAG = "CL_MaAc";
    private static Context context;
    private static List<Fragment> sFragmentList;
    private static boolean sStartActivity;
    // When requested, this adapter returns a Fragment,
    // representing an object in the collection.
    MainPagerAdapter mMainPagerAdapter;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mActionAdd;
    private Menu subMenu;
    private Menu mMenuSettings;
    private Menu mMenuClose;
    private Intent intent;

    public static Context getAppContext() {
        return SMMainActivity.context;
    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SMMainActivity.context = getApplicationContext();

        SharedPrefs.getSharedPrefs(this);

        ListTask.sortList();

    //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
    //    new FetchPicture().execute();

        FireBase.fireBase(getAppContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

//        getSupportActionBar().setHomeButtonEnabled(true);

        mActionAdd = (FloatingActionButton) findViewById(R.id.fab);
        mActionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });


        sFragmentList = new ArrayList<>();

        // ViewPager and its adapters use support library
        // layout.layouts.fragments, so use getSupportFragmentManager.
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
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

//    [Start: Define Menu]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        TODO: Add right maring to menu inflator
        subMenu = (Menu) findViewById(R.id.menu_expand_menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                intent = new Intent(getAppContext(), SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_change_profile:
                Dialog dialog = new Dialog(SMMainActivity.this);
                dialog.setContentView(R.layout.change_profile);
                dialog.setTitle("Change profile");
                dialog.setCancelable(true);

                GridView grid = (GridView) dialog.findViewById(R.id.profile_grid);
                Log.d(TAG,"Number of collums "+grid.getNumColumns());

                grid.setAdapter(new ProfileAdapter(context, ListProfile.getProfileList()));

                //set up button
                Button button = (Button) dialog.findViewById(R.id.close);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });




                //now that the dialog is set up, it's time to show it
                dialog.show();
                return true;
            case R.id.menu_profile:
                intent = new Intent(getAppContext(), ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                FireBase fireBase = FireBase.fireBase(this);
                fireBase.logout();
                SharedPrefs.editor.clear().commit();
                LogInActivity.introWasShown=false;
                //        CLear cache for logout
                File cacheDir = context.getCacheDir();
                File[] files = cacheDir.listFiles();
                Log.d(TAG, Integer.toString(files.length));
                if (files != null) {
                    for (File file : files)
                        file.delete();
                }
                intent = new Intent(getAppContext(), LogInActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    [End: Define Menu]


    private void setupViewPager(ViewPager viewPager) {
        mMainPagerAdapter.addFragment(new CalendarFragment(), getString(R.string.main_screen_calendar));
        mMainPagerAdapter.addFragment(new ListFragment(), getString(R.string.main_screen_tasks));
//        mMainPagerAdapter.addFragment(new MessengerFragment(), "Messenger");
        mViewPager.setAdapter(mMainPagerAdapter);
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







//    Start new thread to download adds
private class FetchPicture extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... params) {
            new FetchAdds().saveImage(FetchAdds.URL_ADDRESS);
        return null;
    }
}


    private static class ProfileAdapter extends ArrayAdapter<ProfileObject> {
        private LayoutInflater inflater;

        public ProfileAdapter(Context context, ArrayList<ProfileObject> profiles) {
            super(context, R.layout.control_calendar_day,profiles);
            inflater = LayoutInflater.from(context);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            view = inflater.inflate(R.layout.profile_square, parent, false);
            ProfileObject current = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(current.getPname());

            TextView score = (TextView) view.findViewById(R.id.score);
            score.setText(current.getPscore());

            return view;
        }
    }
}
