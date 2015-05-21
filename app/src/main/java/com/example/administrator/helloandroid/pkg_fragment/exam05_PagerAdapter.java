package com.example.administrator.helloandroid.pkg_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2015-05-21.
 */
public class exam05_PagerAdapter extends FragmentPagerAdapter {

    public exam05_PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new exam05_Fragment();

        Bundle args = new Bundle();
        args.putInt("page_position", position + 1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + (position + 1);
    }
}
