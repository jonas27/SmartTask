package com.example.joe.smarttask.IntroSlider;

import android.content.Context;

import com.example.joe.smarttask.R;

/**
 * Sets the colors for the circles
 * use material design colors https://material.io/guidelines/style/color.html#color-color-palette
 */

public class IntroColors {

    protected static Context sContext;

    //    helper class is not supposed to instantiated
    private IntroColors() {
    }

    /**
     * @param position is the current page displayed in the slider
     * @param counter  is the current circle for which the color needs to be set
     *                 counter==position: active sheet/circle
     *                 counter!=position: not active sheet
     *                 page colors 0: Light Blue      1: Pink     2: Orange      3: Yellow       4: Light Green
     */
    public static int getColorCircles(int position, int counter) {
        return getColor(position, counter);
    }

    private static int getColor(int position, int counter) {
        int color = 0;
        switch (position) {
            case 0: {
                if (counter == position) {
                    color = sContext.getResources().getColor(R.color.intro_0_circle_active);
                } else {
                    color = sContext.getResources().getColor(R.color.intro_0_circle_inactive);
                }
                break;
            }
            case 1: {
                if (counter == position) {
                    color = sContext.getResources().getColor(R.color.intro_1_circle_active);
                } else {
                    color = sContext.getResources().getColor(R.color.intro_1_circle_inactive);
                }
                break;
            }
            case 2: {
                if (counter == position) {
                    color = sContext.getResources().getColor(R.color.intro_2_circle_active);
                } else {
                    color = sContext.getResources().getColor(R.color.intro_2_circle_inactive);
                }
                break;
            }
            case 3: {
                if (counter == position) {
                    color = sContext.getResources().getColor(R.color.intro_3_circle_active);
                } else {
                    color = sContext.getResources().getColor(R.color.intro_3_circle_inactive);
                }
                break;
            }
            case 4: {
                if (counter == position) {
                    color = sContext.getResources().getColor(R.color.intro_4_circle_active);
                } else {
                    color = sContext.getResources().getColor(R.color.intro_4_circle_inactive);
                }
                break;
            }
        }
        return color;
    }


    public static int getBackgroundColor(int position) {
        return getNewBackgroundColor(position);
    }

    private static int getNewBackgroundColor(int position) {
        int color = 0;
        switch (position) {
            case 0: {
                color = sContext.getResources().getColor(R.color.intro_0_background);
                break;
            }
            case 1: {
                color = sContext.getResources().getColor(R.color.intro_1_background);
                break;
            }
            case 2: {
                color = sContext.getResources().getColor(R.color.intro_2_background);
                break;
            }
            case 3: {
                color = sContext.getResources().getColor(R.color.intro_3_background);
                break;
            }
            case 4: {
                color = sContext.getResources().getColor(R.color.intro_4_background);
                break;
            }

        }
        return color;
    }


}
