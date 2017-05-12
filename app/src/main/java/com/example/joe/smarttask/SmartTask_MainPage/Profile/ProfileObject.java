package com.example.joe.smarttask.SmartTask_MainPage.Profile;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Built 100% by us
 */

public class ProfileObject implements Serializable {

    //TAG for Logs
    private static final String TAG = "CLASS_Profile";

    //    [Start: Variables of a task (Naming has to be equal to FireBase, so don't change!)]
    private String pname;
    private int pscore;
    private int pprivileges;
    private String id;
    private int ptotalscore;
    private String ppincode;
    private long dateTimePhoto;
//    [End: Variables of a task]

    private boolean mCompleted;

    // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    public ProfileObject() {
        super();
    }

    public ProfileObject(String pname, int pscore, String ppincode,
                         int pprivileges, String id, int ptotalscore) {
        super();
        this.pscore = pscore;
        this.pprivileges = pprivileges;
        this.pname = pname;
        this.id = id;
        this.ptotalscore = ptotalscore;
        this.ppincode = ppincode;
    }

    //      [Start: Getter and setters for variables]
    public int getPscore() {return pscore; }
    public void setPscore(int score) {this.pscore = score;}
    public int getPprivileges() {return pprivileges;}

    public void setPprivileges(int pprivileges) {
        this.pprivileges = pprivileges;
    }

    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public String getPid() {
        return id;
    }
    public void setPid(String id) {this.id = id;}
    public int getPtotalscore() {return ptotalscore;}
    public void setPtotalscore(int ptotalscore) {this.ptotalscore = ptotalscore;}
    public String getPpincode() {return ppincode;}
    public void setPpincode(String ppincode) {this.ppincode = ppincode;
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


}
