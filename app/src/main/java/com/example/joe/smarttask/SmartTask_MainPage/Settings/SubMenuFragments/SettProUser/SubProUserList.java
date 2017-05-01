package com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments.SettProUser;

import android.content.Context;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments.SettList.SubSettingsListObject;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 01/05/2017.
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
