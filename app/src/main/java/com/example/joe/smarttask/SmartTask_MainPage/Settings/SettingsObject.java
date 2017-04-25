package com.example.joe.smarttask.SmartTask_MainPage.Settings;

/**
 * Create 100% by us.
 * Defines the objects inside settings (the subcategories: i.e. list behaviour, language etc.)
 * TODO: Wire this class up with sharedPrefs
 */
public class SettingsObject {


    //    [Start: Define Variables which are shared among all objects]
    private String mTitle;
    private String mDescription;
    private int mNumberInList;
    private int mNotificationSettings;
    private int mRewardOnOff;

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

    public int getmRewardOnOff() {
        return mRewardOnOff;
    }

    public void setmRewardOnOff(int mRewardOnOff) {
        this.mRewardOnOff = mRewardOnOff;
    }


}
