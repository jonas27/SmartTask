package com.example.joe.smarttask.FireBase;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by joe on 20/02/2017.
 */

public class CheckSingUpData {

    Context context;
    private boolean noError;

    public CheckSingUpData(Context context) {
        this.context = context;
    }

    protected boolean setData(String[] sArray) {

        noError = checkName(sArray[0]) &&
                checkName(sArray[1]) &&
                checkBirthday(sArray[2]) &&
                checkEmail(sArray[3]) &&
                checkName(sArray[4]) &&
                checkPassword(sArray[5]);
        return false;
    }

    private boolean checkName(String s) {
        boolean onlyLetters = false;
        if (s.equals("")) {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            onlyLetters = true;
            char[] cArray = s.toCharArray();

            for (char c : cArray) {
                if (!Character.isLetter(c)) {
                    onlyLetters = false;
                    Toast.makeText(context, "Please enter only letter as your name.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return onlyLetters;
    }


    public boolean checkBirthday(String s) {
        boolean rightStructure = true;
        if (s.equals("")) {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            char[] cArray = s.toCharArray();
            if (!Character.isDigit(cArray[0]) || !Character.isDigit(cArray[1]) || !Character.isDigit(cArray[3]) || !Character.isDigit(cArray[4]) || !Character.isDigit(cArray[6]) || !Character.isDigit(cArray[7]) || !Character.isDigit(cArray[8]) || !Character.isDigit(cArray[9]) || cArray[2] != '/' || cArray[5] != '/') {
                rightStructure = false;
                Toast.makeText(context, "Please make sure date is in mm/dd/yyyy", Toast.LENGTH_SHORT).show();

            }
        }
        return rightStructure;
    }


    private boolean checkEmail(String s) {
        boolean atSign = true;
        if (s.equals("")) {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            char[] cArray = s.toCharArray();
            for (char c : cArray) {
                if (c == '@') {
                    atSign = true;
                    return atSign;
                } else {
                    atSign = false;

                }
            }
        }
        Toast.makeText(context, "Your email has no @ sign.", Toast.LENGTH_SHORT).show();
        return atSign;
    }

    private boolean checkPassword(String s) {
        boolean specialSign = false;
        if (s.length() < 6) {
            Toast.makeText(context, "Your passwords needs more than 6 characters", Toast.LENGTH_SHORT).show();
        } else {
            char[] cArray = s.toCharArray();
            for (char c : cArray) {
                if (c == '@' || c == '_' || c == '-' || c == '.' || c == ',' || c == '$' || c == '%') {
                    specialSign = true;
                    Toast.makeText(context, "Password must contain special sign", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return !specialSign;
    }


}
