package com.example.joe.smarttask.ChooseProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.SortList;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileObject;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SettingsList;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskFragment;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskPagerActivity;
import com.github.pavlospt.roundedletterview.RoundedLetterView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by joe on 06/05/2017.
 */

public class ChooseProfileFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_ChoosePrFr";

    private static List<ProfileObject> sList;
    private static Context sContext;
    private static RecyclerView sRecyclerView;

    private static Adapter sAdapter;

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
        sList = ListProfile.getProfileList();

//        get FireBase instances and pull profiles
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
//        pullProfiles();

        sRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        sRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        updateUI();
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

//            Intent intent = SubMenuActivity.newIntent(sContext, mSettingsObject.getmTitle());
//            sContext.startActivity(intent);
//            updateUI();
//            sCallbacks.onItemSelected(mSettingsObject);

        }

        //    specify individual settings behaviour on layout
        private void bindProfile(ProfileObject mProfileObject) {
            profileObject = mProfileObject;
            if (profileObject != null) {
                name.setText(profileObject.getPname());
                score.setText(profileObject.getPscore());
                icon.setImageDrawable(sContext.getResources().getDrawable(R.mipmap.smlogo));
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
}
