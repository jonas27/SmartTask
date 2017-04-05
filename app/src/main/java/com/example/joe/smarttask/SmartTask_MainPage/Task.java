package com.example.joe.smarttask.SmartTask_MainPage;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Jones on 24-03-2017.
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
}
