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

    private String task_status;
    private boolean running = false; //스레드 실행 플래그 (task_status를 사용해도 된다)
    private int hours, secs, mins, milli;
    private long time_Start = 0; //시작시간
    private long time_Stop = 0; //정지시간
    private int btn_status = 0; //시작 0, 정지 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread07_stop_watch);

        mTv_time = (TextView) findViewById(R.id.tv_time);
        mTv_status = (TextView) findViewById(R.id.tv_status);

        mTv_time.setText(timeFormatSet(0, 0, 0, 0));

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);

        StopWatch_Task time_Task = new StopWatch_Task();
        task_status = String.valueOf(time_Task.getStatus());
        mTv_status.setText("AsyncTask 상태 : " + task_status);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //시작
            case R.id.btn_start :
                if (btn_status == 0) {
                    time_Start = SystemClock.elapsedRealtime();
                } else {
                    long nowTime = SystemClock.elapsedRealtime(); //재시작 버튼을 누른 시점
                    time_Start += (nowTime - time_Stop);
                }
                btn_status = 0;
                new StopWatch_Task().execute();
                break;
            //일시정지
            case R.id.btn_stop :
                time_Stop = SystemClock.elapsedRealtime();
                btn_status = 1;
                new StopWatch_Task().cancel(true);
                break;
            //초기화
            case R.id.btn_reset :
                time_Start = SystemClock.elapsedRealtime();
                time_Stop = 0L;
                btn_status = 0;
                mTv_time.setText(timeFormatSet(0, 0, 0, 0));
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


    // AsyncTask 인자 <백그라운드, 프로그래스업데이트, 포스트익스큐트>
    private class StopWatch_Task extends AsyncTask<String, String, String> {

        // Background 작업 시작전에 UI 작업을 진행 한다 (쓰레드 수행 전 초기화)
        @Override
        protected void onPreExecute() {
            running = true;
            Log.d(TAG, "onPreExecute");
            task_status = String.valueOf(this.getStatus());
            mTv_status.setText("AsyncTask 상태 : " + task_status);
        }

        // Background 작업이 끝난 후 UI 작업을 진행 한다
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute");
            task_status = String.valueOf(this.getStatus());
            mTv_status.setText("AsyncTask 상태 : " + task_status);
        }

        // UI 업데이트 (Handler 에서 수행할 내용)
        @Override
        protected void onProgressUpdate(String... values) {
            //Log.d(TAG, "onProgressUpdate");
            mTv_time.setText(values[0]);
            task_status = String.valueOf(this.getStatus());
            mTv_status.setText("AsyncTask 상태 : " + task_status);
        }

        // 쓰레드 종료
        @Override
        protected void onCancelled() {
            running = false;
            Log.d(TAG, "onCancelled 종료되었다");
            task_status = String.valueOf(this.getStatus());
            mTv_status.setText("AsyncTask 상태 : " + task_status);
        }

        // background 작업 진행 (Thread 실행 내용)
        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground");
            while(!isCancelled() && running == true) {

                long tempNow = SystemClock.elapsedRealtime();
                long printTime = tempNow - time_Start;
                milli = (int) (printTime % 1000);
                secs = (int) (printTime / 1000) % 60;
                mins = secs / 60;
                hours = mins / 60;

                try {
                    Thread.sleep(10);
                    publishProgress(timeFormatSet(hours, mins, secs, milli));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

}
