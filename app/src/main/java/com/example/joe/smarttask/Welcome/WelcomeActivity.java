package com.example.joe.smarttask.Welcome;

//sub_packages need to import this in order to use R

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.smarttask.R;

/**
 * Class handles the welcome slides
 * Created on 07/02/2017.
 */

public class WelcomeActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private int[] welcome_layouts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_activity);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        welcome_layouts = new int[]{
                R.layout.welcome1,
                R.layout.welcome2
        };




        viewPagerAdapter=new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);

        //for changes in slide add this (eg admin sees more...)
        //viewPager.addOnAdapterChangeListener(viewPagerPageChangeListener);



    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }






    /**
     * instantiates and sets new Window/Container as current. Destroys old window
     * adapted to our needs
     */
    public class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(welcome_layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return welcome_layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
