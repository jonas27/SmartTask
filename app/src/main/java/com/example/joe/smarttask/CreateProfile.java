package com.example.joe.smarttask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class CreateProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, PRIVILIGIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.Privilgies);
        textView.setAdapter(adapter);
    }

    private static final String[] PRIVILIGIES = new String[] {
            "admin", "user", "child"
    };
}


//hw