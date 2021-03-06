package com.smarttask17.joe.smarttask.smartaskMain.settings;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.settings.settingsCoordinator.listSettings.SubSettingsListFragment;
import com.smarttask17.joe.smarttask.smartaskMain.settings.settingsCoordinator.proUserSettings.ProUserFragment;
import com.smarttask17.joe.smarttask.smartaskMain.settings.settingsCoordinator.SubMenuActivity;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SingleFragmentActivity;

/**
 * Activity for settings
 */

public class SettingsActivity extends SingleFragmentActivity implements SettingsFragment.Callbacks, SubSettingsListFragment.Callbacks, ProUserFragment.Callbacks{
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        set background color
        View v = findViewById(R.id.coordinator);
        View root = v.getRootView();
        root.setBackgroundColor(getResources().getColor(R.color.settings_background_blue_dark));

//        Put List sorting on screen if there is a detail_fragment_container in the layout
        if (findViewById(R.id.detail_fragment_container) != null) {
            Fragment newDetail = new SubSettingsListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
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
//            Show options for list
            if (settingsObject.getmTitle().equals(getResources().getString(R.string.settings_list_title))) {
                newDetail = new SubSettingsListFragment();
            } else if (settingsObject.getmTitle().equals(getResources().getString(R.string.settings_prouser_title))) {
                Log.d("cl_SettingsAc ", settingsObject.getmTitle());
                newDetail = new ProUserFragment();
            }
            else {
                newDetail = new SubSettingsListFragment();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();

        }
    }

    public void onSubSettingsUpdatedList() {
        SettingsFragment listFragment = (SettingsFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    public void onSubSettingsUpdatedProUser() {
        SettingsFragment listFragment = (SettingsFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
