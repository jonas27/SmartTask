package com.example.joe.smarttask.SmartTask_MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joe.smarttask.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * Made by us
 */

public class SmartTask_Main_Activity extends FragmentActivity {

    //TAG for Logs
    private static final String TAG = "Class_SM_Main_Activity";
    Button upload;
    private EditText text;

    // [Start declare Firebase Auth and Auth listener]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private DatabaseReference mPostReference;
    // [End declare Firebase auth]

    private SmartTask_FireBase smartTask_fireBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        //set's the content (layout)
        setContentView(R.layout.activity_smarttask);

         mPostReference = FirebaseDatabase.getInstance().getReference().child("User/Zkw8FY9RKsfTsHd2GQy0rDFXm133").child("task");

        // All firebase related changes run over this singleton.
        // Calling it doesn't waste resources!!!
        smartTask_fireBase = SmartTask_FireBase.fireBase(this);

        text = (EditText) findViewById(R.id.listtasks);

        //assign button to upload to firebase
        upload = (Button) findViewById(R.id.tasks);
        upload.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //Profile example
                                          /*
                                          Map<String, String> aMap = new HashMap<String, String>();
                                          aMap.put("profile", "root");
                                          aMap.put("pid" , "1");
                                          aMap.put("picture" , "asd");
                                          aMap.put("pincode" , "4311");
                                          smartTask_fireBase.push(aMap,"profile");
                                          //Task example
                                          Map<String, String> task = new HashMap<String, String>();
                                          task.put("task","root");
                                          task.put("categories","All Rooms");
                                          task.put("colorcode","setcolourlink");
                                          task.put("datetime","setdatetime");
                                          task.put("description","setdescription");
                                          task.put("freqency","setfreq");
                                          task.put("name","getname");
                                          task.put("owner","getPID");
                                          task.put("points","Set score");
                                          task.put("priority","Set priorty");
                                          task.put("responsible","define profile");
                                          task.put("status","set status");
                                          task.put("taskid","Set Task ID");
                                          task.put("subtask/maintaskid","0");
                                          task.put("subtask/tid","0");
                                          smartTask_fireBase.push(task,"task");
                                          */
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





       /* TODO: move this to another class.
        Main is only meant to host Nav Bar and fragment manager
        */
        //Read data from task
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Task post = new Task();
                for(Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator(); i.hasNext();){
                    post = i.next().getValue(Task.class);
                }
                // [START_EXCLUDE]
                Log.d(post.categories,post.categories);
             //   text.setText(post.categories);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(SmartTask_Main_Activity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
    }


    /**
     * TODO: Add layout inflator
     * */
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