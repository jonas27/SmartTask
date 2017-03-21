package com.example.joe.smarttask.SmartTask_MainPage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

public class SmartTask_FireBase extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "current";
    //private static final String TAG = "SmartTask_FireBase";
    // Singleton object of class itself (static --> Garbage collector wont delete it)
    private static SmartTask_FireBase smartTask_fireBase;
    private Context context;
    // [Start declare Firebase Auth, Auth listener, Database and User]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String uid;
    // [End declare Firebase auth]


    private SmartTask_FireBase(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

    }

    //  static factory method for singleton
    public static SmartTask_FireBase fireBase(Context context) {
        if (smartTask_fireBase == null) {
            smartTask_fireBase = new SmartTask_FireBase(context);
        }
        return smartTask_fireBase;
    }

    protected void addNewTask() {
        
    }

    protected void push(String action) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("User/"+user.getUid());
        switch (action) {
            case "CreateUser":
                myRef.child("email").setValue("email@whencreating.com");

                //myRef.setValue("Yo");
                Log.d(TAG, "Value is: " + mAuth.getCurrentUser().getUid());
                break;
            case "CreateProfile":
                database = FirebaseDatabase.getInstance();

                myRef = database.getReference("User/"+user.getUid());
                myRef.child("profile/pid").setValue("1");
                myRef.child("profile/picture").setValue("linktopic");
                myRef.child("profile/pincode").setValue("1234");
                myRef.child("profile/preferences").setValue("12341234");
                myRef.child("profile/privilgies").setValue("1");
                myRef.child("profile/score").setValue("0");

                break;
            default: Log.d(TAG,"Invalid action");
                break;
        }
        Log.d("asdasd","pushing "+user.getUid());

    }

}
