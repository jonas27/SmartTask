package com.smarttask17.joe.smarttask.permissionRequester;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * One of the worst fragments....
 * Helper fragment for requesting information only. Afterwards it finishes its hosting activity.
 * You are born to die!
 *
 * 100% by THE SMARTTASK TEAM
 */

public class PermissionRequesterFragment extends Fragment {

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
        int permsRequestCode = 200;
        requestPermissions(perms, permsRequestCode);
        getActivity().finish();
    }
}
