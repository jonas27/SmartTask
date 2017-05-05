package com.example.joe.smarttask.SmartTask_MainPage.List;

import com.example.joe.smarttask.SmartTask_MainPage.Profile.ListProfile;
import com.example.joe.smarttask.SmartTask_MainPage.Settings.SubMenuFragments.SettList.SubSettingsListObject;
import com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses.SharedPrefs;
import com.example.joe.smarttask.SmartTask_MainPage.Task.TaskObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by joe on 24/04/2017.
 */

public class SortList {

    public static final String DRAW_LINE = "drawLine";
    private static final String TAG = "CL_SortList";
    private static SharedPrefs sharedPrefs;


    public static List<TaskObject> sortList(List<TaskObject> list) {
        if (list != null) {
            return preferedSort(list);
        } else {
            return new ArrayList<>();
        }
    }

    private static List<TaskObject> preferedSort(List<TaskObject> list) {
//        list=deleteNonUserTasks(list);
        switch (SharedPrefs.getPreferredOrder()) {
            case SubSettingsListObject.ORDER_BY_DATE: {
                list = sortByDate(list);
                break;
            }
            case SubSettingsListObject.ORER_BY_PRIORITY: {
                list = sortByPriority(list);
                break;
            }
            default: {
                list = sortByDate(list);
            }
        }

        return splitList(list);
    }
/*

//    if user is no admin only the users tasks should show
    private static List<TaskObject> deleteNonUserTasks(List<TaskObject> list){
        if(ListProfile.getProfile(SharedPrefs.getCurrentProfile()).getPprivileges().equals("true")){
//            nothing, show all tasks
        }else {
            Iterator<TaskObject> itr = list.iterator();
            while(itr.hasNext()){
                TaskObject t=itr.next();
                if(t.getResponsible().equals(SharedPrefs.getCurrentUser())){
//                    leave task
                }
                else{
                    list.remove(t);
                }
            }
        }
        return list;
    }
*/

    private static List<TaskObject> splitList(List<TaskObject> list) {

        List<TaskObject> newList = new ArrayList<>();

        for (int c = 0; c < list.size(); c++) {
            if (list.get(c).getStatus().equals("true")) {
//                TODO: Controll on false
            } else {
                newList.add(list.get(c));
                list.remove(c);
                c--;
            }
        }

        //reverse because of controlling for false --> can be deleted if (TODO controll on false) is implement
        Collections.reverse(newList);


        //        Add completed items if set in settings
        if (SharedPrefs.getShowPastItems() == true) {
//        define separator line
        TaskObject tO = new TaskObject();
        tO.setStatus(DRAW_LINE);
        tO.setId(DRAW_LINE);
            tO.setDatetime("1111111111111");
            tO.setPriority("-1");
        tO.setName("0");
        tO.setDescription("0");
        newList.add(0, tO);

            for (TaskObject t : list) {
                newList.add(0, t);
            }
        }
//reverse because of controlling for false --> can be deleted if (TODO controll on false) is implement
        Collections.reverse(newList);

        return newList;
    }


    //    Sort by date
    private static List<TaskObject> sortByDate(List<TaskObject> list) {
        List<TaskObject> sortedList = new ArrayList<>();
        int position = 0;

        for (TaskObject t : list) {
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

        Collections.reverse(sortedList);
        return sortedList;
    }


    private static List<TaskObject> sortByPriority(List<TaskObject> list) {
        list = sortByDate(list);
        List<TaskObject> priorityHighList = new ArrayList<>();
        List<TaskObject> priorityMiddleList = new ArrayList<>();
        int position = 0;

        for (int c = 0; c < list.size(); c++) {
            if (list.get(c).getPriority().equals("1")) {
                priorityHighList.add(list.get(c));
                list.remove(c);
                c--;
            } else if (list.get(c).getPriority().equals("2")) {
                priorityMiddleList.add(list.get(c));
                list.remove(c);
                c--;
            }
        }

        priorityMiddleList.addAll(list);
        priorityHighList.addAll(priorityMiddleList);

        return priorityHighList;
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
