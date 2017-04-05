package com.example.joe.smarttask.SmartTask_MainPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 14/03/2017.
 */

public class ListFragment extends Fragment {


    public Map<String, Task> tasks = new HashMap<String, Task>();
    private RecyclerView mListRecyclerView;
    private TaskAdapter mAdapter;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;
    private ArrayList<Task> listItems;

    /* This Method should host nothing but super.onCreate method call as fragments follow a slightly different lifecycle than normal activities.
       All intialisations and else should be in onCreateView
    */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/Zkw8FY9RKsfTsHd2GQy0rDFXm133").child("task");
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                    Task task = new Task();
                    tasks.put(i.next().getKey(), task);
                }
                listItems = new ArrayList<Task>();

                Log.d("Length ", String.valueOf(dataSnapshot.getChildrenCount()));
                for (Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                    DataSnapshot current = i.next();
                    Task post = tasks.get(current.getKey());
                    post = current.getValue(Task.class);
                    Log.d("tassk name ", post.getName());
                    listItems.add(post);
                }
                Log.d("Tasks size ", String.valueOf(tasks.size()));

                //TaskAdapter adapter = new TaskAdapter(listItems);
                mAdapter = new TaskAdapter(listItems);
                // Set CustomAdapter as the adapter for RecyclerView.
                mListRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        ListTask mListTask = ListTask.list(getContext());
        List<Task> mList = mListTask.getmTaskList();
        mAdapter = new TaskAdapter(mList);
        mListRecyclerView.setAdapter(mAdapter);
    }


    private class TaskHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;

        public TaskHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mListTasks;

        public TaskAdapter(List<Task> mListTasks) {
            this.mListTasks = mListTasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mListTasks.get(position);
            holder.mTitleTextView.setText(task.getName());
        }

        @Override
        public int getItemCount() {
            return mListTasks.size();
        }
    }

}
