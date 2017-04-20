package com.example.joe.smarttask.SmartTask_MainPage.Task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;

import org.w3c.dom.Text;

/**
 * Created by joe on 14/03/2017.
 */

public class TaskFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_TaskFragment";

    private static final String TASK_ID = "task_id";

    private Toolbar toolbar;

    private TaskObject mTask;
    private String mTaskId;

//    [Start: Define views for task]
    private TextView mTaskDate;
    private CheckBox mTaskSolved;
    private Button mTaskEdit;
    private TextView mTaskName;
    private TextView mTaskResponsible;
    private TextView mTaskDescription;
    private TextView mTaskCategory;
    private TextView mTaskPriority;
    private TextView mTaskPoints;
    private TextView mTaskStatus;
    private Button mTaskDone;
    private Button mTaskPicture;
    private Button mTaskConfirm;
    private ImageView mTaskImageView;
//    [End: Define views for task]


    private  ListTask mList;

    public static TaskFragment newInstance(String taskId) {
        Bundle args = new Bundle();
        args.putSerializable(TASK_ID, taskId);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_task);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mTaskId= (String) getArguments().getSerializable(TASK_ID);
        mList = ListTask.list(getContext());
        this.mTask=mList.getTask(mTaskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        mTaskDate = (TextView) v.findViewById(R.id.task_date);
        mTaskDate.setText(mTask.getDatetime());

        mTaskSolved = (CheckBox) v.findViewById(R.id.task_solved);
        if(mTask.getStatus().toString().equals("true")){mTaskSolved.setChecked(true);}else{mTaskSolved.setChecked(false);}

        mTaskEdit = (Button) v.findViewById(R.id.task_btn_edit);

        mTaskName =(TextView) v.findViewById(R.id.task_name);
        mTaskName.setText(mTask.getName());

        mTaskResponsible = (TextView) v.findViewById(R.id.task_responsible);
        mTaskResponsible.setText(mTask.getResponsible());

        mTaskDescription = (TextView) v.findViewById(R.id.task_description);
        mTaskDescription.setText(mTask.getResponsible());

        mTaskCategory = (TextView) v.findViewById(R.id.task_category);
        mTaskCategory.setText(mTask.getCategories());

        mTaskPriority = (TextView) v.findViewById(R.id.task_category);
        mTaskPriority.setText(mTask.getPriority());

        mTaskPoints =(TextView) v.findViewById(R.id.task_points);
        mTaskPoints.setText(mTask.getPoints());

        mTaskEdit = (Button) v.findViewById(R.id.task_btn_edit);
        mTaskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent();
            }
        });






        return v;
    }

    @Override
    public void onResume(){
        mList = ListTask.list(getContext());
        this.mTask=mList.getTask(mTaskId);
        super.onResume();
    }

}
