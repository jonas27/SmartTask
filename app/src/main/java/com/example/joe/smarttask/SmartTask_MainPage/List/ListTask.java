package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.content.Context;
import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.Calendar.CalendarView;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
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

    private static TaskObject sTaskObject;

    private Context context;


    /**
     * Creates a new ArrayList where all the TaskObject object are going to be stored
     *
     * @param context global information on application environment
     */
    private ListTask(Context context) {
        this.context = context;
        sList = Collections.synchronizedList(new ArrayList<TaskObject>());
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

//            sList=list;
            sList = SortList.sortList(list);
            ListFragment.updateUI(sList);
            CalendarView.updateCalendar();
        }
    }

    //    getter Method for List of Tasks
    public static List<TaskObject> getTaskList() {
        return sList;
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
                return t;
            }
        }
        return null;
    }
//    [End: Sort by Date]


//    Reodering of the first three elements of the Recylcerview is hard/not possible as it caches them in an arraylist which cannot be altered
//    For more infos see http://stackoverflow.com/questions/12510404/reorder-pages-in-fragmentstatepageradapter-using-getitempositionobject-object
//    TODO: review if this is really true (until then donÄt use methods below)
//    [Start: Add and remove taskholder in List for serparator line]
/*    public static void removeSeparator(){
        for(int c=0; c<sList.size();c++){
            if(sList.get(c).getStatus().equals(SortList.DRAW_LINE)){
                sList.remove(c);
            }
        }
    }
    public static void addSeparator(){
        for(int c=0; c<sList.size();c++){
            if(sList.get(c).getStatus().equals("true")){
                sList.add(c-1,sTaskObject);
                return;
            }
        }

    }
    */
//    [End: Add and remove taskholder in List for serparator line]

}

