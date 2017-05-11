package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.content.Context;
import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.List.ListFragment;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.SharedPrefs;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Singleton for handling the list of all profiles which is then displayed in the List fragment
 * It gets a  <p><font color="green"> FireBase {@link DataSnapshot} </font> from the {@link FireBase} class when the this class is first created or on data change (on the server)
 * The information from this DataSnapshot is then put into single profileObject Objects {@link ProfileObject} which are all stored in a ArrayList
 * <p><font color="red"> This class has a static method
 */

public class ListProfile {

    //TAG for Logs
    private static final String TAG = "CL_ListProfile";

    private static ListProfile sListProfile;
    private static ArrayList<ProfileObject> sPlist;
    private static DataSnapshot sDataSnapshot;
    private SharedPrefs sharedPrefs;
    private Context context;


    /**
     * Creates a new ArrayList where all the profileObject object are going to be stored
     *
     * @param context global information on application environment
     */
    public ListProfile(Context context) {

    }

    /**
     * Static factory method for singleton
     *
     * @return THE single Object of this class
     */
    public static ListProfile list() {

        return sListProfile;
    }


    public static void setDataSnapshot(DataSnapshot mProfileSnapshot) {
        sPlist = new ArrayList<>();
        sDataSnapshot = mProfileSnapshot;
        if (sDataSnapshot.hasChildren()) {
            createList();
        }
    }

    /**
     * This method is called by {@link FireBase} and creates List of profiles
     */
    private static void createList() {
//        addUserPlaceholder();
        if (sDataSnapshot != null) {
            for (Iterator<DataSnapshot> i = sDataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                ProfileObject mProfile = i.next().getValue(ProfileObject.class);
                sPlist.add(mProfile);
            }
        }
    }

    //    getter Method for List of profiles
    public static ArrayList<ProfileObject> getProfileList() {
        return sPlist;
    }

    public static ProfileObject getProfile(String mProfileId) {
        Iterator<ProfileObject> itr = sPlist.iterator();
        ProfileObject p;
        for (ProfileObject pp : sPlist) {
            Log.d(TAG, pp.getPname());
            Log.d(TAG, mProfileId);
        }
        for (int c = 0; c < ListProfile.getProfileList().size(); c++) {
            if (ListProfile.getProfileList().get(c).getPid().equals(mProfileId)) {
                return ListProfile.getProfileList().get(c);
            }
        }
        return null;
    }
}
