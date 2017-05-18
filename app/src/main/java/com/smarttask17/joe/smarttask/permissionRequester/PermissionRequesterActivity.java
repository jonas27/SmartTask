package com.smarttask17.joe.smarttask.permissionRequester;

import android.support.v4.app.Fragment;

import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SingleFragmentActivity;

/**
 * Hoster class of fragment
 *
 * 100% by us ;D
 */

public class PermissionRequesterActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PermissionRequesterFragment();
    }
}
