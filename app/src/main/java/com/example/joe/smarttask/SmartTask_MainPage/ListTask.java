package com.example.joe.smarttask.SmartTask_MainPage;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 05/04/2017.
 */

public class ListTask {

    private static ListTask sListTask;
    private Context context;
    private List<Task> mList;
    private Task task;

//    [Start: private constructor to make singleton]

    /**
     * Creates a new ArrayList where all the Task object are going to be stored
     *
     * @param context global information on application environment
     */
    private ListTask(Context context) {
        this.context = context;
        mList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            task = new Task();
            task.setmTaskName("Clean apartment");
            mList.add(task);
        }
    }
//    [End]

//    [Start: static factory method for singleton]
    /**
     * @param context global information on application environment
     * @return THE single Object of this class
     */
    public static ListTask list(Context context) {
        if (sListTask == null) {
            sListTask = new ListTask(context);
        }
        return sListTask;
    }
//    [End: static factory]


    //    getter Method for List of Tasks
    public List<Task> getmTaskList() {
        return mList;
    }


    /**
     * return a task for single view
     *
     * @param id is a unique id identifying a task
     * @return the object with the id or null if the task id was not found
     * *
     */
    public Task getTask(String id) {
        for (Task t : mList) {
            if (id.equals(t.getmTaskID())) {
                return t;
            }
        }
        return null;
    }


}
