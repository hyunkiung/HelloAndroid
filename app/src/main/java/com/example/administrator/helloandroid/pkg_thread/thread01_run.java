package com.example.administrator.helloandroid.pkg_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;


/*
  01.버튼 생성후 클릭 이벤트 생성
  02.출력할 로그 생성
  03.Thread를 확장한 Request_thread 클래스 생성
  04.생성한 클래스에 run 메소드 생성
  05.클래스를 새로운 객체로 생성하고 버튼 클릭했을때 호출되도록 스타트 선언
  06.run 메소드에서는 스레드가 동작하는걸 알수 있도록 반복문을 생성하여 출력 메소드를 호출
  07.출력 메소드에서는 반복되는 데이터값을 받아서 로그로 출력
  08.스레드가 찍히는걸 볼수 있도록 Thread.sleep를 생성 (try, catch 자동 생성)
  09.텍스트뷰를 생성하고 로그 대신 텍스트뷰에 출력되도록 기능 추가.
  10.출력 메소드에서 텍스트뷰에 출력하면 되지만 UI 스레드는 직접 건들수 없기 때문에 핸들러 클래스 생성
*/
public class thread01_run extends ActionBarActivity {

    private static final String TAG = "thread01";
    private Button mBtn_thread1;
    private TextView mTv_thread1;

    Response_handler mHandler = new Response_handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread01_run);

        mBtn_thread1 = (Button) findViewById(R.id.btn_thread1);
        mTv_thread1 = (TextView) findViewById(R.id.tv_thread1);

        mBtn_thread1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "버튼을 눌렀음");

                Request_thread mThread = new Request_thread();
                mThread.start();
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

        private void thread_Print(String data) {
            Log.d(TAG, data);

            //아래 텍스트뷰 출력은 에러는 안나지만 앱이 죽는다.
            //이유는 스레드는 UI스레드를 직접 건들수 없기 때문
            //mTv_thread1.setText(data);

            //핸들러 호출 시작
            // Bundle은 키와값 쌍으로 이루어진 해쉬맵 자료구조.
            // 액티비티와 액티비티 사이에서 데이터를 주고 받을때도 사용.
            //public static Message obtain (Handler h, int what, int arg1, int arg2)
            Message mMessage = mHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("index", data);
            mMessage.setData(bundle);
            mHandler.sendMessage(mMessage);
        }
    }

    private class Response_handler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String data2 = bundle.getString("index");
            mTv_thread1.setText(data2);
        }

    }
}
