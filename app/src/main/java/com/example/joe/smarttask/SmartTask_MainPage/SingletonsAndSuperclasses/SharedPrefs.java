package com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Intro into our app
 * layout is inflated
 */

public class SharedPrefs {

    private static final String PREFS_NAME = "IntroDialog";
    private static final String SHOW_WELCOME = "ShowIntroDialog";
    private static final String LIST_ORDER = "ListSort";

    private static SharedPrefs sharedPrefs;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

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

    //sets if Intro should show
    public void setSharedPreferencesIntro(boolean skipTurorial) {
        editor.putBoolean(SHOW_WELCOME, skipTurorial);
        editor.commit();
    }

    //returns the boolean value of SHOW_WELCOME in file PREFS_NAME (if no value it returns true)
    public boolean getSharedPrefencesIntro() {
        return sharedPreferences.getBoolean(SHOW_WELCOME, true);
    }

    public void setSharedPreferencesListSort(int listOrder) {
        editor.putInt(LIST_ORDER, listOrder);
        editor.commit();
    }

    public int getSharedPrefencesListSort() {
        return sharedPreferences.getInt(LIST_ORDER, 1);
    }
}
