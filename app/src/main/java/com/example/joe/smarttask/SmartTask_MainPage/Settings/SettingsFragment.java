package com.example.joe.smarttask.SmartTask_MainPage.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;

/**
 * Created by joe on 23/04/2017.
 */

public class SettingsFragment extends Fragment {

    private static Context sContext;

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = SMMainActivity.getAppContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.fragment_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(sContext.getResources().getString(R.string.settings_toolbar_text));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        return v;
    }
}
