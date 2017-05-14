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

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileObject;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.FireBase;
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

public class NewTaskFragment extends Fragment {

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
    Spinner mPriority;
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
    private int frequency;
    private int responsiblePosition;
    private String mNameResponsible;
    private String owner;
    private String points;
    private int priority;
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
        mPoints.setText(Integer.toString(taskObject.getPoints()));
        mPriority.setSelection((taskObject.getPriority()));
        mFrequencySpinner.setSelection((taskObject.getFrequency()));
        mResponsible.setSelection(responsiblePosition);
        cal = new GregorianCalendar();
        cal.setTimeInMillis((taskObject.getDatetime()));
        mDate.setText(cal.get(Calendar.DAY_OF_MONTH) + " / " + (cal.get(Calendar.MONTH) + 1) + " / " + cal.get(Calendar.YEAR));
        mTime.setText(String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE)));
    }

    //    Connect the variables to their respective layouts and initializes spinners
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_task, container, false);

        mCategories = (EditText) v.findViewById(R.id.newtask_location);
        mDescription = (EditText) v.findViewById(R.id.newtask_description);
        mName = (EditText) v.findViewById(R.id.newtask_name);
        mPoints = (EditText) v.findViewById(R.id.newtask_points);
        mFrequencySpinner = (Spinner) v.findViewById(R.id.newtask_frequency);
        mPriority = (Spinner) v.findViewById(R.id.newtask_priority);
        mResponsible = (Spinner) v.findViewById(R.id.newtask_responsible);

        ArrayAdapter adapterPriority =ArrayAdapter.createFromResource(getContext(),R.array.newtask_spinner_array_priority,android.R.layout.simple_spinner_item);
        adapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPriority.setAdapter(adapterPriority);

//      Set click listener to Spinner for frequency, define its strings and connect it to the Adapter (Adapter provides access to the data items)
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.newtask_spinner_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFrequencySpinner.setAdapter(spinnerAdapter);

//      Set click listener to Spinner for names, define its strings and connect it to the Adapter (Adapter provides access to the data items)
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
        sTaskChecked=true;
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
            t.setDatetime((datetime));
        }
        if (mDescription.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_description, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setDescription(mDescription.getText().toString());
        }
        if (frequency == 0) {
//            Toast.makeText(getContext(), R.string.newtask_frequency, Toast.LENGTH_SHORT).show();
//            sTaskChecked = false;
            t.setFrequency(0);
        } else {
            t.setFrequency(mFrequencySpinner.getSelectedItemPosition());
        }
        if (mNameResponsible == "") {
//            Toast.makeText(getContext(), R.string.newtask_responsible, Toast.LENGTH_SHORT).show();
//            sTaskChecked = false;
            t.setResponsible("");
        } else {
            t.setResponsible(mResponsible.getSelectedItem().toString());
        }

        if (mName.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_name, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setName(mName.getText().toString());
        }
        t.setOwner("Owner is admin");
        if (mPriority.getSelectedItem().toString().equals("")) {
            t.setPriority(0);
        } else {
            t.setPriority(mPriority.getSelectedItemPosition());
        }
        if (mPoints.getText().toString().equals("")) {
            Toast.makeText(getContext(), R.string.newtask_points, Toast.LENGTH_SHORT).show();
            sTaskChecked = false;
        } else {
            t.setPoints(Integer.parseInt(mPoints.getText().toString()));
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


    private void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }

    public ArrayList<String> getProfileNames() {
        profileObjectList = ListProfile.getProfileList();
        ProfileObject p;
        ArrayList<String> list = new ArrayList<>();
        Iterator<ProfileObject> itr = profileObjectList.iterator();
        int c=0;
        while (itr.hasNext()) {
            p=itr.next();
            list.add(p.getPname());
            if(p.getPname().equals(taskObject.getResponsible())){
                responsiblePosition=c;
            }
            c++;
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
