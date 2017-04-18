package com.example.joe.smarttask.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joe.smarttask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Is for the Email/Password signup in Firebase (Google, Facebook, Twitter etc. logins need no sign up)
 * Class gets 6 variables first Name, second Name, Birthday, Email, User, Password
 * Mostly done by google
 */

public class SignUpActivity extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "SignUpActivity";
    //if bln is wrong the input from the user does not match requirements and no new account will be created
    private static boolean createdNewAccount = false;
    private Intent intent;
    //Buttons and textviews
    private Button signUp;
    private EditText firstName, lastName, birthday, email, userName, password;
    //firebase objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreateSuccesful");
//
//        mAuth = FirebaseAuth.getInstance();
//
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//                // ...
//            }
//        };
//
//        //set's the content (layout)
//        setContentView(R.layout.activity_sign_up);
//
//
//        //assign button clicklistener and set Data
//        signUp = (Button) findViewById(R.id.signupButton);
//        signUp.setOnClickListener(new View.OnClickListener() {
//                                      @Override
//                                      public void onClick(View v) {
//
//                                          sendData();
//                                      }
//                                  }
//        );
//
//        //assign textviews
//        firstName = (EditText) findViewById(R.id.firstName);
//        lastName = (EditText) findViewById(R.id.lastName);
//        birthday = (EditText) findViewById(R.id.birthday);
//        email = (EditText) findViewById(R.id.email);
//        userName = (EditText) findViewById(R.id.userName);
//        password = (EditText) findViewById(R.id.password);
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
//
//
//    //creates new account in FireBase with email & PW
//    public void createAccount(String email, String password) {
//        Log.d(TAG, email + " " + password);
//
//        Log.d(TAG, (mAuth == null) ? "null" : "not null");
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(SignUpActivity.this, "Account created. Please verify your email.", Toast.LENGTH_SHORT).show();
////                            sendVerificationEmail();
//                        }
//                        // ...
//                    }
//                });
//    }
//
////
////    private void sendVerificationEmail() {
////        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////
////        user.sendEmailVerification()
////                .addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////                        if (task.isSuccessful()) {
////                            // email sent
////
////
////                            // after email is sent just logout the user and finish this activity
////                            FirebaseAuth.getInstance().signOut();
////                            finish();
////                        } else {
////                            // email not sent, so display message and restart the activity or do whatever you wish to do
////
////                            //restart this activity
////                            overridePendingTransition(0, 0);
////                            finish();
////                            overridePendingTransition(0, 0);
////                            startActivity(getIntent());
////
////                        }
////                    }
////                });
////    }
//
//    public void singIn(String email, String password) {
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithEmail", task.getException());
//                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // ...
//                    }
//                });
//    }
//
//    //checks if user filled out all fields, if so then creates new user, if not toasts what is missing
//    private void sendData() {
//        String[] dataString = new String[6];
//        dataString[0] = firstName.getText().toString();
//        dataString[1] = lastName.getText().toString();
//        dataString[2] = birthday.toString();
//        dataString[3] = email.getText().toString();
//        dataString[4] = userName.getText().toString();
//        dataString[5] = password.getText().toString();
//
//        //if (data format correct){create new account}
//        //CheckSingUpData checkData = new CheckSingUpData(this);
//        if (CheckSingUpData.controlSignUpArray(dataString, SignUpActivity.this)) {
//            Log.d(TAG, "Data is checked");
//            createAccount(dataString[3], dataString[5]);
//        }
//
//        //}
    }


}
