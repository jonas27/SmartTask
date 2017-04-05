package com.example.joe.smarttask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joe.smarttask.IntroSlider.IntroActivity;
import com.example.joe.smarttask.IntroSlider.ShowIntro;
import com.example.joe.smarttask.SignUp.CheckSingUpData;
import com.example.joe.smarttask.SignUp.SignUpActivity;
import com.example.joe.smarttask.SmartTask_MainPage.*;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by us with Firebase methods taken from google and modified by us
 * Log ing screen for Email, Google and Facebook
 * Goes to Sign-up or App-content (Either Intro or personal site)
 * Created by us (but we took FireBase methods from google and modefied them to fit our needs)
 */

public class LogInActivity extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "LogInActivity";

    private static final int RC_SIGN_IN = 1;
    private Intent intent;
    // To check if introduction pages should be showed
    private ShowIntro showIntro;
    // [START Define Views]
    private Button logInButton;
    private Button signUpButton;
    // [END Define Views]
    private EditText email, password;
    // [Start declare Firebase Auth, Auth listener and User]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    // [End declare Firebase auth]

    // [Google Login]
    private SignInButton mGoogleButton;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // add Views
        email = (EditText) findViewById(R.id.enter_email);
        password = (EditText) findViewById(R.id.enter_password);
        // add Buttons

        // initialize_auth
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("data", mDatabase.toString());
        Log.d("data", mDatabase.getDatabase().toString());
/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        Log.d("DATA ", String.valueOf(myRef));
*/

        //set's status bar color like background
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        // Configure auth listener for Firebase
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
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

        /*
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        */


        //Log in button, either shows intro or goes into app
        logInButton = (Button) findViewById(R.id.logIn);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               if (CheckSingUpData.checkEmailWithPassword(email.getText().toString(), password.getText().toString(), LogInActivity.this)) {
                                                   signIn(email.getText().toString(), password.getText().toString());
                                               }
                                           }
                                       }
        );

        //Brings users to the sign up page
        signUpButton = (Button) findViewById(R.id.signUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                signUp();
                                            }
                                        }
        );




        //Googelogin buttons
        mGoogleButton =(SignInButton) findViewById(R.id.googleButton);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {

                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LogInActivity.this,"Error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, " Button working");
                signIn();
            }
        });



    }

    // Sign in via google
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

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
        intent = new Intent(this, SmartTask_Main_Activity.class);
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
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
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
            } else {
                openApp();
            }
        } else {
            Toast.makeText(LogInActivity.this, "Please verify your email.", Toast.LENGTH_SHORT).show();
        }
    }


}
