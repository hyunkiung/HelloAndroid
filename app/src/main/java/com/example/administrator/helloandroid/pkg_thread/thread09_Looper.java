
package com.example.administrator.helloandroid.pkg_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.helloandroid.R;
/*
핸들러는 스레드의 메시지를 받아서 처리하는것이다.
핸들러 내부적으로 큐를 가지고 있어서 다른 스레들로부터 온 메시지들을
메시지큐에서 하나씩 꺼내서 처리한다.

메시지는 스레드간의 신호다.
핸들러에 보낸다고 곧바로 처리되는것이 아니다.
동시다발적으로 메시지가 발생할 수 있기 때문에
메시지큐에 쌓았다가 처리한다.
메시지큐에 들어 있는 내용을 하나하나 꺼내서 처리하는 놈이 루퍼이다.
메인 스레드가 루퍼를 가지고 있으며 무한 루프를 돌며 큐의 내용을 처리한다.
 */

public class thread09_Looper extends ActionBarActivity {

    TextView textView01, textView02;
    EditText editText01, editText02;

    /**
     * 메인 스레드의 핸들러
     */
    MainHandler mainHandler;

    /**
     * 새로 만든 스레드
     */
    ProcessThread thread1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread09_looper);

        mainHandler = new MainHandler();
        thread1 = new ProcessThread();

        textView01 = (TextView) findViewById(R.id.textView01);
        textView02 = (TextView) findViewById(R.id.textView02);
        editText01 = (EditText) findViewById(R.id.editText01);
        editText02 = (EditText) findViewById(R.id.editText02);

        // 버튼 이벤트 처리
        Button processBtn = (Button) findViewById(R.id.processBtn);
        processBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inStr = editText01.getText().toString();
                Message msgToSend = Message.obtain();
                msgToSend.obj = inStr;

                thread1.handler.sendMessage(msgToSend);
            }
        });

        thread1.start();
    }

    /**
     * 새로 정의한 스레드
     */
    class ProcessThread extends Thread {
        // 새로운 스레드를 위한 핸들러
        ProcessHandler handler;

        public ProcessThread() {
            handler = new ProcessHandler();
        }

        public void run() {
            // 루퍼 사용
            Looper.prepare();
            Looper.loop();
        }

    }

    class ProcessHandler extends Handler {
        public void handleMessage(Message msg) {
            Message resultMsg = Message.obtain();
            resultMsg.obj = msg.obj + " Mike!!!";

            mainHandler.sendMessage(resultMsg);
        }
    }

    class MainHandler extends Handler {
        public void handleMessage(Message msg) {
            String str = (String) msg.obj;
            editText02.setText(str);
        }
    }

}
