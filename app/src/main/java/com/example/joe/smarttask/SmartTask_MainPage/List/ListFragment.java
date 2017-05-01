package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskFragment;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskPagerActivity;
import com.github.pavlospt.roundedletterview.RoundedLetterView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private static final String TAG = "CL_ListFr";
    private static RecyclerView sListRecyclerView;
    private static TaskAdapter sAdapter;
    // [End: get Singletons]
    private static Context sContext;

    //    sets the seperating bar in the recycler view
    private static boolean firstCompletedTask = true;
    private static List<TaskObject> sList;
    public Map<String, TaskObject> tasks = new HashMap<String, TaskObject>();
    // [Start: get Singletons]
    private FireBase mFireBase;
    private ListTask mListTask;


    private static FrameLayout detailView;
    private Fragment taskFragment;

//    Show first item in list view
    private boolean firstTime=true;

//    FireBase stuff
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;


    //    Use notifyDataSetChanged on all views as we do not know
//    which View should be updated when changes on FireBase occur
//    Is it possible to change that? Results in efficiency gain
    public void updateUI(List<TaskObject> list) {
//        Log.d("CLASS_LF", Integer.toString(mList.size()));
//        Log.d("CLASS_LF", mList.get(0).getName());

        sList = ListTask.getTaskList();
        if (sListRecyclerView != null) {
            sAdapter = new TaskAdapter(list);
            sAdapter.notifyDataSetChanged();
            sListRecyclerView.setAdapter(sAdapter);
        }

    }

    //    specifies what is initialized when the view is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_masterdetail, container, false);
//        initSingletons();
        sContext = this.getContext();

        sListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        sListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //        bind detailView for two pane tablet mode

        detailView = (FrameLayout) view.findViewById(R.id.detail_fragment_container);

        //        Firebase stuff
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        pull();


        sList = ListTask.getTaskList();
//        updateUI(sList);


        if(detailView!=null && sList.size()>0) {
//              @param getId: Create new Fragment with first TaskObject in list
            taskFragment = TaskFragment.newInstance(sList.get(0).getId());
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.detail_fragment_container, taskFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }




        return view;
    }


//    private void initSingletons() {
//        mFireBase = FireBase.fireBase(getContext());
//    }

    @Override
    public void onResume() {
        updateUI(sList);
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
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
            } else{
//                Check for tablet mode
            if(detailView==null) {
                Intent intent = TaskPagerActivity.newIntent(sContext, mTask.getId());
                sContext.startActivity(intent);
            }else {
//               @param getId: Create new Fragment with TaskObject with given id
                taskFragment = TaskFragment.newInstance(mTask.getId());
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.detail_fragment_container, taskFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            }
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

            cal = new GregorianCalendar();
            cal.setTimeInMillis(Long.parseLong(mTask.getDatetime()));
            mDateTextView.setText(new SimpleDateFormat("MMM").format(cal.getTime()) + " " + cal.get(Calendar.DAY_OF_MONTH));

//            change rounded layout view priority
            if (Integer.parseInt(mTask.getPriority()) == 1) {
                String s = Integer.toString(R.string.list_high_p);
                mPriority.setTitleText(sContext.getResources().getString(R.string.list_high_p));
                mPriority.setBackgroundColor(sContext.getResources().getColor(R.color.list_high_p_red));
            } else if (Integer.parseInt(mTask.getPriority()) == 2) {
                mPriority.setTitleText(sContext.getResources().getString(R.string.list_middle_p));
                mPriority.setBackgroundColor(sContext.getResources().getColor(R.color.list_middle_p_orange));
            } else if (Integer.parseInt(mTask.getPriority()) == 3) {
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


    //    Adapter converts an object at a certain position into a list row item which will then be inserted
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
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
            if (mListTasks != null) {
                return mListTasks.size();
            } else {
                return 0;
            }
        }
    }





    private void pull() {
        Log.d(TAG, mAuth.getCurrentUser().toString());
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("task");
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot mDataSnapshot) {
                callback(mDataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG + "Err", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);
        sList=ListTask.getTaskList();
        if(sAdapter!=null) {
            sAdapter.notifyDataSetChanged();
        }

    }

    private void callback(DataSnapshot mDataSnapshot) {
        ListTask.setDataSnapshot(mDataSnapshot);
        sList=ListTask.getTaskList();

        Log.d(TAG, sList.get(2).getName());
        updateUI(sList);

        if(detailView!=null && firstTime) {
//              @param getId: Create new Fragment with first TaskObject in list
            taskFragment = TaskFragment.newInstance(sList.get(0).getId());
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.detail_fragment_container, taskFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            firstTime=false;
        }

        //            TODO check if calendar has been initialized or initialize calendar before calling update
//        CalendarView.updateCalendar();


    }

}
