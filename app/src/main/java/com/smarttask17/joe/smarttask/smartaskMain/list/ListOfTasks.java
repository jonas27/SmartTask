package com.smarttask17.joe.smarttask.smartaskMain.list;

import com.smarttask17.joe.smarttask.smartaskMain.SmarttaskMainActivity;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.FireBase;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
import com.smarttask17.joe.smarttask.smartaskMain.task.TaskObject;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Singleton for handling the list of all tasks which is then displayed in the List fragment
 * It gets a  <p><font color="green"> FireBase {@link DataSnapshot} </font> from the {@link FireBase} class when the this class is first created or on data change (on the server)
 * The information from this DataSnapshot is then put into single TaskObject Objects {@link TaskObject} which are all stored in a ArrayList
 * <p><font color="red"> This class has a static method
 */

public class ListOfTasks {

    //TAG for Logs
    private static final String TAG = "CL_ListTask";

    private static ListOfTasks sListOfTasks;
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
        sSortedList = ListSorter.sortList(sList);
        if (SmarttaskMainActivity.showOnlyOwnTasks) {
            return getPersonalList();
        }
        return sSortedList;
    }

    public static List<TaskObject> getPersonalList() {
        TaskObject t;
        for (int c = 0; c < sSortedList.size(); c++) {
            t = sSortedList.get(c);
            if (t.getResponsible().compareTo(SharedPrefs.getCurrentUser()) != 0) {
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
        sSortedList = getSortList();
        for (TaskObject t : sSortedList) {
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
            for (Iterator<DataSnapshot> i = sDataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                TaskObject taskObject = i.next().getValue(TaskObject.class);
                sList.add(taskObject);
            }
            sSortedList = ListSorter.sortList(sList);
            //            CalendarView.updateCalendar();
        }
    }
    @Override
    public String toString(){
        return Arrays.toString(sSortedList.toArray());
    }
}

