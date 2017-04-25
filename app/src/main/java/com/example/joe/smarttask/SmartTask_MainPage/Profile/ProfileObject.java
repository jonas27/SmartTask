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
    private String pscore;
    private String pprivileges;
    private String pid;
    private String ptotalscore;
    private String ppincode;
//    [End: Variables of a task]

    private boolean mCompleted;

    // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    public ProfileObject() {
        super();
    }

    public ProfileObject(String pname, String pscore, String ppincode,
                         String pprivileges, String pid, String ptotalscore) {
        super();
        this.pscore = pscore;
        this.pprivileges = pprivileges;
        this.pname = pname;
        this.pid = pid;
        this.ptotalscore = ptotalscore;
        this.ppincode = ppincode;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pscore", pscore);
        result.put("pid", pid);
        result.put("pname", pname);
        result.put("pprivileges", pprivileges);
        result.put("ptotalscore", ptotalscore);
        result.put("ppincode", ppincode);

        return result;
    }

    //      [Start: Getter and setters for variables]
    public String getPscore() {return pscore; }
    public void setPscore(String score) {this.pscore = score;}
    public String getPprivileges() {return pprivileges;}
    public void setPrivileges(String privileges) {this.pprivileges = pprivileges;}
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public String getPid() {
        return pid;
    }
    public void setPid(String id) {this.pid = pid;}
    public String getPtotalscore() {return ptotalscore;}
    public void setPtotalscore(String ptotalscore) {this.ptotalscore = ptotalscore;}
    public String getPpincode() {return ppincode;}
    public void setPpincode(String ppincode) {this.ppincode = ppincode;
    }

    public boolean isCompleted() {
        return mCompleted;
    }
    public void setmCompleted(boolean mCompleted) {
        this.mCompleted = mCompleted;
    }

    //      [End: Getter and setters for variables]


}
