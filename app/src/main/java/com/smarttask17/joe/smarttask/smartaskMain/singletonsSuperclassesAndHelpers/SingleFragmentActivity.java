package com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.smarttask17.joe.smarttask.R;

/**
 * Created by joe on 05/04/2017.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "CLASS_FragmentActivity";

    private static Context context;

    public static Context getAppContext() {
        return SingleFragmentActivity.context;
    }

    protected void initSingletons() {
    }

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingleFragmentActivity.context = getApplicationContext();

//        set the default layout for the activity the fragment inflates
        setContentView(getLayoutResId());
        initSingletons();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
