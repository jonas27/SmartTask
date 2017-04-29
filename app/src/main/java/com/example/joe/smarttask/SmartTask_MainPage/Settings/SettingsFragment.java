package com.example.joe.smarttask.SmartTask_MainPage.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments.SubMenuActivity;

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
//        sContext = SMMainActivity.getAppContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Pass layout xml to the inflater and assign it to View v.
        View v = inflater.inflate(R.layout.recycler_list, container, false);
        sContext = v.getContext(); // Assign your v to context

//        Set background color to blue
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.fragment_container);
        frameLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.settings_background_blue_dark));

//        toolbar = (Toolbar) getActivity().findViewById(R.id.fragment_toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(sContext.getResources().getString(R.string.settings_toolbar));
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initSingletons();

//        get list and update
//        mList = Settings.getSettingsList();
//        updateUI(mList);

        sRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        sRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    private void initSingletons() {
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
        private SettingsObject mSettingsObject;

        //        bind views here (The Holder defines one list item, which are then coppied)
        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.settings_list_title);
            describtion = (TextView) itemView.findViewById(R.id.settings_list_description);
            icon = (ImageView) itemView.findViewById(R.id.settings_list_ic);
        }

        //        specify what happens when click on a list item
        @Override
        public void onClick(View v) {

            Intent intent = SubMenuActivity.newIntent(sContext, mSettingsObject.getmTitle());
            sContext.startActivity(intent);

        }

        //    specify individual settings behaviour on layout
        private void bindSetting(SettingsObject settingsObject) {
            mSettingsObject = settingsObject;
            title.setText(mSettingsObject.getmTitle());
            describtion.setText(mSettingsObject.getmDescription());
            setIcon(mSettingsObject.getmNumberInList());

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
                case 4: {
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_feedback_grey_24dp));
                    break;
                }
                case 5: {
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_about_grey_24dp));
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

            SettingsObject settingsObject = mListSettings.get(position);
            holder.bindSetting(settingsObject);
        }

        @Override
        public int getItemCount() {
            return mListSettings.size();
        }
    }
    
}
