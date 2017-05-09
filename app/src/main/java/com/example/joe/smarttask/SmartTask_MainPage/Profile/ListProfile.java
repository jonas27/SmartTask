package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.content.Context;
import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.List.ListFragment;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
    private static DataSnapshot sProfileSnapshot;
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
        sProfileSnapshot = mProfileSnapshot;
        if (sProfileSnapshot.hasChildren()) {
            createList();
        }
//        createList();
    }

    /**
     * This method is called by {@link FireBase} and creates List of profiles
     * It then calls the recycler view in {@link ListFragment} to update itslef
     * Static methods are used to ease the call backs from the OnDataChangeListener in {@link FireBase}
     */
    private static void createList() {
//        addUserPlaceholder();
        if (sProfileSnapshot != null) {
            Map<String, ProfileObject> ProfilesMap = new HashMap<>();
            for (Iterator<DataSnapshot> i = sProfileSnapshot.getChildren().iterator(); i.hasNext(); ) {
                ProfileObject profile = new ProfileObject();
                ProfilesMap.put(i.next().getKey(), profile);
            }
sPlist.clear();
            Log.d(TAG, String.valueOf(sProfileSnapshot.getChildrenCount()));
            for (Iterator<DataSnapshot> i = sProfileSnapshot.getChildren().iterator(); i.hasNext(); ) {
                DataSnapshot current = i.next();
                ProfileObject mProfile = ProfilesMap.get(current.getKey());
                mProfile = current.getValue(ProfileObject.class);
                mProfile.setPid(current.getKey());
                sPlist.add(mProfile);
            }
            Log.d(TAG + "profiles size ", String.valueOf(ProfilesMap.size()));
            Log.d(TAG + "profiles size ", ""+sPlist.size());
        }
    }

    //    getter Method for List of profiles
    public static ArrayList<ProfileObject> getProfileList() {
        return sPlist;
    }

    public static ProfileObject getProfile(String mProfileId) {
        Log.d(TAG, "SPlist size  " + Integer.toString(sPlist.size()));
        Log.d(TAG, "SPlist user id  " + sPlist.get(0).getPid());
        for (ProfileObject t : sPlist) {
            if (t.getPid().equals(mProfileId)) {
                return t;
            }
        }
        return null;
    }

}

