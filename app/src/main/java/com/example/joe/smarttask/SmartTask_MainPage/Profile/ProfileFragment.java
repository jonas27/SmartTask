package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Created by joe on 23/04/2017.
 */

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {

    private static final String TAG = "CL_ProfileFragment";
    private static final String PROFILE_ID = "profile_id";

    private ProfileObject mProfile;
    private String mProfileId;

    private TextView mViewProfileName;
    private TextView mViewProfileScore;
    private TextView mViewProfilePrivileges;
    private TextView mViewProfileTotalTask;
    private ImageView mProfilePicture;

    private ListProfile mListProfile;
    private String dir = "/storage/emulated/0/smarttask/";
    private String file;
    private StorageReference storageRef;


    public static ProfileFragment newInstance (String profileId) {
        Bundle args = new Bundle();
        args.putSerializable(PROFILE_ID, profileId);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"so far so good");


//        mProfileId = (String) getArguments().getSerializable(PROFILE_ID);
  //      mListProfile = mListProfile.list(getContext());
    //    this.mProfile = mListProfile.getProfile(mProfileId);
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_view, container, false);

//        on logout shared prefs are deleted and user should sign into profile again
       mProfile = ListProfile.getProfileList().get(0);
       this.mProfileId=mProfile.getPid();
       Log.d(TAG, "Profile id: " + mProfileId);
       storageRef = FirebaseStorage.getInstance().getReference();

        mViewProfileName = (TextView) v.findViewById(R.id.view_profile_name);
        mViewProfileName.setText(mProfile.getPname());

        mViewProfileScore = (TextView) v.findViewById(R.id.view_profile_score);
        mViewProfileScore.setText(mProfile.getPscore() + getString(R.string.view_profile_addtext_points));

        mViewProfilePrivileges = (TextView) v.findViewById(R.id.view_profile_privileges);
        mViewProfilePrivileges.setText(mProfile.getPprivileges() + getString(R.string.view_profile_addtext_levek));

        mViewProfileTotalTask = (TextView) v.findViewById(R.id.view_profile_total_task);
        mViewProfileTotalTask.setText(mProfile.getPtotalscore() + getString(R.string.view_profile_addtext_completed_task));

       getActivity().setTitle(mProfile.getPname());

       String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
       int permsRequestCode = 200;
       requestPermissions(perms, permsRequestCode);

       mProfilePicture = (ImageView) v.findViewById(R.id.profile_image);
       mProfilePicture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.d(TAG, "picture was clicked ");
               file = dir + mProfileId + ".jpg";
               File newfile = new File(file);
               File folder = new File(dir);
               folder.mkdirs();
               try {
                   newfile.createNewFile();

                   Log.d(TAG, "new file created");

                   Uri outputFileUri = FileProvider.getUriForFile(SMMainActivity.getAppContext(), SMMainActivity.getAppContext().getApplicationContext().getPackageName() + ".provider", newfile);

                   Log.d(TAG, "new file created");

                   Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                   startActivityForResult(cameraIntent, 1);
               } catch (IOException e) {
                   Log.d(TAG, "not creating file " + e);
               }
           }
       });

       File taskImage = new File(dir + mProfileId + ".jpg");
       if (taskImage.exists()) {
           Bitmap bitmap = BitmapFactory.decodeFile(taskImage.getAbsolutePath());
           mProfilePicture.setImageBitmap(bitmap);
       } else {
           Log.d(TAG, "Getting from firebase");
           StorageReference currentImage = storageRef.child("images/" + mProfileId + ".jpg");

           File localFile = null;
           try {
               localFile = new File(dir + mProfileId + ".jpg");
               localFile.createNewFile();
               final File finalLocalFile = localFile;
               currentImage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                       Log.d(TAG, "Picture exist");
                       Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                       mProfilePicture.setImageBitmap(bitmap);


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


       return v;
    }



    @Override
    public void onResume(){
        mListProfile = ListProfile.list();
        this.mProfile=mListProfile.getProfile(mProfileId);
        super.onResume();
        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.d(TAG, file);
            Bitmap bitmap = BitmapFactory.decodeFile(file);
            mProfilePicture.setImageBitmap(bitmap);
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