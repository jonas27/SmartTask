package com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments.SettList.ListFragment;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 27/04/2017.
 */

public class SubMenuActivity extends SingleFragmentActivity {

    //Specify parameter for Intent
    public static final String SETTINGS_OPTION = "Settings_Option";

    private Toolbar toolbar;

    private Context sContext;

    public static Intent newIntent(Context packageContext, String mTitle) {
        Intent intent = new Intent(packageContext, SubMenuActivity.class);
        intent.putExtra(SETTINGS_OPTION, mTitle);
        return intent;
    }


    @Override
    protected Fragment createFragment() {

        sContext = getAppContext();

//        initialise toolbar
        toolbar = (Toolbar) findViewById(R.id.fragment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Get string from Intent and
        String mTitle = (String) getIntent().getSerializableExtra(SETTINGS_OPTION);


        if (mTitle.equals(getResources().getString(R.string.settings_list_title))) {
            return new ListFragment();
        } else if (mTitle.equals(getResources().getString(R.string.settings_notifications_title))) {
            return new NotificationFragment();
        } else {
            return new ListFragment();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}
