package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.MainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskPagerActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 14/03/2017.
 */

public class ListFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_ListFragment";


    // [Start: get Singletons]
    private FireBase mFireBase;
    private ListTask mListTask;
    // [End: get Singletons]



    public Map<String, TaskObject> tasks = new HashMap<String, TaskObject>();
    private static RecyclerView sListRecyclerView;
    private static TaskAdapter sAdapter;
    private List<TaskObject> mList;

    private static Context sContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_list, container, false);
        initSingletons();
        sListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        sListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList = ListTask.getmTaskList();
        updateUI(mList);
        sContext=this.getContext();

        return view;
    }

    private void initSingletons(){
        mFireBase = FireBase.fireBase(getContext());
        mListTask = ListTask.list(getContext());
    }

    @Override
    public void onResume() {
        updateUI(mList);
        super.onResume();
    }

//    Use notifyDataSetChanged on all views as we do not know
//    which View should be updated when changes on FireBase occur
//    Is it possible to change that? Results in efficiency gain
    public static void updateUI(List<TaskObject> mList) {
//        Log.d("CLASS_LF", Integer.toString(mList.size()));
//        Log.d("CLASS_LF", mList.get(0).getName());

        if(sListRecyclerView!=null){
            sAdapter = new TaskAdapter(mList);
            sAdapter.notifyDataSetChanged();
            sListRecyclerView.setAdapter(sAdapter);
        }
    }


    // Provide a reference to the views for each data item
    private static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private CheckBox mTaskCompleted;

        private TaskObject mTask;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_description);
            mTaskCompleted= (CheckBox) itemView.findViewById(R.id.list_item_box);

        }

        @Override
        public void onClick(View v) {
            Intent intent = TaskPagerActivity.newIntent(sContext, mTask.getId());
            sContext.startActivity(intent);
        }


        public void bindTask(TaskObject task) {
            mTask = task;
            mTitleTextView.setText(mTask.getName());
            mDescriptionTextView.setText(mTask.getDescription());
            if(mTask.getStatus().equals("true")){
                mTaskCompleted.setChecked(true);
            }else{
                mTaskCompleted.setChecked(false);
            }

        }

    }

    private static class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<TaskObject> mListTasks;

        public TaskAdapter(List<TaskObject> mListTasks) {
            this.mListTasks = mListTasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.getAppContext());
            View view = layoutInflater.inflate(R.layout.fragment_list, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            TaskObject task = mListTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mListTasks.size();
        }
    }

}
