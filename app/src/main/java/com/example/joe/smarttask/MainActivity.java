package com.example.joe.smarttask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.joe.smarttask.MainPage.MPage;
import com.example.joe.smarttask.Welcome.ShowWelcome;
import com.example.joe.smarttask.Welcome.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private ShowWelcome showWelcome;
    private Button logInButton;

    private Toolbar mToolbar;


    //Defines where program starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set's status bar color like background
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        logInButton = (Button) findViewById(R.id.signUp);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               checkShowWelcome();
                                           }
                                       }
        );

        logInButton = (Button) findViewById(R.id.logIn);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               openApp();
                                           }
                                       }
        );

    }


    //checks if welcome page did already run. If yes nothing happens. If no it shows welcome page
    //Intent needs activity line in manifest to access subpackage
    //Creates new Intent with WelcomeActivity and starts it
    private void checkShowWelcome() {
        showWelcome = new ShowWelcome(this);
        if (showWelcome.getSharedPrefencesWelcome()) {
            //hides welcome
            //showWelcome.setSharedPreferencesWelcome(false);
            intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }

    private void openApp() {
        intent = new Intent(this, MPage.class);
        startActivity(intent);
    }


}
