package com.example.joe.smarttask.SmartTask_MainPage.Settings;

import android.content.Context;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 100% by us.
 * 1. Defines all objects for the specific category, e.g. "reward system".
 * 2. Provides a getter method for all settings items
 * 3. Write preferences into shared preferences
 * 4. This class is not serializable so it can't be uploaded to firebase
 */

public class SettingsList {

    //    [Start: Ordering of objects in List (RecyclerView)]
    public static final int LIST_POSITION = 0;
    public static final int NOTIFICATIONS_POSITION = 1;
    public static final int LANGUAGE_POSITION = 2;
    public static final int REWARD_POSITION = 3;
    public static final int FEEDBACK_POSITION = 4;
    public static final int ABOUT_POSITION = 5;
    //    [Start: define list order]
    public static final int ORDER_BY_DATE = 0;
    public static final int ORDER_BY_PRIORITY = 1;
    //    [Start: define notification levels]
    public static final int NOTIFICATION_ALL = 0;
    public static final int NOTIFICATION_ONLY_EMAIL = 1;
    public static final int NOTIFICATION_ONLY_PUSH = 2;

    //    [End: Ordering of objects in List (RecyclerView)]
    public static final int NOTIFICATION_NO = 1;
    private static final String TAG = "CL_SettL";
    //    [End: define list order]
    private static List<SettingsObject> sList;
    private static SettingsList sSettingsList;
    private static Context sContext;
    private static SharedPrefs sharedPrefs;
    //    [End: define list order]


    /**
     * private Constructors means no inheritance and no composition
     */
    private SettingsList() {
        sContext = SMMainActivity.getAppContext();
        sharedPrefs = SharedPrefs.getSharedPrefs(SMMainActivity.getAppContext());
    }


    //    Class can fully be static --> no objects retrival needed
    protected static SettingsList getSettingsList() {
        if (sSettingsList == null) {
            sSettingsList = new SettingsList();
            sList = new ArrayList<>();
            sSettingsList.createList();
            return sSettingsList;
        }
        return sSettingsList;
    }

    /**
     * Attention: could return a null List
     * Make sure to first get the Object reference
     */
    protected static List<SettingsObject> getList() {
        return sList;
    }

    protected void createList() {
        sList.add(0, createListOption());
        sList.add(1, createNotificationOption());

    }

    //    Object for List behaviour
    private SettingsObject createListOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_list_title));
        if (sharedPrefs.getSharedPrefencesListSort() == ORDER_BY_DATE) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_list_sort_date));
        } else if (sharedPrefs.getSharedPrefencesListSort() == ORDER_BY_PRIORITY) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_list_sort_priority));
        }
        listSettings.setmNumberInList(LIST_POSITION);
        return listSettings;
    }

    //    Object for Notification
    private SettingsObject createNotificationOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_notifications_title));
        if (sharedPrefs.getPreferenceLevel() == NOTIFICATION_ALL) {
            listSettings.setmTitle(sContext.getResources().getString(R.string.settings_list_sort_date));
        } else if (sharedPrefs.getSharedPrefencesListSort() == ORDER_BY_PRIORITY) {
            listSettings.setmTitle(sContext.getResources().getString(R.string.settings_list_sort_priority));
        }
        listSettings.setmNumberInList(LIST_POSITION);
        return listSettings;
    }

    public int getListSize() {
        return sList.size();
    }


}
