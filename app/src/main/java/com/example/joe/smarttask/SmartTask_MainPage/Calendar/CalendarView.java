package com.example.joe.smarttask.SmartTask_MainPage.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.MainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jones on 04/12/17.
 */

public class CalendarView extends LinearLayout
{
    // for logging
    private static final String TAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private static String dateFormat = "MMM yyyy";

    // current displayed month
    private static Calendar currentDate = Calendar.getInstance();

    // internal components
    private static LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private static TextView txtDate;
    private static GridView grid;

    private static List<TaskObject> list;


    public CalendarView(Context context)
    {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        list = ListTask.getTaskList();
        Log.d(TAG,"init");
        initControl(context);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view, this);

        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void assignUiElements()
    {
        // layout is inflated, assign local variables to components
        header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers()
    {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnClickListener(new AdapterView.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                TextView current = (TextView) v.findViewById(R.id.day);

            }
        });
    }

    /**
     * Display dates correctly in grid
     */
    public static void updateCalendar()
    {
        updateCalendar(null);
    }

    /**
     * Display dates correctly in grid
     */
    public static void updateCalendar(HashSet<Date> events)
    {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(MainActivity.getAppContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));
        header.setBackgroundColor(Color.parseColor("#03a9f4"));
    }


    private static class CalendarAdapter extends ArrayAdapter<Date>
    {
        // days with events
        private HashSet<Date> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
        {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            Log.d(TAG,"getView");
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);

            // if this day has an event, specify event image
            view.setBackgroundResource(0);

            TextView textDay = (TextView) view.findViewById(R.id.day);
            TextView taskNumber = (TextView) view.findViewById(R.id.tasknumber);

            // clear styling
            textDay.setTypeface(null, Typeface.NORMAL);
            textDay.setTextColor(Color.BLACK);

            if (currentDate.getTime().getMonth() != month)
            {
                // if this day is outside current month, grey it out
                textDay.setTextColor(Color.parseColor("#c7c7c7"));
            }
            else if (day == today.getDate()&&month == today.getMonth()&&year==today.getYear())
            {
                // if it is today, set it to blue/bold
                textDay.setTypeface(null, Typeface.BOLD);
                textDay.setTextColor(Color.parseColor("#4b82ff"));
            }
            int counter = 0;

            for (Iterator<TaskObject> i = list.iterator(); i.hasNext(); ) {
                TaskObject current = i.next();
                Date cDate = new Date(Long.parseLong(current.getDatetime()));

                Log.d(TAG,"getting tasks "+ date.getDate());
                if(day==cDate.getDay()&&month==cDate.getMonth()&&year==cDate.getYear()){
                    Log.d(TAG,"Should set bg "+String.valueOf(date.getDate()));
                    counter++;
                }
            }
            if(counter>0){
                taskNumber.setText(String.valueOf(counter)+" Tasks");
                taskNumber.setVisibility(VISIBLE);
            }
            // set text
            textDay.setText(String.valueOf(date.getDate()));
            // textDay.setBackground(Drawable.createFromPath("res/drawable/borders.xml"));
            textDay.setHeight(70);
            taskNumber.setHeight(35);

            return view;
        }
    }
}