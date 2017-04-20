package com.example.joe.smarttask.SmartTask_MainPage.NewTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.google.android.gms.tasks.Task;

import java.util.Map;

/**
 * Created by joe on 18/04/2017.
 */

public class NewTaskFragment extends FragmentActivity {

    private static final String TAG = "CL_NTF";
    private static boolean sTaskChecked;

    //    [Start: define Views]
    EditText mCategories;
    EditText mColorcode;
    EditText mTime;
    EditText mDate;
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


    //    [Start: Variables of a task (Naming has to be equal to FireBase, so don't change!)]
    private String categories;
    private String colorcode;
    private String datetime;
    private String description;
    private String frequency;
    private String name;
    private String owner;
    private String points;
    private String priority;
    private String responsible;
    private String status;
    private String id;
    private String task;
//    [End: Variables of a task]


    FireBase fireBase;
    TaskObject t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireBase = FireBase.fireBase(this);
        t= new TaskObject();
        sTaskChecked=true;
        setContentView(R.layout.fragment_new_task);

        mCategories = (EditText) findViewById(R.id.newtask_category);
        mDescription = (EditText) findViewById(R.id.newtask_description);
        mFrequency  = (EditText) findViewById(R.id.newtask_frequency);
        mName=(EditText) findViewById(R.id.newtask_name);
        mPoints  = (EditText) findViewById(R.id.newtask_points);
        mPriority = (EditText) findViewById(R.id.newtask_priority);
        mResponsible  = (EditText) findViewById(R.id.newtask_responsible);
        mTime = (EditText) findViewById(R.id.newtask_time);
        mDate = (EditText) findViewById(R.id.newtask_date);

        mCreate=(Button) findViewById(R.id.newtask_create);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTask();
                if(sTaskChecked){
                    fireBase.createTask(t);
                    finish();
                }}
        });

    }


    private void createNewTask(){
        if(mCategories.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_categories, Toast.LENGTH_SHORT).show(); sTaskChecked=false;}
        else{t.setCategories(mCategories.getText().toString());}
//        if(mDate.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_datetime, Toast.LENGTH_SHORT).show();sTaskChecked=false;}
//        else{t.setDatetime(mDate.getText().toString());}
//        if(mTime.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_datetime, Toast.LENGTH_SHORT).show();sTaskChecked=false;}
//        else{t.setDatetime(mTime.getText().toString());}
        if(mDescription.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_description, Toast.LENGTH_SHORT).show();sTaskChecked=false;}
        else{t.setDescription(mDescription.getText().toString());}
        if(mFrequency.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_frequency, Toast.LENGTH_SHORT).show();sTaskChecked=false;}
        else{t.setFrequency(mFrequency.getText().toString());}
        if(mName.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_name, Toast.LENGTH_SHORT).show();sTaskChecked=false;}
        else{t.setName(mName.getText().toString());}
        t.setOwner("Owner is admin");
        if(mPriority.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_priority, Toast.LENGTH_SHORT).show();sTaskChecked=false;}
        else{t.setPriority(mPriority.getText().toString());}
        if(mResponsible.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_responsible, Toast.LENGTH_SHORT).show();sTaskChecked=false;}
        else{t.setResponsible(mResponsible.getText().toString());}
        if(mPoints.getText().toString().equals("")){Toast.makeText(NewTaskFragment.this,R.string.newtask_points, Toast.LENGTH_SHORT).show();sTaskChecked=false;}
        else{t.setPoints(mPoints.getText().toString());}
        t.setStatus("false");
        t.setId("");
        t.setTask("not used (legacy)");
    }



}
