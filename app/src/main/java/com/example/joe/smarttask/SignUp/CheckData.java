package com.example.joe.smarttask.SignUp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by joe on 20/02/2017.
 */

public class CheckData {

    Context context;
    private boolean noError;

    public CheckData(Context context) {
        this.context = context;
    }

    protected boolean setData(String[] sArray) {
        checkName(sArray[0]);
        checkName(sArray[1]);

        checkEmail(context, sArray[3]);


        return noError;
    }

    private boolean checkName(String s) {
        boolean onlyLetters = true;
        char[] cArray = s.toCharArray();

        for (char c : cArray) {
            if (!Character.isLetter(c)) {
                onlyLetters = false;
                Toast.makeText(context, "Please enter only letter as your name", Toast.LENGTH_SHORT).show();
            }
        }
        return onlyLetters;
    }

    private boolean checkEmail(Context context, String s) {
        boolean atSign = true;


        return atSign;
    }


}
