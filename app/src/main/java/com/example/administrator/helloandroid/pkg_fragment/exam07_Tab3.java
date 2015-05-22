package com.example.administrator.helloandroid.pkg_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.helloandroid.R;

@SuppressLint("ValidFragment")
public class exam07_Tab3 extends Fragment {
    Context mContext;

    public exam07_Tab3(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exam07_tab3, null);

        return view;
    }

}
