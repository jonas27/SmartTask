package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

import java.util.List;

/**
 * Created by joe on 23/04/2017.
 */

public class ProfileActivity extends SingleFragmentActivity {
    public static final String PROFILE_ID = "com.example.joe.smarttask.profile_id";

    private Toolbar toolbar;
    private ViewPager mViewPager;
    private List<ProfileObject> mProfileList;

    @Override
    protected Fragment createFragment() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return ProfileFragment.newInstance(SharedPrefs.getCurrentProfile());
    }

    //    Set Toolbar back button action equal to system back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return false;
    }



    /*public static Intent newIntent(Context packageContext, String mId) {
        Intent intent = new Intent(packageContext, ProfileActivity.class);
        intent.putExtra(PROFILE_ID, mId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        final String mPid = (String) getIntent().getSerializableExtra(PROFILE_ID);


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mProfileList = ListProfile.getProfileList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                if (mProfileList == null) {
                    ProfileFragment p=new ProfileFragment();
                    return p;
                }
                ProfileObject profile = mProfileList.get(position);
                return ProfileFragment.newInstance(profile.getPid());
            }
            @Override
            public int getCount() {
                if (mProfileList == null) {
                    return 0;
                }
                return mProfileList.size();}


        });
        if (mProfileList == null) {
            return;
        }

        for (int i = 0; i < mProfileList.size(); i++) {

            if (mProfileList.get(i).getPid().equals(mPid)) {
                mViewPager.setCurrentItem(i);
                break; }
        }
*/    }



