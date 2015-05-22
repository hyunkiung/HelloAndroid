package com.example.administrator.helloandroid.pkg_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.helloandroid.R;

// 이건 뭔가? 이걸 넣으니 생성자 빨간줄이 사라졌다.
@SuppressLint("ValidFragment")
public class exam07_Tab1 extends Fragment {
    Context mContext;

    public exam07_Tab1(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exam07_tab1, null);

        Button button1;
        button1 = (Button) view.findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Uri uri = Uri.parse("http://daum.net");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        });

        return view;
    }


}
