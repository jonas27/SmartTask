package com.example.joe.smarttask.SmartTask_MainPage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by us (with googles firebase methods)
 * Singleton class to manage all connections (Push and Pull) to FireBase
 * Use only protected methods except for pushing Sign Up data.
 */

public class FireBase extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "CL_FireBase";

    //private static final String TAG = "SmartTask_FireBase";
    // Singleton object of class itself
    private static FireBase sFireBase;


    private Context context;

    private static ListTask mListTask;

    // [Start declare Firebase variables]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;
    private static DataSnapshot sDataSnapshot;
    // [End declare Firebase variables]


    private FireBase(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        pull();
    }


    //  static factory method for singleton
    public static FireBase fireBase(Context context) {
        if (sFireBase == null) {
            sFireBase = new FireBase(context);
        }
        return sFireBase;
    }

    public void createTask(TaskObject taskObject) {
        createNewTask(taskObject);
    }

        private void createNewTask(TaskObject taskObject){
            String key = mPostReference.child("User/" + user.getUid() + "/task").push().getKey();
            mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("task");
            Log.d(TAG, key);
            mPostReference.child(key).setValue(taskObject);
        }


    private void push(Map<String, String> map, String root) {
        /*
            Information should be pushed as a map with String destination on firebase server,value.
         */
        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = mDatabase.getReference("User/" + user.getUid());

        Iterator it = map.entrySet().iterator();
        String rootElement = null;
        String rootdistanation = root;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Log.d(TAG, "key: " + pair.getKey() + "  val   " + pair.getValue());
            if (rootElement == null) {
                rootElement = myRef.push().getKey();
            }
            if (rootElement == null) {
                myRef.child(pair.getKey().toString()).setValue(pair.getValue());
            } else {
                myRef.child(rootdistanation).child(rootElement).child(pair.getKey().toString()).setValue(pair.getValue());
            }
            it.remove();
        }
    }

    /**
     * Methods from here fetch/pull data from server
     */
    public DataSnapshot getDataSnapshot() {
        return sDataSnapshot;
    }

    private void pull() {
        Log.d(TAG, mAuth.getCurrentUser().toString());
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("task");
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot mDataSnapshot) {
                callback(mDataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG + "Err", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);
    }

    private void callback(DataSnapshot mDataSnapshot) {
        ListTask.setDataSnapshot(mDataSnapshot);
    }


}