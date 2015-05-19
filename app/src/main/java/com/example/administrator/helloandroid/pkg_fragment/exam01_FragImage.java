package com.example.administrator.helloandroid.pkg_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.helloandroid.R;

// Fragment는 액티비티가 아니다. 따라서 매니패스트에 있으면 안된다.

public class exam01_FragImage extends Fragment {

    private ImageView mImageView;

    public exam01_FragImage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam01_fragimage, container, false);

        mImageView = (ImageView)view.findViewById(R.id.iv_image);

        return view;
    }

    public void setChangeImage(int res) {
        mImageView.setImageResource(res);
    }

}
