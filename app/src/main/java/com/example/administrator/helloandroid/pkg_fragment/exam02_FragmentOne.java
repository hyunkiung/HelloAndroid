package com.example.administrator.helloandroid.pkg_fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.helloandroid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class exam02_FragmentOne extends Fragment {

    public exam02_FragmentOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam02_fragmentone, container, false);
    }
}
