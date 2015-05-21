package com.example.administrator.helloandroid.pkg_fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.helloandroid.R;

public class exam05_PagerTitle extends AppCompatActivity {

    private exam05_PagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam05_pagertitle);

        mCustomPagerAdapter = new exam05_PagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
    }


}
