package com.example.joe.smarttask.Welcome;

//sub_packages need to import this in order to use R

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.joe.smarttask.R;

/**
 * Class handles the welcome slides - slides inflate welcome_activity.xml and are no fragments (only one lifecycle)
 * Created on 07/02/2017.
 */

public class WelcomeActivity extends AppCompatActivity {

    //Intent
    private Intent intent;

    //ViewPager allows flipping through pages
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    //array for welcome slides which inflate welcome_activity.xml
    private int[] welcome_layouts;

    //define Buttons
    private Button skipBtn, nextBtn, gotitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
        //set's the content (layout)
        setContentView(R.layout.welcome_activity);

        //Bind all buttons to objects
        skipBtn = (Button) findViewById(R.id.btnSkip);
        nextBtn = (Button) findViewById(R.id.btnNext);

        //set's status bar color like background
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        //add inflating layouts before pageview as it gives nullpointer exception
        welcome_layouts = new int[]{
                R.layout.welcome1,
                R.layout.welcome2
        };


        //ViewPager allows flipping through pages. ID is defined
        viewPager = (ViewPager) findViewById(R.id.view_pager_welcome_activity);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);

        //for changes in slide
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == welcome_layouts.length - 1) {
                    nextBtn.setText("Got It!");
                } else {
                    nextBtn.setText("Next");
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //actions for buttons
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }





    /**
     * Base class providing the adapter to populate pages inside of a ViewPager
     * instantiates and sets new Window/Container as current. Destroys old window
     * Overwritten Methods below  are requirement to use PagerAdapter
     */

    public class ViewPagerAdapter extends PagerAdapter {


        private LayoutInflater layoutInflater;

        //Create the page for the given position.
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(welcome_layouts[position], container, false);
            container.addView(view);

            return view;
        }

        //Return the number of views available (#numbers of inflating pages)
        @Override
        public int getCount() {
            return welcome_layouts.length;
        }

        //???no idea what it does :D maybe checks if the current view is really an object???
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //Remove a page for the given position.
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
