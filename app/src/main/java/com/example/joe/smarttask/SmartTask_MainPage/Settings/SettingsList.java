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
    public static final int PRO_POSITION = 4;
    public static final int FEEDBACK_POSITION = 5;
    public static final int ABOUT_POSITION = 6;
    //    [End: Ordering of objects in List (RecyclerView)]


    //    [Start: define list order]
    public static final int ORDER_BY_DATE = 0;
    public static final int ORDER_BY_PRIORITY = 1;
    //    [End: define list order]

    //    [Start: define notification levels]
    public static final int NOTIFICATION_ALL = 0;
    public static final int NOTIFICATION_ONLY_EMAIL = 1;
    public static final int NOTIFICATION_ONLY_PUSH = 2;
    //    [End: define notifiaction levels]

    //    [Start: define reward on off -> push to firebase!!!]
    public static final int REWARD_ON = 0;
    public static final int REWARD_OFF = 1;
    //    [End: define rewards on off]

    //    [Start: define reward on off -> push to firebase!!!]
    public static final int FEEDBACK_ON = 0;
    public static final int FEEDBACK_OFF = 1;
    //    [End: define rewards on off]

    //    [Start: define pro user on off -> push to firebase!!!]
    public static final boolean PRO_USER=true;
    //    [End: define pro user on off]

    public static final int NOTIFICATION_NO = 1;
    private static final String TAG = "CL_SettL";

    private static List<SettingsObject> sList;
    private static SettingsList sSettingsList;
    private static Context sContext;
    private static SharedPrefs sharedPrefs;



    /**
     * Helper class (private Constructors means no inheritance and no composition)
     */
    private SettingsList() {
    }


    /**
     * Attention: could return a null List
     * Make sure to first get the Object reference
     */
    protected static List<SettingsObject> getList() {
        sContext = SMMainActivity.getAppContext();
        sharedPrefs = SharedPrefs.getSharedPrefs(SMMainActivity.getAppContext());
        sList = new ArrayList<>();
        createList();
        return sList;
    }

    private static void createList() {
        sList.add(0, createListOption());
        sList.add(1, createNotificationOption());
        sList.add(2, createLanguageOption());
        sList.add(3, createRewardOption());
        sList.add(4, createProUserOption());
        sList.add(5, createFeedbackOption());
        sList.add(6, createAboutOption());

    }

    //    Object for List behaviour
    private static SettingsObject createListOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_list_title));
        if (SharedPrefs.getPreferredOrder() == ORDER_BY_DATE) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_list_sort_date));
        } else if (sharedPrefs.getPreferredOrder() == ORDER_BY_PRIORITY) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_list_sort_priority));
        }
        listSettings.setmNumberInList(LIST_POSITION);
        return listSettings;
    }

    //    Object for Notification
    private static SettingsObject createNotificationOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_notifications_title));
        if (SharedPrefs.getNotificationLevel() == NOTIFICATION_ALL) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_notifications_all));
        } else if (SharedPrefs.getPreferredOrder() == NOTIFICATION_ONLY_EMAIL) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_notifications_email));
        } else if (SharedPrefs.getPreferredOrder() == NOTIFICATION_ONLY_PUSH) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_notifications_push));
        } else if (SharedPrefs.getPreferredOrder() == ORDER_BY_PRIORITY) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_notifications_none));
        }
        listSettings.setmNumberInList(NOTIFICATIONS_POSITION);
        return listSettings;
    }

    //    Object for Language
    private static SettingsObject createLanguageOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_language_title));
        listSettings.setmNumberInList(LANGUAGE_POSITION);
        return listSettings;
    }

    //    Object for Reward
    private static SettingsObject createRewardOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_reward_title));
        if (SharedPrefs.getRewardOnOff() == REWARD_ON) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_reward_enabled));
        } else if (SharedPrefs.getPreferredOrder() == REWARD_OFF) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_reward_disabled));
        }
        listSettings.setmNumberInList(REWARD_POSITION);
        return listSettings;
    }

    //    Object for Pro User
    private static SettingsObject createProUserOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_prouser_title));
        if (SharedPrefs.getProUser() == PRO_USER) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_prouser_on));
        } else {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_prouser_off));
        }
        listSettings.setmNumberInList(PRO_POSITION);
        return listSettings;
    }

    //    Object for Feedback
    private static SettingsObject createFeedbackOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_feedback_title));
        if (sharedPrefs.getFeedback() == FEEDBACK_ON) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_feedback_on));
        } else {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_feedback_off));
        }
        listSettings.setmNumberInList(FEEDBACK_POSITION);
        return listSettings;
    }

    //    Object for About
    private static SettingsObject createAboutOption() {
        SettingsObject listSettings = SettingsObject.getNewSettingsObject();
        listSettings.setmTitle(sContext.getResources().getString(R.string.settings_about_title));
        if (sharedPrefs.getFeedback() == FEEDBACK_ON) {
            listSettings.setmDescription(sContext.getResources().getString(R.string.settings_about_version));
        }
        listSettings.setmNumberInList(ABOUT_POSITION);
        return listSettings;
    }


    public static int getListSize() {
        return sList.size();
    }


}
