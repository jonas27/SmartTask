package com.example.joe.smarttask.introSlider;

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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joe.smarttask.profileSwitcher.ProfileSwitcherActivity;
import com.example.joe.smarttask.R;
import com.example.joe.smarttask.smartaskMain.list.ListOfTasks;
import com.example.joe.smarttask.smartaskMain.newTask.NewTaskActivity;
import com.example.joe.smarttask.smartaskMain.profile.ProfileCreatorActivity;
import com.example.joe.smarttask.smartaskMain.profile.ListOfProfiles;
import com.example.joe.smarttask.smartaskMain.profile.ProfileObject;
import com.example.joe.smarttask.smartaskMain.SmarttaskMainActivity;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
import com.example.joe.smarttask.smartaskMain.task.TaskObject;
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
 *
 * It also handles the login process
 *  1. Download user list and task list
 *  2. If Users set the intro to not show again and is logged into a profile, go straight to SmartTaskMain; if not:
 *  2. Check if the user has a profile - if yes go to ProfileChooser; if not go to ProfileCreator
 *  3. If there is no task go to NewTask; if not:
 *  4. Go to SmartTaskMain
 *
 * Created 80% by us.
 */

public class IntroActivity extends AppCompatActivity {

    //TAG for Logs
    private static final String TAG = "CL_IntroActivity";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;
    private static List<ProfileObject> pList;
    private static List<TaskObject> tList;

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
    private Button skipBtn, nextBtn;
    private CheckBox showIntroAgain;
    private boolean skipButtonPressed;
    //    for SharedPrefs instance
    private SharedPrefs sharedPrefs;

    //boolean to show tutorial again
    private boolean skipTutorial=false;
    private static boolean introWasShown;
    private static Display display;
    private boolean first;
    private TextView welcomeDes1;
    private TextView welcomeTitel1;
    private TextView welcomeSwipeText;
    private boolean noAnim;
    private TextView descriptionTitle;


    @Override
    public void onResume() {
        super.onResume();
openApp();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get sharedPrefs instance and open app if set to skip intro
        sharedPrefs = SharedPrefs.getSharedPrefs(getApplicationContext());

//        get Firebase user
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        pullProfilesAndTasks();

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
                R.layout.intro_item_3,
                R.layout.intro_item_1,
                R.layout.intro_item_2,
                R.layout.intro_item_4,
        };
        //adds circles
        addCircles(0);
        //set's status bar color like background
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        if(android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //ViewPager allows flipping through pages. ID is defined
        viewPager = (ViewPager) findViewById(R.id.view_pager_intro_view_menu);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
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
                if(position==0){
                    welcomeTitel1.animate().y(100).setDuration(1000).start();
                    welcomeSwipeText.animate().x((display.getWidth()/2)-250).setDuration(2000).start();
                    noAnim=false;
                }else if(position==1){
                    welcomeDes1.animate().y(200).setDuration(1000).start();
                    descriptionTitle.animate().y(100).setDuration(1000).start();
                    if(!noAnim){
                        welcomeTitel1.animate().y(-100).setDuration(1000).start();
                        welcomeSwipeText.animate().x(-350).setDuration(1000).start();
                    }
                }else{
                    noAnim = true;
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
                skipButtonPressed=true;openApp();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == intro_layouts.length - 1) {
                    skipButtonPressed=true;
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
        Log.d(TAG,"Show Intro: " + Boolean.toString(sharedPrefs.getSharedPrefencesShowIntro()));
        Log.d(TAG, "current profile " + SharedPrefs.getCurrentProfile());
        pList = ListOfProfiles.getProfileList();
        tList = ListOfTasks.getSortList();

        if (pList != null && (skipButtonPressed || SharedPrefs.getSharedPrefencesShowIntro())) {
            if (pList.size() == 0) {
                Intent intent = new Intent(this, ProfileCreatorActivity.class);
                startActivity(intent);
            } else if (SharedPrefs.getCurrentProfile().equals("")) {
                Intent intent = new Intent(this, ProfileSwitcherActivity.class);
                startActivity(intent);
            } else {
                if (tList != null) {
                    if (tList.size() <= 1) {
                        intent = new Intent(this, NewTaskActivity.class);
                        startActivity(intent);
                    } else{
                        intent = new Intent(this, SmarttaskMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        } else {
           if(skipButtonPressed || SharedPrefs.getSharedPrefencesShowIntro()) { Toast.makeText(this, getString(R.string.intro_waiting_connection), Toast.LENGTH_SHORT).show();}
        }
    }

    public void onCheckboxClicked(View view) {
        skipTutorial=!skipTutorial;
        SharedPrefs.setSharedPreferencesShowIntro(skipTutorial);
        Log.d(TAG, Boolean.toString(SharedPrefs.getSharedPrefencesShowIntro()));
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
            welcomeTitel1 = (TextView) findViewById(R.id.welcome_titel);
            welcomeDes1 = (TextView) findViewById(R.id.textview_description);
            descriptionTitle = (TextView) findViewById(R.id.welcome_titel2);
            welcomeSwipeText = (TextView) findViewById(R.id.swipetext);
            if(position==0){
                welcomeTitel1.animate().y(100).setDuration(1000).start();
                welcomeSwipeText.animate().x((display.getWidth()/2)-250).setDuration(2000).start();
            }

            return view;
        }

        //Return the number of views available (#numbers of inflating pages)
        @Override
        public int getCount() {
            return intro_layouts.length;
        }

//          Is necessary for ViewPager! VP stores views in two separate places and here you just validate if they are the same
//          its kind of weird  and I dont have much time so ask Sokol in the exam :D
//          see for more information http://stackoverflow.com/questions/7277892/instantiateitem-in-pageradapter-and-addview-in-viewpager-confusion/16772250#16772250
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

    //    Firebase loads profiles and tasks to check if new user and empty tasks
    private void pullProfilesAndTasks() {
//    Log.d(TAG, mAuth.getCurrentUser().toString());
        FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot mDataSnapshot) {
                DataSnapshot profiles = mDataSnapshot.child(("profile"));
                loadProfiles(profiles);
                DataSnapshot tasks = mDataSnapshot.child(("task"));
                loadTasks(tasks);
                Log.d(TAG, "This is still listening!!!!!!!!!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG + "Err", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void loadProfiles(DataSnapshot mDataSnapshot) {
        ListOfProfiles.setDataSnapshot(mDataSnapshot);
        pList = ListOfProfiles.getProfileList();
    }

    private void loadTasks(DataSnapshot mDataSnapshot) {
        ListOfTasks.setDataSnapshot(mDataSnapshot);
        tList = ListOfTasks.getSortList();
        openApp();
    }
}
