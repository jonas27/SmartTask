package com.example.joe.smarttask.SmartTask_MainPage.Task;

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

import com.example.joe.smarttask.R;
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
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private String mTaskId;

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

        mDateButton = (Button) v.findViewById(R.id.task_date);
        mDateButton.setText(mTask.getDatetime().toString());
        mDateButton.setEnabled(true);

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.task_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the task's solved property
                mTask.setmCompleted(isChecked);
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
