package com.example.administrator.helloandroid.pkg_fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

public class exam04_Main extends AppCompatActivity {

    private static String TAG = "로그 / fragment Exam4 Main";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    private exam04_MyViewPager myViewPager;
    private MyPagerAdapter myPagerAdapter;

    private int ADAPTER_COUNT = 10;
    private int prevPosition = ADAPTER_COUNT - (ADAPTER_COUNT -1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam04_main);

        myPagerAdapter = new MyPagerAdapter(this.getSupportFragmentManager());

        myViewPager = (exam04_MyViewPager)findViewById(R.id.myPager);
        myViewPager.setPagingEnabled(true);
        myViewPager.setAdapter(myPagerAdapter);
        myViewPager.setCurrentItem(prevPosition, false);

    }

    //=================================================================
    // MyPagerAdapter START
    //=================================================================
    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(prevPosition == position) {
                show_Log("Application Loading~~");
            } else if (prevPosition < position) {
                        show_Log("Next Move!!!");
            } else {
                show_Log("Prev Move!!!");
            }

            TextViewFragment tvFragment = new TextViewFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            tvFragment.setArguments(args);

            show_Log("getItem.position : " + position + " , prevPosition : " + prevPosition);

            prevPosition = position;

            return tvFragment;
        }

        @Override
        public int getCount() {
            return ADAPTER_COUNT;
        }
    }

    //=================================================================
    // MyFragment START
    //=================================================================
    public static class TextViewFragment extends Fragment
    {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_exam04_textview, container, false);

            Bundle args = this.getArguments();
            int position = args.getInt("position");

            TextView tv1 = (TextView)rootView.findViewById(R.id.textView1);
            tv1.setText(String.valueOf(position));

            if(position % 3 == 2)
                tv1.setBackgroundColor(Color.GREEN);
            else if(position % 3 == 1)
                tv1.setBackgroundColor(Color.YELLOW);
            else
                tv1.setBackgroundColor(Color.LTGRAY);

            return rootView;
        }
    }

}
