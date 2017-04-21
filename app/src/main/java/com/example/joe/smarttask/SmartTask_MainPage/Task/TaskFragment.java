package com.example.joe.smarttask.SmartTask_MainPage.Task;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;

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
    private ImageView mTaskSolved;
    private ImageView mTaskUnSolved;
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



        mTaskSolved = (ImageView) v.findViewById(R.id.task_check);
        mTaskUnSolved = (ImageView) v.findViewById(R.id.task_uncheck);

        if(mTask.getStatus().toString().equals("true")){mTaskSolved.setVisibility(View.VISIBLE);mTaskUnSolved.setVisibility(View.INVISIBLE);
        }else{mTaskSolved.setVisibility(View.INVISIBLE);mTaskUnSolved.setVisibility(View.VISIBLE);}

        mTaskEdit = (Button) v.findViewById(R.id.task_btn_edit);

        mTaskName =(TextView) v.findViewById(R.id.task_name);
        mTaskName.setText(mTask.getName());

        mTaskResponsible = (TextView) v.findViewById(R.id.task_responsible);
        mTaskResponsible.setText(mTask.getResponsible());

        mTaskDescription = (TextView) v.findViewById(R.id.task_description);
        mTaskDescription.setText(mTask.getDescription());

        mTaskCategory = (TextView) v.findViewById(R.id.task_category);
        mTaskCategory.setText(mTask.getCategories());

        mTaskPriority = (TextView) v.findViewById(R.id.task_priority);
        mTaskPriority.setText(mTask.getPriority());

        mTaskPoints =(TextView) v.findViewById(R.id.task_points);
        mTaskPoints.setText(mTask.getPoints());

//        mTaskEdit = (Button) v.findViewById(R.id.task_btn_edit);
//        mTaskEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mTask.setStatus("true");
//                FireBase f=FireBase.fireBase(getContext());
//                f.createTask(mTask);
//            }
//        });

        mTaskConfirm = (Button) v.findViewById(R.id.task_btn_confirm);
        mTaskConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setStatus("true");
                FireBase f=FireBase.fireBase(getContext());
                f.createTask(mTask);
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
