package com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SettingsList;

/**
 * Created by joe on 27/04/2017.
 */

public class ListFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_SettF";


    private static RecyclerView sRecyclerView;
    private static RecyclerView.Adapter sAdapter;
    private static Context sContext;
    private SettingsList mSettingsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = SMMainActivity.getAppContext();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_list, container, false);


        return v;
    }


}
