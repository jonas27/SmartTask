package com.example.joe.smarttask.SmartTask_MainPage.Calendar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.smarttask.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.text.SimpleDateFormat;

/**

 */
public class  CalendarFragment extends Fragment {

    CalendarView calendar;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendar = ((CalendarView)view.findViewById(R.id.calendar_view));
        calendar.updateCalendar();
        return view;
    }

}
