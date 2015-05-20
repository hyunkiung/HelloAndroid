package com.example.administrator.helloandroid.pkg_fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015-05-20.
 */
public class exam04_MyViewPager extends ViewPager {

    private static String TAG = "로그 / fragment Exam4 Main";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    private boolean enabled = true;

    public exam04_MyViewPager(Context context) {
        super(context);
        show_Log("exam04_MyViewPager 생성자1");
    }

    public exam04_MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        show_Log("exam04_MyViewPager 생성자2");
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        show_Log("exam04_MyViewPager onTouchEvent / 터치 드래그 길이만큼 더 많이 발생");
        if(this.enabled) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        show_Log("exam04_MyViewPager onInterceptTouchEvent / 터치하면 한번만 발생");
        if(this.enabled) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    public void setPagingEnabled(boolean enabled)
    {
        show_Log("exam04_MyViewPager setPagingEnabled");
        this.enabled = enabled;
    }
}
