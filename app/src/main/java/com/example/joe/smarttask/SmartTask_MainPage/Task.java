package com.example.joe.smarttask.SmartTask_MainPage;

import com.google.firebase.database.Exclude;

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

    //TAG for Logs
    private static final String TAG = "CLASS_Task";

//    [Start: Variables of a task (Naming has to be equal to FireBase, so don't change!)]
    private String categories;
    private String colorcode;
    private String datetime;
    private String description;
    private String frequency;
    private String name;
    private String owner;
    private String points;
    private String priority;
    private String responsible;
    private String status;
    private String id;
    private String task;
//    [End: Variables of a task]

    private boolean mCompleted;

    // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    public Task() {
        super();
    }

    public Task(String categories, String datetime,
                String description, String frequency, String name, String owner,
                String priority, String responsible, String points, String status, String id, String task) {
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

//      [Start: Getter and setters for variables]
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
    public String getColorcode() {
        return colorcode;
    }
    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }
    public String getDatetime() {
        return datetime;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getResponsible() {
        return responsible;
    }
    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public boolean ismCompleted() {
        return mCompleted;
    }
    public void setmCompleted(boolean mCompleted) {
        this.mCompleted = mCompleted;
    }
//      [End: Getter and setters for variables]


}
