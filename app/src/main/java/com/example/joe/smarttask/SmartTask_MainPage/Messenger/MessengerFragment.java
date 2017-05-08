package com.example.joe.smarttask.SmartTask_MainPage.Messenger;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.SmartTask_MainPage.List.ListTask;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Profile.ProfileObject;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.FireBase;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.PictureScale;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by joe on 07/05/2017.
 */

public class MessengerFragment extends Fragment {

    //TAG for Logs
    private static final String TAG = "CL_MessengerFr";

    private static final String DIR = "/storage/emulated/0/smarttask/";

    //    FireBase stuff
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ValueEventListener postListener;
    private DatabaseReference mPostReference;

    private FireBase fireBase;
    private static Context sContext;
    private static RecyclerView sRecyclerView;
    private static Adapter sAdapter;

    //    Views
    private EditText message;
    private ImageView send;

    //    normal objects
    private MessageObject mO;
    private static List<MessageObject> sList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_list, container, false);

        sContext = getContext();

        fireBase = FireBase.fireBase(sContext);

        message = (EditText) getActivity().findViewById(R.id.message);
        send=(ImageView) getActivity().findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getText().toString().compareTo("")!=0){
                    createNewMessage();
                fireBase.createMessage(mO);
                }
            }
        });

//        get FireBase instances and pull profiles
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        pullMessages();

        sRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        sRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void setSendButton(){}


    //    [Start: RecyclerView Holder and Adapter]
    // Provides a reference to the views for each data item
    private static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name_own;
        TextView name_other;
        TextView message_own;
        TextView message_other;
        TextView datetime_own;
        TextView datetime_other;
        private MessageObject messageObject;

        //        bind views here (The Holder defines one list item, which are then coppied)
        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name_own = (TextView) itemView.findViewById(R.id.name_own);
            name_other = (TextView) itemView.findViewById(R.id.name_other);
            message_own = (TextView) itemView.findViewById(R.id.messages_own);
            message_other = (TextView) itemView.findViewById(R.id.messages_other);
            datetime_own = (TextView) itemView.findViewById(R.id.datetime_own);
            datetime_other = (TextView) itemView.findViewById(R.id.datetime_other);
        }

        //        specify what happens when click on a list item
        @Override
        public void onClick(View v) {


        }


        //        TODO: Lots of duplicated code with loading image from sd --> centralize somewhere
        //    specify individual settings behaviour on layout
        private void bindMessage(MessageObject mMessageObject) {
            if (mMessageObject != null) {
                if (mMessageObject.getSenderId().equals(SharedPrefs.getCurrentProfile())) {
                    message_own.setText((mMessageObject.getMessage()));
                    name_own.setText(mMessageObject.getSenderName());
                    datetime_own.setText(Long.toString(mMessageObject.getDateTime()));
                    message_other.setVisibility(View.INVISIBLE);
                    name_other.setVisibility(View.INVISIBLE);
                    datetime_other.setVisibility(View.INVISIBLE);
                } else {
                    message_own.setVisibility(View.INVISIBLE);
                    name_own.setVisibility(View.INVISIBLE);
                    datetime_own.setVisibility(View.INVISIBLE);
                    message_other.setText((mMessageObject.getMessage()));
                    name_other.setText(mMessageObject.getSenderName());
                    datetime_other.setText(Long.toString(mMessageObject.getDateTime()));
                }
            }

        }
    }

    //    Purpose of the Addapter is to provide the data items for the recycler view (or more general the AdapterView)
    private static class Adapter extends RecyclerView.Adapter<Holder> {
        private List<MessageObject> list;

        public Adapter(List<MessageObject> mList) {
            this.list = mList;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(sContext);
            View view = layoutInflater.inflate(R.layout.messenger_recycler_view, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            MessageObject messageObject = list.get(position);
            holder.bindMessage(messageObject);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
//    [End: RecyclerView Holder and Adapter]


    public void createNewMessage() {
        mO = new MessageObject();
        mO.setMessage(message.getText().toString());
        mO.setDateTime(Calendar.getInstance().getTimeInMillis());
        mO.setSenderId(SharedPrefs.getCurrentProfile());
        mO.setSenderName(SharedPrefs.getCurrentUser());
        message.setText("");
    }

    private void pullMessages() {
        Log.d(TAG, mAuth.getCurrentUser().toString());
        mPostReference = FirebaseDatabase.getInstance().getReference().child("User/" + user.getUid()).child("messages");
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
        sList = MessageList.getSortedMessageList();
        if (sAdapter != null) {
            sAdapter.notifyDataSetChanged();
        }

    }

    private void callback(DataSnapshot mDataSnapshot) {
        MessageList.setDataSnapshot(mDataSnapshot);
        sList = MessageList.getSortedMessageList();

//        Log.d(TAG, sList.get(2).getName());
        updateUI(sList);
    }

    //    Use notifyDataSetChanged on all views as we do not know
//    which View should be updated when changes on FireBase occur
//    Is it possible to change that? Results in efficiency gain
    public void updateUI(List<MessageObject> list) {
//        Log.d("CLASS_LF", Integer.toString(mList.size()));
//        Log.d("CLASS_LF", mList.get(0).getName());

        sList = MessageList.getSortedMessageList();
//        Log.d(TAG, "pull Messafes post reference: " + sList.get(0).getSenderName());

        if (sRecyclerView != null) {
            sAdapter = new Adapter(sList);
            sAdapter.notifyDataSetChanged();
            sRecyclerView.setAdapter(sAdapter);
        }

    }

}
