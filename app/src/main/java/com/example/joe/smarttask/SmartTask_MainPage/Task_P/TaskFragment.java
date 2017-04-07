package com.example.joe.smarttask.SmartTask_MainPage.Task_P;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.joe.smarttask.R;

/**
 * Created by joe on 14/03/2017.
 */

public class TaskFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CLASS_TaskFragment";

    private Task mTask;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_smarttask, container, false);

        mDateButton = (Button) v.findViewById(R.id.task_date);
//        mDateButton.setText(mTask.getDatetime().toString());
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


}
