package com.example.joe.smarttask.SmartTask_MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.joe.smarttask.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Delete class --> naming is shit
 */

public class SmartTask_Main_Activity extends FragmentActivity {

    //TAG for Logs
    private static final String TAG = "SmartTask_Main_Activity";
    Button upload;
    // [Start declare Firebase Auth and Auth listener]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    // [End declare Firebase auth]
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        //set's the content (layout)
        setContentView(R.layout.activity_smarttask);

        // initialize_auth
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


        //assign button to upload to firebase
        upload = (Button) findViewById(R.id.tasks);
        upload.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          // Write a message to the database
                                          FirebaseDatabase database = FirebaseDatabase.getInstance();
                                          DatabaseReference myRef = database.getReference("message");


                                          myRef.setValue("Hello, World!");
                                      }
                                  }
        );


        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new SmartTask_ListTasks_Fragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        if (fragment == null) {
            fragment = new SmartTask_ListTasks_Fragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }


}
