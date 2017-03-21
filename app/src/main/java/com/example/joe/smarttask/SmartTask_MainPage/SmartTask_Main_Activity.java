package com.example.joe.smarttask.SmartTask_MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.joe.smarttask.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Made by us
 */

public class SmartTask_Main_Activity extends FragmentActivity {

    //TAG for Logs
    private static final String TAG = "Yes Yes";
    Button upload;

    // [Start declare Firebase Auth and Auth listener]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    // [End declare Firebase auth]

    private SmartTask_FireBase smartTask_fireBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        //set's the content (layout)
        setContentView(R.layout.activity_smarttask);


        // All firebase related changes run over this singleton.
        // Calling it doesn't waste resources!!!
        smartTask_fireBase = SmartTask_FireBase.fireBase(this);


        //assign button to upload to firebase
        upload = (Button) findViewById(R.id.tasks);
        upload.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //Profile example
                                          Map<String, String> aMap = new HashMap<String, String>();
                                          aMap.put("profile/pid" , "1");
                                          aMap.put("profile/picture" , "asd");
                                          aMap.put("profile/pincode" , "4311");
                                          smartTask_fireBase.push(aMap);
                                          //Task example
                                          Map<String, String> task = new HashMap<String, String>();
                                          task.put("task/categories","All Rooms");
                                          task.put("task/colorcode","setcolourlink");
                                          task.put("task/datetime","setdatetime");
                                          task.put("task/description","setdescription");
                                          task.put("task/freqency","setfreq");
                                          task.put("task/name","getname");
                                          task.put("task/owner","getPID");
                                          task.put("task/points","Set score");
                                          task.put("task/priority","Set priorty");
                                          task.put("task/responsible","define profile");
                                          task.put("task/status","set status");
                                          task.put("task/taskid","Set Task ID");
                                          task.put("task/subtask/maintaskid","0");
                                          task.put("task/subtask/tid","0");
                                          smartTask_fireBase.push(task);
                                      }
                                  }
        );


        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new SmartTask_ListTasks_Fragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        if (fragment == null) {
            fragment = new SmartTask_ListTasks_Fragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }


/*


    // Instances of this class are fragments representing a single
// object in our collection.
    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_collection_object, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }


*/





}
