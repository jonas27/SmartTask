package com.example.joe.smarttask.IntroSlider;

//sub_packages need to import this in order to use R

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joe.smarttask.ChooseProfile.ChooseProfileActivity;
import com.example.joe.smarttask.LogInActivity;
import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.NewTask.NewTaskActivity;
import com.example.joe.smarttask.SmartTask_MainPage.NewTask.NewTaskFragment;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.CreateProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileObject;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Class handles the intro slides - slides inflate intro_activity.xml and are no layout.layouts.fragments (only one lifecycle)
 * Created on 07/02/2017.
 */

public class IntroActivity extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "CL_IntroActivity";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;
    private List<ProfileObject> pList;
    private static boolean loadedList;

    //Intent
    private Intent intent;

    //ViewPager allows flipping through pages
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private IntroColors introColors;

    //array for intro slides which inflate intro_activity.xml
    private int[] intro_layouts;

    //define Elements (Buttons, Views...)
    private TextView[] circles;
    private LinearLayout boxCircles;
    private Button skipBtn, nextBtn, gotitBtn;
    private CheckBox showIntroAgain;

    //    for SharedPrefs instance
    private SharedPrefs sharedPrefs;


//    new user created, go to main
    public static boolean userAdded=false;
    public static boolean taskAdded=true;

    //boolean to show tutorial again
    private boolean skipTutorial;
    private static boolean introWasShown;


    @Override
    public void onResume(){
        super.onResume();
        if(userAdded && !taskAdded){
            Intent intent= new Intent(this, NewTaskActivity.class);
            startActivity(intent);
            return;
        }
        else if(userAdded && taskAdded){
            Intent intent= new Intent(this, SMMainActivity.class);
            startActivity(intent);
            finish();
        }
        if(SharedPrefs.getCurrentProfile().equals("")){
            Intent intent= new Intent(this, ChooseProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent= new Intent(this, SMMainActivity.class);
            startActivity(intent);
            finish();
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        intent = getIntent();
        super.onCreate(savedInstanceState);

        //        get sharedPrefs instance and open app if set to skip intro
        sharedPrefs = SharedPrefs.getSharedPrefs(this);


//        get Firebase user
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        pullProfiles();


        //set's the content (layout)
        setContentView(R.layout.intro_view_menu);


        //Bind all elemnts to objects
        skipBtn = (Button) findViewById(R.id.btnSkip);
        nextBtn = (Button) findViewById(R.id.btnNext);
        boxCircles = (LinearLayout) findViewById(R.id.boxCircles);
        showIntroAgain = (CheckBox) findViewById(R.id.showAgain);

        //create Object for Colors
        introColors = new IntroColors();

        //add inflating layouts before pageview as it gives nullpointer exception
        intro_layouts = new int[]{
                R.layout.intro_item_0,
                R.layout.intro_item_1,
                R.layout.intro_item_2,
                R.layout.intro_item_3,
                R.layout.intro_item_4,
        };


        //adds circles
        addCircles(0);

        //set's status bar color like background
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        //ViewPager allows flipping through pages. ID is defined
        viewPager = (ViewPager) findViewById(R.id.view_pager_intro_view_menu);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);

        //methods for changes in current slide number
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //Listener for when new page is selected
            @Override
            public void onPageSelected(int position) {
                //last page
                if (position == intro_layouts.length - 1) {
                    nextBtn.setText(R.string.intro_got_it);
                }
                //else
                else {
                    nextBtn.setText(R.string.intro_next);
                }
                addCircles(position);

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //actions for buttons
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == intro_layouts.length - 1) {
                    openApp();
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });


    }


    private void addCircles(int position) {
        boxCircles.removeAllViews();
        circles = new TextView[intro_layouts.length];
        for (int counter = 0; counter < intro_layouts.length; counter++) {
            circles[counter] = new TextView(this);
            circles[counter].setText(Html.fromHtml("&#8226;"));
            circles[counter].setTextSize(35);
            circles[counter].setTextColor(introColors.chooseColor(position, counter));
            boxCircles.addView(circles[counter]);
        }
    }

    //opens main app
    private void openApp() {
        if(SharedPrefs.getCurrentProfile().equals("") && loadedList && pList.size()==0 && !userAdded){
            taskAdded=false;
            Intent intent = new Intent(this, CreateProfile.class);
            startActivity(intent);
        }else if(loadedList  && pList.size()>0 && !taskAdded){
            intent = new Intent(this, NewTaskActivity.class);
            startActivity(intent);
        }
        else if(loadedList && pList.size()>0 && taskAdded){
            Log.d(TAG, "profiles are there: " + loadedList);
            intent = new Intent(this, ChooseProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else if(SharedPrefs.getCurrentProfile().compareToIgnoreCase("")==0){
            Log.d(TAG, "Kein Profile");
            intent = new Intent(this, ChooseProfileActivity.class);
            startActivity(intent);
            finish();
        }
        else if(SharedPrefs.getCurrentProfile().compareToIgnoreCase("")!=0){
            Log.d(TAG, "Main wird geladen von skip");
            intent = new Intent(this, SMMainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "Waiting for a connection!", Toast.LENGTH_LONG).show();
        }
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        skipTutorial = ((CheckBox) view).isChecked();
    }


    //If activity goes into pause, it writes preference of showing tutorial again in file.
    @Override
    public void onStop() {
        if (skipTutorial) {
            sharedPrefs = SharedPrefs.getSharedPrefs(this);
            //modify boolean showIntroAgain
            sharedPrefs.setSharedPreferencesIntro(!skipTutorial);
        }
        introWasShown=true;
        super.onStop();
    }


    /**
     * Base class providing the adapter to populate pages inside of a ViewPager
     * instantiates and sets new Window/Container as current. Destroys old window
     * Overwritten Methods below  are requirement to use PagerAdapter
     */


    private class ViewPagerAdapter extends PagerAdapter {


        private LayoutInflater layoutInflater;

        //Create the page for the given position.
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(intro_layouts[position], container, false);
            container.addView(view);
            return view;
        }

        //Return the number of views available (#numbers of inflating pages)
        @Override
        public int getCount() {
            return intro_layouts.length;
        }

        //???no idea what it does :D maybe checks if the current view is really an object???
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //Remove a page for the given position.
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }





//    Firebase loads profiles to check if new user
private void pullProfiles() {
//    Log.d(TAG, mAuth.getCurrentUser().toString());
    mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("profile");
    postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot mDataSnapshot) {
            callback(mDataSnapshot);
            Log.d(TAG,"Getting profiles"+mDataSnapshot.getChildren().toString());
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
        pList=ListProfile.getProfileList();
        loadedList=true;

    }


}
