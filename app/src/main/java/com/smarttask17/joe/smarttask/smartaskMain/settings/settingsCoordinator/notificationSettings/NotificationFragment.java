package com.smarttask17.joe.smarttask.smartaskMain.settings.settingsCoordinator.notificationSettings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.SmarttaskMainActivity;
import com.smarttask17.joe.smarttask.smartaskMain.settings.ListOfSettings;

/**
 * Created by joe on 27/04/2017.
 */

public class NotificationFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_SettF";


    private static RecyclerView sRecyclerView;
    private static RecyclerView.Adapter sAdapter;
    private static Context sContext;
    private ListOfSettings mListOfSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = SmarttaskMainActivity.getAppContext();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_notification, container, false);


        return v;
    }


}
