package com.example.joe.smarttask.SmartTask_MainPage.Settings;


public class SettingsObject {


    //    [Start: Define Variables which are shared among all objects]
    private String mTitle;
    private String mDescription;
    private int mNumberInList;


    private int mNotificationSettings;

//    [End: Define Variables which are shared among all objects]

    //    Don't allow inheritance of class
    private SettingsObject() {
    }

    protected static SettingsObject getNewSettingsObject() {
        return new SettingsObject();
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmNumberInList() {
        return mNumberInList;
    }

    public void setmNumberInList(int mNumberInList) {
        this.mNumberInList = mNumberInList;
    }

    public int getmNotificationSettings() {
        return mNotificationSettings;
    }

    public void setmNotificationSettings(int mNotificationSettings) {
        this.mNotificationSettings = mNotificationSettings;
    }


}
