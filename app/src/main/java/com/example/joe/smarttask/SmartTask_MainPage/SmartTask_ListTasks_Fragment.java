package com.example.joe.smarttask.SmartTask_MainPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joe.smarttask.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by joe on 14/03/2017.
 */

public class SmartTask_ListTasks_Fragment extends Fragment {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    public Map<String, Task> tasks = new HashMap<String, Task>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    private RecyclerView mSmartTaskListTaskRecyclerView;
    private DatabaseReference mPostReference;
    private ValueEventListener postListener;
    private ListView listView1;
    private Task listItems[];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/Zkw8FY9RKsfTsHd2GQy0rDFXm133").child("task");


        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                    Task task = new Task();
                    tasks.put(i.next().getKey(), task);
                }

                listItems = new Task[(int) dataSnapshot.getChildrenCount()];
                Log.d("Length ", String.valueOf(dataSnapshot.getChildrenCount()));
                int count = 0;
                for (Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                    DataSnapshot current = i.next();
                    Task post = tasks.get(current.getKey());
                    post = current.getValue(Task.class);
                    Log.d("Task loaded ",post.name);
                    listItems[count]=post;
                    count++;
                }
                Log.d("count", String.valueOf(count));

                Log.d("Tasks size ", String.valueOf(tasks.size()));

                TaskAdapter adapter = new TaskAdapter(getContext(),R.layout.task_list_layout, listItems);
                listView1.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listtasks_smarttask, container, false);
        listView1 = (ListView) view.findViewById(R.id.list);

/*
        listItems = new Task[2];
        listItems[0]=new Task("asd","asd","asd","asd","asd","asd","asd","asd","asd","asd","asd");
        listItems[1]=new Task("asd","dasd","asd","asd","asd","asd","asd","asd","asd","asd","asd");
        if(listView1==null){
            Log.d("Listview null","Listview null");
        }

        TaskAdapter adapter = new TaskAdapter(getContext(),R.layout.task_list_layout, listItems);
        listView1.setAdapter(adapter);
        */
        //  mSmartTaskListTaskRecyclerView = (RecyclerView) view.findViewById(R.id.smarttask_listtask_recycler_view);
      //  mSmartTaskListTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }



}
