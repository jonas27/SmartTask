package com.example.joe.smarttask.Welcome;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Creates a file and boolean in that file with setter and getter methods
 * boolean defines if welcome dialog shows (show if true)
 */

public class ShowWelcome {

    public static final String PREFS_NAME = "WelcomeDialog";
    private static final String SHOW_WELCOME = "ShowWelcomeDialog";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    //constructor opens (or creates) shared preference file in editor (0 means it is private; can only be accessed by the calling application)
    public ShowWelcome(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        editor = sharedPreferences.edit();
    }

    //sets if Welcome should show
    public void setSharedPreferencesWelcome(boolean skipTurorial) {
        editor.putBoolean(SHOW_WELCOME, skipTurorial);
        editor.commit();
    }

    //returns the boolean value of SHOW_WELCOME in file PREFS_NAME (if no value it returns true)
    public boolean getSharedPrefencesWelcome() {
        return sharedPreferences.getBoolean(SHOW_WELCOME, true);
    }


}
