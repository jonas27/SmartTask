package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;


/**
 * Created by joe on 23/04/2017.
 */

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {

    private static final String TAG = "CL_ProfileFragment";
    private static final String PROFILE_ID = "profile_id";

    private static Context sContext;

    private Toolbar toolbar;
    private ProfileObject mProfile;
    private String mProfileId;

    private TextView mViewProfileName;
    private TextView mViewProfileScore;
    private TextView mViewProfilePrivileges;
    private TextView mViewProfileTotalTask;
    private Button mViewProfileDone;

    private ListProfile mListProfile;
    private String dir = "/smarttask/";


    public static ProfileFragment newInstance (String profileId) {
        Bundle args = new Bundle();
        args.putSerializable(PROFILE_ID, profileId);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = SMMainActivity.getAppContext();

        Log.d(TAG,"so far so good");

//        mProfileId = (String) getArguments().getSerializable(PROFILE_ID);
  //      mListProfile = mListProfile.list(getContext());
    //    this.mProfile = mListProfile.getProfile(mProfileId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_view, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.fragment_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mProfile.getPname());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        mViewProfileName = (TextView) v.findViewById(R.id.view_profile_name);
        mViewProfileName.setText(mProfile.getPname());

        mViewProfileScore = (TextView) v.findViewById(R.id.view_profile_score);
        mViewProfileScore.setText(mProfile.getPscore());

        mViewProfilePrivileges = (TextView) v.findViewById(R.id.view_profile_privileges);
        mViewProfilePrivileges.setText(mProfile.getPprivileges());

        mViewProfileTotalTask = (TextView) v.findViewById(R.id.view_profile_total_task);
        mViewProfileTotalTask.setText(mProfile.getPtotalscore());

        return v;
    }



    @Override
    public void onResume(){
        mListProfile = ListProfile.list(getContext());
        this.mProfile=mListProfile.getProfile(mProfileId);
        super.onResume();
        }
}