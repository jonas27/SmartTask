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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joe.smarttask.IntroSlider.IntroActivity;
import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileObject;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.example.joe.smarttask.SmartTask_MainPage.Widgets.DatePickerFragment;
import com.example.joe.smarttask.SmartTask_MainPage.Widgets.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * 100% created by us
 * Class has 2 functions: Create a Task and edit a task
 * Both functions create a new TaskObject and initialize the layout.
 * However, a new task sets all fields to default, whereas editing a tasks takes the values from the old TaskObject
 */

public class NewTaskFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "CL_NTF";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static boolean sTaskChecked;

    public static TaskObject taskObject;
    public static List<ProfileObject> profileObjectList;

    Date mDateNumber;
    //    [Start: define Views]
    EditText mCategories;
    EditText mColorcode;
    Button mTime;
    Button mDate;
    EditText mDescription;
    Spinner mFrequencySpinner;
    EditText mName;
    EditText mOwner;
    EditText mPoints;
    EditText mPriority;
    Spinner mResponsible;
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
    private int time;
    private long datetime;
    private String description;
    private String frequency;
    private String mNameResponsible;
    private String owner;
    private String points;
    private String priority;
    private String responsible;
    private String status;
    //    [End: Variables of a task]
    private String id;
    private String task;

    private Calendar cal;

    public static NewTaskFragment newInstance(TaskObject taskObject) {
        NewTaskFragment.taskObject = taskObject;
        NewTaskFragment fragment = new NewTaskFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireBase = FireBase.fireBase(getContext());
        t = new TaskObject();
        sTaskChecked = true;
    }

    //    This is for editing tasks.
//    The old task is initialized as a new task with the values from the old task
    public void setParameters() {
        mCategories.setText(taskObject.getCategories());
        mDescription.setText(taskObject.getDescription());
//        mFrequencySpinner.setText(taskObject.getFrequency());
        mName.setText(taskObject.getName());
        mPoints.setText(taskObject.getPoints());
        mPriority.setText(taskObject.getPriority());
//        mResponsible.setText(taskObject.getResponsible());

        cal = new GregorianCalendar();
        cal.setTimeInMillis(Long.parseLong(taskObject.getDatetime()));
        mDate.setText(cal.get(Calendar.DAY_OF_MONTH) + " / " + (cal.get(Calendar.MONTH) + 1) + " / " + cal.get(Calendar.YEAR));
        mTime.setText(String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE)));
    }

    //    Connect the variables to their respective layouts and initializes spinners
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_task, container, false);

        mCategories = (EditText) v.findViewById(R.id.newtask_category);
        mDescription = (EditText) v.findViewById(R.id.newtask_description);
        mFrequencySpinner = (Spinner) v.findViewById(R.id.newtask_frequency);
        mName = (EditText) v.findViewById(R.id.newtask_name);
        mPoints = (EditText) v.findViewById(R.id.newtask_points);
        mPriority = (EditText) v.findViewById(R.id.newtask_priority);
        mResponsible = (Spinner) v.findViewById(R.id.newtask_responsible);

//      Set click listener to Spinner for frequency, define its strings and connect it to the Adapter (Adapter provides access to the data items)
        mFrequencySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.newtask_spinner_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFrequencySpinner.setAdapter(spinnerAdapter);

//      Set click listener to Spinner for names, define its strings and connect it to the Adapter (Adapter provides access to the data items)
        mResponsible.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getProfileNames());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mResponsible.setAdapter(dataAdapter);


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
                    IntroActivity.taskAdded=true;
                    getActivity().finish();
                }
            }
        });
        if (taskObject != null) {
            setParameters();
        }

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            cal = (Calendar) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDate.setText(cal.get(Calendar.DAY_OF_MONTH) + " / " + (cal.get(Calendar.MONTH) + 1) + " / " + cal.get(Calendar.YEAR));
        }
        if (requestCode == REQUEST_TIME) {
            time = (Integer) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mTime.setText(String.format("%02d:%02d", time / 60, time % 60));
        }
    }


    private void createNewTask() {
        if (mCategories.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_categories, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setCategories(mCategories.getText().toString());
        }
        if (mDate.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_datetime, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else if (mTime.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_datetime, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            cal.add(Calendar.MINUTE, time);
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
        if (frequency == "") {
            Toast.makeText(getContext(), R.string.newtask_frequency, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setFrequency(frequency);
        }
        if (mNameResponsible == "") {
            Toast.makeText(getContext(), R.string.newtask_responsible, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setResponsible(mNameResponsible);
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
        if (mPoints.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_points, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setPoints(mPoints.getText().toString());
        }
        t.setStatus("false");
        if (taskObject != null) {
            t.setId(taskObject.getId());
        } else {
            t.setId("");
        }
        t.setTask("not used (legacy)");
    }

    @Override
    public void onStop() {
        super.onStop();
        taskObject = null;
    }


    /**
     * This defines the onclick behaviour of the spinner
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Spinner spinnerParent = (Spinner) parent;
        if (spinnerParent.getId() == R.id.newtask_frequency) {
            switch (pos) {
//            sets frequency
                case 0: {
                    frequency = "0";
                    break;
                }
                case 1: {
                    frequency = "1";
                    break;
                }
                case 2: {
                    frequency = "2";
                    break;
                }
                case 3: {
                    frequency = "3";
                    break;
                }
                case 4: {
                    frequency = "4";
                    break;
                }
                case 5: {
                    frequency = "5";
                    break;
                }
            }
        } else if (spinnerParent.getId() == R.id.newtask_responsible) {
//            sets responsible
            mNameResponsible = getName(pos);
        }
    }

    public ArrayList<String> getProfileNames() {
        profileObjectList = ListProfile.getProfileList();
        ArrayList<String> list = new ArrayList<>();
        Iterator<ProfileObject> itr = profileObjectList.iterator();
        while (itr.hasNext()) {
            list.add(itr.next().getPname());
        }
        return list;
    }

    public String getName(int position) {
        return profileObjectList.get(position).getPname();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}
