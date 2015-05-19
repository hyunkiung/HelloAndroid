package com.example.administrator.helloandroid.pkg_fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.helloandroid.R;

public class exam01_main extends AppCompatActivity implements exam01_FragButton.OnImageChangeListener {

    private exam01_FragButton mFragmentButton;
    private exam01_FragImage mFragmentImage;

    private boolean mDualPane;
    private static String TAG = "로그 / fragment Exam1 Main";
    private void show_Log(String msg) { Log.d(TAG, msg); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exam01_main);
        // Android 기본 제공하는 FragmentManager
        // getFragmentManager();

        // v4 용 FragmentManager
        // Fragment는 View가 아니므로 findViewById 로 가져올 수 없다
        mFragmentImage = null;

        mFragmentButton = (exam01_FragButton)getSupportFragmentManager().findFragmentById(R.id.fragment_Button);

        // 세로인 경우
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mFragmentImage = (exam01_FragImage)getSupportFragmentManager().findFragmentById(R.id.fragment_Image);
        }



        show_Log(String.valueOf(mFragmentImage));

        if (mFragmentImage == null) {
            mDualPane = false;
        } else {
            mDualPane = true;
        }

        mFragmentButton.setOnImageChangeListener(this);
    }


    @Override
    public void onImageChanged() {
        // 세로인 경우 (mFragmentImage == null)
        if (mDualPane) {
            mFragmentImage.setChangeImage(R.drawable.gtr44);
        } else {
            Intent intent = new Intent(getApplicationContext(), exam01_ImageMain.class);
            startActivity(intent);
        }
    }
}
