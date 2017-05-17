package com.example.joe.smarttask.smartaskMain.list;

import com.example.joe.smarttask.smartaskMain.settings.settingsCoordinator.listSettings.SubSettingsListObject;
import com.example.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers.SharedPrefs;
import com.example.joe.smarttask.smartaskMain.task.TaskObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by joe on 24/04/2017.
 */

public class ListSorter {

    public static final String DRAW_LINE = "drawLine";
    private static final String TAG = "CL_SortList";


    public static List<TaskObject> sortList(List<TaskObject> list) {
        if (list != null) {
            return preferedSort(list);
        } else {
            return new ArrayList<>();
        }
    }

    private static List<TaskObject> preferedSort(List<TaskObject> list) {
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
        Collections.reverse(newList);

        //        Add completed items if set in settings
        if (SharedPrefs.getShowPastItems() == true) {
//        define separator line
        TaskObject tO = new TaskObject();
        tO.setStatus(DRAW_LINE);
        tO.setId(DRAW_LINE);
            tO.setDatetime(1111111111111l);
            tO.setPriority(-1);
            tO.setResponsible("0");
        tO.setName("0");
        tO.setDescription("0");
        newList.add(0, tO);

            for (TaskObject t : list) {
                newList.add(0, t);
            }
        }
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
                if (t.getDatetime() > sortedList.get(c).getDatetime()) {
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

        for (int c = 0; c < list.size(); c++) {
            if (list.get(c).getPriority()==(0)) {
                priorityHighList.add(list.get(c));
                list.remove(c);
                c--;
            } else if (list.get(c).getPriority()==1) {
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
