package com.example.joe.smarttask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.joe.smarttask.Welcome.ShowWelcome;
import com.example.joe.smarttask.Welcome.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    private Button logInButton;

    //Defines where program starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //checks if welcome page did already run. If yes nothing happens. If no it shows welcome page
        //Intent needs activity line in manifest to access subpackage
        //Creates new Intent with WelcomeActivity and starts it


        logInButton = (Button) findViewById(R.id.logIn);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               checkShowWelcome();
                                           }
                                       }
        );


    }

    public void checkShowWelcome() {
        ShowWelcome showWelcome = new ShowWelcome(this);
        if (showWelcome.getSharedPrefencesWelcome()) {
            showWelcome.setSharedPreferencesWelcome(false);
            Intent myIntent = new Intent(this, WelcomeActivity.class);
            startActivity(myIntent);
        }
    }
}
