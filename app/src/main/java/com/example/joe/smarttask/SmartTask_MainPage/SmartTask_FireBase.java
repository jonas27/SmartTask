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
 * Created by us.
 * Singleton class to manage all connections (Push and Pull) to FireBase
 * Use only protected methods except for pushing Sign Up data.
 */

public class SmartTask_FireBase extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "current";
    private static SmartTask_FireBase smartTask_fireBase;
    private Context context;
    // [Start declare Firebase Auth and Auth listener]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    // [End declare Firebase auth]


    private SmartTask_FireBase(Context context) {
        this.context = context;

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static SmartTask_FireBase fireBase(Context context) {
        if (smartTask_fireBase == null) {
            smartTask_fireBase = new SmartTask_FireBase(context);
        }
        return smartTask_fireBase;
    }

    protected void push2() {

    }

    protected void push() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("3255");
        Log.d(TAG, "Value is: " + mAuth.getCurrentUser().getUid());
    }

}
