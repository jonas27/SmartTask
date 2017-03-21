package com.example.joe.smarttask.SmartTask_MainPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.smarttask.R;

/**
 * Created by joe on 14/03/2017.
 */

public class SmartTask_ListTasks_Fragment extends Fragment {

    private RecyclerView mSmartTaskListTaskRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listtasks_smarttask, container, false);

        mSmartTaskListTaskRecyclerView = (RecyclerView) view.findViewById(R.id.smarttask_listtask_recycler_view);
        mSmartTaskListTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }


}
