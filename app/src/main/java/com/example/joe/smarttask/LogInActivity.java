package com.example.joe.smarttask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joe.smarttask.introSlider.IntroActivity;
import com.example.joe.smarttask.permissionRequester.PermissionRequesterActivity;
import com.example.joe.smarttask.signUp.SignUpDataChecker;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.FireBase;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
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
import com.google.firebase.crash.FirebaseCrash;
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
    private static final String TAG = "CL_LogInActivity";
    private static final String DEVELOPER_MAIL= "smarttask17@gmail.com";
    private static final String TEST_MAIL= "smarttask17@googlemail.com";

    private static final int RC_SIGN_IN = 1;
    public static GoogleApiClient mGoogleApiClient;
    private Intent intent;
    // To check if introduction pages should be showed
    private SharedPrefs sharedPrefs;
    // [START Define Views]
    private Button logInButton;
    // [END Define Views]
    private Button signUpButton;
    private EditText email, password;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    // [Start declare Firebase Auth, Auth listener and User]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    // [End declare Firebase auth]
    private FireBase mFireBase;
    // [Google Login]
    private SignInButton mGoogleButton;
    private DatabaseReference mDatabase;
    public static boolean introWasShown=false;


    @Override
    public void onResume() {
        super.onResume();
        if (user != null) {
            Log.d(TAG, user.getUid());
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Attach Firebase crash reporting tool
        FirebaseCrash.report(new Exception("First Test"));
//        initialize SharedPreferences for app
        sharedPrefs = SharedPrefs.getSharedPrefs(getApplicationContext());

//        We had problems with the permissions! Requesting them inside activities requires min API 23
//        Intent starts new activity with fragment with sole purpose of asking for permissions
        Intent intent = new Intent(this, PermissionRequesterActivity.class);
        startActivity(intent);

        email = (EditText) findViewById(R.id.enter_email);
        password = (EditText) findViewById(R.id.enter_password);

        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);

        password.setHint(R.string.main_log_in_pw);


//      For hiding the hint when editText is selected
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    email.setHint("");
                else
                    email.setHint(R.string.main_log_in_email);
            }
        });
//      For hiding the hint when editText is selected
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    password.setHint("");
                else
                    password.setHint(R.string.main_log_in_pw);
            }
        });


        // initialize_auth and database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                    isVerified(user);
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


        //Log in button, either shows intro or goes into app
        logInButton = (Button) findViewById(R.id.logIn);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               if (SignUpDataChecker.checkEmailWithPassword(email.getText().toString(), password.getText().toString(), LogInActivity.this)) {
//                                                   for easy sign in
                                                   if(email.getText().toString().equals("@17")){signIn(DEVELOPER_MAIL,password.getText().toString());}
                                                   if(email.getText().toString().equals("@test")){signIn(TEST_MAIL,password.getText().toString());}
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
                                                if (SignUpDataChecker.checkEmailWithPassword(email.getText().toString(), password.getText().toString(), LogInActivity.this)) {
                                                    createAccount(email.getText().toString(), password.getText().toString());
                                                }
                                            }
                                        }
        );
        //Googelogin buttons
        mGoogleButton =(SignInButton) findViewById(R.id.googleButton);
        // Customize the google sign in button (size and color)
        mGoogleButton.setSize(SignInButton.SIZE_WIDE);
        mGoogleButton.setColorScheme(0);
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
                signInGoogle();

            }
        });

    }

    // Sign in via google
    private void signInGoogle() {
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

    //starts new intro activity
    private void introShow() {
        intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }

    //opens main app and disconnects
    private void openApp() {
        intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail", task.getException());
//                            Toast.makeText(LogInActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this, "Account already exists!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(LogInActivity.this, "Account created. Please verify your email.", Toast.LENGTH_SHORT).show();
                            sendVerificationEmail();
                        }
                    }
                });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            //restart this activity
                            overridePendingTransition(0, 0);
//                            overridePendingTransition(0, 0);
//                            startActivity(getIntent());
                        }
                    }
                });
    }

    public void isVerified(FirebaseUser user) {
        if (user.isEmailVerified()) {
            if (sharedPrefs.getSharedPrefencesShowIntro() && introWasShown==false) {
                introWasShown=true;
                introShow();
            } else {
                openApp();
            }
        } else {
            Toast.makeText(LogInActivity.this, "Please verify your email.", Toast.LENGTH_SHORT).show();
        }
    }
}
