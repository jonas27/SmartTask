package com.example.joe.smarttask.SmartTask_MainPage.Settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;

import java.util.List;

/**
 * Created by joe on 23/04/2017.
 */

public class SettingsFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_SettF";
    private static RecyclerView sRecyclerView;
    private static SettingsFragment.Adapter sAdapter;
    private static Context sContext;
    private SettingsList mSettingsList;

    private Toolbar toolbar;

    private List<SettingsObject> mList;

    private SettingsObject mSettingsObject;

    public static void updateUI(List<SettingsObject> mList) {

        if (sRecyclerView != null) {
            mList = SettingsList.getList();
            sAdapter = new Adapter(mList);
            sAdapter.notifyDataSetChanged();
            sRecyclerView.setAdapter(sAdapter);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = SMMainActivity.getAppContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_list, container, false);

//        Set background color to blue
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.fragment_container);
        frameLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.settings_background_blue_dark));

        toolbar = (Toolbar) getActivity().findViewById(R.id.fragment_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(sContext.getResources().getString(R.string.settings_toolbar));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        initSingletons();

//        get list and update
//        mList = Settings.getSettingsList();
//        updateUI(mList);

        sRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        sRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    private void initSingletons() {
        mSettingsList = SettingsList.getSettingsList();
    }

    @Override
    public void onResume() {
        updateUI(mList);
        super.onResume();
    }

    // Provide a reference to the views for each data item
    private static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView describtion;
        ImageView icon;


        //        bind views here
        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.settings_list_title);
            describtion = (TextView) itemView.findViewById(R.id.settings_list_description);
            icon = (ImageView) itemView.findViewById(R.id.settings_list_ic);
        }

        @Override
        public void onClick(View v) {

        }

        //    specify individual tasks behaviour on layout
        public void bindTask(SettingsObject mObject) {
            title.setText(mObject.getmTitle());
            describtion.setText(mObject.getmDescription());
            setIcon(mObject.getmNumberInList());

        }

        private void setIcon(int i) {
            switch (i) {
                case 0: {
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_list_grey_24dp));
                    break;
                }
                case 1: {
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_notifications_grey_24dp));
                    break;
                }
                case 2: {
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_language_grey_24dp));
                    break;
                }
                case 3: {
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_monetization_on_grey_24dp));
                    break;
                }
                default: {
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_list_grey_24dp));
                    break;
                }

            }
        }

    }

    private static class Adapter extends RecyclerView.Adapter<Holder> {
        private List<SettingsObject> mListSettings;

        public Adapter(List<SettingsObject> mListSettings) {
            this.mListSettings = mListSettings;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SMMainActivity.getAppContext());
            View view = layoutInflater.inflate(R.layout.fragment_settings, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(SettingsFragment.Holder holder, int position) {

            SettingsObject mObject = mListSettings.get(position);
            holder.bindTask(mObject);
        }

        @Override
        public int getItemCount() {
            return mListSettings.size();
        }
    }
    
}
