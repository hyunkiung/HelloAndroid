package com.example.administrator.helloandroid.pkg_fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.example.administrator.helloandroid.R;

public class exam06_SlidingTab extends AppCompatActivity {

    private static String TAG = "로그 / fragment exam06_SlidingTab";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;  // 액션바와 탭에 적용될 색상
    private ActionBar action_Bar;   // 액션바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam06_slidingtab);

        action_Bar = this.getSupportActionBar();
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        changeColor(currentColor);
    }

    // 액션바 오버플로우 메뉴 레이아웃
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exam06_slidingtab, menu);
        return true;
    }

    // 액션바 오버플로우 메뉴 실행
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_contact:
                exam06_QuickContactFragment dialog = new exam06_QuickContactFragment();
                dialog.show(getSupportFragmentManager(), "QuickContactFragment");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 회전시 Bundle 색상 저장
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
        //show_Log("onSaveInstanceState currentColor === " + currentColor);
    }

    // 회전시 Bundle 색상 복원
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeColor(currentColor);
        //show_Log("onRestoreInstanceState currentColor === " + currentColor);
    }

    // 앱 종료시 SharedPreferences 색상 저장
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = getSharedPreferences("PagerSlidingTab", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("currentColor", currentColor);
        editor.commit();
        //show_Log("onPause currentColor === " + currentColor);
    }

    // 앱 실행시 SharedPreferences 색상 복원
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("PagerSlidingTab", MODE_PRIVATE);
        currentColor = pref.getInt("currentColor", currentColor);
        changeColor(currentColor);
        //show_Log("onResume currentColor === " + currentColor);
    }

    //========================================================
    // changeColor 메소드
    //========================================================
    private void changeColor(int newColor) {

        tabs.setIndicatorColor(newColor);

        // Build.VERSION_CODES.HONEYCOM = 2011년 Android 3.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            show_Log("Build.VERSION.SDK_INT == " + Build.VERSION.SDK_INT);
            show_Log("Build.VERSION_CODES.HONEYCOMB == " + Build.VERSION_CODES.HONEYCOMB);

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

            if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    action_Bar.setBackgroundDrawable(ld);
                }

            } else {

                TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else {
                    action_Bar.setBackgroundDrawable(td);
                }
                td.startTransition(200);

            }

            oldBackground = ld;

            // 액션바 타이틀 보이기 여부 default : true
            action_Bar.setDisplayShowTitleEnabled(true);
            // 액션바 아이콘 보이기 여부 default : false
            action_Bar.setDisplayShowHomeEnabled(true);
            action_Bar.setIcon(R.drawable.ic_launcher_actionbar);
            action_Bar.setTitle("New App");
        }

        currentColor = newColor;
    }

    //=============================================================== onColorClicked
    public void onColorClicked(View v) {
        int color = Color.parseColor(v.getTag().toString());
        changeColor(color);
    }

    //=============================================================== Drawable.Callback
    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            action_Bar.setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    //=============================================================== MyPagerAdapter
    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = { "내장메모리 전체", "외장메모리 전체", "폴더탐색 듣기", "즐겨찾기1", "즐겨찾기2", "즐겨찾기3",
                "즐겨찾기4", "즐겨찾기5" };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return exam06_SuperAwesomeCardFragment.newInstance(position);
        }
    }


}
