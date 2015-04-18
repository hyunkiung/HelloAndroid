package com.example.administrator.helloandroid.pkg_network;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

public class Local_Socket_Chat extends ActionBarActivity implements View.OnClickListener {

    private TextView mTV_chat;
    private TextView mTV_status;
    private Button mBTN_server;
    private Button mBTN_client;
    private Button mBTN_send;
    private EditText mET_message;

    private String msg;
    private Local_Socket_Runnable sendRunnable;
    private Local_Socket_Server sendServer;

    // 흐름 확인용 로그 출력 메소드
    private void Result_Log(String msg) { Log.d("로그 : ", msg); }

    // 메세지 토스트 메소드 (공용)
    void showToast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_socket_chat);

        mTV_chat = (TextView) findViewById(R.id.tv_chat);
        mTV_status = (TextView) findViewById(R.id.tv_status);
        mBTN_server = (Button) findViewById(R.id.btn_server);
        mBTN_client = (Button) findViewById(R.id.btn_client);
        mBTN_send = (Button) findViewById(R.id.btn_send);
        mET_message = (EditText) findViewById(R.id.et_message);

        mBTN_server.setOnClickListener(this);
        mBTN_client.setOnClickListener(this);
        mBTN_send.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_server :
                sendServer = new Local_Socket_Server();
                new Thread(sendServer).start();
                break;

            case R.id.btn_client :
                sendRunnable = new Local_Socket_Runnable(null);
                new Thread(sendRunnable).start();
                break;

            case R.id.btn_send :
                msg = mET_message.getText().toString();
                if (msg.length() > 0)
                {
                    sendRunnable = new Local_Socket_Runnable(msg);
                    new Thread(sendRunnable).start();
                    mET_message.setText("");
                    mTV_chat.append("\n" + sendRunnable.getMsg());
                }
                break;
        }

    }
}
