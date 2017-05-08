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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joe.smarttask.IntroSlider.IntroActivity;
import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;

/**
 * Created by joe on 04/05/2017.
 */

public class CreateProfileFragment extends Fragment {


    private static final String TAG = "CreateProfile";
    private Button CreateProfileDoneButton;
    private Button CreateProfileAddButton;
    private Button CreateProfileUploadButton;
    private Button CreateProfileSavePictureButton;
    private Button CreateProfileCameraButton;
    private Spinner Privilgies;
    private EditText ProfileName, PinCode;
    ProfileObject t;
    private static boolean sTaskChecked;
    FireBase fireBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireBase = FireBase.fireBase(getContext());
        t = new ProfileObject();
        sTaskChecked = true;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_create, container, false);

        ProfileName = (EditText) v.findViewById(R.id.ProfileName);
        PinCode = (EditText) v.findViewById(R.id.Score);
        Privilgies = (Spinner) v.findViewById(R.id.Privilgies);
        String [] userpriv={getString(R.string.smarttask_create_profile_spinner_admin), getString(R.string.smarttask_create_profile_spinner_user),getString(R.string.smarttask_create_profile_spinner_kid)};
        ArrayAdapter adapterpriv = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,userpriv);
        Privilgies.setAdapter(adapterpriv);


        CreateProfileAddButton = (Button) v.findViewById(R.id.CreateProfileAddButton);
        CreateProfileAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewProfile();
                if (sTaskChecked) {
                    fireBase.createProfile(t);
                    getActivity().finish();
                }
            }
        });


        return v;
    }

    private void createNewProfile() {
        if (ProfileName.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.smarttask_create_profile_name_toast, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setPname(ProfileName.getText().toString());
        }
        if (PinCode.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.smarttask_create_profile_pincode_toast, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setPpincode(PinCode.getText().toString());
        }
        t.setPscore("0");
        switch (Privilgies.getSelectedItemPosition()){
            case(0):
                t.setPprivileges("1");
                break;
            case(1):
                t.setPprivileges("2");
                break;
            case(2):
                t.setPprivileges("3");
                break;
            default:
                t.setPprivileges("1");
        }
        t.setPid("");
        t.setPtotalscore("0");
    }
}
