package com.example.joe.smarttask.Welcome;

//sub_packages need to import this in order to use R

import android.content.Context;
import android.content.Intent;
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

    //ViewPager allows flipping through pages
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    //array for welcome slides which inflate welcome_activity.xml
    private int[] welcome_layouts;

    //define Buttons
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        //set's the content (layout)
        setContentView(R.layout.welcome_activity);


        //ViewPager allows flipping through pages. ID is defined
        viewPager = (ViewPager) findViewById(R.id.view_pager_welcome_activity);

        //add inflating layouts
        welcome_layouts = new int[]{
                R.layout.welcome1,
                R.layout.welcome2
        };


        viewPagerAdapter=new ViewPagerAdapter();
        //makes inflating of welcome_activity possible
        viewPager.setAdapter(viewPagerAdapter);


        //for changes in slide add this (eg admin sees more...)
        //viewPager.addOnAdapterChangeListener(viewPagerPageChangeListener);


        //click on button closes app
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
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
