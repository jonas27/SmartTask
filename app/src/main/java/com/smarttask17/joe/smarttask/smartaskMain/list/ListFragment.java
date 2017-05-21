package com.smarttask17.joe.smarttask.smartaskMain.list;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.calendar.CalendarView;
import com.smarttask17.joe.smarttask.smartaskMain.SmarttaskMainActivity;
import com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.FireBase;
import com.smarttask17.joe.smarttask.smartaskMain.task.TaskFragment;
import com.smarttask17.joe.smarttask.smartaskMain.task.TaskObject;
import com.smarttask17.joe.smarttask.smartaskMain.task.TaskPagerActivity;
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
 * Fragment for list of tasks
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
    public static List<TaskObject> sList;

    public static boolean tabletMode=false;

    private static FrameLayout detailView;
    private Fragment taskFragment;

//    Show first item in list view
    private boolean firstTime=true;

//    FireBase stuff
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;


    //    Use notifyDataSetChanged on all views as we do not know
//    which View should be updated when changes on FireBase occur
//    Is it possible to change that? Results in efficiency gain
    public void updateUI() {
        sList = ListOfTasks.getSortList();
        if (sListRecyclerView != null) {
            sAdapter = new TaskAdapter(sList);
            sListRecyclerView.setAdapter(sAdapter);
            sAdapter.notifyDataSetChanged();

        }
    }

    //    specifies what is initialized when the view is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_masterdetail, container, false);

        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
        int permsRequestCode = 200;
        requestPermissions(perms, permsRequestCode);

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

        sList = ListOfTasks.getSortList();

//        Add TaskFragment if tablet
        if(detailView!=null && sList.size()>1) {
            tabletMode=true;
//              @param getId: Create new Fragment with first TaskObject in list
            if(sList.get(0).getPriority()==-1 ){taskFragment = TaskFragment.newInstance(sList.get(1).getId());}
            else{taskFragment = TaskFragment.newInstance(sList.get(0).getId());}
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.detail_fragment_container, taskFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        updateUI();
        return view;
    }

//    private void initSingletons() {
//        mFireBase = FireBase.fireBase(getContext());
//    }

    @Override
    public void onResume() {
        updateUI();
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
        private RelativeLayout mRelativeLayout;
        private RoundedLetterView mPriority;
        private ImageView mTaskSolved;
        private ImageView mTaskUnsolved;
        private View mViewLine;
        private View mViewSeparator;
        private TextView mDateTextView;
        private TextView mResponsibleTextView;

        private TaskObject mTask;
        private GregorianCalendar cal;

        private TaskHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.list_relative_layout);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_description);
//            mTaskCompleted = (CheckBox) itemView.findViewById(R.id.list_item_box);
            mViewLine = (View) itemView.findViewById(R.id.list_line_divide);
            mTaskUnsolved = (ImageView) itemView.findViewById(R.id.list_task_uncheck);
            mTaskSolved = (ImageView) itemView.findViewById(R.id.list_task_check);
            mViewSeparator = (View) itemView.findViewById(R.id.list_item_separator);
            mPriority = (RoundedLetterView) itemView.findViewById(R.id.rlv_name_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date);
            mResponsibleTextView = (TextView) itemView.findViewById(R.id.list_task_responsible);
        }

        @Override
        public void onClick(View v) {
            if (mTask.getPriority()==-1) {
//                divisor line, no action on click
            } else{
//                Check for tablet mode
//                if it has a detailed view it is a tablet
            if(detailView==null) {
                Intent intent = TaskPagerActivity.newIntent(sContext, mTask.getId(),sList);
                sContext.startActivity(intent);
            }else {
//               @param getId: Create new Fragment with TaskObject with given id
                taskFragment = TaskFragment.newInstance(mTask.getId());
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.detail_fragment_container, taskFragment);
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            }
        }

        private void bindTask(TaskObject task) {
            mTask = task;
            if (mTask.getName().toCharArray().length > 22) {
                mTitleTextView.setText(mTask.getName().substring(0, 21) + "...");
            } else {
                mTitleTextView.setText(mTask.getName());
            }
            if (mTask.getDescription().toCharArray().length > 60) {
                mDescriptionTextView.setText(mTask.getDescription().substring(0, 59) + "...");
            } else {
                mDescriptionTextView.setText(mTask.getDescription());
            } if (mTask.getResponsible().toCharArray().length > 25) {
                mResponsibleTextView.setText(mTask.getResponsible().substring(0, 24) + "...");
            } else {
                mResponsibleTextView.setText(mTask.getResponsible());
            }

            cal = new GregorianCalendar();
            cal.setTimeInMillis((mTask.getDatetime()));
            mDateTextView.setText(new SimpleDateFormat("MMM").format(cal.getTime()) + " " + cal.get(Calendar.DAY_OF_MONTH));

//            change rounded layout view priority
            if (mTask.getPriority() == 0) {
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

            if(System.currentTimeMillis()>mTask.getDatetime() && !mTask.getStatus()){
                mTitleTextView.setTextColor(getResources().getColor(R.color.list_red));
            }else{
                mTitleTextView.setTextColor(getResources().getColor(R.color.black));
            }

            mPriority.setVisibility(View.VISIBLE);
            mTitleTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mTaskUnsolved.setVisibility(View.VISIBLE);
            mViewSeparator.setVisibility(View.VISIBLE);
            mTaskSolved.setVisibility(View.INVISIBLE);
            mViewLine.setVisibility(View.INVISIBLE);
            mDateTextView.setVisibility(View.VISIBLE);
            mResponsibleTextView.setVisibility(View.VISIBLE);

            if (mTask.getStatus()) {
//                sListRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(sContext));
                mTaskUnsolved.setVisibility(View.INVISIBLE);
                mTaskSolved.setVisibility(View.VISIBLE);
            } else if (mTask.getPriority()==-1) {
                mTitleTextView.setVisibility(View.INVISIBLE);
                mDescriptionTextView.setVisibility(View.INVISIBLE);
                mTaskUnsolved.setVisibility(View.INVISIBLE);
                mTaskSolved.setVisibility(View.INVISIBLE);
                mPriority.setVisibility(View.INVISIBLE);
                mDateTextView.setVisibility(View.INVISIBLE);
                mResponsibleTextView.setVisibility(View.INVISIBLE);
//                Set bar
                mViewLine.setVisibility(View.VISIBLE);
                mViewSeparator.setVisibility(View.VISIBLE);
            }

        }
    }

    //    Adapter converts an object at a certain position into a list row item which will then be inserted
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<TaskObject> mListTasks;
        private TaskAdapter(List<TaskObject> mListTasks) {
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

    private void pull() {
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("task");
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot mDataSnapshot) {
                Log.d(TAG, "pull in list fragment");
                callback(mDataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG + "Err", "loadPost:onCancelled", databaseError.toException());
            }
        };
        mPostReference.addValueEventListener(postListener);
        sList= ListOfTasks.getSortList();
        if(sAdapter!=null) {
            sAdapter.notifyDataSetChanged();
        }

    }

    private void callback(DataSnapshot mDataSnapshot) {
        ListOfTasks.setDataSnapshot(mDataSnapshot);
        sList= ListOfTasks.getSortList();
        updateUI();
        CalendarView.updateCalendar();
    }

}
