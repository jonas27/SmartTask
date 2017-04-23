package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.content.Context;
import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SettingsHandler;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Singleton for handling the list of all tasks which is then displayed in the List fragment
 * It gets a  <p><font color="green"> FireBase {@link DataSnapshot} </font> from the {@link FireBase} class when the this class is first created or on data change (on the server)
 * The information from this DataSnapshot is then put into single TaskObject Objects {@link TaskObject} which are all stored in a ArrayList
 * <p><font color="red"> This class has a static method
 */

public class ListTask {

    //TAG for Logs
    private static final String TAG = "CL_ListTask";

    private static ListTask sListTask;
    private static List<TaskObject> sList;
    private static DataSnapshot sDataSnapshot;
    private static SharedPrefs sharedPrefs;
    private Context context;


    /**
     * Creates a new ArrayList where all the TaskObject object are going to be stored
     *
     * @param context global information on application environment
     */
    private ListTask(Context context) {
        this.context = context;
        sList = new ArrayList<>();
        createList();
    }


    /**
     * Static factory method for singleton
     *
     * @param context global information on application environment
     * @return THE single Object of this class
     */
    public static ListTask list(Context context) {
        if (sListTask == null) {
            sListTask = new ListTask(context);
        }
        return sListTask;
    }


    public static void setDataSnapshot(DataSnapshot mDataSnapshot) {
        sDataSnapshot = mDataSnapshot;
        if (sList != null) {
            createList();
        }
    }

    /**
     * This method is called by {@link FireBase} and creates List of tasks
     * It then calls the recycler view in {@link ListFragment} to update itslef
     * Static methods are used to ease the call backs from the OnDataChangeListener in {@link FireBase}
     */
    private static void createList() {
        List<TaskObject> list = new ArrayList<>();
        if (sDataSnapshot != null) {
            Map<String, TaskObject> tasksMap = new HashMap<>();
            for (Iterator<DataSnapshot> i = sDataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                TaskObject task = new TaskObject();
                tasksMap.put(i.next().getKey(), task);
            }

            Log.d(TAG, String.valueOf(sDataSnapshot.getChildrenCount()));

            for (Iterator<DataSnapshot> i = sDataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                DataSnapshot current = i.next();
                TaskObject mTask = tasksMap.get(current.getKey());
                mTask = current.getValue(TaskObject.class);
                mTask.setId(current.getKey());
                list.add(mTask);
            }
            Log.d(TAG + "Tasks size ", String.valueOf(tasksMap.size()));
            sList.clear();
            sList = sortList(list);
            ListFragment.updateUI(sList);
        }
    }

    //    getter Method for List of Tasks
    public static List<TaskObject> getTaskList() {
        return sList;
    }

    private static List<TaskObject> sortList(List<TaskObject> list) {
        sharedPrefs = SharedPrefs.getSharedPrefs(SMMainActivity.getAppContext());
        switch (sharedPrefs.getSharedPrefencesListSort()) {
            case SettingsHandler.LIST_SORTED_DATE: {
                return sortDate(list);
            }
        }
        return list;
    }

    //    [Start: Sort by Date (merge Sort)]
//    leave creation of new List as not it is not working --> don't do: return divideList(list);
//    TODO: why is that not working?
    private static List sortDate(List<TaskObject> list) {
        List<TaskObject> l = divideList(list);
        return l;
    }

    private static List<TaskObject> divideList(List<TaskObject> list) {
        List<TaskObject> upperList = new ArrayList<TaskObject>();
        List<TaskObject> bottomList = list;
        if (list.size() <= 1) {
            return list;
        } else {
            for (int counter = 0; counter < list.size() / 2; counter++) {
                upperList.add(0, list.get(counter));
                bottomList.remove(0);
            }
            upperList = divideList(upperList);
            bottomList = divideList(bottomList);
        }
        List<TaskObject> l = mergeList(bottomList, upperList);
        return l;
    }

    private static List<TaskObject> mergeList(List<TaskObject> bottom, List<TaskObject> upper) {
        List<TaskObject> newList = new ArrayList<>();
        int size = (bottom.size() + upper.size());
        for (int c = 0; c < size; c++) {
            if (bottom.size() > 0 && upper.size() > 0 && Long.parseLong(bottom.get(0).getDatetime()) < Long.parseLong(upper.get(0).getDatetime())) {
                newList.add(bottom.get(0));
                bottom.remove(0);
            } else if (bottom.size() == 0) {
                newList.add(upper.get(0));
                upper.remove(0);
            } else if (upper.size() == 0) {
                newList.add(bottom.get(0));
                bottom.remove(0);
            } else {
                newList.add(upper.get(0));
                upper.remove(0);
            }
            Log.d(TAG, "newList: " + Integer.toString(newList.size()));
        }
        return newList;
    }

    /**
     * return a task for single view
     * TODO: change name into UID
     *
     * @param mTaskId is a unique id identifying a task
     * @return the object with the id or null if the task id was not found
     * *
     */
    public TaskObject getTask(String mTaskId) {
        for (TaskObject t : sList) {
            if (mTaskId.equals(t.getId())) {
//                sortList(sList);
                return t;
            }
        }
        return null;
    }
//    [End: Sort by Date]

}

