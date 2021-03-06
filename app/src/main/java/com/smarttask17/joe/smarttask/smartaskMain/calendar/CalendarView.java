package com.smarttask17.joe.smarttask.smartaskMain.calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
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

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.list.ListOfTasks;
import com.smarttask17.joe.smarttask.smartaskMain.task.TaskObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.smarttask17.joe.smarttask.smartaskMain.SmarttaskMainActivity.getAppContext;

/**
 * Creating the GUI, and logic of the calendar, taken from: https://github.com/ahmed-alamir/CalendarVie
 * But we modified it to fit for our application
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
    private static TextView txtDate;
    private static GridView grid;
    private static List<TaskObject> list;
    private ImageView btnPrev;
    private ImageView btnNext;
    private Context context;


    public CalendarView(Context context)
    {
        super(context);
        this.context = context;
    }

    public CalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        list = ListOfTasks.getSortList();
        initControl(context);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        list = ListOfTasks.getSortList();
        initControl(context);
    }

    /**
     * Display dates correctly in grid
     */
    public static void updateCalendar() {
        list = ListOfTasks.getSortList();
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getAppContext(), cells));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));
        header.setBackgroundColor(getAppContext().getResources().getColor(R.color.colorPrimary));
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
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView current = (TextView) view.findViewById(R.id.tasknumber);
                Log.d(TAG,"hint "+current.getHint()+" "+current.getHint().length());
                if(current.getHint().length()>1){
                    SingleDayActivity day = new SingleDayActivity(Long.parseLong(current.getHint().toString()));
                    Intent intent = new Intent(getContext(), day.getClass());
                    getContext().startActivity(intent);
                }
            }
        });
    }

    private static class CalendarAdapter extends ArrayAdapter<Date>
    {
        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days)
        {
            super(context, R.layout.control_calendar_day, days);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null){
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);
            }

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
            int color = 0;
            boolean colorfound = false;
            Date ccDate = null;
            for (Iterator<TaskObject> i = list.iterator(); i.hasNext(); ) {
                TaskObject current = i.next();
                Date cDate = new Date(current.getDatetime());

                if(!current.getStatus()){
                    if(day==cDate.getDate()&&month==cDate.getMonth()&&year==cDate.getYear()){
                        if(current.getPriority()==0){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                color = getAppContext().getResources().getColor(R.color.list_high_p_red);
                                colorfound = true;
                            }

                        }else if(current.getPriority()==1&&!colorfound){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                color = getAppContext().getResources().getColor(R.color.list_middle_p_orange);
                                colorfound = true;
                            }
                        }else if(current.getPriority()==2&&!colorfound){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                color = getAppContext().getResources().getColor(R.color.list_low_p_green);
                            }
                        }
                        counter++;
                        ccDate = cDate;
                    }
                }
            }
            if(counter>0){
                taskNumber.setText(String.valueOf(counter)+" Tasks");
                taskNumber.setBackgroundColor(color);
                taskNumber.setVisibility(VISIBLE);
                taskNumber.setHint(Long.toString(ccDate.getTime()));
            }
            // set text
            textDay.setText(String.valueOf(date.getDate()));
            textDay.setHeight(70);
            taskNumber.setHeight(35);

            return view;
        }
    }
}