package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SingleFragmentActivity;
import com.example.joe.smarttask.SmartTask_MainPage.Task_P.Task;
import com.example.joe.smarttask.SmartTask_MainPage.Task_P.TaskActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 14/03/2017.
 */

public class ListFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_ListFragment";

    public Map<String, Task> tasks = new HashMap<String, Task>();
    private static RecyclerView sListRecyclerView;
    private static TaskAdapter sAdapter;
    private List<Task> mList;

    private static Context sContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        sListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        sListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList = ListTask.getmTaskList();
        updateUI(mList);
        sContext=this.getContext();
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
        if(sListRecyclerView!=null){
            sAdapter = new TaskAdapter(mList);
            sAdapter.notifyDataSetChanged();
            sListRecyclerView.setAdapter(sAdapter);
        }
    }


    private static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private CheckBox mTaskCompleted;

        private Task mTask;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_description);
            mTaskCompleted= (CheckBox) itemView.findViewById(R.id.list_item_box);
        }

        @Override
        public void onClick(View v) {
            Intent intent = TaskActivity.newIntent(sContext, mTask.getName());
            Log.d(TAG,mTask.getName());
            sContext.startActivity(intent);
        }


        public void bindTask(Task task) {
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
        private List<Task> mListTasks;

        public TaskAdapter(List<Task> mListTasks) {
            this.mListTasks = mListTasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SingleFragmentActivity.getAppContext());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task task = mListTasks.get(position);
            holder.bindTask(task);
        }

        @Override
        public int getItemCount() {
            return mListTasks.size();
        }
    }

}
