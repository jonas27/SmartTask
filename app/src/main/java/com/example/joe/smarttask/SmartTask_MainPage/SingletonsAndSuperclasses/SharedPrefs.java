package com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Intro into our app
 * layout is inflated
 */

public class SharedPrefs {

    public static final String PREFS_NAME = "MyPrefs";
    private static final String SHOW_WELCOME = "ShowIntroDialog";
    private static final String LIST_ORDER = "ListSort";
    private static final String NOTIFICATION_LEVEL = "NotificationLevel";
    private static final String LANGUAGE = "Language";
    private static final String REWARD_ON_OFF = "RewardOnOff";
    private static final String PRO_USER = "ProUser";
    private static final String FEEDBACK = "Feedback";
    private static final String SHOW_PAST_ITEMS = "ShowPastItems";
    private static final String CURRENT_PROFILE = "CurrentProfile";
    private static final String CURRENT_USER = "CurrentUser";
    public static SharedPreferences.Editor editor;
    private static SharedPrefs sharedPrefs;
    private static SharedPreferences sharedPreferences;
    private static Context context;

    private int preferredOrder;

    //constructor opens (or creates) shared preference file in editor (0 means it is private; can only be accessed by the calling application)
    private SharedPrefs(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public static SharedPrefs getSharedPrefs(Context context) {
        if (sharedPrefs == null) {
            sharedPrefs = new SharedPrefs(context);
        }
        return sharedPrefs;
    }

    public static int getPreferredOrder() {
         return sharedPreferences.getInt(LIST_ORDER, 0);
    }

    public static void setPreferredOrder(int listOrder) {
        editor.putInt(LIST_ORDER, listOrder);
        editor.commit();
    }

    public static String getCurrentProfile() {
        return sharedPreferences.getString(CURRENT_PROFILE, "");
    }

    public static void setCurrentProfile(String id) {
        editor.putString(CURRENT_PROFILE, id);
        editor.commit();
    }

    public static String getCurrentUser() {
        return sharedPreferences.getString(CURRENT_USER, "");
    }

    public static void setCurrentUser(String name) {
        editor.putString(CURRENT_USER, name);
        editor.commit();
    }

    public static boolean getShowPastItems() {
        return sharedPreferences.getBoolean(SHOW_PAST_ITEMS, true);
    }

    public static void setShowPastItems(boolean b) {
        editor.putBoolean(SHOW_PAST_ITEMS, b);
        editor.commit();
    }

    public static int getNotificationLevel() {
        return sharedPreferences.getInt(NOTIFICATION_LEVEL, 0);
    }

    public static void setNotificationLevel(int i) {
        editor.putInt(NOTIFICATION_LEVEL, i);
        editor.commit();
    }

    public static int getPreferredLanguage() {
        return sharedPreferences.getInt(LANGUAGE, 0);
    }

    public static void setPreferredLanguage(int i) {
        editor.putInt(LANGUAGE, i);
        editor.commit();
    }

    public static int getRewardOnOff() {
        return sharedPreferences.getInt(REWARD_ON_OFF, 0);
    }

    public static void setRewardOnOff(int i) {
        editor.putInt(REWARD_ON_OFF, i);
        editor.commit();
    }

    public static boolean getProUser() {
        return sharedPreferences.getBoolean(PRO_USER, true);
    }

    public static void setProUser(boolean b) {
        editor.putBoolean(PRO_USER, b);
        editor.commit();
    }


    //sets if Intro should show
    public static void setSharedPreferencesIntro(boolean skipTurorial) {
        editor.putBoolean(SHOW_WELCOME, skipTurorial);
        editor.commit();
    }

    //returns the boolean value of SHOW_WELCOME in file PREFS_NAME (if no value it returns true)
    public static boolean getSharedPrefencesIntro() {
        return sharedPreferences.getBoolean(SHOW_WELCOME, true);
    }

    public static int getFeedback() {
        return sharedPreferences.getInt(FEEDBACK, 0);
    }

    public static void setFeedback(int i) {
        editor.putInt(FEEDBACK, i);
        editor.commit();
    }

}
