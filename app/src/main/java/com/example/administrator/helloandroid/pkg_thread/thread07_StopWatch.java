package com.example.administrator.helloandroid.pkg_thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;
/*
StopWatch use AsyncTask
2015.03.29 H.K.U
thread06_StopWatch 문제점 보완을 위해서 doInbackground 계산을 변경
*/
public class thread07_StopWatch extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "thread";
    private TextView mTv_time;
    private TextView mTv_status;

    private boolean running = false;

    private long time_Start = 0L;
    private long time_swap = 0L;
    private long time_update = 0L;
    private long time_milli = 0L;

    private int hours, secs, mins, milli;



    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread07_stop_watch);

        mTv_time = (TextView) findViewById(R.id.tv_time);
        mTv_status = (TextView) findViewById(R.id.tv_status);

        // 시작시간 초기화 uptimeMillis = 시스템이 부팅될때부터 카운팅 된 밀리세컨드 단위의 시간
        time_Start = SystemClock.uptimeMillis();

        mTv_time.setText(timeFormatSet(0, 0, 0, 0));

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);

        //timeStartSet();
        StopWatch_Task time_Task = new StopWatch_Task();
        status = String.valueOf(time_Task.getStatus());
        mTv_status.setText("AsyncTask 상태 : " + time_Start);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start :
                new StopWatch_Task().execute();
                break;
            case R.id.btn_stop :
                //mTimeSwapBuff += mTimeInMilliseconds;
                //time_Start = time_update;
                time_swap += time_milli;
                new StopWatch_Task().cancel(true);
                break;
            case R.id.btn_reset :
                //timeStartSet();
                //mTv_time.setText(timeFormatSet(hours, mins, secs, milli));
                time_Start = 0L;
                time_swap = 0L;
                time_update = 0L;
                time_milli = 0L;
                new StopWatch_Task().cancel(true);
                break;
            default :
                break;
        }
    }

    // 스탑워치 타이머 표기 포맷
    public String timeFormatSet(int h, int m, int s, int mi) {
        String txt = String.format("%02d:%02d:%02d.%03d", h, m, s, mi);
        return txt;
    }

    // 타임 변수 초기화
//    public void timeStartSet() {
//        milli = 0;
//        secs = 0;
//        mins = 0;
//        hours = 0;
//    }


    // AsyncTask 인자 <백그라운드, 프로그래스업데이트, 포스트익스큐트>
    private class StopWatch_Task extends AsyncTask<String, String, String> {

        // Background 작업 시작전에 UI 작업을 진행 한다 (쓰레드 수행 전 초기화)
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
            running = true;
            status = String.valueOf(this.getStatus());
            mTv_status.setText("AsyncTask 상태 : " + status);
        }

        // Background 작업이 끝난 후 UI 작업을 진행 한다
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute");
            status = String.valueOf(this.getStatus());
            mTv_status.setText("AsyncTask 상태 : " + status);
        }

        // UI 업데이트 (Handler 에서 수행할 내용)
        @Override
        protected void onProgressUpdate(String... values) {
            //Log.d(TAG, "onProgressUpdate");
            mTv_time.setText(values[0]);
            status = String.valueOf(this.getStatus());
            mTv_status.setText("AsyncTask 상태 : " + status);
        }

        // 쓰레드 종료
        @Override
        protected void onCancelled() {
            Log.d(TAG, "onCancelled 종료되었다");
            running = false;
            status = String.valueOf(this.getStatus());
            mTv_status.setText("AsyncTask 상태 : " + status);
        }

        // background 작업 진행 (Thread 실행 내용)
        @Override
        protected String doInBackground(String... params) {
            //Log.d(TAG, "doInBackground");
            while(!isCancelled() && running == true) {

                time_milli = SystemClock.uptimeMillis() - time_Start;
                time_update = time_swap + time_milli;

                milli = (int) (time_update % 1000);
                secs = (int) (time_update / 1000);
                secs = secs % 60;
                mins = secs / 60;
                hours = mins / 60;

                try {
                    Thread.sleep(1);
                    publishProgress(timeFormatSet(hours, mins, secs, milli));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

}
