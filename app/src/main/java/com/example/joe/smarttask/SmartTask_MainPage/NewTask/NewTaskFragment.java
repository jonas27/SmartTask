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
    private boolean taskChecked=true;

//    [Start: define Views]
    EditText mCategories;
    EditText mColorcode;
    EditText mDatetime;
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
        setContentView(R.layout.fragment_new_task);

        mCategories = (EditText) findViewById(R.id.newtask_category);
        mDescription = (EditText) findViewById(R.id.newtask_description);
        mFrequency  = (EditText) findViewById(R.id.newtask_frequency);
        mName=(EditText) findViewById(R.id.newtask_name);
        mPoints  = (EditText) findViewById(R.id.newtask_points);
        mPriority = (EditText) findViewById(R.id.newtask_priority);
        mResponsible  = (EditText) findViewById(R.id.newtask_responsible);


        mCreate=(Button) findViewById(R.id.newtask_create);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTask();
                if(taskChecked){
                    fireBase.createTask(t);
                    finish();
            }}
        });

    }


    private void createNewTask(){
        if(mCategories!=null){t.setCategories(mCategories.getText().toString());}
        else{Toast.makeText(NewTaskFragment.this,R.string.newtask_categories, Toast.LENGTH_SHORT).show(); taskChecked=false;}
        if(mDatetime!=null){t.setDatetime(mDatetime.getText().toString());}
        else{Toast.makeText(NewTaskFragment.this,R.string.newtask_datetime, Toast.LENGTH_SHORT).show();taskChecked=false;}
        if(mDescription!=null){t.setDescription(mDescription.getText().toString());}
        else{Toast.makeText(NewTaskFragment.this,R.string.newtask_description, Toast.LENGTH_SHORT).show();taskChecked=false;}
        if(mFrequency!=null){t.setFrequency(mFrequency.getText().toString());}
        else{Toast.makeText(NewTaskFragment.this,R.string.newtask_frequency, Toast.LENGTH_SHORT).show();taskChecked=false;}
        if(mName!=null){t.setName(mName.getText().toString());}
        else{Toast.makeText(NewTaskFragment.this,R.string.newtask_name, Toast.LENGTH_SHORT).show();taskChecked=false;}
        t.setOwner("Owner is admin");
        if(mPriority!=null){t.setPriority(mPriority.getText().toString());}
        else{Toast.makeText(NewTaskFragment.this,R.string.newtask_priority, Toast.LENGTH_SHORT).show();taskChecked=false;}
        if(mResponsible!=null){t.setResponsible(mResponsible.getText().toString());}
        else{Toast.makeText(NewTaskFragment.this,R.string.newtask_responsible, Toast.LENGTH_SHORT).show();taskChecked=false;}
        if(mPoints!=null){t.setPoints(mPoints.getText().toString());}
        else{Toast.makeText(NewTaskFragment.this,R.string.newtask_points, Toast.LENGTH_SHORT).show();taskChecked=false;}
        t.setStatus("false");
        t.setId("not used (legacy)");
        t.setTask("not used (legacy)");
    }



}
