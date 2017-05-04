package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;

public class CreateProfile extends AppCompatActivity {

    private static final String TAG = "CreateProfile";
    private static final String[] PRIVILIGIES = new String[]{
            "admin", "user", "child"
    };

    private Button CreateProfileAddButton;
    private Button CreateProfileUploadButton;
    private Button CreateProfileSavePictureButton;
    private Button CreateProfileCameraButton;
    private EditText ProfileName, PinCode;
    private ProfileObject profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_create);

        ProfileName = (EditText) findViewById(R.id.ProfileName);
        PinCode = (EditText) findViewById(R.id.Score);
        CreateProfileAddButton = (Button) findViewById(R.id.CreateProfileAddButton);

        final FireBase fireBase = FireBase.fireBase(getApplicationContext());

        CreateProfileAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ProfileName.getText().length()>0){
                    profile = new ProfileObject(ProfileName.getText().toString(),"0",PinCode.getText().toString(),"1","","0");
                    fireBase.createProfile(profile);
                    finish();
                }
            }
        });

        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, PRIVILIGIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.Privilgies);
        textView.setAdapter(adapter);
        */


    }




}


