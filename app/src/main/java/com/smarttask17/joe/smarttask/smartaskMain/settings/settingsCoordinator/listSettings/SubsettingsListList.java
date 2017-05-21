package com.smarttask17.joe.smarttask.smartaskMain.settings.settingsCoordinator.listSettings;

import android.content.Context;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * List settings options
 */

public class SubsettingsListList {

    private static List<SubSettingsListObject> list;

    private static Context sContext;

    public static void setsContext(Context context) {
        sContext = context;
    }

    public static List<SubSettingsListObject> getList() {
        if (list == null) {
            createList();
        }
        return list;
    }

    public static void createList() {
        list = new ArrayList<>();
        list.add(getObject1());
        list.add(getObject2());
        list.add(getObject3());
    }

    public static SubSettingsListObject getObject1() {
        SubSettingsListObject o = new SubSettingsListObject();
        o.setTitle(sContext.getResources().getString(R.string.subsettings_list_title_date));
        o.setDescription(sContext.getResources().getString(R.string.subsettings_list_description_date));
        o.setOrder(SubSettingsListObject.ORDER_BY_DATE);
        return o;
    }

    public static SubSettingsListObject getObject2() {
        SubSettingsListObject o = new SubSettingsListObject();
        o.setTitle(sContext.getResources().getString(R.string.subsettings_list_title_priority));
        o.setDescription(sContext.getResources().getString(R.string.subsettings_list_description_priority));
        o.setOrder(SubSettingsListObject.ORER_BY_PRIORITY);
        return o;
    }

    public static SubSettingsListObject getObject3() {
        SubSettingsListObject o = new SubSettingsListObject();
        o.setTitle(sContext.getResources().getString(R.string.subsettings_list_title_pastitems));
        o.setDescription(sContext.getResources().getString(R.string.subsettings_list_description_pastitems));
        o.setShowPastItems(SharedPrefs.getShowPastItems());
        o.setOrder(SubSettingsListObject.NO_ORDER_ITEM);
        return o;
    }

    public static SubSettingsListObject getSubSettingsObject(int position) {
        return list.get(position);
    }

    private void SubSettingsListList() {
    }

}
