package com.example.joe.smarttask.SmartTask_MainPage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joe.smarttask.R;

/**
 * Created by joe on 14/03/2017.
 */

public class ListFragment extends Fragment {


    private static ListFragment sList;
    private RecyclerView mListRecyclerView;
    private Context context;

    /* This Method should host nothing but super.onCreate method call as fragments follow a slightly different lifecycle than normal activities.
       All intialisations and else should be in onCreateView
    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mListRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        mListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }


    private class CrimeHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;

        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }

//    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
//        private ListTask<Task> mTasks;
//
//        public CrimeAdapter(ListTask<Task> mTask) {
//            mTasks = tasks;
//        }
//
//        @Override
//        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            View view = layoutInflater
//                    .inflate(android.R.layout.simple_list_item_1, parent, false);
//            return new CrimeHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(CrimeHolder holder, int position) {
//            Crime crime = mCrimes.get(position);
//            holder.mTitleTextView.setText(crime.getTitle());
//        }
//
//        @Override
//        public int getItemCount() {
//            return mCrimes.size();
//        }
//    }

}
