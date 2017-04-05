package com.example.joe.smarttask.SmartTask_MainPage;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;

/**
 * Created by Jones on 04-04-2017.
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    Context context;
    int layoutResourceId;
    Task data[] = null;

    public TaskAdapter(Context context, int layoutResourceId, Task[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TaskHolder();
            holder.txtTask = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }

        Task Task = data[position];
        holder.txtTask.setText(Task.name);

        return row;
    }

    static class TaskHolder
    {
        TextView txtTask;
    }
}