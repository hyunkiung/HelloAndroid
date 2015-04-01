package com.example.administrator.helloandroid.pkg_thread;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

public class thread08_DelayRunnable extends ActionBarActivity implements View.OnClickListener {

    private TextView mTV_Status1, mTV_Status2;
    private ProgressBar mPGB_Connect1, mPGB_Connect2;
    private Handler nHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread08_delay_runnable);

        mTV_Status1 = (TextView)findViewById(R.id.tv_Status1);
        mPGB_Connect1 = (ProgressBar)findViewById(R.id.pgb_Connect1);
        mTV_Status2 = (TextView)findViewById(R.id.tv_Status2);
        mPGB_Connect2 = (ProgressBar)findViewById(R.id.pgb_Connect2);

        //단계1 : 데이터 전송 시작 버튼 클릭시 다이얼로그를 띄운다.
        findViewById(R.id.btn_Trans).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Trans :
                CreateDialog();
                break;
            default :
                break;
        }
    }

    public void CreateDialog() {
        // 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("접속 요청 알림");
        builder.setMessage("데이터 전송을 요청하시겠습니까?");
        builder.setNegativeButton("취소", null);
        builder.setPositiveButton("요청", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // 전송시작
                mTV_Status1.setText("전송요청을 시작하였습니다.1");
                mPGB_Connect1.setVisibility(View.VISIBLE);
                mTV_Status2.setText("전송요청을 시작하였습니다.2");
                mPGB_Connect2.setVisibility(View.VISIBLE);
                TransThread nThread = new TransThread();
                nThread.start();
            }
        });

        // 생성한 빌더를 객체에 담아서 실행
        AlertDialog dialog = builder.create();
        //단계2 : 다이얼로그에서 확인버튼을 누르면 텍스트뷰에 전송요청중이라고 띄우고 3초뒤 전송시작이라고 바뀌고 프로그래스바 실행
        dialog.show();
    }


    //===========================================
    //== 스레드 메소드
    //===========================================
    private class TransThread extends Thread {
        @Override
        public void run() {
            int MaxCount = 200;

            for (int ii = 1; ii <= MaxCount; ii++) {
                DataTrans(ii, MaxCount);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void DataTrans(final int Data, final int mCount) {
        nHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPGB_Connect1.setMax(mCount);
                mTV_Status1.setText("전송 진행율 : " + Data);
                if (mPGB_Connect1.getProgress() < mPGB_Connect1.getMax()) {
                    mPGB_Connect1.incrementProgressBy(1);
                }

                mPGB_Connect2.setMax(mCount);
                if (mPGB_Connect2.getProgress() < mPGB_Connect2.getMax()) {
                    mPGB_Connect2.incrementProgressBy(1);
                    mTV_Status2.setText("전송 진행율 : " + mPGB_Connect2.getProgress());
                }
            }
        }, 3000);
    }

}
