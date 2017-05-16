package com.example.joe.smarttask.smartaskMain.settings.settingsCoordinator.proUserSettings;

import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;

/**
 * Created by joe on 28/04/2017.
 */

public class SubProUserObject {

//    for settings
    private static boolean proUser;

//    for text
    private String title;
    private String description;


    //    Class needs to be initialized
//    If not static methods will return default values (int 0 etc.) or even throw an exception
    public SubProUserObject() {
        this.proUser = SharedPrefs.getProUser();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isProUser() {
        proUser = SharedPrefs.getProUser();
        return proUser;
    }

    public void setProUser(boolean b) {
        SharedPrefs.setProUser(b);
        proUser = b;
    }
}