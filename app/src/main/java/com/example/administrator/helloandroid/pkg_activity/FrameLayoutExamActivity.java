package com.example.administrator.helloandroid.pkg_activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.helloandroid.R;


public class FrameLayoutExamActivity extends ActionBarActivity {

    // 변수 선언하기
    Button mChangeButton;
    Button mCopyButton;
    ImageView mImage1;
    ImageView mImage2;
    ImageView mImageB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout_exam);

        mChangeButton = (Button)findViewById(R.id.btnSwap);
        mCopyButton = (Button)findViewById(R.id.btnCopy);

        mImage1 = (ImageView)findViewById(R.id.image1);
        mImage2 = (ImageView)findViewById(R.id.image2);
        mImageB = (ImageView)findViewById(R.id.image_Bottom);

        // 이미지 VISIBLE 이벤트
        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImage1.getVisibility() == View.VISIBLE) {
                    mImage1.setVisibility(View.INVISIBLE);
                    mImage2.setVisibility(View.VISIBLE);
                } else {
                    mImage2.setVisibility(View.INVISIBLE);
                    mImage1.setVisibility(View.VISIBLE);
                }
            }
        });

        mCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImage1.getVisibility() == View.VISIBLE) {
                    mImage2.setVisibility(View.GONE);
                    mImageB.setImageDrawable(mImage1.getDrawable());

                } else {
                    mImage1.setVisibility(View.GONE);
                    mImageB.setImageDrawable(mImage2.getDrawable());

                }
            }
        });
    }




    // 리소스에서 그림 파일을 가져와서 BitmapDrawable 객체로 생성
//    BitmapDrawable mBitmap;
//    mBitmap = (BitmapDrawable)getResources().getDrawable(R.id.image1);

    //ImageView에 BitmapDrawable 객체를 Set

//    BitmapDrawable d = (BitmapDrawable)((ImageView) findViewById(R.id.image1)).getDrawable();
//    Bitmap b = d.getBitmap();
//
//    b.setImageDrawable(mBitmap);




}
