package com.smarttask17.joe.smarttask.smartaskMain.settings;

import android.app.Activity;
import android.content.Context;
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

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.SmarttaskMainActivity;

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
    private static Callbacks sCallbacks;
    private static List<SettingsObject> sList;
    private ListOfSettings mListOfSettings;
    private Toolbar toolbar;


    public static void updateUI() {
        if (sRecyclerView != null) {
            sList = ListOfSettings.getList();
            sAdapter = new Adapter(sList);
            sAdapter.notifyDataSetChanged();
            sRecyclerView.setAdapter(sAdapter);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sCallbacks = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Pass layout xml to the inflater and assign it to View v.
        View v = inflater.inflate(R.layout.recycler_list, container, false);
        sContext = v.getContext(); // Assign your v to context

        sList = ListOfSettings.getList();

//        Set background color to blue
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.fragment_container);
        frameLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.settings_background_blue_dark));

        sRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        sRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onResume() {
        updateUI();
        super.onResume();
    }

    /**
     * Hosting activities must also implement Callbacks!
     */
    public interface Callbacks {
        void onItemSelected(SettingsObject settingsObject);
    }

    // Provides a reference to the views for each data item
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

//            Intent intent = SubMenuActivity.newIntent(sContext, mSettingsObject.getmTitle());
//            sContext.startActivity(intent);
            updateUI();
            sCallbacks.onItemSelected(mSettingsObject);

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
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_grade_grey_24dp));
                    break;
                }
                case 5: {
                    icon.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_feedback_grey_24dp));
                    break;
                }
                case 6: {
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

    //    Purpose of the Addapter is to provide the data items for the recycler view (or more general the AdapterView)
    private static class Adapter extends RecyclerView.Adapter<Holder> {
        private List<SettingsObject> mListSubSettings;

        public Adapter(List<SettingsObject> mListSettings) {
            this.mListSubSettings = mListSettings;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SmarttaskMainActivity.getAppContext());
            View view = layoutInflater.inflate(R.layout.fragment_settings, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(SettingsFragment.Holder holder, int position) {
            SettingsObject settingsObject = mListSubSettings.get(position);
            holder.bindSetting(settingsObject);
        }

        @Override
        public int getItemCount() {
            return mListSubSettings.size();
        }
    }
    
}
