package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

import java.util.List;

/**
 * Created by joe on 23/04/2017.
 */

public class ProfileActivity extends FragmentActivity {
    public static final String PROFILE_ID = "com.example.joe.smarttask.profile_id";

    private ViewPager mViewPager;
    private List<ProfileObject> mProfileList;

    public static Intent newIntent(Context packageContext, String mId) {
        Intent intent = new Intent(packageContext, ProfileActivity.class);
        intent.putExtra(PROFILE_ID, mId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final String mPid = (String) getIntent().getSerializableExtra(PROFILE_ID);


        mViewPager = (ViewPager) findViewById(R.id.viewpager_profile);
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
    }


}
