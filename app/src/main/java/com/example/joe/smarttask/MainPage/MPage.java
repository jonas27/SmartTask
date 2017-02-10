package com.example.joe.smarttask.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.joe.smarttask.R;

/**
 * Created by joe on 10/02/2017.
 */

public class MPage extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        //set's the content (layout)
        setContentView(R.layout.welcome_activity);
    }


}
