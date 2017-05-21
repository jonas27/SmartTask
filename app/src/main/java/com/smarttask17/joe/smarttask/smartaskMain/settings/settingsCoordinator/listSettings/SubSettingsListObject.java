package com.smarttask17.joe.smarttask.smartaskMain.settings.settingsCoordinator.listSettings;


import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;

/**
 * List settings object
 */

public class SubSettingsListObject {

//    for settings
    public static final int ORDER_BY_DATE = 0;
    public static final int ORER_BY_PRIORITY = 1;
    public static final int NO_ORDER_ITEM = -1;
    private static int preferredOrder;
//    private static int preferredNotification;
//    private static int preferredLanguage;
//    private static int preferredReward;
    private static boolean showPastItems;

//    for text
    private String title;
    private String description;
    private int order;


    //    Class needs to be initialized
//    If not static methods will return default values (int 0 etc.) or even throw an exception
    public SubSettingsListObject() {
        this.preferredOrder = SharedPrefs.getPreferredOrder();
        this.showPastItems = SharedPrefs.getShowPastItems();
//        this.preferredNotification = SharedPrefs.getNotificationLevel();
//        this.preferredLanguage = SharedPrefs.getPreferredLanguage();
//        this.preferredReward = SharedPrefs.getRewardOnOff();
    }


    public static int getPreferredOrder() {
        return preferredOrder;
    }

    public static void setPreferredOrder(int preferredOrder) {
        SharedPrefs.setPreferredOrder(preferredOrder);
        SubSettingsListObject.preferredOrder = preferredOrder;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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


    public boolean isShowPastItems() {
        showPastItems = SharedPrefs.getShowPastItems();
        return showPastItems;
    }

    public void setShowPastItems(boolean showPastItems) {
        SharedPrefs.setShowPastItems(showPastItems);
        SubSettingsListObject.showPastItems = showPastItems;
    }
}
