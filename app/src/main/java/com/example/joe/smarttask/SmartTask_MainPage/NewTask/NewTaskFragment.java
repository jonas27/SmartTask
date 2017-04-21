package com.example.joe.smarttask.SmartTask_MainPage.NewTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.example.joe.smarttask.SmartTask_MainPage.Widgets.DatePickerFragment;
import com.example.joe.smarttask.SmartTask_MainPage.Widgets.TimePickerFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by joe on 18/04/2017.
 */

public class NewTaskFragment extends Fragment {

    private static final String TAG = "CL_NTF";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static boolean sTaskChecked;
    Date mDateNumber;
    //    [Start: define Views]
    EditText mCategories;
    EditText mColorcode;
    Button mTime;
    Button mDate;
    EditText mDescription;
    EditText mFrequency;
    EditText mName;
    EditText mOwner;
    EditText mPoints;
    EditText mPriority;
    EditText mResponsible;
    EditText mStatus;
    EditText mId;
    EditText mTask;

    Button mCreate;
//    [End: define Views]
FireBase fireBase;
    TaskObject t;
    //    [Start: Variables of a task (Naming has to be equal to FireBase, so don't change!)]
    private String categories;
    private String colorcode;
    private Long date;
    private Long time;
    private long datetime;
    private String description;
    private String frequency;
    private String name;
    private String owner;
    private String points;
    private String priority;
    private String responsible;
    private String status;
    //    [End: Variables of a task]
    private String id;
    private String task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_task, container, false);
        fireBase = FireBase.fireBase(getContext());
        t = new TaskObject();
        sTaskChecked = true;

        mCategories = (EditText) v.findViewById(R.id.newtask_category);
        mDescription = (EditText) v.findViewById(R.id.newtask_description);
        mFrequency = (EditText) v.findViewById(R.id.newtask_frequency);
        mName = (EditText) v.findViewById(R.id.newtask_name);
        mPoints = (EditText) v.findViewById(R.id.newtask_points);
        mPriority = (EditText) v.findViewById(R.id.newtask_priority);
        mResponsible = (EditText) v.findViewById(R.id.newtask_responsible);

        mTime = (Button) v.findViewById(R.id.newtask_time);
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = new TimePickerFragment();
                dialog.setTargetFragment(NewTaskFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mDate = (Button) v.findViewById(R.id.newtask_date);
//        Log.d(TAG,"Date: "+ t.getDatetime());
//        mDateNumber= new Date(Long.parseLong(t.getDatetime()));
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance();
                dialog.setTargetFragment(NewTaskFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mCreate = (Button) v.findViewById(R.id.newtask_create);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTask();
                if (sTaskChecked) {
                    fireBase.createTask(t);
                    getActivity().finish();
                }
            }
        });
return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return; }
        if (requestCode == REQUEST_DATE) {
            Date result = new Date((long) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE));
            date = (Long) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            String formatDate=date.toString().substring(0,4) + "/" + date.toString().substring(4,6) + "/" + date.toString().substring(6,8);
            mDate.setText(formatDate);
        }
        if (requestCode == REQUEST_TIME) {
            time = (Long) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            String formatTime;
            if (time < 2400 && time > 10000) {
                formatTime = time.toString().substring(0, 2) + ":" + time.toString().substring(2, 4);
            }else{
                formatTime = "00:" + time.toString().substring(2, 4);
            }
            mTime.setText(formatTime);
        }
    }








    private void createNewTask() {
        if (mCategories.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_categories, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setCategories(mCategories.getText().toString());
        }
        if(mDate.getText().toString().equals("")){Toast.makeText(getContext(),R.string.newtask_datetime, Toast.LENGTH_SHORT).show();sTaskChecked=false;} else if (mTime.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_datetime, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            int year = Integer.parseInt(Long.toString(date).substring(0, 4));
            int month = Integer.parseInt(Long.toString(date).substring(4, 6));
            int day = Integer.parseInt(Long.toString(date).substring(6));
            int hrs = Integer.parseInt(Long.toString(time).substring(0, 2));
            int min = Integer.parseInt(Long.toString(time).substring(2));
            Calendar cal = new GregorianCalendar(year, month, day, hrs, min, 0);
            datetime = cal.getTimeInMillis();
            Log.d(TAG, Long.toString(datetime));
            t.setDatetime(Long.toString(datetime));
        }
        if (mDescription.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_description, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setDescription(mDescription.getText().toString());
        }
        if (mFrequency.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_frequency, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setFrequency(mFrequency.getText().toString());
        }
        if (mName.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_name, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setName(mName.getText().toString());
        }
        t.setOwner("Owner is admin");
        if (mPriority.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_priority, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setPriority(mPriority.getText().toString());
        }
        if (mResponsible.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_responsible, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setResponsible(mResponsible.getText().toString());
        }
        if (mPoints.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_points, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setPoints(mPoints.getText().toString());
        }
        t.setStatus("false");
        t.setId("");
        t.setTask("not used (legacy)");
    }


}
