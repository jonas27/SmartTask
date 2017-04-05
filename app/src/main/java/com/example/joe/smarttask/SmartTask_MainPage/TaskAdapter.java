package com.example.joe.smarttask.SmartTask_MainPage;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;

import java.util.List;

/**
 * Created by Jones on 04-04-2017.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    int layoutResourceId;
    private List<Task>  data;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Task task = data.get(position);
        holder.txtTask.setText(task.getmTaskName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTask;

    public MyViewHolder(View view) {
        super(view);
        txtTask = (TextView) view.findViewById(R.id.txtTitle);
    }
}
    public TaskAdapter(List<Task> data) {
        this.data = data;
    }
}