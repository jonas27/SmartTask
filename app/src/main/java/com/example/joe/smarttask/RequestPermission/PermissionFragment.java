package com.example.joe.smarttask.RequestPermission;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by joe on 09/05/2017.
 */

public class PermissionFragment extends Fragment {

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
        int permsRequestCode = 200;
        requestPermissions(perms, permsRequestCode);
        getActivity().finish();
    }
}
