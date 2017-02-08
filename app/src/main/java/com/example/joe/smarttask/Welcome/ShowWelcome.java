package com.example.joe.smarttask.Welcome;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by joe on 08/02/2017.
 */

public class ShowWelcome {

    public static final String PREFS_NAME = "WelcomeDialog";
    private static final String SHOW_WELCOME = "ShowWelcomeDialog";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public ShowWelcome(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public void setSharedPreferencesWelcome(boolean showWelcome) {
        editor.putBoolean(SHOW_WELCOME, showWelcome);
        editor.commit();
    }

    //returns the boolean value of SHOW_WELCOME in file PREFS_NAME (if no value it returns true)
    public boolean getSharedPrefencesWelcome() {
        return sharedPreferences.getBoolean(SHOW_WELCOME, true);
    }


}
