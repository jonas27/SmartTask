package com.example.joe.smarttask.SmartTask_MainPage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FetchAdds;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.PictureScale;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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
    private Menu subMenu;
    private Menu mMenuSettings;
    private Menu mMenuClose;
    private static Intent intent;
    private static ImageView picture;
    private static final String dir = "/storage/emulated/0/smarttask/";


    private static Bitmap bitmap;
    private static StorageReference storageRef;


    public static Context getAppContext() {
        return SMMainActivity.context;
    }

    @Override
    public void onResume(){
        super.onResume();
        setIconToolbar(getAppContext(),getSupportActionBar());
    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.instance = this;

        context = getApplicationContext();
        contextMain = this;
        ListTask.sortList();

        //    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        //    new FetchPicture().execute();

        FireBase.fireBase(getAppContext());


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   SmartTask");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setIconToolbar(getAppContext(),getSupportActionBar());




//        getSupportActionBar().setHomeButtonEnabled(true);

        mActionAdd = (FloatingActionButton) findViewById(R.id.fab);
        mActionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });


        sFragmentList = new ArrayList<>();

        // ViewPager and its adapters use support library
        // layout.layouts.fragments, so use getSupportFragmentManager.
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewPager(mViewPager);
//        mViewPager.setAdapter(mMainPagerAdapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //Listener for when new page is selected
            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mActionAdd.setVisibility(View.VISIBLE);
                } else {
                    mActionAdd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(2);
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

    public static void firebaseLoaded() {
//        Log.d(TAG,"CURRENT PROFILE "+SharedPrefs.getCurrentProfile());

//        if(SharedPrefs.getCurrentProfile()==""){
//            showProfiles(true);
//        }
    }

    //show profile selector dialog, with or without close button
    public static void showProfiles(boolean c) {
        final Dialog dialog = new Dialog(instance);
        dialog.setContentView(R.layout.change_profile);
        dialog.setTitle("Change profile");
        dialog.setCancelable(true);

        GridView grid = (GridView) dialog.findViewById(R.id.profile_grid);
        grid.setAdapter(new ProfileAdapter(context, ListProfile.getProfileList()));


        //set up button
        Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        if (c) {
            close.setVisibility(View.INVISIBLE);
        }

        Button add = (Button) dialog.findViewById(R.id.add_profile);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(contextMain, CreateProfile.class);
                contextMain.startActivity(intent);
                dialog.cancel();
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Changed profile", Toast.LENGTH_SHORT).show();
                SharedPrefs.setCurrentProfile(ListProfile.getProfileList().get(position).getPid());
                SharedPrefs.setCurrentUser(ListProfile.getProfileList().get(position).getPname());
                dialog.cancel();
            }
        });

        //now that the dialog is set up, it's time to show it
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                intent = new Intent(getAppContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_settings:
                intent = new Intent(getAppContext(), SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_change_profile:
                showProfiles(false);
                return true;
            case R.id.menu_profile:
                intent = new Intent(getAppContext(), ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                FireBase fireBase = FireBase.fireBase(this);
                fireBase.logout();
                SharedPrefs.editor.clear().commit();
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


    private void setupViewPager(ViewPager viewPager) {
        mMainPagerAdapter.addFragment(new CalendarFragment(), getString(R.string.main_screen_calendar));
        mMainPagerAdapter.addFragment(new ListFragment(), getString(R.string.main_screen_tasks));
//        mMainPagerAdapter.addFragment(new MessengerFragment(), "Messenger");
        mViewPager.setAdapter(mMainPagerAdapter);
    }

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

    private static void setIconToolbar(final Context context, ActionBar actionBar){


        File mProfilePicture;
        String userID=SharedPrefs.getCurrentProfile();

        Log.d(TAG, userID);

        if(userID!="0"){
        File profileImage = new File(dir + userID + ".jpg");
        if (profileImage.exists()) {

            Log.d(TAG, "Picture exists for: " + userID);
//           Bitmap bitmap = BitmapFactory.decodeFile(profileImage.getAbsolutePath());
            Bitmap bitmap= PictureScale.getScaledBitmap(dir + userID + ".jpg",44,44,10);
            Drawable d = new BitmapDrawable(context.getResources(), bitmap);
            actionBar.setIcon(d);
        } else {
            Log.d(TAG, "Getting from firebase");
            StorageReference currentImage = storageRef.child("images/" + userID + ".jpg");

            File localFile = null;
            try {
                localFile = new File(dir + userID + ".jpg");
                localFile.createNewFile();
                final File finalLocalFile = localFile;
                currentImage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "Picture exist");
                        bitmap= PictureScale.getScaledBitmap(finalLocalFile.getAbsolutePath(),44,44,10);
                        Drawable d = new BitmapDrawable(getAppContext().getResources(), bitmap);


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
        if(profileImage.length()==0){
            actionBar.setLogo(R.mipmap.smlogo);
        }}
        else{
            actionBar.setLogo(R.mipmap.smlogo);
            }
        actionBar.setDisplayUseLogoEnabled(true);
    }


    //    Start new thread to download adds
    private class FetchPicture extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            new FetchAdds().saveImage(FetchAdds.URL_ADDRESS);
            return null;
        }
    }


    private static class ProfileAdapter extends ArrayAdapter<ProfileObject> {
        private LayoutInflater inflater;

        public ProfileAdapter(Context context, ArrayList<ProfileObject> profiles) {
            super(context, R.layout.profile_square, profiles);
            inflater = LayoutInflater.from(context);
        }


//        Adapter is bad loads as it loads all the titles on the view in memory.
//        This means it renders 12 profile pictures which crashed the programm
//        Maybe switch to recycler view
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = inflater.inflate(R.layout.profile_square, parent, false);
//            ProfileObject current = getItem(position);
            Log.d(TAG, "Current postion of adapter: " + Integer.toString(position));
            ProfileObject current = ListProfile.getProfileList().get(position);
            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(current.getPname());

            TextView score = (TextView) view.findViewById(R.id.score);
            score.setText(current.getPscore());

            picture = (ImageView) view.findViewById(R.id.profile_image);
            picture.setImageDrawable(context.getResources().getDrawable((R.drawable.ic_logo)));

            String userId = current.getPid();
            Log.d(TAG, "Current ID: " + userId);
            File profileImage = new File(dir + userId + ".jpg");
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                if (profileImage.exists()) {
                    Log.d(TAG, "Foto exists for user: " + current.getPid());

                    Bitmap bitmap = PictureScale.getScaledBitmap(dir + userId + ".jpg", 580,480,4);
                    picture = (ImageView) view.findViewById(R.id.profile_image);
                    picture.setImageBitmap(bitmap);
                } else {
                    Log.d(TAG, "Getting from firebase for user: " + userId);
                    StorageReference currentImage = storageRef.child("images/" + userId + ".jpg");
                    File localFile;
                    try {
                        localFile = new File(dir + userId + ".jpg");
                        localFile.createNewFile();
                        final File finalLocalFile = localFile;
                        currentImage.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Log.d(TAG, "Picture exist");
                                Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                                picture.setImageBitmap(bitmap);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                                picture.setImageDrawable(context.getResources().getDrawable(R.mipmap.smlogo));
                                Log.d(TAG, "NO Picture exist");
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            if(profileImage.length()==0){
                picture.setImageDrawable(context.getResources().getDrawable(R.mipmap.smlogo));
            }

            return view;
        }
    }
}
