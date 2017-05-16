package com.example.joe.smarttask.smartaskMain.messenger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.smarttask.R;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.FireBase;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
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
    private static LinearLayoutManager mLinearLayoutManager;

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
        sRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        sRecyclerView.setLayoutManager(mLinearLayoutManager);
        mLinearLayoutManager.setStackFromEnd(true);

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
//        ImageView icon;
        private MessageObject messageObject;

        //        bind views here (The Holder defines one list item, which are then copied)
        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            icon = (ImageView) itemView.findViewById(R.id.icon);
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

        //    specify individual settings behaviour on layout
        private void bindMessage(MessageObject mMessageObject) {
            if (mMessageObject != null) {
                if (mMessageObject.getSenderId().equals(SharedPrefs.getCurrentProfile())) {
                    message_own.setText(mMessageObject.getMessage());
                    name_own.setText(mMessageObject.getSenderName());
                    new GregorianCalendar();
                    datetime_own.setText(new SimpleDateFormat("EEE, d MMM HH:mm").format(mMessageObject.getDateTime()));
                    message_own.setVisibility(View.VISIBLE);
                    name_own.setVisibility(View.VISIBLE);
                    datetime_own.setVisibility(View.VISIBLE);
                    message_other.setVisibility(View.INVISIBLE);
                    name_other.setVisibility(View.INVISIBLE);
                    datetime_other.setVisibility(View.INVISIBLE);
                } else {
                    message_own.setVisibility(View.INVISIBLE);
                    name_own.setVisibility(View.INVISIBLE);
                    datetime_own.setVisibility(View.INVISIBLE);
                    message_other.setVisibility(View.VISIBLE);
                    name_other.setVisibility(View.VISIBLE);
                    datetime_other.setVisibility(View.VISIBLE);
                    message_other.setText(mMessageObject.getMessage());
                    name_other.setText(mMessageObject.getSenderName());
                    datetime_other.setText(new SimpleDateFormat("EEE, d MMM HH:mm").format(mMessageObject.getDateTime()));
//                    setIcon(mMessageObject.getSenderId());
                }
            }

        }

////        TODO: this is super intensive for the phone!
//        private void setIcon(String userId){
//            String path=DIR + userId + ".jpg";
//            if(userId!="0"){
//                File profileImage = new File(path);
//                if (profileImage.length()>0) {
//                    icon.setImageBitmap(PictureConverter.getRoundProfilePicture(path, 30));
//                } else {
////                    picture is downloaded either at ChooseProfile or at change user (in this class)
//                        icon.setImageDrawable(sContext.getResources().getDrawable(R.mipmap.smlogo));
//                    }
//                }
//            }
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
            View view = layoutInflater.inflate(R.layout.fragment_messenger, parent, false);
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

        @Override
        public void onViewAttachedToWindow(Holder holder){
            super.onViewAttachedToWindow(holder);
            if(!holder.name_other.getText().toString().contentEquals("#EMPTYVIEW") || !holder.name_own.getText().toString().contentEquals("#EMPTYVIEW"))
                holder.itemView.setVisibility(View.VISIBLE);
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
        sList = ListOfMessages.getSortedMessageList();
        if (sAdapter != null) {
            sAdapter.notifyDataSetChanged();
//            sAdapter.notifyItemInserted(sList.size());
            mLinearLayoutManager.scrollToPosition(sList.size());
        }

    }

    private void callback(DataSnapshot mDataSnapshot) {
        ListOfMessages.setDataSnapshot(mDataSnapshot);
        sList = ListOfMessages.getSortedMessageList();
        updateUI(sList);
    }

    //    Use notifyDataSetChanged on all views as we do not know
//    which View should be updated when changes on FireBase occur
//    Is it possible to change that? Results in efficiency gain
    public void updateUI(List<MessageObject> list) {
        sList = ListOfMessages.getSortedMessageList();
        if (sRecyclerView != null) {
            sAdapter = new Adapter(sList);
            sRecyclerView.setAdapter(sAdapter);
            sAdapter.notifyDataSetChanged();
        }

    }

}
