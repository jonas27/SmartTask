package com.smarttask17.joe.smarttask.smartaskMain.task;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.list.ListFragment;
import com.smarttask17.joe.smarttask.smartaskMain.list.ListOfTasks;
import com.smarttask17.joe.smarttask.smartaskMain.newTask.NewTaskActivity;
import com.smarttask17.joe.smarttask.smartaskMain.newTask.NewTaskFragment;
import com.smarttask17.joe.smarttask.smartaskMain.profile.ListOfProfiles;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.FireBase;
import com.smarttask17.joe.smarttask.smartaskMain.profile.ProfileObject;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.PictureConverter;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by joe on 14/03/2017.
 */

public class TaskFragment extends Fragment {

    private static final String TAG = "CL_TaskFragment";
    private static final String TASK_ID = "task_id";

    private TaskObject mTask;
    private String mTaskId;

    //    [Start: Define views for task]
    private TextView mTaskDate;
    private ImageView mTaskSolved;
    private Button mTaskUnSolved;
    private ImageView mTaskIcon;
    private Button mTaskEdit;
    private Button mTaskDelete;
    private Button mTaskUnConfirm;
    private TextView mTaskName;
    private TextView mTaskResponsible;
    private TextView mTaskDescription;
    private TextView mTaskCategory;
    private TextView mTaskPriority;
    private TextView mTaskPoints;
    private TextView mTaskFrequency;
    private TextView mTaskDay;
    private TextView mTaskMonth;
    private Button mTaskComplete;
    private Button mTaskDone;
    private Button mTaskPicture;
    private Button mTaskConfirm;
    private ImageView mTaskImageView;
    //    [End: Define views for task]
    private String file;

    private ListOfTasks mList;
    private String dir = "/storage/emulated/0/smarttask/";
    private StorageReference storageRef;
    private GregorianCalendar gregorianCalendar;
    ProfileObject p;
    private Object mProfileId;

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
        mTaskId = (String) getArguments().getSerializable(TASK_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.task_tabletmode, container, false);

        this.mTask = ListOfTasks.getTask(mTaskId);
        if (mTask.getPriority() == -1) {
            return v;
        }


        storageRef = FirebaseStorage.getInstance().getReference();

        mTaskSolved = (ImageView) v.findViewById(R.id.task_full_star);
        mTaskUnSolved = (Button) v.findViewById(R.id.task_btn_unconfirm);


        mTaskImageView = (ImageView) v.findViewById(R.id.task_imageview);


        mTaskName = (TextView) v.findViewById(R.id.task_name);
        mTaskName.setText(mTask.getName());

        mTaskResponsible = (TextView) v.findViewById(R.id.task_responsible);
        mTaskResponsible.setText(mTask.getResponsible());

        mTaskDescription = (TextView) v.findViewById(R.id.task_description);
        mTaskDescription.setText(mTask.getDescription());

        mTaskCategory = (TextView) v.findViewById(R.id.task_category);
        mTaskCategory.setText(mTask.getCategories());

        mTaskPriority = (TextView) v.findViewById(R.id.task_priority);
        switch (mTask.getPriority()) {
            case 0: {
                mTaskPriority.setText(getString(R.string.newtask_spinner_high));
                break;
            }
            case 1: {
                mTaskPriority.setText(getString(R.string.newtask_spinner_middle));
                break;
            }
            case 2: {
                mTaskPriority.setText(getString(R.string.newtask_spinner_low));
                break;
            }
            case 3: {
                break;
            }
        }

        mTaskFrequency = (TextView) v.findViewById(R.id.task_frequency);
        switch (mTask.getFrequency()) {
            case 0: {
                mTaskFrequency.setText("Once");
                break;
            }
            case 1: {
                mTaskFrequency.setText(R.string.newtask_spinner_daily);
                break;
            }
            case 2: {
                mTaskFrequency.setText(R.string.newtask_spinner_weekly);
                break;
            }
            case 3: {
                mTaskFrequency.setText(R.string.newtask_spinner_monthly);
                break;
            }
            case 4: {
                mTaskFrequency.setText(R.string.newtask_spinner_yearly);
                break;
            }
        }

        mTaskPoints = (TextView) v.findViewById(R.id.task_points);
        mTaskPoints.setText(Integer.toString(mTask.getPoints()));

        gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis((mTask.getDatetime()));
        mTaskDay = (TextView) v.findViewById(R.id.task_day);
        mTaskDay.setText(Integer.toString(gregorianCalendar.get(Calendar.DAY_OF_MONTH)));
        mTaskMonth = (TextView) v.findViewById(R.id.task_month);
        mTaskMonth.setText(gregorianCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
        mTaskDate = (TextView) v.findViewById(R.id.task_date);
        mTaskDate.setText(new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(gregorianCalendar.getTime()));

        mTaskEdit = (Button) v.findViewById(R.id.task_btn_edit);
        mTaskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTaskFragment.taskObject = mTask;
                Intent intent = new Intent(getContext(), NewTaskActivity.class);
                startActivity(intent);
                if (!ListFragment.tabletMode) {
                    getActivity().finish();
                }
            }
        });
        mTaskDelete = (Button) v.findViewById(R.id.task_btn_delete);
        mTaskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBase fireBase = FireBase.fireBase(getContext());
                fireBase.deleteTask(mTask.getId());
                getActivity().finish();
            }
        });
        mTaskUnConfirm = (Button) v.findViewById(R.id.task_btn_unconfirm);
        mTaskUnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBase fireBase = FireBase.fireBase(getContext());
                mTask.setStatus(false);
                Toast.makeText(getContext(), R.string.ViewTaskUnConfirmButton, Toast.LENGTH_SHORT).show();
                fireBase.createTask(mTask);
                getActivity().finish();
            }
        });
        mTaskComplete = (Button) v.findViewById(R.id.task_btn_complete);
        mTaskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setmCompleted(true);
                Toast.makeText(getContext(), R.string.ViewTaskDoneButton, Toast.LENGTH_SHORT).show();
            }
        });
        mTaskConfirm = (Button) v.findViewById(R.id.task_btn_confirm);
        mTaskConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatepoints();
                FireBase fireBase = FireBase.fireBase(getContext());

                Log.d(TAG, mTaskFrequency.getText().toString());
                switch (mTask.getFrequency()) {
                    case 0: {
                        mTask.setStatus(true);
                        Toast.makeText(getContext(), R.string.ViewTaskConfirmButton, Toast.LENGTH_SHORT).show();
                        fireBase.createTask(mTask);
                        getActivity().finish();
                        break;
                    }
                    case 1: {
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTimeInMillis((mTask.getDatetime()));
                        cal.add(Calendar.DAY_OF_YEAR, 1);
                        mTask.setDatetime((cal.getTimeInMillis()));
                        fireBase.createTask(mTask);
                        Toast.makeText(getContext(), R.string.ViewTaskConfirmMoveOneDay, Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        break;
                    }
                    case 2: {
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTimeInMillis((mTask.getDatetime()));
                        cal.add(Calendar.WEEK_OF_YEAR, 1);
                        Toast.makeText(getContext(), R.string.ViewTaskConfirmMoveOneWeek, Toast.LENGTH_SHORT).show();
                        mTask.setDatetime((cal.getTimeInMillis()));
                        fireBase.createTask(mTask);
                        getActivity().finish();
                        break;
                    }
                    case 3: {
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTimeInMillis((mTask.getDatetime()));
                        Toast.makeText(getContext(), R.string.ViewTaskConfirmMoveOneMonth, Toast.LENGTH_SHORT).show();
                        cal.add(Calendar.MONTH, 1);
                        mTask.setDatetime(cal.getTimeInMillis());
                        fireBase.createTask(mTask);
                        getActivity().finish();
                        break;
                    }
                    case 4: {
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTimeInMillis((mTask.getDatetime()));
                        cal.add(Calendar.YEAR, 1);
                        mTask.setDatetime(cal.getTimeInMillis());
                        Toast.makeText(getContext(), R.string.ViewTaskConfirmMoveOneYear, Toast.LENGTH_SHORT).show();
                        fireBase.createTask(mTask);
                        getActivity().finish();
                        break;
                    }
                }
                fireBase.createTask(mTask);
                getActivity().finish();
            }
        });

        mTaskIcon = (ImageView) v.findViewById(R.id.task_responsible_image);

        if (mTask.getResponsible().compareTo("") != 0) {
            String path = dir + ListOfProfiles.getProfileByName(mTask.getResponsible()).getPid() + ".jpg";
            File profileImage = new File(path);
            if (profileImage.length() != 0) {
                if (profileImage.length() > 0) {
                    mTaskIcon.setImageBitmap(PictureConverter.getRoundProfilePicture(path, 100));
                }
            }
        }

        mTaskPicture = (Button) v.findViewById(R.id.task_btn_add_picture);
        mTaskPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file = dir + mTaskId + ".jpg";
                File newfile = new File(file);
                File folder = new File(dir);
                folder.mkdirs();
                try {
                    newfile.createNewFile();
                    Uri outputFileUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getApplicationContext().getPackageName() + ".provider", newfile);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(cameraIntent, 1);
                } catch (IOException e) {
                    Log.d(TAG, "not creating file " + e);
                }
            }
        });
        File taskImage = new File(dir + mTaskId + ".jpg");
        String path = dir + mTaskId + ".jpg";
        if (taskImage.length()!=0) {
            Log.d(TAG, "IS this in tablet mode? " + ListFragment.tabletMode);
            if(ListFragment.tabletMode){
            mTaskImageView.setImageBitmap(PictureConverter.getSquareBitmap(path, 2));}
            else{
                mTaskImageView.setImageBitmap(PictureConverter.getSquareBitmap(taskImage.getAbsolutePath(), 2));
            }
        } else {
            Log.d(TAG, "Getting from firebase");
            StorageReference currentImage = storageRef.child("images/" + mTaskId + ".jpg");
            File localFile = null;
            try {
                localFile = new File(dir + mTaskId + ".jpg");
                localFile.createNewFile();
                final File finalLocalFile = localFile;
                currentImage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "Picture exist");
                        Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                        mTaskImageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.d(TAG, "NO Picture exist");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mTask.getStatus()) {
            mTaskSolved.setVisibility(View.VISIBLE);
            mTaskUnSolved.setVisibility(View.INVISIBLE);
            mTaskConfirm.setVisibility(View.INVISIBLE);
            mTaskUnConfirm.setVisibility(View.VISIBLE);
            mTaskComplete.setVisibility(View.INVISIBLE);
        } else {
            mTaskSolved.setVisibility(View.INVISIBLE);
            mTaskUnSolved.setVisibility(View.VISIBLE);
            mTaskConfirm.setVisibility(View.VISIBLE);
            mTaskUnConfirm.setVisibility(View.INVISIBLE);
            mTaskComplete.setVisibility(View.VISIBLE);
        }
        p = ListOfProfiles.getProfile(SharedPrefs.getCurrentProfile());
        if (p == null) {
            Log.d(TAG, "current profile is null");
        }
        Log.d(TAG, "current profile " + SharedPrefs.getCurrentProfile());

        storageRef = FirebaseStorage.getInstance().getReference();

        if (p.getPprivileges() == 3) {
            mTaskDelete.setVisibility(View.INVISIBLE);
            mTaskEdit.setVisibility(View.INVISIBLE);
            mTaskConfirm.setVisibility(View.INVISIBLE);
            mTaskUnConfirm.setVisibility(View.INVISIBLE);

        }

//        if separator (list item to show adds)
        if (mTask.getPriority() == (-1)) {
            mTaskDate.setVisibility(View.INVISIBLE);
            mTaskSolved.setVisibility(View.INVISIBLE);
            mTaskEdit.setVisibility(View.INVISIBLE);
            mTaskDelete.setVisibility(View.INVISIBLE);
            mTaskName.setVisibility(View.INVISIBLE);
            mTaskResponsible.setVisibility(View.INVISIBLE);
            mTaskDescription.setVisibility(View.INVISIBLE);
            mTaskCategory.setVisibility(View.INVISIBLE);
            mTaskPriority.setVisibility(View.INVISIBLE);
            mTaskPoints.setVisibility(View.INVISIBLE);
//            mTaskStatus.setVisibility(View.INVISIBLE);
//            mTaskDone.setVisibility(View.INVISIBLE);
            mTaskPicture.setVisibility(View.INVISIBLE);
            mTaskConfirm.setVisibility(View.INVISIBLE);
            mTaskImageView.setVisibility(View.INVISIBLE);
            mTaskSolved.setVisibility(View.INVISIBLE);
            mTaskUnSolved.setVisibility(View.INVISIBLE);
            ImageView iconResponsible = (ImageView) v.findViewById(R.id.task_responsible_image);
            iconResponsible.setVisibility(View.INVISIBLE);
            ImageView iconDate = (ImageView) v.findViewById(R.id.task_date_image);
            iconDate.setVisibility(View.INVISIBLE);
            ImageView iconCategory = (ImageView) v.findViewById(R.id.task_category_image);
            iconCategory.setVisibility(View.INVISIBLE);
            ImageView iconPoints = (ImageView) v.findViewById(R.id.task_points_image);
            iconPoints.setVisibility(View.INVISIBLE);
        }
        return v;
    }

    public void updatepoints() {
        Iterator<ProfileObject> itr = ListOfProfiles.getProfileList().iterator();
        while (itr.hasNext()) {
            p = itr.next();
            if (p.getPname().equals(mTask.getResponsible())) {
                p.setPtotalscore(p.getPtotalscore() + 1);
                p.setPscore(p.getPscore() + mTask.getPoints());
                FireBase fireBase = FireBase.fireBase(getContext());
                fireBase.createProfile(p);
                return;
            }

        }

    }

    @Override
    public void onResume() {
        this.mTask = ListOfTasks.getTask(mTaskId);
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.d(TAG, file);
            Bitmap bitmap = BitmapFactory.decodeFile(file);
            mTaskImageView.setImageBitmap(bitmap);
            //Uri u = Uri.parse("file://"+file);
            Log.d(TAG, "Pic saved");
            //mTaskImageView.setImageURI(u);
            Uri uriFile = Uri.fromFile(new File(file));
            UploadTask uploadTask;

            StorageReference fileUpload = storageRef.child("images/" + uriFile.getLastPathSegment());
            uploadTask = fileUpload.putFile(uriFile);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.d(TAG, "failed");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Log.d(TAG, "uploaded");
                    //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }
    }


}
