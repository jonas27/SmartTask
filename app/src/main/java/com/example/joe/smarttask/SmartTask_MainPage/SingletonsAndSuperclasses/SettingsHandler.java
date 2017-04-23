package com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses;

/**
 * Created by joe on 23/04/2017.
 */

public class SettingsHandler {


    /**
     * List will be sorted by datetime
     */
    public final static int LIST_SORTED_DATE = 1;

    /**
     * List will be sorted by priority and sub-sorted by datetime
     */
    public final static int LIST_SORTED_PRIORITY = 2;
    public static final String home = "";
    private static final SettingsHandler ourInstance = new SettingsHandler();

    private SettingsHandler() {
    }

    public static SettingsHandler getInstance() {
        return ourInstance;
    }

}
