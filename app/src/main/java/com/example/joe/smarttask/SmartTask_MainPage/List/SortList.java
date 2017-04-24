package com.example.joe.smarttask.SmartTask_MainPage.List;

import android.util.Log;

import com.example.joe.smarttask.SmartTask_MainPage.SMMainActivity;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SettingsHandler;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 24/04/2017.
 */

public class SortList {

    private static final String TAG = "CL_SortList";

    private static SharedPrefs sharedPrefs;


    public static List<TaskObject> sortList(List<TaskObject> list) {
        List<TaskObject> newList = list;
        return sortByDate(newList);
    }

    private static List<TaskObject> determineSort(List<TaskObject> list) {
        List<TaskObject> newList = new ArrayList<>();
        sharedPrefs = SharedPrefs.getSharedPrefs(SMMainActivity.getAppContext());
        switch (sharedPrefs.getSharedPrefencesListSort()) {
            case SettingsHandler.LIST_SORTED_DATE: {
                newList = (list);
                break;
            }
            default: {
                newList = list;
            }
        }


        return newList;
    }


    private static synchronized List<TaskObject> sortByDate(List<TaskObject> list) {
        List<TaskObject> newList = list;
        List<TaskObject> sortedList = new ArrayList<>();
        int position = 0;

        for (TaskObject t : newList) {
            position = 0;
            for (int c = 0; c < sortedList.size(); c++) {
                if (Long.parseLong(t.getDatetime()) > Long.parseLong(sortedList.get(c).getDatetime())) {
                    position = c;
                    break;
                }
                position = c + 1;
            }
            sortedList.add(position, t);
        }

        for (TaskObject t : sortedList) {
            Log.d(TAG, t.getName());
        }
        return sortedList;

    }

}


//
//
//
//    //    [Start: Sort by Date (merge Sort)]
////    leave creation of new List as not it is not working --> don't do: return divideList(list);
////    TODO: Implement merge sort correctly. Maybe....
//
//    protected static synchronized List<TaskObject>  sortedList(List<TaskObject> list){
//        List<TaskObject> newList=list;
//        return sortDate(newList);
//    }
//
//    private static List<TaskObject> sortList(List<TaskObject> list) {
//        while(locked) {}
//        List<TaskObject> newList=new ArrayList<TaskObject>();
//        locked=true;
//        Log.d(TAG, " locked");
//
//        sharedPrefs = SharedPrefs.getSharedPrefs(SMMainActivity.getAppContext());
//        switch (sharedPrefs.getSharedPrefencesListSort()) {
//            case SettingsHandler.LIST_SORTED_DATE: {
//                newList=sortDate(list);
//                break;
//            }
//        }
//
//
//    if(newList==null){newList=sortDate(list);}
//        return newList;
//    }
//
//    private static List<TaskObject> sortDate(List<TaskObject> list) {
//
//        for(TaskObject i:list){
//            Log.d(TAG, "list: " + i.getName());
//        }
//
//        List<TaskObject> sortedList = divideList(list);
////        List<TaskObject> splitedList = splitList(sortedList);
//        return sortedList;
//    }
//
//    private static List<TaskObject> divideList(List<TaskObject> list) {
//        List<TaskObject> dList=list;
//        List<TaskObject> upperList = new ArrayList<TaskObject>();
//        List<TaskObject> bottomList = dList;
//        if (list.size() <= 1) {
//            return dList;
//        } else {
//            for (int counter = 0; counter < dList.size() / 2; counter++) {
//                upperList.add(0, dList.get(counter));
//                bottomList.remove(0);
//            }
//            upperList = divideList(upperList);
//            bottomList = divideList(bottomList);
//        }
//        List<TaskObject> l = mergeList(bottomList, upperList);
//        return l;
//    }
//
//
////    private static  List<TaskObject> splitList(List<TaskObject> myLlist){
////        List<TaskObject> checkedTaskAtBottomList= new ArrayList<>();
////        for (TaskObject t: myLlist){
////            if(t.getStatus().equals("true")){
////                checkedTaskAtBottomList.add(checkedTaskAtBottomList.size(),t);
////                myLlist.remove(t);
////            }
////        }
////        for (TaskObject t: myLlist){
////                checkedTaskAtBottomList.add(checkedTaskAtBottomList.size(),t);
////        }
////        return checkedTaskAtBottomList;
////    }
//
//    private static List<TaskObject> mergeList(List<TaskObject> bottom, List<TaskObject> upper) {
//        List<TaskObject> newList = new ArrayList<>();
//        for(TaskObject i:upper){
//            Log.d(TAG, "upper: " + i.getName());
//        }
//        for(TaskObject i:bottom){
//            Log.d(TAG, "bottom: " + i.getName());
//        }
//
//        int size = (bottom.size() + upper.size());
//        for (int c = 0; c < size; c++) {
//            if (bottom.size() > 0 && upper.size() > 0 && Long.parseLong(bottom.get(0).getDatetime()) < Long.parseLong(upper.get(0).getDatetime())) {
//                newList.add(bottom.get(0));
//                bottom.remove(0);
//            } else if (bottom.size() == 0) {
//                newList.add(upper.get(0));
//                upper.remove(0);
//            } else if (upper.size() == 0) {
//                newList.add(bottom.get(0));
//                bottom.remove(0);
//            } else {
//                newList.add(upper.get(0));
//                upper.remove(0);
//            }
//
//        }
//        return newList;
//    }