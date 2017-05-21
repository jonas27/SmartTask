package com.smarttask17.joe.smarttask.smartaskMain.settings.settingsCoordinator.proUserSettings;

import android.content.Context;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Pro user list
 */

public class SubProUserList {
    private static List<SubProUserObject> list;

    private static Context sContext;

    public static void setsContext(Context context) {
        sContext = context;
    }

    public static List<SubProUserObject> getList() {
        if (list == null) {
            createList();
        }
        return list;
    }

    public static void createList() {
        list = new ArrayList<>();
        list.add(getObject1());
    }

    public static SubProUserObject getObject1() {
        SubProUserObject o = new SubProUserObject();
        o.setTitle(sContext.getResources().getString(R.string.settings_prouser_title));
        o.setDescription(sContext.getResources().getString(R.string.subsettings_prouser_description));
        o.setProUser(SharedPrefs.getProUser());
        return o;
    }



    public static SubProUserObject getSubProUserObject(int position) {
        return list.get(position);
    }

    private void SubSettingsListList() {
    }


}
