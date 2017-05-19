package com.smarttask17.joe.smarttask.smartaskMain.settings.settingsCoordinator.listSettings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.smarttask17.joe.smarttask.R;
import com.smarttask17.joe.smarttask.smartaskMain.list.ListOfTasks;
import com.smarttask17.joe.smarttask.smartaskMain.SmarttaskMainActivity;
import com.smarttask17.joe.smarttask.smartaskMain.settings.ListOfSettings;
import com.smarttask17.joe.smarttask.smartaskMain.task.TaskPagerActivity;

import java.util.List;

/**
 * Created by joe on 27/04/2017.
 */

public class SubSettingsListFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_SettF";


    private static RecyclerView sRecyclerView;
    private static RecyclerView.Adapter sAdapter;
    private static Context sContext;
    private static Callbacks mCallbacks;
    private ListOfSettings mListOfSettings;
    private List<SubSettingsListObject> list;

    public static void updateUI(List<SubSettingsListObject> list) {

        if (sRecyclerView != null) {
            list = SubsettingsListList.getList();
            sAdapter = new Adapter(list);
            sAdapter.notifyDataSetChanged();
            sRecyclerView.setAdapter(sAdapter);
        }
    }


    public static Intent newIntent(Context packageContext, String mId) {
        Intent intent = new Intent(packageContext, TaskPagerActivity.class);
        intent.putExtra("Settings", mId);
        return intent;
    }

    //    update the description to current selection
    private static void updateSettings() {
        mCallbacks.onSubSettingsUpdatedList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sContext = SmarttaskMainActivity.getAppContext();
        SubsettingsListList.setsContext(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_list, container, false);


        //        Set background color to blue
        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.fragment_container);
        frameLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.settings_background_blue_dark));

        sRecyclerView = (RecyclerView) v.findViewById(R.id.list_recycler_view);
        sRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onResume() {
        updateUI(list);
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        ListOfTasks.getSortList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * Required interface for hosting activities.
     */


    //    Adds a callback to update the Settings list view
    public interface Callbacks {
        void onSubSettingsUpdatedList();
    }

    // Setup the views for the items | Provides a reference for the views of each data item
    private static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SubSettingsListObject listObject;
        TextView title;
        TextView describtion;
        ImageView boxOn;
        ImageView boxOff;
        private Switch toggle;

        //        bind views to layout resources
        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.subsettings_list_title);
            describtion = (TextView) itemView.findViewById(R.id.subsettings_list_description);
            boxOn = (ImageView) itemView.findViewById(R.id.subsettings_box_on);
            boxOff = (ImageView) itemView.findViewById(R.id.subsettings_box_off);
            toggle = (Switch) itemView.findViewById(R.id.subsettings_toggle);
        }

        //        Define behaviour for click on item (it's possible to do different actions for different items
        @Override
        public void onClick(View v) {
            switch (listObject.getOrder()) {
                case SubSettingsListObject.ORDER_BY_DATE: {
                    SubSettingsListObject.setPreferredOrder(SubSettingsListObject.ORDER_BY_DATE);
                    break;
                }
                case SubSettingsListObject.ORER_BY_PRIORITY: {
                    SubSettingsListObject.setPreferredOrder(SubSettingsListObject.ORER_BY_PRIORITY);
                    break;
                }
            }

            if (listObject.getTitle().equals(sContext.getResources().getString(R.string.subsettings_list_title_pastitems))) {
                ListOfTasks.getSortList();
                listObject.setShowPastItems(!listObject.isShowPastItems());
            }


            sAdapter.notifyDataSetChanged();
//            update the description on SettingsActivity
            updateSettings();
        }


        //        define views in the layout file for each Object
        private void setViews(SubSettingsListObject o) {
            listObject = o;
            title.setText(o.getTitle());
            describtion.setText(o.getDescription());
            toggle.setChecked(o.isShowPastItems());


            if (o.getOrder() == SubSettingsListObject.getPreferredOrder()) {
                boxOn.setVisibility(View.VISIBLE);
            } else if (o.getOrder() == SubSettingsListObject.getPreferredOrder()) {
                boxOn.setVisibility(View.VISIBLE);
            } else {
                boxOn.setVisibility(View.INVISIBLE);
            }

            if (o.getTitle().equals(sContext.getResources().getString(R.string.subsettings_list_title_pastitems))) {
                toggle.setVisibility(View.VISIBLE);
                boxOn.setVisibility(View.INVISIBLE);
                boxOff.setVisibility(View.INVISIBLE);
            } else {
                toggle.setVisibility(View.INVISIBLE);
            }
        }


    }

    //    Purpose of the Addapter is to provide the data items for the recycler view (or more general the AdapterView)
    private static class Adapter extends RecyclerView.Adapter<Holder> {
        private List<SubSettingsListObject> list;

        public Adapter(List<SubSettingsListObject> list) {
            this.list = list;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SmarttaskMainActivity.getAppContext());
            View view = layoutInflater.inflate(R.layout.fragment_settings_list, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            SubSettingsListObject o = list.get(position);
            holder.setViews(o);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }


}
