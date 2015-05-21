package com.example.administrator.helloandroid.pkg_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

/**
 * Created by Administrator on 2015-05-21.
 */
public class exam05_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_exam05_textview, container, false);

        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.textView)).setText("Page " + args.getInt("page_position"));

        return rootView;
    }
}
