package com.smarttask17.joe.smarttask.smartaskMain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarttask17.joe.smarttask.profileSwitcher.ProfileSwitcherFragment;
import com.smarttask17.joe.smarttask.smartaskMain.list.ListOfTasks;
import com.smarttask17.joe.smarttask.smartaskMain.messenger.MessengerFragment;
import com.smarttask17.joe.smarttask.LogInActivity;
import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.calendar.CalendarFragment;
import com.smarttask17.joe.smarttask.smartaskMain.list.ListFragment;
import com.smarttask17.joe.smarttask.smartaskMain.newTask.NewTaskActivity;
import com.smarttask17.joe.smarttask.smartaskMain.profile.ListOfProfiles;
import com.smarttask17.joe.smarttask.smartaskMain.profile.MyProfileActivity;
import com.smarttask17.joe.smarttask.smartaskMain.profile.ProfileObject;
import com.smarttask17.joe.smarttask.smartaskMain.settings.SettingsActivity;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.FireBase;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.PictureConverter;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.ViewPagerZoomOutAnimation;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;


/**
 * Class written 100% by us.
 * This is the entry point for the main app
 */

public class SmarttaskMainActivity extends AppCompatActivity {

    private static final String TAG = "CL_MaAc";
    private static final String ADDRESS= "http://jonasburster.de/html//smarttask/";
    private static Context context;
    private static Context contextMain;
    private static List<Fragment> sFragmentList;
    private static SmarttaskMainActivity instance;
    // When requested, this adapter returns a Fragment,
    // representing an object in the collection.
    MainPagerAdapter mMainPagerAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    private FloatingActionButton mActionAdd;
    private EditText message;
    private ImageView send;
    private Menu subMenu;
    private LinearLayout linear;
    private static Intent intent;
    private static final String dir = "/storage/emulated/0/smarttask/";

//    [Start: For Adds]
    public static Bitmap bitmapAdds;
    public static String textAdds;
//    [End: For Adds]

    private static ImageView mToolbarIcon;
    private ProfileObject mProfile;
    private static int backButtonCounter;

    public static boolean showOnlyOwnTasks = false;

    public static Context getAppContext() {
        return SmarttaskMainActivity.context;
    }

    @Override
    public void onResume() {
        super.onResume();
        setIconToolbar();
    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.instance = this;
        FireBase.fireBase(getAppContext());
        context = getApplicationContext();
        contextMain = this;
        ListOfTasks.getSortList();
        sFragmentList = new ArrayList<>();

        //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
            new FetchPicture().execute();
            new FetchText().execute();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToolbarIcon = (ImageView) findViewById(R.id.toolbar_icon);
        mToolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnlyOwnTasks = !showOnlyOwnTasks;
                if (showOnlyOwnTasks) {
                    getSupportActionBar().setTitle("   " + SharedPrefs.getCurrentUser());
                    mToolbarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_white_24dp));
                    ListFragment listFragment = (ListFragment) mMainPagerAdapter.getItem(1);
                    // Check if the tab fragment is available and update recyclerview
                    if (listFragment != null) {
                        listFragment.updateUI();
                    }
                } else {
                    getSupportActionBar().setTitle("   SmartTask");
                    setIconToolbar();
                    ListFragment listFragment = (ListFragment) mMainPagerAdapter.getItem(1);
                    // Check if the tab fragment is available and update recyclerview
                    if (listFragment != null) {
                        listFragment.updateUI();
                    }
                }
            }
        });
        getSupportActionBar().setTitle("   SmartTask");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mActionAdd = (FloatingActionButton) findViewById(R.id.fab);
        mActionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });

        send = (ImageView) findViewById(R.id.send);
        message = (EditText) findViewById(R.id.message);
        linear = (LinearLayout) findViewById(R.id.linear);

        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewPager(mViewPager);
        mViewPager.setPageTransformer(true, new ViewPagerZoomOutAnimation());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //Listener for when new page is selected
            @Override
            public void onPageSelected(int position) {
                CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mViewPager.getLayoutParams();
                p.setMargins(0, 0, 0, 0);
                mViewPager.setLayoutParams(p);
                linear.setVisibility(View.INVISIBLE);
                if (position == 0) {
                    mActionAdd.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    mProfile = ListOfProfiles.getProfile(SharedPrefs.getCurrentProfile());
                    if(mProfile!=null) {
                        switch (mProfile.getPprivileges()) {
                            case 1: {
                                mActionAdd.setVisibility(View.VISIBLE);
                                break;
                            }
                            case 2: {
                                mActionAdd.setVisibility(View.VISIBLE);
                                break;
                            }
                            case 3: {
                                mActionAdd.setVisibility(View.INVISIBLE);
                                break;
                            }
                            default: {
                                mActionAdd.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                } else if (position == 2) {
                    mActionAdd.setVisibility(View.INVISIBLE);
                    linear.setVisibility(View.VISIBLE);
                    int dpValue = 50; // margin in dips
                    float d = context.getResources().getDisplayMetrics().density;
                    int margin = (int) (dpValue * d); // margin in pixels
                    p.setMargins(0, 0, 0, margin);
                    mViewPager.setLayoutParams(p);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
    }

//      Add the fragments to ViewPager
    private void setupViewPager(ViewPager viewPager) {
        mMainPagerAdapter.addFragment(new CalendarFragment(), getString(R.string.main_screen_calendar));
        mMainPagerAdapter.addFragment(new ListFragment(), getString(R.string.main_screen_tasks));
        mMainPagerAdapter.addFragment(new MessengerFragment(), "Messenger");
        mViewPager.setAdapter(mMainPagerAdapter);
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


    public void showDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(new ProfileSwitcherFragment(), "dialog");
//        remove previous dialogs
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        ProfileSwitcherFragment newFragment = new ProfileSwitcherFragment();
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onBackPressed() {
        backButtonCounter++;
        if(backButtonCounter==0){Toast.makeText(context, getString(R.string.main_double_tap),Toast.LENGTH_SHORT);}
        if(backButtonCounter==2){
            finish();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        backButtonCounter=0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(getAppContext(), MyProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                intent = new Intent(getAppContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_change_profile:
//                showProfiles(false);
                showDialog();
                return true;
            case R.id.menu_profile:
                intent = new Intent(getAppContext(), MyProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                FireBase fireBase = FireBase.fireBase(this);
                fireBase.logout();
                SharedPrefs.editor.clear().commit();
                SharedPrefs.setCurrentProfile("");
                SharedPrefs.setCurrentUser("");
                LogInActivity.introWasShown = false;
                //        CLear cache for logout
                File cacheDir = context.getCacheDir();
                File[] files = cacheDir.listFiles();
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


    private static void setIconToolbar() {
        if (showOnlyOwnTasks) {
            mToolbarIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_list_white_24dp));
        } else {
            File mProfilePicture;
            String userID = SharedPrefs.getCurrentProfile();
            String path = dir + userID + ".jpg";
            File profileImage = new File(path);
            if (profileImage != null) {
                if (profileImage.length() > 0) {
                    mToolbarIcon.setImageBitmap(PictureConverter.getRoundProfilePicture(path, 100));
                } else {
//                    picture is downloaded either at ChooseProfile or at change user (in this class)
                    mToolbarIcon.setImageDrawable(getAppContext().getResources().getDrawable(R.mipmap.smlogo));
                }
            }
        }
        RotateAnimation anim = new RotateAnimation(180.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //Setup anim with desired properties
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(0);
        anim.setDuration(300);
        mToolbarIcon.startAnimation(anim);
    }


    //    Start new thread to download adds
    private class FetchPicture extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
//                Download poicture
                java.net.URL url = new java.net.URL(ADDRESS + "carwash_img.png");
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                bitmapAdds= myBitmap;
                Log.d(TAG, "Picture was downloaded");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
    }
    //    Start new thread to download adds
    private class FetchText extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
//                Download txt descrpition
                java.net.URL url = new java.net.URL(ADDRESS + "easywash_description.txt");
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Scanner in= new Scanner(input);
                StringBuilder s=new StringBuilder();
                while(in.hasNext()){
                    s.append(in.next()+ " ");
                }
                textAdds=s.toString();
                Log.d(TAG, "Descritption was downloaded");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
    }




}
