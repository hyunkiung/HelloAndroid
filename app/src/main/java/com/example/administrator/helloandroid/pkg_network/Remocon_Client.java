package com.example.administrator.helloandroid.pkg_network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.lang.ref.WeakReference;

public class Remocon_Client extends AppCompatActivity implements View.OnClickListener {

    private ActionBar action_Bar;   // 액션바

    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 소켓통신 리모콘 : ";
    // 메세지 토스트 메소드 (공용)
    void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private Button mBTN_up;
    private Button mBTN_left;
    private Button mBTN_down;
    private Button mBTN_right;
    private Button mBTN_enter;
    private Button mBTN_server;
    private boolean running = false;

    private Remote_Client_Socket sendCient;

    // 핸들러 클래스 선언
    final Message_Handler_Call mHandler = new Message_Handler_Call(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remocon_client);
        // 액션바 선언
        action_Bar = this.getSupportActionBar();
        if (action_Bar != null) {
            action_Bar.setTitle("소켓 통신으로 데스크탑 서버 컨트롤");
        }

        mBTN_up = (Button)findViewById(R.id.btn_up);
        mBTN_left = (Button)findViewById(R.id.btn_left);
        mBTN_down = (Button)findViewById(R.id.btn_down);
        mBTN_right = (Button)findViewById(R.id.btn_right);
        mBTN_enter = (Button)findViewById(R.id.btn_enter);
        mBTN_server = (Button)findViewById(R.id.btn_server);

        mBTN_up.setOnClickListener(this);
        mBTN_left.setOnClickListener(this);
        mBTN_down.setOnClickListener(this);
        mBTN_right.setOnClickListener(this);
        mBTN_enter.setOnClickListener(this);
        mBTN_server.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendCient.socketClose();
        running = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_up :
                sendCient_Runnable("UP");
                break;
            case R.id.btn_left :
                sendCient_Runnable("LEFT");
                break;
            case R.id.btn_down :
                sendCient_Runnable("DOWN");
                break;
            case R.id.btn_right :
                sendCient_Runnable("RIGHT");
                break;
            case R.id.btn_enter :
                sendCient_Runnable("ENTER");
                break;
            case R.id.btn_server :
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendCient = new Remote_Client_Socket();
                        show_Log(String.valueOf(sendCient));
                        sendCient.connect(mHandler, "192.168.1.3", 6000);
                        running = true;
                    }
                }).start();
                mBTN_server.setText("리모콘을 컨트롤 하세요.");
                break;
        }

    }

    private void sendCient_Runnable(final String msg) {
        if(running) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (msg.length() > 0) {
                        sendCient.sendMessage(msg);
                    }
                }
            }).start();
        } else {
            mBTN_server.setText("서버에 접속부터 해주세요.");
        }
    }


    //========================================================
    // 핸들러 처리를 위한 Handler 상속 클래스
    //========================================================
    static class Message_Handler_Call extends Handler {
        private final WeakReference<Remocon_Client> mActivity;

        Message_Handler_Call(Remocon_Client activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Remocon_Client activity = mActivity.get();
            if(activity != null) {
                activity.sub_HandleMessage(msg);
            }
        }
    }

    private void sub_HandleMessage(Message inputMessage) {
        String msg = (String) inputMessage.obj;
        show_Log(msg);
    }
}
