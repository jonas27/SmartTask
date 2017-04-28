package com.example.joe.smarttask.SmartTask_MainPage.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.smarttask.R;

/**
 * Created by joe on 28/04/2017.
 */

public class SingleDayFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_list, container, false);


        return view;
    }
}
