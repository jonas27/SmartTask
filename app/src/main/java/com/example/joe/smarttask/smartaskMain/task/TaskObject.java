package com.example.joe.smarttask.smartaskMain.task;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Built 100% by us
 * This class creates the individual objects
 * <p>
 * Should we implement it with a map or variables?
 * Depending on this use constructor or getter and setter
 */

public class TaskObject implements Serializable, Comparable<TaskObject> {

    //TAG for Logs
    private static final String TAG = "CLASS_Task";

    //    [Start: Variables of a task (Naming has to be equal to FireBase, so don'p change!)]
    private String categories;
    private String colorcode;
    private long datetime;
    private String description;
    private int frequency;
    private String name;
    private String owner;
    private int points;
    private int priority;
    private String responsible;
    private boolean status;
    private String id;
    private String task;
    private long dateTimePhoto;
//    [End: Variables of a task]

    private boolean mCompleted;

    // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    public TaskObject() {
        super();
    }

    public TaskObject(String categories, long datetime,
                      String description, int frequency, String name, String owner,
                      int priority, String responsible, int points, boolean status, String id, String task) {
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

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setmCompleted(boolean mCompleted) {
        this.mCompleted = mCompleted;
    }

    public long getDateTimePhoto() {
        return dateTimePhoto;
    }

    public void setDateTimePhoto(long dateTimePhoto) {
        this.dateTimePhoto = dateTimePhoto;
    }
//      [End: Getter and setters for variables]


    @Exclude
    @Override
    public int compareTo(TaskObject other) {
        return Long.compare(this.datetime, other.getDatetime());
    }
    @Exclude
    @Override
    public String toString(){
        return this.getName();
    }


}
