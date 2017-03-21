package com.example.joe.smarttask.SignUp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by joe on 20/02/2017.
 */

public class CheckSingUpData {

    Context context;

    //Context for Toast
    private CheckSingUpData(Context context) {
        this.context = context;
    }


    // static factory methods (enhances encapsulation, object handling by garbage collector, class can't be subclassed and
    public static boolean controlSignUpArray(String[] sArray, Context context) {
        CheckSingUpData checkSingUpData = new CheckSingUpData(context);
        return checkSingUpData.checkName(sArray[0]) &&
                checkSingUpData.checkName(sArray[1]) &&
                checkSingUpData.checkBirthday(sArray[2]) &&
                checkSingUpData.checkEmail(sArray[3]) &&
                checkSingUpData.checkName(sArray[4]) &&
                checkSingUpData.checkPassword(sArray[5]);
    }

    public static boolean checkEmailWithPassword(String email, String password, Context context) {
        CheckSingUpData checkSingUpData = new CheckSingUpData(context);
        return checkSingUpData.checkEmail(email) && checkSingUpData.checkPassword(password);
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


    private boolean checkBirthday(String s) {
        boolean rightStructure = true;
        if (s.equals("")) {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            char[] cArray = s.toCharArray();
            if (!Character.isDigit(cArray[0]) || !Character.isDigit(cArray[1]) || !Character.isDigit(cArray[3]) || !Character.isDigit(cArray[4]) || !Character.isDigit(cArray[6]) || !Character.isDigit(cArray[7]) || !Character.isDigit(cArray[8]) || !Character.isDigit(cArray[9]) || cArray[2] != '/' || cArray[5] != '/') {
                rightStructure = true;
                Toast.makeText(context, "Please make sure date is in mm/dd/yyyy", Toast.LENGTH_SHORT).show();

            }
        }
        return rightStructure;
    }


    private boolean checkEmail(String s) {
        if (s.equals("")) {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            char[] cArray = s.toCharArray();
            for (char c : cArray) {
                if (c == '@') {
                    return true;
                }
            }
        }
        Toast.makeText(context, "Your email has no @ sign.", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean checkPassword(String s) {
        if (s.length() > 5) {
            return true;
        }
        //special sign in password?
            /*char[] cArray = s.toCharArray();
            for (char c : cArray) {
                if (c == '@' || c == '_' || c == '-' || c == '.' || c == ',' || c == '$' || c == '%') {
                    return true;
                }
            }
            Toast.makeText(context, "Password must contain special sign", Toast.LENGTH_SHORT).show();*/
        Toast.makeText(context, "Your passwords needs 6 or more characters", Toast.LENGTH_SHORT).show();
        return false;
    }


}
