package com.example.joe.smarttask.smartaskMain.calendar;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.smartaskMain.list.ListOfTasks;
import com.example.joe.smarttask.smartaskMain.list.ListSorter;
import com.example.joe.smarttask.smartaskMain.SmarttaskMainActivity;
import com.example.joe.smarttask.smartaskMain.task.TaskObject;
import com.example.joe.smarttask.smartaskMain.task.TaskPagerActivity;
import com.github.pavlospt.roundedletterview.RoundedLetterView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by joe on 28/04/2017.
 */

public class SingleDayFragment extends Fragment {

    private static Context sContext;
    private Fragment taskFragment;
    private List<TaskObject> sList;
    private RecyclerView sListRecyclerView;
    private static SingleDayFragment.TaskAdapter sAdapter;
    private String TAG = "sDayFragment";
    private List<TaskObject> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_list, container, false);
        sContext = this.getContext();


        sListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        sListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sList = ListOfTasks.getSortList();
        list = new ArrayList<TaskObject>();

        Date ccDate = new Date(SingleDayActivity.getDate());
        for (Iterator<TaskObject> i = sList.iterator(); i.hasNext(); ) {
            TaskObject current = i.next();
            Date cDate = new Date((current.getDatetime()));
            Log.d(TAG," task "+cDate.getDate()+" "+cDate.getMonth()+" "+cDate.getYear());
            Log.d(TAG," current "+ccDate.getDate()+" "+ccDate.getMonth()+" "+ccDate.getYear());
            if(ccDate.getDate()==cDate.getDate()&&ccDate.getMonth()==cDate.getMonth()&&ccDate.getYear()==cDate.getYear()){
                Log.d(TAG," adding "+current.toString());
                list.add(current);
            }
        }
        if (sListRecyclerView != null&&!list.isEmpty()) {
            sAdapter = new TaskAdapter(list);
            sAdapter.notifyDataSetChanged();
            sListRecyclerView.setAdapter(sAdapter);
        }

        return view;
    }


    // Holder describes and provides access to the views for each item in the list
    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private CheckBox mTaskCompleted;
        private RoundedLetterView mPriority;
        private ImageView mTaskSolved;
        private ImageView mTaskUnsolved;
        private View mViewLine;
        private View mViewSeparator;
        private TextView mDateTextView;
        private TextView mTaskResponsibleTextView;

        private TaskObject mTask;
        private GregorianCalendar cal;

        public TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_description);
//            mTaskCompleted = (CheckBox) itemView.findViewById(R.id.list_item_box);
            mViewLine = (View) itemView.findViewById(R.id.list_line_divide);
            mTaskUnsolved = (ImageView) itemView.findViewById(R.id.list_task_uncheck);
            mTaskSolved = (ImageView) itemView.findViewById(R.id.list_task_check);
            mViewSeparator = (View) itemView.findViewById(R.id.list_item_separator);
            mPriority = (RoundedLetterView) itemView.findViewById(R.id.rlv_name_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date);
            mTaskResponsibleTextView = (TextView) itemView.findViewById(R.id.list_task_responsible);
        }

        @Override
        public void onClick(View v) {
            Intent intent = TaskPagerActivity.newIntent(sContext, mTask.getId(),list);
            sContext.startActivity(intent);
        }

        private void bindTask(TaskObject task) {
            mTask = task;
            if (mTask.getName().toCharArray().length > 17) {
                mTitleTextView.setText(mTask.getName().substring(0, 16) + "...");
            } else {
                mTitleTextView.setText(mTask.getName());
            }
            if (mTask.getDescription().toCharArray().length > 60) {
                mDescriptionTextView.setText(mTask.getDescription().substring(0, 59) + "...");
            } else {
                mDescriptionTextView.setText(mTask.getDescription());
            }
            if (mTask.getResponsible().toCharArray().length > 15) {
                mTaskResponsibleTextView.setText(mTask.getResponsible().substring(0, 14) + "...");
            } else {
                mTaskResponsibleTextView.setText(mTask.getResponsible());
            }




            cal = new GregorianCalendar();
            cal.setTimeInMillis((mTask.getDatetime()));
            mDateTextView.setText(new SimpleDateFormat("MMM").format(cal.getTime()) + " " + cal.get(Calendar.DAY_OF_MONTH));

//            change rounded layout view priority
            if ((mTask.getPriority()) == 0) {
                String s = Integer.toString(R.string.list_high_p);
                mPriority.setTitleText(sContext.getResources().getString(R.string.list_high_p));
                mPriority.setBackgroundColor(sContext.getResources().getColor(R.color.list_high_p_red));
            } else if ((mTask.getPriority()) == 1) {
                mPriority.setTitleText(sContext.getResources().getString(R.string.list_middle_p));
                mPriority.setBackgroundColor(sContext.getResources().getColor(R.color.list_middle_p_orange));
            } else if ((mTask.getPriority()) == 2) {
                mPriority.setTitleText(sContext.getResources().getString(R.string.list_low_p));
                mPriority.setBackgroundColor(sContext.getResources().getColor(R.color.list_low_p_green));
            }

            mTitleTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mTaskUnsolved.setVisibility(View.VISIBLE);
            mViewSeparator.setVisibility(View.VISIBLE);
            mTaskSolved.setVisibility(View.INVISIBLE);
            mViewLine.setVisibility(View.INVISIBLE);
            mDateTextView.setVisibility(View.VISIBLE);
            mTaskResponsibleTextView.setVisibility(View.VISIBLE);

            if (mTask.getStatus().equals("true")) {
//                sListRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(sContext));
                mTaskUnsolved.setVisibility(View.INVISIBLE);
                mTaskSolved.setVisibility(View.VISIBLE);
            } else if (mTask.getStatus().equals(ListSorter.DRAW_LINE)) {
                mTitleTextView.setVisibility(View.INVISIBLE);
                mDescriptionTextView.setVisibility(View.INVISIBLE);
                mTaskResponsibleTextView.setVisibility(View.INVISIBLE);
                mTaskUnsolved.setVisibility(View.INVISIBLE);
                mTaskSolved.setVisibility(View.INVISIBLE);
                mViewLine.setVisibility(View.VISIBLE);
                mViewSeparator.setVisibility(View.VISIBLE);
                mPriority.setVisibility(View.INVISIBLE);
                mDateTextView.setVisibility(View.INVISIBLE);
            }

        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<TaskObject> mListTasks;

        public TaskAdapter(List<TaskObject> mListTasks) {
            this.mListTasks = mListTasks;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SmarttaskMainActivity.getAppContext());
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
            if (mListTasks != null) {
                return mListTasks.size();
            } else {
                return 0;
            }
        }
    }
}
