package com.example.joe.smarttask.SmartTask_MainPage.Task;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;

import java.io.File;
import java.io.IOException;

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
    private String dir = "/smarttask/";

    public static TaskFragment newInstance(String taskId) {
        Bundle args = new Bundle();
        args.putSerializable(TASK_ID, taskId);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getDate(long milliSeconds, String dateFormat)
    {

        // Create a DateFormatter object for displaying date in specified format.
        DateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(mTask.getDatetime()));
        return formatter.format(calendar.getTime());
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);




        mTaskDate = (TextView) v.findViewById(R.id.task_date);
        mTaskDate.setText(getDate(Long.parseLong(mTask.getDatetime()),"yyyy/MM/dd  hh:mm"));



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

        mTaskPicture = (Button) v.findViewById(R.id.task_btn_add_picture);
        mTaskPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};

                int permsRequestCode = 200;

                requestPermissions(perms, permsRequestCode);

                String file = dir+mTaskId+".jpg";
                File newfile = new File(file);
                try {
                    newfile.createNewFile();
                    Uri outputFileUri = FileProvider.getUriForFile(SMMainActivity.getAppContext(), SMMainActivity.getAppContext().getApplicationContext().getPackageName() + ".provider", newfile);

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                    startActivityForResult(cameraIntent,1);
                }
                catch (IOException e) {
                    Log.d(TAG,"not creating file "+e);
                }


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
