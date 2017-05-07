package com.example.joe.smarttask.ChooseProfile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileObject;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.PictureScale;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by joe on 06/05/2017.
 */

public class ChooseProfileFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_ChoosePrFr";

    private static final String DIR = "/storage/emulated/0/smarttask/";

    private static List<ProfileObject> sList;
    private static Context sContext;
    private static RecyclerView sRecyclerView;
    private static Adapter sAdapter;

//    Views
    private static Activity activity;
    private static EditText password;

    //    FireBase stuff
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Pass layout xml to the inflater and assign it to View v.
        View v = inflater.inflate(R.layout.recycler_list, container, false);
        sContext = v.getContext();
        activity = this.getActivity();
        sList = ListProfile.getProfileList();


//        get FireBase instances and pull profiles
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        pullProfiles();

        sRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        sRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return v;
    }

    public static void updateUI() {
        if (sRecyclerView != null) {
            sList = ListProfile.getProfileList();
            sAdapter = new Adapter(sList);
            sAdapter.notifyDataSetChanged();
            sRecyclerView.setAdapter(sAdapter);
        }
    }


    //    [Start: RecyclerView Holder and Adapter]
    // Provides a reference to the views for each data item
    private static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView score;
        ImageView icon;
        private ProfileObject profileObject;

        //        bind views here (The Holder defines one list item, which are then coppied)
        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            score = (TextView) itemView.findViewById(R.id.score);
            icon = (ImageView) itemView.findViewById(R.id.profile_image);
        }

        //        specify what happens when click on a list item
        @Override
        public void onClick(View v) {
            //    [Start: Widget for Pincode]
            final Dialog dialog = new Dialog(sContext);
            dialog.setContentView(R.layout.profile_login);
            dialog.setTitle("Set name in String like in toolbar");
            dialog.setCancelable(true);
            password = (EditText) dialog.findViewById(R.id.password);
            Button login = (Button) dialog.findViewById(R.id.ok_btn);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "OnLicklistner works");
                    if (password.getText().toString().equals(profileObject.getPpincode())) {
                        SharedPrefs.setCurrentUser(profileObject.getPname());
                        SharedPrefs.setCurrentProfile(profileObject.getPid());
                        Intent intent = new Intent(sContext, SMMainActivity.class);
                        sContext.startActivity(intent);
                        activity.finish();
                    } else {
                        dialog.cancel();
                        Toast.makeText(sContext, "Placeholder but try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        }

        //    specify individual settings behaviour on layout
        private void bindProfile(ProfileObject mProfileObject) {
            profileObject = mProfileObject;
            Log.d(TAG, "This is Profile with name: " + profileObject.getPname());
            if (profileObject != null) {
                name.setText(profileObject.getPname());
                score.setText(profileObject.getPscore());
                String userID = profileObject.getPid();
                if (userID != "0") {
                    File profileImage = new File(DIR + userID + ".jpg");
                    if (profileImage.exists()) {
                        Log.d(TAG, "Picture exists for: " + userID);
                        Bitmap bitmap = PictureScale.getScaledBitmap(DIR + userID + ".jpg", 80, 80, 2);
                        icon.setImageBitmap(bitmap);
                    } else {
                        Log.d(TAG, "Getting from firebase");
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference currentImage = storageRef.child("images/" + userID + ".jpg");
                        File localFile = null;
                        try {
                            localFile = new File(DIR + userID + ".jpg");
                            localFile.createNewFile();
                            final File finalLocalFile = localFile;
                            currentImage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.d(TAG, "Picture exist");
                                    Bitmap bitmap = PictureScale.getScaledBitmap(finalLocalFile.getAbsolutePath(), 80, 80, 2);
                                    icon.setImageBitmap(bitmap);
                                    sAdapter.notifyDataSetChanged();
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
                    if (profileImage.length() == 0) {
                        icon.setImageDrawable(sContext.getApplicationContext().getResources().getDrawable(R.mipmap.smlogo));
                    }
                } else {
                    icon.setImageDrawable(sContext.getApplicationContext().getResources().getDrawable(R.mipmap.smlogo));
                }
            }
        }
    }

    //    Purpose of the Addapter is to provide the data items for the recycler view (or more general the AdapterView)
    private static class Adapter extends RecyclerView.Adapter<Holder> {
        private List<ProfileObject> list;

        public Adapter(List<ProfileObject> mList) {
            this.list = mList;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(sContext);
            View view = layoutInflater.inflate(R.layout.profile_square_for_choose_fragment, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            ProfileObject profileObject = list.get(position);
            holder.bindProfile(profileObject);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
//    [End: RecyclerView Holder and Adapter]

    //    Firebase loads profiles to check if new user
    private void pullProfiles() {
//    Log.d(TAG, mAuth.getCurrentUser().toString());
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("profile");
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot mDataSnapshot) {
                callback(mDataSnapshot);
                Log.d(TAG, "Getting profiles" + mDataSnapshot.getChildren().toString());
                sAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG + "Err", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);
        //       mPostReference2.addValueEventListener(postListener);
    }


    private void callback(DataSnapshot mDataSnapshot) {
        ListProfile.setDataSnapshot(mDataSnapshot);
        sList = ListProfile.getProfileList();
        updateUI();
    }

}
