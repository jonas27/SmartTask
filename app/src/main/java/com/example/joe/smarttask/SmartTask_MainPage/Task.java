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
    public String colorcode;
    public String datetime;
    public String description;
    public String frequency;
    public String name;
    public String owner;
    public String points;
    public String priority;
    public String responsible;
    public String status;
    public String id;
    public String task;
    //    [Start: Variables of a task]
    private String mTaskID;
    private String mTaskName;
    private Date mDate;
    private String mDescription;


    //    [End: Variables of a task]
    private boolean mCompleted;

    public Task() {
        super();
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Task(String categories, String datetime,
                String description, String frequency, String name, String owner,
                String priority, String responsible, String points, String status,String id,String task) {
        super();
        this.categories = categories;
        this.datetime = datetime;
        this.description = description;
        this.frequency = frequency;
        this.name = name;
        this.owner = owner;
        this.priority = priority;
        this.responsible = responsible;
        this.points = points;
        this.status = status;
        this.id = id;
        this.task = task;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("categories", categories);
        result.put("id", id);
        result.put("status", status);
        result.put("responsible", responsible);
        result.put("priority", priority);
        result.put("points", points);
        result.put("owner", owner);
        result.put("name", name);
        result.put("frequency", frequency);
        result.put("description", description);
        result.put("datetime", datetime);
        result.put("colorcode", colorcode);
        result.put("task", task);

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
