
package com.example.administrator.helloandroid.pkg_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

/*
sendMessage() : 메소드 넘겨받은 메시지를 즉시 메시지큐의 맨뒤에 쌓는다
sendEmptyMessage() : 메소드 sendMessage와 비슷하나 아무 메시지도 보내지않는다.
sendMessageAtFrontOfQueue() : 메소드 넘겨받은 메소드를 메시지큐를 맨 앞에 쌓는다
sendMessageAtTime() : 메소드 넘겨받은 메소드를 특정 시각이 되면 메시지 큐에 쌓는다.
sendMessageDelayed() : 메소드 넘겨받은 메소드를 특정 시간이 지난 이후에 메시지큐에 쌓는다.

개념을 파악하기 전까지 멀티쓰레드와 싱글쓰레드를 바꿔서 생각했다.
멀티쓰레드는 하나의 액티비티에서 두개 이상의 쓰레드와 핸들러가 돌아가는것을 의미한다.
싱글쓰레드는 하나의 쓰레드와 핸들러로 두개 이상의 작업을 수행시키는것이다. 젠장!!
가만 생각해보니 한번 사용한 쓰레드는 다시 못쓰는데 그 쓰레드를 호출해서 다시 일을 시키니 안되는게 당연하지!
 */

public class thread02_progress extends ActionBarActivity implements View.OnClickListener {

    private static final String TAG = "thread";
    private ProgressBar mProgress1;
    private TextView mTv_stats1;
    private ProgressBar mProgress2;
    private TextView mTv_stats2;
    private ProgressBar mProgress3;
    private TextView mTv_stats3;
    private ProgressBar mProgress4;
    private TextView mTv_stats4;

    private Response_handler mHandler = new Response_handler();
    private Response_handler1 mHandler1 = new Response_handler1();
    private Response_handler2 mHandler2 = new Response_handler2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread02_progress);

        // 뷰 선언
        mProgress1 = (ProgressBar) findViewById(R.id.progress1);
        mTv_stats1 = (TextView) findViewById(R.id.tv_stats1);

        mProgress2 = (ProgressBar) findViewById(R.id.progress2);
        mTv_stats2 = (TextView) findViewById(R.id.tv_stats2);

        mProgress3 = (ProgressBar) findViewById(R.id.progress3);
        mTv_stats3 = (TextView) findViewById(R.id.tv_stats3);

        mProgress4 = (ProgressBar) findViewById(R.id.progress4);
        mTv_stats4 = (TextView) findViewById(R.id.tv_stats4);

        // 버튼 리스너 생성
        findViewById(R.id.btn_start1).setOnClickListener(this);
        findViewById(R.id.btn_start2).setOnClickListener(this);
        findViewById(R.id.btn_start3).setOnClickListener(this);
    }

    //======================================================
    // 버튼 이벤트 생성
    //======================================================
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_start1 :
                Request_thread mThread = new Request_thread();
                mThread.start();
                break;
            case R.id.btn_start2 :
                Thread mRunnable = new Thread (new Request_Runnable());
                mRunnable.start();
                break;
            case R.id.btn_start3 :
                Single_thread mThread_single = new Single_thread();
                mThread_single.start();
                break;
        }
    }

    //======================================================
    // Thread-3 멀티스레드 클래스
    //======================================================
    private class Single_thread extends Thread {
        public void run () {
            mProgress3.setProgress(0);
            mProgress4.setProgress(0);
            mHandler.sendEmptyMessage(0);
        }
    }

    private class Response_handler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(mProgress3.getProgress() < mProgress3.getMax()) {
                mProgress3.incrementProgressBy(5);
                mTv_stats3.setText("3번 진행상태 : " + mProgress3.getProgress());
                mHandler.sendEmptyMessageDelayed(0, 500);
            }

            if(mProgress4.getProgress() < mProgress4.getMax()) {
                mProgress4.incrementProgressBy(1);
                mTv_stats4.setText("4번 진행상태 : " + mProgress4.getProgress());
                mHandler.sendEmptyMessageDelayed(0, 100);
            }
        }
    }


    //======================================================
    // Thread-1 스레드 확장형 스레드 클래스
    //======================================================
    private class Request_thread extends Thread {
        public void run() {
            mProgress1.setProgress(0);
            mHandler1.sendEmptyMessage(0);
        }
    }

    //======================================================
    // Thread에게는 Runnable객체가 필요하다
    // Thread가 Thread로서 삶을 시작할 때 시작하는 일이 Runnable의 public void run() 메소드 를 수행하는 것이기 때문
    // Thread가 수행해야할 일을 Runnable의 public void run()에 써주는것
    // Thread가 해야할 일을 지정해주는 것은 Runnable 객체
    // Thread를 이용하는 프로그래밍은 크게 두가지 방법이 있는데 그 첫째는 Thread가 수행해야할 일을
    // Runnable 객체를 별도로 만들어 public void run() 메소드를 원하는 대로 만드는 것이고,
    // 둘째는 Thread 자체가 Runnable 객체이므로 Thread를 상속한후  public void run() 메소드를 원하는 대로 고치는 것.
    // Thread-1 스레드 임플리먼트 런에이블 클래스
    //======================================================
    private class Request_Runnable implements Runnable {
        public void run() {
            mProgress2.setProgress(0);
            mHandler2.sendEmptyMessage(0);
        }
    }

    //======================================================
    // Handler-1 핸들러 확장형 핸들러
    //======================================================
    private class Response_handler1 extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (mProgress1.getProgress() < mProgress1.getMax()) {
                mProgress1.incrementProgressBy(5);
                mTv_stats1.setText("진행상태 : " + mProgress1.getProgress());
                mHandler1.sendEmptyMessageDelayed(0, 500);
            }
        }
    }

    //======================================================
    // Handler-2 핸들러 확장형 핸들러
    //======================================================
    private class Response_handler2 extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (mProgress2.getProgress() < mProgress2.getMax()) {
                mProgress2.incrementProgressBy(1);
                mTv_stats2.setText("진행상태 : " + mProgress2.getProgress());
                mHandler2.sendEmptyMessageDelayed(0, 100);
            }
        }
    }

}
