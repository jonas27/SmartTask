package com.example.joe.smarttask.SmartTask_MainPage;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Built 100% by us
 * This class creates the individual objects
 *
 * Should we implement it with a map or variables?
 * Depending on this use constructor or getter and setter
 */

public class Task{

        public String categories;
    //    [Start: Variables of a task]
    private String mTaskID;
    private String mTaskName;
    private Date mDate;
    private String mDescription;


    //    [End: Variables of a task]
    private boolean mCompleted;

        public Task() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Task(String categories) {
            this.categories = categories;
        }

        // [START post_to_map]
        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("categories", categories);

            return result;
        }

//      Getter and setters for variables

    public String getmTaskID() {
        return mTaskID;
    }

    public void setmTaskID(String mTaskID) {
        this.mTaskID = mTaskID;
    }

    public String getmTaskName() {
        return mTaskName;
    }

    public void setmTaskName(String mTaskName) {
        this.mTaskName = mTaskName;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mEndDate) {
        this.mDate = mEndDate;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean ismCompleted() {
        return mCompleted;
    }

    public void setmCompleted(boolean mCompleted) {
        this.mCompleted = mCompleted;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
