package com.smarttask17.joe.smarttask.smartaskMain.calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarttask17.joe.smarttask.R;

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
