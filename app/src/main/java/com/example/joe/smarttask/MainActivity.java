package com.example.joe.smarttask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joe.smarttask.FireBase.SignUpActivity;
import com.example.joe.smarttask.IntroSlider.IntroActivity;
import com.example.joe.smarttask.IntroSlider.ShowIntro;
import com.example.joe.smarttask.MainPage.MPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Sets the first screen
 */

public class MainActivity extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "MainActivity";

    private Intent intent;

    private ShowIntro showIntro;
    private Button logInButton;

    private Toolbar mToolbar;

    private EditText email, password;

    //firebase objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    //Defines where program starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.enter_email);
        password = (EditText) findViewById(R.id.enter_password);

        //set's status bar color like background
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //check if user is verified
                    isVerified(user);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        //Log in button, eiher shows intro or goes into app
        logInButton = (Button) findViewById(R.id.logIn);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               signIn(email.getText().toString(), password.getText().toString());

                                           }
                                       }
        );

        //Brings users to the sign up page
        logInButton = (Button) findViewById(R.id.signUp);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               signUp();
                                           }
                                       }
        );


    }


    //Starts a new sign up activity
    private void signUp() {
        intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    //starts new intro activity
    private void introShow() {
        intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }

    //checks if intro page did already run. If yes nothing happens. If no it shows intro page
    //Intent needs activity line in manifest to access subpackage
    //Creates new Intent with IntroActivity and starts it
    private boolean checkShowIntro() {
        showIntro = new ShowIntro(this);
        return showIntro.getSharedPrefencesIntro();


    }

    //opens main app
    private void openApp() {
        intent = new Intent(this, MPage.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    public void isVerified(FirebaseUser user) {
        if (user.isEmailVerified()) {
            if (checkShowIntro()) {
                introShow();
            }
        } else {
            Toast.makeText(MainActivity.this, "Please verify your email.", Toast.LENGTH_SHORT).show();
        }
    }


}
