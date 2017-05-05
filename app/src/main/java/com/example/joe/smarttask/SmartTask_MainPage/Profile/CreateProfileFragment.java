package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.joe.smarttask.R;

/**
 * Created by joe on 04/05/2017.
 */

public class CreateProfileFragment extends Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_create, container, false);

        ProfileName = (EditText) v.findViewById(R.id.ProfileName);
        PinCode = (EditText) v.findViewById(R.id.Score);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
//                android.R.layout.simple_dropdown_item_1line, PRIVILIGIES);
//        AutoCompleteTextView textView = (AutoCompleteTextView)
//                v.findViewById(R.id.Privilgies);
//        textView.setAdapter(adapter);
        return v;
    }






}
