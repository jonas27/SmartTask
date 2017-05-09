package com.example.joe.smarttask.RequestPermission;

import android.support.v4.app.Fragment;

import com.example.joe.smarttask.ChooseProfile.ChooseProfileFragment;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SingleFragmentActivity;

/**
 * Created by joe on 09/05/2017.
 */

public class PermissionActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PermissionFragment();
    }
}
