package com.example.joe.smarttask.smartaskMain.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.smartaskMain.SmarttaskMainActivity;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.PictureConverter;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * Created by joe on 23/04/2017.
 */

@SuppressLint("ValidFragment")
public class MyProfileFragment extends Fragment {

    private static final String TAG = "CL_ProfileFragment";
    private static final String PROFILE_ID = "profile_id";

    private ProfileObject mProfile;
    private String mProfileId;

    private TextView mViewProfileName;
    private TextView mViewProfileScore;
    private TextView mViewProfilePrivileges;
    private TextView mViewProfileTotalTask;
    private ImageView mProfilePicture;

    private ListOfProfiles mListOfProfiles;
    private String dir = "/storage/emulated/0/smarttask/";
    private static String path;
    private StorageReference storageRef;
    private Button profileviewdone;

    public static MyProfileFragment newInstance(String profileId) {
        Bundle args = new Bundle();
        args.putSerializable(PROFILE_ID, profileId);
        MyProfileFragment fragment = new MyProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_view, container, false);

        mProfile = ListOfProfiles.getProfile(SharedPrefs.getCurrentProfile());
        this.mProfileId = mProfile.getPid();
        path = dir + mProfileId + ".jpg";
        getActivity().setTitle(mProfile.getPname());
        storageRef = FirebaseStorage.getInstance().getReference();


        mViewProfileName = (TextView) v.findViewById(R.id.view_profile_name);
        mViewProfileName.setText(mProfile.getPname());
        mViewProfileScore = (TextView) v.findViewById(R.id.view_profile_score);
        mViewProfileScore.setText(mProfile.getPscore() + getString(R.string.view_profile_addtext_points));
        mViewProfilePrivileges = (TextView) v.findViewById(R.id.view_profile_privileges);
        String privilegesview = null;
        switch (mProfile.getPprivileges()) {
            case (1):
                privilegesview = getString(R.string.smarttask_create_profile_spinner_admin);
                break;
            case (2):
                privilegesview = getString(R.string.smarttask_create_profile_spinner_user);
                break;
            case (3):
                privilegesview = getString(R.string.smarttask_create_profile_spinner_kid);
                break;
        }
        mViewProfilePrivileges.setText(privilegesview + getString(R.string.view_profile_addtext_levek));
        mViewProfileTotalTask = (TextView) v.findViewById(R.id.view_profile_total_task);
        mViewProfileTotalTask.setText(mProfile.getPtotalscore() + getString(R.string.view_profile_addtext_completed_task));

        mProfilePicture = (ImageView) v.findViewById(R.id.profile_image);
        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File newfile = new File(path);
                File folder = new File(dir);
                folder.mkdirs();

                Uri outputFileUri = FileProvider.getUriForFile(SmarttaskMainActivity.getAppContext(), SmarttaskMainActivity.getAppContext().getApplicationContext().getPackageName() + ".provider", newfile);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, 1);
            }
        });

        File profileImage = new File(path);
        if (profileImage.length()!=0) {
            Bitmap bitmap = PictureConverter.getRoundProfilePicture(path,500);
            mProfilePicture.setImageBitmap(bitmap);
        } else {
            mProfilePicture.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.smlogo));}
        return v;
    }

    @Override
    public void onResume() {
        this.mProfile = ListOfProfiles.getProfile(mProfileId);
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            Uri uriFile = Uri.fromFile(new File(path));
            Bitmap bitmap = PictureConverter.getSquareBitmap(dir + mProfileId + ".jpg",1);
            mProfilePicture.setImageBitmap(bitmap);

            StorageReference fileUpload = storageRef.child("images/" + uriFile.getLastPathSegment());
            UploadTask uploadTask = fileUpload.putFile(uriFile);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains path metadata such as size, content-type, and download URL.
                    //  Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }
    }

}