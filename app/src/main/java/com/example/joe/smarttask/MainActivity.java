package com.example.joe.smarttask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.joe.smarttask.IntroSlider.IntroActivity;
import com.example.joe.smarttask.IntroSlider.ShowIntro;
import com.example.joe.smarttask.MainPage.MPage;
import com.example.joe.smarttask.SignUp.SignUpActivity;


/**
 * Sets the first screen
 */

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    private ShowIntro showIntro;
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


        logInButton = (Button) findViewById(R.id.logIn);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               if (checkShowIntro()) {
                                                   introShow();
                                               }
                                           }
                                       }
        );

        logInButton = (Button) findViewById(R.id.signUp);
        logInButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               signUp();
                                           }
                                       }
        );

    }


    private void signUp() {
        intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

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
        /**
         //re-wrote & named classes and now intent closes with
         "endAllStagingAnimators on 0xb85d99b0 (RippleDrawable) with handle 0xb86c5090"
         and I don't know why.
         **/
            

    }

    private void openApp() {
        intent = new Intent(this, MPage.class);
        startActivity(intent);
    }


}
