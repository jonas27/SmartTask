package com.example.joe.smarttask.SmartTask_MainPage;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jones on 24-03-2017.
 */

public class Task{
        public String categories;

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
}
