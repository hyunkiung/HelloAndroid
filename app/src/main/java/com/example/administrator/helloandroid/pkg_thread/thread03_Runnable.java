
package com.example.administrator.helloandroid.pkg_thread;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;

/*
 thread01_run 소스를 활용하여 Runnable을 사용해본다.

 */
public class thread03_Runnable extends ActionBarActivity {

    private static final String TAG = "thread01";
    private Button mBtn_thread1;
    private TextView mTv_thread1;

    private ProgressBar mProgress1;
    private TextView mTv_stats1;
    private Button mBtn_start1;

    // Response_handler mHandler = new Response_handler();
    // 핸들러가 객체로 만들어질수 있게 기본 클래스로 되어 있다.
    Handler mHandler = new Handler();
    Handler proHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread03_runnable);

        mBtn_thread1 = (Button) findViewById(R.id.btn_thread1);
        mTv_thread1 = (TextView) findViewById(R.id.tv_thread1);

        mProgress1 = (ProgressBar) findViewById(R.id.progress1);
        mTv_stats1 = (TextView) findViewById(R.id.tv_stats1);
        mBtn_start1 = (Button) findViewById(R.id.btn_start1);

        mBtn_thread1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "버튼을 눌렀음");

                Request_thread mThread = new Request_thread();
                mThread.start();
            }
        });

        // 프로그래스바 실행 버튼 리스너
        mBtn_start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress1.setProgress(0);
                Progress_thread pThread = new Progress_thread();
                pThread.start();
            }
        });


    }

    private class Request_thread extends Thread {
        public void run() {
            for (int ii = 0; ii < 100; ii++) {
                thread_Print("## " + ii + "번 호출");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        // post내에서 밖의 인자를 건들수 없기 때문에 final로 변한것에 주의 (자동으로 바뀜)
        private void thread_Print(final String data) {
            Log.d(TAG, data);
            /*
            Message mMessage = mHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("index", data);
            mMessage.setData(bundle);
            mHandler.sendMessage(mMessage);
            */

            // 러너블이 핸들러쪽으로 넘어가서 순서대로 처리된다.
            // 핸들러 객체를 안쓰고 UI를 건든다는것에 주목.
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTv_thread1.setText(data);
                }
            });
        }
    }

    /*
     * private class Response_handler extends Handler {
     * @Override public void handleMessage(Message msg) { Bundle bundle =
     * msg.getData(); String data2 = bundle.getString("index");
     * mTv_thread1.setText(data2); } }
     */


    // 쓰레드에서 UI 접근할때 핸들러를 사용해야 한다.
    //=========================================================
    //=========================================================
    // 변경한 러너블을 이용하여 프로그래스바를 실행해본다.
    //=========================================================
    //=========================================================
    private class Progress_thread extends Thread {
        public void run() {

            proHandler.sendEmptyMessage(0);
            // 반복문이 필요.
            for (int ii = 0; ii < 20; ii++) {

                Runnable_Print();

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        private void Runnable_Print() {
            //--------------------------------
            proHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mProgress1.getProgress() < mProgress1.getMax()) {
                        mProgress1.incrementProgressBy(5);
                        mTv_stats1.setText("진행상태 : " + mProgress1.getProgress());
                        proHandler.sendEmptyMessageDelayed(0, 500);
                    }
                }
            });
            //--------------------------------
        }


    }
    //=========================================================
}
