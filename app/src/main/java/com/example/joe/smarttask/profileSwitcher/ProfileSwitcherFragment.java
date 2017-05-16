package com.example.joe.smarttask.profileSwitcher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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
import com.example.joe.smarttask.smartaskMain.profile.ProfileCreatorActivity;
import com.example.joe.smarttask.smartaskMain.profile.ListOfProfiles;
import com.example.joe.smarttask.smartaskMain.profile.ProfileObject;
import com.example.joe.smarttask.smartaskMain.SmarttaskMainActivity;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.PictureConverter;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
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
 * This is used as a Fragment and as a Dialog.
 * Fragment when no profile is selected after Intro and fragment in SmarttaskMain
 *
 * 100% made by THE SMARTTASK TEAM!
 */

public class ProfileSwitcherFragment extends DialogFragment {

    //TAG for Logs
    private static final String TAG = "CL_ChoosePrFr";

    private static final String DIR = "/storage/emulated/0/smarttask/";
    private static final String FROM_MAIN="fromMain";

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

    private Boolean fromMain;


    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static ProfileSwitcherFragment newInstance(boolean fromMain) {
        ProfileSwitcherFragment f = new ProfileSwitcherFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putBoolean(FROM_MAIN, fromMain);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        fromMain= getArguments().getBoolean(FROM_MAIN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Pass layout xml to the inflater and assign it to View v.
        View v = inflater.inflate(R.layout.recycler_profile, container, false);

        sContext = v.getContext();
        activity = this.getActivity();
        sList = ListOfProfiles.getProfileList();

//        get FireBase instances and pull profiles
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        pullProfiles();

        Button close = (Button) v.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button add = (Button) v.findViewById(R.id.add_profile);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileCreatorActivity.class);
                getContext().startActivity(intent);
                dismiss();
            }
        });

        sRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        sRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return v;
    }

    public static void updateUI() {
        if (sRecyclerView != null) {
            sList = ListOfProfiles.getProfileList();
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
            dialog.setTitle(profileObject.getPname());
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
                        Intent intent = new Intent(sContext, SmarttaskMainActivity.class);
                        sContext.startActivity(intent);
                        dialog.cancel();
                        activity.finish();
                    } else {
                        Toast.makeText(sContext, sContext.getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }
            });
            dialog.show();
        }

        //    specify individual settings behaviour on layout
        private void bindProfile(ProfileObject mProfileObject) {
            profileObject = mProfileObject;
            if (profileObject != null) {
                name.setText(profileObject.getPname());
                score.setText(Integer.toString(profileObject.getPscore()));
                String userID = profileObject.getPid();
                String path=DIR + userID + ".jpg";
                if(userID!="0"){
                    File profileImage = new File(path);
                    if (profileImage.length()!=0) {
                        icon.setImageBitmap(PictureConverter.getRoundProfilePicture(path, 500));
                    } else {
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference currentImage = storageRef.child("images/" + userID + ".jpg");
                        File localFile = null;
                        try {
                            localFile = new File(path);
                            localFile.createNewFile();
                            final File finalLocalFile = localFile;
                            currentImage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.d(TAG, "Picture exist");
                                    icon.setImageBitmap(PictureConverter.getRoundProfilePicture(finalLocalFile.getAbsolutePath(), 500));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    Log.d(TAG, "NO Picture exist");
                                    icon.setImageDrawable(sContext.getResources().getDrawable(R.mipmap.smlogo));
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
        ListOfProfiles.setDataSnapshot(mDataSnapshot);
        sList = ListOfProfiles.getProfileList();
        updateUI();
    }

}
