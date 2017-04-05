package com.example.joe.smarttask.SmartTask_MainPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joe.smarttask.R;

import java.util.List;

/**
 * Created by joe on 14/03/2017.
 */

public class ListFragment extends Fragment {


    private RecyclerView mListRecyclerView;
    private TaskAdapter mAdapter;

    /* This Method should host nothing but super.onCreate method call as fragments follow a slightly different lifecycle than normal activities.
       All intialisations and else should be in onCreateView
    */


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
            holder.mTitleTextView.setText(task.getmTaskName());
        }

        @Override
        public int getItemCount() {
            return mListTasks.size();
        }
    }

}
