package com.example.joe.smarttask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.joe.smarttask.Welcome.ShowWelcome;
import com.example.joe.smarttask.Welcome.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ShowWelcome showWelcome = new ShowWelcome(this);
        if (showWelcome.getSharedPrefencesWelcome()) {
            showWelcome.setSharedPreferencesWelcome(false);
            //Intent needs activity line in manifest to access subpackage
            Intent myIntent = new Intent(this, WelcomeActivity.class);
            startActivity(myIntent);
        }

    }
}
