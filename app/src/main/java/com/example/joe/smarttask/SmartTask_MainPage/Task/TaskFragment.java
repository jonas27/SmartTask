package com.example.joe.smarttask.SmartTask_MainPage.Task;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

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
    private String file;

    private  ListTask mList;
    private String dir = "/storage/emulated/0/smarttask/";
    private StorageReference storageRef;

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
        storageRef = FirebaseStorage.getInstance().getReference();

        mTaskSolved = (ImageView) v.findViewById(R.id.task_check);

        mTaskImageView = (ImageView) v.findViewById(R.id.task_imageview);

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
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};

        int permsRequestCode = 200;

        requestPermissions(perms, permsRequestCode);
        mTaskPicture = (Button) v.findViewById(R.id.task_btn_add_picture);
        mTaskPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                file = dir+mTaskId+".jpg";
                File newfile = new File(file);
                File folder = new File(dir);
                folder.mkdirs();
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

        File taskImage = new File(dir+mTaskId+".jpg");

        if(taskImage.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(taskImage.getAbsolutePath());
            mTaskImageView.setImageBitmap(bitmap);
        }else{
            Log.d(TAG,"Getting from firebase");
            StorageReference currentImage = storageRef.child("images/"+mTaskId+".jpg");

            File localFile = null;
            try {
                localFile = new File(dir+mTaskId+".jpg");
                localFile.createNewFile();
                final File finalLocalFile = localFile;
                currentImage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG,"Picture exist");
                        Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                        mTaskImageView.setImageBitmap(bitmap);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.d(TAG,"NO Picture exist");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return v;
    }

    @Override
    public void onResume(){
        mList = ListTask.list(getContext());
        this.mTask=mList.getTask(mTaskId);
        super.onResume();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.d(TAG,file);
            Bitmap bitmap = BitmapFactory.decodeFile(file);
            mTaskImageView.setImageBitmap(bitmap);
            //Uri u = Uri.parse("file://"+file);
            Log.d(TAG, "Pic saved");
            //mTaskImageView.setImageURI(u);
            Uri uriFile = Uri.fromFile(new File(file));
            UploadTask uploadTask;

            StorageReference fileUpload = storageRef.child("images/"+uriFile.getLastPathSegment());
            uploadTask = fileUpload.putFile(uriFile);

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.d(TAG,"failed");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Log.d(TAG,"uploaded");
                  //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }
    }
}
