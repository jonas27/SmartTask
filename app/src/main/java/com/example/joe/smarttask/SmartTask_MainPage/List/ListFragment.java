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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskPagerActivity;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 14/03/2017.
 */

public class ListFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_ListFragment";
    private static RecyclerView sListRecyclerView;
    private static TaskAdapter sAdapter;
    // [End: get Singletons]
    private static Context sContext;

    //    sets the seperating bar in the recycler view
    private static boolean firstCompletedTask = true;

    public Map<String, TaskObject> tasks = new HashMap<String, TaskObject>();
    // [Start: get Singletons]
    private FireBase mFireBase;
    private ListTask mListTask;
    private List<TaskObject> mList;


    //    Use notifyDataSetChanged on all views as we do not know
//    which View should be updated when changes on FireBase occur
//    Is it possible to change that? Results in efficiency gain
    public static void updateUI(List<TaskObject> mList) {
//        Log.d("CLASS_LF", Integer.toString(mList.size()));
//        Log.d("CLASS_LF", mList.get(0).getName());

        if (sListRecyclerView != null) {
            mList = ListTask.getTaskList();
            sAdapter = new TaskAdapter(mList);
            sAdapter.notifyDataSetChanged();
            sListRecyclerView.setAdapter(sAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_list, container, false);
        initSingletons();
        sListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        sListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList = ListTask.getTaskList();
        updateUI(mList);
        sContext = this.getContext();

        return view;
    }

    private void initSingletons() {
        mFireBase = FireBase.fireBase(getContext());
        mListTask = ListTask.list(getContext());
    }

    @Override
    public void onResume() {
        updateUI(mList);
        super.onResume();
    }

    // Provide a reference to the views for each data item
    private static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private CheckBox mTaskCompleted;
        private RoundedLetterView mPriority;
        private ImageView mTaskSolved;
        private ImageView mTaskUnsolved;
        private View mViewLine;
        private View mViewSeparator;
        private TextView mDateTextView;

        private TaskObject mTask;
        private GregorianCalendar cal;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_description);
            mTaskCompleted = (CheckBox) itemView.findViewById(R.id.list_item_box);
            mViewLine = (View) itemView.findViewById(R.id.list_line_divide);
            mTaskUnsolved = (ImageView) itemView.findViewById(R.id.list_task_uncheck);
            mTaskSolved = (ImageView) itemView.findViewById(R.id.list_task_check);
            mViewSeparator = (View) itemView.findViewById(R.id.list_item_separator);
            mPriority = (RoundedLetterView) itemView.findViewById(R.id.rlv_name_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date);


        }

        @Override
        public void onClick(View v) {
            if (mTask.getStatus().equals(SortList.DRAW_LINE)) {
//                divisor line, no action on click
            } else {
                Intent intent = TaskPagerActivity.newIntent(sContext, mTask.getId());
                sContext.startActivity(intent);
            }
        }

        public void bindTask(TaskObject task) {
            mTask = task;
            if (mTask.getName().toCharArray().length > 18) {
                mTitleTextView.setText(mTask.getName().substring(0, 17) + "...");
            } else {
                mTitleTextView.setText(mTask.getName());
            }
            mDescriptionTextView.setText(mTask.getDescription());

            cal = new GregorianCalendar();
            cal.setTimeInMillis(Long.parseLong(mTask.getDatetime()));
            mDateTextView.setText(new SimpleDateFormat("MMM").format(cal.getTime()) + " " + cal.get(Calendar.DAY_OF_MONTH));


//            change rounded layout view priority
            if (Integer.parseInt(mTask.getPriority()) == 1) {
                String s = Integer.toString(R.string.list_high_p);
                mPriority.setTitleText(sContext.getResources().getString(R.string.list_high_p));
                mPriority.setBackgroundColor(sContext.getResources().getColor(R.color.list_high_p_c));
            } else if (Integer.parseInt(mTask.getPriority()) == 2) {
                mPriority.setTitleText(sContext.getResources().getString(R.string.list_middle_p));
                mPriority.setBackgroundColor(sContext.getResources().getColor(R.color.list_middle_p_c));
            } else if (Integer.parseInt(mTask.getPriority()) == 3) {
                mPriority.setTitleText(sContext.getResources().getString(R.string.list_low_p));
                mPriority.setBackgroundColor(sContext.getResources().getColor(R.color.list_low_p_c));
            }


            mTitleTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mTaskUnsolved.setVisibility(View.VISIBLE);
            mViewSeparator.setVisibility(View.VISIBLE);
            mTaskSolved.setVisibility(View.INVISIBLE);
            mViewLine.setVisibility(View.INVISIBLE);
            mDateTextView.setVisibility(View.VISIBLE);


            if (mTask.getStatus().equals("true")) {
//                sListRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(sContext));
                mTaskUnsolved.setVisibility(View.INVISIBLE);
                mTaskSolved.setVisibility(View.VISIBLE);

            } else if (mTask.getStatus().equals(SortList.DRAW_LINE)) {
                mTitleTextView.setVisibility(View.INVISIBLE);
                mDescriptionTextView.setVisibility(View.INVISIBLE);
                mTaskUnsolved.setVisibility(View.INVISIBLE);
                mTaskSolved.setVisibility(View.INVISIBLE);
                mViewLine.setVisibility(View.VISIBLE);
                mViewSeparator.setVisibility(View.VISIBLE);
                mPriority.setVisibility(View.INVISIBLE);
                mDateTextView.setVisibility(View.INVISIBLE);
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
            LayoutInflater layoutInflater = LayoutInflater.from(SMMainActivity.getAppContext());
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
