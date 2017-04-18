package com.example.joe.smarttask.SmartTask_MainPage.NewTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.google.android.gms.tasks.Task;

import java.util.Map;

/**
 * Created by joe on 18/04/2017.
 */

public class NewTaskFragment extends FragmentActivity {

//    [Start: define Views]
    EditText mName;


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


    String[] mUpload;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_task);

        mName=(EditText) findViewById(R.id.task_name);

        mCreate=(Button) findViewById(R.id.task_create);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTask();
            }
        });

    }


    private void createNewTask(){

        FireBase fireBase = FireBase.fireBase(this);

        TaskObject taskObject= new TaskObject("categories", "datetime",
                "String description", "String frequency", "String name", "String owner",
                "String priority", "String responsible", "String points", "String status", "String id", "String task");

        fireBase.createTask(taskObject);
        finish();



    }



}
