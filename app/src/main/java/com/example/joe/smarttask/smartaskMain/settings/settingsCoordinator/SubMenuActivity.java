package com.example.joe.smarttask.smartaskMain.settings.settingsCoordinator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.smartaskMain.settings.settingsCoordinator.listSettings.SubSettingsListFragment;
import com.example.joe.smarttask.smartaskMain.settings.settingsCoordinator.notificationSettings.NotificationFragment;
import com.example.joe.smarttask.smartaskMain.settings.settingsCoordinator.proUserSettings.ProUserFragment;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SingleFragmentActivity;

/**
 * Created by joe on 27/04/2017.
 */

public class SubMenuActivity extends SingleFragmentActivity implements SubSettingsListFragment.Callbacks, ProUserFragment.Callbacks {

    //Specify parameter for Intent
    public static final String SETTINGS_OPTION = "Settings_Option";

    private Toolbar toolbar;

    private Context sContext;

    public static Intent newIntent(Context packageContext, String mTitle) {
        Intent intent = new Intent(packageContext, SubMenuActivity.class);
        intent.putExtra(SETTINGS_OPTION, mTitle);
        return intent;
    }


//    this is for phones only as fragments get added to this activity and don't need a new host
//    method opens new activity with one subsettings fragment as only client/view
    @Override
    protected Fragment createFragment() {
        sContext = getAppContext();
//        initialise toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Get string from Intent to determine which fragment should be opened
        String mTitle = (String) getIntent().getSerializableExtra(SETTINGS_OPTION);

        if (mTitle.equals(getResources().getString(R.string.settings_list_title))) {
            return new SubSettingsListFragment();
        } else if (mTitle.equals(getResources().getString(R.string.settings_notifications_title))) {
            return new NotificationFragment();
        } else if (mTitle.equals(getResources().getString(R.string.settings_prouser_title))) {
            return new ProUserFragment();
        }
        else {
            return new SubSettingsListFragment();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onSubSettingsUpdatedList() {

    }

    @Override
    public void onSubSettingsUpdatedProUser() {

    }
}
