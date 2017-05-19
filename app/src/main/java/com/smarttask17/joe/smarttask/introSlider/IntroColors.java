package com.smarttask17.joe.smarttask.introSlider;

import android.graphics.Color;

/**
 * Helper class for setting the colors for the circles
 * use material design colors https://material.io/guidelines/style/color.html#color-color-palette
 *
 * 100% by us
 */

public class IntroColors {


    //counter==position: active sheet/circle
    //counter!=position: not active sheet
    //page colors 0: Light Blue      1: Pink     2: Orange      3: Yellow       4: Light Green
    protected int chooseColor(int position, int counter) {
        int color = 0;
        switch (position) {
            case 0: {
                if (counter == position) {
                    color = Color.parseColor("#01579B");
                } else {
                    color = Color.parseColor("#B3E5FC");
                }
                break;
            }
            case 1: {
                if (counter == position) {
                    color = Color.parseColor("#880E4F");
                } else {
                    color = Color.parseColor("#F8BBD0");
                }
                break;
            }
            case 2: {
                if (counter == position) {
                    color = Color.parseColor("#E65100");
                } else {
                    color = Color.parseColor("#FFE0B2");
                }
                break;
            }
            case 3: {
                if (counter == position) {
                    color = Color.parseColor("#FFD600");
                } else {
                    color = Color.parseColor("#FFF9C4");
                }
                break;
            }
            case 4: {
                if (counter == position) {
                    color = Color.parseColor("#33691E");
                } else {
                    color = Color.parseColor("#DCEDC8");
                }
                break;
            }
        }
        return color;
    }
}
