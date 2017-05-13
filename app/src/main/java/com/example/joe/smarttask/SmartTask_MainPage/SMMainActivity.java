package com.example.joe.smarttask.SmartTask_MainPage;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joe.smarttask.ChooseProfile.ChooseProfileFragment;
import com.example.joe.smarttask.SmartTask_MainPage.Messenger.MessengerFragment;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.CreateProfile;
import com.example.joe.smarttask.LogInActivity;
import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.Calendar.CalendarFragment;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListFragment;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.NewTask.NewTaskActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileObject;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SettingsActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.PictureConverter;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsSuperclassesAndHelpers.SharedPrefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class written 100% by us.
 * This is the entry point for the main app
 */

public class SMMainActivity extends AppCompatActivity {

    private static final String TAG = "CL_MaAc";
    private static Context context;
    private static Context contextMain;
    private static List<Fragment> sFragmentList;
    private static boolean sStartActivity;
    private static SMMainActivity instance;
    // When requested, this adapter returns a Fragment,
    // representing an object in the collection.
    MainPagerAdapter mMainPagerAdapter;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    private FloatingActionButton mActionAdd;
    private EditText message;
    private ImageView send;
    private Menu subMenu;
    private LinearLayout linear;
    private static Intent intent;
    private static ImageView picture;
    private static final String dir = "/storage/emulated/0/smarttask/";


    private static Bitmap bitmap;
    private static ImageView mToolbarIcon;
    private ProfileObject mProfile;
    private static String mProfileId;
private static int backButtonCounter;
    private static EditText password;

    public static boolean showOnlyOwnTasks = false;


    public static Context getAppContext() {
        return SMMainActivity.context;
    }

    @Override
    public void onResume() {
        super.onResume();
        setIconToolbar();

    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.instance = this;
        FireBase.fireBase(getAppContext());
        context = getApplicationContext();
        contextMain = this;
        ListTask.getSortList();
        sFragmentList = new ArrayList<>();

        //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        //    new FetchPicture().execute();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToolbarIcon = (ImageView) findViewById(R.id.toolbar_icon);
        mToolbarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnlyOwnTasks = !showOnlyOwnTasks;
                if (showOnlyOwnTasks) {
                    getSupportActionBar().setTitle("   " + SharedPrefs.getCurrentUser());
                    mToolbarIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_white_24dp));
                    ListFragment listFragment = (ListFragment) mMainPagerAdapter.getItem(1);
                    // Check if the tab fragment is available and update recyclerview
                    if (listFragment != null) {
                        listFragment.updateUI();
                    }
                } else {
                    getSupportActionBar().setTitle("   SmartTask");
                    setIconToolbar();
                    ListFragment listFragment = (ListFragment) mMainPagerAdapter.getItem(1);
                    // Check if the tab fragment is available and update recyclerview
                    if (listFragment != null) {
                        listFragment.updateUI();
                    }
                }
            }
        });
        getSupportActionBar().setTitle("   SmartTask");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mActionAdd = (FloatingActionButton) findViewById(R.id.fab);
        mActionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });

        send = (ImageView) findViewById(R.id.send);
        message = (EditText) findViewById(R.id.message);
        linear = (LinearLayout) findViewById(R.id.linear);

        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //Listener for when new page is selected
            @Override
            public void onPageSelected(int position) {
                CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mViewPager.getLayoutParams();
                p.setMargins(0, 0, 0, 0);
                mViewPager.setLayoutParams(p);
                linear.setVisibility(View.INVISIBLE);
                if (position == 0) {
                    mActionAdd.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    mProfile = ListProfile.getProfile(SharedPrefs.getCurrentProfile(context));

                    Log.d(TAG,"Value for current profile in shared: ");
                    Log.d(TAG,"Value for current profile: " + mProfile.getPid());
                    switch (mProfile.getPprivileges()) {
                        case 1: {
                            mActionAdd.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 2: {
                            mActionAdd.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 3: {
                            mActionAdd.setVisibility(View.INVISIBLE);
                            break;
                        }
                        default: {
                            mActionAdd.setVisibility(View.VISIBLE);
                        }
                    }

                } else if (position == 2) {
                    mActionAdd.setVisibility(View.INVISIBLE);
                    linear.setVisibility(View.VISIBLE);
                    int dpValue = 50; // margin in dips
                    float d = context.getResources().getDisplayMetrics().density;
                    int margin = (int) (dpValue * d); // margin in pixels
                    p.setMargins(0, 0, 0, margin);
                    mViewPager.setLayoutParams(p);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
    }


    private void setupViewPager(ViewPager viewPager) {
        mMainPagerAdapter.addFragment(new CalendarFragment(), getString(R.string.main_screen_calendar));
        mMainPagerAdapter.addFragment(new ListFragment(), getString(R.string.main_screen_tasks));
        mMainPagerAdapter.addFragment(new MessengerFragment(), "Messenger");
        mViewPager.setAdapter(mMainPagerAdapter);
    }

//    [Start: Define Menu]

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        TODO: Add right maring to menu inflator
        subMenu = (Menu) findViewById(R.id.menu_expand_menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void showDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(new ChooseProfileFragment(), "dialog");
//        remove previous dialogs
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        ChooseProfileFragment newFragment = new ChooseProfileFragment();
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onBackPressed() {
        backButtonCounter++;
        if(backButtonCounter==0){Toast.makeText(context, getString(R.string.main_double_tap),Toast.LENGTH_SHORT);}
        if(backButtonCounter==2){
            finish();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        backButtonCounter=0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(getAppContext(), ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                intent = new Intent(getAppContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_change_profile:
//                showProfiles(false);
                showDialog();
                return true;
            case R.id.menu_profile:
                intent = new Intent(getAppContext(), ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                FireBase fireBase = FireBase.fireBase(this);
                fireBase.logout();
                SharedPrefs.editor.clear().commit();
                SharedPrefs.setCurrentProfile("");
                SharedPrefs.setCurrentUser("");
                LogInActivity.introWasShown = false;
                //        CLear cache for logout
                File cacheDir = context.getCacheDir();
                File[] files = cacheDir.listFiles();
                Log.d(TAG, Integer.toString(files.length));
                if (files != null) {
                    for (File file : files)
                        file.delete();
                }
                intent = new Intent(getAppContext(), LogInActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    [End: Define Menu]


    public class MainPagerAdapter extends FragmentPagerAdapter {
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return sFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return sFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            sFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private static void setIconToolbar() {
        if (showOnlyOwnTasks) {
            mToolbarIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_list_white_24dp));
        } else {
            File mProfilePicture;
            String userID = SharedPrefs.getCurrentProfile(context);
            String path = dir + userID + ".jpg";
            if (userID != "0") {
                File profileImage = new File(path);
                if (profileImage.length() > 0) {
                    mToolbarIcon.setImageBitmap(PictureConverter.getRoundProfilePicture(path, 100));
                } else {
//                    picture is downloaded either at ChooseProfile or at change user (in this class)
                    mToolbarIcon.setImageDrawable(getAppContext().getResources().getDrawable(R.mipmap.smlogo));
                }
            }
        }
    }


//    //    Start new thread to download adds
//    private class FetchPicture extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            new FetchAdds().saveImage(FetchAdds.URL_ADDRESS);
//            return null;
//        }
//    }


    private static class ProfileAdapter extends ArrayAdapter<ProfileObject> {
        private LayoutInflater inflater;

        public ProfileAdapter(Context context, ArrayList<ProfileObject> profiles) {
            super(context, R.layout.profile_square, profiles);
            inflater = LayoutInflater.from(context);
        }

        //        Adapter loads all the titles on the view in memory.
//        This means it renders 12 profile pictures which crashed the programm+
//        Maybe switch to recycler view
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = inflater.inflate(R.layout.profile_square, parent, false);
            ProfileObject current = ListProfile.getProfileList().get(position);
            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(current.getPname());
            TextView score = (TextView) view.findViewById(R.id.score);
            score.setText(Integer.toString(current.getPscore()));

            picture = (ImageView) view.findViewById(R.id.profile_image);
            String userID = current.getPid();
            String path = dir + userID + ".jpg";
            if (userID != "") {
                File profileImage = new File(path);
                if (profileImage.length() != 0) {
                    picture.setImageBitmap(PictureConverter.getRoundProfilePicture(path, 500));
                } else {
                    Log.d(TAG, "Getting from firebase");
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
                                picture.setImageBitmap(PictureConverter.getRoundProfilePicture(finalLocalFile.getAbsolutePath(), 500));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                picture.setImageDrawable(getAppContext().getResources().getDrawable(R.mipmap.smlogo));
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return view;

        }

    }
}
