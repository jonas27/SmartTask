package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
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
    private static List<TaskObject> sSortedList;

    private static DataSnapshot sDataSnapshot;

    public static void setDataSnapshot(DataSnapshot mDataSnapshot) {
        sDataSnapshot = mDataSnapshot;
        sList = new ArrayList<TaskObject>();
        createList();
    }

//    resorts the list and returns a sorted list
    public static List<TaskObject> getSortList() {
        sSortedList = SortList.sortList(sList);
        if(SMMainActivity.showOnlyOwnTasks){
            return getPersonalList();
        }
        return sSortedList;
    }

    public static List<TaskObject> getPersonalList(){
        TaskObject t;
        for (int c=0; c<sSortedList.size(); c++){
            t=sSortedList.get(c);
            if(t.getResponsible().compareTo(SharedPrefs.getCurrentUser())!=0){
                Log.d(TAG, "THey are equal: " + t.getResponsible());
                sSortedList.remove(t);
                c--;
            }
        }
        return sSortedList;
    }

    /**
     * return a task for single view
     * TODO: change name into UID
     *
     * @param mTaskId is a unique id identifying a task
     * @return the object with the id or null if the task id was not found
     * *
     */
    public static TaskObject getTask(String mTaskId) {
        sSortedList= getSortList();
        Log.d(TAG, "size of sortedlist: " + Integer.toString(sSortedList.size()) );
        for (TaskObject t : sSortedList) {
            Log.d(TAG, "Name of sortedlist items: " + t.getId() );
            if (mTaskId.equals(t.getId())) {
                return t;
            }
        }
        return null;
    }

    /**
     * This method is called by {@link FireBase} and creates List of tasks
     * It then calls the recycler view in {@link ListFragment} to update itslef
     * Static methods are used to ease the call backs from the OnDataChangeListener in {@link FireBase}
     */
    private static void createList() {
        sList = new ArrayList<>();
        sSortedList = new ArrayList<>();
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
                sList.add(mTask);
            }


            sSortedList = SortList.sortList(sList);
//            CalendarView.updateCalendar();
        }
    }
}

