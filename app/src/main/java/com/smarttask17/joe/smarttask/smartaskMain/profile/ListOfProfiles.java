package com.smarttask17.joe.smarttask.smartaskMain.profile;

import android.content.Context;
import android.util.Log;

import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.FireBase;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Singleton for handling the list of all profiles which is then displayed in the List fragment
 * It gets a  <p><font color="green"> FireBase {@link DataSnapshot} </font> from the {@link FireBase} class when the this class is first created or on data change (on the server)
 * The information from this DataSnapshot is then put into single profileObject Objects {@link ProfileObject} which are all stored in a ArrayList
 * <p><font color="red"> This class has a static method
 */

public class ListOfProfiles {

    //TAG for Logs
    private static final String TAG = "CL_ListProfile";

    private static ListOfProfiles sListOfProfiles;
    private static ArrayList<ProfileObject> sPlist;
    private static DataSnapshot sDataSnapshot;
    private SharedPrefs sharedPrefs;
    private Context context;


    /**
     * Creates a new ArrayList where all the profileObject object are going to be stored
     *
     * @param context global information on application environment
     */
    public ListOfProfiles(Context context) {

    }

    /**
     * Static factory method for singleton
     *
     * @return THE single Object of this class
     */
    public static ListOfProfiles list() {
        return sListOfProfiles;
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
        Log.d(TAG, "list size " + sPlist.size());
        for (int c = 0; c < sPlist.size(); c++) {
            if (sPlist.get(c).getPid().equals(mProfileId)) {
                return sPlist.get(c);
            }
        }
        return null;
    }

    public static ProfileObject getProfileByName(String mProfileName) {
        ProfileObject p;
        for (Iterator<ProfileObject> itr = sPlist.iterator(); itr.hasNext();){
            p=itr.next();
            if (p.getPname().equals(mProfileName)) {
                return p;
            }
        }
        return null;
    }

}
