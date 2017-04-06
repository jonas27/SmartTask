package com.example.joe.smarttask.SmartTask_MainPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

    //TAG for Logs
    private static final String TAG = "CLASS_ListFragment";

    public Map<String, Task> tasks = new HashMap<String, Task>();
    private static RecyclerView mListRecyclerView;
    private static TaskAdapter mAdapter;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;
    private ArrayList<Task> listItems;
    private List<Task> mList;

    /* This Method should host nothing but super.onCreate method call as fragments follow a slightly different lifecycle than normal activities.
       All intialisations and else should be in onCreateView
    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList = ListTask.getmTaskList();
        updateUI(mList);
        return view;
    }

    @Override
    public void onResume() {
        updateUI(mList);
        super.onResume();
    }


    public static void updateUI(List<Task> mList) {

//        Log.d("CLASS_LF", Integer.toString(mList.size()));
//        Log.d("CLASS_LF", mList.get(0).getName());
        if(mListRecyclerView!=null){
            mAdapter = new TaskAdapter(mList);
            mAdapter.notifyDataSetChanged();
            mListRecyclerView.setAdapter(mAdapter);

        }
    }


    private static class TaskHolder extends RecyclerView.ViewHolder{
        public TextView mTitleTextView;

        public TaskHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }

    }

    private static class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> mListTasks;

        public TaskAdapter(List<Task> mListTasks) {
            this.mListTasks = mListTasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SingleFragmentActivity.getAppContext());
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
