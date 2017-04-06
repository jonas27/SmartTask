package com.example.joe.smarttask.SmartTask_MainPage;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 05/04/2017.
 */

public class ListTask {

    //TAG for Logs
    private static final String TAG = "CL_ListTask";

    private static FireBase sFireBase;
    private static ListTask sListTask;
    private Context context;
    private static List<Task> sList;
    private Task task;
    private static DataSnapshot sDataSnapshot;


    /**
     * Creates a new ArrayList where all the Task object are going to be stored
     * @param context global information on application environment
     */
    private ListTask(Context context) {
        this.context = context;
        sList = new ArrayList<>();
        createList();
    }



    /**
     * Static factory method for singleton
     * @param context global information on application environment
     * @return THE single Object of this class
     */
    public static ListTask list(Context context) {
        if (sListTask == null) {
            sListTask = new ListTask(context);
        }
        return sListTask;
    }



    public static void setmDataSnapshot(DataSnapshot mDataSnapshot){
        sDataSnapshot=mDataSnapshot;
        if(sList!=null) {
            createList();
        }
    }

    private static void createList() {
        if(sDataSnapshot!=null) {
            Map<String, Task> tasksMap = new HashMap<>();
            for (Iterator<DataSnapshot> i = sDataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                Task task = new Task();
                tasksMap.put(i.next().getKey(), task);
            }

            Log.d(TAG, String.valueOf(sDataSnapshot.getChildrenCount()));
            sList.clear();
            for (Iterator<DataSnapshot> i = sDataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                DataSnapshot current = i.next();
                Task post = tasksMap.get(current.getKey());
                post = current.getValue(Task.class);
                Log.d(TAG, post.getName());
                sList.add(post);
            }
            Log.d(TAG + "Tasks size ", String.valueOf(tasksMap.size()));
            ListFragment.updateUI(sList);
        }
    }

    //    getter Method for List of Tasks
    public List<Task> getmTaskList() {
        return sList;
    }


    /**
     * return a task for single view
     *
     * @param id is a unique id identifying a task
     * @return the object with the id or null if the task id was not found
     * *
     */
    public Task getTask(String id) {
        for (Task t : sList) {
            if (id.equals(t.getId())) {
                return t;
            }
        }
        return null;
    }


}
