package com.example.joe.smarttask.SmartTask_MainPage.Settings;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments.SettList.ListFragment;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments.SubMenuActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 23/04/2017.
 */

public class SettingsActivity extends SingleFragmentActivity implements SettingsFragment.Callbacks, ListFragment.Callbacks {
    Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment createFragment() {
        return new SettingsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        //        initialise toolbar
        toolbar = (Toolbar) findViewById(R.id.fragment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        set background color
        View v = findViewById(R.id.coordinator);
        View root = v.getRootView();
        root.setBackgroundColor(getResources().getColor(R.color.settings_background_blue_dark));
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


    @Override
    public void onItemSelected(SettingsObject settingsObject) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = SubMenuActivity.newIntent(this, settingsObject.getmTitle());
            startActivity(intent);
        } else {
            Fragment newDetail;
            if (settingsObject.getmTitle().equals(getResources().getString(R.string.settings_list_title))) {
                newDetail = new ListFragment();
            } else {
                newDetail = new ListFragment();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();

        }
    }

    public void onSubSettingsUpdated() {
        SettingsFragment listFragment = (SettingsFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
