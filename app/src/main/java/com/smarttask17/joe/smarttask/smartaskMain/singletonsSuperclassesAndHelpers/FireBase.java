package com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.smarttask17.joe.smarttask.smartaskMain.list.ListOfTasks;
import com.smarttask17.joe.smarttask.smartaskMain.messenger.MessageObject;
import com.smarttask17.joe.smarttask.smartaskMain.profile.ListOfProfiles;
import com.smarttask17.joe.smarttask.smartaskMain.profile.ProfileObject;
import com.smarttask17.joe.smarttask.smartaskMain.task.TaskObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by us (with googles firebase methods)
 * Singleton class to manage all connections (Push and Pull) to FireBase
 * Use only protected methods except for pushing Sign Up data.
 */

public class FireBase extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "CL_FireBase";

    private static final String dir = "/storage/emulated/0/smarttask/";
    public static boolean isAdmin;
    //private static final String TAG = "SmartTask_FireBase";
    // Singleton object of class itself
    private static FireBase sFireBase;
    private static ListOfTasks mListOfTasks;
    private static DataSnapshot sDataSnapshot;
    private static DataSnapshot sPDataSnapshot;
    // [Start declare Firebase variables]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private ValueEventListener postListener;
    private ValueEventListener PpostListener;
    private DatabaseReference mPostReference;
    private DatabaseReference mPostReference2;
    private DatabaseReference mPostReferenceMessage;
    // [End declare Firebase variables]


    private FireBase(Context context) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
//        pull();
        Log.d(TAG,"Starting up firebase");
        pull2();
    }


    //  static factory method for singleton
    public static FireBase fireBase(Context context) {
        if (sFireBase == null) {
            sFireBase = new FireBase(context);
        }
        return sFireBase;
    }

    /**
     * Methods from here fetch/pull data from server
     */

//    [start: create a new Task]
    public void createTask(TaskObject mTaskObject) {
        createNewTask(mTaskObject);
    }

    private void createNewTask(TaskObject mTaskObject) {
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("task");
        if(mTaskObject.getId().equals("")){
            String key = mPostReference.child("User/" + user.getUid() + "/task").push().getKey();
            mTaskObject.setId(key);
            mPostReference.child(key).setValue(mTaskObject);
        }
        else{
            mPostReference.child(mTaskObject.getId()).setValue(mTaskObject);
        }
    }

    //    [start: create a new Task]
    public void createMessage(MessageObject mMessageObject) {
        mPostReferenceMessage = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("messages");
        mPostReferenceMessage.child(Long.toString(mMessageObject.getDateTime())).setValue(mMessageObject);
    }


    /**
     * Methods from here fetch/pull data from server
     */
    public DataSnapshot getDataSnapshot() {
        return sDataSnapshot;
    }


//     [start: create a new Profile]
    public void createProfile(ProfileObject mProfileObject) {
        createNewProfile(mProfileObject);
    }

    private void createNewProfile(ProfileObject mProfileObject) {
        mPostReference2 = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("profile");
//        Log.d(TAG, key);
        if(mProfileObject.getPid().equals("")){
            String key = mPostReference2.child("User/" + user.getUid() + "/profile").push().getKey();
            Log.d(TAG,"key "+key);
            mProfileObject.setPid(key);
            mPostReference2.child(key).setValue(mProfileObject);
            if(ListOfProfiles.getProfileList().size()==0){
                SharedPrefs.setCurrentProfile(key);
                SharedPrefs.setCurrentUser(mProfileObject.getPname());
            }
        }
        else{
            mPostReference2.child(mProfileObject.getPid()).setValue(mProfileObject);
        }
    }
    public DataSnapshot getDataSnapshot2() {
        return sDataSnapshot;
    }

    private void pull2() {
        Log.d(TAG, mAuth.getCurrentUser().toString());
        mPostReference2 = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("profile");
        PpostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot mPDataSnapshot) {
                callback2(mPDataSnapshot);
                Log.d(TAG,"Getting profiles"+mPDataSnapshot.getChildren().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG + "Err", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference2.addValueEventListener(PpostListener);
        //       mPostReference2.addValueEventListener(postListener);
    }

    private void callback2(DataSnapshot mPDataSnapshot) {
        ListOfProfiles.setDataSnapshot(mPDataSnapshot);
        }


//        delete a task
    public boolean deleteTask(String id){
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child(("task/")+ id);
        mPostReference.removeValue();
        return false;
    }


    //        [Start: logout]
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        mAuth.signOut();
//        ListOfTasks.getTaskList();
        sFireBase = null;
    }

}