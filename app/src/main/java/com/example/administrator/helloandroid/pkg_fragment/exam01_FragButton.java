package com.example.administrator.helloandroid.pkg_fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.helloandroid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class exam01_FragButton extends Fragment implements View.OnClickListener {

    private Button mButton;

    // Interface 만들어 쓸 때 정형화 된 코드 시작
    public interface OnImageChangeListener {
        void onImageChanged();
    }

    private OnImageChangeListener mListener;

    public void setOnImageChangeListener(OnImageChangeListener listener) {
        mListener = listener;
    }
    // Interface 만들어 쓸 때 정형화 된 코드 끝



    public exam01_FragButton() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam01_fragbutton, container, false);

        mButton = (Button)view.findViewById(R.id.btn_button);
        mButton.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onImageChanged();
        }
    }

}
