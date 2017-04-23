package com.example.joe.smarttask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class CreateProfile extends AppCompatActivity {

    private static final String TAG = "CreateProfile";
    private static final String[] PRIVILIGIES = new String[]{
            "admin", "user", "child"
    };
    private Button CreateProfileDoneButton;
    private Button CreateProfileAddButton;
    private Button CreateProfileUploadButton;
    private Button CreateProfileSavePictureButton;
    private Button CreateProfileCameraButton;
    private EditText ProfileName, PinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_profile);

        ProfileName = (EditText) findViewById(R.id.ProfileName);
        PinCode = (EditText) findViewById(R.id.Pincode);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, PRIVILIGIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.Privilgies);
        textView.setAdapter(adapter);
    }




}


