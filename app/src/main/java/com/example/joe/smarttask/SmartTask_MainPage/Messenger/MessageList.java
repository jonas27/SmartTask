package com.example.joe.smarttask.SmartTask_MainPage.Messenger;

import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 07/05/2017.
 */

public class MessageList {

    //TAG for Logs
    private static final String TAG = "CL_MessengerList";

    private static List<MessageObject> list;

    private static DataSnapshot sDataSnapshot;

    private MessageList(){}

    public static void setDataSnapshot(DataSnapshot mDataSnapshot) {
        sDataSnapshot = mDataSnapshot;
        list = new ArrayList<MessageObject>();
        createList();
    }

    public static List<MessageObject> getSortedMessageList(){
//        TODO: Sort
        return list;
    }


    /**
     * puts Data in MessageObjects
     * List is already sorted as new entries are saved after old entries --> kind of last in last out
     */
    private static void createList() {
        list = new ArrayList<>();
        if (sDataSnapshot != null) {
            for (Iterator<DataSnapshot> i = sDataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                MessageObject messageObject = i.next().getValue(MessageObject.class);
                list.add(messageObject);
            }
        }
    }
}



//    private static List<MessageObject> sortList(List<MessageObject> list){
//        for(int c=0; c<list.size(); c++){
//            for(int counter=c+1; counter<list.size(); counter++){
//                if(Integer.parseIntlist.get(counter).getDateTime()list.get(c).getDateTime())
//            }
//        }
//        return list;
//    }